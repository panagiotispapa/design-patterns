package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
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

public class Tile15 {

    private static final double KA = 1.0;
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
    private static final double RS = GR / 2.0;
    public static final double BU = KI / 2;
    public static final double UX = 0.5 * BU * (BN / BM);
    public static final double KO = (KI / 2.0) / H;

    private static final double ST = RS / P6.P;
    public static final double KW = BU / P6.H;
    public static final double WI = KI - KW;
    //    public static final double IZ = WI * P6.H;
    public static final double WZ = WI * P6.P;


    public final static FinalPointTransition A = fpt(pt(KA, UR));
    public final static FinalPointTransition A2 = fpt(pt(KA, DR));
    public final static FinalPointTransition A3 = fpt(pt(KA, UL));
    public final static FinalPointTransition B = fpt(pt(KB, UP));
    public final static FinalPointTransition B2 = fpt(pt(KB, DOWN));
    public final static FinalPointTransition B3 = fpt(pt(KB, LEFT));
    public final static FinalPointTransition F = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KC, UP));
    public final static FinalPointTransition D = C.append(pt(CD, RIGHT));
    public final static FinalPointTransition E = B.append(pt(BE, RIGHT));
    public final static FinalPointTransition G = F.append(pt(BE, UP));
    public final static FinalPointTransition G2 = B.append(pt(BE, LEFT));
    public final static FinalPointTransition I = fpt(pt(KI, RIGHT));
    public final static FinalPointTransition M = fpt(pt(KI, UP));
    public final static FinalPointTransition M2 = fpt(pt(JL, UP));
    public final static FinalPointTransition N = B.append(pt(BN, RIGHT));
    public final static FinalPointTransition N2 = B.append(pt(BN, LEFT));
    public final static FinalPointTransition N3 = B2.append(pt(BN, LEFT));
    public final static FinalPointTransition N4 = B2.append(pt(BN, RIGHT));
    public final static FinalPointTransition N5 = B3.append(pt(BN, UP));
    public final static FinalPointTransition N6 = B3.append(pt(BN, DOWN));
    public final static FinalPointTransition R = F.append(pt(BN, UP));
    public final static FinalPointTransition N8 = F.append(pt(BN, DOWN));
    //    public final static FinalPointTransition N2 = B.append(pt(KJ, RIGHT));
    public final static FinalPointTransition J = fpt(pt(KJ, UP));
    public final static FinalPointTransition J2 = fpt(pt(KJ, RIGHT));
    public final static FinalPointTransition L = J.append(pt(JL, RIGHT));
    public final static FinalPointTransition L2 = J2.append(pt(JL, UP));
    public final static FinalPointTransition Q = B.append(pt(BP, RIGHT));
    public final static FinalPointTransition Q2 = B.append(pt(BP, LEFT));
    public final static FinalPointTransition Q3 = B2.append(pt(BP, LEFT));
    public final static FinalPointTransition Q4 = B2.append(pt(BP, RIGHT));
    public final static FinalPointTransition Q5 = B3.append(pt(BP, UP));
    public final static FinalPointTransition Q6 = B3.append(pt(BP, DOWN));
    public final static FinalPointTransition Q7 = F.append(pt(BP, UP));
    public final static FinalPointTransition PQ = F.append(pt(BP, DOWN));
    public final static FinalPointTransition S = R.append(pt(RS, UP));
    public final static FinalPointTransition S2 = N.append(pt(RS, RIGHT));
    public final static FinalPointTransition T = S.append(pt(ST, LEFT));
    public final static FinalPointTransition T2 = S2.append(pt(ST, DOWN));
    public final static FinalPointTransition U = B.append(pt(BU, LEFT));
    public final static FinalPointTransition U2 = B.append(pt(BU, RIGHT));
    public final static FinalPointTransition X = U.append(pt(UX, DOWN));
    public final static FinalPointTransition X2 = U2.append(pt(UX, DOWN));
    public final static FinalPointTransition O = fpt(pt(KO, UL));
    public final static FinalPointTransition O2 = fpt(pt(KO, UR));
    public final static FinalPointTransition W = fpt(pt(KW, RIGHT));
    public final static FinalPointTransition W2 = M2.append(pt(KW * P6.P, LEFT));
    public final static FinalPointTransition Z = W.append(pt(WZ * P6.P, RIGHT), pt(WZ * P6.H, DOWN));
    public final static FinalPointTransition Z2 = W2.append(pt(WZ * P6.P, RIGHT), pt(WZ * P6.H, UP));
    public final static FinalPointTransition Q10 = A.append(pt(ST / H, DL));


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
                "RS = GR / 2.0",
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
                        perimeter(1.0, HOR).apply(K),
//                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K)
//                        perimeter(H, VER).apply(K),
//                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        PointsPath.of(C, D),
                        PointsPath.of(J, L),
                        PointsPath.of(J2, L2),
                        PointsPath.of(L2, B.append(pt(KJ, RIGHT))),
                        PointsPath.of(M, N),
                        PointsPath.of(S, T)
                )
                .addFullPaths(
                        gray,
                        PointsPath.of(A.append(pt(ST, LEFT)), A2.append(pt(ST, LEFT))),
                        PointsPath.of(B, D),
                        PointsPath.of(B.append(pt(JL, RIGHT)), B2.append(pt(JL, RIGHT))),
                        PointsPath.of(B, C.append(pt(CD, LEFT))),
                        PointsPath.of(Q3, N),
                        PointsPath.of(N3, Q),
                        PointsPath.of(N2, Q4),
                        PointsPath.of(Q2, N4),
                        PointsPath.of(N2, R),
                        PointsPath.of(N, N5),
                        PointsPath.of(K, E),
                        PointsPath.of(K, G)
                )
                .addCircleWithRadius(
                        gray,
                        Pair.of(A, KB),
                        Pair.of(A2, KB),
                        Pair.of(W, WZ)
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

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(O, X, B, X2, O2),
                PointsPath.of(Q4, Z, R, T, Q10, T2, N, Z2, Q5)
//                PointsPath.circle(X6, R, W, E, I),
//                PointsPath.circle(X7, X5, R2, W4, E2, I2),
//                PointsPath.circle(X4, L3, Q2, W3, U3)
        );
    }

}