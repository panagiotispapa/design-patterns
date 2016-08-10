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
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile24 {
//p. 11

    private static final double KA = 1.0;
    private static final double KB = 1.0 / 3.0;
    private static final double BC = KB;
    private static final double AD = BC / 2;
    private static final double DE = AD * H;
    private static final double KD = 1 - AD;
    private static final double KC = KB + BC;

    public final static FinalPointTransition A = fpt(KA, UP);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = fpt(KD, UP);
    public final static FinalPointTransition E = D.to(DE, UR_H);
    public final static FinalPointTransition F = D.to(DE, UL_H);
    public final static FinalPointTransition G = D.to(DE, DL_H);
    public final static FinalPointTransition I = D.to(DE, DR_H);
    public final static FinalPointTransition J = C.to(BC, UR_V);
    public final static FinalPointTransition L = C.to(BC, UL_V);
    public final static FinalPointTransition M = C.to(BC, DL_V);
    public final static FinalPointTransition N = C.to(BC, DR_V);
    public final static FinalPointTransition P = fpt(KB, UL_V);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_24",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_24_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath())
                .addEquations(sequence(
                        "KB = KA/3",
                        "BC = KB",
                        "CD = DA = CB/2"
                ))
                .addImportantVertexes(Tile24.class)
                .addSinglePathsLines(gray,
                        perimeter(1, VER).apply(K),
                        perimeter(AD, VER).apply(D),
                        diagonals(DE, HOR).apply(D)
                )
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(F, I, J, N, B, M, L, G, E),
                Line.line(B, P)
        );
    }


}