package com.design.arabic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.RatioHelper.P8;
import com.design.common.model.Style;
import com.design.arabic.model.*;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.CircleInstruction.circleInstruction;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.arabic.model.Rect.Vertex.*;
import static com.design.arabic.model.Rect.diagonals;
import static com.design.arabic.model.Rect.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile14 {

    private static final double KA = 1;
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

    public final static FinalPointTransition A = fpt(KA, UL);
    public final static FinalPointTransition A2 = fpt(KA, UR);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition B2 = fpt(KB, LEFT);
    public final static FinalPointTransition B3 = fpt(KB, DOWN);
    public final static FinalPointTransition B4 = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, UL);
    public final static FinalPointTransition D = A.to(AD, DOWN);
    public final static FinalPointTransition E = A.to(AD, RIGHT);
    public final static FinalPointTransition E2 = A2.to(AD, LEFT);
    public final static FinalPointTransition F = fpt(KF, UP);
    public final static FinalPointTransition F2 = fpt(KF, RIGHT);
    public final static FinalPointTransition Z = fpt(KF, DOWN);
    public final static FinalPointTransition F4 = fpt(KF, LEFT);
    public final static FinalPointTransition G = fpt(KF, UL);
    public final static FinalPointTransition I = A.to(AD, DR);
    public final static FinalPointTransition I2 = A2.to(AD, DL);
    public final static FinalPointTransition J = F.to(BF, LEFT);
    public final static FinalPointTransition L = fpt(KJ, UP);
    public final static FinalPointTransition L9 = fpt(KJ, LEFT);

    public final static FinalPointTransition M = B.to(BM, LEFT);
    public final static FinalPointTransition M3 = B.to(BM, RIGHT);
    public final static FinalPointTransition M2 = B3.to(BM, LEFT);
    public final static FinalPointTransition N = A.to(BM, DOWN);
    public final static FinalPointTransition N2 = A2.to(BM, DOWN);


    public final static FinalPointTransition P = B.to(BL, LEFT);
    public final static FinalPointTransition O = P.to(BM, DOWN);
    public final static FinalPointTransition X = O.to(BM, LEFT);
    public final static FinalPointTransition O2 = X.to(BM, LEFT);
    public final static FinalPointTransition X2 = O2.to(BM, DOWN);

    public final static FinalPointTransition L3 = B.to(BL, RIGHT);
    public final static FinalPointTransition O3 = L3.to(BM, DOWN);
    public final static FinalPointTransition X3 = O3.to(BM, RIGHT);
    public final static FinalPointTransition X4 = X3.to(BM, RIGHT, BM, DOWN);

    public final static FinalPointTransition L4 = B2.to(BL, DOWN);
    public final static FinalPointTransition L5 = B2.to(BL, UP);
    public final static FinalPointTransition Q = M.to(MP, DOWN);
    public final static FinalPointTransition Q2 = M3.to(MP, DOWN);
    public final static FinalPointTransition S = Q.to(PQ, DOWN);
    public final static FinalPointTransition T = Q.to(MP, DR);
    public final static FinalPointTransition R = Q.to(2 * MP, DR);
    public final static FinalPointTransition V = R.to(MP, RIGHT);
    public final static FinalPointTransition S2 = V.to(BM, RIGHT);
    public final static FinalPointTransition W = V.to(VW, LEFT);
    public final static FinalPointTransition W2 = S.to(SW, DOWN);
    public final static FinalPointTransition W3 = S2.to(SW, DOWN);
    public final static FinalPointTransition W4 = V.to(VW, RIGHT);
    public final static FinalPointTransition R2 = V.to(MP, RIGHT);
    public final static FinalPointTransition X5 = L.to(2 * PX, DL);
    public final static FinalPointTransition X6 = L.to(2 * PX, DR);
    public final static FinalPointTransition X7 = L5.to(2 * PX, UR);
    public final static FinalPointTransition X8 = L9.to(2 * PX, UR);
    public final static FinalPointTransition U = fpt(KU, UP);
    public final static FinalPointTransition U2 = fpt(KU, UR);
    public final static FinalPointTransition U3 = fpt(KU, UL);


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

        Sequence<String> equations = sequence(
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

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_14_design")
                .addEquations(equations)
                .addImportantVertexes(Tile14.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        line(P, O),
                        line(X2, O2)

                )
                .addFullPaths(
                        gray,
                        line(F2, D),
                        line(Z, E),
                        line(D, I, E),
                        line(L4, L3),
                        line(L5, P),
                        line(M, M2),
                        line(N, N2)
                )
                .addCircleWithRadius(
                        blue,
                        circleInstruction(A, AD),
                        circleInstruction(B, BL)
                )
                .addCirclesCentral(gray, H)
                .addCirclesCentral(blue, KJ)
                .addFullPaths(red, getFullPath())
                .withFontSize(14)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(X8, X2, P, Q, W2, U2),
                line(X6, R, W, E, I),
                line(X7, X5, R2, W4, E2, I2),
                line(X4, L3, Q2, W3, U3)
        );
    }

}