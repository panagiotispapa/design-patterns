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

public class Tile40 {

    // seen at V&A
    public static double KA = 1;
    public static double KB = H;
    public static double KC = KA / 3;
    public static double KD = KC * H;
    public static double KE = 2 * KD;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition B = fpt(KB, UR_V);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition C2 = fpt(KC, UR_H);
    public final static FinalPointTransition D = fpt(KD, UR_V);
    public final static FinalPointTransition E = fpt(KE, UR_V);
    public final static FinalPointTransition F = fpt(KE, DR_V);
    public final static FinalPointTransition G = A.to(KC, DL_H);
    public final static FinalPointTransition I = A.to(KC, LEFT);
    public final static FinalPointTransition J = A.to(KC, UL_H);
    public final static FinalPointTransition L = A.to(KD, UL_V);
    public final static FinalPointTransition M = A.to(KD, DL_V);
//    public final static FinalPointTransition B2 = fpt(KB, UP);
//    public final static FinalPointTransition D = fpt(KD, UR_H);
//    public final static FinalPointTransition D2 = fpt(KD, RIGHT);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_40",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_40_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
//                .withGridRatio(KC)
//                .withGridSize(16)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "AB = KA / 2",
                        "KD = AB"
                ))
                .addImportantVertexes(Tile40.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(KC, HOR).apply(A),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(C, K, C2, E, C, F, G, I, J),
                line(L, A, M)
//                line(A, C)
        );
    }

}