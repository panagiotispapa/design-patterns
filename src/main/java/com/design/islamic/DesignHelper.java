package com.design.islamic;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.view.SvgFactory.*;
import static com.design.common.view.SvgFactory.drawPolygon;
import static com.design.common.view.SvgFactory.highlightPoints;
import static com.design.islamic.GenericTools.*;
import static com.design.islamic.Patterns.*;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.cos;
import static java.util.Arrays.asList;

public class DesignHelper {


    public static List<XMLBuilder> newStarDesign1(final Point2D centre, final double r) {
        ImmutableList.Builder<XMLBuilder> builder = ImmutableList.builder();

        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        final List<Point2D> outsideCentres = buildHexAltPoints(centre, r * HEX_DIST_NEW_CENTRE);

        List<Point2D> pointsLayerMiddle = buildHexPoints(centre, 0.5 * r);

        List<Point2D> edgesAltLayer1 = buildHexAltPoints(centre, r * HEX_DIST_1);

        List<Point2D> edgesAltLayer2 = buildHexAltPoints(centre, r * HEX_DIST2);

        List<Point2D> edgesAltLayer3 = buildHexAltPoints(centre, r * HEX_DIST3);


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

        builder.add(drawPolygon(newHexStarTile(centre, r, HEX_DIST_1), green));
        builder.add(drawPolygon(newHexStarTile(centre, r, HEX_DIST2), green));
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

        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        List<Point2D> edgesAltLayer2 = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAltLayer2, r * HEX_DIST_HEIGHT);
        translatePoints(edgesAltLayer2, centre);

        List<List<Point2D>> layer2 = generateCombsOfPoints(edgesAltLayer2);

        List<List<Point2D>> layer3 = asList(
                asList(edges.get(0), edges.get(3)),
                asList(edges.get(1), edges.get(4)),
                asList(edges.get(2), edges.get(5))
        );

        List<Point2D> edgesAltLayer4 = clonePoints(Patterns.hexPoints);

        scalePoints(edgesAltLayer4, newR * HEX_DIST2);
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
        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        List<Point2D> edgesAltLayer2 = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAltLayer2, r * newHeight);
        translatePoints(edgesAltLayer2, centre);

        List<Point2D> innerEdges = buildHexPoints(centre, newR);

//        List<Point2D> insideStar = buildStarRotatedEdges(centre, newR, HEX_DIST2);

        List<Point2D> edgesInnerHeights = buildHexAltPoints(centre, newR * HEX_DIST_HEIGHT);


        double extConfR = r - newR;

        List<Point2D> edgesLayer8 = buildHexPoints(innerEdges.get(0), extConfR);

        List<Point2D> heightsEdgesLayer8 = buildHeightEdges(innerEdges.get(0), extConfR);

        List<List<Point2D>> heightsLayer8 = buildHeights(innerEdges.get(0), extConfR);


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

        List<Point2D> edges = buildHexPoints(centre, r);
        List<Point2D> heights = buildHeightEdges(centre, r);

        return asList(
                centre,
                heights.get(index),
                edges.get(index),
                heights.get((index + 5) % 6)
        );

    }


}
