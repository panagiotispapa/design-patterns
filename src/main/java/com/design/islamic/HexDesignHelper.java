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

public class HexDesignHelper {

    private final static String black = newStyle("black", 2, 1);
    private final static String blue = newStyle("blue", 2, 1);
    private final static String gray = newStyle("gray", 1, 1);
    private final static String green = newStyle("green", 2, 1);
    private final static String red = newStyle("red", 2, 1);

    private Pair<Point2D, Double> initialConditions;
    private Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines;
    private Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> linesFull;
    private Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> star;
    private Function<Hex, String> highlight;

    private HexDesignHelper(Pair<Point2D, Double> initialConditions) {
        lines = Vertex.vertexesToPointsSingle(initialConditions);
        linesFull = Vertex.vertexesToPointsFull(initialConditions);
        star = combineVertexes(initialConditions);
        highlight = highlightVertexes(initialConditions);
        this.initialConditions = initialConditions;
    }

    public static HexDesignHelper with(Pair<Point2D, Double> initialConditions) {
        return new HexDesignHelper(initialConditions);
    }

    public String newStarDesign1() {

        Hex main = Hex.newHex(NO_TRANSFORMS, 1, HOR);
        Hex outerCentres = Hex.newHex(NO_TRANSFORMS, HEX_DIST_NEW_CENTRE, VER);
        Hex layer1 = Hex.newHex(NO_TRANSFORMS, HEX_DIST_OUTER_CIRCLE, VER);
        Hex layer2 = Hex.newHex(NO_TRANSFORMS, HEX_DIST_DIAGONAL, VER);
        Hex layer3 = Hex.newHex(NO_TRANSFORMS, HEX_DIST3, VER);
        Hex layer4 = Hex.newHex(NO_TRANSFORMS, 1, VER);
        Hex layerMiddle = Hex.newHex(NO_TRANSFORMS, 0.5, HOR);

        final List<Point2D> outsideCentres = outerCentres.vertexes(initialConditions);

        return Stream.of(
                Stream.of(
                        newCircle(initialConditions.getLeft(), initialConditions.getRight(), black)
                ),
                outsideCentres.stream().map(c_centre -> newCircle(c_centre, initialConditions.getRight(), gray)),
                outsideCentres.stream().map(c_centre -> newPolyline(asList(initialConditions.getLeft(), c_centre), gray)),
                Stream.of(
                        Pair.of(main, ALL_LINES),
                        Pair.of(layer1, PERIMETER),
                        Pair.of(layerMiddle, PERIMETER),
                        Pair.of(outerCentres, PERIMETER)
                ).map(lines.andThen(toPolylines(gray))),
                Stream.of(
                        Triple.of(layer4, layer1.getMirror(), STAR),
                        Triple.of(layer4, layer2.getMirror(), STAR),
                        Triple.of(layer4, layer3.getMirror(), STAR)
                ).map(star.andThen(toPolylines(green))),
                Stream.of(
                        outerCentres,
                        layer1,
                        layer2,
                        layer3,
                        layer4
                ).map(highlight)

        ).flatMap(s -> s).collect(joining());

    }

