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
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

//p.
public class Tile17 {

    private static double RATIO_m = 1.0 / 3.0;
    private static double KB = RATIO_m;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(2 * KB, RIGHT);
    public final static FinalPointTransition D = fpt(KB, DR_H);
    public final static FinalPointTransition E = fpt(KB, UR_H);
    public final static FinalPointTransition F = B.to(KB, UR_H);
    public final static FinalPointTransition G = B.to(KB, DR_H);
    public final static FinalPointTransition I = C.to(KB, UR_H);
    public final static FinalPointTransition J = C.to(KB, DR_H);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_17",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.HEX_HOR3.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_17_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KB)
                .withGridSize(8)

                .addFullPaths(red, getFullPath())
                .addEquations(sequence(
                        "KB = (1/3) * KA"
                ))
                .addImportantVertexes(Tile17.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        perimeter(KB, HOR).apply(K),
                        perimeter(2 * KB, HOR).apply(K)
                );

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(G, E, I, A, J, D, F)
        );
    }

}