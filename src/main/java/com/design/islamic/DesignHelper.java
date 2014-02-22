package com.design.islamic;

import com.design.islamic.model.hex.*;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.GenericTools.concatEdges;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.util.Arrays.asList;

public class DesignHelper {

    public static List<XMLBuilder> newStarDesign1(final Point2D centre, final double r) {
        ImmutableList.Builder<XMLBuilder> builder = ImmutableList.builder();

        List<Point2D> edges = cloneAndTranslateScalePoints(centre, r, hexPoints);

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
        final String red = newStyle(RED, 2, 1);

        final double newR = r * cos(HEX_PHI / 2);

        List<Point2D> edges = cloneAndTranslateScalePoints(centre, r, hexPointsAlt);

        List<Point2D> edgesAltLayer2 = cloneAndTranslateScalePoints(centre, r * HEX_DIST_HEIGHT, hexPoints);

        List<List<Point2D>> layer2 = generateCombsOfPoints(edgesAltLayer2);

        List<List<Point2D>> layer3 = asList(
                asList(edges.get(0), edges.get(3)),
                asList(edges.get(1), edges.get(4)),
                asList(edges.get(2), edges.get(5))
        );

        List<Point2D> edgesAltLayer4 = cloneAndTranslateScalePoints(centre, newR * HEX_DIST_DIAGONAL, hexPointsAlt);

        List<Point2D> edgesLayer5 = concatEdges(edgesAltLayer2, edgesAltLayer4);

        out.add(drawPolygon(edges, gray));
        out.addAll(drawPolylines(layer2, gray));
        out.addAll(drawPolylines(layer3, gray));
        out.add(drawPolygon(edgesLayer5, green));

        out.addAll(highlightPoints(edges));
        out.addAll(highlightPoints(edgesAltLayer2));
        out.addAll(highlightPoints(edgesAltLayer4));

//        out.add(drawPolygon(newHexStarTileRotated(centre, r, HEX_DIST_DIAGONAL), red));

        return out;

    }

    public static List<XMLBuilder> newStarDesign3(final Point2D centre, final double r) {

        Tile3 tile3 = new Tile3(centre, r);

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String blue = newStyle(BLUE, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);

        final double newR = r * HEX_DIST_EQ1;
        double newHeight = HEX_DIST_EQ1 * HEX_DIST_HEIGHT;
        List<Point2D> edges = newHexagonRot(centre, r);

        List<Point2D> innerEdges = newHexagonRot(centre, newR);

//        List<Point2D> insideStar = buildStarRotatedEdges(centre, newR, HEX_DIST_DIAGONAL);

        List<Point2D> edgesInnerHeights = newHexagon(centre, newR * HEX_DIST_HEIGHT);

        List<Point2D> innerStar = newHexStarTileRotated(centre, newR, HEX_DIST_DIAGONAL);

        double extConfR = r - newR;

        List<List<Point2D>> extConfigs = buildExtConfigForDesign3(innerEdges, extConfR);

        List<List<Point2D>> layerExt0 = generateCombsOfPoints(newHexagonRot(innerEdges.get(0), extConfR));
        List<Point2D> layerExt0_1 = newHexagon(innerEdges.get(0), extConfR * HEX_DIST_DIAGONAL_ROTATED);
        List<Point2D> layerExt0_2 = newHexagonRot(edgesInnerHeights.get(0), newR * HEX_DIST_DIAGONAL_ROTATED * 0.5);
        List<Point2D> layerExt0_3 = newHexagonRot(edgesInnerHeights.get(5), newR * HEX_DIST_DIAGONAL_ROTATED * 0.5);

        out.add(drawPolygon(edges, gray));
        out.addAll(drawPolylines(layerExt0, gray));

        out.add(drawPolygon(layerExt0_1, blue));
        out.add(drawPolygon(layerExt0_2, blue));
        out.add(drawPolygon(layerExt0_3, blue));
        out.add(drawPolygon(innerStar, blue));

        out.addAll(drawPolylines(tile3.getLines(), red));
        out.addAll(drawPolylines(tile3.getLines2(), red));


//        out.add(drawPolygon(innerEdges, gray));

//        out.add(drawPolygon(insideStar, gray));
//        out.add(drawPolygon(polygon1, gray));
//        out.add(drawPolygon(polygon2, gray));

//        out.addAll(drawPolygons(
//                extPolygons,
//                gray));

        out.add(drawPolygon(
                innerEdges, green));

//        out.add(drawPolygon(
//                edgesLayer8,gray));

        out.addAll(drawPolylines(
                extConfigs, red));

//        out.addAll(drawPolylines(layerExt, gray));

//        out.addAll(highlightPoints(edges));
//        out.addAll(highlightPoints(edgesAltLayer2));
        out.addAll(highlightPoints(innerEdges));
        out.addAll(highlightPoints(edgesInnerHeights));
        out.addAll(highlightPoints(extConfigs.get(0)));
//        out.addAll(highlightPoints(heightsEdgesLayer8));

        return out;

    }

