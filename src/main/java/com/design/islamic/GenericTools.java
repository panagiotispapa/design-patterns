package com.design.islamic;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.islamic.model.Centre.newCentre;
import static com.google.common.collect.Iterables.transform;

public class GenericTools {

//    public static List<Point2D> computeFinalPosOfPoints(List<Point2D> point2DList, final Centre centre, final int r) {
//
//        return ImmutableList.copyOf(transform(point2DList, new Function<Point2D, Point2D>() {
//            @Override
//            public Point2D apply(Point2D point2D) {
//                return new Centre(centre.getX() + r * point2D.getX(), centre.getY() + r * point2D.getY());
//            }
//        }));
//
//    }

    public static void scalePoints(List<Point2D> point2DList, final double r) {
        for (Point2D point2D : point2DList) {
            point2D.setLocation(point2D.getX() * r, point2D.getY() * r);
        }
    }

    public static void translatePoints(List<Point2D> point2DList, final Point2D centre) {
        for (Point2D point2D : point2DList) {
            point2D.setLocation(point2D.getX() + centre.getX(), point2D.getY() + centre.getY());
        }
    }

    public static List<Point2D> clonePoints(Iterable<Point2D> points) {
        return Lists.newArrayList(transform(points, new Function<Point2D, Point2D>() {
            @Override
            public Point2D apply(Point2D point2D) {
                return clonePoint(point2D);
            }
        }));
    }

    public static Point2D clonePoint(Point2D in) {
        return newCentre(in.getX(), in.getY());
    }

}
