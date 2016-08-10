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
import static com.design.arabic.model.Rect.ALL_VERTEX_INDEXES;
import static com.design.arabic.model.Rect.Vertex.*;
import static com.design.arabic.model.Rect.diagonals;
import static com.design.arabic.model.Rect.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile9 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double KC = KA / 4;
    private static final double CE = 2 * KC * H;

    public final static FinalPointTransition A = fpt(KA, UR);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition B2 = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, UR);
    public final static FinalPointTransition C2 = fpt(KC, UL);
    public final static FinalPointTransition C3 = fpt(KC, DL);
    public final static FinalPointTransition C4 = fpt(KC, DR);
    public final static FinalPointTransition D = A.to(KC, DL);
    public final static FinalPointTransition E = C.to(CE, RIGHT);
    public final static FinalPointTransition F = C.to(CE, UP);
    public final static FinalPointTransition F2 = C2.to(CE, UP);
    public final static FinalPointTransition G = B.to(KC * H, RIGHT);
    public final static FinalPointTransition G2 = B.to(KC * H, LEFT);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_09",
                ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        return new DesignHelper(ALL_VERTEX_INDEXES, "rect_tile_09_design")
                .addEquations(sequence(
                        "KB = h",
                        "KC = AD = KA / 4"
                ))
                .addImportantVertexes(Tile9.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        diagonals(CE, VER).apply(C),
                        diagonals(CE, VER).apply(D)
                )
                .addCirclesCentral(gray, H)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(K, C, E, D, A),
                Line.line(C, F, D),
                Line.line(F, G),
                Line.line(F2, G2),
                Line.line(F2, F)
        );
    }

}