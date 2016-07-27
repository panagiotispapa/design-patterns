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

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.diagonals;
import static com.design.islamic.model.Hex.perimeter;
import static java.util.Arrays.asList;

public class TileStar1b {


    private static final double KD = 0.5;
    private static final double KB = H;

    public final static FinalPointTransition A = fpt(pt(1.0, RIGHT));
    public final static FinalPointTransition B = fpt(pt(KB, DR_V));
    public final static FinalPointTransition C = fpt(pt(KB, UR_V));
    public final static FinalPointTransition D = fpt(pt(KD, RIGHT));



    @TileSupplier
    public static Payload getPayloadSimple1b() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload
                .Builder(
                "hex_tile_star_01b",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }
    private static java.util.List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(B, D, C)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper1b() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_01b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
//                .addFullPaths(() -> getPayloadSimple1b().getPathsFull(), red)
//                .addEquations(equations)
                .addImportantVertexes(
                        TileStar1b.class
                )
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(KD, HOR).apply(K)
                ).addFullPaths(red, getFullPath());

    }


}