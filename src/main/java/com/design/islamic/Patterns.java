package com.design.islamic;

import com.design.islamic.model.tiles.svg.SvgFactory;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Set;

import static com.design.islamic.GenericTools.*;
import static com.design.islamic.model.Centre.newCentre;
import static com.design.islamic.model.tiles.svg.SvgFactory.*;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static java.lang.Math.*;
import static java.util.Arrays.asList;

public final class Patterns {

    public final static int HEX_N = 6;

    public final static double HEX_PHI = (2.0 * PI) / HEX_N;
    public final static double HEX_DIST = calcDist(HEX_PHI);
    public final static double HEX_DIST2 = calcDist2(HEX_PHI);
    public final static double HEX_DIST3 = calcDist3(HEX_PHI);
    public final static double HEX_DIST_NEW_CENTRE = 2.0 * HEX_DIST;
    public final static double HEX_DIST_1 = HEX_DIST_NEW_CENTRE - 1;

    public static List<Point2D> hexPoints = computePoints(HEX_N, HEX_PHI);
    public static List<Point2D> hexPointsAlt = computePointsAlt(HEX_N, HEX_PHI);

    private Patterns() {

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
                final double dist = i % 2 == 0 ? j * dist2 : j * dist2 - r * HEX_DIST;
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

    private static double calcDist(double phi) {
        return cos(phi / 2.0);
    }

    private static double calcDist2(double phi) {
        return calcDist(phi) - sin(phi / 2.0) * tan(phi / 2.0);
    }

    private static double calcDist3(double phi) {
        final double phiHalf = phi / 2.0;

        return (1.0-2.0*sin(phiHalf)*sin(phiHalf))*cos(phiHalf);
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
                                newPolygon(
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

        return newPolygon(concatEdges(edges, edgesAlt), style);

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

        String black = newStyle("black", "black", 2, 0, 1);
        String blue = newStyle("black", "blue", 1, 0, 1);
        final String gray = newStyle("black", "gray", 1, 0, 1);
        final String green = newStyle("black", "green", 1, 0, 1);
        builder.add(newCircle(centre, r, black));
        builder.add(newPolygon(edges, blue));
        builder.addAll(highlightPoints(outsideCentres));

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

//        builder.addAll(highlightPoints(pointsLayerMiddle));

        builder.add(newPolyline(asList(edges.get(0), edges.get(2)), gray));
        builder.add(newPolyline(asList(edges.get(0), edges.get(3)), gray));
        builder.add(newPolyline(asList(edges.get(0), edges.get(4)), gray));

        builder.add(newPolyline(asList(edges.get(1), edges.get(3)), gray));
        builder.add(newPolyline(asList(edges.get(1), edges.get(4)), gray));
        builder.add(newPolyline(asList(edges.get(1), edges.get(5)), gray));

        builder.add(newPolyline(asList(edges.get(2), edges.get(4)), gray));
        builder.add(newPolyline(asList(edges.get(2), edges.get(5)), gray));

        builder.add(newPolyline(asList(edges.get(3), edges.get(2)), gray));

        builder.add(newPolyline(asList(edges.get(4), edges.get(2)), gray));
        builder.add(newPolyline(asList(edges.get(4), edges.get(3)), gray));

        builder.add(newPolyline(asList(edges.get(5), edges.get(3)), gray));
        builder.add(newPolyline(asList(edges.get(5), edges.get(4)), gray));

        builder.add(newPolygon(edgesAltLayer1, gray));
        builder.add(newPolygon(pointsLayerMiddle, gray));

        builder.add(newPolygon(GenericTools.concatEdges(edges, edgesAltLayer1), green));
        builder.add(newPolygon(GenericTools.concatEdges(edges, edgesAltLayer2), green));
        builder.add(newPolygon(GenericTools.concatEdges(edges, edgesAltLayer3), green));

        builder.addAll(highlightPoints(edgesAltLayer1));
        builder.addAll(highlightPoints(edgesAltLayer2));
        builder.addAll(highlightPoints(edgesAltLayer3));

        return builder.build();

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

        return newPolygon(edges, style);

    }

}
