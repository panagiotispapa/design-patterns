package com.design.islamic;

import com.design.common.view.SvgFactory;
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
    public final static double HEX_DIST_NEW_CENTRE = 2.0 * HEX_DIST_HEIGHT;
    public final static double HEX_DIST_1 = HEX_DIST_NEW_CENTRE - 1;

    public static List<Point2D> hexPoints = computePoints(HEX_N, HEX_PHI);
    public static List<Point2D> hexPointsAlt = computePointsAlt(HEX_N, HEX_PHI);

    private Patterns() {

    }

    private static List<Point2D> computePoints(int n, double phi, double offset) {

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

    private static double calcRFromHeight(double height, double phi) {
        return height / cos(phi / 2.0);
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

        final String style = newStyle("yellow", "green", 2, 1, 1);

        List<XMLBuilder> hexagons = ImmutableList.copyOf(transform(centresSecondConf, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newHexagon(centre, r, style);
            }
        }));

        XMLBuilder svg = buildSvg(width, height,
                concat(
                        hexagons
//                        , highlightPoints(centres)
                )
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

        List<XMLBuilder> stars = ImmutableList.copyOf(transform(centresFirstConf, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newHexStarTile(centre, r, style, dist);
            }
        }));

        final String style2 = newStyle("blue", "blue", 1, 1, 1);

        XMLBuilder svg = buildSvg(width, height,
                concat(
                        asList(
                                drawPolygon(
                                        asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height)),
                                        style2)),
                        stars
                )
        );

        System.out.println("finished building svg!!!");
        return svg;
    }

    public static XMLBuilder buildHexStarInnerWithRectangles(Iterable<Point2D> centresFirstConf, Iterable<Point2D> centresSecondConf, final double r, int width, int height, double dist) {

        double opacity = 1;

        final String styleGreen = newStyle(GREEN, GREEN, 1, opacity, opacity);
        final String styleBlue = newStyle(BLUE, BLUE, 1, opacity, opacity);
        final String styleOrange = newStyle(ORANGE, ORANGE, 1, opacity, opacity);
        final String styleWhite = newStyle(WHITE, WHITE, 1, 0.4, 0.4);

        List<XMLBuilder> stars = newArrayList();
        for (Point2D centre : centresSecondConf) {
            stars.addAll(newHexStarInnerWithRectangles(centre, r, styleOrange, styleBlue, dist));
        }

        List<Point2D> backGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));
        List<Point2D> forGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));

        List<XMLBuilder> total = newArrayList();
        total.add(drawPolygon(backGroundRect, styleGreen));
        total.addAll(stars);
//        total.add(drawPolygon(forGroundRect, styleWhite));

        XMLBuilder svg = buildSvg(width, height,
                total);

        System.out.println("finished building svg!!!");
        return svg;

    }

    public static XMLBuilder buildHexPatternStarRotated(Iterable<Point2D> centresFirstConf, Iterable<Point2D> centresSecondConf, final double r, int width, int height, final double dist) {

        final String style = newStyle("yellow", "yellow", 1, 1, 1);

        List<XMLBuilder> stars = ImmutableList.copyOf(transform(centresSecondConf, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newHexStarTileRotated(centre, r, style, dist);
            }
        }));

        final String style2 = newStyle("blue", "blue", 1, 1, 1);

        XMLBuilder svg = buildSvg(width, height,
                concat(
                        asList(
                                drawPolygon(
                                        asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height)),
                                        style2)),
                        stars
                )
        );

        System.out.println("finished building svg!!!");
        return svg;
    }

    public static XMLBuilder newHexStarTile(Point2D centre, double r, String style, double dist) {
        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        List<Point2D> edgesAlt = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAlt, r * dist);
        translatePoints(edgesAlt, centre);

        return drawPolygon(concatEdges(edges, edgesAlt), style);

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

    public static List<XMLBuilder> newHexStarInnerWithRectangles(Point2D centre, double r, String style, String styleRect, double dist) {

        String styleWhite = newStyle(WHITE, 1, 1);

        List<XMLBuilder> out = newArrayList();

        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        double newHeight = dist;

        final double newR = calcRFromHeight(r * newHeight, HEX_PHI);

        List<Point2D> innerHexEdges = clonePoints(Patterns.hexPoints);
        scalePoints(innerHexEdges, newR);
        translatePoints(innerHexEdges, centre);

        List<Point2D> edgesInnerHeights = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesInnerHeights, newR * HEX_DIST_HEIGHT);
        translatePoints(edgesInnerHeights, centre);

        double offsetPhi = atan((newR * 0.5) / (r * HEX_DIST_HEIGHT));
        double newR2 = (r * HEX_DIST_HEIGHT) / cos(offsetPhi);
        double newR3 = (r * newHeight + ((r * HEX_DIST_HEIGHT - r * newHeight) / 2.0)) / cos(offsetPhi / 2.0);

        List<Point2D> outerEdges1 = computePoints(HEX_N, HEX_PHI, HEX_PHI / 2.0 - offsetPhi);
        scalePoints(outerEdges1, newR2);
        translatePoints(outerEdges1, centre);

        List<Point2D> outerEdges2 = computePoints(HEX_N, HEX_PHI, HEX_PHI / 2.0 + offsetPhi);
        scalePoints(outerEdges2, newR2);
        translatePoints(outerEdges2, centre);

        List<Point2D> outerEdges3 = computePoints(HEX_N, HEX_PHI, HEX_PHI / 2.0 - offsetPhi / 2.0);
        scalePoints(outerEdges3, newR3);
        translatePoints(outerEdges3, centre);

        List<Point2D> outerEdges4 = computePoints(HEX_N, HEX_PHI, HEX_PHI / 2.0 + offsetPhi / 2.0);
        scalePoints(outerEdges4, newR3);
        translatePoints(outerEdges4, centre);

        List<List<Point2D>> layerExt = newArrayList();

        for (int i = 0; i < edgesInnerHeights.size(); i++) {
            layerExt.add(asList(edgesInnerHeights.get(i), outerEdges3.get(i), outerEdges1.get(i), outerEdges2.get((i + 5) % 6)));
            layerExt.add(asList(edgesInnerHeights.get(i), outerEdges4.get(i), outerEdges2.get(i)));
//            layerExt.add(asList(outerEdges1.get(i), outerEdges2.get((i + 5) % 6)));
        }

        List<List<Point2D>> extPolygons = generateExtPolygonsForStar(innerHexEdges, outerEdges1, outerEdges2);

        List<Point2D> insideStar = buildStarRotatedEdges(centre, newR, HEX_DIST2);

        out.add(drawPolygon(innerHexEdges, style));
        out.addAll(drawPolygons(extPolygons, styleRect));

