package com.design.common.model;

import com.design.common.FinalPointTransition;
import com.design.common.InitialConditions;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Circle extends Supplier<Pair<Point2D, Double>> {
    static Circle of(Point2D centre, Double r) {
        return () -> Pair.of(centre, r);
    }

    default Point2D getCentre() {
        return get().getLeft();
    }

    default Double getR() {
        return get().getRight();
    }

    static Function<Pair<FinalPointTransition, Double>, Circle> toCircleWithRadius(InitialConditions ic) {
        return p -> Circle.of(p.getLeft().toPoint(ic), p.getRight() * ic.getR());
    }

}