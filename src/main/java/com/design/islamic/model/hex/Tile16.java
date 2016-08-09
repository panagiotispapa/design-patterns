package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
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
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.numbers.Integers.range;

//p.
public class Tile16 {
    private static double RATIO_m = 1.0 / 9.0;
    private static double KA = RATIO_m;
    private static double KG = H * KA;

    public final static FinalPointTransition A = fpt(pt(KA, UL_V));
    public final static FinalPointTransition G = fpt(pt(KG, LEFT));
    public final static FinalPointTransition I1 = fpt(pt(8 * KA, UP));
    public final static FinalPointTransition I2 = udr(8, 5);
    public final static FinalPointTransition I3 = udr(6, 3);
    public final static FinalPointTransition I4 = udr(6, 1);
    public final static FinalPointTransition I5 = udr(4, 3);
    public final static FinalPointTransition I6 = udr(9, 8);
    public final static FinalPointTransition I7 = fpt(pt(KA, UR_V));
    public final static FinalPointTransition I8 = fpt(pt(KA, DOWN));
    public final static FinalPointTransition I9 = fpt(pt(8 * KA, DR_V));

    public final static FinalPointTransition L1 = dur(1, 4);
    public final static FinalPointTransition L2 = dur(1, 9);
    public final static FinalPointTransition L3 = dur(3, 6);
    public final static FinalPointTransition L4 = dur(3, 8);
    public final static FinalPointTransition L5 = dur(5, 6);
    public final static FinalPointTransition L6 = dur(4, 1);
    public final static FinalPointTransition L7 = dur(6, 3);
    public final static FinalPointTransition L8 = dur(6, 5);
    public final static FinalPointTransition L9 = dur(8, 3);
    public final static FinalPointTransition L10 = dur(9, 1);

    private static FinalPointTransition udr(int up, int downRight) {
        return fpt(pt(up * KA, UP), pt(downRight * KA, DR_V));
    }

    private static FinalPointTransition dur(int down, int upRight) {
        return fpt(pt(down * KA, DOWN), pt(upRight * KA, UR_V));
    }

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_16",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsSingleLines(whiteBold, getAllSinglePath())
                .build();

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_16_design")
                .addEquations(sequence(
                        "KA = 1 / 9"
                ))
                .addImportantVertexes(Tile16.class)
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .withGridRatio(KA)
                .withGridSize(24)

                .addImportantVertexes(
                        range(1, 9).flatMap(i -> sequence(
                                ImportantVertex.of(String.valueOf(i), fpt(pt(i * KA, UR_V))),
                                ImportantVertex.of(String.valueOf(i), fpt(pt(i * KA, DR_V))),
                                ImportantVertex.of(String.valueOf(i), fpt(pt(i * KA, DOWN))),
                                ImportantVertex.of(String.valueOf(i), fpt(pt(i * KA, UP)))
                        ))
                )
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K)
                )
                .addSinglePathsLines(red,
                        getAllSinglePath()
                );

    }

    private static Sequence<PointsPath> getAllSinglePath() {
        return join(
                getSinglePath(),
                getSinglePath().map(p -> p.mirror(mirrorHor))
        );
    }

    private static Sequence<PointsPath> getSinglePath() {
        return sequence(
                PointsPath.of(I1, I2, I3, I4, I7, L5, L3, L4, I9, L9, L7, L6, L10),
                PointsPath.of(I3, I5, I6),
                PointsPath.of(L2, L1, L3),
                PointsPath.of(L7, L8, I8)
        );
    }

}