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
import static com.design.common.RatioHelper.P6.H;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile32 {

//    http://previews.123rf.com/images/mehdialigol/mehdialigol1510/mehdialigol151000010/46691563-black-and-white-islamic-geometric-pattern-background-Stock-Vector.jpg

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KA / 3;
    public static double KD = KC * H;
    public static double KE = 2 * KD;

    public final static FinalPointTransition A = fpt(1, UP);
    public final static FinalPointTransition A2 = fpt(1, UL_V);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = fpt(KD, UR_H);
    public final static FinalPointTransition E = fpt(KE, UR_H);
    public final static FinalPointTransition F = fpt(KE, UL_H);
    public final static FinalPointTransition G = A2.to(KC, UR_V);
    public final static FinalPointTransition L = A2.to(KC, DR_V);
    public final static FinalPointTransition I = A.to(KC, DL_V);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_32",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_32_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KC)
                .withGridSize(8)
                .addEquations(sequence(
                        "KC = KA / 3",
                        "KD = KC * H"
                ))
                .addImportantVertexes(Tile32.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(KA, VER).apply(K),
                        perimeter(KC, VER).apply(K),
                        perimeter(2*KC, VER).apply(K),
                        perimeter(KC, VER).apply(A),
                        perimeter(KC, VER).apply(A2)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(F, C, E, I, G, L)
        );
    }

}