package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.common.Polygon.VertexPaths;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile18 {

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, HOR);
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_18",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsSingle(VertexPaths.of(
                        Polygon.VertexPath.of(
                                instruction(main, UL_H),
                                instruction(main, DR_H)
                        ),
                        Polygon.VertexPath.of(
                                instruction(main, UR_H),
                                instruction(main, DL_H)
                        )

                ), whiteBold)
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, HOR);

        List<String> equations = Arrays.asList(
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_18_design")
                .addSinglePaths(() -> getPayloadSimple().getPathsSingle(), red)
                .addEquations(equations)
                .addImportantVertexes(asList(
                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER)
//                        Pair.of(main, Hex.DIAGONALS)
                ), gray)
                ;

    }

}