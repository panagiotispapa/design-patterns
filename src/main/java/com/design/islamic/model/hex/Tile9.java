package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.Ratios.*;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;

public class Tile9 {

    private static double RATIO_i = 0.5 * Math.tan(Math.PI / 12);
    private static double RATIO_j = 1 - RATIO_i;
    private static double RATIO_k = 2 * RATIO_i;
    private static double RATIO_l = 1 - RATIO_k;
    private static double RATIO_m = 0.5 * (RATIO_l - RATIO_l * RATIO_l);
    private static double RATIO_n = HEX.£H().apply(RATIO_m);
    private static double RATIO_s = RECT.£H().andThen(HEX.£H().andThen(DOD.$H().andThen(HEX.£H()))).apply(RATIO_m);

    private static double KA1 = 1;
    private static double KB1 = 1;
    private static double KC1 = RATIO_j;
    private static double KD1 = KC1;
    private static double KB2 = RATIO_l;
    private static double KA2 = KB2;
    private static double KB3 = RATIO_l * RATIO_l;
    private static double KA3 = KB3;

    private static double B1B2 = 1 - KB2;
    private static double A1A2 = B1B2;

    private static double B1B3 = 1 - KB3;
    private static double A1A3 = B1B3;

    private static double B2C2 = RATIO_i * KB2;
    private static double KC2 = KB2 - B2C2;
    private static double KD2 = KC2;
    private static double B2B3 = KB2 - KB3;
    private static double C2B2 = 0.5 * B2B3;
    private static double C2E = C2B2;

    private static double D2F = C2E;
    private static double KG1 = RATIO_m;
    private static double KG2 = RATIO_n;
    private static double KG3 = 2 * KG1;
    private static double KG4 = $H.apply(KG3 - KG2);

    private static double A1M = C2E;
    private static double A1N = A1M;
    private static double KN = 1 - A1N;
    private static double KP = $H.apply(KN);
    private static double B2P = KP - KB2;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon hexA1M = Hex.hex(A1M, HOR, centreTransform(1, VER));
        Polygon hexA1N = hexA1M.getMirror();

        Polygon hexPB2 = Hex.hex(B2P, VER, centreTransform(KP, HOR));
        Polygon hexPB2_Rot = Hex.hex(B2P, VER, Polygon.centreTransform(KP, Hex.Vertex.TWO, HOR));

