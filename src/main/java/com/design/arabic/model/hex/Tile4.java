package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.FinalPointTransition;
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
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile4 {

    private static double KA = 0.5 / H;
    private static double KB = KA * KA;
    private static double KC = 2 * KB;
    private static double CD = 1 - KC;

    public final static FinalPointTransition A = fpt(KA, RIGHT);
    public final static FinalPointTransition B = fpt(KB, DR_V);
    public final static FinalPointTransition C = fpt(KC, DR_V);
    public final static FinalPointTransition D = fpt(1, DR_V);
    public final static FinalPointTransition E = D.to(CD, DL_V);
    public final static FinalPointTransition F = D.to(CD, UP);
    public final static FinalPointTransition G = fpt(KA, DR_H);

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_04",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        whiteBold,
                        getFullPath()
                )
                .build();
    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(F, G),
                Line.line(A, E)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_04_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KB)
                .addFullPaths(red, getFullPath())
                .addEquations(sequence(
                        "KA = 0.5 / H",
                        "KB = KA * KA",
                        "KC = 2 * KB",
                        "CD = 1 - KC"
                ))
                .addImportantVertexes(Tile4.class)
                .addSinglePathsLines(
                        gray,
                        Hex.perimeter(1, VER).apply(K),
                        Hex.diagonals(1, VER).apply(K),
                        Hex.innerTriangles(1, VER).apply(K),
                        Hex.innerTriangles(KA, HOR).apply(K),
                        Hex.perimeter(CD, VER).apply(D)
                )
                .addSinglePathsLines(
                        green,
                        Hex.perimeter(KA, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        Hex.perimeter(KB, VER).apply(K)
                );

    }

}