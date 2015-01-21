package com.design.islamic;

import com.design.common.PolygonTools;
import com.design.islamic.model.DrawSegmentsInstructions;
import com.design.islamic.model.Tile;
import com.design.islamic.model.hex.*;
import com.design.islamic.model.tiles.CentreTransform;
import com.design.islamic.model.tiles.Hex;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.GenericTools.concatEdges;
import static com.design.islamic.model.DrawSegmentsInstructions.*;
import static com.design.islamic.model.tiles.CentreTransform.newTransform;
import static com.design.islamic.model.tiles.Hex.NO_TRANSFORMS;
import static com.design.islamic.model.tiles.Hex.Type.HOR;
import static com.design.islamic.model.tiles.Hex.Type.VER;
import static com.design.islamic.model.tiles.Hex.newHex;
import static java.lang.Math.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class HexDesignHelper {

    public static String newStarDesign1(Pair<Point2D, Double> initialConditions) {

        StringBuilder builder = new StringBuilder();

        DrawSegmentsInstructions.Builder iBuilder = DrawSegmentsInstructions.Builder.with(initialConditions);
        SvgBuilder svgBuilder = SvgBuilder.with(builder);

        Hex main = Hex.newHex(NO_TRANSFORMS, 1, HOR);
        Hex outerCentres = Hex.newHex(NO_TRANSFORMS, HEX_DIST_NEW_CENTRE, VER);
        Hex layer1 = Hex.newHex(NO_TRANSFORMS, HEX_DIST_OUTER_CIRCLE, VER);
        Hex layer2 = Hex.newHex(NO_TRANSFORMS, HEX_DIST_DIAGONAL, VER);
        Hex layer3 = Hex.newHex(NO_TRANSFORMS, HEX_DIST3, VER);
        Hex layer4 = Hex.newHex(NO_TRANSFORMS, 1, VER);
        Hex layerMiddle = Hex.newHex(NO_TRANSFORMS, 0.5, HOR);

        final List<Point2D> outsideCentres = iBuilder.vertexes(outerCentres);

        String black = newStyle("black", 2, 1);
        String blue = newStyle("blue", 1, 1);
        final String gray = newStyle("gray", 1, 1);
        final String green = newStyle("green", 1, 1);

        builder.append(newCircle(initialConditions.getLeft(), initialConditions.getRight(), black));

        builder.append(outsideCentres.stream().map(c_centre -> newCircle(c_centre, initialConditions.getRight(), gray)).collect(toList()));
        builder.append(outsideCentres.stream().map(c_centre -> newPolyline(asList(initialConditions.getLeft(), c_centre), gray)).collect(toList()));

        svgBuilder
                .drawPolyLine(iBuilder.vertexes(main), gray)
                .drawLines(iBuilder.lines(main, DIAGONALS_FULL), gray)
                .drawPolyLine(iBuilder.vertexes(layer1), gray)
                .drawPolyLine(iBuilder.vertexes(layerMiddle), gray)
                .drawPolyLine(iBuilder.vertexes(outerCentres), gray)
                .drawPolyLine(iBuilder.combineVertexes(layer4, layer1.getMirror()), green)
                .drawPolyLine(iBuilder.combineVertexes(layer4, layer2.getMirror()), green)
                .drawPolyLine(iBuilder.combineVertexes(layer4, layer3.getMirror()), green)
//                .highlightPoints(iBuilder.vertexes(main))
                .highlightPoints(iBuilder.vertexes(outerCentres))
                .highlightPoints(iBuilder.vertexes(layer1))
                .highlightPoints(iBuilder.vertexes(layer2))
                .highlightPoints(iBuilder.vertexes(layer3))
                .highlightPoints(iBuilder.vertexes(layer4))
        ;

        return builder.toString();

    }

    public static String newStarDesignTest(final Point2D centre, final double r) {

        Hex one = newHex(NO_TRANSFORMS, 1, VER);

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);

//        builder.append(drawPolylinesFromLine2D(lines(0, centre, r, one, PERIMETER), green));
//        builder.append(drawPolylinesFromLine2D(lines(0, centre, r, one, DIAGONALS), gray));
//        builder.append(drawPolylinesFromLine2D(lines(0, centre, r, one.getInternal(), DIAGONALS), gray));
//
        return builder.toString();
    }

    public static String newStarDesign2(final Point2D centre, final double r) {

        Pair<Point2D, Double> initialConditions = Pair.of(centre, r);

        DrawSegmentsInstructions.Builder iBuilder = DrawSegmentsInstructions.Builder.with(initialConditions);
        SvgBuilder svgBuilder = SvgBuilder.with(new StringBuilder());

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);

        final double newR = r * cos(HEX_PHI / 2);

        Hex outer = newHex(NO_TRANSFORMS, 1, VER);
        Hex outerH = outer.getInternal();
        Hex inner = newHex(NO_TRANSFORMS, 0.5, VER);

        return
                svgBuilder
                        .drawLines(iBuilder.lines(outer, PERIMETER), gray)
                        .drawLines(iBuilder.lines(outer, DIAGONALS), gray)
                        .drawLines(iBuilder.lines(inner, PERIMETER), gray)
                        .drawPolyLine(iBuilder.combineVertexes(inner, outerH), green)
                        .highlightPoints(iBuilder.vertexes(outer))
                        .highlightPoints(iBuilder.vertexes(outerH))
                        .highlightPoints(iBuilder.vertexes(inner))
                        .getBuilder().toString()
                ;

    }

    public static String newStarDesign3(final Point2D centre, final double r) {

        Tile3 tile3 = new Tile3(centre, r);

        StringBuilder builder = new StringBuilder();
        Pair<Point2D, Double> initialConditions = Pair.of(centre, r);

        DrawSegmentsInstructions.Builder iBuilder = DrawSegmentsInstructions.Builder.with(initialConditions);
        SvgBuilder svgBuilder = SvgBuilder.with(builder);

        final String gray = newStyle(GRAY, 1, 1);
        final String blue = newStyle(BLUE, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);

        Hex main = Hex.newHex(NO_TRANSFORMS, 1, VER);
        Hex inner = Hex.newHex(NO_TRANSFORMS, HEX_DIST_EQ1, VER);
        Hex inner2 = Hex.newHex(NO_TRANSFORMS, HEX_DIST_EQ1 * 0.5, VER);

        CentreTransform cTransform1 = newTransform(HEX_DIST_EQ1, VER, Hex.Vertex.ONE);
        CentreTransform.VertexBuilder cTransform2 = CentreTransform.transform().ratio(HEX_DIST_EQ1 * HEX_DIST_HEIGHT).type(HOR);

        Hex ext0 = Hex.newHex(cTransform1, 1 - HEX_DIST_EQ1, VER);
        Hex ext0_1 = Hex.newHex(cTransform1, (1 - HEX_DIST_EQ1) * HEX_DIST_DIAGONAL_ROTATED, HOR);

        Hex ext0_2 = Hex.newHex(cTransform2.vertex(Hex.Vertex.ONE), HEX_DIST_EQ1 * HEX_DIST_DIAGONAL_ROTATED * 0.5, VER);
        Hex ext0_3 = Hex.newHex(cTransform2.vertex(Hex.Vertex.SIX), HEX_DIST_EQ1 * HEX_DIST_DIAGONAL_ROTATED * 0.5, VER);

        final double newR = r * HEX_DIST_EQ1;
        double extConfR = r - newR;

        List<List<Point2D>> extConfigs = buildExtConfigForDesign3(iBuilder.vertexes(inner), extConfR);

        svgBuilder
                .drawPolyLine(iBuilder.vertexes(main), gray)
                .drawPolyLines(generateCombsOfPoints(iBuilder.vertexes(ext0)), gray)
                .drawPolyLine(iBuilder.vertexes(ext0_1), blue)
                .drawPolyLine(iBuilder.vertexes(ext0_2), blue)
                .drawPolyLine(iBuilder.vertexes(ext0_3), blue)
                .drawPolyLine(iBuilder.vertexes(inner), green)
                .drawPolyLine(iBuilder.combineVertexes(inner2, inner.getInternal()), blue)
                .drawPolyLines(extConfigs, red)
                .drawPolyLines(tile3.getLines(), red)
                .drawPolyLines(tile3.getLines2(), red)
                .highlightPoints(iBuilder.vertexes(inner.getInternal()))
                .highlightPoints(iBuilder.vertexes(inner))
                .highlightPoints(extConfigs.get(0))
                .highlightPoints(iBuilder.vertexes(inner2))
        ;

        return builder.toString();

    }

    private static List<List<Point2D>> buildExtConfigForDesign3(final List<Point2D> centres, final double r) {

        List<List<Point2D>> out = new ArrayList<>();
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

    public static String newStarDesign4(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();
        Pair<Point2D, Double> initialConditions = Pair.of(centre, r);

        DrawSegmentsInstructions.Builder iBuilder = DrawSegmentsInstructions.Builder.with(initialConditions);
        SvgBuilder svgBuilder = SvgBuilder.with(builder);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Hex layer1 = Hex.newHex(1, VER);
        Hex layer2 = Hex.newHex(HEX_DIST_DIAGONAL, HOR);
        Hex layer3 = Hex.newHex(HEX_DIST_DIAGONAL * HEX_DIST_DIAGONAL, VER);
        Hex layerExt = Hex.newHex(layer3.getRatio() * HEX_DIST_NEW_CENTRE, HOR);
        Hex layerExtPol1 = Hex.newHex(newTransform(layerExt.getRatio(), layerExt.getType(), Hex.Vertex.ONE), layer3.getRatio(), VER);

        return
                svgBuilder
                        .drawPolyLines(generateCombsOfPoints(iBuilder.vertexes(layer1)), gray)
                        .drawPolyLines(generateCombsOfPoints(iBuilder.vertexes(layer2)), gray)
                        .drawPolyLines(generateCombsOfPoints(iBuilder.vertexes(layerExtPol1)), gray)
                        .drawLines(iBuilder.lines(0, layer1.getInternal(), DIAGONALS), gray)
                        .drawPolyLine(iBuilder.vertexes(layer3), green)
                        .drawPolyLines(Tile4.buildExtConf(centre, r * HEX_DIST_DIAGONAL*HEX_DIST_DIAGONAL), red)
                        .highlightPoints(iBuilder.vertexes(layerExt))
                        .getBuilder().toString()
                ;

    }

    public static String newStarDesign5(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> layer1Edges = newHexagonRot(centre, r);

        double layer2R = r * HEX_DIST_DIAGONAL;

        List<Point2D> layer2Edges = newHexagon(centre, layer2R);

        builder.append(
                drawPolylines(generateCombsOfPoints(layer1Edges), gray)
        );

        double layer3R = layer2R * HEX_DIST_DIAGONAL;

        List<Point2D> layer3Edges = newHexagonRot(centre, layer3R);

        List<Point2D> outerEdges = newHexagon(centre, layer3R * HEX_DIST_NEW_CENTRE);

        List<List<Point2D>> lines = Tile5.buildOuterLines(outerEdges, layer3R);

        builder.append(
                drawPolylines(generateCombsOfPoints(layer2Edges), gray)
        );
        builder.append(
                drawPolygon(layer2Edges, blue)
        );

        builder.append(
                drawPolygon(layer3Edges, green)
        );

        builder.append(highlightPoints(outerEdges));
        builder.append(drawPolylines(lines, red));

        return builder.toString();
    }

    public static String newStarDesign6(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> layer1Edges = newHexagonRot(centre, r);

        double newR = r * HEX_DIST_DIAGONAL_ROTATED;

        List<Point2D> layer2Edges = newHexagonRot(centre, newR);
        List<Point2D> outerEdges = newHexagon(centre, newR * HEX_DIST_NEW_CENTRE);

        List<Point2D> outerInnerHex = newHexagonRot(outerEdges.get(0), newR * HEX_DIST_DIAGONAL_ROTATED);

        builder.append(
                drawPolylines(generateCombsOfPoints(layer1Edges), gray)
        );

        builder.append(
                drawPolygons(newHexInnerTrianglesRot(centre, r), gray)
        );

        builder.append(drawPolygon(outerInnerHex, gray));

        builder.append(highlightPoints(layer2Edges));
        builder.append(highlightPoints(outerEdges));

        builder.append(drawPolylines(Tile6.buildOuterLines(outerEdges, newR), red));

        return builder.toString();

    }

    public static String newDesign7(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Tile7 tile7 = new Tile7(centre, r);

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

        builder.append(drawPolygon(outerHex, gray));
        builder.append(drawPolygon(outerHexRotated, gray));

        builder.append(drawPolylines(newHexHeights(centre, r), gray));
        builder.append(drawPolylines(newHexHeightsRot(centre, r), gray));

        builder.append(drawPolygons(newHexInnerTriangles(centre, r), gray));
        builder.append(drawPolygons(newHexInnerTrianglesRot(centre, r), gray));

        builder.append(drawPolygons(newHexInnerTriangles(centre, newR), gray));
        builder.append(drawPolygons(newHexInnerTrianglesRot(centre, newR), gray));

        builder.append(drawPolygon(innerHex, blue));
        builder.append(drawPolygon(innerHexRot, blue));

        builder.append(drawPolygon(innerHex2, blue));
        builder.append(drawPolygon(innerHexRot2, blue));

        builder.append(drawPolygon(outerHexSmall, blue));
        builder.append(drawPolylines(newHexHeightsRot(heightPoints.get(0), rSmall), gray));

        builder.append(highlightPoints(heightPoints));

        builder.append(drawPolylines(tile7.getLines(), red));

        return builder.toString();

    }

    public static String newDesign8(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Tile8 tile8 = new Tile8(centre, r);

        List<Point2D> mainHex = newHexagon(centre, r);
        List<Point2D> mainHexRot = newHexagonRot(centre, r);

        List<Point2D> mainHeights = newHexagonRot(centre, r * HEX_DIST_HEIGHT);
        List<Point2D> mainHeightsRot = newHexagon(centre, r * HEX_DIST_HEIGHT);

        List<Point2D> mainHeightsConcat = concatEdges(mainHeightsRot, mainHeights);

        builder.append(drawPolygon(mainHex, gray));
        builder.append(drawPolygon(mainHexRot, gray));
        builder.append(drawPolygon(mainHeights, gray));
        builder.append(drawPolygon(mainHeightsRot, gray));
        builder.append(drawPolygon(mainHeightsConcat, gray));
//        builder.append(drawPolygons(newHexHeights(centre,r), gray));
        builder.append(drawPolylines(newHexDiag(centre, r), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));
        builder.append(drawPolygon(newHexagon(centre, tile8.getInnerR()), gray));
        builder.append(drawPolygon(newHexagonRot(centre, tile8.getInnerR()), gray));

//        builder.append(highlightPoints(tile8.getPointsA()));
//        builder.append(highlightPoints(tile8.getPointsB()));
//        builder.append(highlightPoints(tile8.getPointsC()));
//        builder.append(highlightPoints(tile8.getHeights()));
//        builder.append(highlightPoints(tile8.getPointsD()));
//        builder.append(highlightPoints(tile8.getPointsE()));
//        builder.append(highlightPoints(tile8.getPointsF()));
        builder.append(highlightPoints(tile8.getPointsG()));
//        builder.append(highlightPoints(tile8.getPointsH()));
        builder.append(drawPolylines(tile8.getLines(), red));

        return builder.toString();
    }

    public static String newDesign9(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Tile9 tile9 = new Tile9(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        List<Point2D> outer = newHexagon(centre, r);
        List<Point2D> outerRot = newHexagonRot(centre, r);

        double rH = r * HEX_DIST_HEIGHT;

        builder.append(drawPolygon(outer, gray));
        builder.append(drawPolygon(outerRot, gray));
//        builder.append(drawPolylines(newHexHeightsRot(centre, r), gray));
//        builder.append(drawPolygons(newHexInnerTrianglesRot(centre, r), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));
        builder.append(drawPolylines(newHexDiag(centre, r), gray));

        builder.append(drawPolylines(concateOuterHexagons(outer, outerRot), gray));

//        out.add(drawPolygon(outerLayer2, gray));
//        out.add(drawPolygon(outerLayer2Rot, gray));

        builder.append(drawPolylines(concateOuterHexagons(tile9.getOuterLayer1(), tile9.getOuterLayer1Rot()), gray));
        builder.append(drawPolylines(concateOuterHexagons(tile9.getOuterLayer2(), tile9.getOuterLayer2Rot()), gray));
//        builder.append(highlightPoints(heightPoints));

        builder.append(drawPolygon(tile9.getInnerLayer1(), gray));
        builder.append(drawPolygon(tile9.getInnerLayer1Rot(), gray));

        builder.append(drawPolylines(buildLines(
                newHexEdge(centre, r * Tile9.INNER_R2, 0),
                asList(newHexEdgeRot(outerRot.get(5), r * Tile9.INNER_R1, 1))), gray));

        builder.append(drawPolylines(buildLines(
                newHexEdge(centre, r * Tile9.INNER_R2, 5),
                asList(newHexEdgeRot(outerRot.get(5), r * Tile9.INNER_R1, 3))), gray));

        builder.append(drawPolylines(buildLines(
                newHexEdgeRot(centre, r * Tile9.INNER_R2, 0),
                asList(newHexEdge(outer.get(0), r * Tile9.INNER_R1, 1))), gray));

        builder.append(drawPolylines(buildLines(
                newHexEdgeRot(centre, r * Tile9.INNER_R2, 5),
                asList(newHexEdge(outer.get(0), r * Tile9.INNER_R1, 5))), gray));

        builder.append(drawPolygons(newHexInnerTrianglesFull(centre, r * Tile9.INNER_R2), gray));
        builder.append(drawPolygons(newHexInnerTrianglesFullRot(centre, r * Tile9.INNER_R2), gray));

        builder.append(drawPolylines(concateOuterHexagons(
                newHexagon(outer.get(0), r * (1.0 - Tile9.OUTER_R1)),
                newHexagonRot(outer.get(0), r * (1.0 - Tile9.OUTER_R1))
        ), gray));

        builder.append(drawPolylines(concateOuterHexagons(
                newHexagon(outer.get(0), r * (1.0 - Tile9.OUTER_R2)),
                newHexagonRot(outer.get(0), r * (1.0 - Tile9.OUTER_R2))
        ), gray));

        for (Point2D edge : outerRot) {
            builder.append(drawPolygon(newHexagonRot(edge, r * Tile9.INNER_R1), blue));
        }

//        out.add(drawPolygon(newHexagonRot(outerRot.get(2), r * Tile9.INNER_R1), blue));
//        out.add(drawPolygon(newHexagonRot(outerRot.get(3), r * Tile9.INNER_R1), blue));
//        out.add(drawPolygon(newHexagonRot(outerRot.get(4), r * Tile9.INNER_R1), blue));
        builder.append(drawPolygon(newHexagonRot(centre, r * (1.0 - Tile9.INNER_R1)), gray));

//        builder.append(drawPolylines(Tile9.calcOuterLines1(centre, r), red));

        builder.append(drawPolygon(newHexagonRot(centre, r), red));
        builder.append(drawPolylines(tile9.getLines(), red));

        builder.append(highlightPoints(newHexagon(centre, r * Tile9.OUTER_R4)));
        builder.append(highlightPoints(newHexagonRot(centre, r * Tile9.OUTER_R4)));

//        builder.append(highlightPoints(tile9.getPointsA()));
//        builder.append(highlightPoints(tile9.getPointsB()));
//        builder.append(highlightPoints(tile9.getPointsC()));
//        builder.append(highlightPoints(tile9.getPointsD()));
//        builder.append(highlightPoints(tile9.getPointsE()));
//        builder.append(highlightPoints(tile9.getPointsF()));
//        builder.append(highlightPoints(tile9.getPointsG()));
//        builder.append(highlightPoints(tile9.getPointsH()));
//        builder.append(highlightPoints(tile9.getPointsI()));
//        builder.append(highlightPoints(tile9.getPointsJ()));
//        builder.append(highlightPoints(tile9.getPointsK()));
//        builder.append(highlightPoints(tile9.getPointsL()));
//        builder.append(highlightPoints(tile9.getPointsM()));
//        builder.append(highlightPoints(tile9.getPointsN()));
//        builder.append(highlightPoints(tile9.getPointsO()));
//        builder.append(highlightPoints(tile9.getPointsP()));
//        builder.append(highlightPoints(tile9.getPointsQ()));
//        builder.append(highlightPoints(tile9.getPointsR()));
//        builder.append(highlightPoints(tile9.getPointsS()));
//        builder.append(highlightPoints(tile9.getInnerLayer1()));
//        builder.append(highlightPoints(tile9.getInnerLayer1Rot()));
        builder.append(highlightPoints(tile9.getOuterLayer1()));
        builder.append(highlightPoints(tile9.getOuterLayer1Rot()));
        builder.append(highlightPoints(tile9.getOuterLayer2()));
        builder.append(highlightPoints(tile9.getOuterLayer2Rot()));

        return builder.toString();

    }

    public static String newDesign11(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Tile11 tile = new Tile11(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));

        for (Point2D edge : tile.getMainHexRot()) {
            builder.append(drawPolygon(newHexagon(edge, tile.getInnerR()), gray));
        }

        builder.append((newCircle(centre, tile.getInnerR(), gray)));
        for (Point2D edge : tile.getMainHeights()) {
            builder.append((newCircle(edge, r / 2.0, gray)));
        }

        builder.append(drawPolylines(newHexHeightsRot(centre, r), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));

        builder.append(highlightPoints(tile.getMainHeights()));
        builder.append(highlightPoints(tile.getPointsA()));
        builder.append(highlightPoints(tile.getPointsB()));
        builder.append(highlightPoints(tile.getPointsC()));
        builder.append(highlightPoints(tile.getPointsD()));
//        builder.append(highlightPoints(newHexagon(centre,tile.getInnerR2())));

        builder.append(drawPolylines(tile.getLines(), red));

        return builder.toString();
    }

    public static String newDesign12(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile12(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

//        final double newR = ((r * RECT_DIST_HEIGHT) / 6.0)/HEX_DIST_HEIGHT;
        final double newR = ((r * RECT_DIST_HEIGHT) / 5.0);

        builder.append(drawPolygon(newRectRot(centre, r), gray));
        builder.append(drawPolygon(newRectRot(centre, r * Tile12.RATIO_W), gray));
//        builder.append(drawPolygon(newHexagonRot(centre, r), gray));
        builder.append(drawPolygon(newHexagonRot(centre, newR), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));

        builder.append(drawPolygon(newHexagonRot(centre, 2 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 3 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 4 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 5 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 6 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 7 * newR), gray));
//        builder.append(drawPolygon(newHexStarTileRotated(centre, 2 * newR, HEX_DIST_DIAGONAL), blue));

//        builder.append(drawPolygon(newHexagonRot(newEdgeAt(centre, 2*newR, HEX_RADIANS_ROT.get(2)), newR), gray));

        builder.append(drawPolygons(tile.getPayload().getPolygons(), red));
        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();

    }

    public static String newDesign13(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile13(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        double newR = r / 3.0;
        double newR2 = newR * PolygonTools.HEX_DIST_NEW_CENTRE;

        List<Point2D> layerOut = newHexagon(centre, 2.0 * newR);

        builder.append(drawPolygon(newHexagon(centre, r), gray));
        builder.append(drawPolygon(newHexagon(centre, newR), gray));

        builder.append(drawPolygon(layerOut, gray));
        builder.append(drawPolygon(newHexagonRot(centre, newR2), gray));

        builder.append(drawPolygon(newHexagon(layerOut.get(0), newR), gray));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign14(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Tile14 tile = new Tile14(centre, r);

        StringBuilder builder = new StringBuilder();

        double newR = r / 4.0;

        builder.append(drawPolygon(newHexagon(centre, r), gray));
        builder.append(drawPolylines(newHexDiag(centre, r), gray));

        for (int i = 1; i < 4; i++) {
            builder.append(drawPolygon(newHexagon(centre, i * newR), gray));
        }

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        builder.append(highlightPoints(tile.getPointsA()));
        builder.append(highlightPoints(tile.getPointsB()));
        builder.append(highlightPoints(tile.getPointsC()));

        return builder.toString();
    }

    public static String newDesign15(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        double newR = (r * sin(PI_QUARTER)) / 2.5;
        Tile tile = new Tile15(centre, r);

        StringBuilder builder = new StringBuilder();

        builder.append(drawPolygon(newRectRot(centre, r), gray));
        builder.append(drawPolygon(newRectRot(centre, r * Tile15.RATIO_W), gray));
        builder.append(drawPolygon(newHexagonRot(centre, newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 2 * newR), gray));
        builder.append(drawPolygon(newHexagonRot(centre, 5 * newR), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, 5 * newR), gray));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign16(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Tile tile = new Tile16(centre, r);

        double newR = r / 9.0;

        StringBuilder builder = new StringBuilder();

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));

        for (int i = 1; i < 9; i++) {
            builder.append(drawPolygon(newHexagonRot(centre, i * newR), gray));
        }

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign17(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile17(centre, r);

        double newR = r / 3.0;

        builder.append(drawPolygon(newHexagon(centre, r), gray));
        builder.append(drawPolygon(newHexagon(centre, newR), gray));
        builder.append(highlightPoints(newHexagon(centre, 2 * newR)));

        builder.append(drawPolygons(tile.getPayload().getPolygons(), red));

        return builder.toString();
    }

    public static String newDesign19(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile19(centre, r);

        double newR = r / 5.0;

        List<Point2D> outerLayer1 = newHexagonRot(centre, 2 * newR);
        List<Point2D> outerLayer2 = newHexagonRot(centre, 3 * newR);
        List<Point2D> outerLayer3 = newHexagonRot(centre, 4 * newR);

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));
        builder.append(drawPolygon(newHexagonRot(centre, newR), gray));
        builder.append(highlightPoints(outerLayer1));
        builder.append(highlightPoints(outerLayer2));
        builder.append(highlightPoints(outerLayer3));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign20(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        double newR = r / 6.0;

        Tile tile = new Tile20(centre, r);

        builder.append(drawPolygon(newHexagon(centre, r), gray));
        builder.append(drawPolygon(newHexagon(centre, newR), gray));
        builder.append(drawPolygon(newHexagon(centre, 2 * newR), gray));
        builder.append(drawPolygon(newHexagon(centre, 3 * newR), gray));
        builder.append(drawPolygon(newHexagon(centre, 4 * newR), gray));
        builder.append(drawPolygon(newHexagon(centre, 5 * newR), gray));

        builder.append(drawPolylines(newHexDiag(centre, r), gray));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign21(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile21(centre, r);

        double newR = r / 6.0;

        builder.append(drawPolygon(newHexagon(centre, r), gray));
        builder.append(drawPolygon(newHexagon(centre, newR), gray));
        builder.append(drawPolygon(newHexagon(centre, 2 * newR), gray));
        builder.append(drawPolygon(newHexagon(centre, 3 * newR), gray));
        builder.append(drawPolygon(newHexagon(centre, 4 * newR), gray));
        builder.append(drawPolygon(newHexagon(centre, 5 * newR), gray));

        builder.append(drawPolylines(newHexDiag(centre, r), gray));
        builder.append(drawPolylines(newHexHeights(centre, r), gray));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign22(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        double newH = r / 16.0;
        double newR = newH / HEX_DIST_HEIGHT;

        Tile tile = new Tile22(centre, r);

        builder.append(drawPolygon(newHexagon(centre, r), gray));

        builder.append(drawPolylines(newHexDiag(centre, r), gray));
        builder.append(drawPolylines(newHexHeights(centre, r), gray));

        for (int i = 1; i < 16; i++) {
            builder.append(drawPolygon(newHexagonRot(centre, i * newR), gray));
        }

        builder.append(drawPolygons(tile.getPayload().getPolygons(), blue));
        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign23(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile23(centre, r);

        double newH = r / 2.0;
        double newR = newH / HEX_DIST_HEIGHT;

        List<Point2D> edges = newHexagon(centre, newR);

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));
//        builder.append(drawPolygon(newHexagon(centre, newR), gray));

        for (Point2D edge : edges) {
            builder.append(drawPolygon(newHexagon(edge, newR), gray));

        }

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));
        builder.append(highlightPoints(edges));

        return builder.toString();
    }

    public static String newDesign24(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile24(centre, r);

        double newR = r / 3.0;

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));

        builder.append(drawPolygon(newHexagonRot(centre, newR), gray));
        //newHexDiag(newEdgeAt(centre,  1* newR, HEX_RADIANS_ROT[0]))
        builder.append(drawPolygon(newHexagonRot(newEdgeAt(centre, 1 * newR, HEX_RADIANS_ROT[0]), newR), gray));
