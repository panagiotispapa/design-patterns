package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile6 {

    public static double KB = 1.0;
    public static double KA = KB / 2.0;
    public static double BA = KA;
    public static double BC = BA * H;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon hexKA = Hex.hex(KA, VER);
        Polygon hexBC = Hex.hex(BC, HOR, Hex.centreTransform(1, DR_V));
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_06",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFullFromLines(asList(
                        asList(
                                instruction(main, DR_V),
                                instruction(hexBC, UL_H),
                                instruction(hexKA, DOWN)
                        ),
                        asList(
                                instruction(main, DR_V),
                                instruction(hexBC, LEFT),
                                instruction(hexKA, UR_V)
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
        Polygon hexKA = Hex.hex(KA, VER);
        Polygon hexBA = Hex.hex(BA, VER, Hex.centreTransform(1, DR_V));
        Polygon hexBC = hexBA.getRegistered();

        List<String> equations = asList(
                "KA=AB=0.5",
                "KB=1",
                "BC=BD=AB*h=0.5*h"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_06_design")
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.INNER_TRIANGLES),
                        Pair.of(main.getRegistered(), Hex.INNER_TRIANGLES),
                        Pair.of(hexBA, Hex.PERIMETER),
                        Pair.of(hexBC, Hex.PERIMETER)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(hexKA, Hex.PERIMETER)
                ), green)
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addEquations(
                        equations
                )
                .addImportantPoints(asList(
                        Triple.of(hexKA, DR_V.getVertex(), "A"),
                        Triple.of(main, DR_V.getVertex(), "B"),
                        Triple.of(hexBC, LEFT.getVertex(), "C"),
                        Triple.of(hexBC, UL_H.getVertex(), "D")
                ))
                .addFullPaths(getPayloadSimple().getPathsFull(), red)
                ;

    }


}