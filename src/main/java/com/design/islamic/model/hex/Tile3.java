package com.design.islamic.model.hex;

import com.design.islamic.model.Tile;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile3 implements Tile {

    private final List<Point2D> innerEdges;
    private final List<Point2D> innerStar;
    private final List<List<Point2D>> outerRectangles;
    private final List<List<Point2D>> outerLines;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    public Tile3(Point2D centre, double r) {

        final double newR = r * HEX_DIST_EQ1;

        innerEdges = cloneAndTranslateScalePoints(centre, newR, hexPointsAlt);
        innerStar = newHexStarTileRotated(centre, newR, HEX_DIST_DIAGONAL);

        final double extConfR = r - newR;

        outerRectangles = buildExtConfig(innerEdges, extConfR);
        outerLines = buildSecondExtConfig(centre, r);

    }

    public List<Point2D> getInnerEdges() {
        return innerEdges;
    }

    public List<List<Point2D>> getOuterRectangles() {
        return outerRectangles;
    }

    @Override
    public List<XMLBuilder> drawMe() {

        List<XMLBuilder> out = newArrayList();
        out.add(drawPolygon(innerEdges, styleWhite));
        out.addAll(drawPolylines(outerRectangles, styleWhite));
        out.add(drawPolygon(innerStar, styleWhiteBold));
        out.addAll(drawPolylines(outerLines, styleWhiteBold));

        return out;
    }

    private static List<List<Point2D>> buildExtConfig(final List<Point2D> centres, final double r) {

        List<List<Point2D>> out = newArrayList();
        //List<Point2D> edges = cloneAndTranslateScalePoints(centre, r, hexPoints);

        double newR = r * HEX_DIST_HEIGHT;

        int index = 0;
        for (Point2D centre : centres) {

            out.add(
                    asList(
                            newHexEdge(centre, newR, (1 + index)),
                            centre,
                            newHexEdge(centre, newR, (index))
                    )
            );
            index++;
        }

        return out;

    }

    public static List<List<Point2D>> buildSecondExtConfig(Point2D centreMain, final double r) {
        final double newR = r * HEX_DIST_EQ1;
        double extConfR = r - newR;
        final double newRHalf = newR * 0.5;

        double extConfRHeight = extConfR * HEX_DIST_HEIGHT;
        double extConfRDiag = extConfR * HEX_DIST_DIAGONAL;

        List<Point2D> innerEdges = newHexagonRot(centreMain, newR);
        List<Point2D> edgesInnerHeights = newHexagon(centreMain, newR * HEX_DIST_HEIGHT);

        List<List<Point2D>> out = newArrayList();
        int index = 0;

        for (Point2D centre : innerEdges) {

            Point2D centre1 = innerEdges.get(toHexIndex(5 + index));
            Point2D centre2 = edgesInnerHeights.get(toHexIndex(index));

            out.add(asList(
                    newHexEdge(centre1, extConfRHeight, index),
                    newHexEdgeRot(centre2, extConfRDiag, (5 + index)),

                    newHexEdgeRot(centre, newRHalf, (4 + index)),
                    newHexEdgeRot(centre2, extConfRDiag, (index)),
                    newHexEdge(centre, extConfRHeight, (0 + index)),
                    newHexEdge(centre, extConfRHeight, (1 + index))

//                    layer5.get((index) % 6),
//                    layer1.get((2 + index) % 6)

            ));
//            out.add(asList(
//                    layer1.get((4 + index) % 6),
//                    layer4.get(toHexIndex(5+index))
//                    ));

            index++;
        }

        return out;

    }

}
