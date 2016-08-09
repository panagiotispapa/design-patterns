package com.design.islamic.model.hex;

import com.design.common.*;
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
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile2 {

    private static double KA = 1.0;
    private static double BD = KA / (1.0 / P + 1.0 / H);
    private static double BA = BD / H;
    private static double KB = KA - BA;
    private static double KC = KB * H;


    public final static FinalPointTransition A = fpt(pt(KA, DR_V));
    public final static FinalPointTransition B = fpt(pt(KB, DR_V));
    public final static FinalPointTransition C = fpt(pt(KC, RIGHT));
    public final static FinalPointTransition D = B.append(pt(BD, RIGHT));
    public final static FinalPointTransition E = C.append(pt(BD, RIGHT));
    public final static FinalPointTransition F = B.append(pt(BD, DR_H));
    public final static FinalPointTransition G = fpt(pt(KB, DOWN));

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();


        return new Payload.Builder("hex_tile_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        whiteBold,
                        getFullPath()
                )
                .build();
    }

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(D, B, F),
                PointsPath.of(B, G)

        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_02_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath())
                .addEquations(
                        "BD = BC",
                        "BC = KB * p",
                        "BD = BA * h",
                        "KB + BA = KA",
                        "BC / p + BD / h = KA",
                        "BD / p + BD / h = KA",
                        "BD * (1 / p + 1 / h) = KA",
                        "BD = KA / (1 / p + 1 / h)"
                )
                .addImportantVertexes(Tile2.class)
                .addSinglePathsLines(
                        gray,
                        Hex.perimeter(1.0, VER).apply(K),
                        Hex.diagonals(1.0, VER).apply(K),
                        Hex.diagonals(H, HOR).apply(K),
                        Hex.diagonals(BD, HOR).apply(B)
                )
                .addSinglePathsLines(
                        green,
                        Hex.perimeter(KB, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        Hex.perimeter(BD, HOR).apply(B)
                );

    }

}