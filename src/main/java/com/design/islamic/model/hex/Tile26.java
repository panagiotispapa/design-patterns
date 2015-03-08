package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;
import com.design.islamic.model.tiles.Grid;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile26 implements Tile {

    private List<List<Point2D>> lines;

    private final double newR;
    private final Point2D centre;

//    private Point2D[] e;

    public Tile26(final Point2D centre, final double r) {

        this.centre = centre;

        lines = newArrayList();

        double height = r * HEX_DIST_HEIGHT;

        newR = r * HEX_DIST_DAM;
//        lines.add(buildArrow(edges, edgesRot, 0));

        for (int i = 0; i < HEX_N; i++) {

            lines.add(asList(
                    newEdgeAt(centre, height, toPhi(i)),
                    newEdgeAt(centre, newR, toPhiRot(i)),
                    newEdgeAt(centre, height, toPhi(i+1))
//                    newEdgeAt(centre, height, toPhiRot(i + 1))

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
                lines,
                Grid.Configs.HEX_VER.getConfiguration());
    }

}
