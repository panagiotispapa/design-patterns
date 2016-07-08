package com.design.common;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.function.Supplier;

public interface PointTransition extends Supplier<Pair<Double, Polygon.Vertex>> {
    static PointTransition pt(double ratio, Polygon.Vertex vertex) {
        return () -> Pair.of(ratio, vertex);
    }

    default Double getRatio() {
        return get().getLeft();
    }

    default Polygon.Vertex getVertex() {
        return get().getRight();
    }

}

