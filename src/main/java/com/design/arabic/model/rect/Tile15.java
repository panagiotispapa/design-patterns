package com.design.arabic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
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

public class Tile15 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double KC = KB * P6.H;
    private static final double CD = KB * P6.P;
    private static final double BE = CD * (KB / KC);
    private static final double KI = KA - KB;
    private static final double KJ = KI * P6.H;
    private static final double JL = KI * P6.P;
    private static final double BM = KB - KI;
    private static final double MN = BM / P6.H;
    private static final double BN = MN * P6.P;
    private static final double BM2 = KB - JL;
    private static final double BP = KJ + BM2 * (P6.P / P6.H);
    private static final double KG = KB / P6.H;
    private static final double FG = KG * P6.P;
    private static final double GR = FG - KJ;
    private static final double RS = GR / 2;
    public static final double BU = KI / 2;
    public static final double UX = 0.5 * BU * (BN / BM);
    public static final double KO = (KI / 2) / H;

    private static final double ST = RS / P6.P;
    public static final double KW = BU / P6.H;
    public static final double WI = KI - KW;
    //    public static final double IZ = WI * P6.H;
    public static final double WZ = WI * P6.P;


    public final static FinalPointTransition A = fpt(KA, UR);
    public final static FinalPointTransition A2 = fpt(KA, DR);
    public final static FinalPointTransition A3 = fpt(KA, UL);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition B2 = fpt(KB, DOWN);
    public final static FinalPointTransition B3 = fpt(KB, LEFT);
    public final static FinalPointTransition F = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = C.to(CD, RIGHT);
    public final static FinalPointTransition E = B.to(BE, RIGHT);
    public final static FinalPointTransition G = F.to(BE, UP);
    public final static FinalPointTransition G2 = B.to(BE, LEFT);
    public final static FinalPointTransition I = fpt(KI, RIGHT);
    public final static FinalPointTransition M = fpt(KI, UP);
    public final static FinalPointTransition M2 = fpt(JL, UP);
    public final static FinalPointTransition N = B.to(BN, RIGHT);
    public final static FinalPointTransition N2 = B.to(BN, LEFT);
    public final static FinalPointTransition N3 = B2.to(BN, LEFT);
    public final static FinalPointTransition N4 = B2.to(BN, RIGHT);
    public final static FinalPointTransition N5 = B3.to(BN, UP);
    public final static FinalPointTransition N6 = B3.to(BN, DOWN);
    public final static FinalPointTransition R = F.to(BN, UP);
    public final static FinalPointTransition N8 = F.to(BN, DOWN);
    //    public final static FinalPointTransition N2 = B.to(KJ, RIGHT);
    public final static FinalPointTransition J = fpt(KJ, UP);
    public final static FinalPointTransition J2 = fpt(KJ, RIGHT);
    public final static FinalPointTransition L = J.to(JL, RIGHT);
    public final static FinalPointTransition L2 = J2.to(JL, UP);
    public final static FinalPointTransition Q = B.to(BP, RIGHT);
    public final static FinalPointTransition Q2 = B.to(BP, LEFT);
    public final static FinalPointTransition Q3 = B2.to(BP, LEFT);
    public final static FinalPointTransition Q4 = B2.to(BP, RIGHT);
    public final static FinalPointTransition Q5 = B3.to(BP, UP);
    public final static FinalPointTransition Q6 = B3.to(BP, DOWN);
    public final static FinalPointTransition Q7 = F.to(BP, UP);
    public final static FinalPointTransition PQ = F.to(BP, DOWN);
    public final static FinalPointTransition S = R.to(RS, UP);
    public final static FinalPointTransition S2 = N.to(RS, RIGHT);
    public final static FinalPointTransition T = S.to(ST, LEFT);
    public final static FinalPointTransition T2 = S2.to(ST, DOWN);
    public final static FinalPointTransition U = B.to(BU, LEFT);
    public final static FinalPointTransition U2 = B.to(BU, RIGHT);
    public final static FinalPointTransition X = U.to(UX, DOWN);
    public final static FinalPointTransition X2 = U2.to(UX, DOWN);
    public final static FinalPointTransition O = fpt(KO, UL);
    public final static FinalPointTransition O2 = fpt(KO, UR);
    public final static FinalPointTransition W = fpt(KW, RIGHT);
    public final static FinalPointTransition W2 = M2.to(KW * P6.P, LEFT);
    public final static FinalPointTransition Z = W.to(WZ * P6.P, RIGHT, WZ * P6.H, DOWN);
    public final static FinalPointTransition Z2 = W2.to(WZ * P6.P, RIGHT, WZ * P6.H, UP);
    public final static FinalPointTransition Q10 = A.to(ST / H, DL);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_15",
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
                "BE = CD * (KB / KC)",
                "KI = KA - KB",
                "KJ = KI * P6.H",
                "JL = KI * P6.P",
                "BN = MN * P6.P",
                "GR = FG - KJ",
                "RS = GR / 2",
                "ST = RS / P6.P",
                "BU = KI / 2",
                "UX = 0.5 * BU * (BN / BM)",
                "",
                ""
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_15_design")
                .addEquations(equations)
                .addImportantVertexes(Tile15.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
//                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K)
//                        perimeter(H, VER).apply(K),
//                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        Line.line(C, D),
                        Line.line(J, L),
                        Line.line(J2, L2),
                        Line.line(L2, B.to(KJ, RIGHT)),
                        Line.line(M, N),
                        Line.line(S, T)
                )
                .addFullPaths(
                        gray,
                        Line.line(A.to(ST, LEFT), A2.to(ST, LEFT)),
                        Line.line(B, D),
                        Line.line(B.to(JL, RIGHT), B2.to(JL, RIGHT)),
                        Line.line(B, C.to(CD, LEFT)),
                        Line.line(Q3, N),
                        Line.line(N3, Q),
                        Line.line(N2, Q4),
                        Line.line(Q2, N4),
                        Line.line(N2, R),
                        Line.line(N, N5),
                        Line.line(K, E),
                        Line.line(K, G)
                )
                .addCircleWithRadius(
                        gray,
                        circleInstruction(A, KB),
                        circleInstruction(A2, KB),
                        circleInstruction(W, WZ)
                )
                .addCirclesCentral(gray,
                        H,
                        KI
                )
//                .addCirclesCentral(asList(
//                        KJ
//                ), blue)
                .addFullPaths(red, getFullPath())
                .withFontSize(14)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(O, X, B, X2, O2),
                Line.line(Q4, Z, R, T, Q10, T2, N, Z2, Q5)
//                Line.circle(X6, R, W, E, I),
//                Line.circle(X7, X5, R2, W4, E2, I2),
//                Line.circle(X4, L3, Q2, W3, U3)
        );
    }

}