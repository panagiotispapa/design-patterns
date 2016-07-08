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

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Tile15 {

    public static final double RATIO_y = 1.0 / 5.0;
    public static final double RATIO_x = RATIO_y * H;

    private static final double RATIO_W = 6.0 * RATIO_x;
    private static final double RATIO_H = 1.0;

    public static final double KB = RATIO_y;
    public static final double KC = RATIO_x;

    public final static FinalPointTransition A = fpt(pt(1.0, DR_V));
    public final static FinalPointTransition B = fpt(pt(RATIO_y, DL_V));
    public final static FinalPointTransition C = fpt(pt(RATIO_x, LEFT));
    public final static FinalPointTransition I1 = fpt(pt(KB, UR_V));
    public final static FinalPointTransition I2 = fpt(pt(2 * KC, RIGHT));
    public final static FinalPointTransition I3 = I2.append(pt(4 * KB, UR_V));
    public final static FinalPointTransition I4 = fpt(pt(5 * KB, UR_V));
    public final static FinalPointTransition I5 = I4.append(pt(2 * KB, UP));
    public final static FinalPointTransition I6 = I5.append(pt(KB, UL_V));
    public final static FinalPointTransition I7 = fpt(pt(3 * KB, UP));
    public final static FinalPointTransition I8 = I7.append(pt(KB, DR_V));
    public final static FinalPointTransition I9 = fpt(pt(KB, UP));
    public final static FinalPointTransition I10 = I9.append(pt(6 * KB, UR_V));


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_15",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsSingleLines(whiteBold, getAllSinglePath())
                .withGridConf(Grid.Configuration.customRect(2.0 * RATIO_W, 2 * RATIO_H))
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        List<String> equations = Arrays.asList(
                "KB = 1/5",
                "KC = h * Ry",
                "h = 5 * Ry",
                "w = 6 * Rx"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_15_design")
                .addEquations(equations)
                .addImportantVertexes(Tile15.class)
                .addSinglePathsLines(
                        gray,
                        IntStream.rangeClosed(1, 6)
                                .mapToObj(i ->
                                        Stream.of(
                                                diagonalLeftToRightVert(1.0).apply(fpt(pt(i * RATIO_x, DR_H))),
                                                diagonalLeftToRightVert(1.0).apply(fpt(pt(i * RATIO_x, UL_H))),
                                                diagonalRightToLeftVert(1.0).apply(fpt(pt(i * RATIO_x, DL_H))),
                                                diagonalRightToLeftVert(1.0).apply(fpt(pt(i * RATIO_x, UR_H))),
                                                diagonalVertical(1.0).apply(fpt(pt(i * RATIO_x, RIGHT))),
                                                diagonalVertical(1.0).apply(fpt(pt(i * RATIO_x, LEFT)))
                                        ).flatMap(s -> s)
                                ).flatMap(s -> s)
                )
                .addImportantVertexes(
                        IntStream.rangeClosed(1, 6).mapToObj(i -> Stream.of(
                                ImportantVertex.of(String.valueOf(i), fpt(pt(i * RATIO_x, RIGHT))),
                                ImportantVertex.of(String.valueOf(i), fpt(pt(i * RATIO_y, UR_V))),
                                ImportantVertex.of(String.valueOf(i), fpt(pt(i * RATIO_y, DR_V)))
                        )).flatMap(s -> s)
                )

                .addSinglePathsLines(
                        blue,
                        perimeter(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K),
                        diagonals(H, HOR).apply(K),
                        diagonalVertical(RATIO_H).apply(fpt(pt(6 * RATIO_x, RIGHT))),
                        diagonalVertical(RATIO_H).apply(fpt(pt(6 * RATIO_x, LEFT)))
                )
                .addSinglePathsLines(
                        green,
                        diagonalHorizontal(RATIO_W).apply(fpt(pt(5 * RATIO_y, UP))),
                        diagonalHorizontal(RATIO_W).apply(fpt(pt(5 * RATIO_y, DOWN)))
                )
                .addSinglePathsLines(red,
                        getAllSinglePath()
                );
    }

    private static List<PointsPath> getAllSinglePath() {
        return Stream.concat(
                getSinglePathUp().stream(),
                getSinglePathUp().stream().map(p -> p.mirror(mirrorVert))
        ).collect(toList());
    }

    private static List<PointsPath> getSinglePathUp() {
        return Stream.concat(
                getSinglePathRight().stream(),
                getSinglePathRight().stream().map(p -> p.mirror(mirrorHor))
        ).collect(toList());
    }

    private static List<PointsPath> getSinglePathRight() {
        return asList(
                PointsPath.of(I9, I10),
                PointsPath.of(I8, I1, I2, I3, I4, I5, I6, I7, I8)
        );
    }

}