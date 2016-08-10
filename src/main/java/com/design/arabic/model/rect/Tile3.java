package com.design.arabic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
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

public class Tile3 {

    private static final double KA = 1;
    private static final double KC = H;
    private static final double KB = H;
    private static final double CA = KA - KB;
    private static final double AE = CA;
    private static final double KF = KC * H;
    private static final double BF = KB - KF;
    private static final double MB = BF;
    private static final double MG = BF;
    private static final double AB = H;
    private static final double MN = MB * (AE / AB);
    private static final double GN = MG - MN;
    private static final double KQ = BF;
    private static final double KR = KB - 2 * BF;
    private static final double QR = KR - KQ;

    public final static FinalPointTransition A = fpt(KA, UL);
    public final static FinalPointTransition A2 = fpt(KA, DR);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition B2 = fpt(KB, LEFT);
    public final static FinalPointTransition B3 = fpt(KB, RIGHT);
    public final static FinalPointTransition B4 = fpt(KB, DOWN);
    public final static FinalPointTransition C = fpt(KC, UL);
    public final static FinalPointTransition F = fpt(KF, UP);
    public final static FinalPointTransition F2 = fpt(KF, RIGHT);
    public final static FinalPointTransition F3 = fpt(KF, LEFT);
    public final static FinalPointTransition F4 = fpt(KF, DOWN);
    public final static FinalPointTransition G = F.to(BF, LEFT);
    public final static FinalPointTransition G2 = F4.to(BF, LEFT);
    public final static FinalPointTransition I6 = G2.to(GN, DOWN);
    public final static FinalPointTransition I1 = F2.to(BF, UP);
    public final static FinalPointTransition I3 = F3.to(BF, UP);
    public final static FinalPointTransition I5 = I3.to(GN, UL);
    public final static FinalPointTransition M = B.to(BF, LEFT);
    public final static FinalPointTransition D = A.to(CA, RIGHT);
    public final static FinalPointTransition E = A.to(CA, DOWN);
    public final static FinalPointTransition D2 = A2.to(CA, UP);
    public final static FinalPointTransition E2 = A2.to(CA, LEFT);
    public final static FinalPointTransition N = M.to(MN, DOWN);
    public final static FinalPointTransition P = G.to(GN, UL);
    public final static FinalPointTransition Q = fpt(KQ, UP);
    public final static FinalPointTransition Q2 = fpt(KQ, LEFT);
    public final static FinalPointTransition R = fpt(KR, UP);
    public final static FinalPointTransition S = Q.to(QR, LEFT);
    public final static FinalPointTransition T = Q.to(QR, RIGHT);
    public final static FinalPointTransition T2 = Q2.to(QR, DOWN);
    public final static FinalPointTransition I2 = I1.to(GN, RIGHT);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_03",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();

        Sequence<String> equations = sequence(
                "KB = h = KC",
                "CA = KA - KB = AE = AD",
                "KF = KC * H",
                "BF = KB - KF = FG = MG = MB",
                "MN / MB = AE / AB",
                "GN = GP",
                "KR = KB - 2 * BF",
                "KQ = MB"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_03_design")
                .addEquations(equations)
                .addImportantVertexes(Tile3.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K)
                )
                .addFullPaths(
                        gray,
                        Line.line(B, E),
                        Line.line(B2, D),
                        Line.line(D2, D),
                        Line.line(E2, E)
                )
                .addFullPaths(
                        gray,
                        Rect.diagonalVertical(H).apply(fpt(BF, LEFT)),
                        Rect.diagonalVertical(H).apply(fpt(BF, RIGHT))
                )
                .addCirclesCentral(gray, H)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(E, P, T, I2, B3),
                Line.line(D, I5, T2, I6, B4)
        );
    }

}