package com.design.islamic.model.hex;

import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.drawPolygon;
import static com.design.common.view.SvgFactory.drawPolygons;
import static com.design.islamic.Patterns.cloneAndTranslateScalePoints;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile3 {

    private final List<Point2D> innerEdges;
    private final List<List<Point2D>> outerRectangles;

    public Tile3(Point2D centre, double r) {

        final double newR = r * HEX_DIST_EQ1;

        innerEdges = cloneAndTranslateScalePoints(centre, newR, hexPoints);

        final double extConfR = r - newR;

        outerRectangles = newArrayList();
        for (int i = 0; i < innerEdges.size(); i++) {
            outerRectangles.add(buildExtConfig(innerEdges.get(i), extConfR, i));
        }

    }

    public List<Point2D> getInnerEdges() {
        return innerEdges;
    }

    public List<List<Point2D>> getOuterRectangles(){
        return outerRectangles;
    }

    public List<XMLBuilder> drawMe(String style, String outerStyle) {

        List<XMLBuilder> out = newArrayList();
        out.add(drawPolygon(getInnerEdges(), style));
        out.addAll(drawPolygons(getOuterRectangles(), outerStyle));

        return out;
    }


    private static List<Point2D> buildExtConfig(final Point2D centre, final double r, int index) {

        List<Point2D> edges = cloneAndTranslateScalePoints(centre, r, hexPoints);
        List<Point2D> heights = cloneAndTranslateScalePoints(centre, r * HEX_DIST_HEIGHT, hexPointsAlt);

        return asList(
                centre,
                heights.get(index),
                edges.get(index),
                heights.get((index + 5) % 6)
        );

    }

}
