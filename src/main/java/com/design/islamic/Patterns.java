package com.design.islamic;

import com.design.common.view.SvgFactory;
import com.design.islamic.model.hex.*;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jamesmurty.utils.XMLBuilder;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.GenericTools.*;
import static com.design.islamic.model.Centre.newCentre;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.*;
import static java.util.Arrays.asList;
import static org.paukov.combinatorics.Factory.createSimpleCombinationGenerator;
import static org.paukov.combinatorics.Factory.createVector;

public final class Patterns {



    private Patterns() {

    }
                                         /*
    public static List<Point2D> computePoints(int n, double phi, double offset) {

        ImmutableList.Builder<Point2D> posBuilder = ImmutableList.builder();
        for (int k = 0; k < n; k++) {
            posBuilder.add(newCentre(
                    cos(phi * k + offset),
                    sin(phi * k + offset)
            ));
        }
        return posBuilder.build();
    }
                                           */


    public static Set<Point2D> edgesFromCentres(Iterable<Point2D> centres, double r) {

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

//    public static List<Point2D> calculateHexEdges(Point2D centre, double r) {
//        List<Point2D> edges = clonePoints(Patterns.hexPoints);
//        scalePoints(edges, r);
//        translatePoints(edges, centre);
//
//        return edges;
//
//    }

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
                final double dist = i % 2 == 0 ? j * dist2 : j * dist2 - r * HEX_DIST_HEIGHT;
                builder.add(newCentre(startPoint.getX() + i * dist1, startPoint.getY() + dist));

            }
        }

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

    public static XMLBuilder buildHexPattern1(Iterable<Point2D> centresFirstConf, Iterable<Point2D> centresSecondConf, final double r, int width, int height) {

        final String styleColoured = newStyle("yellow", "green", 2, 1, 1);

//        List<Point2D> backGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));

        List<Tile1> hexagons = Tile1.fromCentres(centresSecondConf, r);

        List<XMLBuilder> tiles = newArrayList(transform(hexagons, new Function<Tile1, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Tile1 tile) {
                return tile.drawMe(styleColoured);
            }
        }));

        XMLBuilder svg = buildSvg(width, height,
                tiles
        );

        System.out.println("finished building svg!!!");
        return svg;
    }

    public static XMLBuilder buildHexPattern2(Iterable<Point2D> centresFirstConf, Iterable<Point2D> centresSecondConf, final double r, int width, int height) {

        final String style = newStyle("yellow", "green", 1, 1, 0);

        List<XMLBuilder> hexagons = ImmutableList.copyOf(transform(centresSecondConf, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return drawPolygon(cloneAndTranslateScalePoints(centre, r, hexPoints), style);
            }
        }));

        final String style2 = newStyle("blue", "green", 1, 0, 1);

        List<XMLBuilder> circles = ImmutableList.copyOf(
                transform(
                        edgesFromCentres(centresSecondConf, r),
                        new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D edge) {
                return SvgFactory.newCircle(edge, r, style2);
            }
        }));

        XMLBuilder svg = buildSvg(width, height,
                concat(
                        hexagons
                        , circles
                )
        );

        System.out.println("finished building svg!!!");
        return svg;
    }

    public static XMLBuilder buildHexPatternStar(Iterable<Point2D> centresFirstConf, Iterable<Point2D> centresSecondConf, final double r, int width, int height, final double dist) {

        final String style = newStyle("yellow", "yellow", 1, 1, 1);

        List<TileStar> stars = newArrayList(transform(centresFirstConf, new Function<Point2D, TileStar>() {
            @Override
            public TileStar apply(Point2D centre) {
                return new TileStar(centre, r, dist);
            }
        }));

        final String style2 = newStyle("blue", "blue", 1, 1, 1);

        List<Point2D> backGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));

        List<XMLBuilder> allShapes = newArrayList();

        allShapes.add(drawPolygon(backGroundRect, style2));
        allShapes.addAll(newArrayList(transform(stars, new Function<TileStar, XMLBuilder>() {
            @Override
            public XMLBuilder apply(TileStar tileStar) {
                return tileStar.drawMe(style);
            }
        })));

        XMLBuilder svg = buildSvg(width, height, allShapes);

        System.out.println("finished building svg!!!");
        return svg;
    }

    public static XMLBuilder buildHexPattern3(Iterable<Point2D> centresFirstConf, Iterable<Point2D> centresSecondConf, final double r, int width, int height) {

        double opacity = 1;

        final String styleBlack = newStyle(BLACK, BLACK, 1, opacity, opacity);
        final String styleGreen = newStyle(GREEN, GREEN, 1, opacity, opacity);
        final String styleBlue = newStyle(BLUE, BLUE, 1, opacity, opacity);
        final String styleOrange = newStyle(ORANGE, ORANGE, 1, opacity, opacity);
        final String styleWhite = newStyle(WHITE, WHITE, 1, 0.4, 0.4);

        List<Tile3> tiles = newArrayList(transform(centresSecondConf, new Function<Point2D, Tile3>() {
            @Override
            public Tile3 apply(Point2D centre) {
                return new Tile3(centre, r);
            }
        }));

        List<Point2D> backGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));
//        List<Point2D> forGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));

        List<XMLBuilder> total = newArrayList();
        total.add(drawPolygon(backGroundRect, styleBlack));
        for (Tile3 tile : tiles) {
            total.addAll(tile.drawMe());
        }


