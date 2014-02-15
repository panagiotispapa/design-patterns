package com.design.islamic.model.hex;

import com.google.common.base.Function;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.cloneAndTranslateScalePoints;
import static com.design.common.PolygonTools.hexPoints;
import static com.design.common.view.SvgFactory.drawPolygon;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

public class Tile1 {
    private final List<Point2D> edges;

    public static List<Tile1> fromCentres(final Iterable<Point2D> centres, final double r) {
        return newArrayList(transform(centres, new Function<Point2D, Tile1>() {
            @Override
            public Tile1 apply(Point2D centre) {
                return new Tile1(centre, r);
            }
        }));
    }

    public Tile1(final Point2D centre, final double r) {
        edges = cloneAndTranslateScalePoints(centre, r, hexPoints);
    }

    public List<Point2D> getEdges(){
        return edges;
    }

    public XMLBuilder drawMe(String style) {
        return drawPolygon(edges, style);
    }

}
