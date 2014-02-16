package com.design.islamic.model.hex;

import com.design.islamic.model.Tile;
import com.google.common.base.Function;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.design.common.view.SvgFactory.WHITE;
import static com.design.common.view.SvgFactory.newStyle;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile4 implements Tile {

    private final List<Point2D> mainRect;
    private final List<List<Point2D>> outerRectangles;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    public static List<Tile4> fromCentres(final Iterable<Point2D> centres, final double r) {
        return newArrayList(transform(centres, new Function<Point2D, Tile4>() {
            @Override
            public Tile4 apply(Point2D centre) {
                return new Tile4(centre, r);
            }
        }));
    }

    public Tile4(Point2D centre, double r) {

        mainRect = newHexagonRot(centre, r);

        final double newR = r * HEX_DIST_DIAGONAL;

        //innerEdges = cloneAndTranslateScalePoints(centre, newR/Math.cos(HEX_PHI/2.0), hexPoints);

        final double extConfR = newR * HEX_DIST_DIAGONAL;

        outerRectangles = buildExtConf(centre, extConfR);

    }


    public List<List<Point2D>> getOuterRectangles() {
        return outerRectangles;
    }

    @Override
    public List<XMLBuilder> drawMe() {

        List<XMLBuilder> out = newArrayList();
//        out.add(drawPolygon(mainRect, styleWhite));

//        out.add(drawPolygon(getInnerEdges(), style));
        out.addAll(drawPolylines(getOuterRectangles(), styleWhiteBold));

        return out;
    }

    public static List<List<Point2D>> buildExtConf(Point2D centre, double r) {

        List<Point2D> layerExtEdges = newHexagon(centre, r * HEX_DIST_NEW_CENTRE);

        List<List<Point2D>> out = newArrayList();

        int k = 0;

        for (Point2D layerExtEdge : layerExtEdges) {
            List<Point2D> layerExtPolEdges = newHexagonRot(layerExtEdge, r);

            out.add(asList(
                    layerExtPolEdges.get((k) % 6),
                    layerExtPolEdges.get((1 + k) % 6),
                    layerExtPolEdges.get((4 + k) % 6),
                    layerExtPolEdges.get((5 + k) % 6)

            ));

            k++;

        }

        return out;

    }

}
