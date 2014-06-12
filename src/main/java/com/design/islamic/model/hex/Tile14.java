package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.design.common.PolygonTools.*;

public class Tile14 implements Tile {

    private List<List<Point2D>> lines;

    private final double newR;
//    private final double newR2;

    private Point2D[] pointsA;
    private Point2D[] pointsB;
    private Point2D[] pointsC;

    private final Point2D centre;
    private final double r;

    public Tile14(final Point2D centre, final double r) {

        lines = new ArrayList<>();

        this.centre = centre;
        this.r = r;

        newR = r / 4.0;
//        newR2 = newR * HEX_DIST_NEW_CENTRE;

        buildPoints(centre, r);

        for (int i = 0; i < HEX_N; i++) {
            lines.add(Arrays.asList(
                    pointsB[i],
                    pointsA[toHexIndex(i+1)],
                    pointsC[toHexIndex(i+2)]
            ));
        }

    }

    private void buildPoints(final Point2D centre, final double r) {

        List<Point2D> layer2 = newHexagon(centre, 2.0 * newR);

        pointsA = new Point2D[HEX_N];
        pointsB = new Point2D[HEX_N];
        pointsC = new Point2D[HEX_N];

        for (int i = 0; i < HEX_N; i++) {

            pointsA[i] = newEdgeAt(centre, newR, HEX_RADIANS[i]);
            pointsB[i] = getNewPoint(i, 2);
            pointsC[i] = getNewPoint(i,4);

        }

    }

    private Point2D getNewPoint(int index, int offset) {
        Point2D newCentre = newEdgeAt(centre, r, HEX_RADIANS[index]);

        return newEdgeAt(newCentre, newR, HEX_RADIANS[toHexIndex(index + offset)]);
    }

    public Point2D[] getPointsA() {
        return pointsA;
    }

    public Point2D[] getPointsB() {
        return pointsB;
    }

    public Point2D[] getPointsC() {
        return pointsC;
    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(lines);
    }
}
