package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;
import com.design.islamic.model.tiles.Grid;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.PI;
import static java.lang.Math.tan;
import static java.util.Arrays.asList;

public class Tile28 implements Tile {

    private List<List<Point2D>> lines;


    private final Point2D centre;

//    private Point2D[] e;

    public Tile28(final Point2D centre, final double r) {

        this.centre = centre;

        lines = newArrayList();

        double phi = (PI - 2 * HEX_PHI) / 2.0;
        double d1 = tan(phi) * 0.5;
        double d2 = HEX_DIST_HEIGHT - d1;

        double d3 = 0.5-d1;

        double newR = r * d2;
        double newR2 = r * d2*HEX_DIST_HEX_TO_RECT;

        for (int i = 0; i < HEX_N; i++) {

            lines.add(asList(
                    newEdgeAt(centre, newR, toPhiRot(i)),
                    newEdgeAt(centre, newR2, toPhi(i + 1)),
                    newEdgeAt(centre, newR, toPhiRot(i + 1))

            ));

            lines.add(asList(
                    newEdgeAt(newEdgeAt(centre, r, toPhi(i)), d3*r, toPhi(i+2)),
                    newEdgeAt(centre, newR2, toPhi(i+1))
            ));

            lines.add(asList(
                    newEdgeAt(newEdgeAt(centre, r, toPhi(i)), d3*r, toPhi(i+4)),
                    newEdgeAt(centre, newR2, toPhi(i+5))
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
