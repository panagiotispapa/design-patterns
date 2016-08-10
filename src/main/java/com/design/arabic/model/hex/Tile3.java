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
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile3 {


    private static double KA = 1;
    private static double KB = KA * H;
    private static double KT = 0.5 * 0.5;
    private static double KV = KT * H;
    private static double TV = KT * P;
    private static double VQ = TV;
    private static double KQ = KV + VQ;
    private static double KI = KQ;

    public final static FinalPointTransition T = fpt(KT, DOWN);
    public final static FinalPointTransition B = fpt(H, RIGHT);
    public final static FinalPointTransition A = fpt(1, DR_V);
    public final static FinalPointTransition C = fpt(1, DL_V);
    public final static FinalPointTransition F = fpt(1, UL_V);
    public final static FinalPointTransition D = fpt(H, LEFT);
    public final static FinalPointTransition E = fpt(H, DR_H);
    public final static FinalPointTransition V = fpt(KV, DR_H);
    public final static FinalPointTransition Q = fpt(KQ, DR_H);
    public final static FinalPointTransition I = fpt(KQ, RIGHT);

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_03",
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
                Line.line(I, A, Q)
        );
    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_03_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath())
                .addEquations(sequence(
                        "KT = 0.5 * 0.5",
                        "KV = KT * H",
                        "TV = KT * P",
                        "VQ = TV",
                        "KQ = KV + VQ",
                        "KI = KQ"
                ))
                .addImportantVertexes(Tile3.class)
                .addSinglePathsLines(
                        gray,
                        Hex.perimeter(1, VER).apply(K),
                        Hex.diagonals(1, VER).apply(K),
                        Hex.diagonals(H, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        Hex.perimeter(0.5, VER).apply(B)
                )
                .addSinglePathsLines(
                        blue,
                        Hex.perimeter(KT, VER).apply(K),
                        Hex.perimeter(KI, HOR).apply(K)
                )
                .addFullPaths(gray,
                        Line.line(C, B, F)
                );


    }

}