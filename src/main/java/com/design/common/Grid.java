package com.design.common;

import com.design.common.RatioHelper;
import com.design.common.RatioHelper.P4;
import com.design.common.RatioHelper.P6;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Rect;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Grid {

    public static List<Point2D> gridFromStart(Point2D start, double r, Configuration config, int size) {

        return getGridPoints(start, r, config, size);
    }

    public static List<Point2D> grid(Point2D centre, double r, Configuration config, int size) {

        final double half = r * (size / 2);

        final Point2D start = new Point2D.Double(centre.getX() - half * config.getxRatio(), centre.getY() - half * config.getyRatio());

        return getGridPoints(start, r, config, size);
    }

    private static List<Point2D> getGridPoints(Point2D start, double r, Configuration config, int size) {
        return IntStream.range(0, size)
                .mapToObj(nextHorPoint(start, r, config))
                .flatMap(p -> IntStream.range(0, size)
                        .mapToObj(nextVerPoint(p, r, config)))
                .collect(Collectors.toList());
    }

    public static IntFunction<Point2D> nextHorPoint(Point2D start, double r, Configuration config) {
        return i -> new Point2D.Double(start.getX() + i * config.getxRatio() * r, start.getY() + (i % 2) * config.getyOffset() * r);
    }

    public static IntFunction<Point2D> nextVerPoint(Point2D start, double r, Configuration config) {
        return i -> new Point2D.Double(start.getX() + (i % 2) * config.getxOffset() * r, start.getY() + i * config.getyRatio() * r);
    }

    public Supplier<List<Point2D>> getPoints(Point2D startPoint, double r, Configuration config, int size) {
        return () -> gridFromStart(startPoint, r, config, size);
    }

    public static class Configuration {

        private final double xRatio;
        private final double yRatio;
        private final double xOffset;
        private final double yOffset;

        public static Configuration customRect(double xRatio, double yRatio) {
            return config().xRatio(xRatio).yRatio(yRatio).xOffset(0).yOffset(0);
        }

        public interface xRatioBuilder {
            YRatioBuilder xRatio(double xRatio);
        }

        public interface YRatioBuilder {
            XOffsetBuilder yRatio(double yRatio);
        }

        public interface XOffsetBuilder {
            YOffsetBuilder xOffset(double xOffset);
        }

        public interface YOffsetBuilder {
            Configuration yOffset(double yOffset);
        }

        public static xRatioBuilder config() {
            return xRatio -> yRatio -> xOffset -> yOffset -> new Configuration(xRatio, yRatio, xOffset, yOffset);
        }

        private Configuration(double xRatio, double yRatio, double xOffset, double yOffset) {
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

    public static enum Configs {

        HEX_HOR(1, P6.H, 0.5, 0),
        HEX_HOR2(2.0 * P6.H, 1.5, P6.H, 0),
        HEX_HOR3(2.0, 2.0 * P6.H, 1.0, 0),
        HEX_VER(P6.H, 1, 0, 0.5),
        HEX_VER2(1.5, 2.0 * P6.H, 0, P6.H),
        RECT(1, 1, 0, 0),
        RECT2(2 * P4.H, 2 * P4.H, 0, 0),
        RECT3(1, 2, 0, 1);

        private final Configuration configuration;

        Configs(double xRatio, double yRatio, double xOffset, double yOffset) {
            configuration = Configuration.config().xRatio(xRatio).yRatio(yRatio).xOffset(xOffset).yOffset(yOffset);
        }

        public Configuration getConfiguration() {
            return configuration;
        }
    }
}