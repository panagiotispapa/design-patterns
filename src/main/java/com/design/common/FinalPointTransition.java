package com.design.common;

import com.googlecode.totallylazy.Sequence;

import java.awt.geom.Point2D;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.design.common.StreamUtils.m;
import static com.googlecode.totallylazy.Sequences.sequence;

public interface FinalPointTransition extends Supplier<Sequence<PointTransition>> {
    static FinalPointTransition fpt(PointTransition... transitions) {
        return fpt(sequence(transitions));
    }

    static FinalPointTransition fpt(Sequence<PointTransition> transitions) {
        return () -> transitions;
    }

    FinalPointTransition K = fpt();

    default FinalPointTransition withOffset(int offset) {
        return fpt(get().map(t -> t.withOffset(offset)));
    }

    default FinalPointTransition append(PointTransition... transitions) {
        return fpt(
                get().join(sequence(transitions))
        );
    }

    default Point2D toPoint(InitialConditions ic) {
        return get().stream().map(t -> t.forR(ic.getR())).reduce(ic.getCentre(), Points::translate);
    }

    default FinalPointTransition mirror(Function<PointTransition, PointTransition> mapper) {
        return fpt(get().map(m(mapper)));
    }

    default Sequence<PointsPath> toLine(double ratio, VertexPath... vertices) {
        return sequence(
                vertices
        ).map(vp -> vp.toPointsPath(this, ratio));
    }
}
