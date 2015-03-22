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

import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.$H;
import static com.design.islamic.model.Hex.HEIGHT_RATIO;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;

public class Tile6 {

    public static double RATIO_1 = 0.5 / HEIGHT_RATIO;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon inner = Hex.hex(0.5, VER);
        Polygon outer = Hex.hex($H.apply(0.5), HOR, Hex.centreTransform(1, VER));

        return new PayloadSimple.Builder("hex_tile_06",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                Pair.of(main, ONE),
                                Pair.of(outer, FIVE),
                                Pair.of(inner, TWO)
                        ),
                        asList(
                                Pair.of(main, ONE),
                                Pair.of(outer, FOUR),
                                Pair.of(inner, SIX)
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
        Polygon inner1 = Hex.hex(RATIO_1, HOR);
        Polygon inner2 = Hex.hex(0.5, VER);
        Polygon outer = Hex.hex(0.5, VER, Hex.centreTransform(1, VER));
        Polygon outerReg = outer.getRegistered();

        List<String> equations = asList(
                "KA=AB=0.5",
                "KB=1",
                "BC=BD=AB*h=0.5*h"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_06_design")
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.INNER_TRIANGLES),
                        Pair.of(main.getRegistered(), Hex.INNER_TRIANGLES),
                        Pair.of(outer, Hex.PERIMETER),
                        Pair.of(outerReg, Hex.PERIMETER)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(inner2, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(inner1, Hex.PERIMETER)
                ), blue)
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addEquations(
                        equations
                )
                .addImportantPoints(asList(
                        Triple.of(inner2, Hex.Vertex.ONE, "A"),
                        Triple.of(main, Hex.Vertex.ONE, "B"),
                        Triple.of(outerReg, Hex.Vertex.FOUR, "C"),
                        Triple.of(outerReg, Hex.Vertex.FIVE, "D")
                ))
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                ;

    }


}