package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;
import com.design.islamic.model.tiles.Grid;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static com.design.common.PolygonTools.newHexagon;

public class Tile17 implements Tile {
    private List<List<Point2D>> polygons;

    private final double newR;
    private final Point2D centre;

    public Tile17(final Point2D centre, final double r) {

        this.centre = centre;

        polygons = new ArrayList<>();

        newR = r / 3.0;

        List<Point2D> outerLayer = newHexagon(centre, 2 * newR);

//        polygons.add(newHexagon(centre, r));
        polygons.add(newHexagon(centre, newR));

        for (Point2D edge : outerLayer) {

            polygons.add(newHexagon(edge, newR));

        }

    }





    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromPolygons(polygons, Grid.Configs.HEX_VER.getConfiguration());
    }

}
