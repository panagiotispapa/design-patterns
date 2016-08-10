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

public class Tile44 {

    // seen at V&A
    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB / 3;
    public static double KD = KC / H;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition A2 = fpt(1, DR_H);
    public final static FinalPointTransition A3 = fpt(1, DL_H);
    public final static FinalPointTransition A4 = fpt(1, LEFT);
    public final static FinalPointTransition B = fpt(KB, DOWN);
    public final static FinalPointTransition C = fpt(KC, DOWN);
    public final static FinalPointTransition D = fpt(KD, DR_H);
    public final static FinalPointTransition D2 = fpt(KD, UL_H);
    public final static FinalPointTransition E = fpt(KD, RIGHT);
    public final static FinalPointTransition F = A3.to(KD, RIGHT);
    public final static FinalPointTransition F2 = A3.to(KD, UR_H);
    public final static FinalPointTransition F3 = A3.to(KD, UL_H);
    public final static FinalPointTransition F4 = A4.to(KD, DR_H);
    public final static FinalPointTransition G = D.to(KD, DL_H);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_44",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_44_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KD)
                .withGridSize(12)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KB / 3",
                        "KD = KC / H"
                ))
                .addImportantVertexes(Tile44.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(KD, HOR).apply(K),
                        perimeter(KD, HOR).apply(A),
                        perimeter(KD, HOR).apply(A2),
                        perimeter(KD, HOR).apply(A3),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);
    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(E, D, G, F2),
                line(D, D2),
                line(F2, A3, F3),
                line(A3, F, F2, F3, F4)
        );
    }

}