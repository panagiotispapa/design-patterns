package com.design.arabic.model.hex;

import com.design.common.*;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.CircleInstruction.circleInstruction;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.Ratios.*;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile9 {

    private static double RATIO_i = 0.5 * Math.tan(Math.PI / 12);
    private static double RATIO_j = 1 - RATIO_i;
    private static double AD = 2 * RATIO_i;
    private static double KD = 1 - AD;
    private static double RATIO_m = 0.5 * (KD - KD * KD);
    private static double RATIO_n = RATIO_m / H;
    private static double RATIO_s = Mappings.chain(RECT.£H(), HEX.£H(), DOD.$H(), HEX.£H()).apply(RATIO_m);

    private static double KA = 1;
    private static double KC = H;
    private static double KB1 = 1;
    private static double KC1 = RATIO_j;
    private static double KD1 = KC1;
    private static double KB2 = KD;
    private static double KA2 = KB2;
    private static double KB3 = KD * KD;
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
    private static double G2G3 = KG3 - KG2;
    private static double G8G4 = G2G3 * H;
    private static double KG4 = G2G3 * H;

    private static double A1M = C2E;
    private static double A1N = A1M;
    private static double KN = 1 - A1N;
    private static double KP = KN * H;
    private static double B2P = KP - KB2;
    private static double J1J3 = RATIO_i - RATIO_m;
    private static double C1H1 = RATIO_i;
    private static double B3B4 = RATIO_s;
    private static double B3B5 = B3B4 * H;
    private static double G9Q1 = KA2 + KG1 / 4;


    public final static FinalPointTransition A = fpt(KA, RIGHT);
    public final static FinalPointTransition B = fpt(KA, UR_V);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KD, RIGHT);
    public final static FinalPointTransition E_1 = A.to(AD, UL_H);

    public final static FinalPointTransition A1 = fpt(KA, DR_V);
    public final static FinalPointTransition A5 = fpt(KA, UR_V);
    public final static FinalPointTransition B1 = fpt(KA, RIGHT);
    public final static FinalPointTransition C1 = fpt(H, RIGHT);
    public final static FinalPointTransition D1 = fpt(H, DR_V);
    public final static FinalPointTransition B2 = fpt(KB2, RIGHT);
    public final static FinalPointTransition B3 = fpt(KB3, RIGHT);
    public final static FinalPointTransition A2 = fpt(KA2, DR_V);
    public final static FinalPointTransition A3 = fpt(KA3, DR_V);
    public final static FinalPointTransition C2 = fpt(KC2, RIGHT);
    public final static FinalPointTransition D2 = fpt(KD2, DR_V);
    public final static FinalPointTransition E = C2.to(C2E, UP);
    public final static FinalPointTransition F = D2.to(D2F, UR_H);
    public final static FinalPointTransition G1 = fpt(KG1, UL_V);
    public final static FinalPointTransition G8 = fpt(KG3, UR_H);
    public final static FinalPointTransition G2 = fpt(KG2, RIGHT);
    public final static FinalPointTransition G3 = fpt(KG3, RIGHT);
    public final static FinalPointTransition G4 = G8.to(G8G4, DOWN);
    public final static FinalPointTransition G7 = fpt(KG3, DR_H);
    public final static FinalPointTransition G5 = G7.to(G8G4, UP);
    public final static FinalPointTransition G6 = G7.to(G8G4, UL_V);
    public final static FinalPointTransition G9 = fpt(KG1, UR_H);
    public final static FinalPointTransition G10 = G3.to(G8G4, UL_V);
    public final static FinalPointTransition G11 = G3.to(G8G4, DL_V);
    public final static FinalPointTransition M = A1.to(A1M, UR_H);
    public final static FinalPointTransition M1 = A1.to(A1M, UP);
    public final static FinalPointTransition M2 = A1.to(A1M, DL_V);
    public final static FinalPointTransition M3 = fpt(KA, UR_V, A1M, DOWN);
    public final static FinalPointTransition M4 = fpt(KA, UR_V, A1M, UL_V);
    public final static FinalPointTransition M5 = fpt(KA, DOWN, A1M, UL_V);
    public final static FinalPointTransition M6 = fpt(KA, DOWN, A1M, UR_V);
    public final static FinalPointTransition N = fpt(KN, DR_V);
    public final static FinalPointTransition P = fpt(KP, RIGHT);
    public final static FinalPointTransition Q1 = G9.to(G9Q1, DR_V);
    public final static FinalPointTransition Q2 = fpt(KG1, DL_H, G9Q1, DR_V);
    public final static FinalPointTransition Q3 = fpt(KG1, DR_H, G9Q1, UR_V);

    public final static FinalPointTransition I1 = fpt(KG1, UP, KB3 + KG1 / 4, RIGHT);
    public final static FinalPointTransition I4 = fpt(KG1, UP, KB3 - KG1 / 4, RIGHT);
    public final static FinalPointTransition I2 = fpt(KG1, DOWN, KB3 + KG1 / 4, RIGHT);
    public final static FinalPointTransition I3 = fpt(KG1, DOWN, KB3 - KG1 / 4, RIGHT);

    public final static FinalPointTransition R1 = G9.to(KA3 + KG1 / 4, DR_V);
    public final static FinalPointTransition R4 = G9.to(KA3 - KG1 / 4, DR_V);
    public final static FinalPointTransition R2 = fpt(KG1, DL_H,  KA3 + KG1 / 4, DR_V);
    public final static FinalPointTransition R3 = fpt(KG1, DL_H, KA3 - KG1 / 4, DR_V);
    public final static FinalPointTransition H1 = C1.to(C1H1, UP);
    public final static FinalPointTransition H2 = C1.to(C1H1, DOWN);
    public final static FinalPointTransition J1 = C1.to(KG1, UP);
    public final static FinalPointTransition J2 = C1.to(KG1, DOWN);
    public final static FinalPointTransition J3 = J1.to(J1J3, LEFT);
    public final static FinalPointTransition J4 = J2.to(J1J3, LEFT);
    public final static FinalPointTransition B4 = B3.to(B3B4, LEFT);
    public final static FinalPointTransition B5 = B3.to(B3B5, UP);

    public final static FinalPointTransition S1 = fpt(B3B5, UP, KB3 + B3B5 / 4, RIGHT);
    public final static FinalPointTransition S2 = fpt(B3B5, DOWN, KB3 + B3B5 / 4, RIGHT);
    public final static FinalPointTransition P1 = P.to(B2P, UP);
    public final static FinalPointTransition P2 = P.to(B2P, DOWN);

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_09"
                , Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        whiteBold,
                        getFullPath())
                .withSize(Payload.Size.MEDIUM)
                .build();
    }


    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(G4, I4, I2, J4, H2, Q1, R1, R3, G6),
                Line.line(G5, I3, I1, J3, H1, Q3),
                Line.line(G10, R4, R2, Q2),
                Line.line(M4, P1, S2, A2),
                Line.line(M2, P2, S1, fpt(KA2, UR_V)),
                Line.line(A1, A5)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper1() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Sequence<String> equations = sequence(
                "AC = 0.5 * tan(15)",
                "AD = 2 * AC",
                "AD = AE"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09_design1")
                .addEquations(equations)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        perimeter(1, HOR).apply(K),
                        perimeter(AD, VER).apply(A),
                        perimeter(AD, HOR).apply(A)
                )
                .addFullPaths(
                        green,
                        Line.line(fpt(KA, UR_V), fpt(KA, DR_H), fpt(KA, DL_V))
                )
                .addImportantVertexes(
                        ImportantVertex.of("A", A),
                        ImportantVertex.of("B", B),
                        ImportantVertex.of("C", C),
                        ImportantVertex.of("D", D),
                        ImportantVertex.of("E", E_1)
                )
                .withFontSize(14)

                ;
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper3() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09_design3")
                .addFullPaths(red, getFullPath())
                .withFontSize(10)

                ;
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09_design2")

                .addImportantVertexes(
                        ImportantVertex.of("A1", A1),
                        ImportantVertex.of("B1", B1),
                        ImportantVertex.of("C1", C1),
                        ImportantVertex.of("D1", D1),
                        ImportantVertex.of("B2", B2),
                        ImportantVertex.of("A2", A2),
                        ImportantVertex.of("B3", B3),
                        ImportantVertex.of("B4", B4),
                        ImportantVertex.of("B5", B5),
                        ImportantVertex.of("A3", A3),
                        ImportantVertex.of("E2", C2),
                        ImportantVertex.of("D2", D2),
                        ImportantVertex.of("E", E),
                        ImportantVertex.of("F", F),
                        ImportantVertex.of("G1", G1),
                        ImportantVertex.of("G2", G2),
                        ImportantVertex.of("G3", G3),
                        ImportantVertex.of("G4", G4),
                        ImportantVertex.of("G5", G5),
                        ImportantVertex.of("G6", G6),
                        ImportantVertex.of("G7", G7),
                        ImportantVertex.of("G8", G8),
                        ImportantVertex.of("G9", G9),
                        ImportantVertex.of("G10", G10),
                        ImportantVertex.of("G11", G11),

                        ImportantVertex.of("M", M),
                        ImportantVertex.of("M1", M1),
                        ImportantVertex.of("M2", M2),
                        ImportantVertex.of("M3", M3),
                        ImportantVertex.of("M4", M4),
                        ImportantVertex.of("M5", M5),
                        ImportantVertex.of("M6", M6),
                        ImportantVertex.of("N", N),
                        ImportantVertex.of("P", P),
                        ImportantVertex.of("Q1", Q1),
                        ImportantVertex.of("Q2", Q2),
                        ImportantVertex.of("Q3", Q3),
                        ImportantVertex.of("R1", R1),
                        ImportantVertex.of("R2", R2),
                        ImportantVertex.of("R3", R3),
                        ImportantVertex.of("R4", R4),
                        ImportantVertex.of("H1", H1),
                        ImportantVertex.of("H2", H2),

                        ImportantVertex.of("J1", J1),
                        ImportantVertex.of("J2", J2),
                        ImportantVertex.of("J3", J3),
                        ImportantVertex.of("J4", J4),
                        ImportantVertex.of("I1", I1),
                        ImportantVertex.of("I2", I2),
                        ImportantVertex.of("I3", I3),
                        ImportantVertex.of("I4", I4),
                        ImportantVertex.of("S1", S1),
                        ImportantVertex.of("S2", S2),

                        ImportantVertex.of("P1", P1),
                        ImportantVertex.of("P2", P2)
                )

                .addSinglePathsLines(
                        gray,
                        perimeter(B1B2, VER).apply(A),
                        perimeter(B1B2, HOR).apply(A),
                        perimeter(A1M, HOR).apply(A1),
                        perimeter(A1M, VER).apply(A1),
                        perimeter(C2E, VER).apply(C2),
                        perimeter(D2F, HOR).apply(D2),
                        perimeter(RATIO_n, VER).apply(A2),
                        perimeter(RATIO_n, HOR).apply(A2),
                        perimeter(RATIO_n, VER).apply(A3),
                        perimeter(KG1 / 4, VER).apply(A2.to(KG2, UR_V)),
                        perimeter(KG1 / 4, VER).apply(A2.to(KG2, DOWN)),


                        perimeter(KG1 / 4, VER).apply(A3.to(KG2, UP)),
                        perimeter(KG1 / 4, VER).apply(A3.to(KG2, UR_V)),
                        perimeter(KG1 / 4, VER).apply(A3.to(KG2, DOWN)),
                        perimeter(KG1 / 4, VER).apply(A3.to(KG2, DL_V)),

                        perimeter(RATIO_n, HOR).apply(B3),
                        perimeter(KG1 / 4, HOR).apply(B3.to(KG2, UL_H)),
                        perimeter(KG1 / 4, HOR).apply(B3.to(KG2, UR_H)),
                        perimeter(KG1 / 4, HOR).apply(B3.to(KG2, DL_H)),
                        perimeter(KG1 / 4, HOR).apply(B3.to(KG2, DR_H)),

                        perimeter(RATIO_i, VER).apply(C1),
                        perimeter(RATIO_m, VER).apply(C1),

                        perimeter(J1J3, HOR).apply(J1),
                        perimeter(J1J3, HOR).apply(J2),
                        perimeter(B3B4, HOR).apply(B3),

                        perimeter(B3B5 / 4, HOR).apply(B3.to(B3B4, UR_H)),
                        perimeter(B3B5 / 4, HOR).apply(B3.to(B3B4, DR_H)),

                        perimeter(KG3, HOR).apply(K),
                        perimeter(G8G4, VER).apply(G7),
                        perimeter(G8G4, VER).apply(G8),

                        diagonals(1, VER).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(KG1 / H, HOR).apply(A2)

                )
                .addSinglePathsLines(
                        green,
                        perimeter(B2P, VER).apply(P),
                        perimeter(KN, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(1, VER).apply(K),
                        perimeter(1, HOR).apply(K),
                        perimeter(KB2, HOR).apply(K),
                        perimeter(KB2, VER).apply(K),
                        perimeter(KB3, HOR).apply(K),
                        perimeter(KB3, VER).apply(K)
                )
                .addFullPaths(
                        gray,
                        diagonalHorizontal(1).apply(fpt(KG1, UP)),
                        diagonalVertical(1).apply(fpt(KG1, RIGHT)),
                        sequence(
                                Line.line(B1, A1, fpt(1, DR_H)),
                                Line.line(A1, fpt(1, DL_H), fpt(1, UL_V)),
                                Line.line(fpt(KA2, DR_V), fpt(KA2, DL_H), fpt(KA2, UL_V)),
                                Line.line(fpt(KA3, DR_V), fpt(KA3, DL_H), fpt(KA3, UL_V))
                        )
                )
                .addFullPaths(
                        green,
                        Line.line(B1.to(B1B2, UP), B1.to(B1B2, LEFT), B1.to(B1B2, DOWN)),
                        Line.line(B1.to(B1B3, UP), B1.to(B1B3, LEFT), B1.to(B1B3, DOWN)),
                        Line.line(A1.to(A1A2, UR_H), A1.to(A1A2, UL_V), A1.to(A1A2, DL_H)),
                        Line.line(A1.to(A1A3, UR_H), A1.to(A1A3, UL_V), A1.to(A1A3, DL_H))
                )
                .addCircleWithRadius(
                        blue,
                        circleInstruction(fpt(1, UR_V), A1M),
                        circleInstruction(A1, A1M)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(10)
                ;

    }


//    public static void main(String[] args) {
//        Export.export(Tile9c.getDesignHelperE();
//    }

}