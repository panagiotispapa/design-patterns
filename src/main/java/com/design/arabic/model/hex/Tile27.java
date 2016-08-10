package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Line;
import com.design.common.RatioHelper.P6;
import com.design.common.RatioHelper.Ratios;
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
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;
import static java.lang.Math.PI;
import static java.lang.Math.cos;

public class Tile27 {
//p. 13

    private static double ANGLE_1 = PI / 3 - PI / 4;
    private static double BC = 0.5 * Math.tan(ANGLE_1);
    private static double KB = P6.H;
    private static double KC = KB - BC;
    private static double AC = 0.5 / cos(ANGLE_1);
    private static double AD = 2 * Ratios.RECT.$H().apply(AC);
    private static double KD = 1 - AD;
//    private static double RATIO_CD = £H.andThen($1).apply(RATIO_KA);

    public final static FinalPointTransition A = fpt(1, UR_V);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KD, UR_V);
    public final static FinalPointTransition E = fpt(KC, UR_H);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_27",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_27_design")
                .addEquations(sequence(
                        "CAB = 60 - 45 = 15 = th",
                        "BC = tan(th) * AB",
                        "AC = AB/cos(th)",
                        "AD = 2 * AC * cos(45)",
                        "KD = 1 - AD",
                        "KC = h - BC"
                ))
                .addImportantVertexes(Tile27.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(1, VER).apply(K),
                        diagonals(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KC, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(KD, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(C, D, E, A, C)
        );
    }


}