package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.RatioHelper.P12;
import com.design.common.RatioHelper.P6;
import com.design.common.model.Style;
import com.design.islamic.model.*;
import com.googlecode.totallylazy.Sequence;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.diagonals;
import static com.design.islamic.model.Rect.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile18 {

    private static final double KA = 1.0;
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
    private static final double theta = phi * 2.0;

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


    public final static FinalPointTransition A = fpt(pt(KA, UR));
    public final static FinalPointTransition A2 = fpt(pt(KA, DR));
    public final static FinalPointTransition A3 = fpt(pt(KA, UL));
    public final static FinalPointTransition A4 = fpt(pt(KA, DL));
    public final static FinalPointTransition B = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition B2 = fpt(pt(KB, DOWN));
    public final static FinalPointTransition B3 = fpt(pt(KB, LEFT));
    public final static FinalPointTransition C = fpt(pt(KC, RIGHT));
    public final static FinalPointTransition D = C.append(pt(CD, UP));
    public final static FinalPointTransition E = B.append(pt(BE, UP));
    public final static FinalPointTransition F = fpt(pt(KB, UP));
    public final static FinalPointTransition E2 = F.append(pt(BE, RIGHT));
    public final static FinalPointTransition I = fpt(pt(KI * P6.H, RIGHT), pt(KI * P6.P, UP));
    public final static FinalPointTransition I2 = B.append(pt(BI, DOWN));
    public final static FinalPointTransition I3 = B.append(pt(BI, UP));
    public final static FinalPointTransition J = B.append(pt(BI, LEFT));
    public final static FinalPointTransition J2 = fpt(pt(KJ, UP));
    public final static FinalPointTransition O = fpt(pt(KJ * P6.H, RIGHT), pt(KJ * P6.P, UP));
    public final static FinalPointTransition O1 = fpt(pt(KJ * P6.H, RIGHT));
    public final static FinalPointTransition O2 = fpt(pt(KO2, UP));
    public final static FinalPointTransition O3 = fpt(pt(KO3, LEFT));
    public final static FinalPointTransition O4 = fpt(pt(KO4, UL));
    public final static FinalPointTransition O5 = fpt(pt(KO4, UR));
    public final static FinalPointTransition O6 = fpt(pt(KO3, UP));
    public final static FinalPointTransition O7 = fpt(pt(KO4, DOWN));
    public final static FinalPointTransition O8 = fpt(pt(KO4, RIGHT));
    public final static FinalPointTransition O9 = fpt(pt(KO3, DR));
    public final static FinalPointTransition O10 = B2.append(pt(KO4, RIGHT));
    public final static FinalPointTransition O11 = fpt(pt(KO4, DR));
    public final static FinalPointTransition P = F.append(pt(BI, LEFT));
    public final static FinalPointTransition P2 = F.append(pt(BI, RIGHT));
    public final static FinalPointTransition S = fpt(pt(KB, UR));
    public final static FinalPointTransition S2 = fpt(pt(KB, DR));
    public final static FinalPointTransition R = fpt(pt(KB * P6.H, UP), pt(KB * P6.P, RIGHT));
    public final static FinalPointTransition T = A.append(pt(AT, LEFT));
    public final static FinalPointTransition Q = A.append(pt(AQ, DOWN));
    public final static FinalPointTransition Q2 = A3.append(pt(AQ, DOWN));
    public final static FinalPointTransition Q3 = A2.append(pt(AQ, UP));
    public final static FinalPointTransition Q4 = A2.append(pt(AQ, LEFT));
    public final static FinalPointTransition Q5 = A.append(pt(AQ, LEFT));
    public final static FinalPointTransition Q6 = A4.append(pt(AQ, RIGHT));
    public final static FinalPointTransition M = F.append(pt(FM, RIGHT));
    public final static FinalPointTransition W = M.append(pt(MN, LEFT));
    public final static FinalPointTransition N = M.append(pt(MN, DOWN));
    public final static FinalPointTransition W2 = B.append(pt(FW, UP));
    public final static FinalPointTransition W3 = B.append(pt(FW, DOWN));
    public final static FinalPointTransition V = F.append(pt(FV, LEFT));
    public final static FinalPointTransition V2 = F.append(pt(FV, RIGHT));
    public final static FinalPointTransition L = fpt(pt(KL, UR));
    public final static FinalPointTransition L2 = fpt(pt(KL, DR));
    public final static FinalPointTransition X = fpt(pt(KX, UR));
    public final static FinalPointTransition X8 = fpt(pt(KX, DR));
    public final static FinalPointTransition X9 = X8.append(pt(XX2, DL));
    public final static FinalPointTransition X2 = X.append(pt(XX2, DR));
    public final static FinalPointTransition X6 = X.append(pt(XX2, UL));
    public final static FinalPointTransition X3 = fpt(pt(KX3, UR));

    public final static FinalPointTransition U = fpt(pt(KU, UR));
    public final static FinalPointTransition U5 = fpt(pt(KU, DL));
    public final static FinalPointTransition X5 = U5.append(pt(UU3, DR), pt(X4U3, UR));
    public final static FinalPointTransition U2 = U.append(pt(XX2, DR));
    public final static FinalPointTransition U3 = U.append(pt(UU3, DR));
    public final static FinalPointTransition X4 = U3.append(pt(X4U3, DL));

    public final static FinalPointTransition Z = B.append(pt(BZ, DOWN));
    public final static FinalPointTransition Z2 = B2.append(pt(BZ, RIGHT));
    public final static FinalPointTransition V3 = F.append(pt(FV3, LEFT));
    public final static FinalPointTransition V4 = V3.append(pt(V3V4, DOWN));
    public final static FinalPointTransition V5 = B.append(pt(FV3, UP), pt(V3V4, LEFT));
    public final static FinalPointTransition V6 = B2.append(pt(FV3, LEFT), pt(V3V4, UP));


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
                "KJ = KB / 2.0",
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
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        PointsPath.of(C, D),
                        PointsPath.of(I, B),
                        PointsPath.of(S, T),
                        PointsPath.of(F, N),
                        PointsPath.of(M, N),
                        PointsPath.of(O, O1),
                        PointsPath.of(O, B3),
                        PointsPath.of(O3, O4),
                        PointsPath.of(O4, O5),
                        PointsPath.of(X, A.append(pt(AW / 2.0, DOWN))),
                        PointsPath.of(O8, O10),
                        PointsPath.of(Q4, Q3),
                        PointsPath.of(Q, Q5),
                        PointsPath.of(Q, X2, U2),
                        PointsPath.of(X4, U3),
                        PointsPath.of(V4, V3)
                )
                .addFullPaths(
                        gray,
                        PointsPath.of(K, E),
                        PointsPath.of(K, E2),
                        PointsPath.of(P, Q),
                        PointsPath.of(P2, Q2),
                        PointsPath.of(W, W2),
                        PointsPath.of(O5, V),
                        PointsPath.of(O4, V2),
                        PointsPath.of(O8, Z2),
                        PointsPath.of(O7, Z),
                        PointsPath.of(X3, Q),
                        PointsPath.of(X3, Q5)
                )
                .addCircleWithRadius(
                        gray,
                        Pair.of(B, BI),
                        Pair.of(F, BI),
                        Pair.of(B3, BI),
                        Pair.of(B2, BI),
                        Pair.of(O3, KO3)
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

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(Q5, S, Q, X4, V5, O11, V6, X5, Q6),
                PointsPath.of(W, X6, O8, X9, O10)
        );
    }

}