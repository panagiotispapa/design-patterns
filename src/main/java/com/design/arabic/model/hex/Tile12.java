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
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;

//p. 3
public class Tile12 {

    private static double RATIO_m = 1.0 / 7.0;
    private static double RATIO_w = 6 * H * RATIO_m;
    private static double RATIO_h = 5 * RATIO_m;
    private static double KA = RATIO_m;
    private static double KE = RATIO_w;
    private static double KF = RATIO_h;
    private static double KG = KA * H;

    public final static FinalPointTransition A = fpt(KA, DR_V);

    public final static FinalPointTransition E = fpt(KE, RIGHT);
    public final static FinalPointTransition E2 = fpt(KE, LEFT);
    public final static FinalPointTransition E3 = fpt(4 * KG, RIGHT);
    public final static FinalPointTransition F = fpt(KF, UP);
    public final static FinalPointTransition F2 = fpt(KF, DOWN);
    public final static FinalPointTransition G = fpt(KG, LEFT);
    public final static FinalPointTransition I1 = fpt(KA, UR_V);
    public final static FinalPointTransition I2 = fpt(2 * KG, RIGHT);
    public final static FinalPointTransition I3 = fpt(3 * KA, UP, KA, DR_V);
    public final static FinalPointTransition K2 = fpt(3 * KA, UP, 4 * KA, UR_V);
    public final static FinalPointTransition K3 = fpt(2 * KA, UP, 5 * KA, UR_V);
    public final static FinalPointTransition I4 = fpt(5 * KA, UR_V);
    public final static FinalPointTransition I5 = fpt(6 * KA, UR_V);
    public final static FinalPointTransition I6 = I5.to(KA, DOWN);
    public final static FinalPointTransition I7 = fpt(4 * KA, UR_V);
    public final static FinalPointTransition L9 = fpt(KA, UP);
    public final static FinalPointTransition K4 = L9.to(6 * KA, UL_V);
    public final static FinalPointTransition K5 = L9.to(6 * KA, UR_V);
    public final static FinalPointTransition K6 = fpt(6 * KA, UP, 2 * KA, DR_V);
    public final static FinalPointTransition K7 = fpt(4 * KA, UP, 2 * KA, DR_V);
    public final static FinalPointTransition K8 = fpt(3 * KA, UP);


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_12",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsSingleLines(whiteBold,
                        getAllSinglesPath())
                .withGridConf(Grid.Configuration.customRect(2 * RATIO_w, 2 * RATIO_h))

                .build();

    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_12_design")
                .addEquations(sequence(
                        "KB = KD = AC = 1 / 7",
                        "EC = 0.5 - AB"
                ))
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KA)
                .withGridSize(16)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(1, VER).apply(K),
                        diagonals(1, VER).apply(K)

                )
                .addSinglePathsLines(
                        green,
                        diagonalHorizontal(KE).apply(F),
                        diagonalHorizontal(KE).apply(F2)
                )
                .addSinglePathsLines(
                        blue,
                        diagonalVertical(KF).apply(E),
                        diagonalVertical(KF).apply(E2)
                )
                .addImportantVertexes(Tile12.class)
                .addSinglePathsLines(red, getAllSinglesPath())
                ;

    }

    private static Sequence<Line> getAllSinglesPath() {
        return join(
                getSinglesPath(),
                getSinglesPath().map(s -> s.mirror(Hex.mirrorVert))

        );
    }

    private static Sequence<Line> getSinglesPath() {
        return sequence(
                Line.line(K8, I3, I1, I2, I6, I4, K3, K2, K8),
                Line.line(K8, I3, I1, I2, I6, I4, K3, K2, K8).mirror(mirrorHor),
                Line.line(K4, L9, K5),
                Line.line(K6, K7, I7, E3),
                Line.line(K6, K7, I7, E3).mirror(mirrorHor)
        );
    }

}