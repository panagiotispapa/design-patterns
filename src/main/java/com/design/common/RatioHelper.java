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

    public static final Function<Double, Double> $SQ2 = $(Math.sqrt(2));
    public static final Function<Double, Double> £SQ2 = £(Math.sqrt(2));

    public static final Function<Double, Double> $2 = $(2.0);

    public static final Function<Double, Double> £2 = £(2.0);

    public static final Function<Double, Double> $1 = a -> 1.0 - a;

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
        RECT((2.0 * PI) / 4.0);

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
}
