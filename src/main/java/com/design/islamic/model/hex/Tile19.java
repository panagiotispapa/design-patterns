package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile19 implements Tile {

    private List<List<Point2D>> lines;

    private final double newR;
    private final Point2D centre;

    public Tile19(final Point2D centre, final double r) {

        this.centre = centre;

        lines = newArrayList();

        newR = r / 5.0;

        int ur = 5;
        int um = 4;
        int ul = 3;
        int dl = 2;
        int dm = 1;
        int dr = 0;

        for (int i = 0; i < HEX_N; i++) {
            Point2D edge1 = p(centre, 2, i);
            Point2D edge2 = p(centre, 3, i);
            Point2D edge3 = p(centre, 4, i);
            Point2D edge4 = p(edge2, 2, dm + i);

            lines.add(asList(
                    p(edge1, 1, ur + i),
                    edge1,
                    p(edge1, 1, dr + i),
                    p(edge1, 1, dm + i),
                    p(edge1, 1, dl + i),
                    p(edge1, 1, ul + i),
                    p(edge1, 1, um + i)
            ));

            lines.add(asList(
                    p(edge2, 1, um + i),
                    p(edge2, 1, ur + i)
            ));

            lines.add(asList(
                    p(edge3, 1, ur + i),
                    p(edge3, 1, um + i),
                    edge3,
                    p(edge3, 1, dl + i),
                    p(edge3, 1, dm + i)
            ));

            lines.add(asList(
                    p(edge4, 1, um + i),
                    p(edge4, 1, ul + i),
                    p(edge4, 1, dl + i),
                    edge4
            ));

        }

    }

    private Point2D p(Point2D centre, int times, int phiIndex) {
        return newEdgeAt(centre, times * newR, HEX_RADIANS_ROT[toHexIndex(phiIndex)]);
    }



    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(
                lines
        );
    }

}
