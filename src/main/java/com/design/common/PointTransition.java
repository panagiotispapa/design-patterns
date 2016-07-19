package com.design.common;

import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.function.Supplier;

public interface PointTransition extends Supplier<Pair<Double, Polygon.Vertex>> {
    static PointTransition pt(double ratio, Polygon.Vertex vertex) {
        return () -> Pair.of(ratio, vertex);
    }

    default PointTransition withOffset(int offset) {
        return pt(getRatio(), getVertex().withOffset(offset));
    }

    default Double getRatio() {
        return get().getLeft();
    }

    default Polygon.Vertex getVertex() {
        return get().getRight();
    }

    default Point2D forR(Double r) {
        return Points.scale(r * getRatio()).apply(getVertex().getPoint());
    }
}

