package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.centreTransform;
import static com.design.islamic.model.Hex.hex;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile24 {

    public static double KB = 1.0 / 3.0;
    public static double BC = KB;
    public static double AD = BC / 2.0;
    public static double KD = 1 - AD;
    public static double KC = KB + BC;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon hexKB = Hex.hex(KB, VER);
        Polygon hexBC = Hex.hex(BC, VER, centreTransform(KC, UP));
        Polygon hexDA = Hex.hex(AD, VER, centreTransform(KD, UP));
        Polygon hexDA_Reg = hexDA.getRegistered();

        return new PayloadSimple.Builder("hex_tile_24",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(hexKB, DL_V),
                                instruction(hexBC, UL_V),
                                instruction(hexDA_Reg, DL_H),
                                instruction(hexDA_Reg, UR_H)
                        ),
                        asList(
                                instruction(hexKB, UR_V),
                                instruction(hexBC, UR_V),
                                instruction(hexDA_Reg, DR_H),
                                instruction(hexDA_Reg, UL_H)
                        )

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
        Polygon hexKB = Hex.hex(KB, VER);
        Polygon hexKC = Hex.hex(KC, VER);
        Polygon hexBC = Hex.hex(BC, VER, centreTransform(KC, UP));
        Polygon hexDA = Hex.hex(AD, VER, centreTransform(KD, UP));

        List<String> equations = Arrays.asList(
                "KB = KA/3",
                "BC = KB",
                "CD = DA = CB/2"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_24_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, Hex.Vertex.FIVE, "A"),
                        Triple.of(hexKB, Hex.Vertex.FIVE, "B"),
                        Triple.of(hexKC, Hex.Vertex.FIVE, "C"),
                        Triple.of(hex(KD, VER), Hex.Vertex.FIVE, "D")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(hexBC, Hex.PERIMETER),
                        Pair.of(hexDA, Hex.PERIMETER),
                        Pair.of(hexDA.getRegistered(), Hex.DIAGONALS),
                        Pair.of(hexKB, Hex.PERIMETER)
                ), gray)
                ;

    }

}