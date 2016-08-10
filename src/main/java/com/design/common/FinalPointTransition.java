package com.design.common;

import com.googlecode.totallylazy.Sequence;

import java.util.function.Function;
import java.util.function.Supplier;

import static com.design.common.PointTransition.pt;
import static com.design.common.StreamUtils.m;
import static com.googlecode.totallylazy.Sequences.sequence;

public interface FinalPointTransition extends Supplier<Sequence<PointTransition>> {

    static FinalPointTransition fpt(double ratio1, Polygon.Vertex vertex1,double ratio2, Polygon.Vertex vertex2) {
        return fpt(pt(ratio1, vertex1), pt(ratio2, vertex2));
    }

    static FinalPointTransition fpt(double ratio, Polygon.Vertex vertex) {
        return fpt(pt(ratio, vertex));
    }

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

    default FinalPointTransition to(double ratio, Polygon.Vertex vertex, double ratio2, Polygon.Vertex vertex2) {
        return to(pt(ratio, vertex), pt(ratio2, vertex2));
    }
    default FinalPointTransition to(double ratio, Polygon.Vertex vertex) {
        return to(pt(ratio, vertex));
    }

    default FinalPointTransition to(PointTransition... transitions) {
        return fpt(
                get().join(sequence(transitions))
        );
    }

    default CanvasPoint toPoint(InitialConditions ic) {
        return get().stream().map(t -> t.forR(ic.getR())).reduce(ic.getCentre(), CanvasPoint::translate);
    }

    default FinalPointTransition mirror(Function<PointTransition, PointTransition> mapper) {
        return fpt(get().map(m(mapper)));
    }

    default Sequence<Line> toLine(double ratio, VertexPath... vertices) {
        return sequence(
                vertices
        ).map(vp -> vp.toPointsPath(this, ratio));
    }
}
