package com.design.common;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

public interface InitialConditions extends Supplier<Pair<CanvasPoint, Double>> {
    static InitialConditions of(CanvasPoint centre, Double r) {
        return () -> Pair.of(centre, r);
    }

    default CanvasPoint getCentre() {
        return get().getLeft();
    }

    default Double getR() {
        return get().getRight();
    }
}

