package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.Polygon;
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
import static com.design.islamic.model.Hex.Vertex.ONE;
import static com.design.islamic.model.Hex.centreTransform;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

//p. 4
public class Tile13 {

    private static double RATIO_m = 1.0 / 3.0;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        Polygon hexCB = Hex.hex(RATIO_m, HOR, centreTransform(2 * RATIO_m, RIGHT));
        return new PayloadSimple.Builder("hex_tile_13",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFullFromLines(asList(
                        asList(
                                instruction(hexCB, UR_H),
                                instruction(hexCB, UL_H),
                                instruction(hexCB, LEFT),
                                instruction(hexCB, DL_H),
                                instruction(hexCB, DR_H),
                                instruction(hexCB, UR_H)
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
        Polygon hexKC = Hex.hex(2 * RATIO_m, HOR);
        Polygon hexCB = Hex.hex(RATIO_m, HOR, centreTransform(2 * RATIO_m, HOR));

        List<String> equations = Arrays.asList(
                "KB = (1/3) * KA"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_13_design")
                .addFullPaths(getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, ONE, "A"),
                        Triple.of(hexKB, ONE, "B"),
                        Triple.of(hexKC, ONE, "C")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(hexKB, Hex.PERIMETER),
                        Pair.of(hexKC, Hex.PERIMETER),
                        Pair.of(hexCB, Hex.PERIMETER)
                ), gray)

                .addAllVertexesAsImportantPoints(asList(
                        hexCB

                ))
                ;

    }

}