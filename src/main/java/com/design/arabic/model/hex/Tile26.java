package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.RatioHelper.P12;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile26 {
//https://s-media-cache-ak0.pinimg.com/236x/03/7a/b7/037ab7b5f11620dbad8b8e1012438547.jpg

    private static double KA = 1;
    private static double KB = KA * H;
    private static double KC = KA * P;
    private static double KD = KC / H;
    private static double KF = KD / H;
    private static double DF = KF * P;
    private static double DB = KB - KD;
    private static double AB = KA / 2;
    private static double AD = AB / H;
    private static double AE = AD / H;
    private static double CD = DB;
    private static double FD = CD / H;
    private static double CF = FD * P;
    private static double DG = DB / H;
    private static double BG = DG * P;
    private static double AG = AB - BG;
    private static double AI = AG;
    private static double DI = AD - AI;
    private static double DL = DI * H;
    private static double KE = AG;
    private static double LF = DF - DL;
    private static double LM = DL / 2;
    private static double DM = DL - LM;
    private static double DN = 2 * DM * H;
    private static double DQ = DI * H;
    private static double KR = KE / 2;
    private static double KS = KR * P12.H;
    private static double SR = KR * P12.P;
    private static double TR = SR / P;
    private static double TS = TR * H;
    private static double KT = KS + TS;

    public final static FinalPointTransition A = fpt(KA, DR_H);
    public static final FinalPointTransition T6 = A.to(KT * P12.H, UP, KT * P12.P, LEFT);
    public static final FinalPointTransition T5 = A.to(KT * P12.H, UP, KT * P12.P, RIGHT);
    public static final FinalPointTransition T4 = A.to(KT * P12.H, UL_V, KT * P12.P, UR_H);
    public static final FinalPointTransition T3 = A.to(KT * P12.H, UL_V, KT * P12.P, DL_H);
    public final static FinalPointTransition A2 = fpt(KA, DL_H);
    public final static FinalPointTransition B = fpt(KB, DOWN);
    public final static FinalPointTransition C = fpt(KC, DL_H);
    public final static FinalPointTransition C2 = fpt(KC, DR_H);
    public final static FinalPointTransition D = fpt(KD, DOWN);
    public final static FinalPointTransition D2 = fpt(KD, DR_V);
    public final static FinalPointTransition E = A.to(AE, UL_H);
    public final static FinalPointTransition E2 = A2.to(AE, UR_H);
    public final static FinalPointTransition F = C.to(CF, DL_H);
    public final static FinalPointTransition F2 = C2.to(CF, DR_H);
    public final static FinalPointTransition G = B.to(BG, RIGHT);
    public final static FinalPointTransition G2 = B.to(BG, LEFT);
    public final static FinalPointTransition I = A.to(AG, UL_V);
    public final static FinalPointTransition I2 = D.to(DI, UR_V);
    public final static FinalPointTransition L = D.to(DL, LEFT);
    public final static FinalPointTransition M = D.to(DM, LEFT);
    public final static FinalPointTransition M2 = D.to(DM, UL_H);
    public final static FinalPointTransition N = D.to(DN, UL_V);
    public final static FinalPointTransition Q = D.to(DQ, UR_H);
    public final static FinalPointTransition Q2 = D.to(DQ, UL_H);
    public final static FinalPointTransition R = fpt(KR, DR_H);
    public final static FinalPointTransition R2 = fpt(KR, DOWN);
    public final static FinalPointTransition R3 = fpt(KR, DL_H);
    public final static FinalPointTransition S = fpt(KS * P12.H, DOWN, KS * P12.P, RIGHT);
    public final static FinalPointTransition T = fpt(KT * P12.H, DOWN, KT * P12.P, RIGHT);
    public final static FinalPointTransition T2 = fpt(KT * P12.H, DOWN, KT * P12.P, LEFT);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_26",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withSize(Payload.Size.MEDIUM)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();

    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Sequence<String> equations = sequence(
                "KB = KA * H",
                "KC = KA * P",
                "KD = KC / H",
                "DE = DB * P",
                "AD = AB / H",
                "AE = AD / H",
                "FD = CD / H",
                "CF = FD * P",
                "LM = LF",
                "",
                ""

        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_26_design")
                .addEquations(equations)
                .addImportantVertexes(Tile26.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),


                        perimeter(AE, HOR).apply(A2),
                        perimeter(DI, VER).apply(D),
                        perimeter(DI, VER).apply(D2),
                        diagonals(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(AG, HOR).apply(A),
                        perimeter(AG, VER).apply(A),
                        perimeter(AG, HOR).apply(A2),
                        perimeter(AG, VER).apply(A2),
                        perimeter(AG, HOR).apply(K),
                        perimeter(AG, VER).apply(K),
                        perimeter(KT * P12.P, VER).apply(A.to(KT * P12.H, UL_V)),
                        perimeter(KT * P12.P, HOR).apply(A.to(KT * P12.H, UL_V))
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(AE, HOR).apply(A),
                        perimeter(AE, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        Line.line(A, C),
                        Line.line(A2, C2),
                        Line.line(F, F2),
                        Line.line(A2, L),
                        Line.line(K, B),
                        Line.line(N, M),
                        Line.line(R, R2, R3)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(12)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(M, N, M2, D.to(DN, UP), D.to(DM, UR_H), D.to(DN, UR_V), D.to(DM, RIGHT), D.to(DN, DR_V), D.to(DM, DR_H), D.to(DN, DOWN), D.to(DM, DL_H), D.to(DN, DL_V), D.to(DM, LEFT)),
                Line.line(C, N),
                Line.line(G, E2),
                Line.line(G2, E),
                Line.line(F2, F),
                Line.line(D.to(DN, UR_V), C2),
                Line.line(D.to(DN, DR_V), A),
                Line.line(D.to(DN, DL_V), A2),
                Line.line(D.to(DN, UP), K),
                Line.line(D.to(DN, DOWN), D.to(CD, DOWN)),
                Line.line(R3, T2, R2, T, R),
                Line.line(T, Q),
                Line.line(T2, Q2),
                Line.line(K, A),
                Line.line(K, A2),
                Line.line(A, A2),
                Line.line(I, D.to(DI, UR_V), D.to(DI, UP), D.to(DI, UL_V), D.to(DI, DL_V), D.to(DI, DOWN), I),
                Line.line(A.to(KR, UR_H), T5, A.to(KR, UP), T6, A.to(KR, UL_H), T4, A.to(KR, UL_V), T3, A.to(KR, LEFT)),
                Line.line(T3, D.to(DQ, DR_H)),
                Line.line(T4, D.to(DQ, RIGHT)),
                Line.line(T6, D2.to(DQ, DL_H)),
                Line.line(T5, D2.to(DQ, DR_H))
        
        );
    }
}