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
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile24 {

    private static final double KA = 1.0;
    private static final double KB = 1.0 / 3.0;
    private static final double BC = KB;
    private static final double AD = BC / 2.0;
    private static final double DE = AD * H;
    private static final double KD = 1 - AD;
    private static final double KC = KB + BC;

    public final static FinalPointTransition A = fpt(pt(KA, UP));
    public final static FinalPointTransition B = fpt(pt(KB, UP));
    public final static FinalPointTransition C = fpt(pt(KC, UP));
    public final static FinalPointTransition D = fpt(pt(KD, UP));
    public final static FinalPointTransition E = D.append(pt(DE, UR_H));
    public final static FinalPointTransition F = D.append(pt(DE, UL_H));
    public final static FinalPointTransition G = D.append(pt(DE, DL_H));
    public final static FinalPointTransition I = D.append(pt(DE, DR_H));
    public final static FinalPointTransition J = C.append(pt(BC, UR_V));
    public final static FinalPointTransition L = C.append(pt(BC, UL_V));
    public final static FinalPointTransition M = C.append(pt(BC, DL_V));
    public final static FinalPointTransition N = C.append(pt(BC, DR_V));
    public final static FinalPointTransition P = fpt(pt(KB, UL_V));


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
                        perimeter(1.0, VER).apply(K),
                        perimeter(AD, VER).apply(D),
                        diagonals(DE, HOR).apply(D)
                )
                ;

    }

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(F, I, J, N, B, M, L, G, E),
                PointsPath.of(B, P)
        );
    }


}