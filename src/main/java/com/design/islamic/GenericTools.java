package com.design.islamic;

import com.google.common.collect.ImmutableList;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.islamic.model.Centre.newCentre;
import static java.util.stream.Collectors.toList;

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

    public static void scalePoint(Point2D edge, final double r) {
        edge.setLocation(edge.getX() * r, edge.getY() * r);
    }


    public static void translatePoints(List<Point2D> point2DList, Point2D centre) {
        point2DList.forEach(p -> translatePoint(p, centre));
    }

    public static List<List<Point2D>> cloneAndTranslatePointsLists(List<List<Point2D>> point2DLists, final Point2D centre) {

        List<List<Point2D>> out = clonePointsLists(point2DLists);
        translatePointsLists(out, centre);
        return out;

    }

    public static void translatePointsLists(List<List<Point2D>> point2DLists, final Point2D centre) {
        point2DLists.forEach(p -> translatePoints(p, centre));

    }

    public static void translatePoint(Point2D edge, final Point2D centre) {
        edge.setLocation(edge.getX() + centre.getX(), edge.getY() + centre.getY());
    }

    public static List<List<Point2D>> clonePointsLists(List<List<Point2D>> pointsLists) {
        return pointsLists.stream().map(GenericTools::clonePoints).collect(toList());
    }

    public static List<Point2D> clonePoints(List<Point2D> points) {
        return points.stream().map(GenericTools::clonePoint).collect(toList());
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
