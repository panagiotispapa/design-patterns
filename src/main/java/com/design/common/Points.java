package com.design.common;

import java.awt.geom.Point2D;
import java.util.function.Function;

public class Points {

    public static Function<Point2D, Point2D> translateAndScale(Point2D translation, double scaleTo) {
        return scale(scaleTo).andThen(translate(translation));
    }

    public static Function<Point2D, Point2D> translate(Point2D translation) {
        return p -> new Point2D.Double(p.getX() + translation.getX(), p.getY() + translation.getY());
    }

    public static Function<Point2D, Point2D> scale(double scaleTo) {
        return p -> new Point2D.Double(p.getX() * scaleTo, p.getY() * scaleTo);
    }
}
