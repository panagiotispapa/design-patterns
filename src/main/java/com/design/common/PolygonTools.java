package com.design.common;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.Math.*;
import static java.util.stream.Collectors.toList;

public class PolygonTools {

    public static Point2D newEdgeAt(int n, double offset, int N) {
        final double phi = (2.0 * PI) / N;
        return newEdgeAt(phi * (n + offset));
    }

    public static Point2D newEdgeAt(double phi) {
        return new Point2D.Double(
                cos(phi),
                sin(phi)
        );
    }


}
