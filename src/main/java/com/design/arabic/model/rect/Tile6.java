package com.design.arabic.model.rect;

import com.design.common.*;
import com.design.common.RatioHelper.P8;
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

public class Tile6 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double BA = KB;
    private static final double CA = KA - KB;
    private static final double AE = CA;
    private static final double BE = BA - AE;
    private static final double KG = CA;
    private static final double KF = CA;
    private static final double BF = KB - KF;
    private static final double BG = KB + KG;
    private static final double BI = BF * (BE / BG);
    private static final double AL = CA * H;
    private static final double CL = BF / 2;
    private static final double LE = AE - AL;
    private static final double IM = BI * P8.P;
    private static final double IN = IM * P8.P;
    private static final double NM = IM * P8.H;
    private static final double DP = BE * P8.P;
    private static final double DQ = DP * P8.P;
    private static final double QP = DP * P8.H;
    private static final double KR = KF * (LE / CL);
    private static final double RF = KF - KR;
    private static final double RT = RF * P8.P;
    private static final double RS = RT * P8.P;
    private static final double ST = RT * P8.H;


    public final static FinalPointTransition A = fpt(KA, UL);
    public final static FinalPointTransition A2 = fpt(KA, DL);
    public final static FinalPointTransition A3 = fpt(KA, DR);
    public final static FinalPointTransition A4 = fpt(KA, UR);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition B2 = fpt(KB, LEFT);
    public final static FinalPointTransition B3 = fpt(KB, DOWN);
    public final static FinalPointTransition B4 = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KB, UL);
    public final static FinalPointTransition C2 = fpt(KB, DL);
    public final static FinalPointTransition C3 = fpt(KB, DR);
    public final static FinalPointTransition C4 = fpt(KB, UR);
    public final static FinalPointTransition D = A.to(CA, DOWN);
    public final static FinalPointTransition D2 = A2.to(CA, UP);
    public final static FinalPointTransition D3 = A3.to(CA, UP);
    public final static FinalPointTransition D4 = A4.to(CA, DOWN);
    public final static FinalPointTransition E = A.to(CA, RIGHT);
    public final static FinalPointTransition E2 = A2.to(CA, RIGHT);
    public final static FinalPointTransition E3 = A3.to(CA, LEFT);
    public final static FinalPointTransition E4 = A4.to(CA, LEFT);
    public final static FinalPointTransition F = fpt(CA, UP);
    public final static FinalPointTransition G = fpt(CA, DOWN);
    public final static FinalPointTransition I = B.to(BI, LEFT);
    public final static FinalPointTransition I2 = B3.to(BI, LEFT);
    public final static FinalPointTransition I3 = B2.to(BI, DOWN);
    public final static FinalPointTransition I4 = B4.to(BI, DOWN);
    public final static FinalPointTransition J3 = B2.to(BI, UP);
    public final static FinalPointTransition J4 = B4.to(BI, UP);
    public final static FinalPointTransition J = B.to(BI, RIGHT);
    public final static FinalPointTransition J2 = B3.to(BI, RIGHT);
    public final static FinalPointTransition L = A.to(AL, RIGHT);
    public final static FinalPointTransition L2 = A2.to(AL, RIGHT);
    public final static FinalPointTransition N = I.to(IN, RIGHT);
    public final static FinalPointTransition N2 = J.to(IN, LEFT);
    public final static FinalPointTransition M = N.to(NM, DOWN);
    public final static FinalPointTransition M2 = N2.to(NM, DOWN);
    public final static FinalPointTransition Q = D.to(DQ, DOWN);
    public final static FinalPointTransition Q2 = D4.to(DQ, DOWN);
    public final static FinalPointTransition P = Q.to(QP, RIGHT);
    public final static FinalPointTransition P2 = Q2.to(QP, LEFT);
    public final static FinalPointTransition R = fpt(KR, UP);
    public final static FinalPointTransition S = R.to(RS, UP);
    public final static FinalPointTransition T = S.to(ST, LEFT);
    public final static FinalPointTransition T2 = S.to(ST, RIGHT);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_06",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style blue = new Style.Builder(Color.blue, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();

        Sequence<String> equations = sequence(
                "KB = h = KC",
                "CA = KA - KB = AD = AE",
                "BE = AB - AE",
                "BI / BF = BE / BG",
                "KF = AC",
                "AL = AC * h",
                "LE = AE - AL",
                "IM = BI * P8.P"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_06_design")
                .addEquations(equations)
                .addImportantVertexes(Tile6.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        gray,
                        Line.line(C, B3, C4),
                        Line.line(C, B4, C2),
                        Line.line(C2, B, C3),
                        Line.line(C4, B2, C3),
                        Line.line(E2, J),
                        Line.line(I2, E4),
                        Line.line(E3, I),
                        Line.line(E, J2),
                        Line.line(D, I4),
                        Line.line(J3, D3),
                        Line.line(I3, D4),
                        Line.line(D2, J4),
                        Line.line(M, N),
                        Line.line(P, Q),
                        Line.line(T, S)
                )
                .addSinglePathsLines(
                        blue,
                        Line.line(L, L2)
                )
                .addFullPaths(
                        gray,
                        Line.line(B, D),
                        Line.line(B2, E)
                )
                .addCircleWithRadius(
                        blue,
                        circleInstruction(I, IM),
                        circleInstruction(D, DP)
                )
                .addCirclesCentral(gray, H)
                .addFullPaths(red, getFullPath())
                .withFontSize(14)
                ;
    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(E, P, T, M2, B, M, T2, P2, E4)
        );
    }


}