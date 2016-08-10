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

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.arabic.model.Rect.Vertex.*;
import static com.design.arabic.model.Rect.diagonals;
import static com.design.arabic.model.Rect.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.Math.PI;

public class Tile16 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double KC = KB * P6.H;
    private static final double CB = KB - KC;
    private static final double CD = KB * P6.P;
    private static final double BE = CD * (KB / KC);
    private static final double AG = BE;
    private static final double EG = 2 * BE - KB;
    private static final double GI = EG / 2;
    private static final double BI = BE - GI;
    private static final double KJ = KB * P6.H;
    private static final double JD = KB - KJ;
    private static final double KM = KJ - JD;
    private static final double KN = KM * P6.H;
    private static final double MN = KM * P6.P;
    private static final double BM = KB - KM;
    private static final double ML = BM / P12.H;
    private static final double BL = ML * P12.P;
    private static final double MQ = (KM + KB) / P12.H;
    private static final double B2Q = MQ * P12.P;
    private static final double BN = KB - KN;
    private static final double FO = BN;
    private static final double DS = CB / P6.P;
    private static final double IS = DS * P6.H;
    private static final double GE = 2 * GI;
    private static final double MC = KC - KM;
    private static final double MV = MC / P6.H;
    private static final double CV = 0.5 * MV * P6.P;
    private static final double TE = GE;
//    private static final double TX = TE * P6.P;
//    private static final double EX = TE * P6.H;
//    private static final double KE = KB / P6.H;
    private static final double AE = KB - BE;
    private static final double AZ = AE * H;
    private static final double ZE = AZ;
    private static final double KZ = KA - AZ;
    private static final double AO4 = KB - 2 * FO;

    private static final double phi = Math.atan(AO4 / AE);
    private static final double ZY = ZE * Math.tan(0.5 * (2 * PI) / 4 - phi);
    private static final double AY = AZ - ZY;


    private static final double AL = KB - 2 * FO;
    private static final double KW = (AL / H) / 2;
    private static final double KW2 = KW / H;

