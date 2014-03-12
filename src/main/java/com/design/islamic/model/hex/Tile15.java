package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.sin;
import static java.util.Arrays.asList;

public class Tile15 implements Tile {

    public static double RATIO_W = 2 * HEX_DIST_HEIGHT;

    private List<List<Point2D>> lines;

    private final double newR;
//    private final double newR2;


    public Tile15(final Point2D centre, final double r) {

        lines = newArrayList();


        newR = (r * sin(PI_QUARTER)) / 2.5;

        Point2D urCentre = newEdgeAt(centre, 5 * newR, HEX_RADIANS_ROT.get(5));
        Point2D ulCentre = newEdgeAt(centre, 5 * newR, HEX_RADIANS_ROT.get(3));
        Point2D dlCentre = newEdgeAt(centre, 5 * newR, HEX_RADIANS_ROT.get(2));
        Point2D drCentre = newEdgeAt(centre, 5 * newR, HEX_RADIANS_ROT.get(0));

        for (int i = 0; i < HEX_N; i++) {
            lines.add(buildLowerPart(centre, i));
            lines.add(buildUpperPart(centre, i));
        }

        lines.add(buildLowerPart(urCentre, 2));
        lines.add(buildUpperPart(urCentre, 2));
        lines.add(buildUpperPart(urCentre, 3));

        lines.add(buildLowerPart(ulCentre, 1));
        lines.add(buildUpperPart(ulCentre, 1));
        lines.add(buildLowerPart(ulCentre, 0));

        lines.add(buildLowerPart(dlCentre, 5));
        lines.add(buildUpperPart(dlCentre, 5));
        lines.add(buildUpperPart(dlCentre, 0));

        lines.add(buildLowerPart(drCentre, 4));
        lines.add(buildUpperPart(drCentre, 4));
        lines.add(buildLowerPart(drCentre, 3));


    }

    private List<Point2D> buildLowerPart(Point2D centre, int index) {

        Point2D newCentre = newEdgeAt(centre, newR * HEX_DIST_NEW_CENTRE, HEX_RADIANS.get(index));

        return asList(
                newEdgeAt(centre, newR * HEX_DIST_DIAGONAL, HEX_RADIANS.get(index)),
                newEdgeAt(centre, newR, HEX_RADIANS_ROT.get(index)),
                newEdgeAt(newCentre, newR, HEX_RADIANS_ROT.get(index)),
                newEdgeAt(newCentre, newR * HEX_DIST_DIAGONAL, HEX_RADIANS.get(index))
        );

    }

    private List<Point2D> buildUpperPart(Point2D centre, int index) {

        Point2D newCentre = newEdgeAt(centre, newR * HEX_DIST_NEW_CENTRE, HEX_RADIANS.get(index));

        return asList(
                newEdgeAt(centre, newR * HEX_DIST_DIAGONAL, HEX_RADIANS.get(index)),
                newEdgeAt(centre, newR, HEX_RADIANS_ROT.get(toHexIndex(index + 5))),
                newEdgeAt(newCentre, newR, HEX_RADIANS_ROT.get(toHexIndex(index + 5))),
                newEdgeAt(newCentre, newR * HEX_DIST_DIAGONAL, HEX_RADIANS.get(index))
        );

    }


    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(lines);
    }
}
