package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;


public class Tile8 {

    private static final double KB = 0.5;
    private static final double KC = £H.apply(KB);
    private static final double AC = 1 - KC;
    private static final double AD = 1 - HEIGHT_RATIO;
    private static final double AE = $H.apply(AC);
    private static final double AF = $H.apply(AE);
    private static final double FE = AE * 0.5;
    private static final double AG = AC * 0.5;
    private static final double KF = 1 - AF;
    private static final double KH = FE;
    private static final double KI = £H.apply(KH);
    private static final double KJ = 2.0 * KH;
    private static final double JA = 1 - KJ;
    private static final double IJ = KJ - KI;
    private static final double JD = JA - AD;
    private static final double JM = $H.apply(IJ);

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon mainReg = main.getRegistered();
        Polygon hexAG = Hex.hex(AC * 0.5, VER, centreTransform(1, DR_V));
        Polygon hexJD = Hex.hex(JD, HOR, centreTransform(KJ, DR_V));
        Polygon hexJDRot = Hex.hex(JD, HOR, centreTransform(KJ, UR_V));
        Polygon hexJM = Hex.hex(JM, HOR, centreTransform(KJ, DR_V));
        Polygon hexJM_2 = Hex.hex(JM, VER, centreTransform(KJ, RIGHT));
        Polygon hexJM_3 = Hex.hex(JM, VER, centreTransform(KJ, DR_H));
        Polygon hexJM_4 = Hex.hex(JM, HOR, centreTransform(KJ, UR_V));

        return new PayloadSimple.Builder("hex_tile_08",
                 Hex.ALL_VERTEX_INDEXES
        )
                .withLines(                Arrays.asList(
                                asList(
                                        instruction(hexJM_3, UL_V),
                                        instruction(hexAG, DL_V),
                                        instruction(hexAG, UL_V),
                                        instruction(hexAG, UP),
                                        instruction(hexJM_2, UL_V)
                                ),
                                asList(

                                        instruction(hexJM_4, LEFT),
                                        instruction(hexJDRot, RIGHT),
                                        instruction(mainReg, RIGHT),
                                        instruction(hexJD, RIGHT),
                                        instruction(hexJM, LEFT)
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
        Polygon mainHor = main.getMirror();
        Polygon mainReg = main.getRegistered();
        Polygon mainHorReg = mainHor.getRegistered();

        Polygon hexKB = Hex.hex(0.5, VER);
        Polygon hexKC = Hex.hex(hexKB.getRatio() / HEIGHT_RATIO, VER);
        Polygon hexKD = Hex.hex(HEIGHT_RATIO, VER);
        Polygon hexAE = Hex.hex((1 - hexKC.getRatio()) * HEIGHT_RATIO, HOR, centreTransform(1, DR_V));
        Polygon hexAF = Hex.hex(hexAE.getRatio() * HEIGHT_RATIO, VER, centreTransform(1, DR_V));
//        Polygon hexFE = Hex.hex(hexAE.getRatio() * 0.5, VER);
        Polygon hexKF = Hex.hex(KF, HOR);
        Polygon hexAG = Hex.hex(AC * 0.5, VER, centreTransform(1, DR_V));
        Polygon hexKH = Hex.hex(KH, VER);
        Polygon hexKI = Hex.hex(KI, VER);
        Polygon hexKJ = Hex.hex(KJ, VER);
        Polygon hexJD = Hex.hex(JD, HOR, centreTransform(KJ, VER));
        Polygon hexJARot = Hex.hex(JA, HOR, centreTransform(KJ, UR_V));
        Polygon hexJM = Hex.hex(JM, HOR, centreTransform(KJ, VER));
        Polygon hexJM_2 = Hex.hex(JM, VER, centreTransform(KJ, RIGHT));
        Polygon hexJM_3 = Hex.hex(JM, VER, centreTransform(KJ, DR_H));
        Polygon hexJM_4 = Hex.hex(JM, HOR, centreTransform(KJ, UR_V));

        Polygon hexRot1 = Hex.hex(1, HOR, centreTransform(FE, VER));
        Polygon hexRot2 = Hex.hex(1, VER, centreTransform(FE, HOR));
        Polygon hexRot3 = Hex.hex(1, VER, centreTransform(KF, HOR));


        List<String> equations = asList(
                "KB = 0.5",
                "KC = KB/h",
                "KD = h",
                "AC = 1 - KC",
                "AD = 1 - h",
                "AE = AC * h",
                "AF = AE * h",
                "FE = AE * 0.5",
                "AG = AC * 0.5",
                "KH = FE",
                "KI = KH / h",
                "KJ = 2 * KH",
                "JA = 1 - KJ",
                "JL = JA",
                "IJ = KJ-KI",
                "JM = IJ*h"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_08_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainHor, Hex.DIAGONALS),
                        Pair.of(hexAE, Hex.PERIMETER)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.INNER_TRIANGLES)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(mainHor, Hex.INNER_TRIANGLES),
                        Pair.of(mainHor, Hex.PERIMETER)
                ), blue)
                .addAllVertexesAsImportantPoints(
        asList(
//                        hexJARot,
//                        hexKI,
//                        hexAG,
//                        hexJM,
//                        hexJM_2,
//                        hexJM_3,
//                                hexJM_4
                )
                )
                .addMixedLinesInstructionsList(asList(
                        asList(
                                Pair.of(hexRot1, THREE),
                                Pair.of(hexRot1, SIX)
                        ),
                        asList(
                                Pair.of(hexRot2, TWO),
                                Pair.of(hexRot2, FIVE)
                        ),
                        asList(
                                Pair.of(hexRot3, TWO),
                                Pair.of(hexRot3, FIVE)
                        ),
                        asList(
                                Pair.of(main, SIX),
                                Pair.of(mainHor, ONE),
                                Pair.of(main, ONE)
                        ),
                        asList(
                                Pair.of(mainHorReg, SIX),
                                Pair.of(mainReg, ONE),
                                Pair.of(mainHorReg, ONE)
                        )
                ), gray)
                .addImportantPoints(asList(
                        Triple.of(main, DR_V.getVertex(), "A"),
                        Triple.of(hexKB, DR_V.getVertex(), "B"),
                        Triple.of(hexKC, DR_V.getVertex(), "C"),
                        Triple.of(hexKD, DR_V.getVertex(), "D"),
                        Triple.of(hexAE, UL_H.getVertex(), "E"),
                        Triple.of(hexAF, UL_V.getVertex(), "F"),
                        Triple.of(hexKF, RIGHT.getVertex(), "F"),
                        Triple.of(hexAG, UL_V.getVertex(), "G"),
                        Triple.of(hexKH, DR_V.getVertex(), "H"),
                        Triple.of(hexKI, DOWN.getVertex(), "I"),
                        Triple.of(hexKJ, DR_V.getVertex(), "J"),
                        Triple.of(hexJD, DR_H.getVertex(), "L"),
                        Triple.of(hexJM, UP.getVertex(), "M")
                ))

                ;

    }

}