    public String newStarDesign2() {

        Hex outer = newHex(NO_TRANSFORMS, 1, VER);
        Hex outerH = outer.getInternal();
        Hex inner = newHex(NO_TRANSFORMS, 0.5, VER);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(outer, PERIMETER),
                                Pair.of(outer, DIAGONALS),
                                Pair.of(inner, PERIMETER)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                Triple.of(inner, outerH, STAR)
                        ).map(star.andThen(toPolylines(green))),
                        Stream.of(
                                outer,
                                outerH,
                                inner
                        ).map(highlight)

                ).flatMap(s -> s).collect(joining());

    }

    public String newStarDesign3() {

        Tile3 tile3 = new Tile3(initialConditions.getLeft(), initialConditions.getRight());

        Hex main = newHex(NO_TRANSFORMS, 1, VER);
        Hex inner = newHex(NO_TRANSFORMS, HEX_DIST_EQ1, VER);
        Hex inner2 = Hex.newHex(NO_TRANSFORMS, HEX_DIST_EQ1 * 0.5, VER);

        CentreTransform cTransform1 = newTransform(HEX_DIST_EQ1, VER, Vertex.ONE);
        CentreTransform.VertexBuilder cTransform2 = CentreTransform.transform().ratio(HEX_DIST_EQ1 * HEX_DIST_HEIGHT).type(HOR);

        Hex ext0 = Hex.newHex(cTransform1, 1 - HEX_DIST_EQ1, VER);
        Hex ext0_1 = Hex.newHex(cTransform1, (1 - HEX_DIST_EQ1) * HEX_DIST_DIAGONAL_ROTATED, HOR);

        Hex ext0_2 = Hex.newHex(cTransform2.vertex(Vertex.ONE), HEX_DIST_EQ1 * HEX_DIST_DIAGONAL_ROTATED * 0.5, VER);
        Hex ext0_3 = Hex.newHex(cTransform2.vertex(Vertex.SIX), HEX_DIST_EQ1 * HEX_DIST_DIAGONAL_ROTATED * 0.5, VER);

        List<List<Point2D>> extConfigs = buildExtConfigForDesign3(inner.vertexes(initialConditions), initialConditions.getRight() * (1 - HEX_DIST_EQ1));

        return
                Stream.of(
                        Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(ext0, ALL_LINES)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(ext0_1, PERIMETER),
                                Pair.of(ext0_2, PERIMETER),
                                Pair.of(ext0_3, PERIMETER)
                        ).map(lines.andThen(toPolylines(blue))),
                        Stream.of(
                                Triple.of(inner2, inner.getInternal(), STAR)
                        ).map(star.andThen(toPolylines(blue))),
                        Stream.of(
                                Pair.of(inner, PERIMETER)
                        ).map(lines.andThen(toPolylines(green))),
                        Stream.of(
                                extConfigs,
                                tile3.getLines(),
                                tile3.getLines2()
                        ).map(toPolylines(red)),
                        Stream.of(
                                inner.getInternal(),
                                inner,
                                inner2
                        ).map(highlight),
                        Stream.of(
                                highlightPoints().apply(extConfigs.get(0))
                        )

                ).flatMap(s -> s).collect(joining());

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

    public String newStarDesign4() {

        Hex layer1 = Hex.newHex(1, VER);
        Hex layer2 = Hex.newHex(HEX_DIST_DIAGONAL, HOR);
        Hex layer3 = Hex.newHex(HEX_DIST_DIAGONAL * HEX_DIST_DIAGONAL, VER);
        Hex layerExt = Hex.newHex(layer3.getRatio() * HEX_DIST_NEW_CENTRE, HOR);
        Hex layerExtPol1 = Hex.newHex(newTransform(layerExt.getRatio(), layerExt.getType(), Vertex.ONE), layer3.getRatio(), VER);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(layer1, ALL_LINES),
                                Pair.of(layer2, ALL_LINES),
                                Pair.of(layerExtPol1, ALL_LINES),
                                Pair.of(layer1.getInternal(), DIAGONALS)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(layer3, PERIMETER)
                        ).map(lines.andThen(toPolylines(green))),
                        Stream.of(
                                Tile4.buildExtConf(initialConditions.getLeft(), initialConditions.getRight() * HEX_DIST_DIAGONAL * HEX_DIST_DIAGONAL)
                        ).map(toPolylines(red)),
                        Stream.of(
                                layerExt
                        ).map(highlight)
                ).flatMap(s -> s).collect(joining());

    }

    public static String newDesignTest(Pair<Point2D, Double> ic) {

        StringBuilder builder = new StringBuilder();

        final String gray = newStyle(GRAY, 1, 1);
        final String grayLight = newStyle(GRAY, 1, 0.5);
        final String green = newStyle(GREEN, 2, 1);
        final String red = newStyle(RED, 2, 1);
        final String blue = newStyle(BLUE, 2, 1);

        Function<Pair<Hex, List<List<Vertex>>>, List<List<Point2D>>> lines = Vertex.vertexesToPoints(0, ic);

        Hex layer1 = newHex(1, VER);
        Hex layer2 = newHex(newTransform(1, VER, Vertex.ONE), 0.5, VER);

        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = Vertex.vertexesToPointsSingle(ic).andThen(toPolylines(gray));
        List<Point2D> grid = HexGrid.grid(ic.getLeft(), ic.getRight() / 4, HexGrid.TYPE.HOR, 16);
        return builder

                .append(grid.stream().map(p -> drawPolygon(newHexagon(p, ic.getRight() / 4), grayLight)).collect(joining()))

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

    public String newStarDesign5() {

        Hex layer1 = newHex(1, VER);
        Hex layer2 = newHex(HEX_DIST_DIAGONAL, HOR);
        Hex layer3 = newHex(HEX_DIST_DIAGONAL * HEX_DIST_DIAGONAL, VER);
        Hex outer = newHex(layer3.getRatio() * HEX_DIST_NEW_CENTRE, HOR);

        List<List<Point2D>> outerLines = Tile5.buildOuterLines(outer.vertexes(initialConditions), layer3.getRatio() * initialConditions.getRight());
        return
                Stream.of(
                        Stream.of(
                                Pair.of(layer1, ALL_LINES),
                                Pair.of(layer2, ALL_LINES)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(layer2, PERIMETER)
                        ).map(lines.andThen(toPolylines(blue))),
                        Stream.of(
                                Pair.of(layer3, PERIMETER)
                        ).map(lines.andThen(toPolylines(green))),
                        Stream.of(
                                outerLines
                        ).map(toPolylines(red)),
                        Stream.of(
                                outer
                        ).map(highlight)

                ).flatMap(s -> s).collect(joining());

    }

    public String newStarDesign6() {

        Hex layer1 = newHex(1, VER);
        Hex layer2 = newHex(HEX_DIST_DIAGONAL_ROTATED, VER);
        Hex outer = newHex(HEX_DIST_DIAGONAL_ROTATED * HEX_DIST_NEW_CENTRE, HOR);
        Hex outerInner = newHex(newTransform(outer, Vertex.ONE), HEX_DIST_DIAGONAL_ROTATED * HEX_DIST_DIAGONAL_ROTATED, VER);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(layer1, ALL_LINES),
                                Pair.of(outerInner, PERIMETER),
                                Pair.of(layer1.getInternal(), INNER_TRIANGLES)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                Tile6.buildOuterLines(outer.vertexes(initialConditions), initialConditions.getRight() * HEX_DIST_DIAGONAL_ROTATED)
                        ).map(toPolylines(red)),
                        Stream.of(
                                outer,
                                layer2
                        ).map(highlight)

                ).flatMap(s -> s).collect(joining());

    }

    public String newDesign7() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();
        Tile7 tile7 = new Tile7(centre, r);

        Hex outer = newHex(1, HOR);
        Hex outerHeights = outer.getInternal();
        Hex inner = newHex(HEX_DIST_DIAGONAL_ROTATED, HOR);
        Hex inner2 = newHex(HEX_DIST_DIAGONAL_ROTATED * HEX_DIST_DIAGONAL_ROTATED, HOR);
        Hex outerSmall = newHex(newTransform(outer.getInternal(), Vertex.ONE), 1 - HEX_DIST_HEIGHT, VER);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(outer, PERIMETER),
                                Pair.of(outer.getMirror(), PERIMETER),
                                Pair.of(outerHeights, DIAGONALS),
                                Pair.of(outerHeights.getMirror(), DIAGONALS),
                                Pair.of(inner.getInternal(), INNER_TRIANGLES),
                                Pair.of(inner.getInternal().getMirror(), INNER_TRIANGLES)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(inner, PERIMETER),
                                Pair.of(inner.getMirror(), PERIMETER),
                                Pair.of(inner2, PERIMETER),
                                Pair.of(inner2.getMirror(), PERIMETER),
                                Pair.of(outerSmall, PERIMETER)
                        ).map(lines.andThen(toPolylines(blue))),
                        Stream.of(
                                Pair.of(outerSmall.getInternal(), DIAGONALS)
                        ).map(lines.andThen(toPolylines(green))),
                        Stream.of(
                                tile7.getLines()
                        ).map(toPolylines(red)),
                        Stream.of(
                                outerHeights
                        ).map(highlight)
                ).flatMap(s -> s).collect(joining());

    }

    public String newDesign8() {

        Tile8 tile8 = new Tile8(initialConditions.getLeft(), initialConditions.getRight());

        Hex main = newHex(1, HOR);
        Hex mainInternal = main.getInternal();
        Hex tile = newHex(tile8.getInnerR() / initialConditions.getRight(), HOR);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(main.getMirror(), PERIMETER),
                                Pair.of(mainInternal, PERIMETER),
                                Pair.of(mainInternal.getMirror(), PERIMETER),
                                Pair.of(tile, PERIMETER),
                                Pair.of(tile.getMirror(), PERIMETER),
                                Pair.of(main, DIAGONALS),
                                Pair.of(main.getMirror(), DIAGONALS)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                Triple.of(mainInternal, mainInternal.getMirror(), STAR)
                        ).map(star.andThen(toPolylines(blue))),
                        Stream.of(
                                tile8.getPointsG()
                        ).map(highlightPoints()),
                        Stream.of(
                                tile8.getLines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

//                .append(highlightPoints().apply(tile8.getPointsA()))
//                .append(highlightPoints().apply(tile8.getPointsB()))
//                .append(highlightPoints().apply(tile8.getPointsC()))
//                .append(highlightPoints().apply(tile8.getPointsD()))
    }

    public String newDesign9() {

        StringBuilder builder = new StringBuilder();

        Point2D centre = initialConditions.getLeft();
        Double r = initialConditions.getRight();
        Tile9 tile9 = new Tile9(centre, r);

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

        ;

        builder
                .append(
                        Stream.of(
                                Stream.of(
                                        Pair.of(outerr, PERIMETER),
                                        Pair.of(outerr.getMirror(), PERIMETER),
                                        Pair.of(inner1, PERIMETER),
                                        Pair.of(inner1.getMirror(), PERIMETER),
                                        Pair.of(outerr, DIAGONALS),
                                        Pair.of(outerr.getMirror(), DIAGONALS)
                                ).map(lines.andThen(toPolylines(gray))),
                                Stream.of(
                                        Triple.of(outerr, outerr.getMirror(), OUTER_VERTEXES),
                                        Triple.of(outer1, outer1.getMirror(), OUTER_VERTEXES),
                                        Triple.of(outer2, outer2.getMirror(), OUTER_VERTEXES)
                                ).map(star.andThen(toPolylines(gray))),

//        builder.append(drawPolygon(tile9.getInnerLayer1(), gray));
//        builder.append(drawPolygon(tile9.getInnerLayer1Rot(), gray));

                                Stream.of(
                                        buildLines(
                                                newHexEdge(centre, r * Tile9.INNER_R2, 0),
                                                asList(newHexEdgeRot(outerRot.get(5), r * Tile9.INNER_R1, 1)))
                                ).map(toPolylines(gray)),
                                Stream.of(
                                        buildLines(
                                                newHexEdge(centre, r * Tile9.INNER_R2, 5),
                                                asList(newHexEdgeRot(outerRot.get(5), r * Tile9.INNER_R1, 3)))
                                ).map(toPolylines(gray)),
                                Stream.of(
                                        buildLines(
                                                newHexEdgeRot(centre, r * Tile9.INNER_R2, 0),
                                                asList(newHexEdge(outer.get(0), r * Tile9.INNER_R1, 1)))
                                ).map(toPolylines(gray)),
                                Stream.of(
                                        buildLines(
                                                newHexEdgeRot(centre, r * Tile9.INNER_R2, 5),
                                                asList(newHexEdge(outer.get(0), r * Tile9.INNER_R1, 5)))
                                ).map(toPolylines(gray)),
                                Stream.of(
                                        Pair.of(inner2, INNER_TRIANGLES),
                                        Pair.of(inner2.getMirror(), INNER_TRIANGLES),
                                        Pair.of(newHex((1.0 - Tile9.INNER_R1), VER), PERIMETER)
                                ).map(lines.andThen(toPolylines(gray))),
                                Stream.of(
                                        Pair.of(outerr.getMirror(), PERIMETER)
                                ).map(lines.andThen(toPolylines(red))),
                                Stream.of(
                                        tile9.getLines()
                                ).map(toPolylines(red)),
                                Stream.of(
                                        outer4,
                                        outer4.getMirror()
                                ).map(highlight),
                                Stream.of(
                                        tile9.getOuterLayer1(),
                                        tile9.getOuterLayer1Rot(),
                                        tile9.getOuterLayer2(),
                                        tile9.getOuterLayer2Rot()
                                ).map(highlightPoints()),
                                Stream.of(
                                        Pair.of(outerrLayer, PERIMETER)
                                ).map(linesFull.andThen(toPolylines(blue))),
                                Stream.of(
                                        concateOuterHexagons(
                                                newHexagon(outer.get(0), r * (1.0 - Tile9.OUTER_R1)),
                                                newHexagonRot(outer.get(0), r * (1.0 - Tile9.OUTER_R1))
                                        )
                                ).map(toPolylines(gray)),
                                Stream.of(
                                        concateOuterHexagons(
                                                newHexagon(outer.get(0), r * (1.0 - Tile9.OUTER_R2)),
                                                newHexagonRot(outer.get(0), r * (1.0 - Tile9.OUTER_R2))
                                        )
                                ).map(toPolylines(gray))

                        ).flatMap(s -> s).collect(joining())

                )

//
//                .append(grayStar.apply(Triple.of(outer1T, outer1T.getMirror(), OUTER_VERTEXES)))
//                .append(grayStar.apply(Triple.of(outer2T, outer2T.getMirror(), OUTER_VERTEXES)))
//

        ;

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

    public String newDesign11() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile11 tile = new Tile11(initialConditions.getLeft(), initialConditions.getRight());

        Hex main = newHex(1, VER);
        Hex layerInner = newHex(newTransform(main, Vertex.ONE), tile.getInnerR() / r, HOR);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(main, DIAGONALS),
                                Pair.of(main.getInternal(), DIAGONALS)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(layerInner, PERIMETER)
                        ).map(linesFull.andThen(toPolylines(gray))),
                        Stream.of(newCircle(initialConditions.getLeft(), tile.getInnerR(), gray)),
                        tile.getMainHeights().stream().map(edge -> newCircle(edge, r / 2.0, gray)),
                        Stream.of(
                                tile.getMainHeights(),
                                tile.getPointsA(),
                                tile.getPointsB(),
                                tile.getPointsC(),
                                tile.getPointsD()
                        ).map(highlightPoints()),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))
