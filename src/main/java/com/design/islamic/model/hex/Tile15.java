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

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Tile15 {

    public static double RATIO_y = 1.0 / 5.0;
    public static double RATIO_x = RATIO_y * H;

    private static final double RATIO_W = 6.0 * RATIO_x;
    private static final double RATIO_H = 1.0;

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

    private static Pair<Polygon, Polygon.Vertex> u(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_y, centreTransform(timesCentre * RATIO_y, UP), corner);
    }

    private static Pair<Polygon, Polygon.Vertex> d(int times, int timesCentre, Hex.Corner corner) {
        return instruction(times * RATIO_y, centreTransform(timesCentre * RATIO_y, DOWN), corner);
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        List<Pair<Polygon, Polygon.Vertex>> l1 = asList(
                d(6, 1, UR_V),
                instruction(5 * RATIO_y, UR_V),
                u(5, 2, UR_V),
                u(4, 3, UR_V),
                instruction(3 * RATIO_y, UP),
                u(1, 3, DR_V),
                u(1, 1, DR_V),
                u(6, 1, DR_V),
                instruction(5 * RATIO_y, DR_V),
                d(5, 2, DR_V),
                d(4, 3, DR_V),
                instruction(3 * RATIO_y, DOWN),
                d(1, 2, DR_V),
                instruction(RATIO_y, DR_V),
                d(6, 1, UR_V)
        );

        List<Pair<Polygon, Polygon.Vertex>> l2 = asList(
                instruction(RATIO_y, UP),
                u(6, 1, UR_V)
        );

        List<Pair<Polygon, Polygon.Vertex>> l3 = asList(
                instruction(RATIO_y, DOWN),
                d(6, 1, DR_V)
        );

        return new PayloadSimple.Builder("hex_tile_15",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLinesSingle(asList(
                        l1,
                        toLeft(l1),
                        l2,
                        toLeft(l2),
                        l3,
                        toLeft(l3)
                ))
                .withGridConf(Grid.Configuration.customRect(2.0 * RATIO_W, 2 * RATIO_H))
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Hex.hex(1, VER);
        List<String> equations = Arrays.asList(
                "Ry = 1/5",
                "Rx = h * Ry",
                "h = 5 * Ry",
                "w = 6 * Rx"
        );

        List<List<Polygon.Vertex>> diag = asList(asList(UL_V.getVertex(), DR_V.getVertex()), asList(UR_V.getVertex(), DL_V.getVertex()));
        List<List<Polygon.Vertex>> diag2 = asList(asList(UP.getVertex(), DOWN.getVertex()));

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_15_design")
                .addSingleLinesInstructionsList(getPayloadSimple().getLinesSingle(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(main.getRegistered(), Hex.DIAGONALS)
                ), gray)
                .addLinesInstructions(IntStream.range(1, 6)
                        .mapToObj(i -> Pair.of(Hex.hex(1, VER, centreTransform(i * RATIO_y, DOWN)), diag)).collect(toList())
                        , gray)
                .addLinesInstructions(IntStream.range(1, 6)
                        .mapToObj(i -> Pair.of(Hex.hex(1, VER, centreTransform(i * RATIO_y, UP)), diag)).collect(toList())
                        , gray)
                .addLinesInstructions(IntStream.range(1, 6)
                        .mapToObj(i -> Pair.of(Hex.hex(1, VER, centreTransform(i * RATIO_x, RIGHT)), diag2)).collect(toList())
                        , gray)
                .addLinesInstructions(IntStream.range(1, 6)
                        .mapToObj(i -> Pair.of(Hex.hex(1, VER, centreTransform(i * RATIO_x, LEFT)), diag2)).collect(toList())
                        , gray)
                .addLinesInstructions(asList(
                        Pair.of(Hex.hex(RATIO_W, HOR, centreTransform(RATIO_H, DOWN)), asList(asList(RIGHT.getVertex(), LEFT.getVertex()))),
                        Pair.of(Hex.hex(RATIO_W, HOR, centreTransform(RATIO_H, UP)), asList(asList(RIGHT.getVertex(), LEFT.getVertex())))
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(Hex.hex(RATIO_H, VER, centreTransform(RATIO_W, RIGHT)), asList(asList(UP.getVertex(), DOWN.getVertex()))),
                        Pair.of(Hex.hex(RATIO_H, VER, centreTransform(RATIO_W, LEFT)), asList(asList(UP.getVertex(), DOWN.getVertex())))
                ), blue)
                ;

    }

}