//        total.add(drawPolygon(forGroundRect, styleWhite));

        XMLBuilder svg = buildSvg(width, height,
                total);

        System.out.println("finished building svg!!!");
        return svg;

    }


    public static XMLBuilder buildHexPattern4(Iterable<Point2D> centresFirstConf, Iterable<Point2D> centresSecondConf, final double r, int width, int height) {

        double opacity = 1;

        final String styleBlack = newStyle(BLACK, BLACK, 1, opacity, opacity);
        final String styleWhite = newStyle(WHITE, 2, opacity);
        final String styleWhite2 = newStyle(WHITE, 1, opacity);


        List<Tile4> tiles = Tile4.fromCentres(centresSecondConf, r);

        List<Point2D> backGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));

        List<XMLBuilder> total = newArrayList();
        total.add(drawPolygon(backGroundRect, styleBlack));
        for (Tile4 tile : tiles) {
            total.addAll(tile.drawMe(styleWhite, styleWhite2));
        }


//        total.add(drawPolygon(forGroundRect, styleWhite));

        XMLBuilder svg = buildSvg(width, height,
                total);

        System.out.println("finished building svg!!!");
        return svg;

    }

//    public static XMLBuilder buildHexPatternStarRotated(Iterable<Point2D> centresFirstConf, Iterable<Point2D> centresSecondConf, final double r, int width, int height, final double dist) {
//
//        final String style = newStyle("yellow", "yellow", 1, 1, 1);
//
//        List<XMLBuilder> stars = ImmutableList.copyOf(transform(centresSecondConf, new Function<Point2D, XMLBuilder>() {
//            @Override
//            public XMLBuilder apply(Point2D centre) {
//                return newHexStarTileRotated(centre, r, style, dist);
//            }
//        }));
//
//        final String style2 = newStyle("blue", "blue", 1, 1, 1);
//
//        XMLBuilder svg = buildSvg(width, height,
//                concat(
//                        asList(
//                                drawPolygon(
//                                        asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height)),
//                                        style2)),
//                        stars
//                )
//        );
//
//        System.out.println("finished building svg!!!");
//        return svg;
//    }

    public static List<Point2D> newHexStarTile(Point2D centre, double r, double dist) {

        return concatEdges(
                cloneAndTranslateScalePoints(centre, r, hexPoints),
                cloneAndTranslateScalePoints(centre, r * dist, hexPointsAlt)
        );

    }

    public static List<Point2D> cloneAndTranslateScalePoints(final Point2D centre, final double r, Iterable<Point2D> points) {

        List<Point2D> edges = clonePoints(points);
        scalePoints(edges, r);
        translatePoints(edges, centre);

        return edges;

    }

    public static List<List<Point2D>> buildLines(final Point2D centre, List<Point2D> points) {

        List<List<Point2D>> lines = newArrayList();
        for (Point2D point2D : points) {
            lines.add(asList(centre, point2D));
        }

        return lines;
    }

    public static List<Point2D> newHexStarTileRotated(Point2D centre, double r, double dist) {

        final double newR = r * cos(HEX_PHI / 2);

//        List<Point2D> edges = Patterns.cloneAndTranslateScalePoints(centre, r, hexPoints);

        //        List<Point2D> edgesLayer5 = concatEdges(edgesAlt2, edgesAlt);

        return concatEdges(
                cloneAndTranslateScalePoints(centre, newR * dist, hexPoints),
                cloneAndTranslateScalePoints(centre, r * HEX_DIST_HEIGHT, hexPointsAlt)
        );

    }

    private static List<Point2D> buildStarRotatedEdges(final Point2D centre, final double r, double dist) {

        final double newR = r * HEX_DIST_HEIGHT;

        List<Point2D> layer1 = clonePoints(hexPointsAlt);

        scalePoints(layer1, r * HEX_DIST_HEIGHT);
        translatePoints(layer1, centre);

        List<Point2D> layer2 = clonePoints(hexPoints);

        scalePoints(layer2, newR * dist);
        translatePoints(layer2, centre);

        return concatEdges(layer2, layer1);

    }

    private static List<List<Point2D>> generateExtPolygonsForStar(List<Point2D> innerEdges, List<Point2D> outerEdges, List<Point2D> outerEdges2) {
        List<List<Point2D>> out = Lists.newArrayList();

        int size = outerEdges2.size();
        for (int i = 0; i < innerEdges.size(); i++) {
            out.add(asList(outerEdges.get(i), innerEdges.get(i), innerEdges.get(i < size - 1 ? i + 1 : 0), outerEdges2.get(i)));

        }

        return out;
    }

    public static List<List<Point2D>> generateCombsOfPoints(List<Point2D> edges) {

        List<List<Point2D>> combinations = newArrayList();

        Generator<Point2D> edgesLines = createSimpleCombinationGenerator(createVector(
                edges), 2);

        for (ICombinatoricsVector<Point2D> edgesLine : edgesLines) {
            combinations.add(edgesLine.getVector());
        }

        return combinations;
    }

    public static List<Point2D> newHexTile2(Point2D centre, double r, String style) {
        List<Point2D> edges = Patterns.cloneAndTranslateScalePoints(centre, r, hexPoints);

        List<Point2D> edgesAlt = clonePoints(hexPointsAlt);

        scalePoints(edgesAlt, r * HEX_DIST_OUTER_CIRCLE);
        translatePoints(edgesAlt, centre);

        ImmutableList.Builder<Point2D> points = ImmutableList.builder();

        for (int i = 0; i < edges.size(); i++) {
            points.add(edges.get(i));
            points.add(edgesAlt.get(i));
        }

        return points.build();

    }



}
