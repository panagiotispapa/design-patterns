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
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile6 {

    public static double KB = 1.0;
    public static double KA = KB / 2.0;
    public static double BA = KA;
    public static double BC = BA * H;

    public final static FinalPointTransition A = fpt(pt(KA, DR_V));
    public final static FinalPointTransition B = fpt(pt(KB, DR_V));
    public final static FinalPointTransition C = B.append(pt(BC, LEFT));
    public final static FinalPointTransition D = B.append(pt(BC, UL_H));
    public final static FinalPointTransition E = fpt(pt(KA, DOWN));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_06",
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
                PointsPath.of(A, C, B, D, E)
        );
    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_06_design")
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        innerTriangles(1.0, VER).apply(K),
                        innerTriangles(H, HOR).apply(K),
                        perimeter(BA, VER).apply(B),
                        perimeter(BA * H, HOR).apply(B)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KA, VER).apply(K)
                )
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addEquations(
                        sequence(
                                "KA=AB=0.5",
                                "KB=1",
                                "BC=BD=AB*h=0.5*h"
                        )
                )
                .addImportantVertexes(Tile6.class)
                .addFullPaths(red, getFullPath());

    }


}