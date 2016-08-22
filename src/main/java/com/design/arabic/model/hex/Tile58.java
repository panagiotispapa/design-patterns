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
import static com.design.arabic.model.Hex.*;
import static com.design.common.CircleInstruction.circleInstruction;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile58 {

    // p. 27

    private static double RATIO_w = 1.0;
    private static double RATIO_h = RATIO_w;

    // KB + BC = KA
    // P * BD + H * BD = KA
    // BD = KA / (H + P)

    public static double KA = 1.0;
    public static double BD = KA / (H + P);
    public static double KB = BD * H;
    public static double KC = KA;
    public static double KD = BD * P;
    public static double AF = BD * P;
    public static double KG = KB / 2;
    public static double AI = (AF /2 ) / H;

    public final static FinalPointTransition A = fpt(1, LEFT);
    public final static FinalPointTransition A2 = A.to(1, UP);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = fpt(KD, LEFT);
    public final static FinalPointTransition E = C.to(KB, LEFT);
    public final static FinalPointTransition F = A.to(AF, UP);
    public final static FinalPointTransition G = fpt(KG, UP);
    public final static FinalPointTransition I = A.to(AI, UR_H);
    public final static FinalPointTransition F2 = A.to(AF, UR_V);
    public final static FinalPointTransition I2 = A.to(AI, RIGHT);
    public final static FinalPointTransition I3 = fpt(AI, UL_V);
    public final static FinalPointTransition I4 = fpt(AI, UP);
    public final static FinalPointTransition F3 = fpt(AF, UL_H);
    public final static FinalPointTransition I5 = C.to(AI, DL_H);
    public final static FinalPointTransition I6 = C.to(AI, LEFT);
    public final static FinalPointTransition F4 = C.to(AF, DL_V);
    public final static FinalPointTransition I7 = A2.to(AI, DR_V);
    public final static FinalPointTransition F5 = A2.to(AF, DR_H);
    public final static FinalPointTransition I8 = A2.to(AI, DOWN);
    public final static FinalPointTransition J = F2.to(AI, RIGHT);
    public final static FinalPointTransition J2 = F2.to(AI, UP);
    public final static FinalPointTransition J3 = F3.to(AI, LEFT);
    public final static FinalPointTransition J4 = F3.to(AI, UP);
    public final static FinalPointTransition J5 = F4.to(AI, LEFT);
    public final static FinalPointTransition J6 = F4.to(AI, DOWN);
    public final static FinalPointTransition J7 = F5.to(AI, RIGHT);
    public final static FinalPointTransition J8 = F5.to(AI, DOWN);

    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_58",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.SMALL)
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_58_design")
                .addEquations(sequence(
                        "KB + BC = KA",
                        "H * BD + P * BD = KA",
                        "BD = KA / (H + P)",
                        "KA = 1.0",
                        "BD = KA / (H + P)",
                        "KB = BD * H",
                        "KC = KA",
                        "KD = BD * P",
                        "AF = BD * P",
                        "KG = KB / 2",
                        "AI = (AF /2 ) / H"
                ))
                .addImportantVertexes(Tile58.class)
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
                .addSinglePathsLines(
                        blue,
                        line(B, E, F, D, B),
//                        line(),
                        line()
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
                line(F, I, F2, I2),
                line(D, I3, F3, I4),
                line(B, I5, F4, I6),
                line(E, I7, F5, I8),
                line(D, J, F2, J2, J6, F4, J5, E),
                line(B, J4, F3, J3, J7, F5, J8, F)
        );
    }


    public static void main(String[] args) {
        Export.export(getDesignHelper());
        Export.export(getPayload().toSvgPayload());
    }


}