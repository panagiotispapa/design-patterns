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

public class Tile48 {


    public static double KA = 1;
    public static double KB = H;
    public static double AB = KA / 2;
    public static double KD = KB / 3;
    public static double KF = 2 * KD;
    public static double KE = KD / H;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition C = fpt(1, UR_H);
    public final static FinalPointTransition B = fpt(KB, UR_V);
    public final static FinalPointTransition D = fpt(KD, UR_V);
    public final static FinalPointTransition E = fpt(KE, RIGHT);
    public final static FinalPointTransition F = fpt(KF, UR_V);
    public final static FinalPointTransition G = F.to(KE, RIGHT);
    public final static FinalPointTransition I = F.to(KE, UL_H);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_48",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_48_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KE)
                .withGridSize(12)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "AB = KA / 2",
                        "KD = KB / 3",
                        "KF = 2 * KD",
                        "KE = KD / H"
                ))
                .addImportantVertexes(Tile48.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(KE, HOR).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KE, HOR).apply(F)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(E, F, G),
                line(F, I),
                line(K, A, C)
//                line(A, C)
        );
    }

}