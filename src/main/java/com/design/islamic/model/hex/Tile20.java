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

public class Tile20 implements Tile {

    private List<List<Point2D>> lines;

    private final double newR;
    private final Point2D centre;

    private static int MR = 0;
    private static int DR = 1;
    private static int DL = 2;
    private static int ML = 3;
    private static int UL = 4;
    private static int UR = 5;

    public Tile20(final Point2D centre, final double r) {

        this.centre = centre;

        lines = newArrayList();

        newR = r / 6.0;

        for (int i = 0; i < HEX_N; i++) {
            lines.add(newArrow(centre, i));
            Point2D edge = p(centre, 6, i);
            lines.add(newArrow(edge, i + ML));
            lines.add(newArrow(edge, i + DL));

        }

    }

    private List<Point2D> newArrow(Point2D centre, int phiIndex) {

        Point2D c1 = p(centre, 2, phiIndex);
        Point2D c2 = p(centre, 3, phiIndex);

        return asList(
                p(c1, 1, UR + phiIndex),
                p(c1, 2, DL + phiIndex),
                p(c2, 2, DL + phiIndex),
                p(c2, 1, DL + phiIndex),
                p(c1, 2, DR + phiIndex),
                p(c2, 2, DR + phiIndex),
                p(c2, 1, UL + phiIndex)
        );

    }

    private Point2D p(Point2D centre, int times, int phiIndex) {
        return newEdgeAt(centre, times * newR, HEX_RADIANS[toHexIndex(phiIndex)]);
    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(
                lines,
                Grid.Configs.HEX_VER.getConfiguration());
    }

}
