package com.design.arabic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.RatioHelper.P8;
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

public class Tile1 {

    private static final double KA = 1;
    private static final double KB = KA / 2;
    private static final double KC = KB / P8.H;
    private static final double BC = KC * P8.P;
    private static final double KD = KA * H;
    private static final double DE = BC;
    private static final double KP = (H - DE) / H;
    private static final double BP = KP - KB;

    public final static FinalPointTransition A = fpt(KA, DR);
    public final static FinalPointTransition B = fpt(KB, DR);
    public final static FinalPointTransition B2 = fpt(KB, DOWN);
    public final static FinalPointTransition B3 = fpt(KB, RIGHT);
    public final static FinalPointTransition D = fpt(KD, DOWN);
    public final static FinalPointTransition D2 = fpt(KD, RIGHT);
    public final static FinalPointTransition E = D.to(DE, RIGHT);
    public final static FinalPointTransition E2 = D2.to(DE, DOWN);
    public final static FinalPointTransition F = D.to(DE, LEFT);
    public final static FinalPointTransition C = B2.to(DE, RIGHT);
    public final static FinalPointTransition C2 = B3.to(DE, DOWN);
    public final static FinalPointTransition G = B2.to(DE, LEFT);
    public final static FinalPointTransition P = fpt(KP, DR);

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_01",
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

        Sequence<String> equations = sequence(
                "KF = h"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_01_design")
                .addEquations(equations)
                .addImportantVertexes(Tile1.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(KD, VER).apply(K),
                        perimeter(KP, HOR).apply(K),
                        perimeter(KB, HOR).apply(K),
                        perimeter(BP, HOR).apply(B)
                )
                .addFullPaths(red, getFullPath())
                ;
    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(E, C, C2, E2),
                Line.line(C, G)
        );
    }

}