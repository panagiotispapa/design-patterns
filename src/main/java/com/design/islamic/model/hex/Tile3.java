package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.Hex.HEIGHT_RATIO;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Tile3 extends TileBasic {

    public static double ANGLE_1 = Math.atan(0.25 / HEIGHT_RATIO);
    public static double ANGLE_2 = Math.PI / 6.0 + ANGLE_1;

    public static double RATIO_1 = 0.5 * Math.tan(ANGLE_2);
    public static double RATIO_2 = HEIGHT_RATIO - RATIO_1;

    public Tile3(Pair<Point2D, Double> initialConditions) {
        super(initialConditions);
    }

    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon inner = Hex.hex(RATIO_2, HOR);

        return new PayloadSimple(
                asList(
                        asList(
                                Pair.of(inner, ONE),
                                Pair.of(main, ONE),
                                Pair.of(inner, TWO)

                        )
                ), Hex.ALL_VERTEX_INDEXES
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

        double atan = Math.atan(0.5 / (2.0 * HEIGHT_RATIO));

        System.out.println("sssss " + (atan));

        Polygon main = Hex.hex(1, VER);
        Polygon outer = Hex.hex(0.5, VER, centreTransform(HEIGHT_RATIO, HOR));
//        Polygon outer2 = Hex.hex(0.5, Polygon.Type.VER, centreTransform(1, Polygon.Type.VER));
        Polygon mainReg = main.getRegistered();
        Polygon inner = Hex.hex(0.5 * 0.5, VER);
        Polygon inner2 = Hex.hex(1 - 0.5 * 0.5, VER);
        Polygon inner3 = Hex.hex(RATIO_2, HOR);

        List<Pair<Point2D, String>> importantPoints = Stream.of(
                Triple.of(inner, TWO, "A"),
                Triple.of(mainReg, ONE, "B"),
                Triple.of(mainReg, FOUR, "D"),
                Triple.of(main, Hex.Vertex.THREE, "C"),
                Triple.of(mainReg, TWO, "E"),
                Triple.of(main, FOUR, "F"),
                Triple.of(inner2, ONE, "G"),
                Triple.of(main, ONE, "H"),
                Triple.of(inner3, ONE, "I")
        ).map(importantPoint).collect(toList());

        importantPoints.add(Pair.of(initialConditions.getLeft(), "K"));
        List<String> equations = asList(
                "DC/DB = AK/KB",
                "KA=1/4",
                "HDB=th",
                "tan(th)=HB/BD=0.25/h",
                "DKH=5*(PI/6)",
                "KHD=PI-DKH-th=(PI/6)-th",
                "IHB=(PI/6)+th",
                "IB=0.5*tan(IHB)"
        );
        IntStream.range(0, equations.size())
                .forEach(i -> importantPoints.add(Pair.of(new Point2D.Double(1000, (i + 1) * 50), equations.get(i))));

        importantPoints.stream().map(drawText());
        importantPoints.stream().map(Pair::getLeft).map(highlightPoint());

        List<List<Point2D>> apply = toMixVertexesFull.apply(
                asList(
                        Pair.of(outer, FIVE),
                        Pair.of(mainReg, FOUR),
                        Pair.of(outer, TWO)
                )
        );

        return
                Stream.of(
                        Stream.of(
                                highlightPoints("black", 2).apply(hexGrid)
                        ),
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(main, Hex.DIAGONALS),
                                Pair.of(mainReg, Hex.DIAGONALS)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(outer, Hex.PERIMETER)
//                                Pair.of(outer2, Hex.PERIMETER),
//                                Pair.of(outer2, Hex.INNER_TRIANGLES)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(inner, Hex.PERIMETER),
                                Pair.of(inner3, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        importantPoints.stream().map(drawText()),
                        importantPoints.stream().map(Pair::getLeft).map(highlightPoint()),
//                        Stream.of(
//                                Triple.of(mainReg, outer, asList((Polygon.Vertex) Hex.Vertex.TWO, Hex.Vertex.FIVE))
//                        ).map(toStarFull.andThen(toPolylines(gray))),
                        Stream.of(
                                toPolylines(gray).apply(
                                        toMixVertexesFull.apply(
                                                asList(
                                                        Pair.of(outer, FIVE),
                                                        Pair.of(mainReg, FOUR),
                                                        Pair.of(outer, TWO)
                                                )
                                        ))
                        ),
                        Stream.of(
                                getPayloadSimple().toLines(initialConditions)
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());

    }

}