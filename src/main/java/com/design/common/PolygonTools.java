package com.design.common;

import static com.design.common.CanvasPoint.point;
import static java.lang.Math.*;

public class PolygonTools {

    public static CanvasPoint newEdgeAt(int n, double offset, int N) {
        final double phi = (2.0 * PI) / N;
        return newEdgeAt(phi * (n + offset));
    }

    private static CanvasPoint newEdgeAt(double phi) {
        return point(
                cos(phi),
                sin(phi)
        );
    }


}
