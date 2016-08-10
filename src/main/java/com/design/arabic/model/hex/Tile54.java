package com.design.arabic.model.hex;

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

public class Tile54 {

    // p. 23

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB / 4;
    public static double DE = 2 * KC;
    public static double DF = DE / H;
    public static double FE = DF * P;
    public static double BE = 2 * KC * H;

    public final static FinalPointTransition A = fpt(1, UP);
    public final static FinalPointTransition A2 = fpt(1, UR_V);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = C.to(2 * KC, UR_H);
    public final static FinalPointTransition D2 = C.to(2 * KC, DR_H);
    public final static FinalPointTransition E = D.to(2 * KC, RIGHT);
    public final static FinalPointTransition E2 = B.to(BE, DOWN);
    public final static FinalPointTransition F = E.to(FE, DOWN);
    public final static FinalPointTransition F2 = E2.to(FE, UP);
    public final static FinalPointTransition G = B.to(KC, LEFT);

    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_54",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_54_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KC)
                .withGridSize(16)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KB / 4",
                        "DE = 2 * KC",
                        "DF = DE / H",
                        "FE = DF * P",
                        "BE = 2 * KC * H"
                ))
                .addImportantVertexes(Tile54.class)
                .addSinglePathsLines(
                        blue,
                        line(D, E)
                )
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        perimeter(H, HOR).apply(K),
                        perimeter(KC, HOR).apply(K),
                        diagonals(KA, VER).apply(K),
                        diagonals(KB, HOR).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(F2, D2, C, D, F),
                line(E2, G, E)
        );
    }


}