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
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

//p. 2
public class Tile11 {

    private static double RATIO_m = 1.0 / 4.0;
    private static double RATIO_n = RATIO_m / H;
    private static double KA = RATIO_n;
    private static double KE = 2 * KA;
    private static double KD = 3 * KA;
    private static double KF = KD / H;
    private static double KI = 3 * KA;
    private static double KJ = 4 * KA;

    public final static FinalPointTransition A = fpt(KA, RIGHT);
    public final static FinalPointTransition E = fpt(KE, RIGHT);
    public final static FinalPointTransition C = fpt(KE, DR_H);
    public final static FinalPointTransition B = fpt(KD, DR_H);
    public final static FinalPointTransition D = fpt(KD, RIGHT);
    public final static FinalPointTransition E1 = D.to(KA, DL_H);
    public final static FinalPointTransition E2 = D.to(KA, DR_H);
    public final static FinalPointTransition C1 = B.to(KA, RIGHT);
    public final static FinalPointTransition C2 = B.to(KA, UR_H);
    public final static FinalPointTransition F = fpt(KF, DR_V);
    public final static FinalPointTransition I = fpt(KI, RIGHT);
    public final static FinalPointTransition J = fpt(KJ, RIGHT);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_11",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }


    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(C1, C2, C, E, E1, E2)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        Sequence<String> equations = sequence(
                "KA = (1/4) / h",
                "BC DE = KA"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_11_design")
                .addEquations(equations)
                .addImportantVertexes(Tile11.class)
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KA)
                .withGridSize(8)
                .addSinglePathsLines(
                        gray,
                        perimeter(KI, HOR).apply(K),
                        perimeter(KJ, HOR).apply(K),
                        perimeter(KF, VER).apply(K),
                        diagonals(KF, VER).apply(K),
                        perimeter(KA, HOR).apply(K),
                        perimeter(KD, HOR).apply(K),
                        diagonals(KI, HOR).apply(K)
                )
                .addFullPaths(red, getFullPath())
                ;

    }

}