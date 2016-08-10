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
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile5 {

    public static double KD = 1;
    public static double KF = KD * H;
    public static double KG = KD / 2;
    public static double KA = KG / H;
    public static double KC = KA / H;
    public static double KB = Mappings.<Double>chain(i -> i / 2, i -> i / H).apply(KA);
    public static double DC = KB;

    public final static FinalPointTransition A = fpt(KA, RIGHT);
    public final static FinalPointTransition B = fpt(KB, DR_V);
    public final static FinalPointTransition C = fpt(KC, DR_V);
    public final static FinalPointTransition D = fpt(1, DR_V);
    public final static FinalPointTransition E = D.to(DC, DL_V);
    public final static FinalPointTransition E2 = D.to(DC, UP);
    public final static FinalPointTransition I = fpt(KB, UR_V);

    public final static FinalPointTransition F = fpt(KF, RIGHT);
    public final static FinalPointTransition G = fpt(KG, DR_V);
    public final static FinalPointTransition D2 = fpt(1, UR_V);
    public final static FinalPointTransition E3 = D2.to(DC, DOWN);

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_05",
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
                Line.line(I, E2),
                Line.line(B, E3)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_05_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath())
                .addEquations(sequence(
                        "KF = h",
                        "KG = KD / 2",
                        "KA = KG / h",
                        "KB = (0.5*KA)/h",
                        "KC = KA / h = 2 * KB",
                        "DC = DE = KB",
                        "DB = 1 - KB"
                ))
                .addImportantVertexes(Tile5.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(1, VER).apply(K),
                        innerTriangles(1, VER).apply(K),
                        innerTriangles(KA, HOR).apply(K),
                        perimeter(KB, VER).apply(D)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KA, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(KC, VER).apply(K),
                        perimeter(KB, VER).apply(K)
                );

    }

}