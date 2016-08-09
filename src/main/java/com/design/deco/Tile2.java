package com.design.deco;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.RatioHelper.P4;
import com.design.common.model.Style;
import com.design.islamic.model.*;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.*;
import static com.googlecode.totallylazy.Sequences.sequence;


public class Tile2 {

    private static final double KA = 1.0;
    private static final double AB = KA / 4.0;
    private static final double KB = KA - AB;
    private static final double AC = AB * P4.H;

    public final static FinalPointTransition A = fpt(pt(KA, DR));
    public final static FinalPointTransition B = fpt(pt(KB, DR));
    public final static FinalPointTransition C = B.append(pt(AC, RIGHT));
    public final static FinalPointTransition D = fpt(pt(KA, DL));
    public final static FinalPointTransition E = D.append(pt(AC, UP));


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("deco_tile_02",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();



        Sequence<String> equations = sequence(
                "AB = KA / 4.0"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "deco_tile_02_design")
                .addEquations(equations)
                .addImportantVertexes(Tile2.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        perimeter(KB, HOR).apply(K),
                        perimeter(AB, HOR).apply(A)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(AC, VER).apply(A)
                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(E, C)
        );
    }

}