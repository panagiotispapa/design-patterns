package com.design.islamic;

import com.google.common.collect.ImmutableList;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static com.design.islamic.model.Centre.newCentre;

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
        for (Point2D edge : point2DList) {
            scalePoint(edge, r);
        }
    }

    public static void scalePoint(Point2D edge, final double r) {
        edge.setLocation(edge.getX() * r, edge.getY() * r);
    }

    public static List<Point2D> cloneAndTranslatePoints(List<Point2D> point2DList, final Point2D centre) {
        List<Point2D> out = clonePoints(point2DList);
        translatePoints(out, centre);
        return out;
    }

    public static void translatePoints(List<Point2D> point2DList, final Point2D centre) {
        for (Point2D edge : point2DList) {
            translatePoint(edge, centre);
        }
    }

    public static List<List<Point2D>> cloneAndTranslatePointsLists(List<List<Point2D>> point2DLists, final Point2D centre) {
        List<List<Point2D>> out = clonePointsLists(point2DLists);

        translatePointsLists(out, centre);
        return out;

    }

    public static void translatePointsLists(List<List<Point2D>> point2DLists, final Point2D centre) {

        for (List<Point2D> point2DList : point2DLists) {
            translatePoints(point2DList, centre);
        }

    }

    public static void translatePoint(Point2D edge, final Point2D centre) {
        edge.setLocation(edge.getX() + centre.getX(), edge.getY() + centre.getY());
    }

    public static List<List<Point2D>> clonePointsLists(List<List<Point2D>> pointsLists) {
        List<List<Point2D>> out = new ArrayList<List<Point2D>>(pointsLists.size());

        for (List<Point2D> pointsList : pointsLists) {
            out.add(clonePoints(pointsList));
        }

        return out;
    }

    public static List<Point2D> clonePoints(List<Point2D> points) {
        List<Point2D> out = new ArrayList<Point2D>(points.size());
        for (Point2D point : points) {
            out.add(clonePoint(point));
        }
        return out;
    }

    public static Point2D clonePoint(Point2D in) {
        return newCentre(in.getX(), in.getY());
    }

    public static List<Point2D> concatEdges(List<Point2D> edges1, List<Point2D> edges2) {
        ImmutableList.Builder<Point2D> builder = ImmutableList.builder();

        for (int i = 0; i < edges1.size(); i++) {
            builder.add(edges1.get(i));
            builder.add(edges2.get(i));
        }

        return builder.build();
    }

}
