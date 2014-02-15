package com.design.islamic.model.hex;

import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.cloneAndTranslateScalePoints;
import static com.design.common.PolygonTools.hexPoints;
import static com.design.common.PolygonTools.hexPointsAlt;
import static com.design.common.view.SvgFactory.drawPolygon;
import static com.design.islamic.GenericTools.concatEdges;

public class TileStar {

    private final List<Point2D> edges;

    public TileStar(final Point2D centre, final double r, final double dist) {

        edges = concatEdges(
                cloneAndTranslateScalePoints(centre, r, hexPoints),
                cloneAndTranslateScalePoints(centre, r*dist, hexPointsAlt)
        );
    }

    public List<Point2D> getEdges() {
        return edges;
    }


    public XMLBuilder drawMe(String style) {
        return drawPolygon(edges, style);
    }

}
