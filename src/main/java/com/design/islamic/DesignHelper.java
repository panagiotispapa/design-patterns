package com.design.islamic;

import com.design.islamic.model.hex.Tile4;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.GenericTools.*;
import static com.design.islamic.Patterns.*;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.cos;
import static java.util.Arrays.asList;

public class DesignHelper {

    public static List<XMLBuilder> newStarDesign1(final Point2D centre, final double r) {
        ImmutableList.Builder<XMLBuilder> builder = ImmutableList.builder();

        List<Point2D> edges = Patterns.cloneAndTranslateScalePoints(centre, r, hexPoints);

        final List<Point2D> outsideCentres = cloneAndTranslateScalePoints(centre, r * HEX_DIST_NEW_CENTRE, hexPointsAlt);

        List<Point2D> pointsLayerMiddle = cloneAndTranslateScalePoints(centre, 0.5 * r, hexPoints);

        List<Point2D> edgesAltLayer1 = cloneAndTranslateScalePoints(centre, r * HEX_DIST_OUTER_CIRCLE, hexPointsAlt);

        List<Point2D> edgesAltLayer2 = cloneAndTranslateScalePoints(centre, r * HEX_DIST_DIAGONAL, hexPointsAlt);

        List<Point2D> edgesAltLayer3 = cloneAndTranslateScalePoints(centre, r * HEX_DIST3, hexPointsAlt);

        String black = newStyle("black", 2, 1);
        String blue = newStyle("blue", 1, 1);
        final String gray = newStyle("gray", 1, 1);
        final String green = newStyle("green", 1, 1);
        builder.add(newCircle(centre, r, black));
//        builder.add(drawPolygon(edges, blue));
        builder.addAll(highlightPoints(outsideCentres));
        builder.add(drawPolygon(outsideCentres, gray));

        builder.addAll(transform(outsideCentres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newCircle(centre, r, gray);
            }
        }));

        builder.addAll(transform(outsideCentres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D endPoint) {
                return newPolyline(asList(centre, endPoint), gray);
            }
        }));

//

        builder.addAll(drawPolylines(generateCombsOfPoints(edges), gray));

        builder.add(drawPolygon(edgesAltLayer1, gray));
        builder.add(drawPolygon(pointsLayerMiddle, gray));

        builder.add(drawPolygon(newHexStarTile(centre, r, HEX_DIST_OUTER_CIRCLE), green));
        builder.add(drawPolygon(newHexStarTile(centre, r, HEX_DIST_DIAGONAL), green));
        builder.add(drawPolygon(newHexStarTile(centre, r, HEX_DIST3), green));

        builder.addAll(highlightPoints(edgesAltLayer1));
        builder.addAll(highlightPoints(edgesAltLayer2));
        builder.addAll(highlightPoints(edgesAltLayer3));

        return builder.build();

    }

    public static List<XMLBuilder> newStarDesign2(final Point2D centre, final double r) {

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);

        final double newR = r * cos(HEX_PHI / 2);

        List<Point2D> edges = Patterns.cloneAndTranslateScalePoints(centre, r, hexPoints);

        List<Point2D> edgesAltLayer2 = clonePoints(hexPointsAlt);

        scalePoints(edgesAltLayer2, r * HEX_DIST_HEIGHT);
        translatePoints(edgesAltLayer2, centre);

        List<List<Point2D>> layer2 = generateCombsOfPoints(edgesAltLayer2);

        List<List<Point2D>> layer3 = asList(
                asList(edges.get(0), edges.get(3)),
                asList(edges.get(1), edges.get(4)),
                asList(edges.get(2), edges.get(5))
        );

        List<Point2D> edgesAltLayer4 = clonePoints(hexPoints);

        scalePoints(edgesAltLayer4, newR * HEX_DIST_DIAGONAL);
        translatePoints(edgesAltLayer4, centre);

        List<Point2D> edgesLayer5 = concatEdges(edgesAltLayer4, edgesAltLayer2);

        out.add(drawPolygon(edges, gray));
        out.addAll(drawPolylines(layer2, gray));
        out.addAll(drawPolylines(layer3, gray));
        out.add(drawPolygon(edgesLayer5, green));

        out.addAll(highlightPoints(edges));
        out.addAll(highlightPoints(edgesAltLayer2));
        out.addAll(highlightPoints(edgesAltLayer4));

        return out;

    }

    public static List<XMLBuilder> newStarDesign3(final Point2D centre, final double r) {

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);

//        double newHeight = HEX_DIST_HEIGHT * 0.5;
//        double newHeight = HEX_DIST_EQ1*cos(HEX_PHI/2.0);

        final double newR = r * HEX_DIST_EQ1;
        double newHeight = HEX_DIST_EQ1 * HEX_DIST_HEIGHT;
        List<Point2D> edges = Patterns.cloneAndTranslateScalePoints(centre, r, hexPoints);

        List<Point2D> edgesAltLayer2 = clonePoints(hexPointsAlt);

        scalePoints(edgesAltLayer2, r * newHeight);
        translatePoints(edgesAltLayer2, centre);

        List<Point2D> innerEdges = cloneAndTranslateScalePoints(centre, newR, hexPoints);

