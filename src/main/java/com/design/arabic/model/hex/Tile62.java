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

public class Tile62 {

    // p. 84

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB / 3;
    public static double KD = 2 * KC;
    public static double KE = KC / H;
    public static double BF = KE * P;
    public static double BG = BF * H;
    public static double AI = BF;
    public static double KL = KD;
    public static double LE = KL - KE;
    public static double AL = KA - KD;
    public static double AM = AL * P;
    public static double NE = LE * P;


    public final static FinalPointTransition A = fpt(1, UL_H);
    public final static FinalPointTransition A2 = fpt(1, UR_H);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = fpt(KD, UP);
    public final static FinalPointTransition L = fpt(KD, UL_H);
    public final static FinalPointTransition L2 = fpt(KD, UR_H);

    public final static FinalPointTransition E = fpt(KE, UL_H);
    public final static FinalPointTransition E2 = fpt(KE, UR_H);
    public final static FinalPointTransition F = B.to(BF, RIGHT);
    public final static FinalPointTransition F2 = B.to(BF, LEFT);
    public final static FinalPointTransition G = B.to(BG, DOWN);
    public final static FinalPointTransition J = B.to(BF, DR_H);
    public final static FinalPointTransition J2 = B.to(BF, DL_H);
    public final static FinalPointTransition I = A.to(AI, RIGHT);
    public final static FinalPointTransition I2 = A.to(AI, DR_H);
    public final static FinalPointTransition I3 = A2.to(AI, LEFT);
    public final static FinalPointTransition I4 = A2.to(AI, DL_H);
    public final static FinalPointTransition M = A.to(AM, RIGHT);
    public final static FinalPointTransition M2 = A2.to(AM, LEFT);
    public final static FinalPointTransition N = E.to(NE, LEFT);
    public final static FinalPointTransition N2 = E2.to(NE, RIGHT);
    public final static FinalPointTransition L3 = E.to(LE, DL_H);
    public final static FinalPointTransition L4 = E2.to(LE, DR_H);
    public final static FinalPointTransition L5 = E.to(LE, DR_H);
    public final static FinalPointTransition L6 = E2.to(LE, DL_H);
    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_62",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_62_design")
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KB / 3"
                ))
                .addImportantVertexes(Tile62.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(H, VER).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        line(K, E2, F2, F, E, K),
                        line()
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(BF, HOR).apply(B),
                        perimeter(BF, HOR).apply(A),
                        perimeter(KE, HOR).apply(K)
                )
                .addCirclesCentral(
                        green,
                        KD
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }


    private static Sequence<Line> getFullPath() {
        return sequence(
                line(E2, J2, I2, I),
                line(E, J, I4, I3),
                line(L5, L3, M),
                line(L6, L4, M2),
                line(),
                line(),
                line(),
                line()
        );
    }


    public static void main(String[] args) {
        Export.export(getDesignHelper());
        Export.export(getPayload().toSvgPayload());
    }
}