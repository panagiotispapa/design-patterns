package com.design.islamic.model.hex;

import com.design.common.*;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Payload;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.lang.Math.PI;
import static java.util.Arrays.asList;

public class Tile29 {

    private static double ANGLE_1 = PI / 3.0 - PI / 4.0;
    private static double RATIO_M = Math.tan(ANGLE_1);
    private static double RATIO_N = Math.cos(ANGLE_1);

    private static double KB = H;
    private static double KC = KB * H;
    private static double CA = 1 - KC;
    private static double CD = RATIO_M * CA;
    private static double DA = CA / RATIO_N;
    private static double DE = RatioHelper.Ratios.RECT.$H().apply(DA);
    private static double AE = DE;
    private static double AF = 2.0 * AE;
    private static double BF = 0.5 - AF;
    private static double BG = BF;
    private static double KG = KB - BG;
    private static double KP = KG * H;
    private static double HI = 0.5 * KG * RATIO_M;
    private static double KI = KP - HI;

    public final static FinalPointTransition A = fpt(pt(1.0, DR_V));
    public final static FinalPointTransition C = fpt(pt(KC, DR_V));
    public final static FinalPointTransition B = fpt(pt(KB, DR_H));
    public final static FinalPointTransition B2 = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition G = fpt(pt(KG, DR_H));
    public final static FinalPointTransition G2 = fpt(pt(KG, RIGHT));
    public final static FinalPointTransition I = fpt(pt(KI, DOWN));
    public final static FinalPointTransition I2 = fpt(pt(KI, DR_V));
    public final static FinalPointTransition P = fpt(pt(KP, DOWN));
    public final static FinalPointTransition F = B.append(pt(BF, UR_V));
    public final static FinalPointTransition F2 = B.append(pt(BF, DL_V));
    public final static FinalPointTransition E = A.append(pt(AE, DL_V));
    public final static FinalPointTransition D = E.append(pt(AE, UL_H));

    @TileSupplier
    public static Payload getPayloadSimple() {
//        Polygon outer = Hex.hex(1 - RATIO_2, VER, Hex.centreTransform(1, DR_V));
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

        List<String> equations = Arrays.asList(
                "IGH = ANGLE_1 = PI / 3.0 - PI / 4.0",
                "m = tan(ANGLE_1)",
                "n = cos(ANGLE_1)",
                "KB = h * KA",
                "KC = h * KB",
                "CA = 1 - KC",
                "CD = m * CA",
                "DA = CA / n",
                "DE = DA * cos(45)",
                "AE = DE",
                "AF = 2.0 * AE",
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
                        perimeter(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K),
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

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(F2, I2, G2),
                PointsPath.of(A, D, F)
        );
    }

}