//    private static final double KI = KA - KB;


    public final static FinalPointTransition A = fpt(KA, UR);
    public final static FinalPointTransition A2 = fpt(KA, UL);
    public final static FinalPointTransition A3 = fpt(KA, DL);
    public final static FinalPointTransition A4 = fpt(KA, DR);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    //    public static final FinalPointTransition O3 = B.to(2 * FO, UP);
    public final static FinalPointTransition B2 = fpt(KB, LEFT);
    public final static FinalPointTransition B3 = fpt(KB, DOWN);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition C2 = fpt(KC, UP);
    public final static FinalPointTransition C3 = fpt(KC * P6.H, LEFT, KC * P6.P, UP);
    public final static FinalPointTransition C4 = fpt(KC * P6.H, DOWN, KC * P6.P, RIGHT);
    public final static FinalPointTransition C5 = fpt(KC * P6.H, LEFT, KC * P6.P, DOWN);
    public final static FinalPointTransition D = C.to(CD, UP);
    public final static FinalPointTransition D2 = fpt(KC, UP, CD, RIGHT);
    public final static FinalPointTransition I2 = D2.to(CB, UP);
    public final static FinalPointTransition E = B.to(BE, UP);
    public final static FinalPointTransition F = fpt(KB, UP);
    public final static FinalPointTransition E2 = F.to(BE, RIGHT);
    public final static FinalPointTransition G = A.to(AG, DOWN);
    public final static FinalPointTransition I = G.to(GI, UP);
    public final static FinalPointTransition G2 = A2.to(AG, DOWN);
    public final static FinalPointTransition G3 = A2.to(AG, RIGHT);
    public final static FinalPointTransition G4 = A3.to(AG, RIGHT);
    public final static FinalPointTransition J = fpt(KJ * P6.H, RIGHT, KJ * P6.P, UP);
    public final static FinalPointTransition N = fpt(KN, RIGHT);
    public final static FinalPointTransition N2 = fpt(KN, LEFT);
    public final static FinalPointTransition N3 = fpt(KN, UP);
    public final static FinalPointTransition M = N.to(MN, UP);
    public final static FinalPointTransition M2 = N2.to(MN, DOWN);
    public final static FinalPointTransition M3 = N3.to(MN, LEFT);
    public final static FinalPointTransition M4 = N2.to(MN, UP);
    public final static FinalPointTransition P = B.to(BL, UP);
    public final static FinalPointTransition P2 = F.to(BL, RIGHT);
    public final static FinalPointTransition Q = B2.to(B2Q, DOWN);
    public final static FinalPointTransition Q2 = B3.to(B2Q, LEFT);
    public final static FinalPointTransition O = F.to(FO, LEFT);
    public final static FinalPointTransition O2 = F.to(2 * FO, LEFT);
    public final static FinalPointTransition O3 = B.to(2 * FO, UP);
    public final static FinalPointTransition L = F.to(2 * FO, RIGHT);
    public final static FinalPointTransition O5 = B3.to(2 * FO, LEFT);
    public final static FinalPointTransition S = I.to(IS, DOWN);
    public final static FinalPointTransition S2 = I2.to(IS, LEFT);
    public final static FinalPointTransition T = D.to(GE, UP);
    public final static FinalPointTransition T2 = D2.to(GE, RIGHT);
    public final static FinalPointTransition V = C.to(CV, DOWN);
    public final static FinalPointTransition V2 = C.to(CV, UP);
    public final static FinalPointTransition V3 = C2.to(CV, RIGHT);
    public final static FinalPointTransition V4 = C3.to(CV * P6.H, DOWN, CV * P6.P, LEFT);
    public final static FinalPointTransition V5 = C3.to(CV * P6.H, UP, CV * P6.P, RIGHT);
    public final static FinalPointTransition V6 = C4.to(CV * P6.H, LEFT, CV * P6.P, DOWN);
    public final static FinalPointTransition V7 = C5.to(CV * P6.H, DOWN, CV * P6.P, RIGHT);
    public final static FinalPointTransition Z = A.to(AZ, DL);
    public final static FinalPointTransition Y = A.to(AY, DL);
    public final static FinalPointTransition W = fpt(KW, DR);
    public final static FinalPointTransition W2 = fpt(KW2, UP);
    public final static FinalPointTransition W5 = fpt(KW2, LEFT);
    public final static FinalPointTransition W3 = fpt(KW2 * P6.H, RIGHT, KW2 * P6.P, DOWN);
    public final static FinalPointTransition W4 = fpt(KW2 * P6.H, UP, KW2 * P6.P, LEFT);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_16",
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
                "GI = EG / 2",
                "KJ = KB * P6.H",
                "KL = KJ * P6.H",
                "JL = KJ * P6.P",
                "JD = KB - KJ",
                "KM = KJ -JD",
                "KN = KM * P6.H",
                "MN = KM * P6.P",
                "AL = KB - 2 * FO",
                "KW = (AL / H) / 2"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_16_design")
                .addEquations(equations)
                .addImportantVertexes(Tile16.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
//                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K)
//                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        Line.line(C, D),
                        Line.line(I, D),
                        Line.line(fpt(KJ * P6.H, RIGHT), J),
                        Line.line(M, N),
                        Line.line(M3, O),
                        Line.line(K, A),
                        Line.line(E, E2),
                        Line.line(L, O3),
                        Line.line(K, W),
                        Line.line(fpt(KW2 * P6.H, RIGHT), W3),
                        Line.line(fpt(KC * P6.H, LEFT), C3)

                )
                .addFullPaths(
                        gray,
                        Line.line(K, E),
                        Line.line(K, E2),
                        Line.line(F, G),
                        Line.line(F, G2),
                        Line.line(A.to(CB, LEFT), A4.to(CB, LEFT)),
                        Line.line(Q, P),
                        Line.line(Q2, P2),
                        Line.line(O2, O3),
                        Line.line(E2, O3),
                        Line.line(E, L),
                        Line.line(E2, S),
                        Line.line(E, S2),
                        Line.line(T2, L),
                        Line.line(T, O3),
                        Line.line(O5, O3)
                )
                .addCirclesCentral(gray,
                        H,
                        KM
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(14)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(B3, V6, W3, V2, T, O3, Y, L, T2, V3, W4, V4, B2),
                Line.line(G3, V5, W5, V7, G4)
        );
    }

}