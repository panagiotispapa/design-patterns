package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
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
import static com.design.common.Polygon.Type.VER;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

public class Tile19 {

    public static double RATIO_m = 1.0 / 5.0;
    public static double KA = RATIO_m;

    public final static FinalPointTransition A = fpt(pt(KA, DR_V));
    public final static FinalPointTransition B = fpt(pt(2 * KA, DR_V));
    public final static FinalPointTransition C = fpt(pt(3 * KA, DR_V));
    public final static FinalPointTransition D = fpt(pt(4 * KA, DR_V));
    public final static FinalPointTransition E = fpt(pt(5 * KA, DR_V));
    public final static FinalPointTransition F = E.append(pt(KA, DL_V));
    public final static FinalPointTransition G = E.append(pt(2 * KA, DL_V));
    public final static FinalPointTransition I = E.append(pt(3 * KA, DL_V));
    public final static FinalPointTransition J = fpt(pt(KA, UR_V));
    public final static FinalPointTransition I5 = fpt(pt(2 * KA, UR_V));
    public final static FinalPointTransition I6 = fpt(pt(3 * KA, UR_V));
    public final static FinalPointTransition I7 = fpt(pt(4 * KA, UR_V));
    public final static FinalPointTransition I8 = fpt(pt(5 * KA, UR_V));
    public final static FinalPointTransition I9 = I7.append(pt(KA, DOWN));
    public final static FinalPointTransition L1 = I7.append(pt(2 * KA, DOWN));
    public final static FinalPointTransition L2 = I8.append(pt(KA, DOWN));
    public final static FinalPointTransition L3 = I8.append(pt(2 * KA, DOWN));
    public final static FinalPointTransition L4 = I8.append(pt(3 * KA, DOWN));
    public final static FinalPointTransition L5 = D.append(pt(KA, UP));
    public final static FinalPointTransition L6 = E.append(pt(KA, UP));
    public final static FinalPointTransition L7 = C.append(pt(KA, UP));
    public final static FinalPointTransition L8 = A.append(pt(KA, UR_V));
    public final static FinalPointTransition L9 = A.append(pt(2 * KA, UR_V));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_19",
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
                "KA = 1 / 5"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_19_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KA)
                .withGridSize(16)

                .addEquations(equations)
                .addImportantVertexes(Tile19.class)
                .addSinglePathsLines(gray,
                        perimeter(1.0, VER).apply(K),
                        perimeter(KA, VER).apply(K),
                        perimeter(KA, VER).apply(C),
                        perimeter(KA, VER).apply(D),
                        perimeter(KA, VER).apply(E),
                        perimeter(KA, VER).apply(F),
                        perimeter(KA, VER).apply(G),
                        diagonals(1.0, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(A, L9, I6, I5),
                PointsPath.of(J, L8),
                PointsPath.of(C, B, L7, L6),
                PointsPath.of(D, L5),
                PointsPath.of(L3, L4, L1, I7),
                PointsPath.of(L2, I9)
        );
    }
}