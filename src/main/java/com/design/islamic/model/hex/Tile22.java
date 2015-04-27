package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Hex.*;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

//p.
public class Tile22 {


    private static double RATIO_x = 1.0 / 16.0;
    private static double RATIO_y = RATIO_x / H;

    private static Double y(double times) {
        return times * RATIO_y;
    }

    private static Pair<Polygon, Polygon.Vertex> u(double times, double timesCentre, Corner corner) {
        return instruction(y(times), centreTransform(y(timesCentre), Corner.UP), corner);
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_22",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFullFromLines(asList(
                        asList(
                                u(4, 3, Corner.DL_V),
                                u(1, 2, Corner.UL_V),
                                u(1, 2, Corner.DR_V)
                        ),
                        asList(
                                u(1, 3, Corner.UL_V),
                                u(5, 4, Corner.DL_V)
                        ),
                        asList(
                                u(1, 4, Corner.UL_V),
                                u(8, 5, Corner.DL_V)
                        ),
                        asList(
                                u(1, 6, Corner.DL_V),
                                u(9, 6, Corner.DL_V)
                        ),
                        asList(
                                u(1, 5, Corner.UP),
                                u(1, 6, Corner.UR_V),
                                u(1, 8.5, Corner.DR_V),
                                u(0.5, 8.5, Corner.DR_V),
                                u(1, 7, Corner.UP),
                                u(0.5, 7, Corner.UP),
                                u(0.5, 7.5, Corner.DL_V),
                                u(2, 9, Corner.DL_V),
                                u(2, 10, Corner.DL_V)
                        ),
                        asList(
                                u(1, 12.5, Corner.DR_V),
                                u(1, 11, Corner.DR_V),
                                u(7, 10, Corner.DL_V)
                        ),
                        asList(
                                u(1, 7, Corner.UP),
                                u(0.5, 8, Corner.UL_V),
                                u(0.5, 9, Corner.DL_V),
                                u(1, 9, Corner.UR_V),
                                u(2, 10, Corner.DR_V)
                        ),
                        asList(
                                u(2, 13, Corner.DR_V),
                                u(2, 7, Corner.DR_V),
                                u(4, 7, Corner.DR_V),
                                u(4, 9, Corner.DR_V),
                                u(6, 9, Corner.DR_V)
                        ),
                        asList(
                                u(3, 13.5, Corner.DR_V),
                                u(3, 8, Corner.DR_V),
                                u(5, 8, Corner.DR_V)
                        ),
                        asList(
                                u(4, 14, Corner.DR_V),
                                u(4, 11, Corner.DR_V),
                                u(11, 11, Corner.DR_V),
                                u(12, 12, Corner.DR_V),
                                u(11, 12, Corner.DR_V),
                                u(11.5, 12.5, Corner.DR_V)
                        ),
                        asList(
                                u(9, 11, Corner.DR_V),
                                u(11, 13, Corner.DR_V)
                        ),
                        asList(
                                u(5, 14.5, Corner.DR_V),
                                u(5, 12, Corner.DR_V),
                                u(9, 12, Corner.DR_V),
                                u(10.5, 13.5, Corner.DR_V)
                        ),
                        asList(
                                u(6, 15, Corner.DR_V),
                                u(6, 13, Corner.DR_V),
                                u(9, 13, Corner.DR_V),
                                u(10, 14, Corner.DR_V)
                        ),
                        asList(
                                u(7, 14, Corner.DR_V),
                                u(9, 14, Corner.DR_V),
                                u(9.5, 14.5, Corner.DR_V)
                        )

                ), whiteBold)

//                .withGridConf(Grid.Configuration.customRect(2*RATIO_w, 2*RATIO_h))
                .withSize(PayloadSimple.Size.LARGE)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();

    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, HOR);

        List<String> equations = asList(
                "KA = 1/9"
        );

        int N = 16;

        List<Polygon> innerHexesHor = IntStream.range(1, N).mapToObj(i -> Hex.hex(i * RATIO_x, HOR)).collect(toList());
        List<Polygon> innerHexesVer = IntStream.range(1, N).mapToObj(i -> Hex.hex(i * RATIO_y, VER)).collect(toList());

        BiFunction<List<Polygon>, Polygon.Vertex, List<Triple<Polygon, Polygon.Vertex, String>>> indexOnVertexes = (p, v) -> {
            AtomicInteger vIndex2 = new AtomicInteger(1);
            return p.stream().map(h -> Triple.of(h, v, String.valueOf(vIndex2.getAndIncrement()))).collect(toList());
        };

        Polygon mainRegistered = main.getRegistered();
        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_22_design")
                .addFullPaths(getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(mainRegistered, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(mainRegistered, DIAGONALS)

                ), gray)
                .addImportantPoints(asList(
//                        Triple.of(Hex.hex(RATIO_m, VER), ONE, "A")

                ))
//                .addImportantPoints(innerHexes.stream().map(h->Triple.of(h, ONE, String.valueOf(vIndex.getAndIncrement()))).collect(toList()))
                .addImportantPoints(Stream.of(ONE).map(v -> indexOnVertexes.apply(innerHexesHor, v)).map(Collection::stream).flatMap(s -> s).collect(toList()))
                .addImportantPoints(Stream.of(TWO, FIVE).map(v -> indexOnVertexes.apply(innerHexesVer, v)).map(Collection::stream).flatMap(s -> s).collect(toList()))
//                .addLinesInstructions(
//                        innerHexesHor.stream().map(h -> Pair.of(h, PERIMETER)).collect(toList())
//                        , gray)
                .addSinglePaths(
                        innerHexesVer.stream().map(h -> Pair.of(h, PERIMETER)).collect(toList()),
                        gray)
                .addAllVertexesAsImportantPoints(asList(
//                        main
                ))
                .withFontSize(12)


                ;

    }

}