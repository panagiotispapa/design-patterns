package com.design.islamic.model.rect;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;

import static com.design.common.PolygonTools.newRectRot;
import static com.design.islamic.model.Payloads.newPayloadFromPolygons;
import static java.util.Arrays.asList;

public class Tile1 implements Tile {


    private final Point2D centre;
    private final double r;

    public Tile1(Point2D centre, double r) {
        this.centre = centre;
        this.r = r;
    }

    @Override
    public Payload getPayload() {
        return newPayloadFromPolygons(asList(newRectRot(centre, r)));
    }
}
