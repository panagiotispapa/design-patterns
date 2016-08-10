package com.design.common;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

public interface PointTransition extends Supplier<Pair<Double, Polygon.Vertex>> {
    static PointTransition pt(double ratio, Polygon.Vertex vertex) {
        if(vertex == null) {
            System.out.println("j");
        }

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

    default CanvasPoint forR(Double r) {
        if(getVertex() == null) {
            System.out.println("j");
        }
        return getVertex().getCanvasPoint().scale(r * getRatio());
    }
}

