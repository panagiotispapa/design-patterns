package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.PointsPath;
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
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

public class Tile18 {

    public final static FinalPointTransition A = fpt(pt(1.0, DR_H));
    public final static FinalPointTransition B = fpt(pt(1.0, DL_H));
    public final static FinalPointTransition C = fpt(pt(1.0, UL_H));
    public final static FinalPointTransition D = fpt(pt(1.0, UR_H));


    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_18",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewSingleLines(whiteBold, getSinglePaths())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        List<String> equations = Arrays.asList(
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_18_design")
                .addSinglePathsLines(red, getSinglePaths())
                .addEquations(equations)
                .addImportantVertexes(Tile18.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K)
                );
    }

    private static List<PointsPath> getSinglePaths() {
        return asList(
                PointsPath.of(A, C),
                PointsPath.of(B, D)
        );
    }

}