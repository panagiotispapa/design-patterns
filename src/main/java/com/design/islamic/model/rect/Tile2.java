package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.*;
import static java.util.Arrays.asList;

public class Tile2 {

    private static final double KA = 1.0;
    private static final double KC = H;
    private static final double KB = H;
    private static final double KD = H * KB;
    private static final double BD = KD;
    private static final double DC = KD + KC;
    private static final double KE = BD * (KC / DC);
    private static final double FG = KC / H;
    private static final double KF = KE;
    private static final double KG = FG + KF;

    public final static FinalPointTransition A = fpt(pt(KA, DR));
    public final static FinalPointTransition B = fpt(pt(KB, UR));
    public final static FinalPointTransition P = fpt(pt(KB, DR));
    public final static FinalPointTransition C = fpt(pt(KC, LEFT));
    public final static FinalPointTransition D = fpt(pt(KD, RIGHT));
    public final static FinalPointTransition E = fpt(pt(KE, UP));
    public final static FinalPointTransition F = fpt(pt(KF, RIGHT));
    public final static FinalPointTransition G = fpt(pt(KG, RIGHT));
    public final static FinalPointTransition I = G.append(pt(FG, DL));
    public final static FinalPointTransition J = G.append(pt(FG, UL));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_02",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT3.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();

        List<String> equations = Arrays.asList(
                "KC = h",
                "KB = h",
                "KD = h * KB",
                "BD = KD",
                "DC = KD + KC",
                "KE = BD * (KC / DC)",
                "FG = KC / h",
                "KF = KE",
                "KG = FG + KF"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_02_design")
                .addEquations(equations)
                .addImportantVertexes(Tile2.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K),
                        perimeter(FG, HOR).apply(G),
                        perimeter(FG, VER).apply(G)
                )
                .addFullPaths(
                        gray,
                        PointsPath.of(B, C, P)
                )
                .addCirclesCentral(asList(
                        H
                ), gray)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(I, F, J)
        );
    }

}