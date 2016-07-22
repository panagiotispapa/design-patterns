package com.design.islamic.model.rect;

import com.design.common.*;
import com.design.common.RatioHelper.P8;
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

public class Tile6 {

    private static final double KA = 1.0;
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
    private static final double CL = BF / 2.0;
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


    public final static FinalPointTransition A = fpt(pt(KA, UL));
    public final static FinalPointTransition A2 = fpt(pt(KA, DL));
    public final static FinalPointTransition A3 = fpt(pt(KA, DR));
    public final static FinalPointTransition A4 = fpt(pt(KA, UR));
    public final static FinalPointTransition B = fpt(pt(KB, UP));
    public final static FinalPointTransition B2 = fpt(pt(KB, LEFT));
    public final static FinalPointTransition B3 = fpt(pt(KB, DOWN));
    public final static FinalPointTransition B4 = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KB, UL));
    public final static FinalPointTransition C2 = fpt(pt(KB, DL));
    public final static FinalPointTransition C3 = fpt(pt(KB, DR));
    public final static FinalPointTransition C4 = fpt(pt(KB, UR));
    public final static FinalPointTransition D = A.append(pt(CA, DOWN));
    public final static FinalPointTransition D2 = A2.append(pt(CA, UP));
    public final static FinalPointTransition D3 = A3.append(pt(CA, UP));
    public final static FinalPointTransition D4 = A4.append(pt(CA, DOWN));
    public final static FinalPointTransition E = A.append(pt(CA, RIGHT));
    public final static FinalPointTransition E2 = A2.append(pt(CA, RIGHT));
    public final static FinalPointTransition E3 = A3.append(pt(CA, LEFT));
    public final static FinalPointTransition E4 = A4.append(pt(CA, LEFT));
    public final static FinalPointTransition F = fpt(pt(CA, UP));
    public final static FinalPointTransition G = fpt(pt(CA, DOWN));
    public final static FinalPointTransition I = B.append(pt(BI, LEFT));
    public final static FinalPointTransition I2 = B3.append(pt(BI, LEFT));
    public final static FinalPointTransition I3 = B2.append(pt(BI, DOWN));
    public final static FinalPointTransition I4 = B4.append(pt(BI, DOWN));
    public final static FinalPointTransition J3 = B2.append(pt(BI, UP));
    public final static FinalPointTransition J4 = B4.append(pt(BI, UP));
    public final static FinalPointTransition J = B.append(pt(BI, RIGHT));
    public final static FinalPointTransition J2 = B3.append(pt(BI, RIGHT));
    public final static FinalPointTransition L = A.append(pt(AL, RIGHT));
    public final static FinalPointTransition L2 = A2.append(pt(AL, RIGHT));
    public final static FinalPointTransition N = I.append(pt(IN, RIGHT));
    public final static FinalPointTransition N2 = J.append(pt(IN, LEFT));
    public final static FinalPointTransition M = N.append(pt(NM, DOWN));
    public final static FinalPointTransition M2 = N2.append(pt(NM, DOWN));
    public final static FinalPointTransition Q = D.append(pt(DQ, DOWN));
    public final static FinalPointTransition Q2 = D4.append(pt(DQ, DOWN));
    public final static FinalPointTransition P = Q.append(pt(QP, RIGHT));
    public final static FinalPointTransition P2 = Q2.append(pt(QP, LEFT));
    public final static FinalPointTransition R = fpt(pt(KR, UP));
    public final static FinalPointTransition S = R.append(pt(RS, UP));
    public final static FinalPointTransition T = S.append(pt(ST, LEFT));
    public final static FinalPointTransition T2 = S.append(pt(ST, RIGHT));


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

        List<String> equations = Arrays.asList(
                "KB = h = KC",
                "CA = KA - KB = AD = AE",
                "BE = AB - AE",
                "BI / BF = BE / BG",
                "KF = AC",
                "AL = AC * h",
                "LE = AE - AL",
                "IM = BI * P8.P"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_06_design")
                .addEquations(equations)
                .addImportantVertexes(Tile6.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        gray,
                        PointsPath.of(C, B3, C4),
                        PointsPath.of(C, B4, C2),
                        PointsPath.of(C2, B, C3),
                        PointsPath.of(C4, B2, C3),
                        PointsPath.of(E2, J),
                        PointsPath.of(I2, E4),
                        PointsPath.of(E3, I),
                        PointsPath.of(E, J2),
                        PointsPath.of(D, I4),
                        PointsPath.of(J3, D3),
                        PointsPath.of(I3, D4),
                        PointsPath.of(D2, J4),
                        PointsPath.of(M, N),
                        PointsPath.of(P, Q),
                        PointsPath.of(T, S)
                )
                .addSinglePathsLines(
                        blue,
                        PointsPath.of(L, L2)
                )
                .addFullPaths(
                        gray,
                        PointsPath.of(B, D),
                        PointsPath.of(B2, E)
                )
                .addCircleWithRadius(
                        blue,
                        Pair.of(I, IM),
                        Pair.of(D, DP)
                )
                .addCirclesCentral(asList(
                        H
                ), gray)
                .addFullPaths(red, getFullPath())
                .withFontSize(14)
                ;
    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(E, P, T, M2, B, M, T2, P2, E4)
        );
    }


}