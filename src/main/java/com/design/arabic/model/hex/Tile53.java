package com.design.arabic.model.hex;

import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.design.common.*;
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

public class Tile53 {

    // p. 21
    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB * P;
    public static double KD = KC / 3;
    public static double KE = 2 * KD;
    public static double EF = KD / H;
    public static double KI = KB * H;
    public static double GI = KB * P;
    public static double KT = KB / 2;
    public static double KQ = KT * H;
    public static double SG = KT / H;
    public static double TS = SG * P;
    public static double QS = TS * P;
    public static double KS = KQ + QS;
    public static double BS = KB - KS;
    public static double AJ = BS;
    public static double AR = AJ * P;

    public final static FinalPointTransition A = fpt(1, UL_H);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition B1 = fpt(KB, UL_V);
    public final static FinalPointTransition B2 = fpt(KB, UR_V);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = fpt(KD, UP);
    public final static FinalPointTransition E = fpt(KE, UP);
    public final static FinalPointTransition F = E.to(EF, UL_H);
    public final static FinalPointTransition F2 = E.to(EF, DR_H);
    public final static FinalPointTransition F3 = E.to(EF, UR_H);
    public final static FinalPointTransition F4 = E.to(EF, DL_H);
    public final static FinalPointTransition G = fpt(KB, UL_H);
    public final static FinalPointTransition G2 = fpt(KB, UR_H);
    public final static FinalPointTransition I = fpt(KI, UP);
    public final static FinalPointTransition T = fpt(KT, UL_H);
    public final static FinalPointTransition Q = fpt(KQ, UP);
    public final static FinalPointTransition S = fpt(KS, UP);
    public final static FinalPointTransition J = A.to(AJ, DR_H);
    public final static FinalPointTransition R = A.to(AR, RIGHT);
    public final static FinalPointTransition R2 = A.to(AR, DL_H);

    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_53",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_53_design")
//                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
//                .withGridRatio(KC)
//                .withGridSize(20)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC = KB * P",
                        "KD = KC / 3",
                        "KE = 2 * KD",
                        "EF = KD / H",
                        "KI = KB * H",
                        "GI = KB * P",
                        "KT = KB / 2",
                        "KQ = KT * H",
                        "SG = KT / H",
                        "TS = SG * P",
                        "QS = TS * P",
                        "KS = KQ + QS",
                        "BS = KB - KS",
                        "AJ = BS",
                        "AR = AJ * P"
                ))
                .addImportantVertexes(Tile53.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        perimeter(H, VER).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(EF, VER).apply(E)
                )
                .addSinglePathsLines(
                        blue,
                        line(B1, B2),
                        line(G, I, G2),
                        line(S, T, Q)
                )
                .addCircleWithRadius(
                        gray,
                        CircleInstruction.circleInstruction(A, BS)
                )
                .addCirclesCentral(
                        blue,
                        KB
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }


    private static Sequence<Line> getFullPath() {
        return sequence(
                line(B1, F, F2),
                line(B2, F3, F4),
                line(G, S, G2),
                line(R, J, R2)
        );
    }


}