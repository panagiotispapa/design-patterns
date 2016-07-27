package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Payload;
import com.design.islamic.model.Rect;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.diagonals;
import static com.design.islamic.model.Rect.perimeter;
import static java.util.Arrays.asList;

public class Tile19 {

    private static final double KA = 1.0;
    private static final double KC = KA * H;
    private static final double KD = KC / 2.0;
    private static final double AI = KD * H;
    private static final double DI = KD - AI;
    private static final double FJ = DI / H;

    public final static FinalPointTransition A = fpt(pt(KA, DR));
    public final static FinalPointTransition B = fpt(pt(KA, DL));
    public final static FinalPointTransition C = fpt(pt(KC, DOWN));
    public final static FinalPointTransition D = fpt(pt(KD, RIGHT));
    public final static FinalPointTransition D2 = fpt(pt(KD, DOWN));
    public final static FinalPointTransition E = fpt(pt(KC, RIGHT));
    public final static FinalPointTransition F = C.append(pt(KD, RIGHT));
    public final static FinalPointTransition G = A.append(pt(KD, UP));
//    public final static FinalPointTransition G2 = E.append(pt(KD, UP));
    public final static FinalPointTransition I = A.append(pt(AI, LEFT));
    public final static FinalPointTransition J = I.append(pt(DI, UP));
//    public final static FinalPointTransition J2 = I.append(pt(DI, UP));
    public final static FinalPointTransition L = I.append(pt(AI, UP));
//    public final static FinalPointTransition L2 = I.append(pt(EI, UP));
    public final static FinalPointTransition P = L.append(pt(FJ, RIGHT));
//    public final static FinalPointTransition P2 = L2.append(pt(DJ, RIGHT));

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_19",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        List<String> equations = Arrays.asList(
                "KC = h",
                "KD = KA / 2.0"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_19_design")
                .addEquations(equations)
                .addImportantVertexes(Tile19.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        perimeter(KD, VER).apply(C),
                        perimeter(KD, VER).apply(A),
                        perimeter(KD, VER).apply(B),
                        perimeter(KD, VER).apply(E),
                        perimeter(KD, HOR).apply(A),
                        diagonals(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        PointsPath.of(I, A)
                )
                .addFullPaths(red, getFullPath())
                ;
    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(K, D2, F, J , L, P, G, D, K)
//                PointsPath.of(C, G)
        );
    }

}