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

    default FinalPointTransition append(PointTransition... transitions) {
        return fpt(
                Stream.concat(
                        get().stream(),
                        Stream.of(transitions)
                ).collect(toList())
        );
    }

    default Point2D toPoint(InitialConditions ic) {
        return toPoint(0, ic);
    }

    default Point2D toPoint(int offset, InitialConditions ic) {
        return PointTransform.of(get()).get()
                .apply(PointInstruction.of(ic.getCentre(), ic.getR(), offset)).getCentre();
    }

    default FinalPointTransition mirror(Function<PointTransition, PointTransition> mapper) {
        return fpt(get().stream().map(mapper).collect(toList()));
    }

}
