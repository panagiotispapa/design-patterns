package com.design.common;

import org.apache.commons.lang3.tuple.Triple;

import java.util.function.Supplier;

public interface PointTransition extends Supplier<Triple<Double, Polygon.Vertex, Polygon.Type>> {
    static PointTransition of(double ratio, Polygon.Vertex vertex, Polygon.Type type) {
        return () -> Triple.of(ratio, vertex, type);
    }

    default Double getRatio() {
        return get().getLeft();
    }

    default Polygon.Vertex getVertex() {
        return get().getMiddle();
    }

    default Polygon.Type getType() {
        return get().getRight();
    }
}

