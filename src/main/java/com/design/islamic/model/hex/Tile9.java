package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Mappings;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.Ratios.*;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;

public class Tile9 {

    private static double RATIO_i = 0.5 * Math.tan(Math.PI / 12);
    private static double RATIO_j = 1 - RATIO_i;
    private static double RATIO_k = 2 * RATIO_i;
    private static double RATIO_l = 1 - RATIO_k;
    private static double RATIO_m = 0.5 * (RATIO_l - RATIO_l * RATIO_l);
    private static double RATIO_n = RATIO_m / H;
    private static double RATIO_s = Mappings.chain(RECT.£H(), HEX.£H(), DOD.$H(), HEX.£H()).apply(RATIO_m);

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
    private static double KG4 = (KG3 - KG2) * H;

    private static double A1M = C2E;
    private static double A1N = A1M;
    private static double KN = 1 - A1N;
    private static double KP = KN * H;
    private static double B2P = KP - KB2;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon hexA1M = Hex.hex(A1M, HOR, centreTransform(1, DR_V));
        Polygon hexA1N = hexA1M.getMirror();

        Polygon hexPB2 = Hex.hex(B2P, VER, centreTransform(KP, RIGHT));
        Polygon hexPB2_Rot = Hex.hex(B2P, VER, centreTransform(KP, DR_H));

