package com.design.common;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static java.lang.Math.PI;

public class RatioHelper {

    public static Function<Double, Double> $(double factor) {
        return a -> a * factor;
    }

    public static Function<Double, Double> £(double factor) {
        return a -> a / factor;
    }

    private static final Map<Double, Double> heightsMap;
    private static final Map<Double, Double> projectionsMap;

    static {
        heightsMap = new HashMap<>();
        projectionsMap = new HashMap<>();
    }

    private final double phi;

    public RatioHelper(double phi) {
        this.phi = phi / 2.0;
    }

    public Function<Double, Double> $H() {
        return $(retrieveHeight());
    }

    public Function<Double, Double> £H() {
        return £(retrieveHeight());
    }

    public Function<Double, Double> $P() {
        return $(retrieveProjection());
    }

    public Function<Double, Double> £P() {
        return £(retrieveProjection());
    }

    private Double retrieveHeight() {
        return heightsMap.computeIfAbsent(phi, Math::cos);
    }

    private Double retrieveProjection() {
        return projectionsMap.computeIfAbsent(phi, Math::sin);
    }

    public static enum Ratios {
        DOD((2.0 * PI) / 12.0),
        HEX((2.0 * PI) / 6.0),
        RECT((2.0 * PI) / 4.0),
        OCT((2.0 * PI) / 8.0);

        private final RatioHelper helper;

        Ratios(double phi) {
            helper = new RatioHelper(phi);
        }

        public RatioHelper getHelper() {
            return helper;
        }

        public Function<Double, Double> $H() {
            return helper.$H();
        }

        public Function<Double, Double> £H() {
            return helper.£H();
        }

        public Function<Double, Double> $P() {
            return helper.$P();
        }

        public Function<Double, Double> £P() {
            return helper.£P();
        }
    }

    public static class P4 {
        public static final Double H = Ratios.RECT.$H().apply(1.0);
        public static final Double P = Ratios.RECT.$P().apply(1.0);
    }

    public static class P6 {
        public static final Double H = Ratios.HEX.$H().apply(1.0);
        public static final Double P = Ratios.HEX.$P().apply(1.0);
    }

    public static class P8 {
        public static final Double H = Ratios.OCT.$H().apply(1.0);
        public static final Double P = Ratios.OCT.$P().apply(1.0);
    }

    public static class P12 {
        public static final Double H = Ratios.DOD.$H().apply(1.0);
        public static final Double P = Ratios.DOD.$P().apply(1.0);
    }

}
