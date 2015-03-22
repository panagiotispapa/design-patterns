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

import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;

public class TileStar {

    public static double RATIO_1 = 0.5;
    public static double RATIO_2 = HEIGHT_RATIO / (HEIGHT_RATIO + 0.5);
    public static double RATIO_3 = (1.5 - HEIGHT_RATIO) * 0.5;

    public static List<Pair<Polygon, Polygon.Vertex>> getLines(double ratio) {
        Polygon main = Hex.hex(HEIGHT_RATIO, Polygon.Type.HOR);
        Polygon inner = Hex.hex(ratio, VER);

        return
                asList(
                        Pair.of(inner, ONE),
                        Pair.of(main, TWO),
                        Pair.of(inner, TWO)
                );
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple1() {
        return new PayloadSimple
                .Builder(
                "hex_tile_star_01",
                Hex.ALL_VERTEX_INDEXES
        ).withLines(asList(
                getLines(RATIO_1)
        ))
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple2() {
        return new PayloadSimple.Builder("hex_tile_star_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        getLines(RATIO_2)
                ))
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple3() {
        return new PayloadSimple.Builder("hex_tile_star_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        getLines(RATIO_3)
                ))
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper1() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Hex.hex(1, VER);
        Polygon registered = main.getRegistered();
        Polygon inner = Hex.hex(RATIO_1, VER);

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_01_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addMixedLinesInstructionsList(getPayloadSimple1().getLines(), red)
//                .addEquations(equations)
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(registered, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(inner, Hex.PERIMETER)
                ), blue);

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String blueLight = newStyle("blue", 1, 0.3);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Hex.hex(1, VER);
        Polygon registered = main.getRegistered();
        Polygon inner = Hex.hex(1.0 / 2.0, VER);
        Polygon inner2 = Hex.hex(RATIO_2, VER);
        Polygon inner2Reg = inner2.getRegistered();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_02_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addMixedLinesInstructionsList(getPayloadSimple2().getLines(), red)
                .addEquations(asList(
                        "AB=AC",
                        "KB=h/(h+0.5)"
                ))
                .addImportantPoints(asList(
                        Triple.of(inner2, Hex.Vertex.SIX, "B"),
                        Triple.of(inner2Reg, ONE, "A"),
                        Triple.of(registered, ONE, "C")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(registered, Hex.DIAGONALS),
                        Pair.of(main, Hex.DIAGONALS)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(inner2, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(inner, Hex.PERIMETER)
                ), blue);

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper3() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String blueLight = newStyle("blue", 1, 0.3);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        final double KA = 1;
        final double KE = $H.apply(KA);
        final double EB = KE;
        final double AB = EB - KA * 0.5;
        final double AF = $P.apply(AB);

        Polygon main = Hex.hex(1, VER);
        Polygon registered = main.getRegistered();
        Polygon outerSmall = Hex.hex(1.5 - HEIGHT_RATIO, VER, Hex.centreTransform(2 * HEIGHT_RATIO, Polygon.Type.HOR));

        Polygon inner = Hex.hex(HEIGHT_RATIO * 0.5 + HEIGHT_RATIO * HEIGHT_RATIO, Polygon.Type.HOR, Polygon.centreTransform(RATIO_3, ONE, VER));
        Polygon outerBig = Hex.hex(2, VER, Polygon.centreTransform(HEIGHT_RATIO, ONE, Polygon.Type.HOR));
        Polygon hexAF = Hex.hex(AF, VER, Hex.centreTransform(1, VER));

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_03_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addMixedLinesInstructionsList(getPayloadSimple3().getLines(), red)
                .addEquations(asList(
                        "KA = 1",
                        "KE = EB = h",
                        "AB = EB - EA = h - 0.5",
                        "AF = 0.5 * AB",
                        "DF = AD - AF = 0.5 - 0.5 * AB",
                        "BC = 2 * DF = 1 - AB = 1.5 - h"
                ))
                .addAllVertexesAsImportantPoints(asList(
//                        hexAF
                ))
                .addImportantPoints(asList(
                        Triple.of(main, ONE, "A"),
                        Triple.of(outerSmall, THREE, "B"),
                        Triple.of(outerSmall, FOUR, "C"),
                        Triple.of(registered, ONE, "D"),
                        Triple.of(registered, TWO, "E"),
                        Triple.of(hexAF, FIVE, "F")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(outerSmall, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)
//                                Pair.of(outer, Hex.DIAGONALS)

                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(registered, Hex.PERIMETER)
                ), green)
                .addMixedLinesInstructions(asList(
                        Pair.of(outerSmall, THREE),
                        Pair.of(registered, ONE),
                        Pair.of(outerSmall, FOUR)

                ), green)
                .addMixedLinesInstructions(asList(
                        Pair.of(inner, THREE),
                        Pair.of(inner, SIX)

                ), blue)
                .addMixedLinesInstructions(asList(
                        Pair.of(outerBig, TWO),
                        Pair.of(outerBig, FIVE)

                ), gray)
                .addCircle(
                        asList(
                                registered
                        ), blueLight
                )
                ;

    }

}