package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.RatioHelper.P12;
import com.design.common.RatioHelper.P6;
import com.design.common.model.Style;
import com.design.islamic.model.*;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.diagonals;
import static com.design.islamic.model.Rect.perimeter;
import static java.util.Arrays.asList;

public class Tile17 {

    private static final double KA = 1.0;
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
    private static final double BQ = BT / 2.0;
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


    public final static FinalPointTransition A = fpt(pt(KA, UR));
    public final static FinalPointTransition A2 = fpt(pt(KA, DR));
    public final static FinalPointTransition A3 = fpt(pt(KA, UL));
    public final static FinalPointTransition A4 = fpt(pt(KA, DL));
    public final static FinalPointTransition B = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition B2 = fpt(pt(KB, DOWN));
    public final static FinalPointTransition B3 = fpt(pt(KB, LEFT));
    public final static FinalPointTransition C = fpt(pt(KC, RIGHT));
    public final static FinalPointTransition C4 = fpt(pt(KC, LEFT));
    public final static FinalPointTransition C2 = A.append(pt(CB, LEFT));
    public final static FinalPointTransition C3 = A2.append(pt(CB, LEFT));
    public final static FinalPointTransition D = C.append(pt(CD, UP));
    public final static FinalPointTransition D2 = C4.append(pt(CD, UP));
    public final static FinalPointTransition E = B.append(pt(BE, UP));
    //    public final static FinalPointTransition E4 = B.append(pt(BE, DOWN));
    public final static FinalPointTransition F = fpt(pt(KB, UP));
    public final static FinalPointTransition E2 = F.append(pt(BE, RIGHT));
    //    public final static FinalPointTransition E3 = F.append(pt(BE, LEFT));
    public final static FinalPointTransition G = A.append(pt(AG, DOWN));
    public final static FinalPointTransition I = fpt(pt(KI, UP));
    public final static FinalPointTransition J = fpt(pt(KJ, LEFT));
    public final static FinalPointTransition I2 = J.append(pt(KI * P6.P, DOWN));
    public final static FinalPointTransition I3 = fpt(pt(KI, RIGHT));
    public final static FinalPointTransition L = J.append(pt(JL, RIGHT));
    public final static FinalPointTransition M = fpt(pt(KM, UP));
    public final static FinalPointTransition N = F.append(pt(FN, LEFT));
    public final static FinalPointTransition N2 = B2.append(pt(FN, RIGHT));
    public final static FinalPointTransition N3 = F.append(pt(FN, RIGHT));
    public final static FinalPointTransition N4 = B.append(pt(FN, UP));
    public final static FinalPointTransition P = B2.append(pt(B2P, LEFT));
    public final static FinalPointTransition P2 = F.append(pt(B2P, RIGHT));
    public final static FinalPointTransition P3 = B2.append(pt(B2P, RIGHT));
    public final static FinalPointTransition P4 = B3.append(pt(B2P, DOWN));
    public final static FinalPointTransition T = B.append(pt(BI3, UP));
    public final static FinalPointTransition T2 = B2.append(pt(BI3, LEFT));
    public final static FinalPointTransition T3 = F.append(pt(BI3, RIGHT));
    public final static FinalPointTransition T4 = F.append(pt(BI3, LEFT));
    public final static FinalPointTransition T5 = B3.append(pt(BI3, UP));
    public final static FinalPointTransition T6 = B2.append(pt(BI3, RIGHT));
    public final static FinalPointTransition Q = B.append(pt(BQ, UP));
    public final static FinalPointTransition Q2 = B.append(pt(BQ, LEFT));
    public final static FinalPointTransition S = Q.append(pt(BQ, LEFT));
    public final static FinalPointTransition V = Q.append(pt(CB, LEFT));
    public final static FinalPointTransition X = S.append(pt(SX, DR));

