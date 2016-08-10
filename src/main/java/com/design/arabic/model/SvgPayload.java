package com.design.arabic.model;

import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

public interface SvgPayload extends Supplier<Pair<String, String>> {
    static SvgPayload svgPayload(String payload, String name) {
        return () -> Pair.of(payload, name);
    }

    default String getPayload() {
        return get().getLeft();
    }

    default String getName() {
        return get().getRight();
    }

    default Integer sortByName(SvgPayload other) {
        return this.getName().compareTo(other.getName());
    }

}
