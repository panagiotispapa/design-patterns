package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.Hex;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Stream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.$1;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.Hex.$H;
import static com.design.islamic.model.Hex.HEIGHT_RATIO;
import static com.design.islamic.model.Hex.Vertex.ONE;
import static com.design.islamic.model.Hex.Vertex.TWO;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Tile2 extends TileBasic {

    private static double RATIO_KB = HEIGHT_RATIO / (HEIGHT_RATIO + 0.5);
    private static double RATIO_BD = $1.andThen($H).apply(RATIO_KB);

    public Tile2(Pair<Point2D, Double> initialConditions) {

        super(initialConditions);

    }

    @Override
    protected Stream<List<Pair<Polygon, Polygon.Vertex>>> getMainMixVertexesFull() {
        Polygon inner = Hex.hex(RATIO_KB, VER);

        Polygon outer = Hex.hex(RATIO_BD, HOR, centreTransform(RATIO_KB, VER));
        return Stream.of(
                asList(
                        Pair.of(outer, ONE),
                        Pair.of(inner, ONE),
                        Pair.of(outer, TWO)
                ),
                asList(
                        Pair.of(inner, ONE),
                        Pair.of(inner, TWO)
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
        Polygon inner = Hex.hex(RATIO_KB, VER);
        Polygon innerReg = inner.getRegistered();

        Polygon outer = Hex.hex(RATIO_BD, HOR, centreTransform(RATIO_KB, VER));

        List<Pair<Point2D, String>> importantPoints = Stream.of(
                Triple.of(inner, ONE, "B"),
                Triple.of(innerReg, ONE, "C"),
                Triple.of(outer, ONE, "D")
        ).map(importantPoint).collect(toList());

        importantPoints.add(Pair.of(initialConditions.getLeft(), "K"));
        importantPoints.add(Pair.of(new Point2D.Double(1000, 50), "KB=h/(h+0.5)"));
        importantPoints.add(Pair.of(new Point2D.Double(1000, 100), "BD=h*(1-KB)"));

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
                                Pair.of(main.getRegistered(), Hex.DIAGONALS),
                                Pair.of(outer, Hex.DIAGONALS)
//                                Pair.of(inner, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(inner, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(outer, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        importantPoints.stream().map(drawText()),
                        importantPoints.stream().map(Pair::getLeft).map(highlightPoint()),
                        Stream.of(
                                getPayload().getPolylines()
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

    }

}