        Polygon hexA2Q1 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA2, DR_V).andThen(centreTransform(RATIO_n, UR_V)));
        Polygon hexA2Q2 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA2, DR_V).andThen(centreTransform(RATIO_n, DOWN)));

        Polygon hexA3R1 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, DR_V).andThen(centreTransform(RATIO_n, UR_V)));
        Polygon hexA3R2 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, DR_V).andThen(centreTransform(RATIO_n, DOWN)));
        Polygon hexA3R3 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, DR_V).andThen(centreTransform(RATIO_n, DL_V)));
        Polygon hexA3R4 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, DR_V).andThen(centreTransform(RATIO_n, UP)));

        Polygon hexC1H = Hex.hex(RATIO_i, VER, centreTransform(KC1, RIGHT));
        Polygon hexC1H_r = Hex.hex(RATIO_i, VER, centreTransform(KC1, DR_H));

        Polygon hexB3I1 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, RIGHT).andThen(centreTransform(RATIO_n, UR_H)));
        Polygon hexB3I2 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, RIGHT).andThen(centreTransform(RATIO_n, DR_H)));
        Polygon hexB3I3 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, RIGHT).andThen(centreTransform(RATIO_n, DL_H)));
        Polygon hexB3I4 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, RIGHT).andThen(centreTransform(RATIO_n, UL_H)));

        Polygon hexC1J3 = Hex.hex(RATIO_i - RATIO_m, HOR, centreTransform(KC1, RIGHT).andThen(centreTransform(RATIO_m, UP)));
        Polygon hexC1J4 = Hex.hex(RATIO_i - RATIO_m, HOR, centreTransform(KC1, RIGHT).andThen(centreTransform(RATIO_m, DOWN)));

        Polygon hexKG4 = Hex.hex(KG4, VER, centreTransform(KG3, UR_H));
        Polygon hexKG5 = Hex.hex(KG4, VER, centreTransform(KG3, DR_H));

        Polygon hexB3S1 = Hex.hex(RATIO_s * RATIO_k, HOR, centreTransform(KB3, RIGHT).andThen(centreTransform(RATIO_s, UR_H)));
        Polygon hexB3S2 = Hex.hex(RATIO_s * RATIO_k, HOR, centreTransform(KB3, RIGHT).andThen(centreTransform(RATIO_s, DR_H)));
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_09"
                , Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(() ->
                                asList(
                                        () -> asList(
                                                instruction(main, DR_V),
                                                instruction(main, DOWN)
                                        ),
                                        () -> asList(
                                                instruction(hexA1N, DL_H),
                                                instruction(hexA1N, LEFT),
                                                instruction(hexA1N, UL_H)
                                        ),
                                        () -> asList(
                                                instruction(KA2, UR_V),
                                                instruction(hexB3S1, LEFT),
                                                instruction(hexPB2, DOWN)
                                        ),
                                        () -> asList(
                                                instruction(KA2, DR_V),
                                                instruction(hexB3S2, LEFT),
                                                instruction(hexPB2, UP)
                                        ),
                                        () -> asList(
                                                instruction(hexPB2, DOWN),
                                                instruction(hexA1N, UL_V)
                                        ),
                                        () -> asList(
                                                instruction(hexPB2_Rot, UR_V),
                                                instruction(hexA1N, UL_V)
                                        ),
                                        () -> asList(
                                                instruction(hexC1H, DOWN),
                                                instruction(hexA2Q1, UL_V),
                                                instruction(hexA3R1, UL_V),
                                                instruction(hexA3R3, DR_V),
                                                instruction(hexKG5, UL_V)

                                        ),
                                        () -> asList(
                                                instruction(hexC1H_r, UR_V),
                                                instruction(hexA2Q2, UL_V),
                                                instruction(hexA3R2, UL_V),
                                                instruction(hexA3R4, DR_V),
                                                instruction(KG4, centreTransform(KG3, RIGHT), UL_V)
                                        ),
                                        () -> asList(
                                                instruction(hexKG5, UP),
                                                instruction(hexB3I3, RIGHT),
                                                instruction(hexB3I1, LEFT),
                                                instruction(hexC1J3, LEFT),
                                                instruction(hexC1H, UP)

                                        ),
                                        () -> asList(
                                                instruction(hexKG4, DOWN),
                                                instruction(hexB3I4, RIGHT),
                                                instruction(hexB3I2, LEFT),
                                                instruction(hexC1J4, LEFT),
                                                instruction(hexC1H, DOWN)
                                        )

                                ), whiteBold
                )
                .withSize(PayloadSimple.Size.MEDIUM)
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper1() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        Polygon main = Hex.hex(1, VER);
        Polygon mainHor = Hex.hex(1, HOR);
        Polygon hexKC = main.getRegistered();
        Polygon hexKD = Hex.hex(RATIO_l, HOR);
        Polygon hexAD = Hex.hex(RATIO_k, HOR, centreTransform(1, RIGHT));
        Polygon hexAD_VER = hexAD.getMirror();

        List<String> equations = asList(
                "AC = 0.5 * tan(15)",
                "AD = 2 * AC",
                "AD = AE"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09_design1")
                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(mainHor, PERIMETER),
                        Pair.of(hexAD, PERIMETER),
                        Pair.of(hexAD_VER, PERIMETER)
                ), gray)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(main, THREE),
                        () -> Pair.of(mainHor, FIVE),
                        () -> Pair.of(main, SIX)

                )), green)
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

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, VER);
        Polygon mainHor = Hex.hex(1, HOR);

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09_design3")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .withFontSize(10)

                ;
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        Polygon main = Hex.hex(1, VER);
        Polygon mainHor = Hex.hex(1, HOR);
        Polygon hexKC1 = Hex.hex(KC1, HOR);
        Polygon hexKD1 = Hex.hex(KD1, VER);
        Polygon hexKB2 = Hex.hex(KB2, HOR);
        Polygon hexKA2 = hexKB2.getMirror();
        Polygon hexKB3 = Hex.hex(KB3, HOR);
        Polygon hexKA3 = hexKB3.getMirror();

        Polygon hexB1B2 = Hex.hex(B1B2, VER, centreTransform(1, RIGHT));
        Polygon hexB1B2_Hor = hexB1B2.getMirror();

        Polygon hexA1A2 = Hex.hex(A1A2, VER, centreTransform(1, DR_V));
        Polygon hexA1A2_Hor = hexA1A2.getMirror();

        Polygon hexB1B3 = Hex.hex(B1B3, VER, centreTransform(1, RIGHT));
        Polygon hexB1B3_Hor = hexB1B3.getMirror();

        Polygon hexA1A3 = Hex.hex(A1A3, VER, centreTransform(1, DR_V));
        Polygon hexA1A3_Hor = hexA1A3.getMirror();

        Polygon hexKC2 = Hex.hex(KC2, HOR);
        Polygon hexKD2 = Hex.hex(KD2, VER);

        Polygon hexC2E = Hex.hex(C2E, VER, centreTransform(KC2, RIGHT));
        Polygon hexD2F = Hex.hex(D2F, HOR, centreTransform(KD2, DR_V));
        Polygon hexInnerLine = Hex.hex(1, HOR, centreTransform(C2E, DR_V));
        Polygon hexInnerLine2 = Hex.hex(1, VER, centreTransform(C2E, RIGHT));

        Polygon hexKG1 = Hex.hex(RATIO_m, VER);
        Polygon hexKG2 = Hex.hex(RATIO_n, HOR);
        Polygon hexKG3 = Hex.hex(KG3, HOR);
        Polygon hexKG4 = Hex.hex(KG4, VER, centreTransform(KG3, UR_H));
        Polygon hexKG5 = Hex.hex(KG4, VER, centreTransform(KG3, DR_H));

        Polygon hexA1M = Hex.hex(A1M, HOR, centreTransform(1, DR_V));
        Polygon hexA1N = hexA1M.getMirror();
        Polygon hexKN = Hex.hex(KN, VER);
        Polygon hexKP = Hex.hex(KP, HOR);

        Polygon hexPB2 = Hex.hex(B2P, VER, centreTransform(KP, RIGHT));
        Polygon hexA2Q = Hex.hex(RATIO_n, VER, centreTransform(KA2, DR_V));
        Polygon hexA2Q_m = hexA2Q.getMirror();
        Polygon hexA2Q1 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA2, DR_V).andThen(centreTransform(RATIO_n, UR_V)));
        Polygon hexA2Q2 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA2, DR_V).andThen(centreTransform(RATIO_n, DOWN)));

        Polygon hexA3R = Hex.hex(RATIO_n, VER, centreTransform(KA3, VER));
        Polygon hexA3R1 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, DR_V).andThen(centreTransform(RATIO_n, UR_V)));
        Polygon hexA3R2 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, DR_V).andThen(centreTransform(RATIO_n, DOWN)));
        Polygon hexA3R3 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, DR_V).andThen(centreTransform(RATIO_n, DL_V)));
        Polygon hexA3R4 = Hex.hex(RATIO_n * RATIO_k, VER, centreTransform(KA3, DR_V).andThen(centreTransform(RATIO_n, UP)));

        Polygon hexC1H = Hex.hex(RATIO_i, VER, centreTransform(KC1, HOR));
        Polygon hexC1J = Hex.hex(RATIO_m, VER, centreTransform(KC1, HOR));
        Polygon hexC1J3 = Hex.hex(RATIO_i - RATIO_m, HOR, centreTransform(KC1, HOR).andThen(Hex.centreTransform(RATIO_m, UP)));
        Polygon hexC1J4 = Hex.hex(RATIO_i - RATIO_m, HOR, centreTransform(KC1, HOR).andThen(Hex.centreTransform(RATIO_m, DOWN)));

        Polygon hexB3I = Hex.hex(RATIO_n, HOR, centreTransform(KB3, HOR));
        Polygon hexB3I1 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(centreTransform(RATIO_n, UR_H)));
        Polygon hexB3I2 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(centreTransform(RATIO_n, DR_H)));
        Polygon hexB3I3 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(centreTransform(RATIO_n, DL_H)));
        Polygon hexB3I4 = Hex.hex(RATIO_n * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(centreTransform(RATIO_n, UL_H)));

        Polygon hexB3S = Hex.hex(RATIO_s, HOR, centreTransform(KB3, HOR));
        Polygon hexB3S1 = Hex.hex(RATIO_s * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(centreTransform(RATIO_s, UR_H)));
        Polygon hexB3S2 = Hex.hex(RATIO_s * RATIO_k, HOR, centreTransform(KB3, HOR).andThen(centreTransform(RATIO_s, DR_H)));

