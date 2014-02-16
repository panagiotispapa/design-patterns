package com.design.common;

import com.google.common.collect.ImmutableList;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.islamic.GenericTools.*;
import static com.design.islamic.model.Centre.newCentre;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.*;
import static java.util.Arrays.asList;
import static org.paukov.combinatorics.Factory.createSimpleCombinationGenerator;
import static org.paukov.combinatorics.Factory.createVector;

public class PolygonTools {
    public final static int HEX_N = 6;
    public final static int RECT_N = 4;
    public final static int OCT_N = 8;

    public final static double HEX_PHI = (2.0 * PI) / HEX_N;
    public final static double HEX_THETA = (2.0 * PI - HEX_PHI);

    public final static double HEX_DIST_PROJ = calcDistProj(HEX_PHI);
    public final static double HEX_DIST_HEIGHT = calcDistHeight(HEX_PHI);
    public final static double HEX_DIST_HEIGHT2 = calcDistHeight2(HEX_PHI);
    public final static double HEX_DIST_DIAGONAL = calcDistDiagonal(HEX_PHI);
    public final static double HEX_DIST_DIAGONAL_ROTATED = HEX_DIST_DIAGONAL * HEX_DIST_HEIGHT;
    public final static double HEX_DIST3 = calcDist3(HEX_PHI);
    public final static double HEX_DIST_EQ1 = calcDistEq1(HEX_PHI);
    public final static double HEX_DIST_NEW_CENTRE = 2.0 * HEX_DIST_HEIGHT;
    public final static double HEX_DIST_OUTER_CIRCLE = HEX_DIST_NEW_CENTRE - 1;

    public static List<Point2D> hexPoints = computePoints(HEX_N, HEX_PHI);
    public static List<Point2D> hexPointsAlt = computePointsAlt(HEX_N, HEX_PHI);

    private static double calcDistProj(double phi) {
        return sin(phi / 2.0);
    }

    private static double calcDistHeight(double phi) {
        return cos(phi / 2.0);
    }

    private static double calcDistHeight2(double phi) {

        final double cos = cos(phi / 2.0);
        return cos * cos;
    }

    private static double calcDistDiagonal(double phi) {
        return calcDistHeight(phi) - sin(phi / 2.0) * tan(phi / 2.0);
    }

    private static double calcDist3(double phi) {
        final double phiHalf = phi / 2.0;

        return (1.0 - 2.0 * sin(phiHalf) * sin(phiHalf)) * cos(phiHalf);
    }

    private static double calcDistEq1(double phi) {
        return 1.0 / (1.0 + tan(phi / 2.0));
    }

    private static List<Point2D> computePoints(int n, double phi) {

        ImmutableList.Builder<Point2D> posBuilder = ImmutableList.builder();
        for (int k = 0; k < n; k++) {
            posBuilder.add(newEdgeAt(phi * k)
            );
        }
        return posBuilder.build();
    }

    private static List<Point2D> computePointsAlt(int n, double phi) {

        ImmutableList.Builder<Point2D> posBuilder = ImmutableList.builder();
        for (int k = 0; k < n; k++) {
            posBuilder.add(newEdgeAt(phi * (k + 0.5)
            ));
        }
        return posBuilder.build();
    }

    public static Point2D newEdgeAt(double phi) {
        return newCentre(
                cos(phi),
                sin(phi)
        );
    }

    public static List<Point2D> cloneAndTranslateScalePoints(final Point2D centre, final double r, Iterable<Point2D> points) {

        List<Point2D> edges = clonePoints(points);
        scalePoints(edges, r);
        translatePoints(edges, centre);

        return edges;

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

    public static List<List<Point2D>> buildLines(final Point2D centre, List<Point2D> points) {

        List<List<Point2D>> lines = newArrayList();
        for (Point2D point2D : points) {
            lines.add(asList(centre, point2D));
        }

        return lines;
    }

    public static List<Point2D> newHexagon(Point2D centre, double r) {
        return cloneAndTranslateScalePoints(centre, r, hexPoints);
    }

    public static List<Point2D> newHexagonRot(Point2D centre, double r) {
        return cloneAndTranslateScalePoints(centre, r, hexPointsAlt);
    }

    public static List<List<Point2D>> newHexDiag(Point2D centre, double r) {
        return buildLines(centre, newHexagon(centre, r));
    }

    public static List<List<Point2D>> newHexDiagRot(Point2D centre, double r) {
        return buildLines(centre, newHexagonRot(centre, r));
    }

    public static List<List<Point2D>> newHexHeights(Point2D centre, double r) {
        return buildLines(centre, newHexagonRot(centre, r * HEX_DIST_HEIGHT));
    }

    public static List<List<Point2D>> newHexHeightsRot(Point2D centre, double r) {
        return buildLines(centre, newHexagon(centre, r * HEX_DIST_HEIGHT));
    }

    public static List<Point2D> newHexStarTile(Point2D centre, double r, double dist) {

        return concatEdges(

                cloneAndTranslateScalePoints(centre, r * dist, hexPoints),
                cloneAndTranslateScalePoints(centre, r, hexPointsAlt)
        );

    }

    public static List<Point2D> newHexStarTileRotated(Point2D centre, double r, double dist) {

        final double newR = r * HEX_DIST_HEIGHT;

        return concatEdges(
                cloneAndTranslateScalePoints(centre, r * HEX_DIST_HEIGHT, hexPoints),
                cloneAndTranslateScalePoints(centre, newR * dist, hexPointsAlt)
        );

    }

    public static List<List<Point2D>> newHexInnerTriangles(Point2D centre, double r) {
        List<Point2D> heights = newHexagonRot(centre, r * HEX_DIST_HEIGHT);

        return asList(
                asList(heights.get(0), heights.get(2), heights.get(4)),
                asList(heights.get(1), heights.get(3), heights.get(5))

        );

    }

    public static List<List<Point2D>> newHexInnerTrianglesRot(Point2D centre, double r) {
        List<Point2D> heights = newHexagon(centre, r * HEX_DIST_HEIGHT);

        return asList(
                asList(heights.get(0), heights.get(2), heights.get(4)),
                asList(heights.get(1), heights.get(3), heights.get(5))

        );

    }

    public static int toHexIndex(int i) {
        return i % HEX_N;
    }

}
