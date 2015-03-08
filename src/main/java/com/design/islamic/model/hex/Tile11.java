package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.Hex;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.Hex.HEIGHT_RATIO;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Tile11 extends TileBasic {

    public static double RATIO_1 = 1.0 / 3.0;
    public static double RATIO_2 = RATIO_1 / HEIGHT_RATIO;
    public static double RATIO_3 = HEIGHT_RATIO - 0.5 * RATIO_2;
    public static double RATIO_4 = 0.5 - RATIO_3;

    public Tile11(Pair<Point2D, Double> initialConditions) {

        super(initialConditions);

    }

    @Override
    protected Stream<List<Pair<Polygon, Polygon.Vertex>>> getMainMixVertexesFull() {
        Polygon outer = Hex.hex(RATIO_2, Polygon.Type.HOR, centreTransform(1, Polygon.Type.VER));
        Polygon outerReg = outer.getRegistered();

        Polygon inner = Hex.hex(RATIO_3, Polygon.Type.HOR);

        return Stream.of(
                asList(
                        Pair.of(outerReg, Hex.Vertex.THREE),
                        Pair.of(outer, Hex.Vertex.FOUR),
                        Pair.of(outer, Hex.Vertex.FIVE),
                        Pair.of(outerReg, Hex.Vertex.FIVE)
                ),
                asList(
                        Pair.of(inner, Hex.Vertex.ONE),
                        Pair.of(outer, Hex.Vertex.FIVE)
                ),
                asList(
                        Pair.of(inner, Hex.Vertex.TWO),
                        Pair.of(outer, Hex.Vertex.FOUR)
                )
        );
    }

    public String design1() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        List<Point2D> hexGrid = Grid.grid(initialConditions.getLeft(), initialConditions.getRight() / 4.0,
                Grid.Configs.HEX_VER.getConfiguration(), 12);

        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon mainReg = main.getRegistered();
        Polygon outer = Hex.hex(RATIO_2, Polygon.Type.HOR, centreTransform(1, Polygon.Type.VER));
        Polygon outerReg = outer.getRegistered();
        Polygon inner = Hex.hex(RATIO_3, Polygon.Type.HOR);
        Polygon outerSmall = Hex.hex(RATIO_4, Polygon.Type.VER, centreTransform(RATIO_3, Polygon.Type.HOR));

        List<Pair<Point2D, String>> importantPoints = Stream.of(
                Triple.of(main, Hex.Vertex.ONE, "A"),
                Triple.of(outerReg, Hex.Vertex.FIVE, "B"),
                Triple.of(outer, Hex.Vertex.FIVE, "C"),
                Triple.of(outer, Hex.Vertex.SIX, "D"),
                Triple.of(inner, Hex.Vertex.ONE, "E")
        ).map(importantPoint).collect(toList());

        importantPoints.add(Pair.of(initialConditions.getLeft(), "K"));
        List<String> equations = asList(
                "AB=1/3",
                "CD=AB/h",
                "KE=h-(CD/2)",
                "EC=0.5-AB",
                ""
        );

        IntStream.range(0, equations.size())
                .forEach(i -> importantPoints.add(Pair.of(new Point2D.Double(1000, (i + 1) * 50), equations.get(i))));

        importantPoints.stream().map(drawText());
        importantPoints.stream().map(Pair::getLeft).map(highlightPoint());

        return
                Stream.of(
                        Stream.of(
                                highlightPoints("black", 2).apply(hexGrid)
                        ),
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(main, Hex.DIAGONALS),
                                Pair.of(mainReg, Hex.DIAGONALS)
//                                Pair.of(outer, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(outer, Hex.PERIMETER)
                        ).map(toLinesFull.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(inner, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(outerSmall, Hex.PERIMETER)
//                                Pair.of(inner3, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        importantPoints.stream().map(drawText()),
                        importantPoints.stream().map(Pair::getLeft).map(highlightPoint()),
                        Stream.of(
                                getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

    }

}