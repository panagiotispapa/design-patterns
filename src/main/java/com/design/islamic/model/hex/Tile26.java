package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.RatioHelper.P12;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Payload;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.diagonals;
import static com.design.islamic.model.Hex.perimeter;
import static java.util.Arrays.asList;

public class Tile26 {
//https://s-media-cache-ak0.pinimg.com/236x/03/7a/b7/037ab7b5f11620dbad8b8e1012438547.jpg

    private static double KA = 1.0;
    private static double KB = KA * H;
    private static double KC = KA * P;
    private static double KD = KC / H;
    private static double KF = KD / H;
    private static double DF = KF * P;
    private static double DB = KB - KD;
    private static double AB = KA / 2.0;
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
    private static double LM = DL / 2.0;
    private static double DM = DL - LM;
    private static double DN = 2 * DM * H;
    private static double DQ = DI * H;
    private static double KR = KE / 2.0;
    private static double KS = KR * P12.H;
    private static double SR = KR * P12.P;
    private static double TR = SR / P;
    private static double TS = TR * H;
    private static double KT = KS + TS;

    public final static FinalPointTransition A = fpt(pt(KA, DR_H));
    public static final FinalPointTransition T6 = A.append(pt(KT * P12.H, UP), pt(KT * P12.P, LEFT));
    public static final FinalPointTransition T5 = A.append(pt(KT * P12.H, UP), pt(KT * P12.P, RIGHT));
    public static final FinalPointTransition T4 = A.append(pt(KT * P12.H, UL_V), pt(KT * P12.P, UR_H));
    public static final FinalPointTransition T3 = A.append(pt(KT * P12.H, UL_V), pt(KT * P12.P, DL_H));
    public final static FinalPointTransition A2 = fpt(pt(KA, DL_H));
    public final static FinalPointTransition B = fpt(pt(KB, DOWN));
    public final static FinalPointTransition C = fpt(pt(KC, DL_H));
    public final static FinalPointTransition C2 = fpt(pt(KC, DR_H));
    public final static FinalPointTransition D = fpt(pt(KD, DOWN));
    public final static FinalPointTransition D2 = fpt(pt(KD, DR_V));
    public final static FinalPointTransition E = A.append(pt(AE, UL_H));
    public final static FinalPointTransition E2 = A2.append(pt(AE, UR_H));
    public final static FinalPointTransition F = C.append(pt(CF, DL_H));
    public final static FinalPointTransition F2 = C2.append(pt(CF, DR_H));
    public final static FinalPointTransition G = B.append(pt(BG, RIGHT));
    public final static FinalPointTransition G2 = B.append(pt(BG, LEFT));
    public final static FinalPointTransition I = A.append(pt(AG, UL_V));
    public final static FinalPointTransition I2 = D.append(pt(DI, UR_V));
    public final static FinalPointTransition L = D.append(pt(DL, LEFT));
    public final static FinalPointTransition M = D.append(pt(DM, LEFT));
    public final static FinalPointTransition M2 = D.append(pt(DM, UL_H));
    public final static FinalPointTransition N = D.append(pt(DN, UL_V));
    public final static FinalPointTransition Q = D.append(pt(DQ, UR_H));
    public final static FinalPointTransition Q2 = D.append(pt(DQ, UL_H));
    public final static FinalPointTransition R = fpt(pt(KR, DR_H));
    public final static FinalPointTransition R2 = fpt(pt(KR, DOWN));
    public final static FinalPointTransition R3 = fpt(pt(KR, DL_H));
    public final static FinalPointTransition S = fpt(pt(KS * P12.H, DOWN), pt(KS * P12.P, RIGHT));
    public final static FinalPointTransition T = fpt(pt(KT * P12.H, DOWN), pt(KT * P12.P, RIGHT));
    public final static FinalPointTransition T2 = fpt(pt(KT * P12.H, DOWN), pt(KT * P12.P, LEFT));


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

        List<String> equations = asList(
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
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),


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
                        perimeter(KT * P12.P, VER).apply(A.append(pt(KT * P12.H, UL_V))),
                        perimeter(KT * P12.P, HOR).apply(A.append(pt(KT * P12.H, UL_V)))
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(AE, HOR).apply(A),
                        perimeter(AE, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        PointsPath.of(A, C),
                        PointsPath.of(A2, C2),
                        PointsPath.of(F, F2),
                        PointsPath.of(A2, L),
                        PointsPath.of(K, B),
                        PointsPath.of(N, M),
                        PointsPath.of(R, R2, R3)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(12)
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(M, N, M2, D.append(pt(DN, UP)), D.append(pt(DM, UR_H)), D.append(pt(DN, UR_V)), D.append(pt(DM, RIGHT)), D.append(pt(DN, DR_V)), D.append(pt(DM, DR_H)), D.append(pt(DN, DOWN)), D.append(pt(DM, DL_H)), D.append(pt(DN, DL_V)), D.append(pt(DM, LEFT))),
                PointsPath.of(C, N),
                PointsPath.of(G, E2),
                PointsPath.of(G2, E),
                PointsPath.of(F2, F),
                PointsPath.of(D.append(pt(DN, UR_V)), C2),
                PointsPath.of(D.append(pt(DN, DR_V)), A),
                PointsPath.of(D.append(pt(DN, DL_V)), A2),
                PointsPath.of(D.append(pt(DN, UP)), K),
                PointsPath.of(D.append(pt(DN, DOWN)), D.append(pt(CD, DOWN))),
                PointsPath.of(R3, T2, R2, T, R),
                PointsPath.of(T, Q),
                PointsPath.of(T2, Q2),
                PointsPath.of(K, A),
                PointsPath.of(K, A2),
                PointsPath.of(A, A2),
                PointsPath.of(I, D.append(pt(DI, UR_V)), D.append(pt(DI, UP)), D.append(pt(DI, UL_V)), D.append(pt(DI, DL_V)), D.append(pt(DI, DOWN)), I),
                PointsPath.of(A.append(pt(KR, UR_H)), T5, A.append(pt(KR, UP)), T6, A.append(pt(KR, UL_H)), T4, A.append(pt(KR, UL_V)), T3, A.append(pt(KR, LEFT))),
                PointsPath.of(T3, D.append(pt(DQ, DR_H))),
                PointsPath.of(T4, D.append(pt(DQ, RIGHT))),
                PointsPath.of(T6, D2.append(pt(DQ, DL_H))),
                PointsPath.of(T5, D2.append(pt(DQ, DR_H)))

        );
    }
}