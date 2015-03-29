package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.common.RatioHelper.Ratios;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.$H;
import static com.design.islamic.model.Hex.Corner.RIGHT;
import static com.design.islamic.model.Hex.Corner.UR_H;
import static com.design.islamic.model.Hex.Corner.UR_V;
import static com.design.islamic.model.Hex.instruction;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.util.Arrays.asList;

public class Tile27 {

    private static double ANGLE_1 = PI / 3.0 - PI / 4.0;
    private static double BC = 0.5 * Math.tan(ANGLE_1);
    private static double KB = $H.apply(1.0);
    private static double KC = KB - BC;
    private static double AC = 0.5 / cos(ANGLE_1);
    private static double AD = 2.0 * Ratios.RECT.$H().apply(AC);
    private static double KD = 1-AD;
//    private static double RATIO_CD = £H.andThen($1).apply(RATIO_KA);

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon mainReg = main.getRegistered();
        Polygon hexKC = Hex.hex(KC, HOR);
        Polygon hexKD = Hex.hex(KD, VER);

        return new PayloadSimple.Builder("hex_tile_27",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(hexKD, UR_V),
                                instruction(hexKC, RIGHT),
                                instruction(main, UR_V),
                                instruction(hexKC, UR_H),
                                instruction(hexKD, UR_V)
                        )
//                        asList(
//                                instruction(outer, DL_V),
//                                instruction(inner, RIGHT)
//                        )

                ))
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
        Polygon mainReg = main.getRegistered();
        Polygon hexKC = Hex.hex(KC, HOR);
        Polygon hexKD = Hex.hex(KD, VER);

        List<String> equations = asList(
                "CAB = 60 - 45 = 15 = th",
                "BC = tan(th) * AB",
                "AC = AB/cos(th)",
                "AD = 2 * AC * cos(45)",
                "KD = 1 - AD",
                "KC = h - BC"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_27_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, UR_V.getVertex(), "A"),
                        Triple.of(mainReg, RIGHT.getVertex(), "B"),
                        Triple.of(hexKC, RIGHT.getVertex(), "C"),
                        Triple.of(hexKD, UR_V.getVertex(), "D")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainReg, Hex.DIAGONALS)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(hexKC, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(hexKD, Hex.PERIMETER)
                ), blue)
                ;

    }

}