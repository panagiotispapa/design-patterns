package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

//p. 2
public class Tile11 {

    private static double RATIO_m = 1.0 / 4.0;
    private static double RATIO_n = RATIO_m / H;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon hexBC = Hex.hex(RATIO_n, HOR, Hex.centreTransform(3 * RATIO_n, DR_H));
        Polygon hexDE = Hex.hex(RATIO_n, HOR, centreTransform(3 * RATIO_n, RIGHT));

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_11",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(() -> asList(
                        () -> asList(
                                instruction(hexDE, UR_H),
                                instruction(hexDE, UL_H),
                                instruction(hexBC, LEFT),
                                instruction(hexBC, DL_H)
                        )

                ), whiteBold)
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, VER);
        Polygon hexKA = Hex.hex(RATIO_n, HOR);
        Polygon hexBC = Hex.hex(RATIO_n, HOR, centreTransform(3 * RATIO_n, DR_H));
        Polygon hexDE = Hex.hex(RATIO_n, HOR, centreTransform(3 * RATIO_n, RIGHT));

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
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(hexKA, ONE, "A"),
                        Triple.of(mainRegistered, TWO, "B"),
                        Triple.of(hexBC, FIVE, "C"),
                        Triple.of(mainRegistered, ONE, "D"),
                        Triple.of(hexDE, FOUR, "E")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(mainRegistered, DIAGONALS),
                        Pair.of(hexKA, PERIMETER),
                        Pair.of(hexBC, PERIMETER),
                        Pair.of(hexDE, PERIMETER)
                ), gray)
                .addSinglePaths(
                        IntStream.range(2, 5).mapToObj(i -> Pair.of(Hex.hex(i * RATIO_n, HOR), PERIMETER))
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