    private static List<List<Point2D>> buildExtConfigForDesign3(final List<Point2D> centres, final double r) {

        List<List<Point2D>> out = newArrayList();
        //List<Point2D> edges = cloneAndTranslateScalePoints(centre, r, hexPoints);

        int index = 0;
        for (Point2D centre : centres) {
            List<Point2D> heights = newHexagon(centre, r * HEX_DIST_HEIGHT);
            out.add(
                    asList(
                            heights.get(toHexIndex(1 + index)),
                            centre,
                            heights.get(toHexIndex(0 + index))
                    )
            );
            index++;
        }

        return out;

//        return asList(
//                heights.get(index),
//                centre,
//                heights.get((index + 5) % 6)
//        );

    }

    public static List<XMLBuilder> newStarDesign4(final Point2D centre, final double r) {

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> layer1Edges = newHexagonRot(centre, r);
        double layer2r = r * HEX_DIST_DIAGONAL;
        List<Point2D> layer2Edges = newHexagon(centre, layer2r);
        double layer3r = layer2r * HEX_DIST_DIAGONAL;
        List<Point2D> layer3Edges = newHexagonRot(centre, layer3r);

        List<Point2D> layerExtEdges = newHexagon(centre, layer3r * HEX_DIST_NEW_CENTRE);

        List<Point2D> layerExtPol1Edges = newHexagonRot(layerExtEdges.get(0), layer3r);

        out.addAll(
                drawPolylines(
                        generateCombsOfPoints(layer1Edges),
                        gray)
        );

        out.addAll(drawPolylines(newHexHeightsRot(centre, r),
                gray)
        );

        out.addAll(
                drawPolylines(
                        generateCombsOfPoints(layer2Edges),
                        gray)
        );
//
        out.add(
                drawPolygon(layer3Edges, green)
        );
//
        out.addAll(
                drawPolylines(generateCombsOfPoints(layerExtPol1Edges), gray)
        );
//
        out.addAll(
                drawPolylines(Tile4.buildExtConf(centre, layer3r), red)
        );

        out.addAll(highlightPoints(layerExtEdges));

        return out;
    }

    public static List<XMLBuilder> newStarDesign5(final Point2D centre, final double r) {

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> layer1Edges = newHexagonRot(centre, r);

        double layer2R = r * HEX_DIST_DIAGONAL;

        List<Point2D> layer2Edges = newHexagon(centre, layer2R);

        out.addAll(
                drawPolylines(generateCombsOfPoints(layer1Edges), gray)
        );

        double layer3R = layer2R * HEX_DIST_DIAGONAL;

        List<Point2D> layer3Edges = newHexagonRot(centre, layer3R);

        List<Point2D> outerEdges = newHexagon(centre, layer3R * HEX_DIST_NEW_CENTRE);

        List<List<Point2D>> lines = Tile5.buildOuterLines(outerEdges, layer3R);

        out.addAll(
                drawPolylines(generateCombsOfPoints(layer2Edges), gray)
        );
        out.add(
                drawPolygon(layer2Edges, blue)
        );

        out.add(
                drawPolygon(layer3Edges, green)
        );

        out.addAll(highlightPoints(outerEdges));
        out.addAll(drawPolylines(lines, red));

        return out;
    }

    public static List<XMLBuilder> newStarDesign6(final Point2D centre, final double r) {

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> layer1Edges = newHexagonRot(centre, r);

        double newR = r * HEX_DIST_DIAGONAL_ROTATED;

        List<Point2D> layer2Edges = newHexagonRot(centre, newR);
        List<Point2D> outerEdges = newHexagon(centre, newR * HEX_DIST_NEW_CENTRE);

        List<Point2D> outerInnerHex = newHexagonRot(outerEdges.get(0), newR * HEX_DIST_DIAGONAL_ROTATED);

        out.addAll(
                drawPolylines(generateCombsOfPoints(layer1Edges), gray)
        );

        out.addAll(
                drawPolygons(newHexInnerTrianglesRot(centre, r), gray)
        );

        out.add(drawPolygon(outerInnerHex, gray));

        out.addAll(highlightPoints(layer2Edges));
        out.addAll(highlightPoints(outerEdges));

        out.addAll(drawPolylines(Tile6.buildOuterLines(outerEdges, newR), red));

        return out;

    }

