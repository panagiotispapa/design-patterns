package com.design.islamic.model;

import com.design.islamic.model.tiles.shapes.*;
import com.design.islamic.model.tiles.shapes.Shape;
import com.design.islamic.model.tiles.svg.Style;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import java.awt.*;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.Writer;
import java.util.List;
import java.util.Set;

import static com.design.islamic.model.tiles.shapes.Shapes.HEX_PHI;
import static com.design.islamic.model.tiles.shapes.Shapes.newHexagons;
import static com.google.common.collect.Iterables.transform;
import static java.awt.geom.Path2D.WIND_EVEN_ODD;
import static java.lang.Math.cos;

public class PolygonTools {


    public static List<Point2D> computeFinalPosOfPoints(List<Point2D> point2DList, final Centre centre, final int r) {

        return ImmutableList.copyOf(transform(point2DList, new Function<Point2D, Point2D>() {
            @Override
            public Point2D apply(Point2D point2D) {
                return new Centre(centre.getX() + r * point2D.getX(), centre.getY() + r * point2D.getY());
            }
        }));

    }


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


    public static void drawPolygon(Writer writer, Hexagon hexagon, Style style) {
        StringBuilder builder = new StringBuilder();

        builder.append("<polygon points=\"");

        for (Point2D edge : hexagon.getEdges()) {
            builder.append(String.format("%s,%s ", edge.getX(), edge.getY()));
        }

        builder.append("\" style=\"" + style.toString()  +" \" />");

        try {
            writer.write(builder.toString() + "\n" );
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println(builder.toString());


    }
    public static void drawPolygon(Graphics2D g2, Hexagon hexagon) {

        GeneralPath polygon =
                new GeneralPath(WIND_EVEN_ODD,
                        hexagon.getEdges().size());

        polygon.moveTo(hexagon.getEdges().get(0).getX(), hexagon.getEdges().get(0).getY());

        for (int i = 1; i < hexagon.getEdges().size(); i++) {
            polygon.lineTo(
                    hexagon.getEdges().get(i).getX(),
                    hexagon.getEdges().get(i).getY());

        }

        polygon.closePath();
        g2.draw(polygon);
    }

    public static List<? extends com.design.islamic.model.tiles.shapes.Shape> buildHexPattern0(Iterable<? extends Point2D> centres, double r, Style style) {
        return newHexagons(centres, r, style);
    }

    public static List<? extends com.design.islamic.model.tiles.shapes.Shape> drawHexPattern1(Iterable<Point2D> centres, double r, Style style) {
        return Shapes.newCircles(edgesFromCentres(centres, r), r, style);

    }


//    public static void drawHexPattern0(Writer writer, Iterable<Point2D> centres, double r, Style style) {
//        for (Hexagon hexagon : newHexagons(centres, r)) {
//            drawPolygon(writer, hexagon, style);
//        }

//    }
//    public static void drawHexPattern0(Graphics2D g2, Iterable<Point2D> centres, double r) {
//        for (Hexagon hexagon : newHexagons(centres, r)) {
//            drawPolygon(g2, hexagon);
//        }
//    }

//    public static void drawHexPattern1(Graphics2D g2, Iterable<Point2D> centres, double r) {
//        for (Circle circle : Shapes.newCircles(edgesFromCentres(centres, r), r)) {
//            drawCircle(g2, circle);
//        }
//    }

//    public static void drawHexPattern1(Writer writer, Iterable<Point2D> centres, double r, Style style) {
//        for (Circle circle : Shapes.newCircles(edgesFromCentres(centres, r), r)) {
//            drawCircle(writer, circle, style);
//        }
//    }


    public static Set<Point2D> edgesFromCentres(Iterable<Point2D> centres, double r) {

        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(calculateHexEdges(centre, r));
        }

        builder.addAll(centres);

        return builder.build();

    }

//
//    public static void drawHexPattern1(Graphics2D g2, Point2D centre, int r) {
//
//        List<Point2D> points = clonePoints(Shapes.hexPoints);
//        scalePoints(points, r);
//        translatePoints(points, centre);
//
//
//        drawCircle(g2, centre, r);
//
//        for (Point2D point : points) {
//            drawCircle(g2, point, r);
//        }
//
//    }
//

    public static void drawPoints(Graphics2D g2, Iterable<Point2D> points) {

        for (Point2D point : points) {
            fillCircle(g2, point, 3);
        }

    }

    public static void fillCircle(Graphics2D g2, Point2D centre, int r) {
        g2.fillArc((int) centre.getX() - r, (int) centre.getY() - r, 2 * r, 2 * r, 0, 360);
    }

    public static void drawCircle(Writer writer, Circle circle, Style style) {

        String circleInSvg = String.format("<circle cx=\"%f\" cy=\"%f\" r=\"%f\" style=\"%s\" />",
                circle.getCentre().getX(), circle.getCentre().getY(), circle.getR(), style.toString());
        try {
            writer.write(circleInSvg + "\n");
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
//        System.out.println(circleInSvg);
    }



    public static void drawCircle(Graphics2D g2, Circle circle) {
        g2.drawArc(
                (int) (circle.getCentre().getX() - circle.getR()),
                (int) (circle.getCentre().getY() - circle.getR()),
                (int) (2 * circle.getR()),
                (int) (2 * circle.getR()),
                0,
                360);
    }

    public static void drawCircle(Graphics2D g2, Point2D centre, int r) {
        g2.drawArc((int) centre.getX() - r, (int) centre.getY() - r, 2 * r, 2 * r, 0, 360);
    }

    public static List<Point2D> calculateHexEdges(Point2D centre, double r) {
        List<Point2D> edges = PolygonTools.clonePoints(Shapes.hexPoints);
        PolygonTools.scalePoints(edges, r);
        PolygonTools.translatePoints(edges, centre);

        return edges;

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
        return new Centre(in.getX(), in.getY());
    }

    public static List<Point2D> calculateNewCellCentres(Point2D centre, double r) {

        List<Point2D> newPoints = clonePoints(Shapes.hexPointsAlt);

        scalePoints(newPoints, 2 * r * cos(HEX_PHI / 2.0));
        translatePoints(newPoints, centre);
        newPoints.add(centre);


        return newPoints;
    }


    public static Set<Point2D> calculateNewCellCentres(Iterable<Point2D> centres, double r, int level) {

        if (level == 1) {
            return Sets.newHashSet(centres);
        } else {
            return calculateNewCellCentres(calculateNewCellCentres(centres, r), r, --level);
        }

    }

    private static Set<Point2D> calculateNewCellCentres(Iterable<Point2D> centres, double r) {


        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(calculateNewCellCentres(centre, r));
        }


        return builder.build();

    }

}
