package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.VER;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile19 {
// p. 8

    public static double RATIO_m = 1.0 / 5.0;
    public static double KA = RATIO_m;

    public final static FinalPointTransition A = fpt(KA, DR_V);
    public final static FinalPointTransition B = fpt(2 * KA, DR_V);
    public final static FinalPointTransition C = fpt(3 * KA, DR_V);
    public final static FinalPointTransition D = fpt(4 * KA, DR_V);
    public final static FinalPointTransition E = fpt(5 * KA, DR_V);
    public final static FinalPointTransition F = E.to(KA, DL_V);
    public final static FinalPointTransition G = E.to(2 * KA, DL_V);
    public final static FinalPointTransition I = E.to(3 * KA, DL_V);
    public final static FinalPointTransition J = fpt(KA, UR_V);
    public final static FinalPointTransition I5 = fpt(2 * KA, UR_V);
    public final static FinalPointTransition I6 = fpt(3 * KA, UR_V);
    public final static FinalPointTransition I7 = fpt(4 * KA, UR_V);
    public final static FinalPointTransition I8 = fpt(5 * KA, UR_V);
    public final static FinalPointTransition I9 = I7.to(KA, DOWN);
    public final static FinalPointTransition L1 = I7.to(2 * KA, DOWN);
    public final static FinalPointTransition L2 = I8.to(KA, DOWN);
    public final static FinalPointTransition L3 = I8.to(2 * KA, DOWN);
    public final static FinalPointTransition L4 = I8.to(3 * KA, DOWN);
    public final static FinalPointTransition L5 = D.to(KA, UP);
    public final static FinalPointTransition L6 = E.to(KA, UP);
    public final static FinalPointTransition L7 = C.to(KA, UP);
    public final static FinalPointTransition L8 = A.to(KA, UR_V);
    public final static FinalPointTransition L9 = A.to(2 * KA, UR_V);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_19",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_19_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KA)
                .withGridSize(16)

                .addEquations(sequence(
                        "KA = 1 / 5"
                ))
                .addImportantVertexes(Tile19.class)
                .addSinglePathsLines(gray,
                        perimeter(1, VER).apply(K),
                        perimeter(KA, VER).apply(K),
                        perimeter(KA, VER).apply(C),
                        perimeter(KA, VER).apply(D),
                        perimeter(KA, VER).apply(E),
                        perimeter(KA, VER).apply(F),
                        perimeter(KA, VER).apply(G),
                        diagonals(1, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(A, L9, I6, I5),
                Line.line(J, L8),
                Line.line(C, B, L7, L6),
                Line.line(D, L5),
                Line.line(L3, L4, L1, I7),
                Line.line(L2, I9)
        );
    }
}