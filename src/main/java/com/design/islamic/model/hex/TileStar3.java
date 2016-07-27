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
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

public class TileStar3 {

    private static final double KA = 1.0;
    private static final double KE = H;
    private static final double EB = KE;
    private static final double EA = KA / 2.0;
    private static final double AD = KA / 2.0;
    private static final double AB = EB - EA;
    private static final double AF = AB * P;
    private static final double DF = AD - AF;
    private static final double BC = 2 * DF;
    private static final double AI = EA * P;
    private static final double EI = EA * H;
    private static final double IL = EI;
    private static final double KL = KA - IL - AI;



    public final static FinalPointTransition A = fpt(pt(KA, DR_V));
    public final static FinalPointTransition B = A.append(pt(AB, UR_V));
    public final static FinalPointTransition F = A.append(pt(AF, UP));
    public final static FinalPointTransition C = B.append(pt(BC, UP));
    public final static FinalPointTransition D = fpt(pt(H, RIGHT));
    public final static FinalPointTransition E = fpt(pt(H, DR_H));
    public final static FinalPointTransition G = fpt(pt(KL, DOWN));
    public final static FinalPointTransition L = fpt(pt(KL, DR_V));
    public final static FinalPointTransition I = A.append(pt(AI, UL_V));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_star_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
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
                .addFullPaths(red, getFullPath())
                .addEquations(asList(
                        "KA = 1",
                        "KE = EB = h",
                        "AB = EB - EA = h - 0.5",
                        "AF = 0.5 * AB",
                        "DF = AD - AF = 0.5 - 0.5 * AB",
                        "BC = 2 * DF = 1 - AB = 1.5 - h",
                        "IL = EI"
                ))
                .addImportantVertexes(
                        TileStar3.class
                )
                .addSinglePathsLines(gray,
                        diagonals(1.0, VER).apply(K),
                        perimeter(1.5 - H, VER).apply(fpt(pt(2 * H, RIGHT))),
                        diagonals(1.5 - H, VER).apply(fpt(pt(2 * H, RIGHT)))

                )
                .addSinglePathsLines(
                        green,
                        perimeter(H, HOR).apply(K),
                        Stream.of(PointsPath.of(C, D, B))
                )
                .addFullPaths(
                        gray,
                        diagonalVertical(1.0).apply(D)
                )
                .addFullPaths(
                        blue,
                        diagonalHorizontal((H + H * (H - 0.5))).apply(G)
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

    private static java.util.List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(G, E, L)
        );
    }

}