//        out.add(drawPolygon(insideStar, styleWhite));
//        out.addAll(drawPolylines(layerExt, styleWhite));

        return out;

    }

    public static List<XMLBuilder> newStarDesign1(final Point2D centre, final double r) {
        ImmutableList.Builder<XMLBuilder> builder = ImmutableList.builder();

        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        final List<Point2D> outsideCentres = clonePoints(Patterns.hexPointsAlt);
        scalePoints(outsideCentres, r * HEX_DIST_NEW_CENTRE);
        translatePoints(outsideCentres, centre);

        List<Point2D> pointsLayerMiddle = clonePoints(Patterns.hexPoints);
        scalePoints(pointsLayerMiddle, 0.5 * r);
        translatePoints(pointsLayerMiddle, centre);

//        List<Point2D> pointsLayerMiddle = clonePoints(Patterns.hexPoints);
//        scalePoints(pointsLayerMiddle, 0.5 * r);
//        translatePoints(pointsLayerMiddle, centre);

        List<Point2D> edgesAltLayer1 = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAltLayer1, r * HEX_DIST_1);
        translatePoints(edgesAltLayer1, centre);

        List<Point2D> edgesAltLayer2 = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAltLayer2, r * HEX_DIST2);
        translatePoints(edgesAltLayer2, centre);

        List<Point2D> edgesAltLayer3 = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAltLayer3, r * HEX_DIST3);
        translatePoints(edgesAltLayer3, centre);

        String black = newStyle("black", 2, 1);
        String blue = newStyle("blue", 1, 1);
        final String gray = newStyle("gray", 1, 1);
        final String green = newStyle("green", 1, 1);
        builder.add(newCircle(centre, r, black));
//        builder.add(drawPolygon(edges, blue));
        builder.addAll(highlightPoints(outsideCentres));
        builder.add(drawPolygon(outsideCentres, gray));

        builder.addAll(transform(outsideCentres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newCircle(centre, r, gray);
            }
        }));

        builder.addAll(transform(outsideCentres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D endPoint) {
                return newPolyline(asList(centre, endPoint), gray);
            }
        }));

//

        builder.addAll(drawPolylines(generateCombsOfPoints(edges), gray));

        builder.add(drawPolygon(edgesAltLayer1, gray));
        builder.add(drawPolygon(pointsLayerMiddle, gray));

        builder.add(drawPolygon(GenericTools.concatEdges(edges, edgesAltLayer1), green));
        builder.add(drawPolygon(GenericTools.concatEdges(edges, edgesAltLayer2), green));
        builder.add(drawPolygon(GenericTools.concatEdges(edges, edgesAltLayer3), green));

        builder.addAll(highlightPoints(edgesAltLayer1));
        builder.addAll(highlightPoints(edgesAltLayer2));
        builder.addAll(highlightPoints(edgesAltLayer3));

        return builder.build();

    }

    public static List<XMLBuilder> newStarDesign2(final Point2D centre, final double r) {

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);

        final double newR = r * cos(HEX_PHI / 2);

        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        List<Point2D> edgesAltLayer2 = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAltLayer2, r * HEX_DIST_HEIGHT);
        translatePoints(edgesAltLayer2, centre);

        List<List<Point2D>> layer2 = generateCombsOfPoints(edgesAltLayer2);

        List<List<Point2D>> layer3 = asList(
                asList(edges.get(0), edges.get(3)),
                asList(edges.get(1), edges.get(4)),
                asList(edges.get(2), edges.get(5))
        );

        List<Point2D> edgesAltLayer4 = clonePoints(Patterns.hexPoints);

        scalePoints(edgesAltLayer4, newR * HEX_DIST2);
        translatePoints(edgesAltLayer4, centre);

        List<Point2D> edgesLayer5 = concatEdges(edgesAltLayer4, edgesAltLayer2);

        out.add(drawPolygon(edges, gray));
        out.addAll(drawPolylines(layer2, gray));
        out.addAll(drawPolylines(layer3, gray));
        out.add(drawPolygon(edgesLayer5, green));

        out.addAll(highlightPoints(edges));
        out.addAll(highlightPoints(edgesAltLayer2));
        out.addAll(highlightPoints(edgesAltLayer4));

        return out;

    }

    public static List<XMLBuilder> newStarDesign3(final Point2D centre, final double r) {

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);

