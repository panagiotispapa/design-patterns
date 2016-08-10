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
import static java.lang.Math.tan;

public class Tile30 {
    //p. 15
    public static double ANGLE_1 = PI - PI / 4 - PI / 6;
    public static double ANGLE_2 = PI - ANGLE_1;
    public static double ANGLE_3 = PI - ANGLE_2 - PI / 3;
    public static double ANGLE = PI / 3 - PI / 4;


    public static double KB = H;
    public static double KC = KB * H;
    public static double AC = 1 - KC;
    public static double CD = AC * H;
    public static double AD = AC * RatioHelper.P6.P;
    public static double DE = CD / tan(ANGLE_3);
    public static double AE = DE + AD;
    public static double BE = 0.5 - AE;
    public static double BF = BE;
    public static double KF = KB - BF;
    public static double KG = KF * H;
    public static double GP = 0.5 * KG * tan(ANGLE);
    public static double KP = KG - GP;

    public final static FinalPointTransition A = fpt(1, DR_V);
    public final static FinalPointTransition D = A.to(AD, UP);
    public final static FinalPointTransition C = fpt(KC, DR_V);
    public final static FinalPointTransition C2 = fpt(KC, UR_V);
    public final static FinalPointTransition G = fpt(KG, DR_V);
    public final static FinalPointTransition P = fpt(KP, DR_V);
    public final static FinalPointTransition P2 = fpt(KP, UR_V);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition F = fpt(KF, RIGHT);
    public final static FinalPointTransition E = B.to(BF, DOWN);
    public final static FinalPointTransition E2 = B.to(BF, UP);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_30",
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
                "KPF = ANGLE_1 = PI - PI / 4 - PI / 6",
                "FHA = ECA =  ANGLE_2 = PI - ANGLE_1",
                "CEA = ANGLE_3 = PI - ANGLE_2 - PI / 3",
                "ANGLE = PI / 3 - PI / 4",
                "KB = h * KA",
                "KC = h * KB",
                "AC = 1 - KC",
                "CD = h * AC",
                "AD = 0.5 * AC",
                "DE = CD / tan(ANGLE_3)",
                "AE = DE + AD",
                "BE = 0.5 - AE",
                "BF = BE",
                "KF = KB - BF",
                "KG = h * KF",
                "GP = 0.5 * KG * tan(ANGLE)",
                "KP = KG - GP"
//                "DC=DE=KB",
//                "DB=1-KB"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_30_design")
                .addEquations(equations)
                .addImportantVertexes(Tile30.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        diagonals(1, VER).apply(K),
                        perimeter(H, HOR).apply(K),
                        diagonals(H, HOR).apply(K),
                        perimeter(KF, HOR).apply(K),
                        perimeter(KC, VER).apply(K),
                        perimeter(KG, VER).apply(K),
                        perimeter(KP, VER).apply(K),
                        perimeter(CD, HOR).apply(C),
                        perimeter(AD, VER).apply(A),
                        perimeter(AE, VER).apply(A)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(C, E, P2),
                Line.line(P, E2, C2)
        );
    }

}