package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

//p.
public class Tile16 {

    private static final Map<Polygon.Vertex, Polygon.Vertex> toLeft;

    static {
        toLeft = Maps.newHashMap();
        toLeft.put(ONE, THREE);
        toLeft.put(SIX, FOUR);
        toLeft.put(TWO, TWO);
        toLeft.put(THREE, THREE);
        toLeft.put(FOUR, FOUR);
        toLeft.put(FIVE, FIVE);
    }

    private static List<Pair<Polygon, Polygon.Vertex>> toLeft(List<Pair<Polygon, Polygon.Vertex>> right) {
        return right.stream().map(r -> Pair.of(r.getLeft(), toLeft.get(r.getRight()))).collect(toList());
    }

    private static double RATIO_m = 1.0 / 9.0;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        List<Pair<Polygon, Polygon.Vertex>> l1 = asList(
                h(8, UP),
                u(5, 8),
                u(3, 6),
                u(1, 6),
                u(1, 1),
                u(6, 1),
                u(6, 3),
                u(8, 5),
                h(8, DR_V),
                d(3, 8),
                d(3, 6), //
                d(5, 6),
                h(1, DOWN)
        );

        List<Pair<Polygon, Polygon.Vertex>> l2 = asList(
                u(3, 6),
                u(3, 4),
                u(8, 9)
        );

        List<Pair<Polygon, Polygon.Vertex>> l3 = asList(
                u(6, 3),
                u(4, 3),
                u(9, 8)
        );

        List<Pair<Polygon, Polygon.Vertex>> l4 = asList(
                d(3, 6),
                d(1, 4),
                d(1, 9)
        );

        return new PayloadSimple.Builder("hex_tile_16",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLinesSingle(
                        asList(
                                l1,
                                toLeft(l1),
                                l2,
                                toLeft(l2),
                                l3,
                                toLeft(l3),
                                l4,
                                toLeft(l4)
                        )
                )
//                .withGridConf(Grid.Configuration.customRect(2*RATIO_w, 2*RATIO_h))

                .build();

    }

    private static Pair<Polygon, Polygon.Vertex> u(int times, int timesCentre) {
        return instruction(times * RATIO_m, Hex.centreTransform(timesCentre * RATIO_m, UP), DR_V);
    }

    private static Pair<Polygon, Polygon.Vertex> d(int times, int timesCentre) {
        return instruction(times * RATIO_m, Hex.centreTransform(timesCentre * RATIO_m, DOWN), UR_V);
    }


    private static Pair<Polygon, Polygon.Vertex> h(int times, Hex.Corner corner) {
        return instruction(times * RATIO_m, corner);
    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Hex.hex(1, VER);

        List<String> equations = asList(
                "r = 1 / 9"
        );

        List<List<Polygon.Vertex>> xDiagonals = asList(asList(TWO.cast(), FIVE), asList(THREE, SIX));

        List<Pair<Polygon, List<List<Polygon.Vertex>>>> diagonals1 = IntStream.range(1, 9).mapToObj(i -> Pair.of(Hex.hex(1, VER, Polygon.centreTransform(i * RATIO_m, ONE, VER)), xDiagonals)).collect(toList());
        List<Pair<Polygon, List<List<Polygon.Vertex>>>> diagonals2 = IntStream.range(1, 9).mapToObj(i -> Pair.of(Hex.hex(1, VER, Polygon.centreTransform(i * RATIO_m, FOUR, VER)), xDiagonals)).collect(toList());

        List<Polygon> innerHexes = IntStream.range(1, 9).mapToObj(i -> Hex.hex(i * RATIO_m, VER)).collect(toList());

        BiFunction<List<Polygon>, Polygon.Vertex, List<Triple<Polygon, ? extends Polygon.Vertex, String>>> indexOnVertexes = (p, v) -> {
            AtomicInteger vIndex2 = new AtomicInteger(1);
            return p.stream().map(h -> Triple.of(h, v, String.valueOf(vIndex2.getAndIncrement()))).collect(toList());
        };

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_16_design")
                .addSingleLinesInstructionsList(getPayloadSimple().getLinesSingle(), red)
                .addEquations(equations)
                .addLinesInstructions(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS)

                ), gray)
                .addLinesInstructions(diagonals1, gray)
                .addLinesInstructions(diagonals2, gray)
                .addImportantPoints(asList(
//                        Triple.of(Hex.hex(RATIO_m, VER), ONE, "A")

                ))
//                .addImportantPoints(innerHexes.stream().map(h->Triple.of(h, ONE, String.valueOf(vIndex.getAndIncrement()))).collect(toList()))
                .addImportantPoints(Stream.of(ONE, TWO, SIX, FIVE).map(v -> indexOnVertexes.apply(innerHexes, v)).map(Collection::stream).flatMap(s -> s).collect(toList()))
                .addLinesInstructions(
                        innerHexes.stream().map(h -> Pair.of(h, PERIMETER)).collect(toList())
                        , gray)

                .addAllVertexesAsImportantPoints(asList(
//                        main
                ))

                ;

    }

}