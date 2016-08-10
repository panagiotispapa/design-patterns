package com.design.arabic.model.hex;

import com.design.Export;
import com.design.arabic.model.*;
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
import static com.design.common.RatioHelper.P6.P;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile52 {

    // p. 19

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB / 8;
    public static double KD = KC * H;
    public static double EF = 3 * KC;
    public static double EJ = EF * H;
    public static double EL = EJ * H;
    public static double LJ = EJ * P;

    public final static FinalPointTransition A = fpt(1, UR_V);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KD, UP);
    public final static FinalPointTransition E = fpt(2 * KC, RIGHT);
    public final static FinalPointTransition E2 = E.to(KC, UL_H);
    public final static FinalPointTransition E3 = E.to(KC, DL_H);
    public final static FinalPointTransition F = fpt(5 * KC, RIGHT);
    public final static FinalPointTransition F2 = F.to(KC, UR_H);
    public final static FinalPointTransition F3 = F.to(KC, DR_H);
    public final static FinalPointTransition G = fpt(7 * KC, RIGHT);
    public final static FinalPointTransition I = B.to(KC, UL_H);
    public final static FinalPointTransition I2 = B.to(KC, DL_H);
    public final static FinalPointTransition L = E.to(EL, RIGHT);
    public final static FinalPointTransition J = L.to(LJ, DOWN);
    public final static FinalPointTransition J2 = L.to(LJ, UP);
    public final static FinalPointTransition M = G.to(KC, UL_H);
    public final static FinalPointTransition N = I.to(KC, UR_H);
    public final static FinalPointTransition N2 = I2.to(KC, DR_H);
    public final static FinalPointTransition Q = G.to(2 * KC, UL_H);
    public final static FinalPointTransition S = Q.to(3 * KC, UR_H);
    public final static FinalPointTransition T = S.to(3 * KC, LEFT);
    public final static FinalPointTransition V = T.to(2 * KC, UL_H);

    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_52",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.SMALL)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.HEX_HOR2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_52_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KC)
                .withGridSize(20)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KB / 8",
                        "KD = KC * H",
                        "EF = 3 * KC",
                        "EJ = EF * H",
                        "EL = EJ * H",
                        "LJ = EJ * P"
                ))
                .addImportantVertexes(Tile52.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        perimeter(H, HOR).apply(K),
                        perimeter(KC, HOR).apply(K),
                        diagonals(KA, VER).apply(K),
                        diagonals(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        line(L, J)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }


    private static Sequence<Line> getFullPath() {
        return sequence(
                line(E2, J, F2, I, N),
                line(E3, J2, F3, I2, N2),
                line(G, Q, S, T, V)
        );
    }


}