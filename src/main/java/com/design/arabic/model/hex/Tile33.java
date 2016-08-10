package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
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
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile33 {

//    http://thumb1.shutterstock.com/display_pic_with_logo/2537077/384434044/stock-vector-islamic-pattern-vector-abstract-geometric-pattern-seamless-oriental-pattern-with-hexagons-and-384434044.jpg

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB / 6;

    public final static FinalPointTransition A = fpt(1, UP);
    public final static FinalPointTransition A2 = fpt(1, UR_V);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = A.to(KC, DR_H);
    public final static FinalPointTransition E = A2.to(KC, LEFT);
    public final static FinalPointTransition F = fpt(KC, UR_H);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_33",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_33_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
//                .withGridRatio(KC)
//                .withGridSize(8)
                .addEquations(sequence(
                        "KC = KA / 6",
                        "KD = KC * H"
                ))
                .addImportantVertexes(Tile33.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(KA, VER).apply(K),
                        diagonals(KB, HOR).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(KC, HOR).apply(A),
                        perimeter(KC, HOR).apply(A2)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(D, F, E, D)
        );
    }

}