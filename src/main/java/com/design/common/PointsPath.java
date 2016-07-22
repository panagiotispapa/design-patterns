package com.design.common;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
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

    default PointsPath withOffset(int offset) {
        return of(get().stream().map(t -> t.withOffset(offset)).collect(toList()));
    }

    default PointsPath mirror(Function<PointTransition, PointTransition> mapper) {
        return of(get().stream().map(s -> s.mirror(mapper)).collect(toList()));
    }

    default List<Point2D> generatePoints(InitialConditions ic) {
        return get().stream().map(p -> p.toPoint(ic)).collect(toList());
    }

    static Function<FinalPointTransition, Stream<PointsPath>> buildLines(double ratio, Stream<Polygon.Vertex>... vertices) {
        return centre -> Stream.of(
                vertices
        ).map(toPath(centre, ratio));
    }

    static Function<Stream<Polygon.Vertex>, PointsPath> toPath(FinalPointTransition centre, double ratio) {
        return vertexes -> PointsPath.of(
                vertexes.map(v -> PointTransition.pt(ratio, v))
                        .map(centre::append).collect(toList())
        );
    }

}

