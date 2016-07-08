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
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.*;
import static java.util.Arrays.asList;

public class Tile3 {

    private static final double KA = 1.0;
    private static final double AB = KA / 4.0;
    private static final double KB = KA - AB;

    public final static FinalPointTransition A = fpt(pt(KA, DR));
    public final static FinalPointTransition B = fpt(pt(KB, DR));
    public final static FinalPointTransition C = fpt(pt(KA, UR));
    public final static FinalPointTransition D = fpt(pt(KB, UR));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();


        return new Payload.Builder("deco_tile_03",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        List<String> equations = Arrays.asList(
                "AB = KA / 4.0"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "deco_tile_03_design")
                .addFullPaths(red, getFullPath())
                .addEquations(equations)
                .addImportantVertexes(Tile3.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        perimeter(KB, HOR).apply(K),
                        perimeter(AB, HOR).apply(A)
                )
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(A, B, D, C)
        );
    }


}