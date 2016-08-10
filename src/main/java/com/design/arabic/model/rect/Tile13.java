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

public class Tile13 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double KE = KB / 4;
    private static final double BD = KE / H;
    private static final double AJ = BD * H;
    private static final double IJ = BD - AJ;


    public final static FinalPointTransition A = fpt(KA, UR);
    public final static FinalPointTransition A2 = fpt(KA, UL);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition C = B.to(KE, DOWN);
    public final static FinalPointTransition E = fpt(KE, UP);
    public final static FinalPointTransition D = B.to(BD, DR);
    public final static FinalPointTransition F = B.to(BD, DL);
    public final static FinalPointTransition G = A.to(BD, DL);
    public final static FinalPointTransition G2 = A2.to(BD, DR);
    public final static FinalPointTransition I = A.to(BD, LEFT);
    public final static FinalPointTransition I2 = A.to(BD, DOWN);
    public final static FinalPointTransition J = A.to(AJ, LEFT);
    public final static FinalPointTransition J2 = A.to(AJ, DOWN);
    public final static FinalPointTransition L = J.to(IJ, DOWN);
    public final static FinalPointTransition L2 = J2.to(IJ, LEFT);

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_13",
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

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_13_design")
                .addEquations(sequence(
                        "KB = h",
                        "KE = KB / 4"
                ))
                .addImportantVertexes(Tile13.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(BD, HOR).apply(B),
                        perimeter(BD, HOR).apply(A),
                        perimeter(BD, VER).apply(A),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K)
                )
                .addCirclesCentral(gray, H)
                .addFullPaths(red, getFullPath())
                .withFontSize(14)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(G2, F, B, D, G),
                Line.line(I, L, G, L2, I2)
//                Line.circle(L2, M2, M, L, N)
        );
    }

}