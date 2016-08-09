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
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.diagonals;
import static com.design.islamic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile2b {

    private static double KA = 1.0;
    private static double BD = KA / (1.0 / P + 1.0 / H);
    private static double BA = BD / H;
    private static double KB = KA - BA;

    private static double KC = KB * H;
    private static double KF = 0.5 * KB;
    private static double BG = 0.5 * KB * H;
//    private static double BG = 0.5 * KB * H;

    public final static FinalPointTransition A = fpt(pt(1.0, DR_V));
    public final static FinalPointTransition B = fpt(pt(KB, DR_V));
    public final static FinalPointTransition F = fpt(pt(KF, DR_V));
    public final static FinalPointTransition E = fpt(pt(H, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KC, RIGHT));
    public final static FinalPointTransition D = B.append(pt(BD, RIGHT));
    public final static FinalPointTransition D3 = B.append(pt(BD, DL_V));
    public final static FinalPointTransition D2 = B.append(pt(BD, DR_H));
    public final static FinalPointTransition G = B.append(pt(BG, DL_H));
    public final static FinalPointTransition G2 = B.append(pt(BG, UR_H));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_02b",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_02b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addEquations(sequence(
                        "BD = BC",
                        "BC = KB * p",
                        "BD = BA * h",
                        "KB + BA = KA",
                        "KF=KB/2",
                        "KG=(KB/2)*h"
                ))
                .addImportantVertexes(Tile2b.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K),
                        diagonals(H, HOR).apply(K),
                        diagonals(BD, HOR).apply(B)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(BD, HOR).apply(B)

                )
                .addSinglePathsLines(
                        green,
                        perimeter(BG, HOR).apply(B)
                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(C, F, D3, G, D2, D, G2, C, B, D),
                PointsPath.of(D3, B, D2)
        );
    }

}