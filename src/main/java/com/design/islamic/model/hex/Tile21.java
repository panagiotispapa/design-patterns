package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Path;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.Polygon.Type.HOR;
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

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_21",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(() -> asList(
                        () -> asList(
                                h(1, RIGHT),
                                r(2, 1, DR_H),
                                r(2, 2, DR_H),
                                r(1, 2, DR_H),
                                h(1, DR_H)
                        ),
                        () -> asList(
                                r(1, 3, DL_H),
                                h(3, RIGHT),
                                rs(5, 8, DR_H),
                                rs(3, 10, DR_H),
                                rs(1, 10, DR_H),
                                rs(1, 15, DR_H)
                        ),
                        () -> asList(
                                rs(5, 8, DL_H),
                                h(3, DR_H),
                                rs(8, 13, DL_H),
                                rs(10, 13, DL_H),
                                rs(10, 11, DL_H),
                                rs(15, 16, DL_H)
                        ),
                        () -> asList(
                                rs(10, 13, DL_H),
                                rs(12, 15, DL_H),
                                rs(3, 15, DL_H),
                                rs(3, 13, DL_H)
                        )
                ), whiteBold)
                .withSize(PayloadSimple.Size.MEDIUM)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();

    }

    private static ActualVertex r(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_m, Hex.centreTransform(timesCentre * RATIO_m, RIGHT), corner);
    }

    private static ActualVertex rs(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_n, Hex.centreTransform(timesCentre * RATIO_n, RIGHT), corner);
    }

    private static ActualVertex h(int times, Corner corner) {
        return instruction(times * RATIO_m, corner);
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, HOR);

        List<String> equations = asList(
                "r = 1 / 6",
                "r2 = 1 / 16"
        );

        Function<Polygon, List<Path>> xDiagonals = p -> Path.vertexPathsToPaths.apply(Polygon.toVertexPaths(p, asList(asList(TWO.cast(), FIVE), asList(THREE, SIX))));

        int N = 6;
        int N2 = 16;

        List<Pair<Polygon, Function<Polygon, List<Path>>>> diagonals1 = IntStream.range(1, N).mapToObj(i -> Pair.of(Hex.hex(1, HOR, Polygon.centreTransform(i * RATIO_m, ONE, HOR)), xDiagonals)).collect(toList());
        List<Pair<Polygon, Function<Polygon, List<Path>>>> diagonals2 = IntStream.range(1, N).mapToObj(i -> Pair.of(Hex.hex(1, HOR, Polygon.centreTransform(i * RATIO_m, FOUR, HOR)), xDiagonals)).collect(toList());

        List<Polygon> innerHexes = IntStream.range(1, N).mapToObj(i -> Hex.hex(i * RATIO_m, HOR)).collect(toList());
        List<Polygon> innerHexesSmall = IntStream.range(1, N2).mapToObj(i -> Hex.hex(i * RATIO_n, HOR)).collect(toList());

        BiFunction<List<Polygon>, Polygon.Vertex, List<Triple<Polygon, Polygon.Vertex, String>>> indexOnVertexes = (p, v) -> {
            AtomicInteger vIndex2 = new AtomicInteger(1);
            return p.stream().map(h -> Triple.of(h, v, String.valueOf(vIndex2.getAndIncrement()))).collect(toList());
        };

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_21_design")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(main.getRegistered(), DIAGONALS)

                ), gray)
                .addSinglePaths(diagonals1, gray)
                .addSinglePaths(diagonals2, gray)
                .addImportantPoints(asList(
//                        Triple.of(Hex.hex(RATIO_m, VER), ONE, "A")

                ))
                .addImportantPoints(Stream.of(ONE, TWO, SIX).map(v -> indexOnVertexes.apply(innerHexes, v)).map(Collection::stream).flatMap(s -> s).collect(toList()))
                .addSinglePaths(
                        innerHexes.stream().map(h -> Pair.of(h, PERIMETER)).collect(toList())
                        , blue)
                .addSinglePaths(
                        innerHexesSmall.stream().map(h -> Pair.of(h, PERIMETER)).collect(toList())
                        , gray)

                .addAllVertexesAsImportantPoints(asList(
//                        main
                ))

                ;

    }

}