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

        List<Point2D> outerLayer1 = newHexagonRot(centre, 2 * newR);
        List<Point2D> outerLayer2 = newHexagonRot(centre, 3 * newR);
        List<Point2D> outerLayer3 = newHexagonRot(centre, 4 * newR);

        for (int i = 0; i < HEX_N; i++) {
            Point2D edge1 = outerLayer1.get(i);
            Point2D edge2 = outerLayer2.get(i);
            Point2D edge3 = outerLayer3.get(i);
            Point2D edge4 = newEdgeAt(edge2, 2 * newR, d(dm, i));

            lines.add(asList(
                    newEdgeAt(edge1, newR, d(ur, i)),
                    edge1,
                    newEdgeAt(edge1, newR, d(dr, i)),
                    newEdgeAt(edge1, newR, d(dm, i)),
                    newEdgeAt(edge1, newR, d(dl, i)),
                    newEdgeAt(edge1, newR, d(ul, i)),
                    newEdgeAt(edge1, newR, d(um, i))
            ));

            lines.add(asList(
                    newEdgeAt(edge2, newR, d(um, i)),
                    newEdgeAt(edge2, newR, d(ur, i))
            ));

            lines.add(asList(
                    newEdgeAt(edge3, newR, d(ur, i)),
                    newEdgeAt(edge3, newR, d(um, i)),
                    edge3,
                    newEdgeAt(edge3, newR, d(dl, i)),
                    newEdgeAt(edge3, newR, d(dm, i))
            ));

            lines.add(asList(
                    newEdgeAt(edge4, newR, d(um, i)),
                    newEdgeAt(edge4, newR, d(ul, i)),
                    newEdgeAt(edge4, newR, d(dl, i)),
                    edge4
//                    newEdgeAt(edge4, newR, d(dm, i))
            ));

        }

    }

    private static double d(int phiIndex, int index) {
        return HEX_RADIANS_ROT[toHexIndex(phiIndex + index)];
    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(
                lines
        );
    }

}
