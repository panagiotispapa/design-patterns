package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

//p.
public class Tile21 {

    private static double RATIO_m = 1.0 / 6.0;
    private static double RATIO_n = 1.0 / 16.0;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        return new PayloadSimple.Builder("hex_tile_21",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                h(1, RIGHT),
                                r(2, 1, DR_H),
                                r(2, 2, DR_H),
                                r(1, 2, DR_H),
                                h(1, DR_H)
                        ),
                        asList(
                                r(1, 3, DL_H),
                                h(3, RIGHT),
                                rs(5, 8, DR_H),
                                rs(3, 10, DR_H),
                                rs(1, 10, DR_H),
                                rs(1, 15, DR_H)
                        ),
                        asList(
                                rs(5, 8, DL_H),
                                h(3, DR_H),
                                rs(8, 13, DL_H),
                                rs(10, 13, DL_H),
                                rs(10, 11, DL_H),
                                rs(15, 16, DL_H)
                        ),
                        asList(
                                rs(10, 13, DL_H),
                                rs(12, 15, DL_H),
                                rs(3, 15, DL_H),
                                rs(3, 13, DL_H)
                        )
                ))
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();

    }

    private static Pair<Polygon, Polygon.Vertex> r(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_m, Hex.centreTransform(timesCentre * RATIO_m, RIGHT), corner);
    }

    private static Pair<Polygon, Polygon.Vertex> rs(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_n, Hex.centreTransform(timesCentre * RATIO_n, RIGHT), corner);
    }

    private static Pair<Polygon, Polygon.Vertex> h(int times, Corner corner) {
        return instruction(times * RATIO_m, corner);
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Hex.hex(1, HOR);

        List<String> equations = asList(
                "r = 1 / 6",
                "r2 = 1 / 16"
        );

        List<List<Polygon.Vertex>> xDiagonals = asList(asList(TWO.cast(), FIVE), asList(THREE, SIX));

        int N = 6;
        int N2 = 16;

        List<Pair<Polygon, List<List<Polygon.Vertex>>>> diagonals1 = IntStream.range(1, N).mapToObj(i -> Pair.of(Hex.hex(1, HOR, Polygon.centreTransform(i * RATIO_m, ONE, HOR)), xDiagonals)).collect(toList());
        List<Pair<Polygon, List<List<Polygon.Vertex>>>> diagonals2 = IntStream.range(1, N).mapToObj(i -> Pair.of(Hex.hex(1, HOR, Polygon.centreTransform(i * RATIO_m, FOUR, HOR)), xDiagonals)).collect(toList());

        List<Polygon> innerHexes = IntStream.range(1, N).mapToObj(i -> Hex.hex(i * RATIO_m, HOR)).collect(toList());
        List<Polygon> innerHexesSmall = IntStream.range(1, N2).mapToObj(i -> Hex.hex(i * RATIO_n, HOR)).collect(toList());

        BiFunction<List<Polygon>, Polygon.Vertex, List<Triple<Polygon, ? extends Polygon.Vertex, String>>> indexOnVertexes = (p, v) -> {
            AtomicInteger vIndex2 = new AtomicInteger(1);
            return p.stream().map(h -> Triple.of(h, v, String.valueOf(vIndex2.getAndIncrement()))).collect(toList());
        };

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_21_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addLinesInstructions(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(main.getRegistered(), DIAGONALS)

                ), gray)
                .addLinesInstructions(diagonals1, gray)
                .addLinesInstructions(diagonals2, gray)
                .addImportantPoints(asList(
//                        Triple.of(Hex.hex(RATIO_m, VER), ONE, "A")

                ))
                .addImportantPoints(Stream.of(ONE, TWO, SIX).map(v -> indexOnVertexes.apply(innerHexes, v)).map(Collection::stream).flatMap(s -> s).collect(toList()))
                .addLinesInstructions(
                        innerHexes.stream().map(h -> Pair.of(h, PERIMETER)).collect(toList())
                        , blue)
                .addLinesInstructions(
                        innerHexesSmall.stream().map(h -> Pair.of(h, PERIMETER)).collect(toList())
                        , gray)

                .addAllVertexesAsImportantPoints(asList(
//                        main
                ))

                ;

    }

}