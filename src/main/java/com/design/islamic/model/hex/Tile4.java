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

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.$1;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.£H;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Tile4 extends TileBasic {

    private static double RATIO_KA = £H.apply(0.5);
    private static double RATIO_CD = £H.andThen($1).apply(RATIO_KA);

    public Tile4(Pair<Point2D, Double> initialConditions) {

        super(initialConditions);

    }


    @Override
    protected Stream<List<Pair<Polygon, Polygon.Vertex>>> getMainMixVertexesFull() {
        Polygon inner = Hex.hex(RATIO_KA, HOR);
        Polygon outer = Hex.hex(RATIO_CD, VER, centreTransform(1, VER));

        return Stream.of(
                asList(
                        Pair.of(outer, FIVE),
                        Pair.of(inner, TWO)
                ),
                asList(
                        Pair.of(outer, THREE),
                        Pair.of(inner, ONE)
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

        Polygon main = Hex.hex(1, VER);
        Polygon inner2 = Hex.hex(RATIO_KA, Polygon.Type.HOR);
        Polygon inner1 = inner2.getFramed();
        Polygon inner3 = Hex.hex(RATIO_KA * RATIO_KA, VER);
        Polygon outer = Hex.hex(inner3.getRatio(), VER, centreTransform(1, VER));
//        Polygon innerReg = inner.getRegistered();

//        Polygon outer = Hex.hex(RATIO_2, Polygon.Type.HOR, centreTransform(RATIO_1, Polygon.Type.VER));

        List<Pair<Point2D, String>> importantPoints = Stream.of(
                Triple.of(inner2, Hex.Vertex.ONE, "A"),
                Triple.of(inner3, Hex.Vertex.ONE, "B"),
                Triple.of(inner1, Hex.Vertex.ONE, "C"),
                Triple.of(main, Hex.Vertex.ONE, "D"),
                Triple.of(outer, Hex.Vertex.THREE, "E")
        ).map(importantPoint).collect(toList());

        importantPoints.add(Pair.of(initialConditions.getLeft(), "K"));
        List<String> equations = asList(
                "i=0.5/h",
                "KA=i",
                "KB=i*i",
                "KC=KA/h=2*KB",
                "DC=DE=KB",
                "DC=1-KC"
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
                                Pair.of(main, Hex.INNER_TRIANGLES),
                                Pair.of(inner2, Hex.INNER_TRIANGLES),
                                Pair.of(outer, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(inner2, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(inner1, Hex.PERIMETER),
                                Pair.of(inner3, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        importantPoints.stream().map(drawText()),
                        importantPoints.stream().map(Pair::getLeft).map(highlightPoint()),
                        Stream.of(
                                getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

    }

}