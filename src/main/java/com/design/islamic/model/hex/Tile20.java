package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Path;
import com.design.common.model.Path.Paths;
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
public class Tile20 {

    private static double RATIO_m = 1.0 / 6.0;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_20",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(VertexPaths.of(
                        VertexPath.of(
                                r(2, 5, DL_H),
                                r(2, 4, DL_H),
                                r(1, 3, DL_H),
                                r(2, 3, DL_H),
                                r(2, 2, DL_H),
                                r(1, 2, UR_H),
                                r(2, 3, DR_H)
                        ),
                        VertexPath.of(
                                r(1, 4, DL_H),
                                h(4, RIGHT),
                                r(3, 4, DR_H),
                                r(3, 4, DL_H),
                                r(2, 4, DL_H)
                        ),
                        VertexPath.of(
                                r(4, 4, DL_H),
                                r(4, 2, DR_H),
                                r(3, 2, DR_H)
                        )

                ), whiteBold)

//                .withGridConf(Grid.Configuration.customRect(2*RATIO_w, 2*RATIO_h))

                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();

    }

    private static ActualVertex h(int times, Corner corner) {
        return instruction(times * RATIO_m, corner);
    }

    private static ActualVertex r(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_m, Hex.centreTransform(timesCentre * RATIO_m, RIGHT), corner);
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, HOR);

        List<String> equations = asList(
                "r = 1 / 6"
        );

        Function<Polygon, Paths> xDiagonals = p -> Path.vertexPathsToPaths.apply(Polygon.toVertexPaths(p, asList(asList(TWO.cast(), FIVE), asList(THREE, SIX))));

        List<Pair<Polygon, Function<Polygon, Paths>>> diagonals1 = IntStream.range(1, 6).mapToObj(i -> Pair.of(Hex.hex(1, HOR, Polygon.centreTransform(i * RATIO_m, ONE, HOR)), xDiagonals)).collect(toList());
        List<Pair<Polygon, Function<Polygon, Paths>>> diagonals2 = IntStream.range(1, 6).mapToObj(i -> Pair.of(Hex.hex(1, HOR, Polygon.centreTransform(i * RATIO_m, FOUR, HOR)), xDiagonals)).collect(toList());

        List<Polygon> innerHexes = IntStream.range(1, 6).mapToObj(i -> Hex.hex(i * RATIO_m, HOR)).collect(toList());

        BiFunction<List<Polygon>, Polygon.Vertex, List<DesignHelper.ImportantVertex>> indexOnVertexes = (p, v) -> {
            AtomicInteger vIndex2 = new AtomicInteger(1);
            return p.stream().map(h -> DesignHelper.ImportantVertex.of(h, v, String.valueOf(vIndex2.getAndIncrement()))).collect(toList());
        };

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_20_design")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS)

                ), gray)
                .addSinglePaths(diagonals1, gray)
                .addSinglePaths(diagonals2, gray)
                .addImportantVertexes(asList(
//                        ImportantVertex.of(Hex.hex(RATIO_m, VER), ONE, "A")

                ))
//                .addImportantVertexes(innerHexes.stream().map(h->ImportantVertex.of(h, ONE, String.valueOf(vIndex.getAndIncrement()))).collect(toList()))
                .addImportantVertexes(Stream.of(ONE, TWO, SIX).map(v -> indexOnVertexes.apply(innerHexes, v)).map(Collection::stream).flatMap(s -> s).collect(toList()))
                .addSinglePaths(
                        innerHexes.stream().map(h -> Pair.of(h, PERIMETER)).collect(toList())
                        , gray)

                .addAllVertexesAsImportantPoints(asList(
//                        main
                ))

                ;

    }

}