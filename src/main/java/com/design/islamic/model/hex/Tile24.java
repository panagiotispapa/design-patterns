package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile24 implements Tile {

    private List<List<Point2D>> lines;

    private final double newR;
    private final Point2D centre;

    private final int ur = 5;
    private final int um = 4;
    private final int ul = 3;
    private final int dl = 2;
    private final int dm = 1;
    private final int dr = 0;

//    private Point2D[] e;

    public Tile24(final Point2D centre, final double r) {

        this.centre = centre;

        lines = newArrayList();

        newR = r / 3.0;

        double d1 = Math.cos(HEX_PHI_HALF) * newR * HEX_DIST_HEIGHT;
        double d2 = newR - d1;

//        lines.add(buildArrow(edges, edgesRot, 0));

        for (int i = 0; i < HEX_N; i++) {
            Point2D c1 = e(centre, newR, i);
            Point2D c2 = e(centre, 2 * newR, i);
            Point2D c3 = e(centre, 3 * newR, i);

            lines.add(asList(
                    e(c1, dm + i),
                    e(c1, dl + i),
                    e(c1, ur + i),
                    e(c1, um + i),
                    e(c1, dm + i)
            ));

            lines.add(asList(
                    e(c2, dl + i),
                    e(c2, dm + i),
                    e(c2, d2, dm + i),
                    e(c3, d2, um + i)
            ));

            lines.add(asList(
                    e(c2, um + i),
                    e(c2, ur + i),
                    e(c2, d2, ur + i),
                    e(c3, d2, dl + i)
            ));

        }

    }

    private Point2D e(Point2D centre, double r, int index) {
        return newEdgeAt(centre, r, HEX_RADIANS_ROT[index % HEX_N]);
    }

    private Point2D e(Point2D centre, int index) {
        return newEdgeAt(centre, newR, HEX_RADIANS_ROT[index % HEX_N]);
    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(
                lines
        );
    }

}
