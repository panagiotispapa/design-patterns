package com.design.islamic.model.hex;

import com.design.common.*;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

//p.
public class Tile20 {

    private static double RATIO_m = 1.0 / 6.0;
    private static double KA = RATIO_m;

    public final static FinalPointTransition A = fpt(pt(KA, UL_H));
    public final static FinalPointTransition I1 = fpt(right(2));
    public final static FinalPointTransition I2 = fpt(right(3));
    public final static FinalPointTransition I3 = fpt(right(4));
    public final static FinalPointTransition I4 = I3.append(ur(2));
    public final static FinalPointTransition I5 = I2.append(ur(1));
    public final static FinalPointTransition I6 = I2.append(ur(2));
    public final static FinalPointTransition I7 = I2.append(ur(4));
    public final static FinalPointTransition I8 = I1.append(ur(1));
    public final static FinalPointTransition I9 = I1.append(ur(2));
    public final static FinalPointTransition L1 = I1.append(ur(3));
    public final static FinalPointTransition L2 = fpt(ur(2));
    public final static FinalPointTransition L3 = fpt(ur(3));
    public final static FinalPointTransition L4 = fpt(ur(4));
    public final static FinalPointTransition L5 = L3.append(right(1));
    public final static FinalPointTransition L6 = L2.append(right(1));

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
    public static PayloadSimple getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_20",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold, getFullPath())
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

        List<String> equations = asList(
                "r = 1 / 6"
        );


        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_20_design")
                .addEquations(equations)
                .addSinglePathsLines(
                        gray,
                        IntStream.rangeClosed(1, 6)
                                .mapToObj(i ->
                                        Stream.of(
                                                diagonalLeftToRightHor(1.0).apply(fpt(right(i))),
                                                diagonalLeftToRightHor(1.0).apply(fpt(left(i))),
                                                diagonalRightToLeftHor(1.0).apply(fpt(right(i))),
                                                diagonalRightToLeftHor(1.0).apply(fpt(left(i)))
                                        ).flatMap(s -> s)
                                ).flatMap(s -> s)
                )
                .addImportantVertexes(Tile20.class)
                .addImportantVertexes(
                        IntStream.rangeClosed(1, 6).mapToObj(i -> Stream.of(
                                ImportantVertex.of(String.valueOf(i), right(i)),
                                ImportantVertex.of(String.valueOf(i), ur(i)),
                                ImportantVertex.of(String.valueOf(i), dr(i))
                        )).flatMap(s -> s)
                )
                .addSinglePathsLines(gray,
                        IntStream.rangeClosed(1, 6).mapToObj(i ->
                                perimeter(i * KA, HOR).apply(K)).flatMap(s -> s),
                        diagonals(1.0, HOR).apply(K)

                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(L2, I1, I8),
                PointsPath.of(I2, L6, I9, L1, L3),
                PointsPath.of(L5, L4, I7, I5, I9, L1, L5),
                PointsPath.of(I6, I4, I3)
        );
    }

}