//

                ).flatMap(s -> s).collect(joining());

//        builder.append(highlightPoints(newHexagon(centre,tile.getInnerR2())));

    }

    public String newDesign12() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile12(centre, r);

        final double ratio = RECT_DIST_HEIGHT / 5.0;

//        final double newR = ((r * RECT_DIST_HEIGHT) / 6.0)/HEX_DIST_HEIGHT;

//        builder.append(drawPolygon(newHexagonRot(centre, r), gray));

        return
                Stream.of(
                        Stream.of(
//                                Pair.of(newHex(1, VER), PERIMETER),
//                                Pair.of(newHex(Tile12.RATIO_W, VER), PERIMETER),
                                Pair.of(newHex(ratio, VER), PERIMETER),
                                Pair.of(newHex(1, VER), DIAGONALS),
                                Pair.of(newHex(2 * ratio, VER), PERIMETER),
                                Pair.of(newHex(3 * ratio, VER), PERIMETER),
                                Pair.of(newHex(4 * ratio, VER), PERIMETER),
                                Pair.of(newHex(5 * ratio, VER), PERIMETER),
                                Pair.of(newHex(6 * ratio, VER), PERIMETER),
                                Pair.of(newHex(7 * ratio, VER), PERIMETER)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                drawPolygon(newRectRot(centre, r), gray),
                                drawPolygon(newRectRot(centre, r * Tile12.RATIO_W), gray)
                        ),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red)),
                        Stream.of(drawPolygons(tile.getPayload().getPolygons(), red))

                ).flatMap(s -> s).collect(joining());

