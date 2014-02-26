package com.design.islamic;

import com.design.islamic.model.Payload;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.Centre.newCentre;
import static java.util.Arrays.asList;

public final class Patterns {



    private Patterns() {

    }


    private static Set<Point2D> edgesFromCentres(Iterable<Point2D> centres, double r) {

        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        builder.addAll(calculateHexEdges(centres, r));
        builder.addAll(centres);

        return builder.build();

    }

    public static Set<Point2D> calculateHexEdges(Iterable<Point2D> centres, double r) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(cloneAndTranslateScalePoints(centre, r, hexPoints));
        }

        return builder.build();
    }

    public static Set<Point2D> calculateNewCellCentresFirstConf(Point2D startPoint, double r, int level) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        final double dist1 = 2 * r;

        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                final double dist = j % 2 == 0 ? dist1 * i : dist1 * i - r;
                builder.add(newCentre(startPoint.getX() + dist, startPoint.getY() + j * r * HEX_DIST_NEW_CENTRE));

            }
        }

        return builder.build();
    }

    public static Set<Point2D> calculateNewCellCentresSecondConf(Point2D startPoint, double r, int level) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        final double dist1 = r + r * 0.5;
        final double dist2 = r * HEX_DIST_NEW_CENTRE;


        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
//                final double dist = i % 2 == 0 ? j * dist2 : j * dist2 - r * HEX_DIST_HEIGHT;
                final double dist_ = j % 2 == 0 ? i * dist2 : i * dist2 - r * HEX_DIST_HEIGHT;
//                builder.add(newCentre(startPoint.getX() + i * dist1, startPoint.getY() + dist));
                builder.add(newCentre(startPoint.getX() + dist_, startPoint.getY() + j*dist1));

            }
        }

//        builder.add(newCentre(startPoint.getX() + 2 * dist2, startPoint.getY() ));
        return builder.build();
    }

    public static Set<Point2D> calculateNewCellCentresThirdConf(Point2D startPoint, double r, int level) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        final double dist1 = r + r * 0.5;
        final double dist2 = r * HEX_DIST_NEW_CENTRE;


        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                final double dist = i % 2 == 0 ? j * dist2 : j * dist2 - r * HEX_DIST_HEIGHT;
                builder.add(newCentre(startPoint.getX() + i * dist1, startPoint.getY() + dist));
            }
        }

        return builder.build();
    }
//
//    public static Set<Point2D> calculateNewRectCentresConf(Point2D startPoint, double r, int level) {
//        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();
//
//        final double dist1 = 2*r*RECT_DIST_HEIGHT;
//
//        for (int i = 0; i < level; i++) {
//            for (int j = 0; j < level; j++) {
//                builder.add(newCentre(startPoint.getX() + i*dist1, startPoint.getY() + j*dist1));
//
//            }
//        }
//
////        builder.add(newCentre(startPoint.getX() + 2 * dist1, startPoint.getY() ));
//        return builder.build();
//    }

    public static Set<Point2D> calculateNewRectCentresConf(Point2D startPoint, double r, int level, double ratio) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        final double dist1 = 2*r*RECT_DIST_HEIGHT;

        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                builder.add(newCentre(startPoint.getX() + i*ratio*dist1, startPoint.getY() + j*dist1));

            }
        }

//        builder.add(newCentre(startPoint.getX() + 2 * dist1, startPoint.getY() ));
        return builder.build();
    }

    public static Set<Point2D> calculateNewCellCentres(Point2D centre, double r, int level) {
        return calculateNewCellCentres(calculateNewCellCentres(centre, r), r, level);
    }

    private static Set<Point2D> calculateNewCellCentres(Iterable<Point2D> centres, double r, int level) {

        if (level == 1) {
            return Sets.newHashSet(centres);
        } else {
            return calculateNewCellCentres(calculateNewCellCentres(centres, r), r, --level);
        }

    }

    private static List<Point2D> calculateNewCellCentres(Point2D centre, double r) {
        return cloneAndTranslateScalePoints(centre, r * HEX_DIST_NEW_CENTRE, hexPointsAlt);

    }

    private static Set<Point2D> calculateNewCellCentres(Iterable<Point2D> centres, double r) {

        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(calculateNewCellCentres(centre, r));
        }

        return builder.build();

    }

    public static List<Payload> buildHexPatterns(Set<Point2D> centres, Payload payload) {


        List<Payload> payloads = new ArrayList<Payload>(centres.size());

        for (Point2D centre : centres) {
            payloads.add(payload.translate(centre));
        }

        return payloads;

    }



    public static String buildHexPatternBlackAndWhite(List<Payload> payloads, Dimension dim) {

        final String styleBlack = newStyle(BLACK, BLACK, 1, 1, 1);
        List<Point2D> backGroundRect = asList(
                newCentre(0, 0),
                newCentre(dim.getWidth(), 0),
                newCentre(dim.getWidth(), dim.getHeight()),
                newCentre(0, dim.getHeight()));

        StringBuilder shapes = new StringBuilder();
        shapes.append(drawPolygon(backGroundRect, styleBlack));



        for (Payload payload1 : payloads) {
            shapes.append(drawPayload(payload1));
//            total.addAll(drawPayload(payload1));
        }

        String svg = buildSvg(dim,
                shapes.toString());




        System.out.println("finished building svg!!!");
        return svg;

    }


//    public static XMLBuilder buildHexPatternBlackAndWhite(CentreConfiguration centreConfiguration, Dimension dim, Function<Point2D, Tile> transformation) {
//
//        final String styleBlack = newStyle(BLACK, BLACK, 1, 1, 1);
//
//        List<Tile> tiles = newArrayList(transform(centreConfiguration.getCentresSecondConf(), transformation));
//
//        List<Point2D> backGroundRect = asList(
//                newCentre(0, 0),
//                newCentre(dim.getWidth(), 0),
//                newCentre(dim.getWidth(), dim.getHeight()),
//                newCentre(0, dim.getHeight()));
//
//        List<XMLBuilder> total = newArrayList();
//        total.add(drawPolygon(backGroundRect, styleBlack));
//        for (Tile tile : tiles) {
//            total.addAll(tile.drawMe());
//        }
//
//
////        total.add(drawPolygon(forGroundRect, styleWhite));
//
//        XMLBuilder svg = buildSvg(dim,
//                total);
//
//        System.out.println("finished building svg!!!");
//        return svg;
//
//    }







}