//        double newHeight = HEX_DIST_HEIGHT * 0.5;
        double newHeight = HEX_DIST2;

        final double newR = calcRFromHeight(r * newHeight, HEX_PHI);
        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        List<Point2D> edgesAltLayer2 = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAltLayer2, r * newHeight);
        translatePoints(edgesAltLayer2, centre);

        List<Point2D> edgesLayer3 = clonePoints(Patterns.hexPoints);
        scalePoints(edgesLayer3, newR);
        translatePoints(edgesLayer3, centre);

        List<Point2D> insideStar = buildStarRotatedEdges(centre, newR, HEX_DIST2);

        List<Point2D> edgesInnerHeights = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesInnerHeights, newR * HEX_DIST_HEIGHT);
        translatePoints(edgesInnerHeights, centre);

        double offsetPhi = atan((newR * 0.5) / (r * HEX_DIST_HEIGHT));
        double newR2 = (r * HEX_DIST_HEIGHT) / cos(offsetPhi);
        double newR3 = (r * newHeight + ((r * HEX_DIST_HEIGHT - r * newHeight) / 2.0)) / cos(offsetPhi / 2.0);

        List<Point2D> edgesLayer4 = computePoints(HEX_N, HEX_PHI, HEX_PHI / 2.0 - offsetPhi);
        scalePoints(edgesLayer4, newR2);
        translatePoints(edgesLayer4, centre);

        List<Point2D> edgesLayer5 = computePoints(HEX_N, HEX_PHI, HEX_PHI / 2.0 + offsetPhi);
        scalePoints(edgesLayer5, newR2);
        translatePoints(edgesLayer5, centre);

        List<Point2D> edgesLayer6 = computePoints(HEX_N, HEX_PHI, HEX_PHI / 2.0 - offsetPhi / 2.0);
        scalePoints(edgesLayer6, newR3);
        translatePoints(edgesLayer6, centre);

        List<Point2D> edgesLayer7 = computePoints(HEX_N, HEX_PHI, HEX_PHI / 2.0 + offsetPhi / 2.0);
        scalePoints(edgesLayer7, newR3);
        translatePoints(edgesLayer7, centre);

        List<List<Point2D>> extPolygons = generateExtPolygonsForStar(edgesLayer3, edgesLayer4, edgesLayer5);

        List<List<Point2D>> layerExt = newArrayList();

        for (int i = 0; i < edgesInnerHeights.size(); i++) {
            layerExt.add(asList(edgesInnerHeights.get(i), edgesLayer6.get(i), edgesLayer4.get(i)));
            layerExt.add(asList(edgesInnerHeights.get(i), edgesLayer7.get(i), edgesLayer5.get(i)));
            layerExt.add(asList(edgesLayer4.get(i), edgesLayer5.get((i + 5) % 6)));
        }

        out.add(drawPolygon(edges, gray));
        out.add(drawPolygon(edgesLayer3, gray));

        out.add(drawPolygon(insideStar, gray));
//        out.add(drawPolygon(polygon1, gray));
//        out.add(drawPolygon(polygon2, gray));

        out.addAll(drawPolygons(
                extPolygons,
                gray));

        System.out.println(5 % 6);
        System.out.println(6 % 6);
        System.out.println(7 % 6);
        System.out.println(8 % 6);

//        out.add(newPolyline(asList(edgesLayer4.get(0), edgesLayer5.get(5)), gray));
//        out.add(newPolyline(asList(edgesLayer4.get(1), edgesLayer5.get(0)), gray));
//        out.add(newPolyline(asList(layerInnerHeights.get(0), edgesLayer6.get(0), edgesLayer4.get(0)), gray));
//        out.add(newPolyline(asList(layerInnerHeights.get(0), edgesLayer7.get(0), edgesLayer5.get(0)), gray));
        out.addAll(drawPolylines(layerExt, gray));

        out.addAll(highlightPoints(edges));
//        out.addAll(highlightPoints(edgesAltLayer2));
        out.addAll(highlightPoints(edgesLayer3));
        out.addAll(highlightPoints(edgesLayer4));
        out.addAll(highlightPoints(edgesLayer5));
        out.addAll(highlightPoints(edgesLayer6));
        out.addAll(highlightPoints(edgesLayer7));
        out.addAll(highlightPoints(edgesInnerHeights));

        return out;

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
