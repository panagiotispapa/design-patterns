package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.Polygon.VertexPaths;
import com.design.common.RatioHelper.P6;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile3c {


    private static final double KB = P6.H;
    private static final double KC = KB * P6.H;
    private static final double CA = 1 - KC;
    private static final double CB = 0.5 * KB;
    private static final double CD = CB;
    private static final double KD = 1 - CA - CD;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, HOR);
        Polygon main_Ver = main.getRegistered();
        Polygon hexKD = Hex.hex(KD, HOR);
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_03c",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        VertexPaths.of(
                                Polygon.VertexPath.of(
                                        instruction(main_Ver, UR_V),
                                        instruction(hexKD, RIGHT),
                                        instruction(main_Ver, DR_V)
                                )

                        ), whiteBold)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, HOR);
        Polygon main_Ver = main.getRegistered();
        Polygon hexKC = Hex.hex(KC, HOR);
        Polygon hexKD = Hex.hex(KD, HOR);

        List<String> equations = Arrays.asList(
                "KB = h",
                "KC = h * KB",
                "BC = 0.5 * KB",
                "DC = BC",
                "CA = KA - KC",
                "KD = KA - KC - DC"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_03c_design")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantVertexes(
                        ImportantVertex.of(main, RIGHT.getVertex(), "A"),
                        ImportantVertex.of(main_Ver, DR_V.getVertex(), "B"),
                        ImportantVertex.of(hexKC, RIGHT.getVertex(), "C"),
                        ImportantVertex.of(hexKD, RIGHT.getVertex(), "D")
                )
                .addSinglePaths(Arrays.asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(main_Ver, Hex.PERIMETER),
                        Pair.of(main_Ver, Hex.DIAGONALS)

                ), gray)
                .addSinglePaths(asList(
                        Pair.of(hexKC, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(hexKD, Hex.PERIMETER)
//                        Pair.of(inner3, Hex.PERIMETER)
                ), blue)
                ;

    }

}