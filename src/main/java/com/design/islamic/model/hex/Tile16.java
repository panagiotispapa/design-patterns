package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;
import com.design.islamic.model.tiles.Grid;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static com.design.common.PolygonTools.HEX_RADIANS_ROT;
import static com.design.common.PolygonTools.newEdgeAt;
import static java.util.Arrays.asList;

public class Tile16 implements Tile {
    private List<List<Point2D>> lines;

    private final double newR;
    private final Point2D centre;

    public Tile16(final Point2D centre, final double r) {

        this.centre = centre;

        lines = new ArrayList<>();

        newR = r / 9.0;

        double ur = HEX_RADIANS_ROT[5];
        double um = HEX_RADIANS_ROT[4];
        double ul = HEX_RADIANS_ROT[3];
        double dl = HEX_RADIANS_ROT[2];
        double dm = HEX_RADIANS_ROT[1];
        double dr = HEX_RADIANS_ROT[0];

        lines.add(asList(
                e(centre, 1, ul),
                e(e(6, um), 1, dl),
                e(e(6, um), 3, dl),
                e(e(8, um), 5, dl),
                e(centre, 8, um)
        ));

        lines.add(asList(
                e(centre, 1, ur),
                e(e(6, um), 1, dr),
                e(e(6, um), 3, dr),
                e(e(8, um), 5, dr),
                e(centre, 8, um)
        ));


        lines.add(asList(
                e(e(9, dm), 1, ul),
                e(e(3, dm), 1, dl),
                e(e(3, dm), 3, dl),
                e(e(1, dm), 5, dl),
                e(centre, 1, dm)
        ));

        lines.add(asList(
                e(e(9, dm), 1, ur),
                e(e(3, dm), 1, dr),
                e(e(3, dm), 3, dr),
                e(e(1, dm), 5, dr),
                e(centre, 1, dm)
        ));





        lines.add(asList(
                e(e(1, um), 8, ul),
                e(e(1, um), 3, ul),
                e(e(3, um), 3, ul)
        ));

        lines.add(asList(
                e(e(1, um), 8, ur),
                e(e(1, um), 3, ur),
                e(e(3, um), 3, ur)
        ));


        lines.add(asList(
                e(e(8, dm), 8, ul),
                e(e(8, dm), 3, ul),
                e(e(6, dm), 3, ul)
        ));

        lines.add(asList(
                e(e(8, dm), 8, ur),
                e(e(8, dm), 3, ur),
                e(e(6, dm), 3, ur)
        ));


        lines.add(asList(
                e(centre, 1, ur),
                e(e(1, ur), 5, dr),
                e(e(3, ur), 3, dr),
                e(e(3, ur), 1, dr),
                e(e(8, ur), 1, dr)
        ));

        lines.add(asList(
                e(centre, 1, ul),
                e(e(1, ul), 5, dl),
                e(e(3, ul), 3, dl),
                e(e(3, ul), 1, dl),
                e(e(8, ul), 1, dl)
        ));


        lines.add(asList(
                e(e(3, ur), 3, dr),
                e(e(5, ur), 3, dr),
                e(centre, 8, dr)
        ));

        lines.add(asList(
                e(e(3, ul), 3, dl),
                e(e(5, ul), 3, dl),
                e(centre, 8, dl)
        ));





    }

    private Point2D e(Point2D newCentre, int times, double phi) {
        return newEdgeAt(newCentre, times * newR, phi);
    }

    private Point2D e(int times, double phi) {
        return newEdgeAt(centre, times * newR, phi);
    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(lines, Grid.Configs.HEX_VER.getConfiguration());
    }

}
