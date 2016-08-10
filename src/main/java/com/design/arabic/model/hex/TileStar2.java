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
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class TileStar2 {

    public static double KB = H / (H + 0.5);
    public static final double KA = KB * H;

    private static final double KC = H;

    public final static FinalPointTransition A = fpt(KA, RIGHT);
    public final static FinalPointTransition B = fpt(KB, UR_V);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KB, DR_V);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_star_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }


    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(D, C, B)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_02_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addEquations(sequence(
                        "AB=AC",
                        "KB= h / (h + 0.5)"
                ))
                .addImportantVertexes(
                        TileStar2.class
                )
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(1, VER).apply(K),
                        diagonals(H, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(0.5, VER).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KB, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                ;

    }


}