    public static List<XMLBuilder> newStarDesign7(final Point2D centre, final double r) {

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Tile7 tile7 = new Tile7(centre,r);

        List<Point2D> outerHex = newHexagon(centre, r);
        List<Point2D> outerHexRotated = newHexagonRot(centre, r);

        double newR = r * HEX_DIST_DIAGONAL_ROTATED;

        List<Point2D> innerHex = newHexagon(centre, newR);
        List<Point2D> innerHexRot = newHexagonRot(centre, newR);

        List<Point2D> innerHex2 = newHexagon(centre, newR * HEX_DIST_DIAGONAL_ROTATED);
        List<Point2D> innerHexRot2 = newHexagonRot(centre, newR * HEX_DIST_DIAGONAL_ROTATED);

        List<Point2D> heightPoints = newHexagonRot(centre, r * HEX_DIST_HEIGHT);

        double rSmall = r * (1 - HEX_DIST_HEIGHT);

        List<Point2D> outerHexSmall = newHexagonRot(heightPoints.get(0), rSmall);

        out.add(drawPolygon(outerHex, gray));
        out.add(drawPolygon(outerHexRotated, gray));

        out.addAll(drawPolylines(newHexHeights(centre, r), gray));
        out.addAll(drawPolylines(newHexHeightsRot(centre, r), gray));

        out.addAll(drawPolygons(newHexInnerTriangles(centre, r), gray));
        out.addAll(drawPolygons(newHexInnerTrianglesRot(centre, r), gray));

        out.addAll(drawPolygons(newHexInnerTriangles(centre, newR), gray));
        out.addAll(drawPolygons(newHexInnerTrianglesRot(centre, newR), gray));

        out.add(drawPolygon(innerHex, blue));
        out.add(drawPolygon(innerHexRot, blue));

        out.add(drawPolygon(innerHex2, blue));
        out.add(drawPolygon(innerHexRot2, blue));

        out.add(drawPolygon(outerHexSmall, blue));
        out.addAll(drawPolylines(newHexHeightsRot(heightPoints.get(0), rSmall), gray));

        out.addAll(highlightPoints(heightPoints));

        out.addAll(drawPolylines(tile7.getLines(), red));

        return out;

    }

