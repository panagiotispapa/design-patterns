package com.design.common;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public interface FinalPointTransition extends Supplier<List<PointTransition>> {
    static FinalPointTransition fpt(PointTransition... transitions) {
        return () -> Stream.of(transitions).collect(toList());
    }

    static FinalPointTransition fpt(Stream<PointTransition> transitions) {
        return () -> transitions.collect(toList());
    }

    static FinalPointTransition fpt(List<PointTransition> transitions) {
        return () -> transitions;
    }

    FinalPointTransition K = fpt();

    default FinalPointTransition withOffset(int offset) {
        return fpt(get().stream().map(t -> t.withOffset(offset)).collect(toList()));
    }

    default FinalPointTransition append(PointTransition... transitions) {
        return fpt(
                Stream.concat(
                        get().stream(),
                        Stream.of(transitions)
                ).collect(toList())
        );
    }

    default Point2D toPoint(InitialConditions ic) {
        return get().stream().map(t -> t.forR(ic.getR())).reduce(ic.getCentre(), Points::translate);
    }

    default FinalPointTransition mirror(Function<PointTransition, PointTransition> mapper) {
        return fpt(get().stream().map(mapper).collect(toList()));
    }

}
