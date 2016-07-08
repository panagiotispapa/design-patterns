package com.design.common;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

public interface PointTransform extends Supplier<Function<PointInstruction, PointInstruction>> {
    static PointTransform of(Function<PointInstruction, PointInstruction> centreTransform) {
        return () -> centreTransform;
    }

    static PointTransform of(Function<PointInstruction, PointInstruction>... centreTransforms) {
        return () -> Stream.of(centreTransforms).reduce(Function.identity(), Function::andThen);
    }

    static PointTransform of(Supplier<Stream<PointTransform>> centreTransforms) {
        return () -> centreTransforms.get().map(Supplier::get).reduce(Function.identity(), Function::andThen);
    }

    static PointTransform of(PointTransform... pointTransforms) {
        return of(() -> Stream.of(pointTransforms));
    }

    static PointTransform of(PointTransition... pointTransition) {
        return of(() -> Stream.of(pointTransition).map(PointTransform::of));
    }

    static PointTransform of(List<PointTransition> pointTransition) {
        return of(() -> pointTransition.stream().map(PointTransform::of));
    }

    static PointTransform of(PointTransition pointTransition) {
        return PointTransform.of(p -> PointInstruction.of(Points.translateAndScale(p.getCentre(), p.getR() * pointTransition.getRatio()).apply(pointTransition.getVertex().getPoint(p.getRadIndex())), p.getR(), p.getRadIndex()));
    }

}