//        Polygon hexA2Q = Hex.hex(A2Q, VER, centreTransform(KA2, VER));
//        Polygon hexA2Q_Hor = hexA2Q.getMirror();
//        Polygon hexQR = Hex.hex(QR, VER, centreTransform(KA2, VER).andThen(Polygon.centreTransform(A2Q, Hex.Vertex.TWO, VER)));

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09_design2")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addImportantPoints(asList(
                        Triple.of(main, DR_V.getVertex(), "A1"),
                        Triple.of(mainHor, RIGHT.getVertex(), "B1"),
                        Triple.of(hexKC1, RIGHT.getVertex(), "C1"),
                        Triple.of(hexKD1, DR_V.getVertex(), "D1"),
                        Triple.of(hexKB2, RIGHT.getVertex(), "B2"),
                        Triple.of(hexKA2, DR_V.getVertex(), "A2"),
                        Triple.of(hexKB3, RIGHT.getVertex(), "B3"),
                        Triple.of(hexKA3, DR_V.getVertex(), "A3"),
                        Triple.of(hexKC2, RIGHT.getVertex(), "C2"),
                        Triple.of(hexKD2, DR_V.getVertex(), "D2"),
                        Triple.of(hexC2E, UP.getVertex(), "E"),
                        Triple.of(hexD2F, UR_H.getVertex(), "F"),
                        Triple.of(hexKG1, UL_V.getVertex(), "G1"),
                        Triple.of(hexKG2, RIGHT.getVertex(), "G2"),
                        Triple.of(hexKG3, RIGHT.getVertex(), "G3"),
                        Triple.of(hexKG4, DOWN.getVertex(), "G4"),
                        Triple.of(hexKG5, UP.getVertex(), "G5"),
                        Triple.of(hexKG5, UL_V.getVertex(), "G6"),

                        Triple.of(hexA1M, UR_H.getVertex(), "M"),
                        Triple.of(hexKN, DR_V.getVertex(), "N"),
                        Triple.of(hexKP, RIGHT.getVertex(), "P"),
                        Triple.of(hexA2Q1, UL_V.getVertex(), "Q1"),
                        Triple.of(hexA2Q2, UL_V.getVertex(), "Q1"),

                        Triple.of(hexA3R1, UL_V.getVertex(), "R1"),
                        Triple.of(hexA3R2, UL_V.getVertex(), "R2"),
                        Triple.of(hexA3R3, DR_V.getVertex(), "R3"),
                        Triple.of(hexA3R4, DR_V.getVertex(), "R4"),

                        Triple.of(hexC1H, UP.getVertex(), "H1"),
                        Triple.of(hexC1H, DOWN.getVertex(), "H2"),

                        Triple.of(hexB3I1, LEFT.getVertex(), "I1"),
                        Triple.of(hexB3I2, LEFT.getVertex(), "I2"),
                        Triple.of(hexB3I3, RIGHT.getVertex(), "I3"),
                        Triple.of(hexB3I4, RIGHT.getVertex(), "I4"),

                        Triple.of(hexB3S1, LEFT.getVertex(), "S1"),
                        Triple.of(hexB3S2, LEFT.getVertex(), "S2"),
                        Triple.of(hexC1J, UP.getVertex(), "J1"),
                        Triple.of(hexC1J, DOWN.getVertex(), "J2"),
                        Triple.of(hexC1J3, LEFT.getVertex(), "J3"),
                        Triple.of(hexC1J4, LEFT.getVertex(), "J4")
