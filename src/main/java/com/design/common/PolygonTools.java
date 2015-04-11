package com.design.common;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.design.islamic.GenericTools.*;
import static java.lang.Math.*;
import static java.util.stream.Collectors.toList;

public class PolygonTools {


    public final static int HEX_N = 6;
    public final static double HEX_PHI = (2.0 * PI) / HEX_N;


    public static double[] HEX_RADIANS = computeDegrees(HEX_N, HEX_PHI);

    public static List<Point2D> hexPoints = newEdgesAt(HEX_RADIANS);

    public static List<Point2D> calcVertexes(int N, double offset) {
        final double phi = (2.0 * PI) / N;
        return IntStream.range(0, N)
                .mapToDouble(k -> phi * (k + offset))
                .mapToObj(PolygonTools::newEdgeAt).collect(toList());
    }

    private static double[] computeDegrees(int n, double phi) {

//        IntStream.range(0,n).mapToDouble(k-> phi*k).toArray();

        double[] degrees = new double[n];

        for (int k = 0; k < n; k++) {
            degrees[k] = phi * k;
        }

        return degrees;

    }


    private static List<Point2D> newEdgesAt(double[] radians) {
        return Arrays.stream(radians).mapToObj(PolygonTools::newEdgeAt).collect(toList());
    }

    public static Point2D newEdgeAt(double phi) {
        return new Point2D.Double(
                cos(phi),
                sin(phi)
        );
    }

    public static List<Point2D> cloneAndTranslateScalePoints(final Point2D centre, final double r, List<Point2D> points) {

        List<Point2D> edges = clonePoints(points);
        edges.stream().forEach(e -> {
            scalePoint(e, r);
            translatePoint(e, centre);
        });

        return edges;

    }


    public static List<Point2D> newHexagon(Point2D centre, double r) {
        return cloneAndTranslateScalePoints(centre, r, hexPoints);
    }


}
