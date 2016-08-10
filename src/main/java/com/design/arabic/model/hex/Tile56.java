package com.design.arabic.model.hex;

import com.design.Export;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile56 {

    // p. 26

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB / 3.5;
    public static double KD = KC * H;
    public static double EG = KC / H;
    public static double FG = EG * P;
    public static double IJ = KC / 2;
    public static double IL = IJ / H;
    public static double JL = IL * P;

    public final static FinalPointTransition A = fpt(1, UP);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KD, UP);
    public final static FinalPointTransition E = C.to(2 * KC, UR_H);
    public final static FinalPointTransition F = C.to(3 * KC, UR_H);
    public final static FinalPointTransition G = F.to(FG, DR_V);
    public final static FinalPointTransition I = E.to(KC, RIGHT);
    public final static FinalPointTransition J = I.to(KC / 2, RIGHT);
    public final static FinalPointTransition L = J.to(JL, DOWN);
    public final static FinalPointTransition M = E.to(KC, DR_H);
    public final static FinalPointTransition N = fpt(KC, UR_H);
    public final static FinalPointTransition Q = fpt(2 * KC, UR_H);

    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_56",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.SMALL)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.HEX_HOR2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_56_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KC)
                .withGridSize(16)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KB / 3.5",
                        "KD = KC * H",
                        "EG = KC / H",
                        "FG = EG * P",
                        "IJ = KC / 2",
                        "IL = IJ / H",
                        "JL = IL * P"
                ))
                .addImportantVertexes(Tile56.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        perimeter(H, HOR).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(KC, HOR).apply(F),
                        perimeter(KC, HOR).apply(F.to(KC, RIGHT)),
                        diagonals(KC, HOR).apply(F),
                        diagonals(KA, VER).apply(K),
                        diagonals(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        line(E, J, L)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(C, E, G, I, L, M, N),
                line(E, Q.to(KC, UR_H), Q.to(KC, UL_H))
//                line(E2, G, E)
        );
    }


    public static void main(String[] args) {
        Export.export(getDesignHelper());
        Export.export(getPayload().toSvgPayload());
    }


}