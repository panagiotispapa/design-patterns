package com.design.arabic.model.hex;

import com.design.common.*;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.RatioHelper.P6.H;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.numbers.Integers.range;

//p. 10
public class Tile20 {


    private static double KA = 1.0 / 6.0;
    private static double KB = KA * H;

    public final static FinalPointTransition A = fpt(KA, UL_H);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition I1 = fpt(right(2));
    public final static FinalPointTransition I2 = fpt(right(3));
    public final static FinalPointTransition I3 = fpt(right(4));
    public final static FinalPointTransition I4 = I3.to(ur(2));
    public final static FinalPointTransition I5 = I2.to(ur(1));
    public final static FinalPointTransition I6 = I2.to(ur(2));
    public final static FinalPointTransition I7 = I2.to(ur(4));
    public final static FinalPointTransition I8 = I1.to(ur(1));
    public final static FinalPointTransition I9 = I1.to(ur(2));
    public final static FinalPointTransition L1 = I1.to(ur(3));
    public final static FinalPointTransition L2 = fpt(ur(2));
    public final static FinalPointTransition L3 = fpt(ur(3));
    public final static FinalPointTransition L4 = fpt(ur(4));
    public final static FinalPointTransition L5 = L3.to(right(1));
    public final static FinalPointTransition L6 = L2.to(right(1));

    private static PointTransition up(Integer times) {
        return pt(times * KA, UP);
    }

    private static PointTransition right(Integer times) {
        return pt(times * KA, RIGHT);
    }

    private static PointTransition left(Integer times) {
        return pt(times * KA, LEFT);
    }

    private static PointTransition ur(Integer times) {
        return pt(times * KA, UR_H);
    }

    private static PointTransition dr(Integer times) {
        return pt(times * KA, DR_H);
    }


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_20",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
//                .withGridConf(Grid.Configuration.customRect(2*RATIO_w, 2*RATIO_h))
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_20_design")
                .addEquations(sequence(
                        "KA = 1 / 6"
                ))
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KA)
                .withGridSize(16)
                .addImportantVertexes(Tile20.class)
                .addImportantVertexes(
                        range(1, 6).flatMap(i -> sequence(
                                ImportantVertex.of(String.valueOf(i), right(i)),
                                ImportantVertex.of(String.valueOf(i), ur(i)),
                                ImportantVertex.of(String.valueOf(i), dr(i))
                        ))
                )
                .addSinglePathsLines(gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K)

                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(L2, I1, I8),
                Line.line(I2, L6, I9, L1, L3),
                Line.line(L5, L4, I7, I5, I9, L1, L5),
                Line.line(I6, I4, I3)
        );
    }

}