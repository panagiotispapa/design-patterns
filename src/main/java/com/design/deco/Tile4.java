package com.design.deco;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.*;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.arabic.model.Rect.Vertex.*;
import static com.design.arabic.model.Rect.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile4 {

    private static final double KA = 1.0;
    private static final double KB = KA / 5.0;
    private static final double KC = KA * H;
    private static final double CD = KB;
    private static final double CE = CD * H;

    public final static FinalPointTransition A = fpt(KA, RIGHT);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition B2 = fpt(KB, LEFT);
    public final static FinalPointTransition C = fpt(KC, DR);
    public final static FinalPointTransition D = C.to(CD, LEFT);
    public final static FinalPointTransition E = C.to(CE, DL);

//    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("deco_tile_04",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT3.getConfiguration())
                .build();
    }

//    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "deco_tile_04_design")
                .addFullPaths(red, getFullPath())
                .addEquations(
                        "KB = KA / 5.0"
                )
                .addImportantVertexes(Tile4.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(CD, VER).apply(C),
                        perimeter(CE, HOR).apply(C)
                )
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(E, B2)
        );
    }

}