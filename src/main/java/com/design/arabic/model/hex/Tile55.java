package com.design.arabic.model.hex;

import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile55 {

    // p. 25

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB / 5;
    public static double KD = KC / H;

    public final static FinalPointTransition A = fpt(1, UL_H);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = fpt(KD, UR_H);
    public final static FinalPointTransition E = D.to(2 * KD, RIGHT);
    public final static FinalPointTransition F = E.to(3 * KD, DR_H);
    public final static FinalPointTransition G = D.to(2 * KD, UL_H);
    public final static FinalPointTransition I = G.to(3 * KD, LEFT);

    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_55",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.SMALL)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_55_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KD)
                .withGridSize(20)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KB / 5",
                        "KD = KC / H"
                ))
                .addImportantVertexes(Tile55.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KD, HOR).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }


    private static Sequence<Line> getFullPath() {
        return sequence(
                line(I, G, D, E, F),
                line()
        );
    }


}