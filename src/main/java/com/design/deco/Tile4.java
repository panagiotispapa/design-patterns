package com.design.deco;

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
import static com.design.islamic.model.Rect.Corner.*;
import static com.design.islamic.model.Rect.*;
import static java.util.Arrays.asList;

public class Tile4 {

    private static final double KA = 1.0;
    private static final double KB = KA / 5.0;
    private static final double KC = KA * H;
    private static final double CD = KB;
    private static final double CE = CD * H;

    public final static FinalPointTransition A = fpt(pt(KA, RIGHT));
    public final static FinalPointTransition B = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition B2 = fpt(pt(KB, LEFT));
    public final static FinalPointTransition C = fpt(pt(KC, DR));
    public final static FinalPointTransition D = C.append(pt(CD, LEFT));
    public final static FinalPointTransition E = C.append(pt(CE, DL));
    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("deco_tile_04",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT3.getConfiguration())
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "deco_tile_04_design")
                .addFullPaths(red, getFullPath())
                .addEquations(equations)
                .addImportantVertexes(Tile4.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(CD, VER).apply(C),
                        perimeter(CE, HOR).apply(C)
                )
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(E, B2)
        );
    }

}