package com.design.deco;

import com.design.common.*;
import com.design.common.model.Style;
import com.design.islamic.model.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.islamic.model.Rect.Corner.*;
import static com.design.islamic.model.Rect.*;
import static java.util.Arrays.asList;

public class Tile5 {

    private static final double KA = 1.0;
    private static final double Ratio_m = H / 2.0;
    private static final double KC = Ratio_m;

    public final static FinalPointTransition A = fpt(pt(KA, DR));
    public final static FinalPointTransition B = fpt(pt(H, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KC, RIGHT));


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
    public static PayloadSimple getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("deco_tile_05",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewSingleLines(whiteBold, getSinglePath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        List<String> equations = Arrays.asList(
                "KB = KA / 5.0"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "deco_tile_05_design")
                .addEquations(equations)
                .addImportantVertexes(Tile5.class)
                .addSinglePathsLines(
                        gray,
                        IntStream.rangeClosed(1, 2)
                                .mapToObj(i ->
                                        Stream.of(
                                                diagonalHorizontal(H).apply(fpt(u(i))),
                                                diagonalHorizontal(H).apply(fpt(d(i))),
                                                diagonalVertical(H).apply(fpt(r(i))),
                                                diagonalVertical(H).apply(fpt(l(i)))
                                        ).flatMap(s -> s)
                                ).flatMap(s -> s),
                        diagonals(H, VER).apply(K)
                )
                .addSinglePathsLines(red, getSinglePath())
                ;
    }

    private static List<PointsPath> getSinglePath() {
        return asList(
                PointsPath.of(K, B, fpt(u(2), r(2))),
                PointsPath.of(fpt(u(1), l(1)), fpt(u(1), r(2))),
                PointsPath.of(fpt(u(2), l(1)), fpt(d(1), l(1))),
                PointsPath.of(fpt(u(1)), fpt(d(2))),
                PointsPath.of(fpt(r(1)), fpt(d(2), r(1))),
                PointsPath.of(fpt(d(1)), fpt(d(1), l(2)), fpt(d(2), l(2)), fpt(d(2), l(2)), fpt(d(2), r(1))),
                PointsPath.of(fpt(d(1), r(1)), fpt(d(1), r(2)), fpt(d(2), r(2))),
                PointsPath.of(fpt(l(1)), fpt(l(2)), fpt(u(2), l(2)), fpt(u(2), r(1)), fpt(u(1), r(1)))

        );
    }

}