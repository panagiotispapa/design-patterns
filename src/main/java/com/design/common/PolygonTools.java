package com.design.common;

import com.google.common.collect.ImmutableList;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.islamic.model.Centre.newCentre;
import static java.lang.Math.*;
import static java.lang.Math.sin;
import static java.lang.Math.tan;

public class PolygonTools {
    public final static int HEX_N = 6;
    public final static int RECT_N = 4;
    public final static int OCT_N = 8;

    public final static double HEX_PHI = (2.0 * PI) / HEX_N;
    public final static double HEX_THETA = (2.0 * PI - HEX_PHI);

    public final static double HEX_DIST_HEIGHT = calcDistHeight(HEX_PHI);
    public final static double HEX_DIST_HEIGHT2 = calcDistHeight2(HEX_PHI);
    public final static double HEX_DIST_DIAGONAL = calcDistDiagonal(HEX_PHI);
    public final static double HEX_DIST3 = calcDist3(HEX_PHI);
    public final static double HEX_DIST_EQ1 = calcDistEq1(HEX_PHI);
    public final static double HEX_DIST_NEW_CENTRE = 2.0 * HEX_DIST_HEIGHT;
    public final static double HEX_DIST_OUTER_CIRCLE = HEX_DIST_NEW_CENTRE - 1;

    public static List<Point2D> hexPoints = computePoints(HEX_N, HEX_PHI);
    public static List<Point2D> hexPointsAlt = computePointsAlt(HEX_N, HEX_PHI);



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
    public static Point2D newEdgeAt(double phi){
        return newCentre(
                cos(phi),
                sin(phi)
        );
    }



}
