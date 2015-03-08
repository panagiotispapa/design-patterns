package com.design.common;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.design.common.Mappings.fromListOfLists;

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

    public static Function<List<List<Point2D>>, List<List<Point2D>>> applyGrid(List<Point2D> grid) {
        return p -> grid.stream().map(g -> fromListOfLists(translate(g)).apply(p))
                .map(Collection::stream).flatMap(s -> s).collect(Collectors.toList());
    }
}
