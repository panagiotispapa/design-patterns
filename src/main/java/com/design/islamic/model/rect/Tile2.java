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
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile2 {

    private static final double KA = 1.0;
    private static final double KC = H;
    private static final double KB = H;
    private static final double KD = H * KB;
    private static final double KL = 1.0 - H;
    private static final double KF = KL;
    private static final double KG = KA + KF;
    private static final double FG = KA;

    public final static FinalPointTransition A = fpt(pt(KA, DR));
    public final static FinalPointTransition B = fpt(pt(KB, UR));
    public final static FinalPointTransition P = fpt(pt(KB, DR));
    public final static FinalPointTransition C = fpt(pt(KC, LEFT));
    public final static FinalPointTransition C2 = fpt(pt(KC, RIGHT));
    public final static FinalPointTransition D = fpt(pt(KD, RIGHT));
    public final static FinalPointTransition E = fpt(pt(KL, UP));
    public final static FinalPointTransition F = fpt(pt(KF, RIGHT));
    public final static FinalPointTransition G = fpt(pt(KG, RIGHT));
    public final static FinalPointTransition I = G.append(pt(FG, DL));
    public final static FinalPointTransition J = G.append(pt(FG, UL));
    public final static FinalPointTransition L = fpt(pt(KF, DR));
    public final static FinalPointTransition M = fpt(pt(H, DOWN));
    public final static FinalPointTransition N = I.append(pt(H, UP));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_02",
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
                "KC = h",
                "KB = h",
                "KD = h * KB",
                "BD = KD",
                "AM = AL",
                "KL = KA - AL = KA - AM = 1 - h",
                "IN = h",
                "FG = IN / h = 1.0",
                "KG = FG + KF = 1.0 + KF"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_02_design")
                .addEquations(equations)
                .addImportantVertexes(Tile2.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K),
                        perimeter(FG, HOR).apply(G),
                        perimeter(FG, VER).apply(G)
                )
                .addFullPaths(
                        gray,
                        PointsPath.of(B, C, P)
                )
                .addCirclesCentral(gray, H)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(I, F, J),
                PointsPath.of(M, L, C2)
        );
    }

}