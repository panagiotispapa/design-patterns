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
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class TileStar3 {

    private static final double KA = 1;
    private static final double KE = H;
    private static final double EB = KE;
    private static final double EA = KA / 2;
    private static final double AD = KA / 2;
    private static final double AB = EB - EA;
    private static final double AF = AB * P;
    private static final double DF = AD - AF;
    private static final double BC = 2 * DF;
    private static final double AI = EA * P;
    private static final double EI = EA * H;
    private static final double IL = EI;
    private static final double KL = KA - IL - AI;



    public final static FinalPointTransition A = fpt(KA, DR_V);
    public final static FinalPointTransition B = A.to(AB, UR_V);
    public final static FinalPointTransition F = A.to(AF, UP);
    public final static FinalPointTransition C = B.to(BC, UP);
    public final static FinalPointTransition D = fpt(H, RIGHT);
    public final static FinalPointTransition E = fpt(H, DR_H);
    public final static FinalPointTransition G = fpt(KL, DOWN);
    public final static FinalPointTransition L = fpt(KL, DR_V);
    public final static FinalPointTransition I = A.to(AI, UL_V);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_star_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blueLight = new Style.Builder(Color.BLUE, 1).withStrokeOpacity(0.3).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        final double KA = 1;
        final double KE = KA * H;
        final double KD = KE;
        final double EB = KE;
        final double AB = EB - KA * 0.5;
        final double AF = AB * P;

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_03_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath())
                .addEquations(sequence(
                        "KA = 1",
                        "KE = EB = h",
                        "AB = EB - EA = h - 0.5",
                        "AF = 0.5 * AB",
                        "DF = AD - AF = 0.5 - 0.5 * AB",
                        "BC = 2 * DF = 1 - AB = 1.5 - h",
                        "IL = EI"
                ))
                .addImportantVertexes(
                        TileStar3.class
                )
                .addSinglePathsLines(gray,
                        diagonals(1, VER).apply(K),
                        perimeter(1.5 - H, VER).apply(fpt(2 * H, RIGHT)),
                        diagonals(1.5 - H, VER).apply(fpt(2 * H, RIGHT))

                )
                .addSinglePathsLines(
                        green,
                        perimeter(H, HOR).apply(K),
                        sequence(Line.line(C, D, B))
                )
                .addFullPaths(
                        gray,
                        diagonalVertical(1).apply(D)
                )
                .addFullPaths(
                        blue,
                        diagonalHorizontal((H + H * (H - 0.5))).apply(G)
                )
                .addCircleWithRadius(
                        blueLight,
                        circleInstruction(fpt(H, RIGHT), H),
                        circleInstruction(fpt(H, LEFT), H),
                        circleInstruction(fpt(H, UL_H), H),
                        circleInstruction(fpt(H, UR_H), H),
                        circleInstruction(fpt(H, DL_H), H),
                        circleInstruction(fpt(H, DR_H), H)
                )
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(G, E, L)
        );
    }

}
