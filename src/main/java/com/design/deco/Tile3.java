package com.design.deco;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.islamic.model.Rect.Corner.DR;
import static com.design.islamic.model.Rect.Corner.UR;
import static com.design.islamic.model.Rect.*;
import static java.util.Arrays.asList;

public class Tile3 {

    private static final double KA = 1.0;
    private static final double AB = KA / 4.0;
    private static final double KB = KA - AB;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        Polygon main = Rect.rect(1, HOR);
        Polygon rectKA = Rect.rect(KA, HOR);
        Polygon rectKB = Rect.rect(KB, HOR);
        Polygon rectAB = Rect.rect(AB, HOR, centreTransform(KA, DR));

        return new PayloadSimple.Builder("deco_tile_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(VertexPaths.of(
                        VertexPath.of(
                                instruction(rectKB, UR),
                                instruction(rectKB, DR)
                        ),
                        VertexPath.of(
                                instruction(rectKB, DR),
                                instruction(rectKA, DR)
                        )
                ), whiteBold)
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        Polygon main = Rect.rect(1, HOR);
        Polygon rectKB = Rect.rect(KB, HOR);
        Polygon rectAB = Rect.rect(AB, HOR, centreTransform(KA, DR));

        List<String> equations = Arrays.asList(
                "AB = KA / 4.0"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "deco_tile_03_design")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantVertexes(
                        ImportantVertex.of(main, DR.getVertex(), "A"),
                        ImportantVertex.of(rectKB, DR.getVertex(), "B")
                )
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(rectKB, PERIMETER),
                        Pair.of(rectAB, PERIMETER)
                ), gray)
                ;

    }

}