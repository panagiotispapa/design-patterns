package com.design.islamic;

import com.design.common.view.SvgFactory;
import com.design.islamic.model.hex.Tile1;
import com.design.islamic.model.hex.Tile3;
import com.design.islamic.model.hex.TileStar;
import com.design.islamic.model.hex.Tiles;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jamesmurty.utils.XMLBuilder;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Set;

import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.GenericTools.*;
import static com.design.islamic.model.Centre.newCentre;
import static com.design.islamic.model.hex.Tiles.*;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.*;
import static java.util.Arrays.asList;
import static org.paukov.combinatorics.Factory.createSimpleCombinationGenerator;
import static org.paukov.combinatorics.Factory.createVector;

public final class Patterns {

    public final static int HEX_N = 6;

    public final static double HEX_PHI = (2.0 * PI) / HEX_N;
    public final static double HEX_DIST_HEIGHT = calcDistHeight(HEX_PHI);
    public final static double HEX_DIST_HEIGHT2 = calcDistHeight2(HEX_PHI);
    public final static double HEX_DIST2 = calcDist2(HEX_PHI);
    public final static double HEX_DIST3 = calcDist3(HEX_PHI);
    public final static double HEX_DIST_EQ1 = calcDistEq1(HEX_PHI);
    public final static double HEX_DIST_NEW_CENTRE = 2.0 * HEX_DIST_HEIGHT;
    public final static double HEX_DIST_1 = HEX_DIST_NEW_CENTRE - 1;

    public static List<Point2D> hexPoints = computePoints(HEX_N, HEX_PHI);
    public static List<Point2D> hexPointsAlt = computePointsAlt(HEX_N, HEX_PHI);

    private Patterns() {

    }

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

    private static List<Point2D> computePoints(int n, double phi) {

        ImmutableList.Builder<Point2D> posBuilder = ImmutableList.builder();
        for (int k = 0; k < n; k++) {
            posBuilder.add(newCentre(
                    cos(phi * k),
                    sin(phi * k)
            ));
        }
        return posBuilder.build();
    }

    private static List<Point2D> computePointsAlt(int n, double phi) {

        ImmutableList.Builder<Point2D> posBuilder = ImmutableList.builder();
        for (int k = 0; k < n; k++) {
            posBuilder.add(newCentre(
                    cos(phi * (k + 0.5)),
                    sin(phi * (k + 0.5))
            ));
        }
        return posBuilder.build();
    }

    public static Set<Point2D> edgesFromCentres(Iterable<Point2D> centres, double r) {

        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        builder.addAll(calculateHexEdges(centres, r));
        builder.addAll(centres);

        return builder.build();

    }

    public static Set<Point2D> calculateHexEdges(Iterable<Point2D> centres, double r) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(calculateHexEdges(centre, r));
        }

        return builder.build();
    }

    public static List<Point2D> calculateHexEdges(Point2D centre, double r) {
        List<Point2D> edges = clonePoints(Patterns.hexPoints);
        scalePoints(edges, r);
        translatePoints(edges, centre);

        return edges;

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

    private static double calcDistHeight(double phi) {
        return cos(phi / 2.0);
    }

    private static double calcDistHeight2(double phi) {

        final double cos = cos(phi / 2.0);
        return cos * cos;
    }

    private static double calcDist2(double phi) {
        return calcDistHeight(phi) - sin(phi / 2.0) * tan(phi / 2.0);
    }

    private static double calcDist3(double phi) {
        final double phiHalf = phi / 2.0;

        return (1.0 - 2.0 * sin(phiHalf) * sin(phiHalf)) * cos(phiHalf);
    }

    private static double calcDistEq1(double phi) {
        return 1.0 / (1.0 + tan(phi / 2.0));
    }

    private static List<Point2D> calculateNewCellCentres(Point2D centre, double r) {
        List<Point2D> newPoints = clonePoints(Patterns.hexPointsAlt);

        scalePoints(newPoints, r * HEX_DIST_NEW_CENTRE);
        translatePoints(newPoints, centre);
        newPoints.add(centre);

        return newPoints;
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

        List<Tile1> hexagons = Tiles.newTiles1(centresSecondConf, r);

        List<XMLBuilder> tiles = newArrayList(transform(hexagons, new Function<Tile1, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Tile1 tile) {
                return draw(tile, styleColoured);
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
                return newHexagon(centre, r, style);
            }
        }));

        final String style2 = newStyle("blue", "green", 1, 0, 1);

        List<XMLBuilder> circles = ImmutableList.copyOf(transform(edgesFromCentres(centresSecondConf, r), new Function<Point2D, XMLBuilder>() {
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
                return newTileStar(centre, r, dist);
            }
        }));

        final String style2 = newStyle("blue", "blue", 1, 1, 1);

        List<Point2D> backGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));

        List<XMLBuilder> allShapes = newArrayList();

        allShapes.add(drawPolygon(backGroundRect, style2));
        allShapes.addAll(newArrayList(transform(stars, new Function<TileStar, XMLBuilder>() {
            @Override
            public XMLBuilder apply(TileStar tileStar) {
                return draw(tileStar, style);
            }
        })));

        XMLBuilder svg = buildSvg(width, height, allShapes);

        System.out.println("finished building svg!!!");
        return svg;
    }

    public static XMLBuilder buildHexPattern3(Iterable<Point2D> centresFirstConf, Iterable<Point2D> centresSecondConf, final double r, int width, int height) {

        double opacity = 1;

        final String styleGreen = newStyle(GREEN, GREEN, 1, opacity, opacity);
        final String styleBlue = newStyle(BLUE, BLUE, 1, opacity, opacity);
        final String styleOrange = newStyle(ORANGE, ORANGE, 1, opacity, opacity);
        final String styleWhite = newStyle(WHITE, WHITE, 1, 0.4, 0.4);

        List<Tile3> tiles = newArrayList(transform(centresSecondConf, new Function<Point2D, Tile3>() {
            @Override
            public Tile3 apply(Point2D centre) {
                return newTile3(centre, r);
            }
        }));

        List<Point2D> backGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));
