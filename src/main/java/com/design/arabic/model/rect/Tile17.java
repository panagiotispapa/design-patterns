package com.design.arabic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.RatioHelper.P12;
import com.design.common.RatioHelper.P6;
import com.design.common.model.Style;
import com.design.arabic.model.*;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.CircleInstruction.circleInstruction;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.arabic.model.Rect.Vertex.*;
import static com.design.arabic.model.Rect.diagonals;
import static com.design.arabic.model.Rect.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile17 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double KC = KB * P6.H;
    private static final double CB = KB - KC;
    private static final double CD = KB * P6.P;
    private static final double BE = CD * (KB / KC);
    private static final double AG = BE;
    private static final double KD = KC / P6.H;
    private static final double KI = CD;
    private static final double IF = KB - KI;
    private static final double KJ = KI * P6.H;
    private static final double JI2 = KI * P6.P;
    private static final double I2L = JI2 / P12.H;
    private static final double JL = I2L * P12.P;
    private static final double KL = KJ - JL;
    private static final double LM = KL / P12.P;
    private static final double KM = LM * P12.H;
    private static final double FM = KM - KB;
    private static final double MN = FM / P12.H;
    private static final double FN = MN * P12.P;
    private static final double BM = KM + KB;
    private static final double MP = BM / P12.H;
    private static final double B2P = MP * P12.P;
    private static final double A4P = KB - B2P;
    private static final double BI3 = KB - KI;
    private static final double BT = BI3;
    private static final double BQ = BT / 2;
    private static final double SQ = BQ;
    private static final double SV = SQ - CB;
    private static final double SX = SV / H;
    private static final double XZ = SX / P6.H;
    private static final double SZ = XZ * P6.P;
    private static final double ZW = BQ - SZ * H;
    private static final double KW = KI + ZW;
    private static final double BW = KB - KW;
    private static final double BZ = BW / P6.H;
    private static final double UC = BZ - CB;
    private static final double UX = UC / P6.H;
    private static final double KU = KB - BZ;
    private static final double AY = KB - BZ;
    private static final double YY2 = AY / P12.H;
    private static final double AY2 = YY2 * P12.P;
    private static final double KX2 = KU + UX;
    private static final double AT = KB - BT;
    private static final double KL3 = 0.5 * (AT / H);
    private static final double KL6 = KL3 / P12.H;
    private static final double L5L6 = KL6 * P12.P;
    private static final double L6L7 = L5L6 / P6.H;
    private static final double L5L7 = L6L7 * P6.P;
    private static final double KL7 = L5L7 + KL3;
    private static final double PY6 = KB - A4P - BZ;
    private static final double PP5 = PY6 * P12.P;


    public final static FinalPointTransition A = fpt(KA, UR);
    public final static FinalPointTransition A2 = fpt(KA, DR);
    public final static FinalPointTransition A3 = fpt(KA, UL);
    public final static FinalPointTransition A4 = fpt(KA, DL);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition B2 = fpt(KB, DOWN);
    public final static FinalPointTransition B3 = fpt(KB, LEFT);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition C4 = fpt(KC, LEFT);
    public final static FinalPointTransition C2 = A.to(CB, LEFT);
    public final static FinalPointTransition C3 = A2.to(CB, LEFT);
    public final static FinalPointTransition D = C.to(CD, UP);
    public final static FinalPointTransition D2 = C4.to(CD, UP);
    public final static FinalPointTransition E = B.to(BE, UP);
    //    public final static FinalPointTransition E4 = B.to(BE, DOWN);
    public final static FinalPointTransition F = fpt(KB, UP);
    public final static FinalPointTransition E2 = F.to(BE, RIGHT);
    //    public final static FinalPointTransition E3 = F.to(BE, LEFT);
    public final static FinalPointTransition G = A.to(AG, DOWN);
    public final static FinalPointTransition I = fpt(KI, UP);
    public final static FinalPointTransition J = fpt(KJ, LEFT);
    public final static FinalPointTransition I2 = J.to(KI * P6.P, DOWN);
    public final static FinalPointTransition I3 = fpt(KI, RIGHT);
    public final static FinalPointTransition L = J.to(JL, RIGHT);
    public final static FinalPointTransition M = fpt(KM, UP);
    public final static FinalPointTransition N = F.to(FN, LEFT);
    public final static FinalPointTransition N2 = B2.to(FN, RIGHT);
    public final static FinalPointTransition N3 = F.to(FN, RIGHT);
    public final static FinalPointTransition N4 = B.to(FN, UP);
    public final static FinalPointTransition P = B2.to(B2P, LEFT);
    public final static FinalPointTransition P2 = F.to(B2P, RIGHT);
    public final static FinalPointTransition P3 = B2.to(B2P, RIGHT);
    public final static FinalPointTransition P4 = B3.to(B2P, DOWN);
    public final static FinalPointTransition T = B.to(BI3, UP);
    public final static FinalPointTransition T2 = B2.to(BI3, LEFT);
    public final static FinalPointTransition T3 = F.to(BI3, RIGHT);
    public final static FinalPointTransition T4 = F.to(BI3, LEFT);
    public final static FinalPointTransition T5 = B3.to(BI3, UP);
    public final static FinalPointTransition T6 = B2.to(BI3, RIGHT);
    public final static FinalPointTransition Q = B.to(BQ, UP);
    public final static FinalPointTransition Q2 = B.to(BQ, LEFT);
    public final static FinalPointTransition S = Q.to(BQ, LEFT);
    public final static FinalPointTransition V = Q.to(CB, LEFT);
    public final static FinalPointTransition X = S.to(SX, DR);

    public final static FinalPointTransition Z = S.to(SZ, DL);
    public final static FinalPointTransition W = Z.to(ZW, DOWN);
    public final static FinalPointTransition U = B.to(BZ, LEFT);
    public final static FinalPointTransition X2 = U.to(UX, RIGHT);
    public final static FinalPointTransition X3 = fpt(KX2, UP);
    public final static FinalPointTransition X4 = fpt(KX2 * P6.H, UP, KX2 * P6.P, RIGHT);
    public final static FinalPointTransition X5 = fpt(KX2 * P6.H, UP, KX2 * P6.P, LEFT);
    public final static FinalPointTransition X6 = fpt(KX2 * P6.H, LEFT, KX2 * P6.P, UP);
    public final static FinalPointTransition L2 = fpt(KL, RIGHT);
    public final static FinalPointTransition L3 = fpt(KL3, DR);
    public final static FinalPointTransition L5 = fpt(KL3, UR);
    public final static FinalPointTransition L6 = fpt(KL6 * P6.H, RIGHT, KL6 * P6.P, UP);
    public final static FinalPointTransition L7 = fpt(KL7, UR);
    public final static FinalPointTransition Y = B.to(BZ, UP);
    public final static FinalPointTransition Y2 = A.to(AY2, LEFT);
    public final static FinalPointTransition Y3 = F.to(BZ, RIGHT);
    public final static FinalPointTransition Y4 = A.to(AY2, DOWN);
    public final static FinalPointTransition Y5 = A4.to(AY2, UP);
    public final static FinalPointTransition Y7 = A4.to(AY2, RIGHT);
    public final static FinalPointTransition Y6 = B2.to(BZ, LEFT);
    public final static FinalPointTransition P5 = P.to(PP5 * P12.P, RIGHT, PP5 * P12.H, UP);
    public final static FinalPointTransition P7 = P4.to(PP5 * P12.P, UP, PP5 * P12.H, RIGHT);
    public final static FinalPointTransition L8 = fpt(KL7 * P12.H, LEFT, KL7 * P12.P, DOWN);
    public final static FinalPointTransition L9 = fpt(KL7 * P12.H, DOWN, KL7 * P12.P, LEFT);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_17",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .withSize(Payload.Size.MEDIUM)
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        Sequence<String> equations = sequence(
                "KB = h",
                "KC = KB * P6.H",
                "CD = KB * P6.P",
                "BE = CD * (KB / KC) = AG",
                "EG + AE + BE = KB",
                "EG + AG - EG + BE - EG = KB",
                "EG = 2 * BE - KB",
                "KI = CD",
                "IF = KB - KI",
                "KJ = KI * P6.H",
                "LM = KL / P12.P",
                "BQ = BT / 2",
                "BQ = BS"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_17_design")
                .addEquations(equations)
                .addImportantVertexes(Tile17.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K)
//                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(SZ, HOR).apply(S)
                )
                .addSinglePathsLines(
                        blue,
                        Line.line(C, D),
                        Line.line(J, I2),
                        Line.line(K, M),
                        Line.line(L, M),
                        Line.line(S, Q),
                        Line.line(S, Q2),
                        Line.line(Z, W),
                        Line.line(Z, B),
                        Line.line(X, U),
                        Line.line(T, T3)

                )
                .addSinglePathsLines(
                        gray,
                        Line.line(D2, D, B2, D2)
//                        Line.circle(E, E2)

                )
                .addFullPaths(
                        gray,
                        Line.line(K, E),
                        Line.line(K, E2),
                        Line.line(D2, F, D),
                        Line.line(C3, C2),
                        Line.line(T, T2),
                        Line.line(P, N),
                        Line.line(P2, N2),
                        Line.line(P3, N3),
                        Line.line(Y, Y2),
                        Line.line(Y3, Y4)
                )
                .addCircleWithRadius(
                        gray,
                        circleInstruction(B, BZ),
                        circleInstruction(F, BZ),
                        circleInstruction(B2, BZ),
                        circleInstruction(B3, BZ),
                        circleInstruction(U, UX)
//                        Pair.circle(Y, YY2)
                )
                .addCirclesCentral(gray,
                        H,
                        KI,
                        KX2
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(14)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(X6, X5, X3, X4),
                Line.line(N3, L7, N4),
                Line.line(Y5, P5, L8, T5),
                Line.line(Y7, P7, L9, T6)
        );
    }

}