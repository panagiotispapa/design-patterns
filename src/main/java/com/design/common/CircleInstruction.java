package com.design.common;

import com.design.common.model.Circle;
import org.apache.commons.lang3.tuple.Pair;

import java.util.function.Supplier;

public interface CircleInstruction extends Supplier<Pair<FinalPointTransition, Double>> {
    static CircleInstruction circleInstruction(FinalPointTransition centre, Double r) {
        return () -> Pair.of(centre, r);
    }

    default FinalPointTransition getCentre() {
        return get().getLeft();
    }

    default Double getR() {
        return get().getRight();
    }

    default Circle toCircle(InitialConditions ic) {
        return Circle.circle(getCentre().toPoint(ic), getR() * ic.getR());
    }
}

