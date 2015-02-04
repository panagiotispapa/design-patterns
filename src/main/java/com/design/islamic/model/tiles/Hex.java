package com.design.islamic.model.tiles;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.design.common.Mappings.fromListOfLists;
import static com.design.common.PolygonTools.calcVertexes;
import static com.design.islamic.model.tiles.Hex.Type.HOR;
import static com.design.islamic.model.tiles.Hex.Type.VER;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.util.stream.Collectors.toList;

public class Hex {
    public static final double PHI = (2.0 * PI) / 6.0;

    public static final double HEIGHT_RATIO = cos(PHI / 2.0);
    public static List<CentreTransform> NO_TRANSFORMS = Collections.emptyList();

    private final List<CentreTransform> transforms;
    private final double ratio;
    private final Type type;

    public Hex(List<CentreTransform> transforms, double ratio, Type type) {
        this.transforms = transforms;
        this.ratio = ratio;
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    public List<CentreTransform> getTransforms() {
        return transforms;
    }

    public double getRatio() {
        return ratio;
    }

    public Hex getMirror() {
        return newHex(transforms, ratio, type.revert());
    }

    public Hex getInternal() {
        return newHex(transforms, ratio * HEIGHT_RATIO, type.revert());
    }

    public Hex getExternal() {
        return newHex(transforms, ratio / HEIGHT_RATIO, type.revert());
    }

    public static Hex newHex(double ratio, Type type) {
        return newHex(NO_TRANSFORMS, ratio, type);
    }

    public static Hex newHex(CentreTransform transform, double ratio, Type type) {
        return newHex(Arrays.asList(transform), ratio, type);
    }

    public static Hex newHex(List<CentreTransform> transforms, double ratio, Type type) {
        return new Hex(transforms, ratio, type);
    }

    public List<Point2D> vertexes(Pair<Point2D, Double> initialConditions) {
        return Arrays.stream(Hex.Vertex.values()).map(Hex.Vertex.mapToPoint(0, initialConditions, this)).collect(toList());
    }

    public static Function<Hex, List<Point2D>> vertexes2(Pair<Point2D, Double> initialConditions) {
        return hex -> Arrays.stream(Hex.Vertex.values()).map(Hex.Vertex.mapToPoint(0, initialConditions, hex)).collect(toList());
    }

    public List<List<Point2D>> lines(Pair<Point2D, Double> initialConditions, List<List<Hex.Vertex>> instructions) {
        return lines(0, initialConditions, instructions);
    }

    public List<List<Point2D>> lines(int offset, Pair<Point2D, Double> initialConditions, List<List<Hex.Vertex>> instructions) {
        return fromListOfLists(Hex.Vertex.mapToPoint(offset, initialConditions, this)).apply(instructions);
    }
//    public List<List<Point2D>> lines(Pair<Point2D, Double> initialConditions, List<List<Hex.Vertex>> instructions) {
//        return lines(0,initialConditions, instructions);
//    }

    public Function<List<List<Hex.Vertex>>, List<List<Point2D>>> lines(Pair<Point2D, Double> initialConditions) {
        return lines(0, initialConditions);
    }

    public Function<List<List<Hex.Vertex>>, List<List<Point2D>>> lines(int offset, Pair<Point2D, Double> initialConditions) {
        return fromListOfLists(Hex.Vertex.mapToPoint(offset, initialConditions, this));
    }

    public static enum Type {
        VER,
        HOR;

        public Type revert() {
            return this == VER ? HOR : VER;
        }
    }

    public static enum Vertex {
        ONE(0),
        TWO(1),
        THREE(2),
        FOUR(3),
        FIVE(4),
        SIX(5);

        private final int index;

        public int getIndex() {
            return getIndex(0);
        }

        public int getIndex(int offset) {
            return (index + offset) % 6;
        }

        public static List<Vertex> ALL = Arrays.stream(Hex.Vertex.values()).collect(toList());

        private static final Map<Type, List<Point2D>> vertexes;

        static {
            vertexes = new HashMap<>();
            vertexes.put(HOR, calcVertexes(values().length, 0));
            vertexes.put(VER, calcVertexes(values().length, 0.5));

        }

        private Vertex(int index) {
            this.index = index;

        }

        public static Function<Vertex, Point2D> mapToPoint(Pair<Point2D, Double> initialConditions, Pair<Hex, Integer> instruction) {
            return mapToPoint(instruction.getRight(), initialConditions, instruction.getLeft());
        }

        public static Function<Vertex, Point2D> mapToPoint(int offset, Pair<Point2D, Double> initialConditions, Hex hex) {
            return v -> v.transform(offset, initialConditions, hex);
        }

        private static Function<Hex, Point2D> findCentre(int offset, Pair<Point2D, Double> initialConditions) {
            return hex -> {

                Point2D finalCentre = initialConditions.getLeft();
                for (CentreTransform centreTransform : hex.getTransforms()) {
                    finalCentre = transform(offset, initialConditions).apply(centreTransform);
                }
                return finalCentre;

            };
        }

        public static Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> vertexesToPointsFull(Pair<Point2D, Double> initialConditions) {
            return p -> IntStream.range(0, 6).mapToObj(i -> Hex.Vertex.vertexesToPoints(i, initialConditions).apply(Pair.of(p.getLeft(), p.getRight())))
                    .flatMap(l -> l.stream()).collect(toList());
        }

        public static Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> vertexesToPointsSingle(Pair<Point2D, Double> initialConditions) {
            return vertexesToPoints(0, initialConditions);
        }

        public static Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> vertexesToPoints(int offset, Pair<Point2D, Double> initialConditions) {
            return p -> fromListOfLists(
                    vertexToPoint(offset, initialConditions, p.getLeft(), findCentre(offset, initialConditions).apply(p.getLeft())))
                    .apply(p.getRight());
        }

        private static Function<Vertex, Point2D> vertexToPoint(int offset, Pair<Point2D, Double> initialConditions, Hex hex, Point2D finalCentre) {
            Double finalR = initialConditions.getRight() * hex.getRatio();

            return transform(offset, hex.getType(), finalR, finalCentre);

        }

        private static Function<Vertex, Point2D> transform(int offset, Type type, Double finalR, Point2D centre) {
            return v -> goTo(finalR, centre).apply(v.getVertexPoint(offset, type));
        }

        public Point2D transform(int offset, Pair<Point2D, Double> initialConditions, Hex hex) {

            Double finalR = initialConditions.getRight() * hex.getRatio();
            Point2D finalCentre = findCentre(offset, initialConditions).apply(hex);

            return transform(offset, hex.getType(), finalR, finalCentre).apply(this);

        }

        private static Function<CentreTransform, Point2D> transform(int offset, Pair<Point2D, Double> initialConditions) {
            return c -> goTo(initialConditions.getRight() * c.getRatio(), initialConditions.getLeft())
                    .apply(c.getVertex().getVertexPoint(offset, c.getType()));
        }

        private static Function<Point2D, Point2D> goTo(double distance, Point2D translation) {
            return p -> new Point2D.Double(p.getX() * distance + translation.getX(), p.getY() * distance + translation.getY());
        }

        public Point2D getVertexPoint(int offset, Type type) {
            return vertexes.get(type).get(getIndex(offset));
        }

    }

}
