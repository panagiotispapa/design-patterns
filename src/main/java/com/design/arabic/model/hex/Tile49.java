package com.design.arabic.model.hex;

import com.design.common.*;
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
import static com.design.common.RatioHelper.P6.P;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile49 {

    public static double KA = 1;
    public static double KB = H;
    public static double KD = KB / 3;
    public static double BD = KA / 6;
    public static double KE = BD;
    public static double BC = KA / 2;
    public static double DC = BD + BC;
    public static double CF = DC * P;
    public static double KF = KA - CF;
    public static double FG = KE / 2;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition C = fpt(1, UR_H);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition B2 = fpt(KB, UR_V);
    public final static FinalPointTransition B3 = fpt(KB, UL_V);
    public final static FinalPointTransition D = B.to(BD, LEFT);
    public final static FinalPointTransition D2 = B2.to(BD, DR_H);
    public final static FinalPointTransition E = fpt(KE, UP);
    public final static FinalPointTransition E2 = fpt(KE, UR_V);
    public final static FinalPointTransition F = fpt(KF, UR_H);
    public final static FinalPointTransition G = F.to(FG, UL_V);
    public final static FinalPointTransition G2 = F.to(FG, DR_V);


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_49",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_49_design")
//                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
//                .withGridRatio(KE)
//                .withGridSize(16)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KD = KB / 3",
                        "BD = KA / 6",
                        "KE = BD",
                        "BC = KA / 2",
                        "DC = BD + BC",
                        "CF = DC * P",
                        "KF = KA - CF",
                        "FG = KE / 2"
                ))
                .addImportantVertexes(Tile49.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(BD, VER).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(D, D2),
                line(E, G),
                line(E2, G2)
        );
    }

}