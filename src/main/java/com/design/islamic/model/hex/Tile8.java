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

public class Tile8 {

    private static final double KA = 1.0;
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
    private static final double KJ = 2.0 * KS;
    private static final double JA = 1 - KJ;
    private static final double IJ = KJ - KI;
    private static final double JD = JA - AD;
    private static final double JM = IJ * H;

    public final static FinalPointTransition A = fpt(pt(KA, DR_V));
    public final static FinalPointTransition A2 = fpt(pt(KA, DR_H));
    public final static FinalPointTransition A3 = fpt(pt(KA, DOWN));
    public final static FinalPointTransition B = fpt(pt(KB, DR_V));
    public final static FinalPointTransition C = fpt(pt(KC, DR_V));
    public final static FinalPointTransition D = fpt(pt(KD, DR_V));
    public final static FinalPointTransition D2 = fpt(pt(KD, DR_H));
    public final static FinalPointTransition D3 = fpt(pt(KD, DOWN));

    public final static FinalPointTransition F = fpt(pt(KF, DR_V));
    public final static FinalPointTransition F2 = fpt(pt(KF, RIGHT));
    public final static FinalPointTransition G = fpt(pt(KG, DR_V));
    public final static FinalPointTransition E = G.append(pt(AG, UP));
    public final static FinalPointTransition E2 = G.append(pt(AG, DOWN));
    public final static FinalPointTransition E3 = G.append(pt(AG, UR_V));
    public final static FinalPointTransition T1 = fpt(pt(KJ, RIGHT));
    public final static FinalPointTransition J = fpt(pt(KJ, DR_V));
    public final static FinalPointTransition J2 = fpt(pt(KJ, DOWN));
    public final static FinalPointTransition S = fpt(pt(KS, DR_V));
    public final static FinalPointTransition M = J.append(pt(JM, UL_H));
    public final static FinalPointTransition M2 = J2.append(pt(JM, UL_H));
    public final static FinalPointTransition M3 = J2.append(pt(JM, UR_H));
    public final static FinalPointTransition M4 = T1.append(pt(JM, UL_V));
    public final static FinalPointTransition I = fpt(pt(KI, DOWN));
    public final static FinalPointTransition L = J.append(pt(JD, DR_H));
    public final static FinalPointTransition L2 = J2.append(pt(JD, DR_H));


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


    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(M2, L2, D2, L, M),
                PointsPath.of(M3, E2, G, E3, M4)
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
                        diagonals(1.0, VER).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        perimeter(AE, HOR).apply(A)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(1.0, VER).apply(K),
                        innerTriangles(1.0, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(1.0, HOR).apply(K),
                        innerTriangles(1.0, HOR).apply(K)
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
                        diagonalHorizontal(1.0).apply(fpt(pt(FE, UP))),
                        diagonalVertical(1.0).apply(fpt(pt(FE, RIGHT))),
                        diagonalVertical(1.0).apply(fpt(pt(KF, RIGHT))),
                        sequence(
                                PointsPath.of(A, A2, A3),
                                PointsPath.of(D, D2, D3)
                        )

                )
                .addImportantVertexes(Tile8.class);

    }

}