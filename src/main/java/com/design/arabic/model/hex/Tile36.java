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

public class Tile36 {

//    https://thumbs.dreamstime.com/z/vector-seamless-pattern-geometric-texture-arabic-islamic-art-49861177.jpg

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB / 5.5;
    public static double KD = KC * H;
    public static double KE = 2 * KD;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition A2 = fpt(1, UR_H);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = fpt(KD, UL_H);
    public final static FinalPointTransition E = fpt(KE, UL_H);
    public final static FinalPointTransition E2 = fpt(KE, UR_H);
    public final static FinalPointTransition F = fpt(2 * KC, UP);
    public final static FinalPointTransition G = F.to(KC, UL_V);
    public final static FinalPointTransition I = F.to(2 * KC, UR_V);
    public final static FinalPointTransition J = I.to(KC, UP);
    public final static FinalPointTransition L = J.to(2 * KC, DR_V);
    public final static FinalPointTransition M = I.to(3 * KC, DR_V);
    public final static FinalPointTransition P = M.to(4 * KC, UP);
    public final static FinalPointTransition N = L.to(2 * KC, UP);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_36",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_36_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KC)
                .withGridSize(16)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KB / 5.5",
                        "KD = KC * H",
                        "KE = 2 * KD"
                ))
                .addImportantVertexes(Tile36.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K),
                        perimeter(KC, VER).apply(K),
                        perimeter(KD, HOR).apply(K),
                        perimeter(KE, HOR).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(E, C, E2),
                line(N, L, J, G, F, I, M, P, N)
        );
    }

}