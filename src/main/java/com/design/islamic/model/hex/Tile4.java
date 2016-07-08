package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.FinalPointTransition;
import com.design.common.PointsPath;
import com.design.common.RatioHelper.P6;
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
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;

public class Tile4 {

    private static double KA = 0.5 / P6.H;
    private static double KB = KA * KA;
    private static double KC = 2 * KB;
    private static double CD = 1 - KC;

    public final static FinalPointTransition A = fpt(pt(KA, RIGHT));
    public final static FinalPointTransition B = fpt(pt(KB, DR_V));
    public final static FinalPointTransition C = fpt(pt(KC, DR_V));
    public final static FinalPointTransition D = fpt(pt(1, DR_V));
    public final static FinalPointTransition E = D.append(pt(CD, DL_V));
    public final static FinalPointTransition F = D.append(pt(CD, UP));
    public final static FinalPointTransition G = fpt(pt(KA, DR_H));

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_04",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        whiteBold,
                        getFullPath()
                )
                .build();
    }

    private static List<PointsPath> getFullPath() {
        return Arrays.asList(
                PointsPath.of(F, G),
                PointsPath.of(A, E)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        List<String> equations = asList(
                "i=0.5/h",
                "KA=i",
                "KB=i*i",
                "KC=KA/h=2*KB",
                "DC=DE=KB",
                "DC=1-KC"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_04_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath())
                .addEquations(equations)
                .addImportantVertexes(Tile4.class)
                .addSinglePathsLines(
                        gray,
                        Hex.perimeter(1.0, VER).apply(K),
                        Hex.diagonals(1.0, VER).apply(K),
                        Hex.innerTriangles(1.0, VER).apply(K),
                        Hex.innerTriangles(KA, HOR).apply(K),
                        Hex.perimeter(CD, VER).apply(D)
                )
                .addSinglePathsLines(
                        green,
                        Hex.perimeter(KA, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        Hex.perimeter(KB, VER).apply(K)
                );

    }

}