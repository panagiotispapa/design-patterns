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

        int index = 0;
        for (Point2D centre : centres) {
            List<Point2D> heights = cloneAndTranslateScalePoints(centre, r * HEX_DIST_HEIGHT, hexPoints);
            out.add(
                    asList(
                            heights.get(toHexIndex(1+index) ),
                            centre,
                            heights.get(toHexIndex(0+index) )
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

        List<Point2D> innerEdges = cloneAndTranslateScalePoints(centreMain, newR, hexPointsAlt);
        List<Point2D> edgesInnerHeights = cloneAndTranslateScalePoints(centreMain, newR * HEX_DIST_HEIGHT, hexPoints);

        List<List<Point2D>> out = newArrayList();
        int index = 0;

        for (Point2D centre : innerEdges) {

            List<Point2D> layer1 = cloneAndTranslateScalePoints(centre, newRHalf, hexPointsAlt);

            List<Point2D> layer3 = cloneAndTranslateScalePoints(centre, extConfR * HEX_DIST_HEIGHT, hexPoints);
            List<Point2D> layer5 = cloneAndTranslateScalePoints(innerEdges.get(toHexIndex(5 + index)), extConfR * HEX_DIST_HEIGHT, hexPoints);

            List<Point2D> layer4 = cloneAndTranslateScalePoints(edgesInnerHeights.get(toHexIndex(0 + index)),  extConfR*HEX_DIST_DIAGONAL, hexPointsAlt);

            out.add(asList(
                    layer5.get(toHexIndex(0+index)),
                    layer4.get(toHexIndex(5+index)),

                    layer1.get((4 + index) % 6),
                    layer4.get(toHexIndex(index)),
                    layer3.get(toHexIndex(0+index)),
                    layer3.get(toHexIndex(1+index))


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
