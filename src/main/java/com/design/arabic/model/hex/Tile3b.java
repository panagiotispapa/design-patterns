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

public class Tile3b {


    private static double KA = 1;
    private static double KB = KA * H;
    private static double AE = 0.5;
    private static double AL = AE * P;
    private static double EL = AE * H;
    private static double ML = EL;
    private static double AM = ML + AL;
    private static double KM = KA - AM;
    private static double KL = KA - AL;

    public final static FinalPointTransition B = fpt(H, RIGHT);
    public final static FinalPointTransition A = fpt(1, DR_V);
    public final static FinalPointTransition C = fpt(1, DL_V);
    public final static FinalPointTransition F = fpt(1, UL_V);
    public final static FinalPointTransition D = fpt(H, LEFT);
    public final static FinalPointTransition M = fpt(KM, DR_V);
    public final static FinalPointTransition L = fpt(KL, DR_V);
    public final static FinalPointTransition E = fpt(H, DR_H);

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_03b",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        whiteBold,
                        getFullPathB()
                )
                .build();
    }

    private static Sequence<Line> getFullPathB() {
        return sequence(
                Line.line(B, M, E)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_03b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPathB())
                .addEquations(
                        "AE = 0.5",
                        "AL = AE * P",
                        "EL = AE * H",
                        "ML = EL",
                        "AM = ML + AL",
                        "KM = KA - AM",
                        "KL = KA - AL"
                )
                .addImportantVertexes(Tile3b.class)
                .addSinglePathsLines(
                        gray,
                        Hex.perimeter(1, VER).apply(K),
                        Hex.diagonals(1, VER).apply(K),
                        Hex.diagonals(H, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        Hex.perimeter(KM, VER).apply(K)
                )
                .addSinglePathsLines(gray,
                        Line.line(E, L)
                );

    }

}