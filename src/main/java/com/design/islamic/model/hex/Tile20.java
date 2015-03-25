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
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.DIAGONALS;
import static com.design.islamic.model.Hex.PERIMETER;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

//p.
public class Tile20 {


    private static double RATIO_m = 1.0 / 6.0;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {


        return new PayloadSimple.Builder("hex_tile_20",
                Hex.ALL_VERTEX_INDEXES
        )

                .withLines(asList(
                        asList(
                                dl(2, 5),
                                dl(2, 4),
                                dl(1, 3),
                                dl(2, 3),
                                dl(2, 2),
                                ur(1, 2),
                                dr(2, 3)
                        ),
                        asList(
                                dl(1, 4),
                                Pair.of(h(4), ONE),
                                dr(3, 4),
                                dl(3, 4),
                                dl(2, 4)

                        ),
                        asList(
                                dl(4, 4),
                                dr(4, 2),
                                dr(3, 2)


                        )

                ))

//                .withGridConf(Grid.Configuration.customRect(2*RATIO_w, 2*RATIO_h))

                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();

    }


    private static Pair<Polygon, Polygon.Vertex> u(int times, int timesCentre) {
        return Pair.of(h(times, timesCentre, FIVE), ONE);

    }

    private static Pair<Polygon, Polygon.Vertex> d(int times, int timesCentre) {
        return Pair.of(h(times, timesCentre, TWO), SIX);

    }


    private static Pair<Polygon, Polygon.Vertex> ul(int times, int timesCentre) {
        return Pair.of(h(times, timesCentre), FIVE);
    }

    private static Pair<Polygon, Polygon.Vertex> ur(int times, int timesCentre) {
        return Pair.of(h(times, timesCentre), SIX);
    }

    private static Pair<Polygon, Polygon.Vertex> dl(int times, int timesCentre) {
        return Pair.of(h(times, timesCentre), THREE);
    }

    private static Pair<Polygon, Polygon.Vertex> dr(int times, int timesCentre) {
        return Pair.of(h(times, timesCentre), TWO);
    }


    private static Polygon h(int times) {
        return Hex.hex(times * RATIO_m, HOR);
    }

    private static Polygon h(int times, int timesCentre) {
        return h(times, timesCentre, ONE);
    }

    private static Polygon h(int times, int timesCentre, Polygon.Vertex vertex) {
        return Hex.hex(times * RATIO_m, HOR, Polygon.centreTransform(timesCentre * RATIO_m, vertex, HOR));
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
                "r = 1 / 6"
        );

        List<List<Polygon.Vertex>> xDiagonals = asList(asList(TWO.cast(), FIVE), asList(THREE, SIX));

        List<Pair<Polygon, List<List<Polygon.Vertex>>>> diagonals1 = IntStream.range(1, 6).mapToObj(i -> Pair.of(Hex.hex(1, HOR, Polygon.centreTransform(i * RATIO_m, ONE, HOR)), xDiagonals)).collect(toList());
        List<Pair<Polygon, List<List<Polygon.Vertex>>>> diagonals2 = IntStream.range(1, 6).mapToObj(i -> Pair.of(Hex.hex(1, HOR, Polygon.centreTransform(i * RATIO_m, FOUR, HOR)), xDiagonals)).collect(toList());

        List<Polygon> innerHexes = IntStream.range(1, 6).mapToObj(i -> Hex.hex(i * RATIO_m, HOR)).collect(toList());

        BiFunction<List<Polygon>, Polygon.Vertex, List<Triple<Polygon, ? extends Polygon.Vertex, String>>> indexOnVertexes = (p, v) -> {
            AtomicInteger vIndex2 = new AtomicInteger(1);
            return p.stream().map(h -> Triple.of(h, v, String.valueOf(vIndex2.getAndIncrement()))).collect(toList());
        };


        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_20_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
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
                .addImportantPoints(Stream.of(ONE, TWO, SIX).map(v -> indexOnVertexes.apply(innerHexes, v)).map(Collection::stream).flatMap(s -> s).collect(toList()))
                .addLinesInstructions(
                        innerHexes.stream().map(h -> Pair.of(h, PERIMETER)).collect(toList())
                        , gray)

                .addAllVertexesAsImportantPoints(asList(
//                        main
                ))


                ;

    }

}