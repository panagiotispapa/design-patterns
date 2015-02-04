package com.design.islamic;

import com.design.common.PolygonTools;
import com.design.islamic.model.Tile;
import com.design.islamic.model.hex.*;
import com.design.islamic.model.tiles.CentreTransform;
import com.design.islamic.model.tiles.Hex;
import com.design.islamic.model.tiles.HexGrid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.DrawSegmentsInstructions.*;
import static com.design.islamic.model.DrawSegmentsInstructions.CombinedVertexes.OUTER_VERTEXES;
import static com.design.islamic.model.DrawSegmentsInstructions.CombinedVertexes.STAR;
import static com.design.islamic.model.tiles.CentreTransform.newTransform;
import static com.design.islamic.model.tiles.Hex.*;
import static com.design.islamic.model.tiles.Hex.Type.HOR;
import static com.design.islamic.model.tiles.Hex.Type.VER;
import static java.lang.Math.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class HexDesignHelper {

    public static String newStarDesign1(Pair<Point2D, Double> ic) {

        StringBuilder builder = new StringBuilder();

        Hex main = Hex.newHex(NO_TRANSFORMS, 1, HOR);
        Hex outerCentres = Hex.newHex(NO_TRANSFORMS, HEX_DIST_NEW_CENTRE, VER);
        Hex layer1 = Hex.newHex(NO_TRANSFORMS, HEX_DIST_OUTER_CIRCLE, VER);
        Hex layer2 = Hex.newHex(NO_TRANSFORMS, HEX_DIST_DIAGONAL, VER);
        Hex layer3 = Hex.newHex(NO_TRANSFORMS, HEX_DIST3, VER);
        Hex layer4 = Hex.newHex(NO_TRANSFORMS, 1, VER);
        Hex layerMiddle = Hex.newHex(NO_TRANSFORMS, 0.5, HOR);

        final List<Point2D> outsideCentres = outerCentres.vertexes(ic);

        String black = newStyle("black", 2, 1);
        String blue = newStyle("blue", 1, 1);
        final String gray = newStyle("gray", 1, 1);
        final String green = newStyle("green", 1, 1);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);
        Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star = combineVertexes(ic);
        Function<Hex, String> highlight = highlightVertexes(ic);
        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Triple<Hex, Hex, CombinedVertexes>, String> greenStar = star.andThen(toPolylines(green));

        builder
                .append(newCircle(ic.getLeft(), ic.getRight(), black))

                .append(outsideCentres.stream().map(c_centre -> newCircle(c_centre, ic.getRight(), gray)).collect(toList()))
                .append(outsideCentres.stream().map(c_centre -> newPolyline(asList(ic.getLeft(), c_centre), gray)).collect(toList()))

                .append(grayLines.apply(Pair.of(main, ALL_LINES)))
                .append(grayLines.apply(Pair.of(layer1, PERIMETER)))
                .append(grayLines.apply(Pair.of(layerMiddle, PERIMETER)))
                .append(grayLines.apply(Pair.of(outerCentres, PERIMETER)))
                .append(greenStar.apply(Triple.of(layer4, layer1.getMirror(), STAR)))
                .append(greenStar.apply(Triple.of(layer4, layer2.getMirror(), STAR)))
                .append(greenStar.apply(Triple.of(layer4, layer3.getMirror(), STAR)))
                .append(highlight.apply(outerCentres))
                .append(highlight.apply(layer1))
                .append(highlight.apply(layer2))
                .append(highlight.apply(layer3))
                .append(highlight.apply(layer4))
        ;

        return builder.toString();

    }

    public static String newStarDesign2(Pair<Point2D, Double> ic) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);

        Hex outer = newHex(NO_TRANSFORMS, 1, VER);
        Hex outerH = outer.getInternal();
        Hex inner = newHex(NO_TRANSFORMS, 0.5, VER);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);
        Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star = combineVertexes(ic);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));

        Function<Triple<Hex, Hex, CombinedVertexes>, String> greenStar = star.andThen(toPolylines(green));

        return
                builder
                        .append(grayLines.apply(Pair.of(outer, PERIMETER)))
                        .append(grayLines.apply(Pair.of(outer, DIAGONALS)))
                        .append(grayLines.apply(Pair.of(inner, PERIMETER)))
                        .append(greenStar.apply(Triple.of(inner, outerH, STAR)))
                        .append(highlight.apply(outer))
                        .append(highlight.apply(outerH))
                        .append(highlight.apply(inner))
                        .toString()

                ;

    }

    public static String newStarDesign3(Pair<Point2D, Double> ic) {

        Tile3 tile3 = new Tile3(ic.getLeft(), ic.getRight());

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String blue = newStyle(BLUE, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);
        Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star = combineVertexes(ic);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Vertex>>>, String> blueLines = lines.andThen(toPolylines(blue));
        Function<Triple<Hex, Hex, CombinedVertexes>, String> blueStar = star.andThen(toPolylines(blue));

        Hex main = newHex(NO_TRANSFORMS, 1, VER);
        Hex inner = newHex(NO_TRANSFORMS, HEX_DIST_EQ1, VER);
        Hex inner2 = Hex.newHex(NO_TRANSFORMS, HEX_DIST_EQ1 * 0.5, VER);

        CentreTransform cTransform1 = newTransform(HEX_DIST_EQ1, VER, Vertex.ONE);
        CentreTransform.VertexBuilder cTransform2 = CentreTransform.transform().ratio(HEX_DIST_EQ1 * HEX_DIST_HEIGHT).type(HOR);

        Hex ext0 = Hex.newHex(cTransform1, 1 - HEX_DIST_EQ1, VER);
        Hex ext0_1 = Hex.newHex(cTransform1, (1 - HEX_DIST_EQ1) * HEX_DIST_DIAGONAL_ROTATED, HOR);

        Hex ext0_2 = Hex.newHex(cTransform2.vertex(Vertex.ONE), HEX_DIST_EQ1 * HEX_DIST_DIAGONAL_ROTATED * 0.5, VER);
        Hex ext0_3 = Hex.newHex(cTransform2.vertex(Vertex.SIX), HEX_DIST_EQ1 * HEX_DIST_DIAGONAL_ROTATED * 0.5, VER);

        List<List<Point2D>> extConfigs = buildExtConfigForDesign3(inner.vertexes(ic), ic.getRight() * (1 - HEX_DIST_EQ1));

        builder
                .append(grayLines.apply(Pair.of(main, PERIMETER)))
                .append(grayLines.apply(Pair.of(ext0, ALL_LINES)))

                .append(blueLines.apply(Pair.of(ext0_1, PERIMETER)))
                .append(blueLines.apply(Pair.of(ext0_2, PERIMETER)))
                .append(blueLines.apply(Pair.of(ext0_3, PERIMETER)))

                .append(blueStar.apply(Triple.of(inner2, inner.getInternal(), STAR)))

                .append(greenLines.apply(Pair.of(inner, PERIMETER)))
                .append(drawPolylines(extConfigs, red))
                .append(drawPolylines(tile3.getLines(), red))
                .append(drawPolylines(tile3.getLines2(), red))
                .append(highlight.apply(inner.getInternal()))
                .append(highlight.apply(inner))
                .append(highlight.apply(inner2))
                .append(highlightPoints().apply(extConfigs.get(0)))

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

    public static String newStarDesign4(Pair<Point2D, Double> ic) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);
        Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star = combineVertexes(ic);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));

        Hex layer1 = Hex.newHex(1, VER);
        Hex layer2 = Hex.newHex(HEX_DIST_DIAGONAL, HOR);
        Hex layer3 = Hex.newHex(HEX_DIST_DIAGONAL * HEX_DIST_DIAGONAL, VER);
        Hex layerExt = Hex.newHex(layer3.getRatio() * HEX_DIST_NEW_CENTRE, HOR);
        Hex layerExtPol1 = Hex.newHex(newTransform(layerExt.getRatio(), layerExt.getType(), Vertex.ONE), layer3.getRatio(), VER);

        return builder
                .append(grayLines.apply(Pair.of(layer1, ALL_LINES)))
                .append(grayLines.apply(Pair.of(layer2, ALL_LINES)))
                .append(grayLines.apply(Pair.of(layerExtPol1, ALL_LINES)))
                .append(grayLines.apply(Pair.of(layer1.getInternal(), DIAGONALS)))
                .append(greenLines.apply(Pair.of(layer3, PERIMETER)))
                .append(drawPolylines(Tile4.buildExtConf(ic.getLeft(), ic.getRight() * HEX_DIST_DIAGONAL * HEX_DIST_DIAGONAL), red))
                .append(highlight.apply(layerExt))
                .toString();

    }

    public static String newDesignTest(Pair<Point2D, Double> ic) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);

        Hex layer1 = newHex(1, VER);
        Hex layer2 = newHex(newTransform(1, VER, Vertex.ONE), 0.5, VER);

        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = Vertex.vertexesToPointsSingle(ic).andThen(toPolylines(gray));
        List<Point2D> grid = HexGrid.grid(ic.getLeft(), ic.getRight() / 4, HexGrid.TYPE.VER, 16);
        return builder


