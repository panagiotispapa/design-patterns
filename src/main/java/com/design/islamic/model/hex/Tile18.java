package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;
import com.design.islamic.model.tiles.Grid;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static com.design.common.PolygonTools.newHexagon;
import static com.design.islamic.model.Payloads.EMPTY;
import static java.util.Arrays.asList;

public class Tile18 implements Tile {
    private List<List<Point2D>> polygons;
    private List<List<Point2D>> lines;

    private final double newR;
    private final Point2D centre;

    public Tile18(final Point2D centre, final double r) {

        this.centre = centre;

        polygons = new ArrayList<>();
        lines = new ArrayList<>();

        newR = r / 3.0;



        List<Point2D> mainLayer = newHexagon(centre, r);
//        polygons.add(mainLayer);

        lines.add(asList(
                mainLayer.get(1),
                mainLayer.get(4),
                mainLayer.get(3),
                mainLayer.get(2),
                mainLayer.get(5),
                mainLayer.get(0),
                mainLayer.get(1)
        ));
//        lines.add(asList(mainLayer.get(2), mainLayer.get(5)));

    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromPolygonsAndLines(
                EMPTY,
                polygons,
                lines,
                EMPTY,
                Grid.Configs.HEX_VER.getConfiguration());
    }

}
