package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

public class TileStar {

    public static double RATIO_1 = 0.5;
    public static double RATIO_2 = H / (H + 0.5);
    public static double RATIO_3 = (1.5 - H) * 0.5;

    private static final double KB = 0.5;
    private static final double KC = H;

    public final static FinalPointTransition A = fpt(pt(1.0, DR_V));
    public final static FinalPointTransition B = fpt(pt(KB, DR_V));
    public final static FinalPointTransition C = fpt(pt(KC, RIGHT));
    public final static FinalPointTransition D = fpt(pt(KC, DR_H));

    public final static FinalPointTransition A1 = fpt(pt(1.0, RIGHT));
    public final static FinalPointTransition B1 = fpt(pt(H, DR_V));
    public final static FinalPointTransition C1 = fpt(pt(H, UR_V));
    public final static FinalPointTransition D1 = fpt(pt(RATIO_1, RIGHT));

    public final static FinalPointTransition A2 = fpt(pt(RATIO_2 * H, RIGHT));
    public final static FinalPointTransition B2 = fpt(pt(RATIO_2, UR_V));
    public final static FinalPointTransition C2 = fpt(pt(H, RIGHT));
    public final static FinalPointTransition D2 = fpt(pt(RATIO_2, DR_V));

    public final static FinalPointTransition A3 = fpt(pt(1.0, DR_V));
    public final static FinalPointTransition B3 = A3.append(pt(H - 0.5, UR_V));
    public final static FinalPointTransition F3 = A3.append(pt(0.5 * (H - 0.5), UP));
    public final static FinalPointTransition C3 = B3.append(pt(1.5 - H, UP));
    public final static FinalPointTransition D3 = fpt(pt(H, RIGHT));
    public final static FinalPointTransition E3 = fpt(pt(H, DR_H));
    public final static FinalPointTransition G3 = fpt(pt(RATIO_3, DOWN));


    @TileSupplier
    public static PayloadSimple getPayloadSimple1() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple
                .Builder(
                "hex_tile_star_01",
                Hex.ALL_VERTEX_INDEXES
        ).withPathsNewFull(whiteBold, getFullPath1())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple2() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_star_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold, getFullPath2())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple3() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_star_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold, getFullPath3())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple1b() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple
                .Builder(
                "hex_tile_star_01b",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold, getFullPath1b())
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple2b() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple
                .Builder(
                "hex_tile_star_02b",
                Hex.ALL_VERTEX_INDEXES
        ).withPathsNewFull(whiteBold, getFullPath2b())
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple3b() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple
                .Builder(
                "hex_tile_star_03b",
                Hex.ALL_VERTEX_INDEXES
        ).withPathsNewFull(whiteBold, getFullPath3b())
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper1() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_01_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addImportantVertexes(
                        ImportantVertex.of("A", A),
                        ImportantVertex.of("B", B),
                        ImportantVertex.of("C", C),
                        ImportantVertex.of("D", D)
                )
//                .addEquations(equations)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KC, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(KB, VER).apply(K)
                )
                .addFullPaths(red, getFullPath1())
                ;

    }

    private static List<PointsPath> getFullPath2() {
        return asList(
                PointsPath.of(D2, C2, B2)
        );
    }

    private static List<PointsPath> getFullPath3() {
        return asList(
                PointsPath.of(fpt(pt(RATIO_3, DR_V)), fpt(pt(H, RIGHT)), fpt(pt(RATIO_3, UR_V)))
        );
    }

    private static List<PointsPath> getFullPath1() {
        return asList(
                PointsPath.of(D, B, C)
        );
    }

    private static List<PointsPath> getFullPath1b() {
        return asList(
                PointsPath.of(B1, D1, C1)
        );
    }

    private static List<PointsPath> getFullPath2b() {
        return asList(
                PointsPath.of(fpt(pt(H, UR_V)), fpt(pt(RATIO_2, RIGHT)), fpt(pt(H, DR_V)))
        );
    }

    private static List<PointsPath> getFullPath3b() {
        return asList(
                PointsPath.of(fpt(pt(RATIO_3, RIGHT)), fpt(pt(H, DR_V)), fpt(pt(RATIO_3, DR_H)))
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_02_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addEquations(asList(
                        "AB=AC",
                        "KB=h/(h+0.5)"
                ))
                .addImportantVertexes(
                        ImportantVertex.of("A", A2),
                        ImportantVertex.of("B", B2),
                        ImportantVertex.of("C", C2),
                        ImportantVertex.of("D", D2)
                )
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K),
                        diagonals(H, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(0.5, VER).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(RATIO_2, VER).apply(K)
                )
                .addFullPaths(red, getFullPath2())
                ;

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper3() {
        Style blueLight = new Style.Builder(Color.BLUE, 1).withStrokeOpcacity(0.3).build();
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
                .addFullPaths(red, getFullPath3())
                .addEquations(asList(
                        "KA = 1",
                        "KE = EB = h",
                        "AB = EB - EA = h - 0.5",
                        "AF = 0.5 * AB",
                        "DF = AD - AF = 0.5 - 0.5 * AB",
                        "BC = 2 * DF = 1 - AB = 1.5 - h"
                ))
                .addImportantVertexes(
                        ImportantVertex.of("A", A3),
                        ImportantVertex.of("B", B3),
                        ImportantVertex.of("C", C3),
                        ImportantVertex.of("D", D3),
                        ImportantVertex.of("E", E3),
                        ImportantVertex.of("F", F3),
                        ImportantVertex.of("G", G3)
                )
                .addSinglePathsLines(gray,
                        diagonals(1.0, VER).apply(K),
                        perimeter(1.5 - H, VER).apply(fpt(pt(2 * H, RIGHT))),
                        diagonals(1.5 - H, VER).apply(fpt(pt(2 * H, RIGHT)))

                )
                .addSinglePathsLines(
                        green,
                        perimeter(H, HOR).apply(K),
                        Stream.of(PointsPath.of(C3, D3, B3))
                )
                .addFullPaths(
                        gray,
                        diagonalVertical(1.0).apply(D3)
                )
                .addFullPaths(
                        blue,
                        diagonalHorizontal((H + H * (H - 0.5))).apply(G3)
                )
                .addCircleWithRadius(
                        blueLight,
                        Pair.of(fpt(pt(H, RIGHT)), H),
                        Pair.of(fpt(pt(H, LEFT)), H),
                        Pair.of(fpt(pt(H, UL_H)), H),
                        Pair.of(fpt(pt(H, UR_H)), H),
                        Pair.of(fpt(pt(H, DL_H)), H),
                        Pair.of(fpt(pt(H, DR_H)), H)
                )
                ;

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper1b() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_01b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
//                .addFullPaths(() -> getPayloadSimple1b().getPathsFull(), red)
//                .addEquations(equations)
                .addImportantVertexes(
                        ImportantVertex.of("A1", A1),
                        ImportantVertex.of("B1", B1),
                        ImportantVertex.of("C1", C1),
                        ImportantVertex.of("D1", D1)
                )
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(RATIO_1, HOR).apply(K)
                ).addFullPaths(red, getFullPath1b());

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2b() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_02b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath2b())
//                .addEquations(equations)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(RATIO_2, HOR).apply(K)
                )
                ;

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper3b() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_03b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath3b())
//                .addEquations(equations)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(RATIO_3, HOR).apply(K)
                )
                ;

    }

}