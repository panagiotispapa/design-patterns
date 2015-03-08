package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;
import com.design.islamic.model.tiles.Grid;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.cos;
import static java.util.Arrays.asList;

public class Tile29 implements Tile {

    private List<List<Point2D>> lines;

    private final Point2D centre;

//    private Point2D[] e;

    public Tile29(final Point2D centre, final double r) {

        this.centre = centre;

        lines = newArrayList();

        double phi = HEX_PHI - PI_QUARTER;

        double d1 = HEX_DIST_HEIGHT * HEX_DIST_HEIGHT;
        double d2 = 1 - d1;
        double d3 = d2 * cos(PI_QUARTER);
        double d4 = 2 * d3;
        double d5 = 0.5 - d4;
        double d6 = HEX_DIST_HEIGHT - d5;

        double newR = r * d6;
        double newR2 = r * d6 * HEX_DIST_HEX_TO_RECT;

        for (int i = 0; i < HEX_N; i++) {
            Point2D c1 = newEdgeAt(centre, r, toPhiRot(i));

            lines.add(asList(
                    newEdgeAt(centre, newR, toPhi(i)),
                    newEdgeAt(centre, newR2, toPhiRot(i)),
                    newEdgeAt(centre, newR, toPhi(i + 1))

            ));

            lines.add(asList(
                    newEdgeAt(centre, newR, toPhi(i)),
                    newEdgeAt(c1, r * d4, toPhiRot(i + 4))
//                    newEdgeAt(centre, r, toPhiRot(i))

            ));
            lines.add(asList(
                    c1,
                    newEdgeAt(c1, r * d2, toPhiRot(3 + i) - phi),
                    newEdgeAt(c1, r * d4, toPhiRot(2 + i))

//                    newEdgeAt(centre, r, toPhiRot(i))

            ));

        }

    }

    private double toPhi(int index) {
        return HEX_RADIANS[toHexIndex(index)];
    }

    private double toPhiRot(int index) {
        return HEX_RADIANS_ROT[toHexIndex(index)];
    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(
                lines,
                Grid.Configs.HEX_VER.getConfiguration());
    }

}
