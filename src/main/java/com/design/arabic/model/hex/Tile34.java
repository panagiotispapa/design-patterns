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

public class Tile34 {

//    http://thumb101.shutterstock.com/display_pic_with_logo/2537077/387179047/stock-vector-mashrabiya-islamic-pattern-monochrome-arabic-background-with-overlapping-hexagons-387179047.jpg

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KA / 5;
    public static double KD = KC * H;
    public static double KE = 2 * KD;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition A2 = fpt(1, DL_H);
    public final static FinalPointTransition A3 = fpt(1, LEFT);
    //    public final static FinalPointTransition A2 = fpt(1, UR_V);
//    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KD, UP);
    public final static FinalPointTransition E = fpt(KE, UP);
    public final static FinalPointTransition F = fpt(KC, UR_H);
    public final static FinalPointTransition G = fpt(KE, UR_V);
    public final static FinalPointTransition I = fpt(2 * KC, DR_H);
    public final static FinalPointTransition J = A.to(2 * KC, DL_H);
    public final static FinalPointTransition J2 = A.to(2 * KC, UL_H);
    public final static FinalPointTransition L = A2.to(2 * KC, RIGHT);
    public final static FinalPointTransition M = E.to(KC, UR_H);
    public final static FinalPointTransition M2 = E.to(KC, UL_H);
    public final static FinalPointTransition N = A.to(3 * KC, UL_H);
    public final static FinalPointTransition N2 = A3.to(3 * KC, UR_H);
    public final static FinalPointTransition P = fpt(4 * KC, RIGHT);
    public final static FinalPointTransition Q = fpt(4 * KC, UR_H);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_34",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_34_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KD)
                .withGridSize(16)
                .addEquations(sequence(
                        "KC = KA / 5",
                        "KI = 2 * KC",
                        "KP = 4 * KC",
                        "AN = 3 * KC"
                ))
                .addImportantVertexes(Tile34.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(4 * KC, HOR).apply(K),
                        perimeter(KC, HOR).apply(E),
                        diagonals(KC, HOR).apply(E),
                        perimeter(2 * KC, HOR).apply(K),
                        perimeter(3 * KC, HOR).apply(A),
                        perimeter(2 * KC, HOR).apply(A),
                        perimeter(2 * KC, HOR).apply(A2)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(K, F, G, C),
                Line.line(L, I, J),
                Line.line(N2, M2, E, M, N),
                Line.line(J2, A, J),
                Line.line(P, Q)
//                Line.line(A2, A3)
        );
    }

}