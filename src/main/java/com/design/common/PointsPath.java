package com.design.common;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public interface PointsPath extends Supplier<List<FinalPointTransition>> {
    static PointsPath of(FinalPointTransition... transitions) {
        return () -> Stream.of(transitions).collect(toList());
    }

    static PointsPath of(List<FinalPointTransition> transitions) {
        return () -> transitions;
    }

    static PointsPath of(Stream<FinalPointTransition> transitions) {
        return () -> transitions.collect(toList());
    }

    default PointsPath mirror(Function<PointTransition, PointTransition> mapper) {
        return of(get().stream().map(s -> s.mirror(mapper)).collect(toList()));
    }
}

