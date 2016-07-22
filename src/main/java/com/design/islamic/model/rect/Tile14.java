package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
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

public class Tile14 {

    private static final double KA = 1.0;
    private static final double KC = H;
    private static final double KF = KC * H;
    private static final double KB = H;
    private static final double CA = KA - KB;
    private static final double AD = CA * H;
    private static final double BF = KB - KF;
    private static final double KJ = KF / P8.H;
    private static final double BL = KB - KJ;
    private static final double BM = BL * H;
    private static final double MP = BL - BM;
    private static final double PQ = MP / H;
    private static final double KV = KB - BM;
    private static final double BE = KB - AD;
    private static final double ZV = KV + KF;
    private static final double ZB = KB + KF;
    private static final double VW = ZV * (BE / ZB);
    private static final double SW = VW - BM;
    private static final double PX = BM / H;
    private static final double KU = KF * (BE / (KB + KF));

    public final static FinalPointTransition A = fpt(pt(KA, UL));
    public final static FinalPointTransition A2 = fpt(pt(KA, UR));
    public final static FinalPointTransition B = fpt(pt(KB, UP));
    public final static FinalPointTransition B2 = fpt(pt(KB, LEFT));
    public final static FinalPointTransition B3 = fpt(pt(KB, DOWN));
    public final static FinalPointTransition B4 = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KC, UL));
    public final static FinalPointTransition D = A.append(pt(AD, DOWN));
    public final static FinalPointTransition E = A.append(pt(AD, RIGHT));
    public final static FinalPointTransition E2 = A2.append(pt(AD, LEFT));
    public final static FinalPointTransition F = fpt(pt(KF, UP));
    public final static FinalPointTransition F2 = fpt(pt(KF, RIGHT));
    public final static FinalPointTransition Z = fpt(pt(KF, DOWN));
    public final static FinalPointTransition F4 = fpt(pt(KF, LEFT));
    public final static FinalPointTransition G = fpt(pt(KF, UL));
    public final static FinalPointTransition I = A.append(pt(AD, DR));
    public final static FinalPointTransition I2 = A2.append(pt(AD, DL));
    public final static FinalPointTransition J = F.append(pt(BF, LEFT));
    public final static FinalPointTransition L = fpt(pt(KJ, UP));
    public final static FinalPointTransition L9 = fpt(pt(KJ, LEFT));

    public final static FinalPointTransition M = B.append(pt(BM, LEFT));
    public final static FinalPointTransition M3 = B.append(pt(BM, RIGHT));
    public final static FinalPointTransition M2 = B3.append(pt(BM, LEFT));
    public final static FinalPointTransition N = A.append(pt(BM, DOWN));
    public final static FinalPointTransition N2 = A2.append(pt(BM, DOWN));


    public final static FinalPointTransition P = B.append(pt(BL, LEFT));
    public final static FinalPointTransition O = P.append(pt(BM, DOWN));
    public final static FinalPointTransition X = O.append(pt(BM, LEFT));
    public final static FinalPointTransition O2 = X.append(pt(BM, LEFT));
    public final static FinalPointTransition X2 = O2.append(pt(BM, DOWN));

    public final static FinalPointTransition L3 = B.append(pt(BL, RIGHT));
    public final static FinalPointTransition O3 = L3.append(pt(BM, DOWN));
    public final static FinalPointTransition X3 = O3.append(pt(BM, RIGHT));
    public final static FinalPointTransition X4 = X3.append(pt(BM, RIGHT), pt(BM, DOWN));

    public final static FinalPointTransition L4 = B2.append(pt(BL, DOWN));
    public final static FinalPointTransition L5 = B2.append(pt(BL, UP));
    public final static FinalPointTransition Q = M.append(pt(MP, DOWN));
    public final static FinalPointTransition Q2 = M3.append(pt(MP, DOWN));
    public final static FinalPointTransition S = Q.append(pt(PQ, DOWN));
    public final static FinalPointTransition T = Q.append(pt(MP, DR));
    public final static FinalPointTransition R = Q.append(pt(2 * MP, DR));
    public final static FinalPointTransition V = R.append(pt(MP, RIGHT));
    public final static FinalPointTransition S2 = V.append(pt(BM, RIGHT));
    public final static FinalPointTransition W = V.append(pt(VW, LEFT));
    public final static FinalPointTransition W2 = S.append(pt(SW, DOWN));
    public final static FinalPointTransition W3 = S2.append(pt(SW, DOWN));
    public final static FinalPointTransition W4 = V.append(pt(VW, RIGHT));
    public final static FinalPointTransition R2 = V.append(pt(MP, RIGHT));
    public final static FinalPointTransition X5 = L.append(pt(2 * PX, DL));
    public final static FinalPointTransition X6 = L.append(pt(2 * PX, DR));
    public final static FinalPointTransition X7 = L5.append(pt(2 * PX, UR));
    public final static FinalPointTransition X8 = L9.append(pt(2 * PX, UR));
    public final static FinalPointTransition U = fpt(pt(KU, UP));
    public final static FinalPointTransition U2 = fpt(pt(KU, UR));
    public final static FinalPointTransition U3 = fpt(pt(KU, UL));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_14",
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
                "KB = h = KC",
                "CA = KA - KB = AE = AD",
                "AD = CA * h",
                "KF = KC * h",
                "KJ = KF / P8.h",
                "BL = KB - KJ",
                "BM = BL * h = AN",
                "MP = BL - BM",
                "PQ = MP / H",
                "KV = KB - BM",
                "VW / ZV = BE / ZB",
                "PO = OX = AN = BM",
                "PX = BM / H",
                "KU / KF = BE / (KF + KB)"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_14_design")
                .addEquations(equations)
                .addImportantVertexes(Tile14.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        PointsPath.of(P, O),
                        PointsPath.of(X2, O2)

                )
                .addFullPaths(
                        gray,
                        PointsPath.of(F2, D),
                        PointsPath.of(Z, E),
                        PointsPath.of(D, I, E),
                        PointsPath.of(L4, L3),
                        PointsPath.of(L5, P),
                        PointsPath.of(M, M2),
                        PointsPath.of(N, N2)
                )
                .addCircleWithRadius(
                        blue,
                        Pair.of(A, AD),
                        Pair.of(B, BL)
                )
                .addCirclesCentral(asList(
                        H
                ), gray)
                .addCirclesCentral(asList(
                        KJ
                ), blue)
                .addFullPaths(red, getFullPath())
                .withFontSize(14)
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(X8, X2, P, Q, W2, U2),
                PointsPath.of(X6, R, W, E, I),
                PointsPath.of(X7, X5, R2, W4, E2, I2),
                PointsPath.of(X4, L3, Q2, W3, U3)
        );
    }

}