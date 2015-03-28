package com.design.islamic;

import com.design.common.Polygon;
import com.design.common.PolygonTools;
import com.design.islamic.model.DrawSegmentsInstructions;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Tile;
import com.design.islamic.model.hex.*;
import com.design.islamic.model.tiles.Grid;
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
import static com.design.islamic.model.DrawSegmentsInstructions.CombinedVertexes;
import static com.design.islamic.model.DrawSegmentsInstructions.CombinedVertexes.OUTER_VERTEXES;
import static com.design.islamic.model.DrawSegmentsInstructions.CombinedVertexes.STAR;
import static java.lang.Math.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class HexDesignHelper {

    private final static String black = newStyle("black", 2, 1);
    private final static String blue = newStyle("blue", 2, 1);
    private final static String gray = newStyle("gray", 1, 1);
    private final static String green = newStyle("green", 2, 1);
    private final static String red = newStyle("red", 2, 1);

    private Pair<Point2D, Double> initialConditions;
//    private Function<Hex, String> highlight;

    private Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLines;
    private Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLinesFull;
    private Function<Triple<Polygon, Polygon, CombinedVertexes>, List<List<Point2D>>> toStar;
    private Function<Polygon, List<Point2D>> toVertexes;

    private HexDesignHelper(Pair<Point2D, Double> initialConditions) {
        this.initialConditions = initialConditions;

        toLines = Polygon.toLines(0, initialConditions);
        toLinesFull = Polygon.toLines(IntStream.range(0, 6).boxed().collect(toList()), initialConditions);
        toStar = DrawSegmentsInstructions.combVertexes(initialConditions);
        toVertexes = Polygon.vertexes(initialConditions);
    }

    public static HexDesignHelper with(Pair<Point2D, Double> initialConditions) {
        return new HexDesignHelper(initialConditions);
    }




    private static Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform(double ratio, Polygon.Type type) {
        return Polygon.centreTransform(ratio, Hex.Vertex.ONE, type);
    }

    public String newStarDesign3() {

        Tile2b tile2b = new Tile2b(initialConditions.getLeft(), initialConditions.getRight());

        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon inner = Hex.hex(HEX_DIST_EQ1, Polygon.Type.VER);
        Polygon inner2 = Hex.hex(HEX_DIST_EQ1 * 0.5, Polygon.Type.VER);

        Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> cTransform1 = centreTransform(HEX_DIST_EQ1, Polygon.Type.VER);
        Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> cTransform2_1 = Polygon.centreTransform(HEX_DIST_EQ1 * HEX_DIST_HEIGHT, Hex.Vertex.ONE, Polygon.Type.HOR);
        Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> cTransform2_2 = Polygon.centreTransform(HEX_DIST_EQ1 * HEX_DIST_HEIGHT, Hex.Vertex.SIX, Polygon.Type.HOR);

        Polygon ext0 = Hex.hex(1 - HEX_DIST_EQ1, Polygon.Type.VER, cTransform1);
        Polygon ext0_1 = Hex.hex((1 - HEX_DIST_EQ1) * HEX_DIST_DIAGONAL_ROTATED, Polygon.Type.HOR, cTransform1);

        Polygon ext0_2 = Hex.hex(HEX_DIST_EQ1 * HEX_DIST_DIAGONAL_ROTATED * 0.5, Polygon.Type.VER, cTransform2_1);
        Polygon ext0_3 = Hex.hex(HEX_DIST_EQ1 * HEX_DIST_DIAGONAL_ROTATED * 0.5, Polygon.Type.VER, cTransform2_2);

        List<List<Point2D>> extConfigs = buildExtConfigForDesign3(toVertexes.apply(inner), initialConditions.getRight() * (1 - HEX_DIST_EQ1));

        return
                Stream.of(
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(ext0, Hex.ALL_LINES)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(ext0_1, Hex.PERIMETER),
                                Pair.of(ext0_2, Hex.PERIMETER),
                                Pair.of(ext0_3, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        Stream.of(
                                Triple.of(inner2, inner.getRegistered(), STAR)
                        ).map(toStar.andThen(toPolylines(blue))),
                        Stream.of(
                                Pair.of(inner, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                extConfigs,
                                tile2b.getLines(),
                                tile2b.getLines2()
                        ).map(toPolylines(red)),
                        Stream.of(
                                inner.getRegistered(),
                                inner,
                                inner2
                        ).map(toVertexes.andThen(highlightPoints())),
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


    public String newDesignTest2() {

        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon inner = Hex.hex(0.5, Polygon.Type.VER);
        Polygon outer = Hex.hex(0.5, Polygon.Type.VER, Polygon.centreTransform(1, Hex.Vertex.ONE, Polygon.Type.VER));

        Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLines = Polygon.toLines(0, initialConditions);
        Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLinesFull = Polygon.toLines(IntStream.range(0, 6).boxed().collect(toList()), initialConditions);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER)
//                                Pair.of(outer, HexNew.PERIMETER)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(main.getRegistered(), Hex.PERIMETER),
                                Pair.of(main.getFramed(), Hex.PERIMETER)
//                                Pair.of(outer, HexNew.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(outer, asList(asList((Polygon.Vertex) Hex.Vertex.THREE, Hex.Vertex.FOUR, Hex.Vertex.FIVE)))
                        ).map(toLinesFull.andThen(toPolylines(blue))),
                        Stream.of(
                                inner
                        ).map(Polygon.vertexes(initialConditions).andThen(highlightPoints())),
                        Stream.of(
                                Triple.of(inner, main.getRegistered(), STAR)
                        ).map(toStar.andThen(toPolylines(red)))

                ).flatMap(s -> s).collect(joining());

    }

    public String newDesignTest() {

        StringBuilder builder = new StringBuilder();

//        final String gray = newStyle(GRAY, 1, 1);
        final String grayLight = newStyle(GRAY, 1, 0.5);
//        final String green = newStyle(GREEN, 2, 1);
//        final String red = newStyle(RED, 2, 1);
//        final String blue = newStyle(BLUE, 2, 1);

        Polygon layer1 = Hex.hex(1, Polygon.Type.VER);
        Polygon layer2 = Hex.hex(0.5, Polygon.Type.VER, Polygon.centreTransform(1, Hex.Vertex.ONE, Polygon.Type.VER));

//        Function<Pair<Hex, List<List<Vertex>>>, String> grayLines = Vertex.vertexesToPointsSingle(ic).andThen(toPolylines(gray));
        List<Point2D> grid = Grid.grid(initialConditions.getLeft(), initialConditions.getRight() / 4,
                Grid.Configs.HEX_HOR.getConfiguration(), 16);

        return builder

                .append(grid.stream().map(p -> drawPolygon(newHexagon(p, initialConditions.getRight() / 4), grayLight)).collect(joining()))

//                .append(grid.stream().map(p -> Vertex.vertexesToPointsSingle(Pair.of(p, ic.getRight())).andThen(toPolylines(newStyle(GRAY, 1, 0.5)))).map(f -> f.apply(Pair.of(HexNew.hex(1.0 / 4.0, Polygon.Type.HOR), HexNew.PERIMETER))).collect(joining()))

                .append(
                        Stream.of(
                                Pair.of(layer1, Hex.PERIMETER),
                                Pair.of(layer1, Hex.DIAGONALS)
                        ).map(toLines.andThen(toPolylines(gray))).collect(joining())
                )

//                .append(Hex.Vertex.vertexesToPoints(0, ic).andThen(toPolylines(blue)).apply(Pair.of(layer2, HexNew.PERIMETER2)))
//                .append(Hex.Vertex.vertexesToPoints(1, ic).andThen(toPolylines(blue)).apply(Pair.of(layer2, HexNew.PERIMETER2)))

//                .append(Vertex.vertexesToPointsFull(ic).andThen(toPolylines(blue)).apply(Pair.of(layer2, HexNew.PERIMETER2)))

                .append(highlightPoints().apply(grid))
//                .append(grid.stream().map(p->Pair.of(HexNew.hex( 1.0/4.0,HOR),PERIMETER)).map(Vertex.vertexesToPointsSingle(Pair.of(p,))).collect(joining()))

//                .append(toPolylines(blue).apply(collectii))

                .toString()
                ;

    }












    public String newDesign15() {
        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        double ratio = sin(PI_QUARTER) / 2.5;

        Tile tile = new Tile15(centre, r);
        return
                Stream.of(
                        Stream.of(
                                Pair.of(Hex.hex(ratio, Polygon.Type.VER), Hex.PERIMETER),
                                Pair.of(Hex.hex(2 * ratio, Polygon.Type.VER), Hex.PERIMETER),
                                Pair.of(Hex.hex(5 * ratio, Polygon.Type.VER), Hex.PERIMETER),
                                Pair.of(Hex.hex(5 * ratio, Polygon.Type.VER), Hex.DIAGONALS)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                drawPolygon(newRectRot(centre, r), gray),
                                drawPolygon(newRectRot(centre, r * Tile15.RATIO_W), gray)
                        ),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

    }













    public String newDesign24() {
        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile24(centre, r);

        double ratio = 1 / 3.0;

        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon inner = Hex.hex(ratio, Polygon.Type.VER);
        Polygon outer = Hex.hex(ratio, Polygon.Type.VER, centreTransform(inner.getRatio(), inner.getType()));

        return

                Stream.of(
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(inner, Hex.PERIMETER),
                                Pair.of(outer, Hex.PERIMETER)

                        ).map(toLines).map(toPolylines(gray)),
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
                        Pair.of(Hex.hex(1, Polygon.Type.VER), Hex.PERIMETER),
                        Pair.of(Hex.hex(HEX_DIST_DAM, Polygon.Type.VER), Hex.PERIMETER)
                ).map(toLines.andThen(toPolylines(gray))),
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
                        Pair.of(Hex.hex(1, Polygon.Type.VER), Hex.PERIMETER)
                ).map(toLines.andThen(toPolylines(gray))),
                Stream.of(
                        Hex.hex(HEX_DIST_HEX_TO_RECT, Polygon.Type.HOR)
                ).map(toVertexes.andThen(highlightPoints())),
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

        Polygon main = Hex.hex(1, Polygon.Type.HOR);
        Polygon inner1 = Hex.hex(d2, Polygon.Type.HOR);
        Polygon inner2 = Hex.hex(d2 * HEX_DIST_HEX_TO_RECT, Polygon.Type.HOR);

        return Stream.of(
                Stream.of(
                        linesTmp
                ).map(toPolylines(gray)),

                Stream.of(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(main.getRegistered(), Hex.DIAGONALS)
                ).map(toLines.andThen(toPolylines(gray))),
                Stream.of(
                        inner1.getMirror(),
                        inner2

                ).map(toVertexes.andThen(highlightPoints())),
                Stream.of(
                        tile.getPayload().getPolylines()
                ).map(toPolylines(red))
        ).flatMap(s -> s).collect(joining());

    }

    public String newDesign29() {
        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile29(centre, r);

        double d1 = HEX_DIST_HEIGHT * HEX_DIST_HEIGHT;
        double d2 = 1 - d1;
        double d3 = d2 * cos(PI_QUARTER);
        double d4 = 2 * d3;
        double d5 = 0.5 - d4;
        double d6 = HEX_DIST_HEIGHT - d5;

        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon hex1 = Hex.hex(HEX_DIST_HEIGHT, Polygon.Type.HOR);
        List<Point2D> layer1 = newHexagon(centre, r * HEX_DIST_HEIGHT);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(main, Hex.DIAGONALS),
                                Pair.of(main.getRegistered(), Hex.DIAGONALS)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                asList(asList(
                                        layer1.get(0), layer1.get(5)
                                ))
                        ).map(toPolylines(gray)),
                        Stream.of(
                                Hex.hex(d1, Polygon.Type.VER),
                                Hex.hex(d6, Polygon.Type.HOR),
                                Hex.hex(d6 * HEX_DIST_HEX_TO_RECT, Polygon.Type.VER)
                        ).map(toVertexes.andThen(highlightPoints())),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

    }

    public String newDesign30() {
        Double r = initialConditions.getRight();
        Point2D centre = initialConditions.getLeft();

        Tile tile = new Tile30(centre, r);
        Polygon main = Hex.hex(1, Polygon.Type.VER);

        double d1 = HEX_DIST_HEIGHT * HEX_DIST_HEIGHT;
        double d2 = 1 - d1;
        double d3 = d2 * cos(PI_QUARTER);
        double d4 = 2 * d3;
        double d5 = 0.5 - d4;
        double d6 = HEX_DIST_HEIGHT - d5;

        List<Point2D> layer1 = newHexagon(centre, r * HEX_DIST_HEIGHT);

        return
                Stream.of(
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(main, Hex.DIAGONALS),
                                Pair.of(main.getRegistered(), Hex.DIAGONALS)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                asList(asList(
                                        layer1.get(0), layer1.get(5)
                                ))
                        ).map(toPolylines(gray)),
                        Stream.of(
                                Hex.hex(d1, Polygon.Type.VER),
                                Hex.hex(d6, Polygon.Type.HOR),
                                Hex.hex(d6 * HEX_DIST_HEX_TO_RECT, Polygon.Type.VER)
                        ).map(toVertexes.andThen(highlightPoints())),
                        Stream.of(
                                tile.getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

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
