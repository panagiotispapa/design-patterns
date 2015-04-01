package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile18 {

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, HOR);

        return new PayloadSimple.Builder("hex_tile_18",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLinesSingle(asList(
                        asList(
                                instruction(main, UL_H),
                                instruction(main, DR_H)
                        ),
                        asList(
                                instruction(main, UR_H),
                                instruction(main, DL_H)
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

        Polygon main = Hex.hex(1, HOR);

        List<String> equations = Arrays.asList(
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_18_design")
                .addSingleLinesInstructionsList(getPayloadSimple().getLinesSingle(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER)
//                        Pair.of(main, Hex.DIAGONALS)
                ), gray)
                .addLinesInstructions(asList(
//                        Pair.of(inner2, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
//                        Pair.of(inner1, Hex.PERIMETER),
//                        Pair.of(inner3, Hex.PERIMETER)
                ), blue)
                ;

    }

}