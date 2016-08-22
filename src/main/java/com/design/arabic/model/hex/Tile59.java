package com.design.arabic.model.hex;

import com.design.Export;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.design.common.*;
import com.design.common.model.Style;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile59 {

    // p. 28

    private static double RATIO_w = 1.0;
    private static double RATIO_h = RATIO_w;


    //6 * KB + 3 * KC = KA
    //6 * KB + 3 * (KB/H) = KA
    //KB = KA/(6 + 3/H)

    public static double KA = 1.0;
    public static double KB = KA / (6 + 3.0 / H);
    public static double KC = KB / H;
    public static double KD = 2 * KB;
    public static double AF = 3 * KC;
    public static double KI = 3 * KB;

    public static double FI = KA - KI - AF;
    public static double FL = FI / P;
    public static double IL = FL * H;


    public final static FinalPointTransition A = fpt(1, LEFT);
    public final static FinalPointTransition A2 = A.to(1, UP);
    public final static FinalPointTransition A3 = fpt(1, UP);
    public final static FinalPointTransition B = fpt(KB, LEFT);
    public final static FinalPointTransition C = fpt(KC, UL_V);
    public final static FinalPointTransition D = fpt(KD, LEFT);
    public final static FinalPointTransition D2 = fpt(KD, UL_H);
    public final static FinalPointTransition C2 = fpt(KC, UP);
    public final static FinalPointTransition C3 = A.to(KC, RIGHT);
    public final static FinalPointTransition D3 = A.to(KD, UR_V);
    public final static FinalPointTransition C4 = A.to(KC, UR_H);
    public final static FinalPointTransition D4 = A.to(KD, UP);
    public final static FinalPointTransition D5 = A3.to(KD, DOWN);
    public final static FinalPointTransition C5 = A3.to(KC, DL_H);
    public final static FinalPointTransition D6 = A3.to(KD, DL_V);
    public final static FinalPointTransition C6 = A3.to(KC, LEFT);
    public final static FinalPointTransition D7 = A2.to(KD, RIGHT);
    public final static FinalPointTransition C7 = A2.to(KC, DR_V);
    public final static FinalPointTransition D8 = A2.to(KD, DR_H);
    public final static FinalPointTransition C8 = A2.to(KC, DOWN);
    public final static FinalPointTransition E = D.to(KC, UL_V);
    public final static FinalPointTransition E2 = D2.to(KC, UL_V);
    public final static FinalPointTransition E3 = D2.to(KC, UP);
    public final static FinalPointTransition E4 = D5.to(KC, DL_H);
    public final static FinalPointTransition E5 = D6.to(KC, DL_H);
    public final static FinalPointTransition E6 = D6.to(KC, LEFT);
    public final static FinalPointTransition E7 = D8.to(KC, DR_V);
    public final static FinalPointTransition E8 = D8.to(KC, DOWN);
    public final static FinalPointTransition E9 = D7.to(KC, DR_V);
    public final static FinalPointTransition E10 = D4.to(KC, UR_H);
    public final static FinalPointTransition F = A.to(AF, RIGHT);
    public final static FinalPointTransition F2 = fpt(AF, UP);
    public final static FinalPointTransition F3 = A3.to(AF, LEFT);
    public final static FinalPointTransition F4 = A2.to(AF, DOWN);

    public final static FinalPointTransition G = F.to(KC, UL_H);
    public final static FinalPointTransition G2 = D3.to(KC, UR_H);
    public final static FinalPointTransition I = fpt(KI, LEFT);
    public final static FinalPointTransition I2 = A3.to(KI, DOWN);
    public final static FinalPointTransition I3 = A2.to(KI, RIGHT);
    public final static FinalPointTransition I4 = A.to(KI, UP);
    public final static FinalPointTransition J = fpt(KI, UP);
    public final static FinalPointTransition L = I.to(IL, UP);
    public final static FinalPointTransition L2 = I2.to(IL, LEFT);
    public final static FinalPointTransition L3 = I3.to(IL, DOWN);
    public final static FinalPointTransition L4 = I4.to(IL, RIGHT);


    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_59",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.MEDIUM)
                .withPathsSingleLines(whiteBold, getSinglePathFull())
                .withGridConf(Grid.Configuration.customRect(2 * RATIO_w, 2 * RATIO_h))
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_59_design")
                .addEquations(sequence(
                        "6 * KB + 3 * KC = KA",
                        "6 * KB + 3 * (KB/H) = KA",
                        "KB = KA/(6 + 3/H)",
                        "KA = 1.0",
                        "KB = KA / (6 + 3.0 / H)",
                        "KC = KB / H",
                        "KD = 2 * KB",
                        "AF = 3 * KC",
                        "KI = 3 * KB",

                        "FI = KA - KI - AF",
                        "FL = FI / P",
                        "IL = FL * H"
                ))
                .addImportantVertexes(Tile59.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        perimeter(1, HOR).apply(K),
                        diagonals(KA, VER).apply(K),
                        diagonals(KA, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        diagonalVertical(KA).apply(fpt(KA, LEFT)),
                        diagonalVertical(KA).apply(fpt(KA, RIGHT)),
                        diagonalHorizontal(KA).apply(fpt(KA, UP)),
                        diagonalHorizontal(KA).apply(fpt(KA, DOWN))
                )
                .addSinglePathsLines(red, getSinglePathFull())
                .withFontSize(15);

    }

    private static Sequence<Line> getSinglePathFull() {
        Sequence<Line> leftSide = join(
                getSinglePath(),
                getSinglePath().map(s -> s.mirror(Hex.mirrorVert))
        );
        return join(
                leftSide,
                leftSide.map(s -> s.mirror(Hex.mirrorHor))
        );
    }

    private static Sequence<Line> getSinglePath() {
        return sequence(
                line(E, D, C, D2, C2),
                line(C3, G2, F4, L4, E10, D4, C4, D3),
                line(D3, G, F),
                line(E, L, F, E2, D2, E3, F2),
                line(F3, E6, D6, E5, F2, L2, E4, D5, C5, D6, C6),
                line(F4, E8, D8, E7, F3, L3, E9, D7, C7, D8, C8)
        );
    }


    public static void main(String[] args) {
        Export.export(getDesignHelper());
        Export.export(getPayload().toSvgPayload());
    }


}