//                        Triple.of(hexB3I, TWO, "I2")
                ))
                .addSinglePaths(asList(
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
                .addSinglePaths(asList(
                        Pair.of(hexKN, PERIMETER),
                        Pair.of(hexPB2, PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(mainHor, PERIMETER),
                        Pair.of(hexKB2, PERIMETER),
                        Pair.of(hexKA2, PERIMETER),
                        Pair.of(hexKB3, PERIMETER),
                        Pair.of(hexKA3, PERIMETER)
                ), blue)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(main, SIX),
                        () -> Pair.of(mainHor, ONE),
                        () -> Pair.of(main, ONE)
                )), gray)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(main, THREE),
                        () -> Pair.of(mainHor, FIVE),
                        () -> Pair.of(main, SIX)

                )), gray)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(hexKA2, THREE),
                        () -> Pair.of(hexKB2, FIVE),
                        () -> Pair.of(hexKA2, SIX)

                )), gray)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(hexKA3, THREE),
                        () -> Pair.of(hexKB3, FIVE),
                        () -> Pair.of(hexKA3, SIX)

                )), gray)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(hexB1B2, TWO),
                        () -> Pair.of(hexB1B2_Hor, FOUR),
                        () -> Pair.of(hexB1B2, FIVE)

                )), green)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(hexB1B3, TWO),
                        () -> Pair.of(hexB1B3_Hor, FOUR),
                        () -> Pair.of(hexB1B3, FIVE)

                )), green)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(hexA1A2_Hor, THREE),
                        () -> Pair.of(hexA1A2, FOUR),
                        () -> Pair.of(hexA1A2_Hor, SIX)

                )), green)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(hexA1A3_Hor, THREE),
                        () -> Pair.of(hexA1A3, FOUR),
                        () -> Pair.of(hexA1A3_Hor, SIX)
                )), green)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(hexInnerLine, THREE),
                        () -> Pair.of(hexInnerLine, SIX)
                )), gray)
                .addFullPaths(() -> asList(() -> asList(
                        () -> Pair.of(hexInnerLine2, TWO),
                        () -> Pair.of(hexInnerLine2, FIVE)
                )), gray)
                .addCircleWithRadius(asList(
                        Pair.of(main, A1M)
                ), blue)
                .withFontSize(10)
                ;

    }

}