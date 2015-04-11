package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import com.design.islamic.model.tiles.Grid;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.H;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.instruction;
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

    private static List<Pair<Polygon, Polygon.Vertex>> toLeft(List<Pair<Polygon, Polygon.Vertex>> right) {
        return right.stream().map(r -> Pair.of(r.getLeft(), toLeft.get(r.getRight()))).collect(toList());
    }

    private static double RATIO_m = 1.0 / 7.0;
    private static double RATIO_w = 6 * H * RATIO_m;
    private static double RATIO_h = 5 * RATIO_m;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Polygon main = Hex.hex(1, VER);
        Polygon hexKA1 = Hex.hex(RATIO_m, VER);
        Polygon hexKA2 = Hex.hex(2 * RATIO_m, VER);
        Polygon hexKA3 = Hex.hex(3 * RATIO_m, VER);
        Polygon hexKA4 = Hex.hex(4 * RATIO_m, VER);
        Polygon hexKA5 = Hex.hex(5 * RATIO_m, VER);
        Polygon hexKA6 = Hex.hex(6 * RATIO_m, VER);

        List<Pair<Polygon, Polygon.Vertex>> l1 = asList(
                Pair.of(h(2, 6, TWO), SIX),
                Pair.of(h(2, 4, TWO), SIX),
                h(4, DR_V),
                h(4, UR_V),
                u(2, 4, DR_V),
                u(2, 6, DR_V)
        );
        List<Pair<Polygon, Polygon.Vertex>> l2 = asList(
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
                .withLines(
                        asList(
                                asList(
                                        instruction(hexKA1, UR_V),
                                        instruction(hexKA2.getRegistered(), RIGHT),
                                        instruction(hexKA1, DR_V)
                                )

                        )
                )
                .withLinesSingle(
                        asList(
                                l2,
                                toLeft(l2),
                                l1,
                                toLeft(l1)
                        )
                )
                .withGridConf(Grid.Configuration.customRect(2 * RATIO_w, 2 * RATIO_h))

                .build();

    }

//    private static Polygon h(int times) {
//        return Hex.hex(times * RATIO_m, VER);
//    }

    private static Pair<Polygon, Polygon.Vertex> h(int times, Hex.Corner corner) {
        return instruction(times * RATIO_m, corner);
    }

    private static Polygon h(int times, int timesCentre) {
        return h(times, timesCentre, ONE);
    }

    private static Pair<Polygon, Polygon.Vertex> u(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_m, Hex.centreTransform(timesCentre * RATIO_m, UP), corner);
    }

    private static Pair<Polygon, Polygon.Vertex> d(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_m, Hex.centreTransform(timesCentre * RATIO_m, DOWN), corner);
    }

    private static Polygon h(int times, int timesCentre, Polygon.Vertex vertex) {
        return Hex.hex(times * RATIO_m, VER, Polygon.centreTransform(timesCentre * RATIO_m, vertex, VER));
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

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
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addSingleLinesInstructionsList(getPayloadSimple().getLinesSingle(), red)
                .addEquations(equations)
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)

                ), gray)
                .addLinesInstructions(
                        IntStream.range(1, 7).mapToObj(i -> Pair.of(Hex.hex(i * RATIO_m, VER), Hex.PERIMETER)).collect(toList())
                        , gray)
                .addImportantPoints(asList(
                        Triple.of(hexKA1, Hex.Vertex.ONE, "A1"),
                        Triple.of(hexKA2, Hex.Vertex.ONE, "A2"),
                        Triple.of(hexKA3, Hex.Vertex.ONE, "A3"),
                        Triple.of(hexKA4, Hex.Vertex.ONE, "A4"),
                        Triple.of(hexKA5, Hex.Vertex.ONE, "A5"),
                        Triple.of(hexKA6, Hex.Vertex.ONE, "A6"),
                        Triple.of(main, Hex.Vertex.ONE, "A7")
                ))
                .addLinesInstructions(asList(
                        Pair.of(
                                Hex.hex(RATIO_w, HOR, Hex.centreTransform(RATIO_h, UP)),
                                asList(asList(ONE.cast(), FOUR.cast()))
                        ),
                        Pair.of(
                                Hex.hex(RATIO_w, HOR, Hex.centreTransform(RATIO_h, DOWN)),
                                asList(asList(ONE.cast(), FOUR.cast()))
                        )
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(
                                Hex.hex(RATIO_h, VER, Hex.centreTransform(RATIO_w, RIGHT)),
                                asList(asList(TWO.cast(), FIVE.cast()))
                        ),
                        Pair.of(
                                Hex.hex(RATIO_h, VER, Hex.centreTransform(RATIO_w, LEFT)),
                                asList(asList(TWO.cast(), FIVE.cast()))
                        )
                ), blue)
//                .addMixedLinesInstructionsList(grid, green)
                .addAllVertexesAsImportantPoints(asList(
                        main
                ))

                ;

    }

}