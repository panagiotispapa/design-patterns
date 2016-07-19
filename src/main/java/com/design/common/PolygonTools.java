package com.design.common;

import java.awt.geom.Point2D;

import static java.lang.Math.*;

public class PolygonTools {

    public static Point2D newEdgeAt(int n, double offset, int N) {
        final double phi = (2.0 * PI) / N;
        return newEdgeAt(phi * (n + offset));
    }

    private static Point2D newEdgeAt(double phi) {
        return new Point2D.Double(
                cos(phi),
                sin(phi)
        );
    }


}