        Polygon hexA2Q1 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA2, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.SIX, VER)));
        Polygon hexA2Q2 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA2, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.TWO, VER)));

        Polygon hexA3R1 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.SIX, VER)));
        Polygon hexA3R2 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.TWO, VER)));
        Polygon hexA3R3 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.THREE, VER)));
        Polygon hexA3R4 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.FIVE, VER)));

        Polygon hexC1H = Hex.hex(RATIO_i, VER, centreTransform(KC1, HOR));
        Polygon hexC1H_r = Hex.hex(RATIO_i, VER, Polygon.centreTransform(KC1, Hex.Vertex.TWO, HOR));

        Polygon hexB3I1 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.SIX, HOR)));
        Polygon hexB3I2 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.TWO, HOR)));
        Polygon hexB3I3 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.THREE, HOR)));
        Polygon hexB3I4 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.FIVE, HOR)));

        Polygon hexC1J3 = Hex.hex(RATIO_i - RATIO_m, HOR, centreTransform(KC1, HOR).andThen(Hex.centreTransform(RATIO_m, Hex.Vertex.FIVE, VER)));
        Polygon hexC1J4 = Hex.hex(RATIO_i - RATIO_m, HOR, centreTransform(KC1, HOR).andThen(Hex.centreTransform(RATIO_m, Hex.Vertex.TWO, VER)));

        Polygon hexKG4 = Hex.hex(KG4, VER, Polygon.centreTransform(KG3, Hex.Vertex.SIX, HOR));
        Polygon hexKG5 = Hex.hex(KG4, VER, Polygon.centreTransform(KG3, Hex.Vertex.TWO, HOR));

        Polygon hexB3S1 = Hex.hex(RATIO_s * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_s, Hex.Vertex.SIX, HOR)));
        Polygon hexB3S2 = Hex.hex(RATIO_s * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_s, Hex.Vertex.TWO, HOR)));

        return new PayloadSimple.Builder("hex_tile_09"
                , Hex.ALL_VERTEX_INDEXES
        )
                .withLines(
                        asList(
                                asList(
                                        Pair.of(main, ONE),
                                        Pair.of(main, TWO)
                                ),
                                asList(
                                        Pair.of(hexA1N, THREE),
                                        Pair.of(hexA1N, FOUR),
                                        Pair.of(hexA1N, FIVE)
                                ),
                                asList(
                                        Pair.of(Hex.hex(KA2, VER), SIX),
                                        Pair.of(hexB3S1, FOUR),
                                        Pair.of(hexPB2, TWO)
                                ),
                                asList(
                                        Pair.of(Hex.hex(KA2, VER), ONE),
                                        Pair.of(hexB3S2, FOUR),
                                        Pair.of(hexPB2, FIVE)
                                ),
                                asList(
                                        Pair.of(hexPB2, TWO),
                                        Pair.of(hexA1N, FOUR)
                                ),
                                asList(

                                        Pair.of(hexPB2_Rot, SIX),
                                        Pair.of(hexA1N, FOUR)
                                ),
                                asList(
                                        Pair.of(hexC1H, TWO),
                                        Pair.of(hexA2Q1, FOUR),
                                        Pair.of(hexA3R1, FOUR),
                                        Pair.of(hexA3R3, ONE),
                                        Pair.of(hexKG5, FOUR)

                                ),
                                asList(
                                        Pair.of(hexC1H_r, SIX),
                                        Pair.of(hexA2Q2, FOUR),
                                        Pair.of(hexA3R2, FOUR),
                                        Pair.of(hexA3R4, ONE),
                                        Pair.of(Hex.hex(KG4, VER, Polygon.centreTransform(KG3, Hex.Vertex.ONE, HOR)), FOUR)
                                ),
                                asList(
                                        Pair.of(hexKG5, FIVE),
                                        Pair.of(hexB3I3, ONE),
                                        Pair.of(hexB3I1, FOUR),
                                        Pair.of(hexC1J3, FOUR),
                                        Pair.of(hexC1H, FIVE)

                                ),
                                asList(
                                        Pair.of(hexKG4, TWO),
                                        Pair.of(hexB3I4, ONE),
                                        Pair.of(hexB3I2, FOUR),
                                        Pair.of(hexC1J4, FOUR),
                                        Pair.of(hexC1H, TWO)

                                )

                        )
                )
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper1() {

        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Hex.hex(1, VER);
        Polygon mainHor = Hex.hex(1, HOR);
        Polygon hexKC = main.getRegistered();
        Polygon hexKD = Hex.hex(RATIO_l, HOR);
        Polygon hexAD = Hex.hex(RATIO_k, HOR, centreTransform(1, HOR));
        Polygon hexAD_VER = hexAD.getMirror();

        List<String> equations = asList(
                "AC = 0.5 * tan(15)",
                "AD = 2 * AC",
                "AD = AE"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09_design1")
                .addEquations(equations)
                .addLinesInstructions(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(mainHor, PERIMETER),
                        Pair.of(hexAD, PERIMETER),
                        Pair.of(hexAD_VER, PERIMETER)
                ), gray)
                .addMixedLinesInstructions(asList(
                        Pair.of(main, THREE),
                        Pair.of(mainHor, FIVE),
                        Pair.of(main, SIX)

                ), green)
                .addImportantPoints(asList(
                        Triple.of(mainHor, ONE, "A"),
                        Triple.of(main, SIX, "B"),
                        Triple.of(hexKC, ONE, "C"),
                        Triple.of(hexKD, ONE, "D"),
                        Triple.of(hexAD, FIVE, "E")
                ))
                .withFontSize(14)

                ;
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper3() {

        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Hex.hex(1, VER);
        Polygon mainHor = Hex.hex(1, HOR);

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09_design3")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .withFontSize(10)

                ;
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2() {

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

        Polygon hexB1B2 = Hex.hex(B1B2, VER, centreTransform(1, HOR));
        Polygon hexB1B2_Hor = hexB1B2.getMirror();

        Polygon hexA1A2 = Hex.hex(A1A2, VER, centreTransform(1, VER));
        Polygon hexA1A2_Hor = hexA1A2.getMirror();

        Polygon hexB1B3 = Hex.hex(B1B3, VER, centreTransform(1, HOR));
        Polygon hexB1B3_Hor = hexB1B3.getMirror();

        Polygon hexA1A3 = Hex.hex(A1A3, VER, centreTransform(1, VER));
        Polygon hexA1A3_Hor = hexA1A3.getMirror();

        Polygon hexKC2 = Hex.hex(KC2, HOR);
        Polygon hexKD2 = Hex.hex(KD2, VER);

        Polygon hexC2E = Hex.hex(C2E, VER, centreTransform(KC2, HOR));
        Polygon hexD2F = Hex.hex(D2F, HOR, centreTransform(KD2, VER));
        Polygon hexInnerLine = Hex.hex(1, HOR, centreTransform(C2E, VER));
        Polygon hexInnerLine2 = Hex.hex(1, VER, centreTransform(C2E, HOR));

        Polygon hexKG1 = Hex.hex(RATIO_m, VER);
        Polygon hexKG2 = Hex.hex(RATIO_n, HOR);
        Polygon hexKG3 = Hex.hex(KG3, HOR);
        Polygon hexKG4 = Hex.hex(KG4, VER, Polygon.centreTransform(KG3, Hex.Vertex.SIX, HOR));
        Polygon hexKG5 = Hex.hex(KG4, VER, Polygon.centreTransform(KG3, Hex.Vertex.TWO, HOR));

        Polygon hexA1M = Hex.hex(A1M, HOR, centreTransform(1, VER));
        Polygon hexA1N = hexA1M.getMirror();
        Polygon hexKN = Hex.hex(KN, VER);
        Polygon hexKP = Hex.hex(KP, HOR);

        Polygon hexPB2 = Hex.hex(B2P, VER, centreTransform(KP, HOR));
        Polygon hexA2Q = Hex.hex(RATIO_n, VER, centreTransform(KA2, VER));
        Polygon hexA2Q_m = hexA2Q.getMirror();
        Polygon hexA2Q1 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA2, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.SIX, VER)));
        Polygon hexA2Q2 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA2, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.TWO, VER)));

        Polygon hexA3R = Hex.hex(RATIO_n, VER, centreTransform(KA3, VER));
        Polygon hexA3R1 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.SIX, VER)));
        Polygon hexA3R2 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.TWO, VER)));
        Polygon hexA3R3 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.THREE, VER)));
        Polygon hexA3R4 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, VER).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.FIVE, VER)));

        Polygon hexC1H = Hex.hex(RATIO_i, VER, centreTransform(KC1, HOR));
        Polygon hexC1J = Hex.hex(RATIO_m, VER, centreTransform(KC1, HOR));
        Polygon hexC1J3 = Hex.hex(RATIO_i - RATIO_m, HOR, centreTransform(KC1, HOR).andThen(Hex.centreTransform(RATIO_m, Hex.Vertex.FIVE, VER)));
        Polygon hexC1J4 = Hex.hex(RATIO_i - RATIO_m, HOR, centreTransform(KC1, HOR).andThen(Hex.centreTransform(RATIO_m, Hex.Vertex.TWO, VER)));

        Polygon hexB3I = Hex.hex(RATIO_n, HOR, centreTransform(KB3, HOR));
        Polygon hexB3I1 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.SIX, HOR)));
        Polygon hexB3I2 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.TWO, HOR)));
        Polygon hexB3I3 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.THREE, HOR)));
        Polygon hexB3I4 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_n, Hex.Vertex.FIVE, HOR)));

        Polygon hexB3S = Hex.hex(RATIO_s, HOR, centreTransform(KB3, HOR));
        Polygon hexB3S1 = Hex.hex(RATIO_s * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_s, Hex.Vertex.SIX, HOR)));
        Polygon hexB3S2 = Hex.hex(RATIO_s * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(Polygon.centreTransform(RATIO_s, Hex.Vertex.TWO, HOR)));

