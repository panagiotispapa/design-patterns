package com.design.islamic.model.tiles;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.*;
import java.util.function.Function;

import static com.design.common.Mappings.fromListOfLists;
import static com.design.common.PolygonTools.calcVertexes;
import static com.design.islamic.model.tiles.Hex.Type.HOR;
import static com.design.islamic.model.tiles.Hex.Type.VER;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.util.stream.Collectors.toList;

public class Hex {
    public static final double PHI = (2.0 * PI) / 6.0;

    private static final double HEIGHT_RATIO = cos(PHI / 2.0);
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
        return Arrays.stream(Hex.Vertex.values()).map(v -> v.transform(0, initialConditions, this)).collect(toList());
    }

    public List<List<Point2D>> lines(Pair<Point2D, Double> initialConditions, List<List<Hex.Vertex>> instructions) {
        return lines(0, initialConditions, instructions);
    }

    public List<List<Point2D>> lines(int offset, Pair<Point2D, Double> initialConditions, List<List<Hex.Vertex>> instructions) {
        return fromListOfLists(instructions, v -> v.transform(offset, initialConditions, this));
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

        private static final Map<Type, List<Point2D>> vertexes;

        static {
            vertexes = new HashMap<>();
            vertexes.put(HOR, calcVertexes(values().length, 0));
            vertexes.put(VER, calcVertexes(values().length, 0.5));

        }

        private Vertex(int index) {
            this.index = index;

        }

        public Point2D transform(int offset, Pair<Point2D, Double> initialConditions, Hex hex) {

            Point2D finalCentre = initialConditions.getLeft();
            Double R = initialConditions.getRight();
            for (CentreTransform centreTransform : hex.getTransforms()) {
                finalCentre = transform(offset, initialConditions).apply(centreTransform);
            }

            return goTo(R * hex.getRatio(), finalCentre).apply(getVertexPoint(offset, hex.getType()));
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
