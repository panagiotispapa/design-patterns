package com.design.common;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
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



    public final static double PI_HALF = PI / 2.0;
    public final static double PI_QUARTER = PI / 4.0;




    public final static int OCT_N = 8;
    public final static double OCT_PHI = (2.0 * PI) / OCT_N;


    public static double[] OCT_RADIANS = computeDegrees(OCT_N, OCT_PHI);
    public static double[] OCT_RADIANS_ROT = computeDegreesRot(OCT_N, OCT_PHI);


    public static List<Point2D> octPoints = newEdgesAt(OCT_RADIANS);
    public static List<Point2D> octPointsRot = newEdgesAt(OCT_RADIANS_ROT);


    public final static int RECT_N = 4;
    public final static double RECT_PHI = (2.0 * PI) / RECT_N;


    public static double[] RECT_RADIANS = computeDegrees(RECT_N, RECT_PHI);
    public static double[] RECT_RADIANS_ROT = computeDegreesRot(RECT_N, RECT_PHI);

    public static List<Point2D> rectPoints = newEdgesAt(RECT_RADIANS);
    public static List<Point2D> rectPointsRot = newEdgesAt(RECT_RADIANS_ROT);

    public final static double RECT_DIST_HEIGHT = calcDistHeight(RECT_PHI);

    public final static int HEX_N = 6;
    public final static double HEX_PHI = (2.0 * PI) / HEX_N;
    public final static double HEX_PHI_HALF = HEX_PHI / 2.0;
    public final static double HEX_PHI_QUARTER = HEX_PHI / 4.0;
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
    public final static double HEX_DIST_DAM = calcDistDam();

    public static double[] HEX_RADIANS = computeDegrees(HEX_N, HEX_PHI);
    public static double[] HEX_RADIANS_ROT = computeDegreesRot(HEX_N, HEX_PHI);

    public static List<Point2D> hexPoints = newEdgesAt(HEX_RADIANS);
    public static List<Point2D> hexPointsAlt = newEdgesAt(HEX_RADIANS_ROT);

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

    private static double calcDistDam() {

        double phi1 = Math.atan(1 / (4.0 * sin(HEX_PHI)));
        double phi2 = HEX_PHI - phi1;
        double d1 = (HEX_DIST_HEIGHT / 2.0) * tan(phi2);

        return HEX_DIST_HEIGHT * HEX_DIST_HEIGHT - d1;
    }

    private static double calcDist3(double phi) {
        final double phiHalf = phi / 2.0;

        return (1.0 - 2.0 * sin(phiHalf) * sin(phiHalf)) * cos(phiHalf);
    }

    private static double calcDistEq1(double phi) {
        return 1.0 / (1.0 + tan(phi / 2.0));
    }

    private static double[] computeDegrees(int n, double phi) {

        double[] degrees = new double[n];

        for (int k = 0; k < n; k++) {
            degrees[k] = phi * k;
        }

        return degrees;

    }

    private static double[] computeDegreesRot(int n, double phi) {

        double[] degrees = new double[n];

        for (int k = 0; k < n; k++) {
            degrees[k]= phi * (k + 0.5);
        }

        return degrees;


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

    public static Point2D newRectEdge(Point2D centre, double r, int index) {
        Point2D edge = newEdgeAt(RECT_PHI * (index % RECT_N));
        scalePoint(edge, r);
        translatePoint(edge, centre);

        return edge;
    }

    public static Point2D newRectEdgeRot(Point2D centre, double r, int index) {
        Point2D edge = newEdgeAt(RECT_PHI * (index % RECT_N) + 0.5);
        scalePoint(edge, r);
        translatePoint(edge, centre);

        return edge;
    }

    public static Point2D newHexEdge(Point2D centre, double r, int index) {
        Point2D edge = newEdgeAt(HEX_PHI * index);
        scalePoint(edge, r);
        translatePoint(edge, centre);

        return edge;
    }

    public static Point2D newHexEdgeRot(Point2D centre, double r, int index) {
        Point2D edge = newEdgeAt(HEX_PHI * (index + 0.5));
        scalePoint(edge, r);
        translatePoint(edge, centre);

        return edge;
    }

    public static Point2D newEdgeAt(Point2D centre, double r, double phi) {

        Point2D edge = newEdgeAt(phi);
        scalePoint(edge, r);
        translatePoint(edge, centre);

        return edge;
    }

    private static List<Point2D> newEdgesAt(double[] radians) {
        List<Point2D> edges = newArrayList();
        for (double radian : radians) {
            edges.add(newEdgeAt(radian));
        }
        return edges;
    }

    public static Point2D newEdgeAt(double phi) {
        return newCentre(
                cos(phi),
                sin(phi)
        );
    }

    public static List<Point2D> cloneAndTranslateScalePoints(final Point2D centre, final double r, List<Point2D> points) {

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

    public static List<Point2D> newOct(Point2D centre, double r) {
        return cloneAndTranslateScalePoints(centre, r, octPoints);
    }

    public static List<Point2D> newOctRot(Point2D centre, double r) {
        return cloneAndTranslateScalePoints(centre, r, octPointsRot);
    }

    public static List<Point2D> newRect(Point2D centre, double r) {
        return cloneAndTranslateScalePoints(centre, r, rectPoints);
    }

    public static List<Point2D> newRectRot(Point2D centre, double r) {
        return cloneAndTranslateScalePoints(centre, r, rectPointsRot);
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

    public static List<List<Point2D>> newHexInnerTrianglesFull(Point2D centre, double r) {
        List<Point2D> hex = newHexagon(centre, r);

        return asList(
                asList(hex.get(0), hex.get(2), hex.get(4)),
                asList(hex.get(1), hex.get(3), hex.get(5))
        );

    }

    public static List<List<Point2D>> newHexInnerTrianglesFullRot(Point2D centre, double r) {
        List<Point2D> hex = newHexagonRot(centre, r);

        return asList(
                asList(hex.get(0), hex.get(2), hex.get(4)),
                asList(hex.get(1), hex.get(3), hex.get(5))
        );

    }

    public static List<List<Point2D>> concateOuterHexagons(List<Point2D> outer, List<Point2D> outerRot) {

        List<List<Point2D>> out = newArrayList();

        int index = 0;
        for (Point2D edge : outer) {

            out.add(asList(edge, outerRot.get(toHexIndex(1 + index))));
            out.add(asList(outerRot.get(toHexIndex(index)), outer.get(toHexIndex(2 + index))));

//            out.add(asList(outer.get(toHexIndex(1+index)), outerRot.get(index)));
            index++;
        }

        return out;

    }

    public static int toHexIndex(int i) {
        return i % HEX_N;
    }
    public static int toOctIndex(int i) {
        return i % OCT_N;
    }


    public static long TIME;
}
