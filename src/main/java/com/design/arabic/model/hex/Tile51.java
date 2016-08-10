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

public class Tile51 {

    // p. 20

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KB / 3.5;
    public static double KD = KC * H;
    public static double KE = 2 * KD;
    public static double KF = 2 * KC;
    public static double BM = KD;
    public static double AB = KA / 2;
    public static double AM = AB - BM;
    public static double AN = AM / 2;
    public static double AQ = AN / H;

    public final static FinalPointTransition A = fpt(1, UR_V);
    public final static FinalPointTransition B = fpt(KB, UR_H);
    public final static FinalPointTransition C = fpt(KC, UR_H);
    public final static FinalPointTransition C2 = fpt(KC, UL_H);
    public final static FinalPointTransition D = fpt(KD, UP);
    public final static FinalPointTransition E = fpt(KE, UP);
    public final static FinalPointTransition E2 = fpt(KE, UR_V);
    public final static FinalPointTransition F = fpt(KF, UR_H);
    public final static FinalPointTransition G = F.to(KC, UL_H);
    public final static FinalPointTransition I = F.to(KC, RIGHT);
    public final static FinalPointTransition J = F.to(KC, UR_H);
    public final static FinalPointTransition L = J.to(KC, UL_H);
    public final static FinalPointTransition M = J.to(KC, RIGHT);
    public final static FinalPointTransition M2 = A.to(AM, DL_V);
    public final static FinalPointTransition M3 = A.to(AM, DOWN);
    public final static FinalPointTransition N = A.to(AN, UL_V);
    public final static FinalPointTransition Q = A.to(AQ, LEFT);
    public final static FinalPointTransition Q2 = A.to(AQ, DL_H);


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_51",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_51_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KC)
                .withGridSize(16)
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H",
                        "KC =  KB / 3.5",
                        "KD =  KC * H",
                        "KE =  2 * KD",
                        "KF =  2 * KC",
                        "BM =  KD",
                        "AB =  KA / 2",
                        "AM =  AB - BM",
                        "AN =  AM / 2",
                        "AQ =  AN / H"
                ))
                .addImportantVertexes(Tile51.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        perimeter(H, HOR).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(AQ, HOR).apply(A),
                        diagonals(KA, VER).apply(K),
                        diagonals(KB, HOR).apply(K)
                )
                .addCirclesCentral(
                        blue,
                        KC
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15);

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(M3, Q2, M2, Q, M, G, E, C, E2, I, L)
//                line()
//                line(E, I, J, G, F.to(KC, DL_V)),
//                line(M3, Q2, M2, Q, M, J, L)
        );
    }

}