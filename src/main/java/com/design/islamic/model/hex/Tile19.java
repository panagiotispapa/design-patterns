package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.VER;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.ONE;
import static com.design.islamic.model.Hex.Vertex.THREE;
import static java.util.Arrays.asList;

public class Tile19 {

    public static double RATIO_m = 1.0 / 5.0;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon hexKA = Hex.hex(RATIO_m, VER);
        Polygon hexKB = Hex.hex(2 * RATIO_m, VER);
        Polygon hexKC = Hex.hex(3 * RATIO_m, VER);
        Polygon hexKD = Hex.hex(4 * RATIO_m, VER);

        Polygon hexBA = Hex.hex(RATIO_m, VER, centreTransform(2 * RATIO_m, DR_V));
        Polygon hexCB = Hex.hex(RATIO_m, VER, centreTransform(3 * RATIO_m, DR_V));
        Polygon hexDC = Hex.hex(RATIO_m, VER, centreTransform(4 * RATIO_m, DR_V));
        Polygon hexED = Hex.hex(RATIO_m, VER, centreTransform(1, DR_V));
        Polygon hexFG = Hex.hex(RATIO_m, VER, CentreTransform.of(centreTransform(1, VER), centreTransform(RATIO_m, DL_V)));
        Polygon hexGH = Hex.hex(RATIO_m, VER, CentreTransform.of(centreTransform(1, VER), centreTransform(2 * RATIO_m, DL_V)));

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_19",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(VertexPaths.of(
                        VertexPath.of(
                                instruction(hexKA, UR_V),
                                instruction(hexKB.getRegistered(), RIGHT),
                                instruction(hexKA, DR_V)
                        ),
                        VertexPath.of(
                                instruction(hexBA, DL_V),
                                instruction(hexBA, DOWN),
                                instruction(hexBA, DR_V),
                                instruction(hexCB, UL_V),
                                instruction(hexCB, UP),
                                instruction(hexCB, UR_V)
                        ),
                        VertexPath.of(
                                instruction(hexDC, UR_V),
                                instruction(hexDC, UP),
                                instruction(hexCB, DR_V),
                                instruction(hexDC, DL_V),
                                instruction(hexDC, DOWN)

                        ),
                        VertexPath.of(
                                instruction(hexFG, UL_V),
                                instruction(hexGH, UL_V),
                                instruction(hexGH, DL_V),
                                instruction(hexFG, DL_V)
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

        Polygon main = Hex.hex(1, VER);
        Polygon hexKA = Hex.hex(RATIO_m, VER);
        Polygon hexKB = Hex.hex(2 * RATIO_m, VER);
        Polygon hexKC = Hex.hex(3 * RATIO_m, VER);
        Polygon hexKD = Hex.hex(4 * RATIO_m, VER);

        Polygon hexBA = Hex.hex(RATIO_m, VER, centreTransform(2 * RATIO_m, DR_V));
        Polygon hexCB = Hex.hex(RATIO_m, VER, centreTransform(3 * RATIO_m, DR_V));
        Polygon hexDC = Hex.hex(RATIO_m, VER, centreTransform(4 * RATIO_m, DR_V));
        Polygon hexED = Hex.hex(RATIO_m, VER, centreTransform(1, DR_V));
        Polygon hexFG = Hex.hex(RATIO_m, VER, CentreTransform.of(centreTransform(1, DR_V), centreTransform(RATIO_m, DL_V)));
        Polygon hexGH = Hex.hex(RATIO_m, VER, CentreTransform.of(centreTransform(1, DR_V), centreTransform(2 * RATIO_m, DL_V)));

        List<String> equations = Arrays.asList(
                "KA = 1 / 5"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_19_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantVertexes(
                        ImportantVertex.of(hexKA, ONE, "A"),
                        ImportantVertex.of(hexKB, ONE, "B"),
                        ImportantVertex.of(hexKC, ONE, "C"),
                        ImportantVertex.of(hexKD, ONE, "D"),
                        ImportantVertex.of(hexED, THREE, "F"),
                        ImportantVertex.of(main, ONE, "E"),
                        ImportantVertex.of(hexFG, THREE, "G"),
                        ImportantVertex.of(hexGH, THREE, "H")
                )
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(hexKA, PERIMETER),
                        Pair.of(hexKB, PERIMETER),
                        Pair.of(hexKC, PERIMETER),
                        Pair.of(hexKD, PERIMETER),
                        Pair.of(hexBA, PERIMETER),
                        Pair.of(hexCB, PERIMETER),
                        Pair.of(hexDC, PERIMETER),
                        Pair.of(hexED, PERIMETER),
                        Pair.of(hexFG, PERIMETER),
                        Pair.of(hexGH, PERIMETER)
                ), gray)
                .addAllVertexesAsImportantPoints(asList(
//                        main

                ))
                ;

    }

}