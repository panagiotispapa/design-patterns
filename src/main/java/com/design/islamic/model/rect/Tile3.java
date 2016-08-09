package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.*;
import com.googlecode.totallylazy.Sequence;

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

public class Tile3 {

    private static final double KA = 1.0;
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

    public final static FinalPointTransition A = fpt(pt(KA, UL));
    public final static FinalPointTransition A2 = fpt(pt(KA, DR));
    public final static FinalPointTransition B = fpt(pt(KB, UP));
    public final static FinalPointTransition B2 = fpt(pt(KB, LEFT));
    public final static FinalPointTransition B3 = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition B4 = fpt(pt(KB, DOWN));
    public final static FinalPointTransition C = fpt(pt(KC, UL));
    public final static FinalPointTransition F = fpt(pt(KF, UP));
    public final static FinalPointTransition F2 = fpt(pt(KF, RIGHT));
    public final static FinalPointTransition F3 = fpt(pt(KF, LEFT));
    public final static FinalPointTransition F4 = fpt(pt(KF, DOWN));
    public final static FinalPointTransition G = F.append(pt(BF, LEFT));
    public final static FinalPointTransition G2 = F4.append(pt(BF, LEFT));
    public final static FinalPointTransition I6 = G2.append(pt(GN, DOWN));
    public final static FinalPointTransition I1 = F2.append(pt(BF, UP));
    public final static FinalPointTransition I3 = F3.append(pt(BF, UP));
    public final static FinalPointTransition I5 = I3.append(pt(GN, UL));
    public final static FinalPointTransition M = B.append(pt(BF, LEFT));
    public final static FinalPointTransition D = A.append(pt(CA, RIGHT));
    public final static FinalPointTransition E = A.append(pt(CA, DOWN));
    public final static FinalPointTransition D2 = A2.append(pt(CA, UP));
    public final static FinalPointTransition E2 = A2.append(pt(CA, LEFT));
    public final static FinalPointTransition N = M.append(pt(MN, DOWN));
    public final static FinalPointTransition P = G.append(pt(GN, UL));
    public final static FinalPointTransition Q = fpt(pt(KQ, UP));
    public final static FinalPointTransition Q2 = fpt(pt(KQ, LEFT));
    public final static FinalPointTransition R = fpt(pt(KR, UP));
    public final static FinalPointTransition S = Q.append(pt(QR, LEFT));
    public final static FinalPointTransition T = Q.append(pt(QR, RIGHT));
    public final static FinalPointTransition T2 = Q2.append(pt(QR, DOWN));
    public final static FinalPointTransition I2 = I1.append(pt(GN, RIGHT));


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
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K)
                )
                .addFullPaths(
                        gray,
                        PointsPath.of(B, E),
                        PointsPath.of(B2, D),
                        PointsPath.of(D2, D),
                        PointsPath.of(E2, E)
                )
                .addFullPaths(
                        gray,
                        Rect.diagonalVertical(H).apply(fpt(pt(BF, LEFT))),
                        Rect.diagonalVertical(H).apply(fpt(pt(BF, RIGHT)))
                )
                .addCirclesCentral(gray, H)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(E, P, T, I2, B3),
                PointsPath.of(D, I5, T2, I6, B4)
        );
    }

}