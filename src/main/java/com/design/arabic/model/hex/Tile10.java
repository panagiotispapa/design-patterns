package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
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
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile10 {

    private static double KA = 1;
    private static double KF = KA * H;
    private static double AB = KA / 3;
    private static double AC = AB / H;
    private static double KE = KF - 0.5 * AC;
    private static double EC = 0.5 - KE;


    public final static FinalPointTransition A = fpt(KA, DR_V);
    public final static FinalPointTransition F = fpt(KF, RIGHT);
    public final static FinalPointTransition E = fpt(KE, RIGHT);
    public final static FinalPointTransition B = A.to(AB, UP);
    public final static FinalPointTransition C = A.to(AC, UL_H);
    public final static FinalPointTransition D = A.to(AC, UR_H);
    public final static FinalPointTransition G = A.to(AC, LEFT);
    public final static FinalPointTransition I = A.to(AB, DL_V);
    public final static FinalPointTransition J = fpt(KE, DR_H);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_10",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .build();

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(C, B),
                Line.line(G, I),
                Line.line(J, G, C, E)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Sequence<String> equations = sequence(
                "AB = 1 / 3",
                "CD = AB / h",
                "KE = h - (CD / 2)",
                "EC = 0.5 - AB"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_10_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
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