//        builder.append(drawPolygon(newHexStarTileRotated(centre, 2 * newR, HEX_DIST_DIAGONAL), blue));

//        builder.append(drawPolygon(newHexagonRot(newEdgeAt(centre, 2*newR, HEX_RADIANS_ROT.get(2)), newR), gray));

    }

    public String newDesign13() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile13(centre, r);

        double newR = r / 3.0;
        double newR2 = newR * PolygonTools.HEX_DIST_NEW_CENTRE;

        double ratio = 1 / 3.0;
        double ratio2 = ratio * PolygonTools.HEX_DIST_NEW_CENTRE;

        Hex out = newHex(2.0 * ratio, HOR);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(newHex(1, HOR), PERIMETER),
                                Pair.of(newHex(ratio, HOR), PERIMETER),
                                Pair.of(newHex(ratio2, VER), PERIMETER),
                                Pair.of(newHex(newTransform(out, Vertex.ONE), ratio, HOR), PERIMETER),
                                Pair.of(out, PERIMETER)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

    }

    public String newDesign14() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile14 tile = new Tile14(centre, r);

        double ratio = 1 / 4.0;

        Hex main = newHex(1, HOR);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(main, DIAGONALS)
                        ).map(lines.andThen(toPolylines(gray))),
                        IntStream.range(1, 4).mapToObj(i -> Pair.of(newHex(i * ratio, HOR), PERIMETER)).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red)),
                        Stream.of(
                                asList(tile.getPointsA()),
                                asList(tile.getPointsB()),
                                asList(tile.getPointsC())
                        ).map(highlightPoints())
                ).flatMap(s -> s).collect(joining());

    }

    public String newDesign15() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        double ratio = sin(PI_QUARTER) / 2.5;

        Tile tile = new Tile15(centre, r);
        return
                Stream.of(
                        Stream.of(
                                Pair.of(newHex(ratio, VER), PERIMETER),
                                Pair.of(newHex(2 * ratio, VER), PERIMETER),
                                Pair.of(newHex(5 * ratio, VER), PERIMETER),
                                Pair.of(newHex(5 * ratio, VER), DIAGONALS)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                drawPolygon(newRectRot(centre, r), gray),
                                drawPolygon(newRectRot(centre, r * Tile15.RATIO_W), gray)
                        ),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

    }

    public String newDesign16() {
        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile16(centre, r);

        double ratio = 1 / 9.0;

        Hex main = newHex(1, VER);
        return
                Stream.of(
                        Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(main, DIAGONALS)
                        ).map(lines.andThen(toPolylines(gray))),
                        IntStream.range(1, 9).mapToObj(i -> Pair.of(newHex(i * ratio, VER), PERIMETER)).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))
                ).flatMap(s -> s).collect(joining());

    }

    public String newDesign17() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile17(centre, r);

        double ratio = 1 / 3.0;
        double newR = r / 3.0;

        return
                Stream.of(
                        Stream.of(
                                Pair.of(newHex(1, HOR), PERIMETER),
                                Pair.of(newHex(ratio, HOR), PERIMETER)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                newHex(2 * ratio, HOR)
                        ).map(highlight),
                        Stream.of(drawPolygons(tile.getPayload().getPolygons(), red))

                ).flatMap(s -> s).collect(joining());
    }

    public String newDesign19() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile19(centre, r);

        double ratio = 1 / 5.0;
        double newR = r / 5.0;

        Hex main = newHex(1, VER);
        Hex outer0 = newHex(ratio, VER);
        Hex outer1 = newHex(2 * ratio, VER);
        Hex outer2 = newHex(3 * ratio, VER);
        Hex outer3 = newHex(4 * ratio, VER);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(outer0, PERIMETER)
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                outer1,
                                outer2,
                                outer3
                        ).map(highlight),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))
                ).flatMap(s -> s).collect(joining());

    }

    public String newDesign20() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        double ratio = 1 / 6.0;
        Hex main = newHex(1, HOR);

        Tile tile = new Tile20(centre, r);
        return
                Stream.of(
                        Stream.concat(
                                Stream.of(
                                        Pair.of(main, PERIMETER),
                                        Pair.of(main, DIAGONALS)
                                ),
                                IntStream.range(1, 6).mapToObj(i -> Pair.of(newHex(i * ratio, HOR), PERIMETER))

                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))
                ).flatMap(s -> s).collect(joining());
    }

    public String newDesign21() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        double ratio = 1 / 6.0;
        Hex main = newHex(1, HOR);

        Tile tile = new Tile21(centre, r);

        return
                Stream.of(
                        Stream.concat(
                                Stream.of(
                                        Pair.of(main, PERIMETER),
                                        Pair.of(main, DIAGONALS),
                                        Pair.of(main.getInternal(), DIAGONALS)
                                ),
                                IntStream.range(1, 6).mapToObj(i -> Pair.of(newHex(i * ratio, HOR), PERIMETER))
                        ).map(lines.andThen(toPolylines(gray))),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());
    }

    public String newDesign22() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        double ratioH = 1 / 16.0;
        double ratio = ratioH / HEX_DIST_HEIGHT;

        double newH = r / 16.0;
        double newR = newH / HEX_DIST_HEIGHT;

        Hex main = newHex(1, HOR);

        Tile tile = new Tile22(centre, r);

        return
                Stream.of(
                        Stream.concat(
                                Stream.of(
                                        Pair.of(main, PERIMETER),
                                        Pair.of(main, DIAGONALS),
                                        Pair.of(main.getInternal(), DIAGONALS)
                                ),
                                IntStream.range(1, 16).mapToObj(i -> Pair.of(newHex(i * ratio, VER), PERIMETER))
                        ).map(lines).map(toPolylines(gray)),
                        Stream.of(
                                toPolylines(red).apply(tile.getPayload().getPolylines()),
                                drawPolygons(tile.getPayload().getPolygons(), blue)
                        )

                ).flatMap(s -> s).collect(joining());

    }

    public String newDesign23() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile23(centre, r);

        double ratioH = 1 / 2.0;
        double ratio = ratioH / HEX_DIST_HEIGHT;

        Hex main = newHex(1, HOR);
        Hex layer1 = newHex(ratio, HOR);

        return

                Stream.of(
                        Stream.of(
                                Pair.of(main.getMirror(), PERIMETER)
                        ).map(lines).map(toPolylines(gray)),
                        Stream.of(
                                Pair.of(newHex(newTransform(layer1, Vertex.ONE), ratio, HOR), PERIMETER)
                        ).map(linesFull).map(toPolylines(gray)),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red)),
                        Stream.of(
                                layer1
                        ).map(highlight)

                ).flatMap(s -> s).collect(joining());

    }

    public String newDesign24() {


        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile24(centre, r);

        double ratio = 1 / 3.0;

        Hex main = newHex(1, VER);
        Hex inner = newHex(ratio, VER);
        Hex outer = newHex(newTransform(inner, Vertex.ONE), ratio, VER);


        return

                Stream.of(
                        Stream.of(
                                Pair.of(main, PERIMETER),
                                Pair.of(inner, PERIMETER),
                                Pair.of(outer, PERIMETER)

                        ).map(lines).map(toPolylines(gray)),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());


        //newHexDiag(newEdgeAt(centre,  1* newR, HEX_RADIANS_ROT[0]))
//        builder.append(drawPolygon(newHexagonRot(newEdgeAt(centre, 3 * newR, HEX_RADIANS_ROT[0]), newR), gray));

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

    public String newDesign26() {


        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();


        Tile tile = new Tile26(centre, r);

        return Stream.of(
                Stream.of(
                        Pair.of(newHex(1, VER), PERIMETER),
                        Pair.of(newHex(HEX_DIST_DAM, VER), PERIMETER)
                ).map(lines.andThen(toPolylines(gray))),
                Stream.of(
                        tile.getPayload().getPolylines()
                ).map(toPolylines(red))
        ).flatMap(s -> s).collect(joining());


    }


    public String newDesign27() {

        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();


//        double d = (1 / 2.0) * atan(HEX_PHI - RECT_PHI_HALF);
//        double d2 = r * (HEX_DIST_HEIGHT - d);

        Tile tile = new Tile27(centre, r);

        return Stream.of(
                Stream.of(
                        Pair.of(newHex(1, VER), PERIMETER)
                ).map(lines.andThen(toPolylines(gray))),
                Stream.of(
                        newHex(HEX_DIST_HEX_TO_RECT, HOR)
                ).map(highlight),
                Stream.of(
                        tile.getPayload().getPolylines()
                ).map(toPolylines(red))
        ).flatMap(s -> s).collect(joining());


    }



    public String newDesign28() {


        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile28(centre, r);

        List<Point2D> outerLayer = newHexagon(centre, r);

        double phi = (PI - 2 * HEX_PHI) / 2.0;
        double d1 = tan(phi) * 0.5;
        double d2 = HEX_DIST_HEIGHT - d1;

        List<List<Point2D>> linesTmp = asList(
                asList(
                        outerLayer.get(1),
                        outerLayer.get(3)

                ), asList(
                outerLayer.get(2),
                outerLayer.get(0)

        )

        );


        Hex main = newHex(1, HOR);
        Hex inner1 = newHex(d2, HOR);
        Hex inner2 = newHex(d2 * HEX_DIST_HEX_TO_RECT, HOR);


        return Stream.of(
                Stream.of(
                        linesTmp
                ).map(toPolylines(gray)),

                Stream.of(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(main.getInternal(), DIAGONALS)
                ).map(lines.andThen(toPolylines(gray))),
                Stream.of(
                        inner1.getMirror(),
                        inner2

                ).map(highlight),
                Stream.of(
                        tile.getPayload().getPolylines()
                ).map(toPolylines(red))
        ).flatMap(s -> s).collect(joining());

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