    public final static FinalPointTransition Z = S.append(pt(SZ, DL));
    public final static FinalPointTransition W = Z.append(pt(ZW, DOWN));
    public final static FinalPointTransition U = B.append(pt(BZ, LEFT));
    public final static FinalPointTransition X2 = U.append(pt(UX, RIGHT));
    public final static FinalPointTransition X3 = fpt(pt(KX2, UP));
    public final static FinalPointTransition X4 = fpt(pt(KX2 * P6.H, UP), pt(KX2 * P6.P, RIGHT));
    public final static FinalPointTransition X5 = fpt(pt(KX2 * P6.H, UP), pt(KX2 * P6.P, LEFT));
    public final static FinalPointTransition X6 = fpt(pt(KX2 * P6.H, LEFT), pt(KX2 * P6.P, UP));
    public final static FinalPointTransition L2 = fpt(pt(KL, RIGHT));
    public final static FinalPointTransition L3 = fpt(pt(KL3, DR));
    public final static FinalPointTransition L5 = fpt(pt(KL3, UR));
    public final static FinalPointTransition L6 = fpt(pt(KL6 * P6.H, RIGHT), pt(KL6 * P6.P, UP));
    public final static FinalPointTransition L7 = fpt(pt(KL7, UR));
    public final static FinalPointTransition Y = B.append(pt(BZ, UP));
    public final static FinalPointTransition Y2 = A.append(pt(AY2, LEFT));
    public final static FinalPointTransition Y3 = F.append(pt(BZ, RIGHT));
    public final static FinalPointTransition Y4 = A.append(pt(AY2, DOWN));
    public final static FinalPointTransition Y5 = A4.append(pt(AY2, UP));
    public final static FinalPointTransition Y7 = A4.append(pt(AY2, RIGHT));
    public final static FinalPointTransition Y6 = B2.append(pt(BZ, LEFT));
    public final static FinalPointTransition P5 = P.append(pt(PP5 * P12.P, RIGHT), pt(PP5 * P12.H, UP));
    public final static FinalPointTransition P7 = P4.append(pt(PP5 * P12.P, UP), pt(PP5 * P12.H, RIGHT));
    public final static FinalPointTransition L8 = fpt(pt(KL7 * P12.H, LEFT), pt(KL7 * P12.P, DOWN));
    public final static FinalPointTransition L9 = fpt(pt(KL7 * P12.H, DOWN), pt(KL7 * P12.P, LEFT));


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

        List<String> equations = Arrays.asList(
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
                "BQ = BT / 2.0",
                "BQ = BS",
                "",
                ""
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_17_design")
                .addEquations(equations)
                .addImportantVertexes(Tile17.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
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
                        PointsPath.of(C, D),
                        PointsPath.of(J, I2),
                        PointsPath.of(K, M),
                        PointsPath.of(L, M),
                        PointsPath.of(S, Q),
                        PointsPath.of(S, Q2),
                        PointsPath.of(Z, W),
                        PointsPath.of(Z, B),
                        PointsPath.of(X, U),
                        PointsPath.of(T, T3)

                )
                .addSinglePathsLines(
                        gray,
                        PointsPath.of(D2, D, B2, D2)
//                        PointsPath.of(E, E2)

                )
                .addFullPaths(
                        gray,
                        PointsPath.of(K, E),
                        PointsPath.of(K, E2),
                        PointsPath.of(D2, F, D),
                        PointsPath.of(C3, C2),
                        PointsPath.of(T, T2),
                        PointsPath.of(P, N),
                        PointsPath.of(P2, N2),
                        PointsPath.of(P3, N3),
                        PointsPath.of(Y, Y2),
                        PointsPath.of(Y3, Y4)
                )
                .addCircleWithRadius(
                        gray,
                        Pair.of(B, BZ),
                        Pair.of(F, BZ),
                        Pair.of(B2, BZ),
                        Pair.of(B3, BZ),
                        Pair.of(U, UX)
//                        Pair.of(Y, YY2)
                )
                .addCirclesCentral(asList(
                        H,
                        KI,
                        KX2
                ), gray)
                .addFullPaths(red, getFullPath())
                .withFontSize(14)
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(X6, X5, X3, X4),
                PointsPath.of(N3, L7, N4),
                PointsPath.of(Y5, P5, L8, T5),
                PointsPath.of(Y7, P7, L9, T6)


        );
    }

}