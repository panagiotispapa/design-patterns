package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Grid;
import com.design.common.Mappings;
import com.design.common.Polygon;
import com.design.common.Polygon.VertexPath;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.centreTransform;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile2b {

    private static double KB = H / (H + 0.5);
    private static double BD = Mappings.<Double>chain(i -> 1 - i, i -> i * H).apply(KB);
    private static double KC = KB * H;
    private static double KF = 0.5 * KB;
    private static double KG = 0.5 * KB * H;


    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon hexKB = Hex.hex(KB, VER);
        Polygon hexBD = Hex.hex(BD, HOR, Hex.centreTransform(KB, DR_V));
        Polygon hexKF = Hex.hex(KF, VER);
        Polygon hexKC = Hex.hex(KC, HOR);
        Polygon hexBG = Hex.hex(KG, HOR, centreTransform(KB, VER));

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_02b",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        Polygon.VertexPaths.of(
                                VertexPath.of(
                                        instruction(hexBD, RIGHT),
                                        instruction(hexKB, DR_V),
                                        instruction(hexBD, DR_H)
                                ),
                                VertexPath.of(
                                        instruction(hexKB, DR_V),
                                        instruction(hexKB, DOWN)
                                ),
                                VertexPath.of(
                                        instruction(hexKF, DR_V),
                                        instruction(hexKC, DR_H),
                                        instruction(hexKF, DOWN)
                                ),
                                VertexPath.of(
                                        instruction(hexKC, DR_H),
                                        instruction(hexBG, DL_H),
                                        instruction(hexBD, DR_H),
                                        instruction(hexBD, RIGHT),
                                        instruction(hexBG, UR_H),
                                        instruction(hexKC, RIGHT)
                                )
                        ), whiteBold
                )
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, VER);
        Polygon hexKB = Hex.hex(KB, VER);
        Polygon hexKBReg = hexKB.getRegistered();
        Polygon hexKF = Hex.hex(KF, VER);
        Polygon hexBG = Hex.hex(KG, HOR, centreTransform(KB, VER));

        Polygon outer = Hex.hex(BD, HOR, Hex.centreTransform(KB, VER));

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_02b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(asList(
                        "KB=h/(h+0.5)",
                        "BD=h*(1-KB)",
                        "KF=KB/2",
                        "KG=(KB/2)*h"
                ))
                .addImportantVertexes(asList(
                        ImportantVertex.of(main, DR_V.getVertex(), "A"),
                        ImportantVertex.of(main.getRegistered(), RIGHT.getVertex(), "E"),
                        ImportantVertex.of(hexKB, DR_V.getVertex(), "B"),
                        ImportantVertex.of(hexKBReg, RIGHT.getVertex(), "C"),
                        ImportantVertex.of(outer, DR_V.getVertex(), "D"),
                        ImportantVertex.of(hexKF, DR_V.getVertex(), "F"),
                        ImportantVertex.of(hexBG, DL_H.getVertex(), "G")

                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(main.getRegistered(), Hex.DIAGONALS),
                        Pair.of(outer, Hex.DIAGONALS)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(hexKB, Hex.PERIMETER),
                        Pair.of(hexBG, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(outer, Hex.PERIMETER)
                ), blue)
                ;

    }

}