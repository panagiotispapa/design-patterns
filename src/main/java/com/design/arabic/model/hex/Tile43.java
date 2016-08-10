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
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile43 {

    // seen at V&A
    public static double KA = 1;
    public static double KB = H;
    public static double KD = KB / 3;
    public static double KC = KD / H;

    public final static FinalPointTransition A = RIGHT.fpt(1);
    public final static FinalPointTransition A2 = DL_H.fpt(1);
    public final static FinalPointTransition B = DOWN.fpt(KB);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KD, DOWN);
    public final static FinalPointTransition D2 = fpt(KD, UP);
    public final static FinalPointTransition E = fpt(KC, DR_H);
    public final static FinalPointTransition M = fpt(KC, DL_H);
    public final static FinalPointTransition N = M.to(KC, LEFT);
    public final static FinalPointTransition F = E.to(KC, DL_H);
    public final static FinalPointTransition G = A2.to(KC, UR_H);
    public final static FinalPointTransition I = A2.to(KC, UL_H);
    public final static FinalPointTransition J = A.to(KC, DL_H);
    public final static FinalPointTransition L = A.to(KC, LEFT);
    public final static FinalPointTransition L2 = A.to(KC, UL_H);
    public final static FinalPointTransition D3 = A.to(UL_V.pt(KD));
    public final static FinalPointTransition D4 = A.to(KD, DL_V);
    public final static FinalPointTransition D5 = A.to(KD, UP);
    public final static FinalPointTransition D6 = A.to(KD, DOWN);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_43",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.SMALL)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_43_design")
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KD = KB / 3",
                        "KC = KD / H"
                ))
                .addImportantVertexes(Tile43.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(KC, HOR).apply(A),
//                        perimeter(KC, HOR).apply(B),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(E, C),
                line(J, L, L2),
                line(D, D2),
                line(I, N),
                line(E, F, G),
                line(D5, D6),
                line(D3, A, D4)
        );
    }

}