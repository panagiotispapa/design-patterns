package com.design.arabic.model.hex;

import com.design.common.*;
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
import static com.design.common.RatioHelper.P6.P;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile7 {

    public static double KB = 1;
    public static double KA = KB * H;
    public static double AB = KA - KB;
    public static double BE = AB * P;
    public static double KC = 0.5 * KA;
    public static double KD = KC / H;

    public final static FinalPointTransition A = fpt(KA, DR_V);
    public final static FinalPointTransition B = fpt(KB, DR_V);
    public final static FinalPointTransition C = fpt(KC, DR_V);
    public final static FinalPointTransition D = fpt(KD, DR_V);
    public final static FinalPointTransition E = B.to(BE, DOWN);
    public final static FinalPointTransition F = B.to(BE, UR_V);
    public final static FinalPointTransition G = fpt(KD, DR_H);
    public final static FinalPointTransition I = fpt(KD, RIGHT);
    public final static FinalPointTransition J = fpt(KA, DR_H);
    public final static FinalPointTransition L = fpt(KA, RIGHT);

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_07",
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
                Line.line(G, E),
                Line.line(I, F),
                Line.line(J, D, L)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_07_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        perimeter(1, HOR).apply(K),
                        diagonals(1, VER).apply(K),
                        diagonals(1, HOR).apply(K),
                        innerTriangles(H, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KD, VER).apply(K),
                        perimeter(KD, HOR).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .addImportantVertexes(Tile7.class)

                .addEquations(sequence(
                        "AB = 1 - h",
                        "KC = 0.5 * h",
                        "KD = KC/h",
                        "BE = 0.5 * AB"
                ));

    }

}