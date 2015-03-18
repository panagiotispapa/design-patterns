package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;

public class Tile9New {

    private static double RATIO_I = 0.5 * Math.tan(Math.PI / 12);
    private static double RATIO_J = 1 - RATIO_I;
    private static double RATIO_L = 1 - 2 * RATIO_I;
//    private static double RATIO_CD = £H.andThen($1).apply(RATIO_KA);

    private static double KA1 = 1;
    private static double KB1 = 1;
    private static double KC1 = RATIO_J;
    private static double KD1 = KC1;
    private static double KB2 = RATIO_L;
    private static double KA2 = KB2;
    private static double KB3 = RATIO_L * RATIO_L;
    private static double KA3 = KB3;

    private static double DB = 1 - KB2;
    private static double AE = DB;

    private static double BF = 1 - KB3;
    private static double AH = BF;

    private static double B2C2 = RATIO_I * KB2;
    private static double KC2 = KB2 - B2C2;
    private static double KD2 = KC2;
    private static double B2B3 = KB2 - KB3;
    private static double C2B2 = 0.5 * B2B3;
    private static double C2E = C2B2;
    private static double D2F = C2E;
    private static double KG = C2E;
    private static double KH1 = £H.apply(KG);
    private static double KJ1 = 2 * KG;
    private static double H1J1 = KJ1 - KH1;
    private static double I1J1 = $H.apply(H1J1);
    private static double KH2 = KH1;
    private static double KJ2 = KJ1;
    private static double H2J2 = H1J1;
    private static double I2J2 = I1J1;
    private static double A1M = C2E;
    private static double A1N = A1M;
    private static double KN = 1 - A1N;
    private static double KP = $H.apply(KN);

    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon hexA1M = Hex.hex(A1M, HOR, centreTransform(1, VER));
        Polygon hexA1N = hexA1M.getMirror();

