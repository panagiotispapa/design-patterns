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
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile12 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double KC = KB / 3;


    public final static FinalPointTransition A = fpt(KA, UR);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = fpt(2 * KC, UP);
    public final static FinalPointTransition E = C.to(KC, LEFT);
    public final static FinalPointTransition F = C.to(KC, RIGHT);
    public final static FinalPointTransition G = D.to(KC, RIGHT);
    public final static FinalPointTransition I = A.to(KC, DOWN);
    public final static FinalPointTransition J = B.to(KC, RIGHT);
    public final static FinalPointTransition L = I.to(KC, LEFT);
    public final static FinalPointTransition P = fpt(2 * KC, RIGHT);
    public final static FinalPointTransition Q = P.to(KC, UP);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_12",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsSingleLines(whiteBold, getAllSinglesPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_12_design")
                .addEquations(sequence(
                        "KB = h",
                        "KC = KB / 3"
                ))
                .addImportantVertexes(Tile12.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(KC / H, HOR).apply(K),
                        perimeter(2 * KC / H, HOR).apply(K)
                )
                .addCirclesCentral(gray, H)
                .addSinglePathsLines(red, getAllSinglesPath())
                ;

    }

    private static Sequence<Line> getAllSinglesPath() {
        return join(
                getSinglesPath2(),
                getSinglesPath2().map(s -> s.mirror(Rect.mirrorHor))
        );
    }

    private static Sequence<Line> getSinglesPath2() {
        return join(
                getSinglesPath(),
                getSinglesPath().map(s -> s.mirror(Rect.mirrorVert))
        );
    }


    private static Sequence<Line> getSinglesPath() {
        return sequence(
//                Line.circle(J, G, C),
                Line.line(C, F, P, Q, I, L, J, G, C)
        );
    }

}