    public static List<XMLBuilder> newStarDesign8(final Point2D centre, final double r) {

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> outerHex = newHexagon(centre, r);
        List<Point2D> outerHexRot = newHexagonRot(centre, r);

        List<Point2D> outerHeights = newHexagonRot(centre, r * HEX_DIST_HEIGHT);
        List<Point2D> outerHeightsRot = newHexagon(centre, r * HEX_DIST_HEIGHT);

        List<Point2D> outerHeightsConcat = concatEdges(outerHeightsRot, outerHeights);

        double newR = r * HEX_DIST_DIAGONAL;

        List<Point2D> innerHex = newHexagon(centre, newR);
        List<Point2D> innerHexRot = newHexagonRot(centre, newR);

        List<Point2D> layerOut1 = newHexagon(outerHex.get(0), r * Tile8.OUTER_R1);
        List<Point2D> layerOut1Heights = newHexagonRot(outerHex.get(0), r * Tile8.OUTER_R1 * HEX_DIST_HEIGHT);

        List<Point2D> layerOut2 = newHexagonRot(outerHex.get(0), r * Tile8.OUTER_R1 * HEX_DIST_HEIGHT);

        List<Point2D> outerPoints1 = Tile8.calcOuterPoints1(centre, r);
        List<Point2D> outerPoints2 = newHexagon(centre, r * Tile8.OUTER_R5);
        List<Point2D> outerPoints2Rot = newHexagonRot(centre, r * Tile8.OUTER_R5);

//        List<Point2D> innerHex2 = newHexagon(centre, newR*HEX_DIST_DIAGONAL);
//        List<Point2D> innerHexRot2 = newHexagonRot(centre, newR*HEX_DIST_DIAGONAL);

//        out.add(drawPolygon(outerHex, gray));
//        out.add(drawPolygon(outerHexRot, gray));
        out.addAll(drawPolylines(generateCombsOfPoints(outerHex), gray));
        out.addAll(drawPolylines(generateCombsOfPoints(outerHexRot), gray));
//        out.addAll(drawPolygons(newHexInnerTriangles(centre, r), gray));
//        out.addAll(drawPolygons(newHexInnerTrianglesRot(centre, r), gray));
        out.addAll(drawPolylines(newHexDiag(centre, r), gray));
        out.addAll(drawPolylines(newHexDiagRot(centre, r), gray));

//        out.add(drawPolygon(innerHex, blue));
//        out.add(drawPolygon(innerHexRot, blue));
        out.add(drawPolygon(outerHeightsConcat, blue));
        out.add(drawPolygon(layerOut1, blue));
        out.add(drawPolygon(layerOut2, gray));
//        out.add(drawPolygon(newHexagonRot(layerOut2.get(0), r*Tile8.OUTER_R4), gray));

        out.add(drawPolygon(newHexagonRot(centre, r * (1 - Tile8.OUTER_R3)), gray));

//        out.addAll(drawPolygons(Tile8.calcOuterHex1(centre, r), gray));

        out.add(drawPolygon(newHexagonRot(outerPoints1.get(6), r * Tile8.OUTER_R2), blue));
        out.addAll(drawPolylines(newHexHeightsRot(outerPoints1.get(6), r * Tile8.OUTER_R2), gray));
        out.addAll(drawPolylines(newHexDiagRot(outerPoints1.get(6), r * Tile8.OUTER_R2), gray));
        out.add(drawPolygon(newHexagon(outerPoints1.get(6), r * Tile8.OUTER_R2 * HEX_DIST_HEIGHT * 0.72), gray));
//        out.add(drawPolygon(newHexagonRot(outerPoints2.get(0), r*Tile8.OUTER_R4), gray));
//        out.addAll(drawPolylines(newHexDiagRot(outerPoints2.get(0), r*Tile8.OUTER_R4), gray));
        out.addAll(drawPolylines(generateCombsOfPoints(newHexagonRot(outerPoints2.get(0), r * Tile8.OUTER_R4)), gray));

//        out.add(drawPolygon(innerHex2, blue));
//        out.add(drawPolygon(innerHexRot2, blue));

//        out.addAll(highlightPoints(layerOut1Heights));

//        out.add(drawPolygon(outerPoints2, gray));
//        out.add(drawPolygon(outerPoints2Rot, gray));
        out.addAll(drawPolylines(generateCombsOfPoints(outerPoints2), gray));
        out.addAll(drawPolylines(generateCombsOfPoints(outerPoints2Rot), gray));

        out.add(drawPolygon(newHexagon(outerPoints2.get(0), r * Tile8.OUTER_R5 * Tile8.OUTER_R1), gray));
        out.add(drawPolygon(newHexagonRot(outerPoints2.get(0), r * Tile8.OUTER_R5 * Tile8.OUTER_R1 * HEX_DIST_HEIGHT), gray));

//        out.add(drawPolygon(newHexagon(centre, r*Tile8.OUTER_R5),gray));

        out.addAll(highlightPoints(outerPoints1));
        out.addAll(highlightPoints(outerPoints2));

        out.add(drawPolygon(newHexagonRot(outerHexRot.get(3), r * Tile8.OUTER_R3), blue));

        out.addAll(drawPolylines(Tile8.calcOuterLines1(centre, r), red));
        out.addAll(drawPolylines(Tile8.calcOuterLines2(centre, r), red));
        out.addAll(drawPolylines(Tile8.calcOuterLines3(centre, r), red));

        return out;

    }