//        List<Point2D> forGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));

        List<XMLBuilder> total = newArrayList();
        total.add(drawPolygon(backGroundRect, styleBlue));
        for (Tile3 tile : tiles) {
            total.addAll(draw(tile, styleOrange, styleGreen));
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
                buildHexPoints(centre, r),
                buildHexAltPoints(centre, r * dist)
        );

    }

    public static XMLBuilder newHexStarTileRotated(Point2D centre, double r, String style, double dist) {

        final double newR = r * cos(HEX_PHI / 2);

        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        List<Point2D> edgesAlt = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAlt, r * HEX_DIST_HEIGHT);
        translatePoints(edgesAlt, centre);

        List<Point2D> edgesAlt2 = clonePoints(Patterns.hexPoints);

        scalePoints(edgesAlt2, newR * dist);
        translatePoints(edgesAlt2, centre);

//        List<Point2D> edgesLayer5 = concatEdges(edgesAlt2, edgesAlt);

        return drawPolygon(concatEdges(edgesAlt2, edgesAlt), style);

    }

    public static List<Point2D> buildHexPoints(final Point2D centre, final double r) {

        List<Point2D> edges = clonePoints(hexPoints);
        scalePoints(edges, r);
        translatePoints(edges, centre);

        return edges;
    }

    public static List<Point2D> buildHexAltPoints(final Point2D centre, final double r) {

        List<Point2D> edges = clonePoints(hexPointsAlt);
        scalePoints(edges, r);
        translatePoints(edges, centre);

        return edges;
    }

    public static List<Point2D> buildHeightEdges(final Point2D centre, final double r) {
        List<Point2D> heights = clonePoints(hexPointsAlt);
        scalePoints(heights, r * HEX_DIST_HEIGHT);
        translatePoints(heights, centre);

        return heights;
    }

    public static List<List<Point2D>> buildHeights(final Point2D centre, final double r) {

        List<List<Point2D>> heights = newArrayList();
        for (Point2D point2D : buildHeightEdges(centre, r)) {
            heights.add(asList(centre, point2D));
        }

        return heights;
    }

    private static List<Point2D> buildStarRotatedEdges(final Point2D centre, final double r, double dist) {

        final double newR = r * HEX_DIST_HEIGHT;

        List<Point2D> layer1 = clonePoints(Patterns.hexPointsAlt);

        scalePoints(layer1, r * HEX_DIST_HEIGHT);
        translatePoints(layer1, centre);

        List<Point2D> layer2 = clonePoints(Patterns.hexPoints);

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
        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        List<Point2D> edgesAlt = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAlt, r * HEX_DIST_1);
        translatePoints(edgesAlt, centre);

        ImmutableList.Builder<Point2D> points = ImmutableList.builder();

        for (int i = 0; i < edges.size(); i++) {
            points.add(edges.get(i));
            points.add(edgesAlt.get(i));
        }

        return points.build();

    }

    public static XMLBuilder newHexagon(Point2D centre, double r, String style) {
        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        return drawPolygon(edges, style);

    }

}