//        List<Point2D> insideStar = buildStarRotatedEdges(centre, newR, HEX_DIST_DIAGONAL);

        List<Point2D> edgesInnerHeights = cloneAndTranslateScalePoints(centre, newR * HEX_DIST_HEIGHT, hexPointsAlt);

        double extConfR = r - newR;

        List<Point2D> edgesLayer8 = cloneAndTranslateScalePoints(innerEdges.get(0), extConfR, hexPoints);

        List<Point2D> heightsEdgesLayer8 = cloneAndTranslateScalePoints(innerEdges.get(0), extConfR * HEX_DIST_HEIGHT, hexPointsAlt);

        List<List<Point2D>> heightsLayer8 = buildLines(innerEdges.get(0),
                heightsEdgesLayer8);

        List<List<Point2D>> extConfigs = newArrayList();
        for (int i = 0; i < innerEdges.size(); i++) {
            extConfigs.add(buildExtConfigForDesign3(innerEdges.get(i), extConfR, i));
        }

        out.add(drawPolygon(edges, gray));
//        out.add(drawPolygon(innerEdges, gray));

//        out.add(drawPolygon(insideStar, gray));
//        out.add(drawPolygon(polygon1, gray));
//        out.add(drawPolygon(polygon2, gray));

//        out.addAll(drawPolygons(
//                extPolygons,
//                gray));

        out.add(drawPolygon(
                innerEdges, gray));

//        out.add(drawPolygon(
//                edgesLayer8,gray));

        out.addAll(drawPolygons(
                extConfigs, gray));

        System.out.println(5 % 6);
        System.out.println(6 % 6);
        System.out.println(7 % 6);
        System.out.println(8 % 6);

//        out.addAll(drawPolylines(layerExt, gray));

//        out.addAll(highlightPoints(edges));
//        out.addAll(highlightPoints(edgesAltLayer2));
        out.addAll(highlightPoints(innerEdges));
        out.addAll(highlightPoints(edgesInnerHeights));
        out.addAll(highlightPoints(extConfigs.get(0)));
//        out.addAll(highlightPoints(heightsEdgesLayer8));

        return out;

    }

    private static List<Point2D> buildExtConfigForDesign3(final Point2D centre, final double r, int index) {

        List<Point2D> edges = cloneAndTranslateScalePoints(centre, r, hexPoints);
        List<Point2D> heights = cloneAndTranslateScalePoints(centre, r * HEX_DIST_HEIGHT, hexPointsAlt);

        return asList(
                centre,
                heights.get(index),
                edges.get(index),
                heights.get((index + 5) % 6)
        );

    }

    public static List<XMLBuilder> newStarDesign4(final Point2D centre, final double r) {

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 1, 1);
        final String red = newStyle(RED, 1, 1);

        List<Point2D> layer1Edges = cloneAndTranslateScalePoints(centre, r, hexPoints);
        double layer2r = r * HEX_DIST_DIAGONAL;
        List<Point2D> layer2Edges = cloneAndTranslateScalePoints(centre, layer2r, hexPointsAlt);
        double layer3r = layer2r * HEX_DIST_DIAGONAL;
        List<Point2D> layer3Edges = cloneAndTranslateScalePoints(centre, layer3r, hexPoints);

        List<Point2D> layerExtEdges = cloneAndTranslateScalePoints(centre, layer3r * HEX_DIST_NEW_CENTRE, hexPointsAlt);

        List<Point2D> layerExtPol1Edges = cloneAndTranslateScalePoints(layerExtEdges.get(0), layer3r, hexPoints);

        out.addAll(
                drawPolylines(
                        generateCombsOfPoints(layer1Edges),
                        gray)
        );

        out.addAll(
                drawPolylines(
                        buildLines(centre, cloneAndTranslateScalePoints(centre, r * HEX_DIST_HEIGHT, hexPointsAlt)),
                        gray)
        );

        out.addAll(
                drawPolylines(
                        generateCombsOfPoints(layer2Edges),
                        gray)
        );

        out.add(
                drawPolygon(layer3Edges, green)
        );

        out.addAll(
                drawPolylines(generateCombsOfPoints(layerExtPol1Edges), gray)
        );


        out.addAll(
                drawPolylines(Tile4.buildExtConf(centre, layer3r), red)
        );


        out.addAll(highlightPoints(layerExtEdges));

        return out;
    }

    private static List<List<Point2D>> buildExtConfForDesign4(Point2D centre, double r) {


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
