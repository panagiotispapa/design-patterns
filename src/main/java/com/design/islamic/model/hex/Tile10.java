package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.Polygon.VertexPath;
import com.design.common.Polygon.VertexPaths;
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
import static com.design.islamic.model.Hex.centreTransform;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile10 {

    private static double KA = 1.0;
    private static double KF = KA * H;
    private static double AB = KA / 3.0;
    private static double AC = AB / H;
    private static double KE = KF - 0.5 * AC;
    private static double EC = 0.5 - KE;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Polygon hexAC = Hex.hex(AC, HOR, centreTransform(1, DR_V));
        Polygon outerReg = hexAC.getRegistered();

        Polygon hexKE = Hex.hex(KE, HOR);
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_10",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(VertexPaths.of(
                        VertexPath.of(
                                instruction(outerReg, DL_V),
                                instruction(hexAC, LEFT),
                                instruction(hexAC, UL_H),
                                instruction(outerReg, UP)
                        ),
                        VertexPath.of(
                                instruction(hexKE, RIGHT),
                                instruction(hexAC, UL_H)
                        ),
                        VertexPath.of(
                                instruction(hexKE, DR_H),
                                instruction(hexAC, LEFT)
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
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainReg, Hex.DIAGONALS),
                        Pair.of(hexAC, Hex.PERIMETER)

                ), gray)
                .addSinglePaths(asList(
                        Pair.of(hexKE, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(hexEC, Hex.PERIMETER)

                ), blue)
                .addImportantVertexes(
                        ImportantVertex.of(main, DR_V.getVertex(), "A"),
                        ImportantVertex.of(outerReg, UP.getVertex(), "B"),
                        ImportantVertex.of(hexAC, UL_H.getVertex(), "C"),
                        ImportantVertex.of(hexAC, UR_H.getVertex(), "D"),
                        ImportantVertex.of(hexKE, RIGHT.getVertex(), "E"),
                        ImportantVertex.of(mainReg, RIGHT.getVertex(), "F")
                )
                .addAllVertexesAsImportantPoints(asList(
//                        outer
                ))
                ;

    }

}