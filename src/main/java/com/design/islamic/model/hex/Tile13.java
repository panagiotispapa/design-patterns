package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;

public class Tile13 implements Tile {

    private List<List<Point2D>> polygons;
    private List<List<Point2D>> lines;

    private final double newR;
//    private final double newR2;

    private Point2D[] pointsA;
    private Point2D[] pointsB;
    private Point2D[] pointsC;
    private Point2D[] pointsD;
    private Point2D[] pointsE;
    private Point2D[] pointsF;

    public Tile13(final Point2D centre, final double r) {

        lines = newArrayList();

        newR = r / 3.0;
//        newR2 = newR * HEX_DIST_NEW_CENTRE;

        buildPoints(centre, r);

        for (int i = 0; i < HEX_N; i++) {
            lines.add(Arrays.asList(
                    pointsA[i],
                    pointsB[i],
                    pointsC[i],
                    pointsD[i],
                    pointsE[i],
                    pointsF[i]
            ));
        }

    }

    private void buildPoints(final Point2D centre, final double r) {


        List<Point2D> layer2 = newHexagon(centre, 2.0 * newR);

        pointsA = new Point2D[HEX_N];
        pointsB = new Point2D[HEX_N];
        pointsC = new Point2D[HEX_N];
        pointsD = new Point2D[HEX_N];
        pointsE = new Point2D[HEX_N];
        pointsF = new Point2D[HEX_N];

        for (int i = 0; i < HEX_N; i++) {

            pointsA[i] = newEdgeAt(layer2.get(i), newR, HEX_RADIANS.get(toHexIndex(i+3)));
            pointsB[i] = newEdgeAt(layer2.get(i), newR, HEX_RADIANS.get(toHexIndex(i+2)));
            pointsC[i] = newEdgeAt(layer2.get(i), newR, HEX_RADIANS.get(toHexIndex(i+1)));
            pointsD[i] = newEdgeAt(layer2.get(i), newR, HEX_RADIANS.get(toHexIndex(i+5)));
            pointsE[i] = newEdgeAt(layer2.get(i), newR, HEX_RADIANS.get(toHexIndex(i+4)));
            pointsF[i] = newEdgeAt(layer2.get(i), newR, HEX_RADIANS.get(toHexIndex(i+3)));

        }

    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(lines);
    }
}