    public static List<XMLBuilder> newStarDesign9(final Point2D centre, final double r) {

        Tile9 tile9 = new Tile9(centre, r);

        List<XMLBuilder> out = newArrayList();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> outer = newHexagon(centre, r);
        List<Point2D> outerRot = newHexagonRot(centre, r);

        double rH = r * HEX_DIST_HEIGHT;

        out.add(drawPolygon(outer, gray));
        out.add(drawPolygon(outerRot, gray));
//        out.addAll(drawPolylines(newHexHeightsRot(centre, r), gray));
//        out.addAll(drawPolygons(newHexInnerTrianglesRot(centre, r), gray));
        out.addAll(drawPolylines(newHexDiagRot(centre, r), gray));
        out.addAll(drawPolylines(newHexDiag(centre, r), gray));

        out.addAll(drawPolylines(concateOuterHexagons(outer, outerRot), gray));

//        out.add(drawPolygon(outerLayer2, gray));
//        out.add(drawPolygon(outerLayer2Rot, gray));

        out.addAll(drawPolylines(concateOuterHexagons(tile9.getOuterLayer1(), tile9.getOuterLayer1Rot()), gray));
        out.addAll(drawPolylines(concateOuterHexagons(tile9.getOuterLayer2(), tile9.getOuterLayer2Rot()), gray));
//        out.addAll(highlightPoints(heightPoints));

        out.add(drawPolygon(tile9.getInnerLayer1(), gray));
        out.add(drawPolygon(tile9.getInnerLayer1Rot(), gray));


        out.addAll(drawPolylines(buildLines(
                newHexEdge(centre, r * Tile9.INNER_R2,0),
                asList(newHexEdgeRot(outerRot.get(5),r * Tile9.INNER_R1,1))),gray));

        out.addAll(drawPolylines(buildLines(
                newHexEdge(centre, r * Tile9.INNER_R2, 5),
                asList(newHexEdgeRot(outerRot.get(5), r * Tile9.INNER_R1, 3))), gray));


        out.addAll(drawPolylines(buildLines(
                newHexEdgeRot(centre, r * Tile9.INNER_R2, 0),
                asList(newHexEdge(outer.get(0), r * Tile9.INNER_R1, 1))),gray));


        out.addAll(drawPolylines(buildLines(
                newHexEdgeRot(centre, r * Tile9.INNER_R2, 5),
                asList(newHexEdge(outer.get(0), r * Tile9.INNER_R1, 5))),gray));


        out.addAll(drawPolygons(newHexInnerTrianglesFull(centre, r * Tile9.INNER_R2), gray));
        out.addAll(drawPolygons(newHexInnerTrianglesFullRot(centre, r * Tile9.INNER_R2), gray));

        out.addAll(drawPolylines(concateOuterHexagons(
                newHexagon(outer.get(0), r * (1.0 - Tile9.OUTER_R1)),
                newHexagonRot(outer.get(0), r * (1.0 - Tile9.OUTER_R1))
        ), gray));

        out.addAll(drawPolylines(concateOuterHexagons(
                newHexagon(outer.get(0), r * (1.0 - Tile9.OUTER_R2)),
                newHexagonRot(outer.get(0), r * (1.0 - Tile9.OUTER_R2))
        ), gray));

        for (Point2D edge : outerRot) {
            out.add(drawPolygon(newHexagonRot(edge, r * Tile9.INNER_R1), blue));
        }

//        out.add(drawPolygon(newHexagonRot(outerRot.get(2), r * Tile9.INNER_R1), blue));
//        out.add(drawPolygon(newHexagonRot(outerRot.get(3), r * Tile9.INNER_R1), blue));
//        out.add(drawPolygon(newHexagonRot(outerRot.get(4), r * Tile9.INNER_R1), blue));
        out.add(drawPolygon(newHexagonRot(centre, r * (1.0 - Tile9.INNER_R1)), gray));

//        out.addAll(drawPolylines(Tile9.calcOuterLines1(centre, r), red));


        out.add(drawPolygon(newHexagonRot(centre, r), red));
        out.addAll(drawPolylines(tile9.getLines(), red));


        out.addAll(highlightPoints(newHexagon(centre, r * Tile9.OUTER_R4)));
        out.addAll(highlightPoints(newHexagonRot(centre, r * Tile9.OUTER_R4)));


//        out.addAll(highlightPoints(tile9.getPointsA()));
//        out.addAll(highlightPoints(tile9.getPointsB()));
//        out.addAll(highlightPoints(tile9.getPointsC()));
//        out.addAll(highlightPoints(tile9.getPointsD()));
//        out.addAll(highlightPoints(tile9.getPointsE()));
//        out.addAll(highlightPoints(tile9.getPointsF()));
//        out.addAll(highlightPoints(tile9.getPointsG()));
//        out.addAll(highlightPoints(tile9.getPointsH()));
//        out.addAll(highlightPoints(tile9.getPointsI()));
//        out.addAll(highlightPoints(tile9.getPointsJ()));
//        out.addAll(highlightPoints(tile9.getPointsK()));
//        out.addAll(highlightPoints(tile9.getPointsL()));
//        out.addAll(highlightPoints(tile9.getPointsM()));
//        out.addAll(highlightPoints(tile9.getPointsN()));
//        out.addAll(highlightPoints(tile9.getPointsO()));
//        out.addAll(highlightPoints(tile9.getPointsP()));
//        out.addAll(highlightPoints(tile9.getPointsQ()));
//        out.addAll(highlightPoints(tile9.getPointsR()));
//        out.addAll(highlightPoints(tile9.getPointsS()));
//        out.addAll(highlightPoints(tile9.getInnerLayer1()));
//        out.addAll(highlightPoints(tile9.getInnerLayer1Rot()));
        out.addAll(highlightPoints(tile9.getOuterLayer1()));
        out.addAll(highlightPoints(tile9.getOuterLayer1Rot()));
        out.addAll(highlightPoints(tile9.getOuterLayer2()));
        out.addAll(highlightPoints(tile9.getOuterLayer2Rot()));

        return out;

    }

}
