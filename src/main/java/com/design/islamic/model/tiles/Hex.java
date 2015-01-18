package com.design.islamic.model.tiles;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.design.common.PolygonTools.calcVertexes;
import static com.design.islamic.model.tiles.Hex.Type.HOR;
import static com.design.islamic.model.tiles.Hex.Type.VER;
import static java.lang.Math.PI;
import static java.lang.Math.cos;

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

    public Hex getInternal() {
        System.out.println("HEIGHT_RATIO " + HEIGHT_RATIO);
        return newHex(transforms, ratio * HEIGHT_RATIO, type.revert());
    }

    public Hex getExternal() {
        return newHex(transforms, ratio / HEIGHT_RATIO, type.revert());
    }

    public static Hex newHex(List<CentreTransform> transforms, double ratio, Type type) {
        return new Hex(transforms, ratio, type);
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

        public Point2D transform(int offset, Point2D startingPoint, double R, Hex hex) {

            Point2D finalCentre = startingPoint;
            for (CentreTransform centreTransform : hex.getTransforms()) {
                finalCentre = transform(offset, finalCentre, R, centreTransform);
            }

            final Point2D vertexOrig = getVertexPoint(offset, hex.getType());

            return new Point2D.Double(
                    vertexOrig.getX() * R * hex.getRatio() + finalCentre.getX(),
                    vertexOrig.getY() * R * hex.getRatio() + finalCentre.getY()
            );

        }

        public Point2D transform(int offset, Point2D startingPoint, double R, CentreTransform centreTransform) {

            final float ratio = centreTransform.getRatio();

            final Hex.Type type = centreTransform.getType();
            final Point2D vertexOrig = centreTransform.getVertex().getVertexPoint(offset, type);

            return new Point2D.Double(
                    vertexOrig.getX() * R * ratio + startingPoint.getX(),
                    vertexOrig.getY() * R * ratio + startingPoint.getY()
            );

        }

        public Point2D getVertexPoint(int offset, Type type) {
            return vertexes.get(type).get(getIndex(offset));
        }

    }

}
