package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.islamic.model.Payloads.newPayloadFromPolygonsAndLines;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile12 implements Tile {

    private final Point2D centre;
    private final double r;

    private final double newR;

    private List<List<Point2D>> polygons;
    private List<List<Point2D>> lines;

    public Tile12(final Point2D centre, final double r) {
        this.centre = centre;
        this.r = r;

//        newR = ((r * RECT_DIST_HEIGHT) / 6.0)/HEX_DIST_HEIGHT ;
        newR = ((r * RECT_DIST_HEIGHT) / 5.0) ;


        int ur = 5;
        int um = 4;
        int ul = 3;
        int dl = 2;
        int dm = 1;
        int dr = 0;

        Point2D[] u = new Point2D[]{
                e(centre, 1, um),
                e(centre, 2, um),
                e(centre, 3, um),
                e(centre, 4, um)
        };

        Point2D[] d = new Point2D[]{
                e(centre, 1, dm),
                e(centre, 2, dm),
                e(centre, 3, dm),
                e(centre, 4, dm)
        };

        polygons = asList(
//                newHexagonRot(centre, r),
//                newHexStarTileRotated(centre, 2 * newR, HEX_DIST_DIAGONAL)
//                newHexagonRot(centre1, newR),
//                newHexagonRot(centre2, newR),
//                newHexagonRot(centreH1, newR),
//                newHexagonRot(centreH2, newR)

        );

        lines = newArrayList();

        lines.add(asList(
                e(u[0], 6, ul),
                u[0],
                e(u[0], 6, ur)
        ));

        lines.add(asList(
                e(d[0], 6, dl),
                d[0],
                e(d[0], 6, dr)
        ));


        lines.add(asList(
                e(d[0], 6, ur),
                e(centre, 5, ur),
                e(u[1], 5, ur),
                e(u[2], 4, ur),
                e(u[2], 1, dl),
                e(u[0], 1, dl),
                e(u[0], 2, dl),
                e(d[0], 1, ul),
                e(d[1], 1, dl),
                e(d[2], 4, dr),
                e(d[1], 5, dr),
                e(centre, 5, dr),
                e(u[0], 6, dr)
        ));


        lines.add(asList(
                e(d[0], 6, ul),
                e(centre, 5, ul),
                e(u[1], 5, ul),
                e(u[2], 4, ul),
                e(u[2], 1, dr),
                e(u[0], 1, dr),
                e(u[0], 2, dr),
                e(d[0], 1, ur),
                e(d[1], 1, dr),
                e(d[2], 4, dl),
                e(d[1], 5, dl),
                e(centre, 5, dl),
                e(u[0], 6, dl)
        ));


        lines.add(asList(
                e(d[3], 2, dr),
                e(d[1], 2, dr),
                e(centre, 4, dr),
                e(centre, 4, ur),
                e(u[1], 2, ur),
                e(u[3], 2, ur)

                ));

        lines.add(asList(
                e(d[3], 2, dl),
                e(d[1], 2, dl),
                e(centre, 4, dl),
                e(centre, 4, ul),
                e(u[1], 2, ul),
                e(u[3], 2, ul)

                ));



        lines.add(asList(
                e(d[0], 6, ur),
                e(d[0], 2, ur),
                e(u[0], 6, dr)
        ));


        lines.add(asList(
                e(d[0], 6, ul),
                e(d[0], 2, ul),
                e(u[0], 6, dl)
        ));


    }


    private Point2D e(Point2D newCentre, int times, int index) {
        return newEdgeAt(newCentre, times * newR, HEX_RADIANS_ROT.get(index));
    }

    @Override
    public Payload getPayload() {
        return newPayloadFromPolygonsAndLines(
                polygons, lines
        );
    }
}
