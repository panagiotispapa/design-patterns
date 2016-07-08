package com.design.islamic;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public class GenericTools {
    public static Function<Point2D, Point2D> scalePoint(final double r) {
        return e -> new Point2D.Double(e.getX() * r, e.getY() * r);
    }

    public static Function<Point2D, Point2D> translatePoint(final Point2D centre) {
        return e -> new Point2D.Double(e.getX() + centre.getX(), e.getY() + centre.getY());
    }

    public static List<Point2D> clonePoints(List<Point2D> points) {
        return points.stream().map(GenericTools::clonePoint).collect(toList());
    }

    public static Point2D clonePoint(Point2D in) {
        return new Point2D.Double(in.getX(), in.getY());
    }

    public static <T> Supplier<T> s(Supplier<T> supplier) {
        return supplier;
    }

    public static <A, B> Function<List<A>, List<B>> mapLists(Function<A, B> mapper) {
        return in -> in.stream().map(mapper).collect(toList());
    }

    public static <A, B> Function<List<A>, List<B>> flatMapLists(Function<A, List<B>> mapper) {
        return in -> in.stream().map(mapper).map(List::stream).flatMap(s -> s).collect(toList());
    }

}