        return new PayloadSimple("hex_tile_04",
                asList(
                        asList(
                                Pair.of(main, ONE),
                                Pair.of(main, TWO)
                        ),
                        asList(
                                Pair.of(hexA1N, THREE),
                                Pair.of(hexA1N, FOUR),
                                Pair.of(hexA1N, FIVE)
                        )

                ), Hex.ALL_VERTEX_INDEXES
        );
    }

    public static DesignHelper getDesignHelper() {

        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Hex.hex(1, VER);
        Polygon mainHor = Hex.hex(1, HOR);
        Polygon hexKC1 = Hex.hex(KC1, HOR);
        Polygon hexKD1 = Hex.hex(KD1, VER);
        Polygon hexKB2 = Hex.hex(KB2, HOR);
        Polygon hexKA2 = hexKB2.getMirror();
        Polygon hexKB3 = Hex.hex(KB3, HOR);
        Polygon hexKA3 = hexKB3.getMirror();

        Polygon hexBD = Hex.hex(DB, VER, centreTransform(1, HOR));
        Polygon hexBD_Hor = hexBD.getMirror();

        Polygon hexAE = Hex.hex(AE, VER, centreTransform(1, VER));
        Polygon hexAE_Hor = hexAE.getMirror();

        Polygon hexBF = Hex.hex(BF, VER, centreTransform(1, HOR));
        Polygon hexBF_Hor = hexBF.getMirror();

        Polygon hexAH = Hex.hex(AH, VER, centreTransform(1, VER));
        Polygon hexAH_Hor = hexAH.getMirror();

        Polygon hexKC2 = Hex.hex(KC2, HOR);
        Polygon hexKD2 = Hex.hex(KD2, VER);

        Polygon hexC2E = Hex.hex(C2E, VER, centreTransform(KC2, HOR));
        Polygon hexD2F = Hex.hex(D2F, HOR, centreTransform(KD2, VER));
        Polygon hexInnerLine = Hex.hex(1, HOR, centreTransform(C2E, VER));
        Polygon hexInnerLine2 = Hex.hex(1, VER, centreTransform(C2E, HOR));

        Polygon hexKG = Hex.hex(KG, VER);
        Polygon hexKH1 = Hex.hex(KH1, HOR);
        Polygon hexKJ1 = Hex.hex(KJ1, HOR);
        Polygon hexI1J1 = Hex.hex(I1J1, VER, centreTransform(KJ1, HOR));

        Polygon hexKH2 = Hex.hex(KH2, VER);
        Polygon hexKJ2 = Hex.hex(KJ2, VER);
        Polygon hexI2J2 = Hex.hex(I2J2, HOR, centreTransform(KJ2, VER));
        Polygon hexA1M = Hex.hex(A1M, HOR, centreTransform(1, VER));
        Polygon hexA1N = hexA1M.getMirror();
        Polygon hexKN = Hex.hex(KN, VER);
        Polygon hexKP = Hex.hex(KP, HOR);

        List<String> equations = asList(
                "i = 0.5 * tan(15)",
                "j = 1 - i",
                "l = 1 - 2 * i",
                "KA = KB = 1",
                "BC = j",
                "KB2 = l",
                "KE = l"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, ONE, "A1"),
                        Triple.of(mainHor, ONE, "B1"),
                        Triple.of(hexKC1, ONE, "C1"),
                        Triple.of(hexKD1, ONE, "D1"),
                        Triple.of(hexKB2, ONE, "B2"),
                        Triple.of(hexKA2, ONE, "A2"),
                        Triple.of(hexKB3, ONE, "B3"),
                        Triple.of(hexKA3, ONE, "A3"),
                        Triple.of(hexKC2, ONE, "C2"),
                        Triple.of(hexKD2, ONE, "D2"),
                        Triple.of(hexC2E, FIVE, "E"),
                        Triple.of(hexD2F, SIX, "F"),
                        Triple.of(hexKG, FOUR, "G"),
                        Triple.of(hexKH1, ONE, "H1"),
                        Triple.of(hexKH2, ONE, "H2"),
                        Triple.of(hexKJ1, ONE, "J1"),
                        Triple.of(hexKJ2, ONE, "J2"),
                        Triple.of(hexI1J1, FOUR, "I1"),
                        Triple.of(hexI2J2, FIVE, "I2"),
                        Triple.of(hexA1M, SIX, "M"),
                        Triple.of(hexKN, ONE, "N"),
                        Triple.of(hexKP, ONE, "P")
                ))
                .addLinesInstructions(asList(
                        Pair.of(hexBD, PERIMETER),
                        Pair.of(hexBD_Hor, PERIMETER),
//                        Pair.of(hexAE, PERIMETER),
//                        Pair.of(hexAE_Hor, PERIMETER),
//                        Pair.of(hexInnerLine2, DIAGONALS),
                        Pair.of(hexA1M, PERIMETER),
                        Pair.of(hexA1N, PERIMETER),
                        Pair.of(hexC2E, PERIMETER),
                        Pair.of(hexD2F, PERIMETER),

                        Pair.of(main, DIAGONALS),
                        Pair.of(mainHor, DIAGONALS)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(hexKN, PERIMETER)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(mainHor, PERIMETER),
                        Pair.of(hexKB2, PERIMETER),
                        Pair.of(hexKA2, PERIMETER),
                        Pair.of(hexKB3, PERIMETER),
                        Pair.of(hexKA3, PERIMETER)
                ), blue)
                .addMixedLinesInstructions(asList(
                        Pair.of(main, SIX),
                        Pair.of(mainHor, ONE),
                        Pair.of(main, ONE)

                ), gray)

                .addMixedLinesInstructions(asList(
                        Pair.of(main, THREE),
                        Pair.of(mainHor, FIVE),
                        Pair.of(main, SIX)

                ), gray)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexKA2, THREE),
                        Pair.of(hexKB2, FIVE),
                        Pair.of(hexKA2, SIX)

                ), gray)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexKA3, THREE),
                        Pair.of(hexKB3, FIVE),
                        Pair.of(hexKA3, SIX)

                ), gray)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexBD, TWO),
                        Pair.of(hexBD_Hor, FOUR),
                        Pair.of(hexBD, FIVE)

                ), green)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexBF, TWO),
                        Pair.of(hexBF_Hor, FOUR),
                        Pair.of(hexBF, FIVE)

                ), green)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexAE_Hor, THREE),
                        Pair.of(hexAE, FOUR),
                        Pair.of(hexAE_Hor, SIX)

                ), green)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexAH_Hor, THREE),
                        Pair.of(hexAH, FOUR),
                        Pair.of(hexAH_Hor, SIX)
                ), green)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexInnerLine, THREE),
                        Pair.of(hexInnerLine, SIX)
                ), gray)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexInnerLine2, TWO),
                        Pair.of(hexInnerLine2, FIVE)
                ), gray)
                .addCircleWithRadius(asList(
                        Pair.of(main, A1M)
                ), blue)
                .addAllVertexesAsImportantPoints(asList(
                        main,
                        mainHor
//                        hexBD,
//                        hexBD_Hor
//                        hexC2E
//                        hexD2F
//                        hexInnerLine2
//                        hexI2J2
//                        hexA1N
                ))
                ;

    }

}