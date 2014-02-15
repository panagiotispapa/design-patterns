package com.design.islamic.model.hex;

import com.design.islamic.model.Tile;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.google.common.collect.Lists.newArrayList;

public class Tile6 implements Tile {
    private final List<Point2D> mainHex;
    private final List<List<Point2D>> outerEdges;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);


    public Tile6(Point2D centre, double r) {

        mainHex = cloneAndTranslateScalePoints(centre, r, hexPoints);

        double newR = r * HEX_DIST_DIAGONAL_ROTATED;
        outerEdges = buildOuterLines(
                cloneAndTranslateScalePoints(centre, newR * HEX_DIST_NEW_CENTRE, hexPointsAlt),
                newR

        );

    }

    public static List<List<Point2D>> buildOuterLines(List<Point2D> outerEdges, double r) {
        List<List<Point2D>> out = newArrayList();

        int index = 0;
        for (Point2D outerEdge : outerEdges) {
            List<Point2D> edges = cloneAndTranslateScalePoints(outerEdge, r, hexPoints);
            List<Point2D> innerEdges = cloneAndTranslateScalePoints(outerEdge, r*HEX_DIST_DIAGONAL_ROTATED, hexPoints);


            out.add(
                    Arrays.asList(
                            edges.get(toHexIndex(2 + index)),
                            innerEdges.get(toHexIndex(3 + index)),
                            edges.get(toHexIndex(3 + index)),
                            edges.get(toHexIndex(4 + index)),
                            innerEdges.get(toHexIndex(4 + index)),
                            edges.get(toHexIndex(5 + index))
                    )
            );

            index++;
        }

        return out;
    }


    @Override
    public List<XMLBuilder> drawMe() {
        List<XMLBuilder> out = newArrayList();

//        out.add(drawPolygon(mainHex, styleWhite));
        out.addAll(drawPolylines(outerEdges, styleWhiteBold));


        return out;
    }
}
