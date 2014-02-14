package com.design.islamic.model.hex;

import com.google.common.base.Function;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.drawPolygon;
import static com.design.common.view.SvgFactory.drawPolylines;
import static com.design.islamic.Patterns.cloneAndTranslateScalePoints;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile4 {

    private final List<Point2D> mainRect;
    private final List<List<Point2D>> outerRectangles;

    public static List<Tile4> fromCentres(final Iterable<Point2D> centres, final double r) {
        return newArrayList(transform(centres, new Function<Point2D, Tile4>() {
            @Override
            public Tile4 apply(Point2D centre) {
                return new Tile4(centre, r);
            }
        }));
    }

    public Tile4(Point2D centre, double r) {

        mainRect = cloneAndTranslateScalePoints(centre, r, hexPoints);

        final double newR = r * HEX_DIST_DIAGONAL;

        //innerEdges = cloneAndTranslateScalePoints(centre, newR/Math.cos(HEX_PHI/2.0), hexPoints);

        final double extConfR = newR * HEX_DIST_DIAGONAL;

        outerRectangles = buildExtConf(centre, extConfR);

    }


    public List<List<Point2D>> getOuterRectangles() {
        return outerRectangles;
    }

    public List<XMLBuilder> drawMe(String style, String mainStyle) {

        List<XMLBuilder> out = newArrayList();
        out.add(drawPolygon(mainRect, mainStyle));

//        out.add(drawPolygon(getInnerEdges(), style));
        out.addAll(drawPolylines(getOuterRectangles(), style));

        return out;
    }

    public static List<List<Point2D>> buildExtConf(Point2D centre, double r) {

        List<Point2D> layerExtEdges = cloneAndTranslateScalePoints(centre, r * HEX_DIST_NEW_CENTRE, hexPointsAlt);

        List<List<Point2D>> out = newArrayList();

        int k = 0;

        for (Point2D layerExtEdge : layerExtEdges) {
            List<Point2D> layerExtPolEdges = cloneAndTranslateScalePoints(layerExtEdge, r, hexPoints);

            out.add(asList(
                    layerExtPolEdges.get((1 + k) % 6),
                    layerExtPolEdges.get((2 + k) % 6),
                    layerExtPolEdges.get((5 + k) % 6),
                    layerExtPolEdges.get((0 + k) % 6)

            ));

            k++;

        }

        return out;

    }

}
