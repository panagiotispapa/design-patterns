package com.design.common.model;

import com.design.common.FinalPointTransition;
import com.design.common.InitialConditions;
import com.design.common.view.SvgFactory;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.function.Function;
import java.util.function.Supplier;

public interface Circle extends Supplier<Pair<Point2D, Double>> {
    static Circle circle(Point2D centre, Double r) {
        return () -> Pair.of(centre, r);
    }

    default Point2D getCentre() {
        return get().getLeft();
    }

    default Double getR() {
        return get().getRight();
    }

    default String draw(Style style) {
        return SvgFactory.newCircle(getCentre(), getR(), style);
    }

    static Function<Pair<FinalPointTransition, Double>, Circle> toCircleWithRadius(InitialConditions ic) {
        return p -> Circle.circle(p.getLeft().toPoint(ic), p.getRight() * ic.getR());
    }

}