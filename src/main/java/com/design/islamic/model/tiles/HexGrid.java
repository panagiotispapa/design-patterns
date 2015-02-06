package com.design.islamic.model.tiles;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.design.islamic.model.tiles.Hex.HEIGHT_RATIO;

public class HexGrid {

    public static List<Point2D> grid(Point2D centre, double r, TYPE type, int size) {

        final double half = r * (size / 2);

        final Point2D start = new Point2D.Double(centre.getX() - half * type.getxRatio(), centre.getY() - half * type.getyRatio());

        return IntStream.range(0, size)
                .mapToObj(nextHorPoint(start, r, type))
                .flatMap(p -> IntStream.range(0, size)
                        .mapToObj(nextVerPoint(p, r, type)))
                .collect(Collectors.toList());
    }

    public static IntFunction<Point2D> nextHorPoint(Point2D start, double r, TYPE type) {
        return i -> new Point2D.Double(start.getX() + i * type.getxRatio() * r, start.getY() + (i % 2) * type.getyOffset() * r);
    }

    public static IntFunction<Point2D> nextVerPoint(Point2D start, double r, TYPE type) {
        return i -> new Point2D.Double(start.getX() + (i % 2) * type.getxOffset() * r, start.getY() + i * type.getyRatio() * r);
    }

    public static enum TYPE {

        HOR(1, HEIGHT_RATIO, 0.5, 0),
        VER(HEIGHT_RATIO, 1, 0, 0.5);

        private final double xRatio;
        private final double yRatio;
        private final double xOffset;
        private final double yOffset;

        TYPE(double xRatio, double yRatio, double xOffset, double yOffset) {
            this.xRatio = xRatio;
            this.yRatio = yRatio;
            this.xOffset = xOffset;
            this.yOffset = yOffset;
        }

        public double getxRatio() {
            return xRatio;
        }

        public double getyRatio() {
            return yRatio;
        }

        public double getxOffset() {
            return xOffset;
        }

        public double getyOffset() {
            return yOffset;
        }
    }
}