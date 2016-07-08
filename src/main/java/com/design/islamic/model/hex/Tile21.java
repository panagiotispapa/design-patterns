package com.design.islamic.model.hex;

import com.design.common.*;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Payload;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

//p.
public class Tile21 {

    private static double RATIO_m = 1.0 / 6.0;
    private static double RATIO_n = 1.0 / 16.0;
    private static double KA = RATIO_m;
    private static double KB = RATIO_n;

    public final static FinalPointTransition A = fpt(pt(KA, UL_H));
    public final static FinalPointTransition B = fpt(pt(KB, UL_H));
    public final static FinalPointTransition I1 = fpt(pt(KA, RIGHT));
    public final static FinalPointTransition I2 = fpt(pt(3 * KA, RIGHT));
    public final static FinalPointTransition I3 = fpt(pt(KA, UR_H));
    public final static FinalPointTransition I4 = fpt(pt(3 * KA, UR_H));
    public final static FinalPointTransition I5 = I1.append(pt(2 * KA, UR_H));
    public final static FinalPointTransition I6 = I3.append(pt(2 * KA, RIGHT));
    public final static FinalPointTransition I7 = I6.append(pt(KA, UR_H));
    public final static FinalPointTransition L1 = I4.append(pt(5 * KB, RIGHT));
    public final static FinalPointTransition L2 = L1.append(pt(2 * KB, UL_H));
    public final static FinalPointTransition L3 = L2.append(pt(2 * KB, LEFT));
    public final static FinalPointTransition L4 = L3.append(pt(5 * KB, UR_H));
    public final static FinalPointTransition L5 = L2.append(pt(2 * KB, UR_H));
    public final static FinalPointTransition L6 = L5.append(pt(9 * KB, DR_H));
    public final static FinalPointTransition L7 = L6.append(pt(2 * KB, LEFT));
    public final static FinalPointTransition L8 = L7.append(pt(2 * KB, UL_H));
    public final static FinalPointTransition L9 = L7.append(pt(2 * KB, DL_H));
    public final static FinalPointTransition L10 = L9.append(pt(5 * KB, RIGHT));


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_21",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withSize(Payload.Size.MEDIUM)
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
                "r = 1 / 6",
                "r2 = 1 / 16"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_21_design")
                .addEquations(equations)
                .addSinglePathsLines(
                        gray,
                        IntStream.rangeClosed(1, 6)
                                .mapToObj(i ->
                                        Stream.of(
                                                diagonalLeftToRightHor(1.0).apply(fpt(pt(i * KA, RIGHT))),
                                                diagonalLeftToRightHor(1.0).apply(fpt(pt(i * KA, LEFT))),
                                                diagonalRightToLeftHor(1.0).apply(fpt(pt(i * KA, RIGHT))),
                                                diagonalRightToLeftHor(1.0).apply(fpt(pt(i * KA, LEFT)))
                                        ).flatMap(s -> s)
                                ).flatMap(s -> s)
                )
                .addImportantVertexes(Tile21.class)
                .addImportantVertexes(
                        IntStream.rangeClosed(1, 6).mapToObj(i -> Stream.of(
                                DesignHelper.ImportantVertex.of(String.valueOf(i), pt(i * KA, RIGHT)),
                                DesignHelper.ImportantVertex.of(String.valueOf(i), pt(i * KA, UR_H)),
                                DesignHelper.ImportantVertex.of(String.valueOf(i), pt(i * KA, DR_H))
                        )).flatMap(s -> s)
                )
                .addSinglePathsLines(blue,
                        IntStream.range(1, 6).mapToObj(i ->
                                perimeter(i * KA, HOR).apply(K)).flatMap(s -> s)
                )
                .addSinglePathsLines(gray,
                        IntStream.range(1, 16).mapToObj(i ->
                                perimeter(i * KB, HOR).apply(K)).flatMap(s -> s)
                )
                .addSinglePathsLines(gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(RatioHelper.P6.H,VER ).apply(K)

                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(I1, I5, I7, I6, I3),
                PointsPath.of(I6, I2, L8, L7, L6, L5, L2, L3, L4),
                PointsPath.of(I5, I4, L1, L2),
                PointsPath.of(L7, L9, L10)
        );
    }


}