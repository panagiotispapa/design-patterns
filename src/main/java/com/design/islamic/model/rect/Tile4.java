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
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.diagonals;
import static com.design.islamic.model.Rect.perimeter;
import static java.util.Arrays.asList;

public class Tile4 {

    private static final double KA = 1.0;
    private static final double KB = H;
    private static final double KD = H * KB;
    private static final double BD = KB - KD;


    public final static FinalPointTransition A = fpt(pt(KA, UL));
    public final static FinalPointTransition B = fpt(pt(KB, UP));
    public final static FinalPointTransition C = fpt(pt(KB, UL));
    public final static FinalPointTransition C2 = fpt(pt(KB, UR));
    public final static FinalPointTransition D = fpt(pt(KD, UP));
    public final static FinalPointTransition E = D.append(pt(BD, RIGHT));
    public final static FinalPointTransition F = D.append(pt(BD, LEFT));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_04",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();

        List<String> equations = Arrays.asList(
                "KB = h = KC",
                "KD = KD * h"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_04_design")
                .addEquations(equations)
                .addImportantVertexes(Tile4.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K)
                )
                .addCirclesCentral(asList(
                        H
                ), gray)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(C, F, B, E, C2)
        );
    }

}