//        Polygon hexA2Q = Hex.hex(A2Q, VER, centreTransform(KA2, VER));
//        Polygon hexA2Q_Hor = hexA2Q.getMirror();
//        Polygon hexQR = Hex.hex(QR, VER, centreTransform(KA2, VER).andThen(Polygon.centreTransform(A2Q, Hex.Vertex.TWO, VER)));

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09_design2")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
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
                        Triple.of(hexKG1, FOUR, "G1"),
                        Triple.of(hexKG2, ONE, "G2"),
                        Triple.of(hexKG3, ONE, "G3"),
                        Triple.of(hexKG4, TWO, "G4"),
                        Triple.of(hexKG5, FIVE, "G5"),
                        Triple.of(hexKG5, FOUR, "G6"),

                        Triple.of(hexA1M, SIX, "M"),
                        Triple.of(hexKN, ONE, "N"),
                        Triple.of(hexKP, ONE, "P"),
                        Triple.of(hexA2Q1, FOUR, "Q1"),
                        Triple.of(hexA2Q2, FOUR, "Q1"),

                        Triple.of(hexA3R1, FOUR, "R1"),
                        Triple.of(hexA3R2, FOUR, "R2"),
                        Triple.of(hexA3R3, ONE, "R3"),
                        Triple.of(hexA3R4, ONE, "R4"),

                        Triple.of(hexC1H, FIVE, "H1"),
                        Triple.of(hexC1H, TWO, "H2"),

                        Triple.of(hexB3I1, FOUR, "I1"),
                        Triple.of(hexB3I2, FOUR, "I2"),
                        Triple.of(hexB3I3, ONE, "I3"),
                        Triple.of(hexB3I4, ONE, "I4"),

                        Triple.of(hexB3S1, FOUR, "S1"),
                        Triple.of(hexB3S2, FOUR, "S2"),
                        Triple.of(hexC1J, FIVE, "J1"),
                        Triple.of(hexC1J, TWO, "J2"),
                        Triple.of(hexC1J3, FOUR, "J3"),
                        Triple.of(hexC1J4, FOUR, "J4")
