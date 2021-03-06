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

public class Tile4 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double KD = H * KB;
    private static final double BD = KB - KD;


    public final static FinalPointTransition A = fpt(KA, UL);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition C = fpt(KB, UL);
    public final static FinalPointTransition C2 = fpt(KB, UR);
    public final static FinalPointTransition D = fpt(KD, UP);
    public final static FinalPointTransition E = D.to(BD, RIGHT);
    public final static FinalPointTransition F = D.to(BD, LEFT);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_04",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_04_design")
                .addEquations(sequence(
                        "KB = h = KC",
                        "KD = KD * h"
                ))
                .addImportantVertexes(Tile4.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K)
                )
                .addCirclesCentral(gray,
                        H
                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(C, F, B, E, C2)
        );
    }

}