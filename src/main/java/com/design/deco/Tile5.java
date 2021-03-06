package com.design.deco;

import com.design.common.*;
import com.design.common.model.Style;
import com.design.arabic.model.*;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.common.PointTransition.pt;
import static com.design.arabic.model.Rect.Vertex.*;
import static com.design.arabic.model.Rect.*;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.numbers.Integers.range;

public class Tile5 {

    private static final double KA = 1.0;
    private static final double Ratio_m = H / 2.0;
    private static final double KC = Ratio_m;

    public final static FinalPointTransition A = fpt(KA, DR);
    public final static FinalPointTransition B = fpt(H, RIGHT);
    public final static FinalPointTransition C = fpt(KC, RIGHT);


    private static PointTransition u(int times) {
        return pt(times * KC, UP);
    }

    private static PointTransition d(int times) {
        return pt(times * KC, DOWN);
    }

    private static PointTransition l(int times) {
        return pt(times * KC, LEFT);
    }

    private static PointTransition r(int times) {
        return pt(times * KC, RIGHT);
    }

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("deco_tile_05",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsSingleLines(whiteBold, getSinglePath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "deco_tile_05_design")
                .addEquations(
                        "KB = KA / 5.0"
                )
                .addImportantVertexes(Tile5.class)
                .addSinglePathsLines(
                        gray,
                        range(1, 2)
                                .flatMap(i ->
                                        sequence(
                                                diagonalHorizontal(H).apply(fpt(u(i))),
                                                diagonalHorizontal(H).apply(fpt(d(i))),
                                                diagonalVertical(H).apply(fpt(r(i))),
                                                diagonalVertical(H).apply(fpt(l(i)))
                                        ).flatMap(s -> s)
                                ),
                        diagonals(H, VER).apply(K)
                )
                .addSinglePathsLines(red, getSinglePath())
                ;
    }

    private static Sequence<Line> getSinglePath() {
        return sequence(
                Line.line(K, B, fpt(u(2), r(2))),
                Line.line(fpt(u(1), l(1)), fpt(u(1), r(2))),
                Line.line(fpt(u(2), l(1)), fpt(d(1), l(1))),
                Line.line(fpt(u(1)), fpt(d(2))),
                Line.line(fpt(r(1)), fpt(d(2), r(1))),
                Line.line(fpt(d(1)), fpt(d(1), l(2)), fpt(d(2), l(2)), fpt(d(2), l(2)), fpt(d(2), r(1))),
                Line.line(fpt(d(1), r(1)), fpt(d(1), r(2)), fpt(d(2), r(2))),
                Line.line(fpt(l(1)), fpt(l(2)), fpt(u(2), l(2)), fpt(u(2), r(1)), fpt(u(1), r(1)))

        );
    }

}