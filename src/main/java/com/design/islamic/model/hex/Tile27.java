package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.atan;
import static java.util.Arrays.asList;

public class Tile27 implements Tile {

    private List<List<Point2D>> lines;

    private final double newR;
    private final Point2D centre;

//    private Point2D[] e;

    public Tile27(final Point2D centre, final double r) {

        this.centre = centre;

        lines = newArrayList();




        double d = (1 / 2.0) * atan(HEX_PHI - RECT_PHI_HALF);
        double d2 = r * (HEX_DIST_HEIGHT - d);

        newR = (d2/HEX_DIST_HEIGHT) * HEX_DIST_DAM;
//        double height = d2 * HEX_DIST_HEIGHT;
//        lines.add(buildArrow(edges, edgesRot, 0));

        for (int i = 0; i < HEX_N; i++) {

            lines.add(asList(
                    newEdgeAt(centre, d2, toPhi(i)),
                    newEdgeAt(centre, newR, toPhiRot(i)),
                    newEdgeAt(centre, d2, toPhi(i+1))

            ));

            lines.add(asList(
                    newEdgeAt(centre, r, toPhiRot(i)),
                    newEdgeAt(centre, d2, toPhi(i+1)),
                    newEdgeAt(centre, r, toPhiRot(i+1))


            ));

        }

    }

    private double toPhi(int index) {
        return HEX_RADIANS[toHexIndex(index)];
    }

    private double toPhiRot(int index) {
        return HEX_RADIANS_ROT[toHexIndex(index)];
    }

//    private Point2D e(Point2D centre, double r, int index) {
//        return newEdgeAt(centre, r, HEX_RADIANS_ROT[index % HEX_N]);
//    }
//
//    private Point2D e(Point2D centre, int index) {
//        return newEdgeAt(centre, newR, HEX_RADIANS_ROT[index % HEX_N]);
//    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(
                lines
        );
    }

}
