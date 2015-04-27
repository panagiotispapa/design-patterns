package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.Mappings;
import com.design.common.Polygon;
import com.design.common.RatioHelper.P6;
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

import static com.design.common.Polygon.Type.VER;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile5 {

    public static double KD = 1.0;
    public static double KF = KD * P6.H;
    public static double KG = KD / 2.0;
    public static double KA = KG / P6.H;
    public static double KB = Mappings.<Double>chain(i -> i / 2.0, i -> i / P6.H).apply(KA);
    public static double RATIO_2 = KA * KA;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon outer = Hex.hex(1 - RATIO_2, VER, Hex.centreTransform(1, DR_V));
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_05",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFullFromLines(asList(
                        asList(
                                instruction(outer, UP),
                                instruction(outer, UL_V),
                                instruction(outer, DL_V)
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
        Polygon hexKA = Hex.hex(KA, Polygon.Type.HOR);
        Polygon inner1 = hexKA.getFramed();
        Polygon hexKB = Hex.hex(KB, VER);
        Polygon outer = Hex.hex(hexKB.getRatio(), VER, Hex.centreTransform(1, DR_V));
//        Polygon innerReg = inner.getRegistered();


        List<String> equations = Arrays.asList(
                "KF = h",
                "KG = KD / 2",
                "KA = KG / h",
                "KB = (0.5*KA)/h",
                "KC = KA / h = 2 * KB",
                "DC = DE = KB",
                "DB = 1 - KB"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_05_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(hexKA, RIGHT.getVertex(), "A"),
                        Triple.of(hexKB, DR_V.getVertex(), "B"),
                        Triple.of(inner1, DR_V.getVertex(), "C"),
                        Triple.of(main, DR_V.getVertex(), "D"),
                        Triple.of(outer, DL_V.getVertex(), "E"),
                        Triple.of(main.getRegistered(), RIGHT.getVertex(), "F"),
                        Triple.of(Hex.hex(KG, VER), DR_V.getVertex(), "G")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(main, Hex.INNER_TRIANGLES),
                        Pair.of(hexKA, Hex.INNER_TRIANGLES),
                        Pair.of(outer, Hex.PERIMETER)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(hexKA, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(inner1, Hex.PERIMETER),
                        Pair.of(hexKB, Hex.PERIMETER)
                ), blue)
                ;

    }

}