//                        Triple.of(hexB3I, TWO, "I2")
                ))
                .addLinesInstructions(asList(
                        Pair.of(hexB1B2, PERIMETER),
                        Pair.of(hexB1B2_Hor, PERIMETER),
//                        Pair.of(hexA1A2, PERIMETER),
//                        Pair.of(hexA1A2_Hor, PERIMETER),
//                        Pair.of(hexInnerLine2, DIAGONALS),
                        Pair.of(hexA1M, PERIMETER),
                        Pair.of(hexA1N, PERIMETER),
                        Pair.of(hexC2E, PERIMETER),
                        Pair.of(hexD2F, PERIMETER),

                        Pair.of(hexA2Q, PERIMETER),
                        Pair.of(hexA2Q_m, PERIMETER),
                        Pair.of(hexA2Q1, PERIMETER),
                        Pair.of(hexA2Q2, PERIMETER),

                        Pair.of(hexA3R, PERIMETER),
                        Pair.of(hexA3R1, PERIMETER),
                        Pair.of(hexA3R2, PERIMETER),
                        Pair.of(hexA3R3, PERIMETER),
                        Pair.of(hexA3R4, PERIMETER),

                        Pair.of(hexB3I, PERIMETER),
                        Pair.of(hexB3I1, PERIMETER),
                        Pair.of(hexB3I2, PERIMETER),
                        Pair.of(hexB3I3, PERIMETER),
                        Pair.of(hexB3I4, PERIMETER),

                        Pair.of(hexC1H, PERIMETER),
                        Pair.of(hexC1J, PERIMETER),
                        Pair.of(hexC1J3, PERIMETER),
                        Pair.of(hexC1J4, PERIMETER),

                        Pair.of(hexB3S, PERIMETER),
                        Pair.of(hexB3S1, PERIMETER),
                        Pair.of(hexB3S2, PERIMETER),

                        Pair.of(hexKG3, PERIMETER),
                        Pair.of(hexKG4, PERIMETER),
                        Pair.of(hexKG5, PERIMETER),

                        Pair.of(main, DIAGONALS),
                        Pair.of(mainHor, DIAGONALS)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(hexKN, PERIMETER),
                        Pair.of(hexPB2, PERIMETER)
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
                        Pair.of(hexB1B2, TWO),
                        Pair.of(hexB1B2_Hor, FOUR),
                        Pair.of(hexB1B2, FIVE)

                ), green)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexB1B3, TWO),
                        Pair.of(hexB1B3_Hor, FOUR),
                        Pair.of(hexB1B3, FIVE)

                ), green)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexA1A2_Hor, THREE),
                        Pair.of(hexA1A2, FOUR),
                        Pair.of(hexA1A2_Hor, SIX)

                ), green)
                .addMixedLinesInstructions(asList(
                        Pair.of(hexA1A3_Hor, THREE),
                        Pair.of(hexA1A3, FOUR),
                        Pair.of(hexA1A3_Hor, SIX)
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
                .withFontSize(10)
                ;

    }

}