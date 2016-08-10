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
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile39 {

//    http://thumb1.shutterstock.com/display_pic_with_logo/1233944/336441650/stock-vector-seamless-black-and-white-islamic-hexagonal-stars-pattern-vector-336441650.jpg

    public static double KA = 1;
    public static double KB = H;
    public static double KD = KB / 2;
    public static double KC = KD * H;
    public static double KE = 2 * KC;

    public final static FinalPointTransition A = fpt(1, UP);
    public final static FinalPointTransition A2 = fpt(1, UR_V);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition E = fpt(KE, UP);
    public final static FinalPointTransition E2 = fpt(KE, UR_V);
    public final static FinalPointTransition D = fpt(KD, UR_H);
    public final static FinalPointTransition F = E.to(KD, RIGHT);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_39",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.SMALL)
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_39_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KC)
                .withGridSize(12)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KD = KB / 2",
                        "KC = KD * H",
                        "KE = 2 * KC",
                        "EF = ED = KD"
                ))
                .addImportantVertexes(Tile39.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(KA, VER).apply(K),
                        diagonals(KB, HOR).apply(K),
                        perimeter(KC, VER).apply(K),
                        perimeter(2 * KC, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(F, E, D, E2)
        );
    }

}