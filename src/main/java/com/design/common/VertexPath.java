package com.design.common;

import com.googlecode.totallylazy.Sequence;

import java.util.function.Supplier;

import static com.design.common.PointTransition.pt;
import static com.googlecode.totallylazy.Sequences.sequence;

public interface VertexPath extends Supplier<Sequence<Polygon.Vertex>> {

    static VertexPath vp(Polygon.Vertex... vertices) {
        return vp(sequence(vertices));
    }

    static VertexPath vp(Sequence<Polygon.Vertex> vertices) {
        return () -> vertices;
    }

    default PointsPath toPointsPath(FinalPointTransition centre, double ratio) {
        return PointsPath.of(
                get().map(v -> pt(ratio, v))
                        .map(centre::append)

        );
    }
}
