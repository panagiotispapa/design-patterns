package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.DIAGONALS;
import static com.design.islamic.model.Hex.PERIMETER;
import static com.design.islamic.model.Hex.Vertex.*;
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

        Polygon hexBA = Hex.hex(RATIO_m, VER, Hex.centreTransform(2 * RATIO_m, VER));
        Polygon hexCB = Hex.hex(RATIO_m, VER, Hex.centreTransform(3 * RATIO_m, VER));
        Polygon hexDC = Hex.hex(RATIO_m, VER, Hex.centreTransform(4 * RATIO_m, VER));
        Polygon hexED = Hex.hex(RATIO_m, VER, Hex.centreTransform(1, VER));
        Polygon hexFG = Hex.hex(RATIO_m, VER, Hex.centreTransform(1, VER).andThen(Polygon.centreTransform(RATIO_m, THREE, VER)));
        Polygon hexGH = Hex.hex(RATIO_m, VER, Hex.centreTransform(1, VER).andThen(Polygon.centreTransform(2 * RATIO_m, THREE, VER)));

        return new PayloadSimple.Builder("hex_tile_19",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                Pair.of(hexKA, SIX),
                                Pair.of(hexKB.getRegistered(), ONE),
                                Pair.of(hexKA, ONE)
                        ),
                        asList(
                                Pair.of(hexBA, THREE),
                                Pair.of(hexBA, TWO),
                                Pair.of(hexBA, ONE),
                                Pair.of(hexCB, FOUR),
                                Pair.of(hexCB, FIVE),
                                Pair.of(hexCB, SIX)
                        ),
                        asList(
                                Pair.of(hexDC, SIX),
                                Pair.of(hexDC, FIVE),
                                Pair.of(hexCB, ONE),
                                Pair.of(hexDC, THREE),
                                Pair.of(hexDC, TWO)

                        ),
                        asList(
                                Pair.of(hexFG, FOUR),
                                Pair.of(hexGH, FOUR),
                                Pair.of(hexGH, THREE),
                                Pair.of(hexFG, THREE)
                        )
                ))
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Hex.hex(1, VER);
        Polygon hexKA = Hex.hex(RATIO_m, VER);
        Polygon hexKB = Hex.hex(2 * RATIO_m, VER);
        Polygon hexKC = Hex.hex(3 * RATIO_m, VER);
        Polygon hexKD = Hex.hex(4 * RATIO_m, VER);

        Polygon hexBA = Hex.hex(RATIO_m, VER, Hex.centreTransform(2 * RATIO_m, VER));
        Polygon hexCB = Hex.hex(RATIO_m, VER, Hex.centreTransform(3 * RATIO_m, VER));
        Polygon hexDC = Hex.hex(RATIO_m, VER, Hex.centreTransform(4 * RATIO_m, VER));
        Polygon hexED = Hex.hex(RATIO_m, VER, Hex.centreTransform(1, VER));
        Polygon hexFG = Hex.hex(RATIO_m, VER, Hex.centreTransform(1, VER).andThen(Polygon.centreTransform(RATIO_m, THREE, VER)));
        Polygon hexGH = Hex.hex(RATIO_m, VER, Hex.centreTransform(1, VER).andThen(Polygon.centreTransform(2*RATIO_m, THREE, VER)));

        List<String> equations = Arrays.asList(
                "KA = 1 / 5"

        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_19_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(hexKA, ONE, "A"),
                        Triple.of(hexKB, ONE, "B"),
                        Triple.of(hexKC, ONE, "C"),
                        Triple.of(hexKD, ONE, "D"),
                        Triple.of(hexED, THREE, "F"),
                        Triple.of(main, ONE, "E"),
                        Triple.of(hexFG, THREE, "G"),
                        Triple.of(hexGH, THREE, "H")
                ))
                .addLinesInstructions(asList(
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