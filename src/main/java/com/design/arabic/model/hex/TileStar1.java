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

public class TileStar1 {

    private static final double KB = 0.5;
    private static final double KC = H;

    public final static FinalPointTransition A = fpt(1, DR_V);
    public final static FinalPointTransition B = fpt(KB, DR_V);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KC, DR_H);


    @TileSupplier
    public static Payload getPayloadSimple1() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload
                .Builder(
                "hex_tile_star_01",
                Hex.ALL_VERTEX_INDEXES
        ).withPathsFull(whiteBold, getFullPath1())
                .build();
    }

    private static Sequence<Line> getFullPath1() {
        return sequence(
                Line.line(D, B, C)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper1() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_01_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addImportantVertexes(
                        TileStar1.class
                )
//                .addEquations(equations)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(1, VER).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KC, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(KB, VER).apply(K)
                )
                .addFullPaths(red, getFullPath1())
                ;

    }
}
