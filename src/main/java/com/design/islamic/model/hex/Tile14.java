package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.Polygon.VertexPaths;
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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.ONE;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

//p.
public class Tile14 {

    private static double RATIO_m = 1.0 / 4.0;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Polygon hexAB = Hex.hex(3 * RATIO_m, HOR, Hex.centreTransform(1, RIGHT));

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_14",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(VertexPaths.of(
                        Polygon.VertexPath.of(
                                instruction(hexAB, UL_H),
                                instruction(hexAB, LEFT),
                                instruction(hexAB, DL_H)
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
        Polygon hexKB = Hex.hex(RATIO_m, HOR);
        Polygon hexAB = Hex.hex(3 * RATIO_m, HOR, Hex.centreTransform(1, HOR));

        List<String> equations = Arrays.asList(
                "KB = (1/4) * KA"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_14_design")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantVertexes(
                        ImportantVertex.of(main, ONE, "A"),
                        ImportantVertex.of(hexKB, ONE, "B")
                )
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(hexKB, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)
                ), gray)

                .addSinglePaths(asList(
                        Pair.of(hexAB, Hex.PERIMETER)
                ), blue)

                .addSinglePaths(IntStream.range(1, 4).mapToObj(i -> Pair.of(Hex.hex(i * RATIO_m, HOR), Hex.PERIMETER)).collect(Collectors.toList()), gray)

                .addAllVertexesAsImportantPoints(asList(
//                        hexAB
                ))
                ;

    }

}