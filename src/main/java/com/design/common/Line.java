package com.design.common;

import com.design.common.model.Path;
import com.design.common.model.Style;
import com.googlecode.totallylazy.Sequence;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.googlecode.totallylazy.Sequences.sequence;
import static java.util.stream.Collectors.toList;

public interface Line extends Supplier<Sequence<FinalPointTransition>> {
    static Line line(FinalPointTransition... transitions) {
        return line(sequence(transitions));
    }

    static Line line(Sequence<FinalPointTransition> transitions) {
        return () -> transitions;
    }

    default Line withOffset(int offset) {
        return line(get().map(t -> t.withOffset(offset)));
    }

    default Line mirror(Function<PointTransition, PointTransition> mapper) {
        return line(get().map(s -> s.mirror(mapper)));
    }

    default List<CanvasPoint> generatePoints(InitialConditions ic) {
        return get().stream().map(p -> p.toPoint(ic)).collect(toList());
    }

    default Path toPath(Style style) {
        return new Path(style, this);
    }

}

