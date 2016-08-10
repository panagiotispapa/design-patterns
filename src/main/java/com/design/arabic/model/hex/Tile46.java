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

public class Tile46 {

//    https://yourdowsingfriend.files.wordpress.com/2013/07/dep_5324096-seamless-geometric-pattern.jpg

    public static double KA = 1;
    public static double KB = H;
    public static double AB = KA / 2;
    public static double KD = AB;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition C = fpt(1, UR_H);
    public final static FinalPointTransition B = fpt(KB, UR_V);
    public final static FinalPointTransition D = fpt(1, UL_H);
    public final static FinalPointTransition E = fpt(1, LEFT);
    public final static FinalPointTransition F = fpt(1, DL_H);
    public final static FinalPointTransition G = fpt(1, DR_H);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_46",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.SMALL)
                .withPathsSingleLines(whiteBold, getSinglePath())
                .withGridConf(Grid.Configs.HEX_VER3.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_46_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
//                .withGridRatio(KC)
//                .withGridSize(16)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "AB = KA / 2",
                        "KD = AB"
                ))
                .addImportantVertexes(Tile46.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(AB, HOR).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addSinglePathsLines(red, getSinglePath())
                .withFontSize(15);

    }

    private static Sequence<Line> getSinglePath() {
        return sequence(
                line(A, K, C, D, E, F, G, K)
        );
    }

}