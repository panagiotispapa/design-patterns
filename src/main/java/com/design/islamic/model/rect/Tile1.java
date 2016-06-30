package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.RatioHelper.P4;
import com.design.common.RatioHelper.P8;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.Rect;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.islamic.model.Rect.Corner.*;
import static com.design.islamic.model.Rect.*;
import static java.util.Arrays.asList;

public class Tile1 {

    private static final double KA = 1.0;
    private static final double KB = KA / 2.0;
    private static final double KC = KB / P8.H;
    private static final double BC = KC * P8.P;
    private static final double KD = KA * P4.H;
    private static final double DE = BC;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon rectBC = Rect.rect(BC, HOR, centreTransform(KB, DR));
        Polygon rectBC_Rot = Rect.rect(BC, HOR, centreTransform(KB, DL));
        Polygon rectDE = Rect.rect(DE, VER, centreTransform(KD, DOWN));
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("rect_tile_01",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        VertexPaths.of(
                                VertexPath.of(
                                        instruction(rectDE, RIGHT),
                                        instruction(rectBC, DL),
                                        instruction(rectBC_Rot, DR),
                                        instruction(rectDE, LEFT)
                                ),
                                VertexPath.of(
                                        instruction(rectBC, DL),
                                        instruction(rectBC, UR)
                                )
                        ), whiteBold)
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Rect.rect(1, HOR);
        Polygon rectKB = Rect.rect(KB, HOR);
        Polygon rectBC = Rect.rect(BC, HOR, centreTransform(KB, DR));
        Polygon rectBC_Rot = Rect.rect(BC, HOR, centreTransform(KB, DL));
        Polygon rectKD = Rect.rect(KD, VER);
        Polygon rectDE = Rect.rect(DE, VER, centreTransform(KD, DOWN));

        List<String> equations = Arrays.asList(
                "KF = h"
        );

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_01_design")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantVertexes(
                        ImportantVertex.of(main, DR.getVertex(), "A"),
                        ImportantVertex.of(rectKB, DR.getVertex(), "B"),
                        ImportantVertex.of(rectBC, DL.getVertex(), "C"),
                        ImportantVertex.of(rectKD, DOWN.getVertex(), "D"),
                        ImportantVertex.of(rectDE, RIGHT.getVertex(), "E"),
                        ImportantVertex.of(rectDE, LEFT.getVertex(), "F"),
                        ImportantVertex.of(rectBC_Rot, DR.getVertex(), "G")
                )
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(rectKB, PERIMETER),
                        Pair.of(rectBC, PERIMETER),
                        Pair.of(rectBC, DIAGONALS),
                        Pair.of(rectKD, PERIMETER),
                        Pair.of(rectKD, DIAGONALS),
                        Pair.of(rectDE, PERIMETER),
                        Pair.of(rectBC_Rot, PERIMETER)
                ), gray)
                ;

    }

}