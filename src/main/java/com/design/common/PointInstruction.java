package com.design.common;

import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.function.Supplier;

public interface PointInstruction extends Supplier<Triple<Point2D, Double, Integer>> {

    static PointInstruction of(Point2D centre, Double r, Integer radIndex) {
        return () -> Triple.of(centre, r, radIndex);
    }

    default Point2D getCentre() {
        return get().getLeft();
    }

    default Double getR() {
        return get().getMiddle();
    }

    default Integer getRadIndex() {
        return get().getRight();
    }
}