//                .append(grid.stream().map(p -> Vertex.vertexesToPointsSingle(Pair.of(p, ic.getRight())).andThen(toPolylines(newStyle(GRAY, 1, 0.5)))).map(f -> f.apply(Pair.of(newHex(1.0 / 4.0, HOR), PERIMETER))).collect(joining()))


                .append(grayLines.apply(Pair.of(layer1, PERIMETER)))
                .append(grayLines.apply(Pair.of(layer1, DIAGONALS)))
//                .append(Hex.Vertex.vertexesToPoints(0, ic).andThen(toPolylines(blue)).apply(Pair.of(layer2, PERIMETER2)))
//                .append(Hex.Vertex.vertexesToPoints(1, ic).andThen(toPolylines(blue)).apply(Pair.of(layer2, PERIMETER2)))

                .append(Vertex.vertexesToPointsFull(ic).andThen(toPolylines(blue)).apply(Pair.of(layer2, PERIMETER2)))

                .append(highlightPoints().apply(grid))
//                .append(grid.stream().map(p->Pair.of(newHex( 1.0/4.0,HOR),PERIMETER)).map(Vertex.vertexesToPointsSingle(Pair.of(p,))).collect(joining()))

//                .append(toPolylines(blue).apply(collectii))

                .toString()
                ;

    }

    public static String newStarDesign5(Pair<Point2D, Double> ic) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);
        Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star = combineVertexes(ic);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Vertex>>>, String> blueLines = lines.andThen(toPolylines(blue));
        Function<Triple<Hex, Hex, CombinedVertexes>, String> blueStar = star.andThen(toPolylines(blue));

        Hex layer1 = newHex(1, VER);
        Hex layer2 = newHex(HEX_DIST_DIAGONAL, HOR);
        Hex layer3 = newHex(HEX_DIST_DIAGONAL * HEX_DIST_DIAGONAL, VER);
        Hex outer = newHex(layer3.getRatio() * HEX_DIST_NEW_CENTRE, HOR);

        List<List<Point2D>> outerLines = Tile5.buildOuterLines(outer.vertexes(ic), layer3.getRatio() * ic.getRight());

        return
                builder
                        .append(grayLines.apply(Pair.of(layer1, ALL_LINES)))
                        .append(grayLines.apply(Pair.of(layer2, ALL_LINES)))

                        .append(blueLines.apply(Pair.of(layer2, PERIMETER)))
                        .append(greenLines.apply(Pair.of(layer3, PERIMETER)))

                        .append(drawPolylines(outerLines, red))
                        .append(highlight.apply(outer))
                        .toString()
                ;

    }

    public static String newStarDesign6(Pair<Point2D, Double> ic) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);
        Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star = combineVertexes(ic);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Vertex>>>, String> blueLines = lines.andThen(toPolylines(blue));
        Function<Triple<Hex, Hex, CombinedVertexes>, String> blueStar = star.andThen(toPolylines(blue));

        Hex layer1 = newHex(1, VER);
        Hex layer2 = newHex(HEX_DIST_DIAGONAL_ROTATED, VER);
        Hex outer = newHex(HEX_DIST_DIAGONAL_ROTATED * HEX_DIST_NEW_CENTRE, HOR);
        Hex outerInner = newHex(newTransform(outer, Vertex.ONE), HEX_DIST_DIAGONAL_ROTATED * HEX_DIST_DIAGONAL_ROTATED, VER);

        return builder
                .append(grayLines.apply(Pair.of(layer1, ALL_LINES)))
                .append(grayLines.apply(Pair.of(outerInner, PERIMETER)))
                .append(grayLines.apply(Pair.of(layer1.getInternal(), INNER_TRIANGLES)))
                .append(drawPolylines(Tile6.buildOuterLines(outer.vertexes(ic), ic.getRight() * HEX_DIST_DIAGONAL_ROTATED), red))
                .append(highlight.apply(layer2))
                .append(highlight.apply(outer))
                .toString();

    }

    public static String newDesign7(Pair<Point2D, Double> ic) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);
        Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star = combineVertexes(ic);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Vertex>>>, String> blueLines = lines.andThen(toPolylines(blue));
        Function<Triple<Hex, Hex, CombinedVertexes>, String> blueStar = star.andThen(toPolylines(blue));

        Double r = ic.getRight();
        Point2D centre = ic.getLeft();
        Tile7 tile7 = new Tile7(centre, r);

        Hex outer = newHex(1, HOR);
        Hex outerHeights = outer.getInternal();
        Hex inner = newHex(HEX_DIST_DIAGONAL_ROTATED, HOR);
        Hex inner2 = newHex(HEX_DIST_DIAGONAL_ROTATED * HEX_DIST_DIAGONAL_ROTATED, HOR);
        Hex outerSmall = newHex(newTransform(outer.getInternal(), Vertex.ONE), 1 - HEX_DIST_HEIGHT, VER);

        return
                builder
                        .append(grayLines.apply(Pair.of(outer, PERIMETER)))
                        .append(grayLines.apply(Pair.of(outer.getMirror(), PERIMETER)))

                        .append(grayLines.apply(Pair.of(outerHeights, DIAGONALS)))
                        .append(grayLines.apply(Pair.of(outerHeights.getMirror(), DIAGONALS)))

                        .append(blueLines.apply(Pair.of(inner, PERIMETER)))
                        .append(blueLines.apply(Pair.of(inner.getMirror(), PERIMETER)))
                        .append(blueLines.apply(Pair.of(inner2, PERIMETER)))
                        .append(blueLines.apply(Pair.of(inner2.getMirror(), PERIMETER)))
                        .append(blueLines.apply(Pair.of(outerSmall, PERIMETER)))

                        .append(grayLines.apply(Pair.of(inner.getInternal(), INNER_TRIANGLES)))
                        .append(grayLines.apply(Pair.of(inner.getInternal().getMirror(), INNER_TRIANGLES)))
                        .append(drawPolylines(tile7.getLines(), red))
                        .append(greenLines.apply(Pair.of(outerSmall.getInternal(), DIAGONALS)))
                        .append(highlight.apply(outerHeights))

                        .toString()
                ;

    }

    public static String newDesign8(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Pair<Point2D, Double> ic = Pair.of(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);
        Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star = combineVertexes(ic);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Vertex>>>, String> blueLines = lines.andThen(toPolylines(blue));
        Function<Triple<Hex, Hex, CombinedVertexes>, String> blueStar = star.andThen(toPolylines(blue));

        Tile8 tile8 = new Tile8(centre, r);

        Hex main = newHex(1, HOR);
        Hex mainInternal = main.getInternal();
        Hex tile = newHex(tile8.getInnerR() / r, HOR);

        return

                builder
                        .append(
                                Stream.of(
                                        Pair.of(main, PERIMETER),
                                        Pair.of(main.getMirror(), PERIMETER),
                                        Pair.of(mainInternal, PERIMETER),
                                        Pair.of(mainInternal.getMirror(), PERIMETER),
                                        Pair.of(tile, PERIMETER),
                                        Pair.of(tile.getMirror(), PERIMETER),
                                        Pair.of(main, DIAGONALS),
                                        Pair.of(main.getMirror(), DIAGONALS)
                                ).map(grayLines).collect(joining())
                        )
                        .append(blueStar.apply(Triple.of(mainInternal, mainInternal.getMirror(), STAR)))

//                .append(highlightPoints().apply(tile8.getPointsA()))
//                .append(highlightPoints().apply(tile8.getPointsB()))
//                .append(highlightPoints().apply(tile8.getPointsC()))
//                .append(highlightPoints().apply(tile8.getPointsD()))
                        .append(highlightPoints().apply(tile8.getPointsG()))

                        .append(drawPolylines(tile8.getLines(), red))

                        .toString()
                ;
    }

    public static String newDesign9(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Pair<Point2D, Double> ic = Pair.of(centre, r);
        Tile9 tile9 = new Tile9(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> linesFull = Vertex.vertexesToPointsFull(ic);

        Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star = combineVertexes(ic);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> redLines = lines.andThen(toPolylines(red));
        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Vertex>>>, String> blueLines = lines.andThen(toPolylines(blue));
        Function<Triple<Hex, Hex, CombinedVertexes>, String> grayStar = star.andThen(toPolylines(gray));

        Hex outerr = newHex(1, HOR);
        Hex outer1 = newHex(Tile9.OUTER_R1, HOR);
        Hex outer2 = newHex(Tile9.OUTER_R2, HOR);
        Hex inner1 = newHex(Tile9.INNER_R1, HOR);
        Hex inner2 = newHex(Tile9.INNER_R2, HOR);
        Hex outer4 = newHex(Tile9.OUTER_R4, HOR);

        Hex outerrLayer = newHex(newTransform(outerr.getMirror(), Vertex.ONE), Tile9.INNER_R1, VER);
        List<Point2D> outer = newHexagon(centre, r);
        List<Point2D> outerRot = newHexagonRot(centre, r);

        Hex outer1T = newHex(newTransform(outerr, Hex.Vertex.ONE), 1.0 - Tile9.OUTER_R1, HOR);
        Hex outer2T = newHex(newTransform(outerr, Hex.Vertex.ONE), 1.0 - Tile9.OUTER_R2, HOR);

        builder
                .append(
                        Stream.of(
                                Pair.of(outerr, PERIMETER),
                                Pair.of(outerr.getMirror(), PERIMETER),
                                Pair.of(inner1, PERIMETER),
                                Pair.of(inner1.getMirror(), PERIMETER),
                                Pair.of(outerr, DIAGONALS),
                                Pair.of(outerr.getMirror(), DIAGONALS)
                        ).map(grayLines).collect(joining())
                )

                .append(grayStar.apply(Triple.of(outerr, outerr.getMirror(), OUTER_VERTEXES)))
                .append(grayStar.apply(Triple.of(outer1, outer1.getMirror(), OUTER_VERTEXES)))
                .append(grayStar.apply(Triple.of(outer2, outer2.getMirror(), OUTER_VERTEXES)))
        ;

//        builder.append(drawPolygon(tile9.getInnerLayer1(), gray));
//        builder.append(drawPolygon(tile9.getInnerLayer1Rot(), gray));

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

        builder
                .append(grayLines.apply(Pair.of(inner2, INNER_TRIANGLES)))
                .append(grayLines.apply(Pair.of(inner2.getMirror(), INNER_TRIANGLES)))
                .append(grayLines.apply(Pair.of(newHex((1.0 - Tile9.INNER_R1), VER), PERIMETER)))
//
                .append(redLines.apply(Pair.of(outerr.getMirror(), PERIMETER)))
                .append(drawPolylines(tile9.getLines(), red))
//
//                .append(grayStar.apply(Triple.of(outer1T, outer1T.getMirror(), OUTER_VERTEXES)))
//                .append(grayStar.apply(Triple.of(outer2T, outer2T.getMirror(), OUTER_VERTEXES)))
//
                .append(Stream.of(
                        outer4,
                        outer4.getMirror()
                ).map(highlight).collect(joining()))
                .append(Stream.of(
                        tile9.getOuterLayer1(),
                        tile9.getOuterLayer1Rot(),
                        tile9.getOuterLayer2(),
                        tile9.getOuterLayer2Rot()
                ).map(highlightPoints()).collect(joining()))
                .append(linesFull.andThen(toPolylines(blue)).apply(Pair.of(outerrLayer, PERIMETER)))
        ;

        builder.append(drawPolylines(concateOuterHexagons(
                newHexagon(outer.get(0), r * (1.0 - Tile9.OUTER_R1)),
                newHexagonRot(outer.get(0), r * (1.0 - Tile9.OUTER_R1))
        ), gray));
//
        builder.append(drawPolylines(concateOuterHexagons(
                newHexagon(outer.get(0), r * (1.0 - Tile9.OUTER_R2)),
                newHexagonRot(outer.get(0), r * (1.0 - Tile9.OUTER_R2))
        ), gray));

//        out.add(drawPolygon(newHexagonRot(outerRot.get(2), r * Tile9.INNER_R1), blue));
//        out.add(drawPolygon(newHexagonRot(outerRot.get(3), r * Tile9.INNER_R1), blue));
//        out.add(drawPolygon(newHexagonRot(outerRot.get(4), r * Tile9.INNER_R1), blue));
//        builder.append(drawPolylines(Tile9.calcOuterLines1(centre, r), red));

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

        return builder.toString();

    }

    public static String newDesign11(final Point2D centre, final double r) {

        StringBuilder builder = new StringBuilder();

        Tile11 tile = new Tile11(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Pair<Point2D, Double> ic = Pair.of(centre, r);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> linesFull = Vertex.vertexesToPointsFull(ic);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);
        Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star = combineVertexes(ic);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Vertex>>>, String> blueLines = lines.andThen(toPolylines(blue));
        Function<Triple<Hex, Hex, CombinedVertexes>, String> blueStar = star.andThen(toPolylines(blue));

        Hex main = newHex(1, VER);
        Hex layerInner = newHex(newTransform(main, Vertex.ONE), tile.getInnerR() / r, HOR);

        return
                builder
                        .append(linesFull.andThen(toPolylines(gray)).apply(Pair.of(layerInner, PERIMETER)))
                        .append((newCircle(centre, tile.getInnerR(), gray)))
                        .append(Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(main, DIAGONALS),
                                Pair.of(main.getInternal(), DIAGONALS)
                        ).map(grayLines).collect(joining()))
                        .append(tile.getMainHeights().stream().map(edge -> newCircle(edge, r / 2.0, gray)).collect(joining()))

                        .append(
                                Stream.of(
                                        tile.getMainHeights(),
                                        tile.getPointsA(),
                                        tile.getPointsB(),
                                        tile.getPointsC(),
                                        tile.getPointsD()
                                ).map(highlightPoints()).collect(joining())
                        )
                        .append(drawPolylines(tile.getLines(), red))
                        .toString()
                ;

//        builder.append(highlightPoints(newHexagon(centre,tile.getInnerR2())));

    }

    public static String newDesign12(final Point2D centre, final double r) {
        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile12(centre, r);

        Pair<Point2D, Double> ic = Pair.of(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, List<List<Point2D>>> lines = Hex.Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> blueLines = lines.andThen(toPolylines(blue));

        final double ratio = RECT_DIST_HEIGHT / 5.0;

//        final double newR = ((r * RECT_DIST_HEIGHT) / 6.0)/HEX_DIST_HEIGHT;

//        builder.append(drawPolygon(newHexagonRot(centre, r), gray));

        return
                builder
                        .append(drawPolygon(newRectRot(centre, r), gray))
                        .append(drawPolygon(newRectRot(centre, r * Tile12.RATIO_W), gray))
                        .append(Stream.of(
                                Pair.of(newHex(ratio, VER), PERIMETER),
                                Pair.of(newHex(1, VER), DIAGONALS),
                                Pair.of(newHex(2 * ratio, VER), PERIMETER),
                                Pair.of(newHex(3 * ratio, VER), PERIMETER),
                                Pair.of(newHex(4 * ratio, VER), PERIMETER),
                                Pair.of(newHex(5 * ratio, VER), PERIMETER),
                                Pair.of(newHex(6 * ratio, VER), PERIMETER),
                                Pair.of(newHex(7 * ratio, VER), PERIMETER)
                        ).map(grayLines).collect(joining()))
                        .append(drawPolygons(tile.getPayload().getPolygons(), red))
                        .append(drawPolylines(tile.getPayload().getPolylines(), red))
                        .toString();

//        builder.append(drawPolygon(newHexStarTileRotated(centre, 2 * newR, HEX_DIST_DIAGONAL), blue));

//        builder.append(drawPolygon(newHexagonRot(newEdgeAt(centre, 2*newR, HEX_RADIANS_ROT.get(2)), newR), gray));

    }

    public static String newDesign13(final Point2D centre, final double r) {
        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile13(centre, r);

        Pair<Point2D, Double> ic = Pair.of(centre, r);

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, List<List<Point2D>>> lines = Hex.Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));

        double newR = r / 3.0;
        double newR2 = newR * PolygonTools.HEX_DIST_NEW_CENTRE;

        double ratio = 1 / 3.0;
        double ratio2 = ratio * PolygonTools.HEX_DIST_NEW_CENTRE;

        Hex out = newHex(2.0 * ratio, HOR);

        return
                builder
                        .append(Stream.of(
                                Pair.of(newHex(1, HOR), PERIMETER),
                                Pair.of(newHex(ratio, HOR), PERIMETER),
                                Pair.of(newHex(ratio2, VER), PERIMETER),
                                Pair.of(newHex(newTransform(out, Vertex.ONE), ratio, HOR), PERIMETER),
                                Pair.of(out, PERIMETER)
                        ).map(grayLines).collect(joining())
                        )
                        .append(drawPolylines(tile.getPayload().getPolylines(), red))

                        .toString();

    }

    public static String newDesign14(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);
        Pair<Point2D, Double> ic = Pair.of(centre, r);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, List<List<Point2D>>> lines = Hex.Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> blueLines = lines.andThen(toPolylines(blue));

        Tile14 tile = new Tile14(centre, r);

        StringBuilder builder = new StringBuilder();

        double ratio = 1 / 4.0;

        Hex main = newHex(1, HOR);

        return
                builder
                        .append(grayLines.apply(Pair.of(main, PERIMETER)))
                        .append(grayLines.apply(Pair.of(main, DIAGONALS)))
                        .append(IntStream.range(1, 4).mapToObj(i -> Pair.of(newHex(i * ratio, HOR), PERIMETER)).map(grayLines).collect(joining()))

                        .append(drawPolylines(tile.getPayload().getPolylines(), red))
                        .append(Stream.of(
                                asList(tile.getPointsA()),
                                asList(tile.getPointsB()),
                                asList(tile.getPointsC())
                        ).map(highlightPoints()).collect(joining()))
                        .toString()
                ;

    }

    public static String newDesign15(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);
        Pair<Point2D, Double> ic = Pair.of(centre, r);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, List<List<Point2D>>> lines = Hex.Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> redLines = lines.andThen(toPolylines(red));

        double ratio = sin(PI_QUARTER) / 2.5;

        double newR = (r * sin(PI_QUARTER)) / 2.5;
        Tile tile = new Tile15(centre, r);

        return
                new StringBuilder()
                        .append(Stream.of(
                                Pair.of(newHex(ratio, VER), PERIMETER),
                                Pair.of(newHex(2 * ratio, VER), PERIMETER),
                                Pair.of(newHex(5 * ratio, VER), PERIMETER),
                                Pair.of(newHex(5 * ratio, VER), DIAGONALS)
                        ).map(grayLines).collect(joining()))
                        .append(drawPolygon(newRectRot(centre, r), gray))
                        .append(drawPolygon(newRectRot(centre, r * Tile15.RATIO_W), gray))
                        .append(toPolylines(red).apply(tile.getPayload().getPolylines()))

                        .toString();

    }

    public static String newDesign16(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);
        Pair<Point2D, Double> ic = Pair.of(centre, r);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, List<List<Point2D>>> lines = Hex.Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> redLines = lines.andThen(toPolylines(red));


        Tile tile = new Tile16(centre, r);

        double ratio = 1 / 9.0;

        Hex main = newHex(1, VER);

        StringBuilder builder = new StringBuilder();
        return
                builder
                        .append(Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(main, DIAGONALS)
                        ).map(grayLines).collect(joining()))

                        .append(IntStream.range(1, 9).mapToObj(i -> Pair.of(newHex(i * ratio, VER), PERIMETER)).map(grayLines).collect(joining()))
                        .append(toPolylines(red).apply(tile.getPayload().getPolylines()))
                        .toString()
                ;

    }

    public static String newDesign17(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);
        Pair<Point2D, Double> ic = Pair.of(centre, r);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, List<List<Point2D>>> lines = Hex.Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> redLines = lines.andThen(toPolylines(red));


        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile17(centre, r);


        double ratio = 1 / 3.0;
        double newR = r / 3.0;

        builder
                .append(Stream.of(
                        Pair.of(newHex(1, HOR), PERIMETER),
                        Pair.of(newHex(ratio, HOR), PERIMETER)
                ).map(grayLines).collect(joining()))
                .append(highlight.apply(newHex(2 * ratio, HOR)))
                .append(drawPolygons(tile.getPayload().getPolygons(), red))

        ;

        return builder.toString();

    }

    public static String newDesign19(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);
        Pair<Point2D, Double> ic = Pair.of(centre, r);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, List<List<Point2D>>> lines = Hex.Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> redLines = lines.andThen(toPolylines(red));

        StringBuilder builder = new StringBuilder();

        Tile tile = new Tile19(centre, r);

        double ratio = 1 / 5.0;
        double newR = r / 5.0;

        Hex main = newHex(1, VER);
        Hex outer0 = newHex(ratio, VER);
        Hex outer1 = newHex(2 * ratio, VER);
        Hex outer2 = newHex(3 * ratio, VER);
        Hex outer3 = newHex(4 * ratio, VER);

        builder
                .append(Stream.of(
                        Pair.of(main, PERIMETER),
                        Pair.of(outer0, PERIMETER)
                ).map(grayLines).collect(joining()))
                .append(Stream.of(
                        outer1,
                        outer2,
                        outer3
                ).map(highlight).collect(joining()))
                .append(toPolylines(red).apply(tile.getPayload().getPolylines()))
        ;

        return builder.toString();

    }

    public static String newDesign20(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);
        Pair<Point2D, Double> ic = Pair.of(centre, r);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, List<List<Point2D>>> lines = Hex.Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> redLines = lines.andThen(toPolylines(red));

        StringBuilder builder = new StringBuilder();

        double ratio = 1 / 6.0;
        Hex main = newHex(1, HOR);

        Tile tile = new Tile20(centre, r);

        builder
                .append(Stream.concat(
                        Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(main, DIAGONALS)
                        ),
                        IntStream.range(1, 6).mapToObj(i -> Pair.of(newHex(i * ratio, HOR), PERIMETER))

                ).map(grayLines).collect(joining()))
                .append(toPolylines(red).apply(tile.getPayload().getPolylines()))

        ;
        return builder.toString();

    }

    public static String newDesign21(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);
        Pair<Point2D, Double> ic = Pair.of(centre, r);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, List<List<Point2D>>> lines = Hex.Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> redLines = lines.andThen(toPolylines(red));

        StringBuilder builder = new StringBuilder();

        double ratio = 1 / 6.0;
        Hex main = newHex(1, HOR);

        Tile tile = new Tile21(centre, r);

        builder
                .append(Stream.concat(
                        Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(main, DIAGONALS),
                                Pair.of(main.getInternal(), DIAGONALS)
                        ),
                        IntStream.range(1, 6).mapToObj(i -> Pair.of(newHex(i * ratio, HOR), PERIMETER))

                ).map(grayLines).collect(joining()))
                .append(toPolylines(red).apply(tile.getPayload().getPolylines()));

        return builder.toString();
    }

    public static String newDesign22(final Point2D centre, final double r) {

        final String gray = newStyle(GRAY, 1, 1);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);
        Pair<Point2D, Double> ic = Pair.of(centre, r);
        Function<Hex, String> highlight = highlightVertexes(ic);

        Function<Pair<Hex, List<List<Hex.Vertex>>>, List<List<Point2D>>> lines = Hex.Vertex.vertexesToPoints(0, ic);
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> grayLines = lines.andThen(toPolylines(gray));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> greenLines = lines.andThen(toPolylines(green));
        Function<Pair<Hex, List<List<Hex.Vertex>>>, String> redLines = lines.andThen(toPolylines(red));

        StringBuilder builder = new StringBuilder();

        double ratioH = 1 / 16.0;
        double ratio = ratioH / HEX_DIST_HEIGHT;

        double newH = r / 16.0;
        double newR = newH / HEX_DIST_HEIGHT;

        Hex main = newHex(1, HOR);

        Tile tile = new Tile22(centre, r);

        builder
                .append(Stream.concat(
                        Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(main, DIAGONALS),
                                Pair.of(main.getInternal(), DIAGONALS)
                        ),
                        IntStream.range(1, 16).mapToObj(i -> Pair.of(newHex(i * ratio, VER), PERIMETER))

                ).map(grayLines).collect(joining()))
                .append(toPolylines(red).apply(tile.getPayload().getPolylines()))
                .append(drawPolygons(tile.getPayload().getPolygons(), blue))
        ;

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
