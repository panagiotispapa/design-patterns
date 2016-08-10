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

public class Tile35 {

//    https://thumbs.dreamstime.com/z/abstract-seamless-geometric-islamic-wallpaper-pattern-your-design-51924874.jpg

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KA / 4;
    public static double KF = 3 * KC;
    public static double KG = H * KF;
    public static double KI = (KF / 2) / H;
    public static double KD = KC * H;
    public static double KE = (KC / 2) / H;
    public static double KJ = 5 * KE;

    public final static FinalPointTransition A = fpt(1, RIGHT);
    public final static FinalPointTransition A2 = fpt(1, UR_H);
    public final static FinalPointTransition C = fpt(KC, RIGHT);
    public final static FinalPointTransition D = fpt(KD, UP);
    public final static FinalPointTransition E = fpt(KE, UR_V);
    public final static FinalPointTransition E2 = A.to(KE, UL_V);
    public final static FinalPointTransition E3 = A.to(KE, DL_V);
    public final static FinalPointTransition C2 = A.to(KC, UL_H);
    public final static FinalPointTransition F = A.to(KC, LEFT);
    public final static FinalPointTransition C4 = A.to(KC, DL_H);
    public final static FinalPointTransition C5 = A2.to(KC, DR_H);
    public final static FinalPointTransition G = fpt(KG, UR_V);
    public final static FinalPointTransition I = fpt(KI, UR_V);
    public final static FinalPointTransition J = fpt(KJ, UR_V);
    public final static FinalPointTransition F2 = fpt(KF, UR_H);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_35",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_35_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KD)
                .withGridSize(16)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KA / 4",
                        "KF = 3 * KC",
                        "KG = H * KF",
                        "KI = (KF / 2) / H",
                        "KD = KC * H",
                        "KE = (KC / 2) / H",
                        "KJ = 5 * KE"
                ))
                .addImportantVertexes(Tile35.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(KF, HOR).apply(K),
                        perimeter(KG, VER).apply(K),
                        perimeter(KI, VER).apply(K),
                        perimeter(KC, HOR).apply(A),
                        perimeter(KE, VER).apply(A)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(C2, E2, F, E3, C4),
                line(F2, I, F),
                line(C2, J, C5)
        );
    }

}