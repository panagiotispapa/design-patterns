package com.design.deco;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.RatioHelper.P4;
import com.design.common.model.Style;
import com.design.islamic.model.*;
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

public class Tile2 {

    private static final double KA = 1.0;
    private static final double AB = KA / 4.0;
    private static final double KB = KA - AB;
    private static final double AC = AB * P4.H;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Polygon main = Rect.rect(1, HOR);
        Polygon rectKB = Rect.rect(KB, HOR);
        Polygon rectAB = Rect.rect(AB, HOR, centreTransform(KA, DR));
        Polygon rectAC = Rect.rect(AC, VER, centreTransform(KA, DR));

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("deco_tile_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(() -> asList(
                        () -> asList(
                                instruction(rectAC, UP),
                                instruction(rectKB, DL)
                        ),
                        () -> asList(
                                instruction(rectAC, LEFT),
                                instruction(rectKB, UR)
                        )
                ), whiteBold)
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Rect.rect(1, HOR);
        Polygon rectKB = Rect.rect(KB, HOR);
        Polygon rectAB = Rect.rect(AB, HOR, centreTransform(KA, DR));
        Polygon rectAC = Rect.rect(AC, VER, centreTransform(KA, DR));

        List<String> equations = Arrays.asList(
                "AB = KA / 4.0"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "deco_tile_02_design")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, DR.getVertex(), "A"),
                        Triple.of(rectKB, DR.getVertex(), "B"),
                        Triple.of(rectAC, UP.getVertex(), "C")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(rectKB, PERIMETER),
                        Pair.of(rectAB, PERIMETER)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(rectAC, PERIMETER)
                ), green)
                ;

    }

}