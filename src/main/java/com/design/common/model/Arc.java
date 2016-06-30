package com.design.common.model;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

public interface Arc extends Supplier<Pair<Circle, Boolean>> {

    static Arc of(Circle circle, Boolean up) {
        return () -> Pair.of(circle, up);
    }

    default Circle getCircle() {
        return get().getLeft();
    }

    default Boolean isUp() {
        return get().getRight();
    }

}