//        builder.append(drawPolygon(newHexagonRot(newEdgeAt(centre, 3 * newR, HEX_RADIANS_ROT[0]), newR), gray));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign25(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile25(centre, r);

        double height = r * HEX_DIST_HEIGHT;

//        double newR = height * HEX_DIST_HEIGHT - (height / 2.0);

        double phi1 = Math.atan((r / 2.0) / (height * 2));
        double phi2 = HEX_PHI - phi1;
        double d1 = (height / 2.0) * tan(phi2);
        double newR = height * HEX_DIST_HEIGHT - d1;
//        double d2 = (height/2.0)*tan(phi2);
//
        List<Point2D> layer1 = newHexagon(centre, r);
        List<Point2D> layerRot = newHexagonRot(centre, r * HEX_DIST_HEIGHT);

        List<List<Point2D>> lines = asList(
                asList(
                        layerRot.get(1),
                        layer1.get(5)
                ),
                asList(
                        layerRot.get(1),
                        layer1.get(4)
                ),
                asList(
                        layerRot.get(0),
                        layerRot.get(3)
                ),
                asList(
                        layerRot.get(2),
                        layerRot.get(5)
                ),
                asList(
                        layerRot.get(1),
                        layerRot.get(4)
                ),
                asList(
                        layerRot.get(0),
                        layer1.get(3)
                ),
                asList(
                        layerRot.get(2),
                        layer1.get(0)
                ),
                asList(
                        layer1.get(1),
                        layer1.get(4)
                )
        );

        builder.append(drawPolygon(layer1, gray));
        builder.append(drawPolygon(layerRot, gray));
        builder.append(drawPolylines(lines, gray));

        builder.append(highlightPoints(newHexagon(centre, newR)));
        builder.append(highlightPoints(newHexagon(centre, height * HEX_DIST_HEIGHT)));
