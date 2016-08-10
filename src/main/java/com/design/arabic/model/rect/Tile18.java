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

public class Tile18 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double KC = KB * P6.H;
    private static final double CB = KB - KC;
    private static final double CD = KB * P6.P;


    private static final double BE = CD * (KB / KC);

    private static final double KI = KB * P6.H;
    private static final double BI = KB * P6.P;
    private static final double KJ = KB - BI;


    private static final double AS = KA - KB;
    private static final double ST = AS * H;
    private static final double AT = ST;
    private static final double FT = KB - AT;
    private static final double PT = BI + FT;
    private static final double AP = KB + BI;
    private static final double AQ = AP * (ST / PT);

    private static final double phi = Math.atan(AQ / AP);
    private static final double theta = phi * 2;

    private static final double FM = Math.cos(theta) * BI;
    private static final double MN = Math.sin(theta) * BI;
    private static final double FW = FM - MN;
    private static final double AW = KB - FW;
    private static final double KO1 = KJ * P6.H;
    private static final double OO1 = KJ * P6.P;
    private static final double B3O1 = KB + KO1;
    private static final double KO2 = KB * (OO1 / B3O1);
    private static final double O3O4 = KO2 / (1 + KO2 / KB);
    private static final double KO3 = O3O4;
    private static final double KO4 = O3O4 / H;

    private static final double O6J2 = KJ - KO3;
    private static final double FV = BI * (KO3 / O6J2);
    private static final double KL = KA - KB * H;

    public static final double BZ = KO4 + (KB / P6.H) * P6.P;
    public static final double AX = AW * H;
    public static final double KX = KA - AX;
    public static final double AL = KA - KL;
    public static final double LX = AL - AX;
    public static final double LX2 = LX / P12.H;
    public static final double XX2 = LX2 * P12.P;
    public static final double AU = AQ * H;
    public static final double UQ = AU;
    public static final double KU = KA - AU;
    public static final double UX = KU - KX;
    public static final double U2Q = UQ - XX2;
    public static final double UX3 = UQ * (UX / U2Q);
    public static final double KX3 = KU - UX3;
    public static final double SU = AS - AU;
    public static final double UQ5 = UQ;
    public static final double QQ5 = AQ / H;
    public static final double QU3 = QQ5 * (SU / UQ5) / (UX3 / UQ + SU / UQ5);
    public static final double X4U3 = QU3 * (UX3 / UQ);
    public static final double UU3 = UQ - QU3;

    public static final double FJ2 = KB - KJ;
    public static final double VP2 = BI + FV;
    public static final double AI2 = KB + BI;
    public static final double VV3 = VP2 * (AQ / AI2) / ( FJ2 / FV + AQ / AI2);
    public static final double V3V4 = VV3 * ( FJ2 / FV);
    public static final double FV3 = FV - VV3;
/*

X4U3  = (QQ5 - QU3) * (SU / UQ5)
X4U3 = QU3 * (X3U / UQ)
QU3 * (X3U / UQ) = (QQ5 - QU3) *(SU / UQ5)
QU3 * (X3U / UQ) = (QQ5 - QU3) *(SU / UQ5)
QU3 * (X3U / UQ + SU / UQ5) = QQ5 * (SU / UQ5)
QU3  = QQ5 * (SU / UQ5) / (X3U / UQ + SU / UQ5)

 */
