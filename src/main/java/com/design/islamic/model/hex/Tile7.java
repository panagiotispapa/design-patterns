package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.£2;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Tile7 extends TileBasic {

    public static double RATIO_AB = 1.0 - HEIGHT_RATIO;
    public static double RATIO_BE = $P.apply(RATIO_AB);
    public static double RATIO_KD = $H.andThen(£2.andThen(£H)).apply(1.0);

    public Tile7(Pair<Point2D, Double> initialConditions) {

        super(initialConditions);

    }

    public static PayloadSimple getPayloadSimple() {
        Polygon inner = Hex.hex(RATIO_KD, HOR);
        Polygon outer = Hex.hex(RATIO_BE, VER, centreTransform(1, VER));
        Polygon outerBig = Hex.hex(0.5, VER, centreTransform(1, VER));

        return new PayloadSimple(
                Arrays.asList(
                        asList(
                                Pair.of(outer, FIVE),
                                Pair.of(inner, TWO)
                        ),
                        asList(
                                Pair.of(outer, THREE),
                                Pair.of(inner, ONE)
                        ),
                        asList(
                                Pair.of(outerBig, FIVE),
                                Pair.of(outerBig, FOUR),
                                Pair.of(outerBig, THREE)
                        )
                )
                , Hex.ALL_VERTEX_INDEXES
        );
    }

    public String design1() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        List<Point2D> hexGrid = Grid.grid(initialConditions.getLeft(), initialConditions.getRight() / 4.0, Grid.Configs.HEX_VER.getConfiguration(), 12);
//        List<Point2D> hexGrid = Grid.gridFromStart(new Point2D.Double(0, 0), initialConditions.getRight() / 4.0,
//                Grid.Configuration.customRect(RECT_DIST_HEIGHT * 2 * 1.2, RECT_DIST_HEIGHT * 2), 24);
//                Grid.Configs.HEX_VER2.getConfiguration(), 24);

//        CentreConfiguration centreConfiguration = new CentreConfiguration(initialConditions.getRight() / 4.0, 8);
//        Set<Point2D> centresConfig = centreConfiguration.getCentresConfig(CentreConfiguration.Conf.HEX_THIRD, 1.0);

        Polygon main = Hex.hex(1, VER);
        Polygon mainReg = main.getRegistered();
        Polygon mainHor = main.getMirror();
        Polygon mainHorReg = mainHor.getRegistered();
        Polygon inner = Hex.hex(0.5, VER);
        Polygon innerReg = inner.getRegistered();
        Polygon innerHor = inner.getMirror();
        Polygon innerHorReg = innerHor.getRegistered();

        Polygon outerSmall = Hex.hex(RATIO_BE, VER, centreTransform(1, VER));
//        Polygon inner1 = Hex.hex(RATIO_1, HOR);
//        Polygon inner2 = Hex.hex(0.5, VER);
//        Polygon outer = Hex.hex(0.5, VER, centreTransform(1, VER));
//        Polygon outerReg = outer.getRegistered();

//        List<Pair<Point2D, String>> importantPoints = new ArrayList<>();
        List<Pair<Point2D, String>> importantPoints = Stream.of(
                Triple.of(main, ONE, "B"),
                Triple.of(mainHorReg, ONE, "A"),
                Triple.of(innerHorReg, ONE, "C"),
                Triple.of(inner, ONE, "D"),
                Triple.of(outerSmall, FIVE, "E")
        ).map(importantPoint).collect(toList());

        importantPoints.add(Pair.of(initialConditions.getLeft(), "K"));
        List<String> equations = asList(
                "AB=1-h",
                "KC=0.5*h",
                "KD=KC/h",
                "BE=0.5*AB"
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
                                Pair.of(mainHor, Hex.PERIMETER),
                                Pair.of(main, Hex.DIAGONALS),
                                Pair.of(mainHor, Hex.DIAGONALS),
                                Pair.of(mainHorReg, Hex.INNER_TRIANGLES)
//                                Pair.of(main.getRegistered(), Hex.INNER_TRIANGLES),
//                                Pair.of(outer, Hex.PERIMETER),
//                                Pair.of(outerReg, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(inner, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(innerHor, Hex.PERIMETER),
                                Pair.of(outerSmall, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        importantPoints.stream().map(drawText()),
                        importantPoints.stream().map(Pair::getLeft).map(highlightPoint()),
//                        Stream.of(centresConfig).map(highlightPoints()),
                        Stream.of(
                                getPayloadSimple().toLines(initialConditions)
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());
    }

}