//        builder.append(highlightPoints(newHexagon(centre, d1)));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign26(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile26(centre, r);

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));
        builder.append(drawPolygon(newHexagonRot(centre, r * HEX_DIST_DAM), gray));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign27(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

//        double d = (1 / 2.0) * atan(HEX_PHI - RECT_PHI_HALF);
//        double d2 = r * (HEX_DIST_HEIGHT - d);

        Tile tile = new Tile27(centre, r);

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));
//        builder.append(drawPolygon(newHexagonRot(centre, r * HEX_DIST_DAM), gray));

        builder.append(highlightPoints(newHexagon(centre, r * HEX_DIST_HEX_TO_RECT)));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign28(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Tile tile = new Tile28(centre, r);

        StringBuilder builder = new StringBuilder();

        List<Point2D> outerLayer = newHexagon(centre, r);

        double phi = (PI - 2 * HEX_PHI) / 2.0;
        double d1 = tan(phi) * 0.5;
        double d2 = HEX_DIST_HEIGHT - d1;

        List<List<Point2D>> lines = asList(
                asList(
                        outerLayer.get(1),
                        outerLayer.get(3)

                ), asList(
                outerLayer.get(2),
                outerLayer.get(0)

        )

        );

        builder.append(drawPolylines(lines, gray));
        builder.append(drawPolylines(newHexDiag(centre, r), gray));
        builder.append(drawPolylines(newHexHeights(centre, r), gray));

        builder.append(highlightPoints(newHexagonRot(centre, r * d2)));
        builder.append(highlightPoints(newHexagon(centre, r * d2 * HEX_DIST_HEX_TO_RECT)));

        builder.append(drawPolygon(outerLayer, gray));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign29(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Tile tile = new Tile29(centre, r);

        StringBuilder builder = new StringBuilder();

        double d1 = HEX_DIST_HEIGHT * HEX_DIST_HEIGHT;
        double d2 = 1 - d1;
        double d3 = d2 * cos(PI_QUARTER);
        double d4 = 2 * d3;
        double d5 = 0.5 - d4;
        double d6 = HEX_DIST_HEIGHT - d5;

        List<Point2D> layer1 = newHexagon(centre, r * HEX_DIST_HEIGHT);

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));
        builder.append(drawPolylines(newHexHeightsRot(centre, r), gray));
        builder.append(newPolyline(asList(
                layer1.get(0), layer1.get(5)
        ), gray));

        builder.append(highlightPoints(newHexagonRot(centre, r * d1)));
        builder.append(highlightPoints(newHexagon(centre, r * d6)));
        builder.append(highlightPoints(newHexagonRot(centre, r * d6 * HEX_DIST_HEX_TO_RECT)));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesign30(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Tile tile = new Tile30(centre, r);

        StringBuilder builder = new StringBuilder();

        double d1 = HEX_DIST_HEIGHT * HEX_DIST_HEIGHT;
        double d2 = 1 - d1;
        double d3 = d2 * cos(PI_QUARTER);
        double d4 = 2 * d3;
        double d5 = 0.5 - d4;
        double d6 = HEX_DIST_HEIGHT - d5;

        List<Point2D> layer1 = newHexagon(centre, r * HEX_DIST_HEIGHT);

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));
        builder.append(drawPolylines(newHexDiagRot(centre, r), gray));
        builder.append(drawPolylines(newHexHeightsRot(centre, r), gray));
        builder.append(newPolyline(asList(
                layer1.get(0), layer1.get(5)
        ), gray));

        builder.append(highlightPoints(newHexagonRot(centre, r * d1)));
        builder.append(highlightPoints(newHexagon(centre, r * d6)));
        builder.append(highlightPoints(newHexagonRot(centre, r * d6 * HEX_DIST_HEX_TO_RECT)));

        builder.append(drawPolylines(tile.getPayload().getPolylines(), red));

        return builder.toString();
    }

    public static String newDesignTemplate(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        return builder.toString();
    }

    public static String newDesign39(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        StringBuilder builder = new StringBuilder();

        builder.append(drawPolygon(newHexagonRot(centre, r), gray));
        builder.append(drawPolygon(newHexagon(centre, r / HEX_DIST_HEIGHT), gray));

//        builder.append(drawPolygon(newHexagon(newEdgeAt(centre, r*HEX_DIST_NEW_CENTRE, HEX_RADIANS[1]),
//                r*HEX_DIST_HEIGHT), gray));

        return builder.toString();
    }

}
