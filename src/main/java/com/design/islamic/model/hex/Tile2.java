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

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.$1;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.$H;
import static com.design.islamic.model.Hex.HEIGHT_RATIO;
import static com.design.islamic.model.Hex.Vertex.ONE;
import static com.design.islamic.model.Hex.Vertex.TWO;
import static java.util.Arrays.asList;

public class Tile2 {

    private static double RATIO_KB = HEIGHT_RATIO / (HEIGHT_RATIO + 0.5);
    private static double RATIO_BD = $1.andThen($H).apply(RATIO_KB);

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon inner = Hex.hex(RATIO_KB, VER);
        Polygon outer = Hex.hex(RATIO_BD, HOR, Hex.centreTransform(RATIO_KB, VER));
        return new PayloadSimple.Builder("hex_tile_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(
                        asList(
                                asList(
                                        Pair.of(outer, ONE),
                                        Pair.of(inner, ONE),
                                        Pair.of(outer, TWO)
                                ),
                                asList(
                                        Pair.of(inner, ONE),
                                        Pair.of(inner, TWO)
                                )
                        )
                )
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
        Polygon inner = Hex.hex(RATIO_KB, VER);
        Polygon innerReg = inner.getRegistered();

        Polygon outer = Hex.hex(RATIO_BD, HOR, Hex.centreTransform(RATIO_KB, VER));

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_02_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(asList(
                        "KB=h/(h+0.5)",
                        "BD=h*(1-KB)"
                ))
                .addImportantPoints(asList(
                        Triple.of(inner, ONE, "B"),
                        Triple.of(innerReg, ONE, "C"),
                        Triple.of(outer, ONE, "D")

                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(main.getRegistered(), Hex.DIAGONALS),
                        Pair.of(outer, Hex.DIAGONALS)
//                                Pair.of(inner, Hex.PERIMETER)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(inner, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(outer, Hex.PERIMETER)
                ), blue)
                ;

    }

}