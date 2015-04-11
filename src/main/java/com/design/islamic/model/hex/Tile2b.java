package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Mappings;
import com.design.common.Polygon;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

public class Tile2b {

    private static double KB = H / (H + 0.5);
    private static double BD = Mappings.<Double>chain(i -> 1 - i, i -> i * H).apply(KB);
    private static double KC = KB * H;
    private static double KF = 0.5 * KB;
    private static double KG = 0.5 * KB * H;


    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon hexKB = Hex.hex(KB, VER);
        Polygon hexBD = Hex.hex(BD, HOR, Hex.centreTransform(KB, DR_V));
        Polygon hexKF = Hex.hex(KF, VER);
        Polygon hexKC = Hex.hex(KC, HOR);
        Polygon hexBG = Hex.hex(KG, HOR, centreTransform(KB, VER));

        return new PayloadSimple.Builder("hex_tile_02b",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(
                        asList(
                                asList(
                                        instruction(hexBD, RIGHT),
                                        instruction(hexKB, DR_V),
                                        instruction(hexBD, DR_H)
                                ),
                                asList(
                                        instruction(hexKB, DR_V),
                                        instruction(hexKB, DOWN)
                                ),
                                asList(
                                        instruction(hexKF, DR_V),
                                        instruction(hexKC, DR_H),
                                        instruction(hexKF, DOWN)
                                ),
                                asList(
                                        instruction(hexKC, DR_H),
                                        instruction(hexBG, DL_H),
                                        instruction(hexBD, DR_H),
                                        instruction(hexBD, RIGHT),
                                        instruction(hexBG, UR_H),
                                        instruction(hexKC, RIGHT)
                                )
                        )
                )
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
        Polygon hexKB = Hex.hex(KB, VER);
        Polygon hexKBReg = hexKB.getRegistered();
        Polygon hexKF = Hex.hex(KF, VER);
        Polygon hexBG = Hex.hex(KG, HOR, centreTransform(KB, VER));

        Polygon outer = Hex.hex(BD, HOR, Hex.centreTransform(KB, VER));

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_02b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(asList(
                        "KB=h/(h+0.5)",
                        "BD=h*(1-KB)",
                        "KF=KB/2",
                        "KG=(KB/2)*h"
                ))
                .addImportantPoints(asList(
                        Triple.of(main, DR_V.getVertex(), "A"),
                        Triple.of(main.getRegistered(), RIGHT.getVertex(), "E"),
                        Triple.of(hexKB, DR_V.getVertex(), "B"),
                        Triple.of(hexKBReg, RIGHT.getVertex(), "C"),
                        Triple.of(outer, DR_V.getVertex(), "D"),
                        Triple.of(hexKF, DR_V.getVertex(), "F"),
                        Triple.of(hexBG, DL_H.getVertex(), "G")

                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(main.getRegistered(), Hex.DIAGONALS),
                        Pair.of(outer, Hex.DIAGONALS)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(hexKB, Hex.PERIMETER),
                        Pair.of(hexBG, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(outer, Hex.PERIMETER)
                ), blue)
                ;

    }

}