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

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Polygon main = Rect.rect(1, VER);
        Polygon rectKB = Rect.rect(KB, VER);
        Polygon rectKC = Rect.rect(KC, HOR);
        Polygon rectCD = Rect.rect(CD, VER, centreTransform(KC, DR));
        Polygon rectCE = Rect.rect(CE, HOR, centreTransform(KC, DR));

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();


        return new PayloadSimple.Builder("deco_tile_04",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(VertexPaths.of(
                        VertexPath.of(
                                instruction(rectKB, LEFT),
                                instruction(rectCE, DL)
                        )
                ), whiteBold)
                .withGridConf(Grid.Configs.RECT3.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        Polygon main = Rect.rect(1, VER);
        Polygon rectKB = Rect.rect(KB, VER);
        Polygon rectKC = Rect.rect(KC, HOR);
        Polygon rectCD = Rect.rect(CD, VER, centreTransform(KC, DR));
        Polygon rectCE = Rect.rect(CE, HOR, centreTransform(KC, DR));

        List<String> equations = Arrays.asList(
                "KB = KA / 5.0"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "deco_tile_04_design")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantVertexes(
                        ImportantVertex.of(main, RIGHT.getVertex(), "A"),
                        ImportantVertex.of(rectKB, RIGHT.getVertex(), "B"),
                        ImportantVertex.of(rectKC, DR.getVertex(), "C"),
                        ImportantVertex.of(rectCD, LEFT.getVertex(), "D"),
                        ImportantVertex.of(rectCE, DL.getVertex(), "E")
                )
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(rectKB, PERIMETER),
                        Pair.of(rectKC, PERIMETER),
                        Pair.of(rectCD, PERIMETER),
                        Pair.of(rectCE, PERIMETER)
                ), gray)
                ;

    }

}