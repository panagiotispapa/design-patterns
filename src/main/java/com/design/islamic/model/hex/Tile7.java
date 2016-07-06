package com.design.islamic.model.hex;

import com.design.common.*;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

public class Tile7 {

    public static double KB = 1.0;
    public static double KA = KB * H;
    public static double AB = KA - KB;
    public static double BE = AB * P;
    public static double KC = 0.5 * KA;
    public static double KD = KC / H;

    public final static FinalPointTransition A = fpt(pt(KA, DR_V));
    public final static FinalPointTransition B = fpt(pt(KB, DR_V));
    public final static FinalPointTransition C = fpt(pt(KC, DR_V));
    public final static FinalPointTransition D = fpt(pt(KD, DR_V));
    public final static FinalPointTransition E = B.append(pt(BE, DOWN));
    public final static FinalPointTransition F = B.append(pt(BE, UR_V));
    public final static FinalPointTransition G = fpt(pt(KD, DR_H));
    public final static FinalPointTransition I = fpt(pt(KD, RIGHT));
    public final static FinalPointTransition J = fpt(pt(KA, DR_H));
    public final static FinalPointTransition L = fpt(pt(KA, RIGHT));

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_07",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(
                        whiteBold,
                        getFullPath()
                )
                .build();
    }

    private static List<PointsPath> getFullPath() {
        return Arrays.asList(
                PointsPath.of(G, E),
                PointsPath.of(I, F),
                PointsPath.of(J, D, L)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        List<String> equations = asList(
                "AB = 1 - h",
                "KC = 0.5 * h",
                "KD = KC/h",
                "BE = 0.5 * AB"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_07_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, VER).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        innerTriangles(H, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KD, VER).apply(K),
                        perimeter(KD, HOR).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .addImportantVertexes(Tile7.class)

                .addEquations(equations);

    }

}