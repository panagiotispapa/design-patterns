package com.design.common;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.function.Supplier;

public interface InitialConditions extends Supplier<Pair<Point2D, Double>> {
    static InitialConditions of(Point2D centre, Double r) {
        return () -> Pair.of(centre, r);
    }

    default Point2D getCentre() {
        return get().getLeft();
    }

    default Double getR() {
        return get().getRight();
    }
}

