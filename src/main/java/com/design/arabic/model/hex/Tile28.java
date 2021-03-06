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
import static java.lang.Math.PI;

public class Tile28 {
//p.14


    public static double KB = 0.5;
    public static double KC = KB / H;
    private static double ANGLE_1 = PI / 3 - PI / 4;
    private static double BD = 0.5 * KC * Math.tan(ANGLE_1);
    private static double KD = KB - BD;
    private static double KE = H;
    private static double CE = KC - KE;
    private static double EF = CE;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition D = fpt(KD, RIGHT);
    public final static FinalPointTransition C = fpt(KC, DR_V);
    public final static FinalPointTransition E = fpt(KE, DR_V);
    public final static FinalPointTransition F = E.to(CE, UR_H);
    public final static FinalPointTransition E2 = fpt(KE, UR_V);
    public final static FinalPointTransition F2 = E2.to(CE, DR_H);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_28",
                Hex.ALL_VERTEX_INDEXES
        )
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_28_design")
                .addEquations(sequence(
                        "KE = h",
                        "KB = 0.5 * KA",
                        "BD = tan(15) * BC",
                        "KD = KA - DB - BA",
                        "KC = KB / h",
                        "CE = KE - KC",
                        "CE = EF"
                ))
                .addImportantVertexes(Tile28.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        innerTriangles(1, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(EF, HOR).apply(E)
                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(F, D, F2)
        );
    }
}