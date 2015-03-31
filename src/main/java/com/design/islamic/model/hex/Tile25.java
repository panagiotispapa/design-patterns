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
import static com.design.islamic.model.Hex.$H;
import static com.design.islamic.model.Hex.Corner.DR_V;
import static com.design.islamic.model.Hex.Corner.RIGHT;
import static com.design.islamic.model.Hex.Corner.UR_V;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile25 {


    private static final double KB = $H.apply(1.0);
    private static final double KC = $H.apply(KB);
    private static final double CA = 1 - KC;
    private static final double CB = 0.5 * KB;
    private static final double CD = CB;
    private static final double KD = 1 - CA - CD;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, HOR);
        Polygon main_Ver = main.getRegistered();
        Polygon hexKD = Hex.hex(KD, HOR);
        return new PayloadSimple.Builder("hex_tile_25",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(main_Ver, UR_V),
                                instruction(hexKD, RIGHT),
                                instruction(main_Ver, DR_V)
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
        Polygon main_Ver = main.getRegistered();
        Polygon hexKC = Hex.hex(KC, HOR);
        Polygon hexKD = Hex.hex(KD, HOR);

        List<String> equations = Arrays.asList(
                "KB = h",
                "KC = h * KB",
                "BC = 0.5 * KB",
                "DC = BC",
                "CA = KA - KC",
                "KD = KA - KC - DC"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_25_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, RIGHT.getVertex(), "A"),
                        Triple.of(main_Ver, DR_V.getVertex(), "B"),
                        Triple.of(hexKC, RIGHT.getVertex(), "C"),
                        Triple.of(hexKD, RIGHT.getVertex(), "D")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(main_Ver, Hex.PERIMETER),
                        Pair.of(main_Ver, Hex.DIAGONALS)

                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(hexKC, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(hexKD, Hex.PERIMETER)
//                        Pair.of(inner3, Hex.PERIMETER)
                ), blue)
                ;

    }

}