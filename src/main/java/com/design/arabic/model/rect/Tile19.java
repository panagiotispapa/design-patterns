package com.design.arabic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Payload;
import com.design.arabic.model.Rect;
import com.design.arabic.model.TileSupplier;
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

public class Tile19 {

    private static final double KA = 1;
    private static final double KC = KA * H;
    private static final double KD = KC / 2;
    private static final double AI = KD * H;
    private static final double DI = KD - AI;
    private static final double FJ = DI / H;

    public final static FinalPointTransition A = fpt(KA, DR);
    public final static FinalPointTransition B = fpt(KA, DL);
    public final static FinalPointTransition C = fpt(KC, DOWN);
    public final static FinalPointTransition D = fpt(KD, RIGHT);
    public final static FinalPointTransition D2 = fpt(KD, DOWN);
    public final static FinalPointTransition E = fpt(KC, RIGHT);
    public final static FinalPointTransition F = C.to(KD, RIGHT);
    public final static FinalPointTransition G = A.to(KD, UP);
//    public final static FinalPointTransition G2 = E.to(KD, UP);
    public final static FinalPointTransition I = A.to(AI, LEFT);
    public final static FinalPointTransition J = I.to(DI, UP);
//    public final static FinalPointTransition J2 = I.to(DI, UP);
    public final static FinalPointTransition L = I.to(AI, UP);
//    public final static FinalPointTransition L2 = I.to(EI, UP);
    public final static FinalPointTransition P = L.to(FJ, RIGHT);
//    public final static FinalPointTransition P2 = L2.to(DJ, RIGHT);

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_19",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_19_design")
                .addEquations(sequence(
                        "KC = h",
                        "KD = KA / 2"
                ))
                .addImportantVertexes(Tile19.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(KD, VER).apply(C),
                        perimeter(KD, VER).apply(A),
                        perimeter(KD, VER).apply(B),
                        perimeter(KD, VER).apply(E),
                        perimeter(KD, HOR).apply(A),
                        diagonals(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        Line.line(I, A)
                )
                .addFullPaths(red, getFullPath())
                ;
    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(K, D2, F, J , L, P, G, D, K)
//                Line.circle(C, G)
        );
    }

}