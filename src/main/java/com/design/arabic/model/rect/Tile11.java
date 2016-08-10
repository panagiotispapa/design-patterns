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

public class Tile11 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double KE = KB / 4;
    private static final double KC = KE / H;


    public final static FinalPointTransition A = fpt(KA, UR);
    public final static FinalPointTransition A2 = fpt(KA, UL);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition B2 = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, UR);
    public final static FinalPointTransition D = fpt(KC, UL);
    public final static FinalPointTransition E = fpt(KE, UP);
    public final static FinalPointTransition F = fpt(2 * KE, UP);
    public final static FinalPointTransition F2 = fpt(2 * KE, RIGHT);
    public final static FinalPointTransition G = fpt(3 * KE, UP);
    public final static FinalPointTransition G2 = fpt(3 * KE, RIGHT);
    public final static FinalPointTransition I = F.to(KE, LEFT);
    public final static FinalPointTransition J = B.to(KE, LEFT);
    public final static FinalPointTransition J2 = B2.to(KE, DOWN);
    public final static FinalPointTransition I2 = F2.to(KE, DOWN);
    public final static FinalPointTransition L = A.to(KE, LEFT);
    public final static FinalPointTransition N = A.to(KE, DOWN);
    public final static FinalPointTransition L2 = A2.to(KE, RIGHT);
    public final static FinalPointTransition M = G.to(2 * KE, RIGHT);
    public final static FinalPointTransition M2 = G.to(2 * KE, LEFT);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_11",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_11_design")
                .addEquations(sequence(
                        "KB = h",
                        "KE = KB / 4"
                ))
                .addImportantVertexes(Tile11.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(2 * KC, HOR).apply(K),
                        perimeter(3 * KC, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K)
                )
                .addCirclesCentral(gray, H)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(J, I, I2, J2),
                Line.line(L2, M2, M, L, N)
        );
    }

}