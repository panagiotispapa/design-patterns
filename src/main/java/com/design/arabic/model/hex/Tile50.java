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

import static com.design.common.CircleInstruction.circleInstruction;
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

public class Tile50 {

    // p. 18

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB * H;
    public static double KD = KC;
    public static double BD = KB - KD;
    public static double KE = 2 * BD;
    public static double KF = KE * 0.5 / H;
    public static double ED = KB - BD - KE;
    public static double EI = ED * H;
    public static double EG = EI * H;
    public static double GI = EI * P;
    public static double KG = KE + EG;
    public static double DJ = BD / P;
    public static double BJ = DJ * H;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition B = fpt(KB, UR_V);
    public final static FinalPointTransition B2 = fpt(KB, UP);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KD, UR_V);
    public final static FinalPointTransition D2 = fpt(KD, UP);
    public final static FinalPointTransition E = fpt(KE, UR_V);
    public final static FinalPointTransition E2 = fpt(KE, DR_V);
    public final static FinalPointTransition G = fpt(KG, UR_V);
    public final static FinalPointTransition F = fpt(KF, RIGHT);
    public final static FinalPointTransition I = G.to(GI, UL_H);
    public final static FinalPointTransition I2 = G.to(GI, DR_H);
    public final static FinalPointTransition J = B.to(BJ, DR_H);
    public final static FinalPointTransition J2 = B.to(BJ, UL_H);
    public final static FinalPointTransition J3 = B2.to(BJ, RIGHT);


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_50",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_50_design")
//                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
//                .withGridRatio(KE)
//                .withGridSize(16)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KB * H",
                        "KD = KC",
                        "BD = KB - KD",
                        "KE = 2 * BD",
                        "KF = KE * 0.5 / H",
                        "ED = KB - BD - KE",
                        "EI = ED * H",
                        "EG = EI * H",
                        "GI = EI * P",
                        "KG = KE + EG",
                        "DJ = BD / P",
                        "BJ = DJ * H"
                ))
                .addImportantVertexes(Tile50.class)
                .addSinglePathsLines(
                        blue,
                        line(G, I)
                )
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(H, VER).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addCirclesCentral(
                        blue,
                        KC
                )
                .addFullPaths(red, getFullPath())
                .addCircleWithRadius(gray, circleInstruction(D, BD))
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(J, I, E, F, E2),
                line(E, I2, J2, J3)
//                line(E2, G2)
        );
    }

}