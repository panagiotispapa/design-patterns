package com.design.islamic.model.hex;

import com.design.common.*;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

public class Tile10 {

    private static double KA = 1.0;
    private static double KF = KA * H;
    private static double AB = KA / 3.0;
    private static double AC = AB / H;
    private static double KE = KF - 0.5 * AC;
    private static double EC = 0.5 - KE;


    public final static FinalPointTransition A = fpt(pt(KA, DR_V));
    public final static FinalPointTransition F = fpt(pt(KF, RIGHT));
    public final static FinalPointTransition E = fpt(pt(KE, RIGHT));
    public final static FinalPointTransition B = A.append(pt(AB, UP));
    public final static FinalPointTransition C = A.append(pt(AC, UL_H));
    public final static FinalPointTransition D = A.append(pt(AC, UR_H));
    public final static FinalPointTransition G = A.append(pt(AC, LEFT));
    public final static FinalPointTransition I = A.append(pt(AB, DL_V));
    public final static FinalPointTransition J = fpt(pt(KE, DR_H));

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_10",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold, getFullPath())
                .build();

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(C, B),
                PointsPath.of(G, I),
                PointsPath.of(J, G, C, E)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        List<String> equations = asList(
                "AB = 1 / 3",
                "CD = AB / h",
                "KE = h - (CD / 2)",
                "EC = 0.5 - AB"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_10_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addEquations(equations)
                .addSinglePathsLines(
                        gray,
                        perimeter(KA, VER).apply(K),
                        perimeter(AC, HOR).apply(A),
                        diagonals(KA, VER).apply(K),
                        diagonals(KF, HOR).apply(K)

                )
                .addSinglePathsLines(
                        blue,
                        perimeter(EC, VER).apply(E)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KE, HOR).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .addImportantVertexes(Tile10.class)
                ;

    }

}