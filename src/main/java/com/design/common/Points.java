package com.design.common;

import java.awt.geom.Point2D;
import java.util.function.Function;

public class Points {

    public static Point2D translate(Point2D translation, Point2D point) {
        return new Point2D.Double(point.getX() + translation.getX(), point.getY() + translation.getY());
    }

    public static Function<Point2D, Point2D> scale(double scaleTo) {
        return p -> new Point2D.Double(p.getX() * scaleTo, p.getY() * scaleTo);
    }
}
