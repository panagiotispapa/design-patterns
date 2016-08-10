package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
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
import static com.design.arabic.model.Hex.Vertex.UL_H;
import static com.design.arabic.model.Hex.Vertex.UP;
import static com.design.arabic.model.Hex.Vertex.UR_H;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile23 {


    private static double KA = 1;
    private static double KB = H * KA;
    private static double KC = H * KB;
    private static double AC = KA - KC;
    private static double CD = H * AC;

    public final static FinalPointTransition A = fpt(KA, UP);
    public final static FinalPointTransition B = fpt(KB, UL_H);
    public final static FinalPointTransition B2 = fpt(KB, UR_H);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = C.to(CD, UR_H);
    public final static FinalPointTransition E = C.to(CD, UL_H);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_23",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withSize(Payload.Size.SMALL)
                .withGridConf(Grid.Configs.HEX_HOR2.getConfiguration())
                .build();

    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_23_design")
                .addEquations(sequence(
                        "KB = H * KA",
                        "KC = H * KB",
                        "AC = KA - KC",
                        "CD = H * AC"
                ))
                .addImportantVertexes(Tile23.class)
                .addSinglePathsLines(gray,
                        perimeter(1, VER).apply(K),
                        perimeter(H, HOR).apply(K),
                        diagonals(1, VER).apply(K),
                        diagonals(H, HOR).apply(K)
                )
                .addSinglePathsLines(blue,
                        perimeter(AC, VER).apply(C),
                        diagonals(AC * H, HOR).apply(C)
                )
                .addFullPaths(red, getFullPath())
//                .withFontSize(12)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(B, B2),
                Line.line(E, C, D)
        );
    }


}