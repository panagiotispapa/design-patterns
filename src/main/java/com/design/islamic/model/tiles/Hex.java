package com.design.islamic.model.tiles;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.*;

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

    public List<Line2D> lines(int offset, Pair<Point2D, Double> initialConditions, List<Pair<Hex.Vertex, Hex.Vertex>> instructions) {
        return instructions.stream().map(i -> new Line2D.Double(
                i.getLeft().transform(offset, initialConditions, this),
                i.getRight().transform(offset, initialConditions, this)
        )).collect(toList());
    }

    public List<List<Point2D>> lines2(Pair<Point2D, Double> initialConditions, List<List<Hex.Vertex>> instructions) {
        return lines2(0, initialConditions, instructions);
    }

    public List<List<Point2D>> lines2(int offset, Pair<Point2D, Double> initialConditions, List<List<Hex.Vertex>> instructions) {
        return instructions.stream().map(v->toPoints(offset, initialConditions, v)).collect(toList());
    }

    private List<Point2D> toPoints(int offset, Pair<Point2D, Double> initialConditions, List<Vertex> vertexes) {
        return vertexes.stream().map(v->v.transform(offset, initialConditions, this)).collect(toList());
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
                finalCentre = transform(offset, initialConditions, centreTransform);
            }

            final Point2D vertexOrig = getVertexPoint(offset, hex.getType());

            return transform(vertexOrig, Triple.of(finalCentre, R, hex.getRatio()));

        }

        public Point2D transform(int offset, Pair<Point2D, Double> initialConditions, CentreTransform centreTransform) {

            final double ratio = centreTransform.getRatio();

            final Hex.Type type = centreTransform.getType();
            final Point2D vertexOrig = centreTransform.getVertex().getVertexPoint(offset, type);

            final Double R = initialConditions.getRight();
            final Point2D startingPoint = initialConditions.getLeft();

            return transform(vertexOrig, Triple.of(startingPoint, R, ratio));
        }

        private static Point2D transform(Point2D vertex, Triple<Point2D, Double, Double> transformation) {
            final Double newR = transformation.getRight() * transformation.getMiddle();
            return new Point2D.Double(
                    vertex.getX() * newR + transformation.getLeft().getX(),
                    vertex.getY() * newR + transformation.getLeft().getY()
            );
        }

        public Point2D getVertexPoint(int offset, Type type) {
            return vertexes.get(type).get(getIndex(offset));
        }

    }

}
