package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Payload;
import com.design.islamic.model.TileSupplier;
import com.google.common.base.Supplier;

import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

//p. 3
public class Tile12 {

    private static double RATIO_m = 1.0 / 7.0;
    private static double RATIO_w = 6 * H * RATIO_m;
    private static double RATIO_h = 5 * RATIO_m;
    private static double KA = RATIO_m;
    private static double KE = RATIO_w;
    private static double KF = RATIO_h;
    private static double KG = KA * H;

    public final static FinalPointTransition A = fpt(pt(KA, DR_V));

    public final static FinalPointTransition E = fpt(pt(KE, RIGHT));
    public final static FinalPointTransition E2 = fpt(pt(KE, LEFT));
    public final static FinalPointTransition E3 = fpt(pt(4 * KG, RIGHT));
    public final static FinalPointTransition F = fpt(pt(KF, UP));
    public final static FinalPointTransition F2 = fpt(pt(KF, DOWN));
    public final static FinalPointTransition G = fpt(pt(KG, LEFT));
    public final static FinalPointTransition I1 = fpt(pt(KA, UR_V));
    public final static FinalPointTransition I2 = fpt(pt(2 * KG, RIGHT));
    public final static FinalPointTransition I3 = fpt(pt(3 * KA, UP), pt(KA, DR_V));
    public final static FinalPointTransition K2 = fpt(pt(3 * KA, UP), pt(4 * KA, UR_V));
    public final static FinalPointTransition K3 = fpt(pt(2 * KA, UP), pt(5 * KA, UR_V));
    public final static FinalPointTransition I4 = fpt(pt(5 * KA, UR_V));
    public final static FinalPointTransition I5 = fpt(pt(6 * KA, UR_V));
    public final static FinalPointTransition I6 = I5.append(pt(KA, DOWN));
    public final static FinalPointTransition I7 = fpt(pt(4 * KA, UR_V));
    public final static FinalPointTransition L9 = fpt(pt(KA, UP));
    public final static FinalPointTransition K4 = L9.append(pt(6 * KA, UL_V));
    public final static FinalPointTransition K5 = L9.append(pt(6 * KA, UR_V));
    public final static FinalPointTransition K6 = fpt(pt(6 * KA, UP), pt(2 * KA, DR_V));
    public final static FinalPointTransition K7 = fpt(pt(4 * KA, UP), pt(2 * KA, DR_V));
    public final static FinalPointTransition K8 = fpt(pt(3 * KA, UP));


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_12",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsSingleLines(whiteBold,
                        getAllSinglesPath())
                .withGridConf(Grid.Configuration.customRect(2 * RATIO_w, 2 * RATIO_h))

                .build();

    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        List<String> equations = asList(
                "KB = KD = AC = 1 / 7",
                "EC = 0.5 - AB"
        );


        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_12_design")
                .addEquations(equations)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K)

                )
                .addSinglePathsLines(
                        gray,
                        IntStream.range(1, 7).mapToObj(i -> perimeter(i * KA, VER).apply(K)).flatMap(s -> s)
                )
                .addSinglePathsLines(
                        green,
                        diagonalHorizontal(KE).apply(F),
                        diagonalHorizontal(KE).apply(F2)
                )
                .addSinglePathsLines(
                        blue,
                        diagonalVertical(KF).apply(E),
                        diagonalVertical(KF).apply(E2)
                )
                .addImportantVertexes(Tile12.class)
                .addSinglePathsLines(red, getAllSinglesPath())
                ;

    }

    private static List<PointsPath> getAllSinglesPath() {
        return Stream.concat(
                getSinglesPath().get(),
                getSinglesPath().get().map(s -> s.mirror(Hex.mirrorVert))

        ).collect(toList());
    }

    private static Supplier<Stream<PointsPath>> getSinglesPath() {
        return () -> Stream.of(
                PointsPath.of(K8, I3, I1, I2, I6, I4, K3, K2, K8),
                PointsPath.of(K8, I3, I1, I2, I6, I4, K3, K2, K8).mirror(mirrorHor),
                PointsPath.of(K4, L9, K5),
                PointsPath.of(K6, K7, I7, E3),
                PointsPath.of(K6, K7, I7, E3).mirror(mirrorHor)

        );
    }

}