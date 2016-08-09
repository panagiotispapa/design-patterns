package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
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
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile18 {

    public final static FinalPointTransition A = fpt(pt(1.0, DR_H));
    public final static FinalPointTransition B = fpt(pt(1.0, DL_H));
    public final static FinalPointTransition C = fpt(pt(1.0, UL_H));
    public final static FinalPointTransition D = fpt(pt(1.0, UR_H));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_18",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsSingleLines(whiteBold, getSinglePaths())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_18_design")
                .addSinglePathsLines(red, getSinglePaths())
                .addEquations(sequence(
                ))
                .addImportantVertexes(Tile18.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K)
                );
    }

    private static Sequence<PointsPath> getSinglePaths() {
        return sequence(
                PointsPath.of(A, C),
                PointsPath.of(B, D)
        );
    }

}