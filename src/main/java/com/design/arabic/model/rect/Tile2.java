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
import static com.design.arabic.model.Rect.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile2 {

    private static final double KA = 1;
    private static final double KC = H;
    private static final double KB = H;
    private static final double KD = H * KB;
    private static final double KL = 1 - H;
    private static final double KF = KL;
    private static final double KG = KA + KF;
    private static final double FG = KA;

    public final static FinalPointTransition A = fpt(KA, DR);
    public final static FinalPointTransition B = fpt(KB, UR);
    public final static FinalPointTransition P = fpt(KB, DR);
    public final static FinalPointTransition C = fpt(KC, LEFT);
    public final static FinalPointTransition C2 = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KD, RIGHT);
    public final static FinalPointTransition E = fpt(KL, UP);
    public final static FinalPointTransition F = fpt(KF, RIGHT);
    public final static FinalPointTransition G = fpt(KG, RIGHT);
    public final static FinalPointTransition I = G.to(FG, DL);
    public final static FinalPointTransition J = G.to(FG, UL);
    public final static FinalPointTransition L = fpt(KF, DR);
    public final static FinalPointTransition M = fpt(H, DOWN);
    public final static FinalPointTransition N = I.to(H, UP);


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
                "FG = IN / h = 1",
                "KG = FG + KF = 1 + KF"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_02_design")
                .addEquations(equations)
                .addImportantVertexes(Tile2.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K),
                        perimeter(FG, HOR).apply(G),
                        perimeter(FG, VER).apply(G)
                )
                .addFullPaths(
                        gray,
                        Line.line(B, C, P)
                )
                .addCirclesCentral(gray, H)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(I, F, J),
                Line.line(M, L, C2)
        );
    }

}