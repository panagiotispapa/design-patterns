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

public class Tile21 implements Tile {

    private List<List<Point2D>> lines;

    private final double newR;
    private final double newR2;
    private final Point2D centre;

    private static int MR = 0;
    private static int DR = 1;
    private static int DL = 2;
    private static int ML = 3;
    private static int UL = 4;
    private static int UR = 5;

    public Tile21(final Point2D centre, final double r) {

        this.centre = centre;

        lines = newArrayList();

        newR = r / 6.0;
        newR2 = r / 18.0;

        for (int i = 0; i < HEX_N; i++) {
            Point2D c1 = p(centre, 1, MR + i);
            Point2D c2 = p(centre, 2, MR + i);
            Point2D c3 = p(centre, 3, MR + i);
            Point2D c4 = p(centre, 4, MR + i);
            Point2D c5 = p(centre, 5, MR + i);

            lines.add(asList(
                    p(centre, 1, MR + i),
                    p(c3, 2, UL + i),
                    p(c4, 2, UL + i),
                    p(c3, 1, UL + i),
                    p(centre, 1, UR + i)
            ));

            lines.add(asList(
                    p(p(centre, 3, UR + i), 1, DR + i),
                    p(centre, 3, UR + i),
                    p2(p2(centre, 9, UR + i), 5, MR + i),
                    p2(p2(centre, 11, UR + i), 3, MR + i),
                    p2(p2(centre, 11, UR + i), 1, MR + i),
                    p2(p2(centre, 18, UR + i), 1, DR + i)
            ));

            lines.add(asList(
                    p(c3, 1, UL + i),
                    c3,
                    p2(p2(centre, 9, MR + i), 5, UR + i),
                    p2(p2(centre, 11, MR + i), 3, UR + i),
                    p2(p2(centre, 11, MR + i), 1, UR + i),
                    p2(p2(centre, 18, MR + i), 1, UL + i)
            ));


            lines.add(asList(
                    p2(p2(centre, 11, MR + i), 3, UR + i),
                    p2(p2(centre, 16, MR + i), 3, UL + i),
                    p2(p2(centre, 16, MR + i), 13, UL + i),
                    p2(p2(centre, 14, MR + i), 11, UL + i)
            ));
        }

    }

    private Point2D p(Point2D centre, int times, int phiIndex) {
        return newEdgeAt(centre, times * newR, HEX_RADIANS[toHexIndex(phiIndex)]);
    }

    private Point2D p2(Point2D centre, int times, int phiIndex) {
        return newEdgeAt(centre, times * newR2, HEX_RADIANS[toHexIndex(phiIndex)]);
    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(
                lines,
                Grid.Configs.HEX_VER.getConfiguration());
    }

}