/*

V3V4 = VV3 * ( FJ2 / FV)
V3V4 = (VP2 - VV3) * (AQ / AI2)
VV3 * ( FJ2 / FV) = (VP2 - VV3) * (AQ / AI2)
VV3 * ( FJ2 / FV + AQ / AI2) = VP2 * (AQ / AI2)
VV3 = VP2 * (AQ / AI2) / ( FJ2 / FV + AQ / AI2)



 */


    public final static FinalPointTransition A = fpt(KA, UR);
    public final static FinalPointTransition A2 = fpt(KA, DR);
    public final static FinalPointTransition A3 = fpt(KA, UL);
    public final static FinalPointTransition A4 = fpt(KA, DL);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition B2 = fpt(KB, DOWN);
    public final static FinalPointTransition B3 = fpt(KB, LEFT);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = C.to(CD, UP);
    public final static FinalPointTransition E = B.to(BE, UP);
    public final static FinalPointTransition F = fpt(KB, UP);
    public final static FinalPointTransition E2 = F.to(BE, RIGHT);
    public final static FinalPointTransition I = fpt(KI * P6.H, RIGHT, KI * P6.P, UP);
    public final static FinalPointTransition I2 = B.to(BI, DOWN);
    public final static FinalPointTransition I3 = B.to(BI, UP);
    public final static FinalPointTransition J = B.to(BI, LEFT);
    public final static FinalPointTransition J2 = fpt(KJ, UP);
    public final static FinalPointTransition O = fpt(KJ * P6.H, RIGHT, KJ * P6.P, UP);
    public final static FinalPointTransition O1 = fpt(KJ * P6.H, RIGHT);
    public final static FinalPointTransition O2 = fpt(KO2, UP);
    public final static FinalPointTransition O3 = fpt(KO3, LEFT);
    public final static FinalPointTransition O4 = fpt(KO4, UL);
    public final static FinalPointTransition O5 = fpt(KO4, UR);
    public final static FinalPointTransition O6 = fpt(KO3, UP);
    public final static FinalPointTransition O7 = fpt(KO4, DOWN);
    public final static FinalPointTransition O8 = fpt(KO4, RIGHT);
    public final static FinalPointTransition O9 = fpt(KO3, DR);
    public final static FinalPointTransition O10 = B2.to(KO4, RIGHT);
    public final static FinalPointTransition O11 = fpt(KO4, DR);
    public final static FinalPointTransition P = F.to(BI, LEFT);
    public final static FinalPointTransition P2 = F.to(BI, RIGHT);
    public final static FinalPointTransition S = fpt(KB, UR);
    public final static FinalPointTransition S2 = fpt(KB, DR);
    public final static FinalPointTransition R = fpt(KB * P6.H, UP, KB * P6.P, RIGHT);
    public final static FinalPointTransition T = A.to(AT, LEFT);
    public final static FinalPointTransition Q = A.to(AQ, DOWN);
    public final static FinalPointTransition Q2 = A3.to(AQ, DOWN);
    public final static FinalPointTransition Q3 = A2.to(AQ, UP);
    public final static FinalPointTransition Q4 = A2.to(AQ, LEFT);
    public final static FinalPointTransition Q5 = A.to(AQ, LEFT);
    public final static FinalPointTransition Q6 = A4.to(AQ, RIGHT);
    public final static FinalPointTransition M = F.to(FM, RIGHT);
    public final static FinalPointTransition W = M.to(MN, LEFT);
    public final static FinalPointTransition N = M.to(MN, DOWN);
    public final static FinalPointTransition W2 = B.to(FW, UP);
    public final static FinalPointTransition W3 = B.to(FW, DOWN);
    public final static FinalPointTransition V = F.to(FV, LEFT);
    public final static FinalPointTransition V2 = F.to(FV, RIGHT);
    public final static FinalPointTransition L = fpt(KL, UR);
    public final static FinalPointTransition L2 = fpt(KL, DR);
    public final static FinalPointTransition X = fpt(KX, UR);
    public final static FinalPointTransition X8 = fpt(KX, DR);
    public final static FinalPointTransition X9 = X8.to(XX2, DL);
    public final static FinalPointTransition X2 = X.to(XX2, DR);
    public final static FinalPointTransition X6 = X.to(XX2, UL);
    public final static FinalPointTransition X3 = fpt(KX3, UR);

    public final static FinalPointTransition U = fpt(KU, UR);
    public final static FinalPointTransition U5 = fpt(KU, DL);
    public final static FinalPointTransition X5 = U5.to(UU3, DR, X4U3, UR);
    public final static FinalPointTransition U2 = U.to(XX2, DR);
    public final static FinalPointTransition U3 = U.to(UU3, DR);
    public final static FinalPointTransition X4 = U3.to(X4U3, DL);

    public final static FinalPointTransition Z = B.to(BZ, DOWN);
    public final static FinalPointTransition Z2 = B2.to(BZ, RIGHT);
    public final static FinalPointTransition V3 = F.to(FV3, LEFT);
    public final static FinalPointTransition V4 = V3.to(V3V4, DOWN);
    public final static FinalPointTransition V5 = B.to(FV3, UP, V3V4, LEFT);
    public final static FinalPointTransition V6 = B2.to(FV3, LEFT, V3V4, UP);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_18",
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
                "BI = KB * P6.P",
                "KJ = KB / 2",
                "KL = KJ / H",
                "AQ = AP * (ST / PT)",
                "MFN = 2 * APQ",
                "X4U3  = (QQ5 - QU3) * (SU / UQ5)",
                "X4U3 = QU3 * (X3U / UQ)",
                "V3V4 = VV3 * ( FJ2 / FV)",
                "V3V4 = (VP2 - VV3) * (AQ / AI2)"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_18_design")
                .addEquations(equations)
                .addImportantVertexes(Tile18.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        Line.line(C, D),
                        Line.line(I, B),
                        Line.line(S, T),
                        Line.line(F, N),
                        Line.line(M, N),
                        Line.line(O, O1),
                        Line.line(O, B3),
                        Line.line(O3, O4),
                        Line.line(O4, O5),
                        Line.line(X, A.to(AW / 2, DOWN)),
                        Line.line(O8, O10),
                        Line.line(Q4, Q3),
                        Line.line(Q, Q5),
                        Line.line(Q, X2, U2),
                        Line.line(X4, U3),
                        Line.line(V4, V3)
                )
                .addFullPaths(
                        gray,
                        Line.line(K, E),
                        Line.line(K, E2),
                        Line.line(P, Q),
                        Line.line(P2, Q2),
                        Line.line(W, W2),
                        Line.line(O5, V),
                        Line.line(O4, V2),
                        Line.line(O8, Z2),
                        Line.line(O7, Z),
                        Line.line(X3, Q),
                        Line.line(X3, Q5)
                )
                .addCircleWithRadius(
                        gray,
                        circleInstruction(B, BI),
                        circleInstruction(F, BI),
                        circleInstruction(B3, BI),
                        circleInstruction(B2, BI),
                        circleInstruction(O3, KO3)
                )
                .addCirclesCentral(gray,
                        H,
                        KJ,
                        KO4
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(12)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(Q5, S, Q, X4, V5, O11, V6, X5, Q6),
                Line.line(W, X6, O8, X9, O10)
        );
    }

}