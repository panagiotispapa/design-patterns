package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Tile;
import com.design.islamic.model.tiles.Grid;

import java.awt.geom.Point2D;

import static com.design.common.PolygonTools.newHexagonRot;
import static com.design.islamic.model.Payloads.newPayloadFromPolygons;
import static java.util.Arrays.asList;

public class Tile1 implements Tile {


    private final Point2D centre;
    private final double r;



    public Tile1(final Point2D centre, final double r) {
        this.centre = centre;
        this.r = r;

    }



    @Override
    public Payload getPayload() {
        return newPayloadFromPolygons(asList(newHexagonRot(centre, r)), Grid.Configs.HEX_VER.getConfiguration());
    }
}
