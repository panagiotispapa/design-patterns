package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
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
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.numbers.Integers.range;

public class Tile15 {
// p. 6

    public static final double RATIO_y = 1.0 / 5.0;
    public static final double RATIO_x = RATIO_y * H;

    private static final double RATIO_W = 6 * RATIO_x;
    private static final double RATIO_H = 1;

    public static final double KB = RATIO_y;
    public static final double KC = RATIO_x;

    public final static FinalPointTransition A = fpt(1, DR_V);
    public final static FinalPointTransition B = fpt(RATIO_y, DL_V);
    public final static FinalPointTransition C = fpt(RATIO_x, LEFT);
    public final static FinalPointTransition I1 = fpt(KB, UR_V);
    public final static FinalPointTransition I2 = fpt(2 * KC, RIGHT);
    public final static FinalPointTransition I3 = I2.to(4 * KB, UR_V);
    public final static FinalPointTransition I4 = fpt(5 * KB, UR_V);
    public final static FinalPointTransition I5 = I4.to(2 * KB, UP);
    public final static FinalPointTransition I6 = I5.to(KB, UL_V);
    public final static FinalPointTransition I7 = fpt(3 * KB, UP);
    public final static FinalPointTransition I8 = I7.to(KB, DR_V);
    public final static FinalPointTransition I9 = fpt(KB, UP);
    public final static FinalPointTransition I10 = I9.to(6 * KB, UR_V);


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_15",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsSingleLines(whiteBold, getAllSinglePath())
                .withGridConf(Grid.Configuration.customRect(2 * RATIO_W, 2 * RATIO_H))
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_15_design")
                .addEquations(sequence(
                        "KB = 1/5",
                        "KC = h * Ry",
                        "h = 5 * Ry",
                        "w = 6 * Rx"
                ))
                .addImportantVertexes(Tile15.class)
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KB)
                .withGridSize(16)
                .addImportantVertexes(
                        range(1, 6).flatMap(i -> sequence(
                                ImportantVertex.of(String.valueOf(i), fpt(i * RATIO_x, RIGHT)),
                                ImportantVertex.of(String.valueOf(i), fpt(i * RATIO_y, UR_V)),
                                ImportantVertex.of(String.valueOf(i), fpt(i * RATIO_y, DR_V))
                        ))
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(1, VER).apply(K),
                        diagonals(1, VER).apply(K),
                        diagonals(H, HOR).apply(K),
                        diagonalVertical(RATIO_H).apply(fpt(6 * RATIO_x, RIGHT)),
                        diagonalVertical(RATIO_H).apply(fpt(6 * RATIO_x, LEFT))
                )
                .addSinglePathsLines(
                        green,
                        diagonalHorizontal(RATIO_W).apply(fpt(5 * RATIO_y, UP)),
                        diagonalHorizontal(RATIO_W).apply(fpt(5 * RATIO_y, DOWN))
                )
                .addSinglePathsLines(red,
                        getAllSinglePath()
                );
    }

    private static Sequence<Line> getAllSinglePath() {
        return join(
                getSinglePathUp(),
                getSinglePathUp().map(p -> p.mirror(mirrorVert))
        );
    }

    private static Sequence<Line> getSinglePathUp() {
        return join(
                getSinglePathRight(),
                getSinglePathRight().map(p -> p.mirror(mirrorHor))
        );
    }

    private static Sequence<Line> getSinglePathRight() {
        return sequence(
                Line.line(I9, I10),
                Line.line(I8, I1, I2, I3, I4, I5, I6, I7, I8)
        );
    }

}