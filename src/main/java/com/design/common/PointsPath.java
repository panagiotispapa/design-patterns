package com.design.common;

import com.design.common.model.Path;
import com.design.common.model.Style;
import com.googlecode.totallylazy.Sequence;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.googlecode.totallylazy.Sequences.sequence;
import static java.util.stream.Collectors.toList;

public interface PointsPath extends Supplier<Sequence<FinalPointTransition>> {
    static PointsPath of(FinalPointTransition... transitions) {
        return of(sequence(transitions));
    }

    static PointsPath of(Sequence<FinalPointTransition> transitions) {
        return () -> transitions;
    }

    default PointsPath withOffset(int offset) {
        return of(get().map(t -> t.withOffset(offset)));
    }

    default PointsPath mirror(Function<PointTransition, PointTransition> mapper) {
        return of(get().map(s -> s.mirror(mapper)));
    }

    default List<Point2D> generatePoints(InitialConditions ic) {
        return get().stream().map(p -> p.toPoint(ic)).collect(toList());
    }

    default Path toPath(Style style) {
        return new Path(style, this);
    }

}

