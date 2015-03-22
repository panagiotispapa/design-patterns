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
import java.util.stream.IntStream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.centreTransform;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

//p. 2
public class Tile11 {

    private static double RATIO_m = 1.0 / 4.0;
    private static double RATIO_n = Hex.£H.apply(RATIO_m);

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon hexBC = Hex.hex(RATIO_n, HOR, Polygon.centreTransform(3 * RATIO_n, TWO, HOR));
        Polygon hexDE = Hex.hex(RATIO_n, HOR, centreTransform(3 * RATIO_n, HOR));

        return new PayloadSimple.Builder("hex_tile_11",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                        Pair.of(hexDE, SIX),
                        Pair.of(hexDE, FIVE),
                        Pair.of(hexBC, FOUR),
                        Pair.of(hexBC, THREE)
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
        Polygon hexKA = Hex.hex(RATIO_n, HOR);
        Polygon hexBC = Hex.hex(RATIO_n, HOR, Polygon.centreTransform(3 * RATIO_n, TWO, HOR));
        Polygon hexDE = Hex.hex(RATIO_n, HOR, centreTransform(3 * RATIO_n, HOR));

//        Polygon inner2 = Hex.hex(RATIO_1, Polygon.Type.HOR);
//        Polygon inner1 = inner2.getFramed();
//        Polygon inner3 = Hex.hex(RATIO_1 * RATIO_1, VER);
//        Polygon outer = Hex.hex(inner3.getRatio(), VER, Hex.centreTransform(1, VER));

//        Polygon outer = Hex.hex(RATIO_2, Polygon.Type.HOR, centreTransform(RATIO_1, Polygon.Type.VER));

        List<String> equations = Arrays.asList(
                "KA = (1/4) / h",
                "BC DE = KA"
        );

        Polygon mainRegistered = main.getRegistered();
        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_11_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(hexKA, ONE, "A"),
                        Triple.of(mainRegistered, TWO, "B"),
                        Triple.of(hexBC, FIVE, "C"),
                        Triple.of(mainRegistered, ONE, "D"),
                        Triple.of(hexDE, FOUR, "E")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainRegistered, Hex.DIAGONALS),
                        Pair.of(hexKA, Hex.PERIMETER),
                        Pair.of(hexBC, Hex.PERIMETER),
                        Pair.of(hexDE, Hex.PERIMETER)
                ), gray)
                .addLinesInstructions(
                        IntStream.range(2, 5).mapToObj(i -> Pair.of(Hex.hex(i * RATIO_n, HOR), Hex.PERIMETER))
                                .collect(toList())
                        , gray
                )
//                .addAllVertexesAsImportantPoints(asList(
//                        hexBC,
//                        hexDE
//                ))
                ;

    }

}