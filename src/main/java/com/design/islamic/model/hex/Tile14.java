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
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.view.SvgFactory.newStyle;
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

        return new PayloadSimple.Builder("hex_tile_14",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(hexAB, UL_H),
                                instruction(hexAB, LEFT),
                                instruction(hexAB, DL_H)
                        )
                ))
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
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
        Polygon hexAB = Hex.hex(3 * RATIO_m, HOR, Hex.centreTransform(1, HOR));

        List<String> equations = Arrays.asList(
                "KB = (1/4) * KA"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_14_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, ONE, "A"),
                        Triple.of(hexKB, ONE, "B")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(hexKB, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)
                ), gray)

                .addLinesInstructions(asList(
                        Pair.of(hexAB, Hex.PERIMETER)
                ), blue)

                .addLinesInstructions(IntStream.range(1, 4).mapToObj(i -> Pair.of(Hex.hex(i * RATIO_m, HOR), Hex.PERIMETER)).collect(Collectors.toList()), gray)

                .addAllVertexesAsImportantPoints(asList(
//                        hexAB
                ))
                ;

    }

}