package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.RatioHelper.P6;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

//p. 3
public class Tile12 {

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

    private static Polygon.VertexPath toLeft(Polygon.VertexPath right) {
        return () -> right.get().stream().map(r -> ActualVertex.of(r.get().getLeft(), toLeft.get(r.get().getRight()))).collect(toList());
    }

    private static double RATIO_m = 1.0 / 7.0;
    private static double RATIO_w = 6 * P6.H * RATIO_m;
    private static double RATIO_h = 5 * RATIO_m;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        Polygon main = Hex.hex(1, VER);
        Polygon hexKA1 = Hex.hex(RATIO_m, VER);
        Polygon hexKA2 = Hex.hex(2 * RATIO_m, VER);
        Polygon hexKA3 = Hex.hex(3 * RATIO_m, VER);
        Polygon hexKA4 = Hex.hex(4 * RATIO_m, VER);
        Polygon hexKA5 = Hex.hex(5 * RATIO_m, VER);
        Polygon hexKA6 = Hex.hex(6 * RATIO_m, VER);

        Polygon.VertexPath l1 = VertexPath.of(
                () -> Pair.of(h(2, 6, TWO), SIX),
                () -> Pair.of(h(2, 4, TWO), SIX),
                h(4, DR_V),
                h(4, UR_V),
                u(2, 4, DR_V),
                u(2, 6, DR_V)
        );

        Polygon.VertexPath l2 = VertexPath.of(
                u(6, 1, UR_V),
                u(1, 2, DR_V),
                u(1, 3, DR_V),
                h(3, UP),
                u(4, 7, DR_V),
                u(5, 7, DR_V),
                u(5, 5, DR_V),
                u(6, 5, DR_V),
                u(2, 1, DR_V),
                u(6, 1, DR_V),
                h(5, DR_V),
                d(5, 2, DR_V),
                d(4, 3, DR_V),
                h(3, DOWN),
                d(1, 2, DR_V),
                d(1, 1, DR_V),
                d(6, 1, DR_V)
        );

        return new PayloadSimple.Builder("hex_tile_12",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        VertexPaths.of(
                                VertexPath.of(
                                        instruction(hexKA1, UR_V),
                                        instruction(hexKA2.getRegistered(), RIGHT),
                                        instruction(hexKA1, DR_V)
                                )
                        ), whiteBold
                )
                .withPathsSingle(
                        VertexPaths.of(
                                l2,
                                toLeft(l2),
                                l1,
                                toLeft(l1)
                        ), whiteBold
                )
                .withGridConf(Grid.Configuration.customRect(2 * RATIO_w, 2 * RATIO_h))

                .build();

    }

//    private static Polygon h(int times) {
//        return Hex.hex(times * RATIO_m, VER);
//    }

    private static ActualVertex h(int times, Hex.Corner corner) {
        return instruction(times * RATIO_m, corner);
    }

    private static Polygon h(int times, int timesCentre) {
        return h(times, timesCentre, ONE);
    }

    private static ActualVertex u(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_m, Hex.centreTransform(timesCentre * RATIO_m, UP), corner);
    }

    private static ActualVertex d(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_m, Hex.centreTransform(timesCentre * RATIO_m, DOWN), corner);
    }

    private static Polygon h(int times, int timesCentre, Polygon.Vertex vertex) {
        return Hex.hex(times * RATIO_m, VER, Polygon.centreTransform(timesCentre * RATIO_m, vertex, VER));
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, VER);
        Polygon hexKA1 = Hex.hex(RATIO_m, VER);
        Polygon hexKA2 = Hex.hex(2 * RATIO_m, VER);
        Polygon hexKA3 = Hex.hex(3 * RATIO_m, VER);
        Polygon hexKA4 = Hex.hex(4 * RATIO_m, VER);
        Polygon hexKA5 = Hex.hex(5 * RATIO_m, VER);
        Polygon hexKA6 = Hex.hex(6 * RATIO_m, VER);

//        Polygon hexAC = Hex.hex(AC, HOR, centreTransform(1, VER));

        List<String> equations = asList(
                "KB = KD = AC = 1 / 7",
                "EC = 0.5 - AB"
        );

        List<List<Pair<Polygon, Polygon.Vertex>>> grid = IntStream.range(1, 6).mapToObj(i -> {
            Polygon p = h(i, 8 - i);
            return asList(
                    Pair.of(p, THREE.cast()),
                    Pair.of(p, FOUR.cast()),
                    Pair.of(p, FIVE.cast())
            );
        }).collect(toList());

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_12_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addSinglePaths(() -> getPayloadSimple().getPathsSingle(), red)
                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)

                ), gray)
                .addSinglePaths(
                        IntStream.range(1, 7).mapToObj(i -> Pair.of(Hex.hex(i * RATIO_m, VER), Hex.PERIMETER)).collect(toList())
                        , gray)
                .addImportantVertexes(
                        ImportantVertex.of(hexKA1, Hex.Vertex.ONE, "A1"),
                        ImportantVertex.of(hexKA2, Hex.Vertex.ONE, "A2"),
                        ImportantVertex.of(hexKA3, Hex.Vertex.ONE, "A3"),
                        ImportantVertex.of(hexKA4, Hex.Vertex.ONE, "A4"),
                        ImportantVertex.of(hexKA5, Hex.Vertex.ONE, "A5"),
                        ImportantVertex.of(hexKA6, Hex.Vertex.ONE, "A6"),
                        ImportantVertex.of(main, Hex.Vertex.ONE, "A7")
                )
                .addSinglePaths(asList(
                        Pair.of(Hex.hex(RATIO_w, HOR, Hex.centreTransform(RATIO_h, UP)), DIAGONAL_ONE),
                        Pair.of(Hex.hex(RATIO_w, HOR, Hex.centreTransform(RATIO_h, DOWN)), DIAGONAL_ONE)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(Hex.hex(RATIO_h, VER, Hex.centreTransform(RATIO_w, RIGHT)), DIAGONAL_TWO),
                        Pair.of(Hex.hex(RATIO_h, VER, Hex.centreTransform(RATIO_w, LEFT)), DIAGONAL_TWO)
                ), blue)
//                .addMixedLinesInstructionsList(grid, green)
                .addAllVertexesAsImportantPoints(asList(
                        main
                ))

                ;

    }

}