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
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;

public class Tile10 {

    private static double AB = 1.0 / 3.0;
    private static double AC = £H.apply(AB);
    private static double KE = HEIGHT_RATIO - 0.5 * AC;
    private static double EC = 0.5 - KE;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Polygon hexAC = Hex.hex(AC, HOR, centreTransform(1, DR_V));
        Polygon outerReg = hexAC.getRegistered();

        Polygon hexKE = Hex.hex(KE, HOR);

        return new PayloadSimple.Builder("hex_tile_10",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(outerReg, DL_V),
                                instruction(hexAC, LEFT),
                                instruction(hexAC, UL_H),
                                instruction(outerReg, UP)
                        ),
                        asList(
                                instruction(hexKE, RIGHT),
                                instruction(hexAC, UL_H)
                        ),
                        asList(
                                instruction(hexKE, DR_H),
                                instruction(hexAC, LEFT)
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
        Polygon mainReg = main.getRegistered();
        Polygon hexAC = Hex.hex(AC, HOR, centreTransform(1, DR_V));
        Polygon outerReg = hexAC.getRegistered();
        Polygon hexKE = Hex.hex(KE, HOR);
        Polygon hexEC = Hex.hex(EC, VER, centreTransform(KE, RIGHT));

        List<String> equations = asList(
                "AB = 1 / 3",
                "CD = AB / h",
                "KE = h - (CD / 2)",
                "EC = 0.5 - AB"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_10_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainReg, Hex.DIAGONALS),
                        Pair.of(hexAC, Hex.PERIMETER)

                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(hexKE, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(hexEC, Hex.PERIMETER)
//                                Pair.of(inner3, Hex.PERIMETER)

                ), blue)
                .addImportantPoints(asList(
                        Triple.of(main, ONE, "A"),
                        Triple.of(outerReg, FIVE, "B"),
                        Triple.of(hexAC, FIVE, "C"),
                        Triple.of(hexAC, SIX, "D"),
                        Triple.of(hexKE, ONE, "E")
                ))
                .addAllVertexesAsImportantPoints(asList(
//                        outer
                ))
                ;

    }

}