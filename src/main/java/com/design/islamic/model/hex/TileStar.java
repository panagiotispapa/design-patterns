package com.design.islamic.model.hex;

import com.design.common.PolygonTools;
import com.design.islamic.model.Tile;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.newHexStarTile;
import static com.design.common.PolygonTools.newHexStarTileRotated;
import static com.design.common.view.SvgFactory.*;
import static java.util.Arrays.asList;

public class TileStar implements Tile{

    private final List<Point2D> edges;


    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);


    public TileStar(final Point2D centre, final double r, final double dist) {

        edges = newHexStarTileRotated(centre, r, dist);

    }

    public List<Point2D> getEdges() {
        return edges;
    }

    @Override
    public List<XMLBuilder> drawMe() {
        return asList(drawPolygon(edges, styleWhiteBold))

                ;
    }

    public XMLBuilder drawMe(String style) {
        return drawPolygon(edges, style);
    }

}
