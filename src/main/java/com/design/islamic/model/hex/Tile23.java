package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile23 implements Tile {

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

    private final double DIAG = HEX_DIST_DIAGONAL_ROTATED;

    public Tile23(final Point2D centre, final double r) {

        this.centre = centre;

        lines = newArrayList();

        double newH = r / 2.0;
        newR = newH / HEX_DIST_HEIGHT;

        List<Point2D> edges = newHexagon(centre, newR);
        List<Point2D> edgesRot = newHexagonRot(centre, r);

//        lines.add(buildArrow(edges, edgesRot, 0));

        for (int i = 0; i < HEX_N; i++) {
            lines.add(buildArrow(edges, edgesRot, i));
        }

    }

    private List<Point2D> buildArrow(List<Point2D> edges, List<Point2D> edgesRot, int index) {
        return asList(
                edgesRot.get(index),
                edges.get(toHexIndex(index + 1)),
                edgesRot.get(toHexIndex(index + 1))
        );
    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(
                lines
        );
    }

}
