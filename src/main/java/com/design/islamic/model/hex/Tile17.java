package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.centreTransform;
import static java.util.Arrays.asList;

//p.
public class Tile17 {

    private static double RATIO_m = 1.0 / 3.0;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon hexKB = Hex.hex(RATIO_m, HOR);

        Polygon hexCB = Hex.hex(RATIO_m, HOR, centreTransform(2 * RATIO_m, HOR));
        return new PayloadSimple.Builder("hex_tile_17",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                Pair.of(hexCB, SIX),
                                Pair.of(hexCB, FIVE),
                                Pair.of(hexCB, FOUR),
                                Pair.of(hexCB, THREE),
                                Pair.of(hexCB, TWO),
                                Pair.of(hexCB, ONE),
                                Pair.of(hexCB, SIX)
                        ),
                        asList(
                                Pair.of(hexKB, ONE),
                                Pair.of(hexKB, TWO)

                        )

                ))
                .withGridConf(Grid.Configs.HEX_HOR3.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Hex.hex(1, HOR);
        Polygon hexKB = Hex.hex(RATIO_m, HOR);
        Polygon hexKC = Hex.hex(2*RATIO_m, HOR);
        Polygon hexCB = Hex.hex(RATIO_m, HOR, centreTransform(2 * RATIO_m, HOR));


        List<String> equations = Arrays.asList(
                "KB = (1/3) * KA"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_17_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, ONE, "A"),
                        Triple.of(hexKB, ONE, "B"),
                        Triple.of(hexKC, ONE, "C")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(hexKB, Hex.PERIMETER),
                        Pair.of(hexKC, Hex.PERIMETER),
                        Pair.of(hexCB, Hex.PERIMETER)
                ), gray)

                .addAllVertexesAsImportantPoints(asList(
//                        hexCB

                ))
                ;

    }

}