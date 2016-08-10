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

public class Tile8 {

    private static final double KA = 1;
    private static final double KD = KA * H;
    private static final double KB = 0.5;
    private static final double KC = KB / H;
    private static final double AC = 1 - KC;
    private static final double AD = 1 - KD;
    private static final double AE = AC * H;
    private static final double AF = AE * H;
    private static final double FE = AE * 0.5;
    private static final double AG = AC * 0.5;
    private static final double KG = KA - AG;
    private static final double KF = 1 - AF;
    private static final double KS = FE;
    private static final double KI = KS / H;
    private static final double KJ = 2 * KS;
    private static final double JA = 1 - KJ;
    private static final double IJ = KJ - KI;
    private static final double JD = JA - AD;
    private static final double JM = IJ * H;

    public final static FinalPointTransition A = fpt(KA, DR_V);
    public final static FinalPointTransition A2 = fpt(KA, DR_H);
    public final static FinalPointTransition A3 = fpt(KA, DOWN);
    public final static FinalPointTransition B = fpt(KB, DR_V);
    public final static FinalPointTransition C = fpt(KC, DR_V);
    public final static FinalPointTransition D = fpt(KD, DR_V);
    public final static FinalPointTransition D2 = fpt(KD, DR_H);
    public final static FinalPointTransition D3 = fpt(KD, DOWN);

    public final static FinalPointTransition F = fpt(KF, DR_V);
    public final static FinalPointTransition F2 = fpt(KF, RIGHT);
    public final static FinalPointTransition G = fpt(KG, DR_V);
    public final static FinalPointTransition E = G.to(AG, UP);
    public final static FinalPointTransition E2 = G.to(AG, DOWN);
    public final static FinalPointTransition E3 = G.to(AG, UR_V);
    public final static FinalPointTransition T1 = fpt(KJ, RIGHT);
    public final static FinalPointTransition J = fpt(KJ, DR_V);
    public final static FinalPointTransition J2 = fpt(KJ, DOWN);
    public final static FinalPointTransition S = fpt(KS, DR_V);
    public final static FinalPointTransition M = J.to(JM, UL_H);
    public final static FinalPointTransition M2 = J2.to(JM, UL_H);
    public final static FinalPointTransition M3 = J2.to(JM, UR_H);
    public final static FinalPointTransition M4 = T1.to(JM, UL_V);
    public final static FinalPointTransition I = fpt(KI, DOWN);
    public final static FinalPointTransition L = J.to(JD, DR_H);
    public final static FinalPointTransition L2 = J2.to(JD, DR_H);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_08",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        whiteBold,
                        getFullPath()
                )
                .withSize(Payload.Size.MEDIUM)
                .build();
    }


    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(M2, L2, D2, L, M),
                Line.line(M3, E2, G, E3, M4)
        );
    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Sequence<String> equations = sequence(
                "KB = 0.5",
                "KC = KB/h",
                "KD = h",
                "AC = 1 - KC",
                "AD = 1 - h",
                "AE = AC * h",
                "AF = AE * h",
                "FE = AE * 0.5",
                "AG = AC * 0.5",
                "KS = FE",
                "KI = KS / h",
                "KJ = 2 * KS",
                "JA = 1 - KJ",
                "JL = JA",
                "IJ = KJ-KI",
                "JM = IJ*h"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_08_design")
                .addFullPaths(red, getFullPath())
                .addEquations(equations)
                .addSinglePathsLines(
                        gray,
                        diagonals(1, VER).apply(K),
                        diagonals(1, HOR).apply(K),
                        perimeter(AE, HOR).apply(A)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(1, VER).apply(K),
                        innerTriangles(1, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(1, HOR).apply(K),
                        innerTriangles(1, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(JM, VER).apply(J),
                        perimeter(JM, HOR).apply(J),
                        perimeter(JM, HOR).apply(J2)
                )
                .addSinglePathsLines(
                        gray,
                        perimeter(KD, VER).apply(K),
                        perimeter(KD, HOR).apply(K),
                        perimeter(KJ, VER).apply(K),
                        perimeter(KJ, HOR).apply(K)
//                        perimeter(KS, VER).apply(K),
//                        perimeter(KS, HOR).apply(K)
                )
                .addFullPaths(
                        gray,
                        diagonalHorizontal(1).apply(fpt(FE, UP)),
                        diagonalVertical(1).apply(fpt(FE, RIGHT)),
                        diagonalVertical(1).apply(fpt(KF, RIGHT)),
                        sequence(
                                Line.line(A, A2, A3),
                                Line.line(D, D2, D3)
                        )

                )
                .addImportantVertexes(Tile8.class);

    }

}