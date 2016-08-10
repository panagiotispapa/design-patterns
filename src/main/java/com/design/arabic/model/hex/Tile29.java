package com.design.arabic.model.hex;

import com.design.common.*;
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

public class Tile29 {
//p. 15
    private static double ANGLE_1 = PI / 3 - PI / 4;
    private static double RATIO_M = Math.tan(ANGLE_1);
    private static double RATIO_N = Math.cos(ANGLE_1);

    private static double KB = H;
    private static double KC = KB * H;
    private static double CA = 1 - KC;
    private static double CD = RATIO_M * CA;
    private static double DA = CA / RATIO_N;
    private static double DE = RatioHelper.Ratios.RECT.$H().apply(DA);
    private static double AE = DE;
    private static double AF = 2 * AE;
    private static double BF = 0.5 - AF;
    private static double BG = BF;
    private static double KG = KB - BG;
    private static double KP = KG * H;
    private static double HI = 0.5 * KG * RATIO_M;
    private static double KI = KP - HI;

    public final static FinalPointTransition A = fpt(1, DR_V);
    public final static FinalPointTransition C = fpt(KC, DR_V);
    public final static FinalPointTransition B = fpt(KB, DR_H);
    public final static FinalPointTransition B2 = fpt(KB, RIGHT);
    public final static FinalPointTransition G = fpt(KG, DR_H);
    public final static FinalPointTransition G2 = fpt(KG, RIGHT);
    public final static FinalPointTransition I = fpt(KI, DOWN);
    public final static FinalPointTransition I2 = fpt(KI, DR_V);
    public final static FinalPointTransition P = fpt(KP, DOWN);
    public final static FinalPointTransition F = B.to(BF, UR_V);
    public final static FinalPointTransition F2 = B.to(BF, DL_V);
    public final static FinalPointTransition E = A.to(AE, DL_V);
    public final static FinalPointTransition D = E.to(AE, UL_H);

    @TileSupplier
    public static Payload getPayloadSimple() {
//        Polygon outer = Hex.hex(1 - RATIO_2, VER, Hex.centreTransform(1, DR_V);
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_29",
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

        Sequence<String> equations = sequence(
                "IGH = ANGLE_1 = PI / 3 - PI / 4",
                "m = tan(ANGLE_1)",
                "n = cos(ANGLE_1)",
                "KB = h * KA",
                "KC = h * KB",
                "CA = 1 - KC",
                "CD = m * CA",
                "DA = CA / n",
                "DE = DA * cos(45)",
                "AE = DE",
                "AF = 2 * AE",
                "BF = 0.5 - AF",
                "BG = BF",
                "KG = KB - BG",
                "KP = h * KG",
                "PI = 0.5 * KG * m",
                "KI = KH - HI"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_29_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addEquations(equations)
                .addImportantVertexes(Tile29.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(1, VER).apply(K),
                        perimeter(H, HOR).apply(K),
                        diagonals(H, HOR).apply(K),
                        perimeter(BF, VER).apply(B),
                        perimeter(AE, HOR).apply(E),
                        diagonals(AE, HOR).apply(E)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KG, HOR).apply(K)

                )
                .addFullPaths(red, getFullPath())
                ;
    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(F2, I2, G2),
                Line.line(A, D, F)
        );
    }

}