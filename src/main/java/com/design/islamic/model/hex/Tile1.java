package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Tile;
import com.google.common.base.Function;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.WHITE;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Payloads.newPayloadFromPolygons;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile1 implements Tile {
    private final List<Point2D> edges;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);

    private final Point2D centre;
    private final double r;

    public static List<Tile1> fromCentres(final Iterable<Point2D> centres, final double r) {
        return newArrayList(transform(centres, new Function<Point2D, Tile1>() {
            @Override
            public Tile1 apply(Point2D centre) {
                return new Tile1(centre, r);
            }
        }));
    }

    public Tile1(final Point2D centre, final double r) {
        this.centre = centre;
        this.r = r;

        edges = cloneAndTranslateScalePoints(centre, r, hexPointsAlt);
    }

    public List<Point2D> getEdges(){
        return edges;
    }




    @Override
    public Payload getPayload() {
        return newPayloadFromPolygons(asList(newHexagonRot(centre, r)));
    }
}
