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
import static com.design.common.RatioHelper.P6.P;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

public class Tile3 {


    private static double KA = 1.0;
    private static double KB = KA * H;
    private static double HEIGHT = KB;

    private static double ANGLE_1 = Math.atan(0.25 / HEIGHT);
    private static double ANGLE_2 = Math.PI / 6.0 + ANGLE_1;

    private static double IB = 0.5 * Math.tan(ANGLE_2);
    private static double KI = KB - IB;
    private static double HL = 0.5 * P;
    private static double EL = 0.5 * H;
    private static double HM = EL + HL;
    private static double KM = 1 - HM;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon hexKI = Hex.hex(KI, HOR);
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(() ->
                                asList(
                                        () -> asList(
                                                instruction(hexKI, RIGHT),
                                                instruction(main, DR_V),
                                                instruction(hexKI, DR_H)
                                        )
                                ), whiteBold
                )
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple2() {
        Polygon main = Hex.hex(1, VER);
        Polygon mainReg = main.getRegistered();
        Polygon hexKM = hex(KM, VER);
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_03b",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(() ->
                                asList(
                                        () -> asList(
                                                instruction(mainReg, RIGHT),
                                                instruction(hexKM, DR_V),
                                                instruction(mainReg, DR_H)
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
        Polygon mainReg = main.getRegistered();
        Polygon hexBH = Hex.hex(0.5, VER, centreTransform(KB, RIGHT));
        Polygon hexKA = Hex.hex(0.5 * 0.5, VER);
        Polygon hexKI = Hex.hex(KI, HOR);
//        Polygon hexHL = Hex.hex(HL, VER, centreTransform(1, DR_V));
//        Polygon hexKM = hex(KM, VER);

        List<String> equations = asList(
                "DC/DB = AK/KB",
                "KA=1/4",
                "HDB=th",
                "tan(th)=HB/BD=0.25/h",
                "DKH=5*(PI/6)",
                "KHD=PI-DKH-th=(PI/6)-th",
                "IHB=(PI/6)+th",
                "IB=0.5*tan(IHB)"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_03_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(hexKA, DOWN.getVertex(), "A"),
                        Triple.of(mainReg, RIGHT.getVertex(), "B"),
                        Triple.of(mainReg, LEFT.getVertex(), "D"),
                        Triple.of(main, DL_V.getVertex(), "C"),
                        Triple.of(mainReg, DR_H.getVertex(), "E"),
                        Triple.of(main, UL_V.getVertex(), "F"),
                        Triple.of(main, DR_V.getVertex(), "H"),
                        Triple.of(hexKI, RIGHT.getVertex(), "I")
//                        Triple.of(hexHL, UL_V.getVertex(), "L"),
//                        Triple.of(hexKM, DR_V.getVertex(), "M")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),

                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainReg, Hex.DIAGONALS)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(hexBH, Hex.PERIMETER),
                        Pair.of(hexBH, Hex.PERIMETER)
//                        Pair.of(hexKM, Hex.PERIMETER)
//                        Pair.of(hexKJ, Hex.PERIMETER)
//                                Pair.of(outer2, Hex.PERIMETER),
//                                Pair.of(outer2, Hex.INNER_TRIANGLES)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(hexKA, Hex.PERIMETER),
                        Pair.of(hexKI, Hex.PERIMETER)
                ), blue)
                .addFullPaths(() -> asList(() -> asList(
                                instruction(hexBH, UP),
                                instruction(mainReg, LEFT),
                                instruction(hexBH, DOWN)
                        )), gray
                );

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        Polygon main = Hex.hex(1, VER);
        Polygon mainReg = main.getRegistered();
        Polygon hexHL = Hex.hex(HL, VER, centreTransform(1, DR_V));
        Polygon hexKM = hex(KM, VER);

        List<String> equations = asList(
//                "DC/DB = AK/KB",
//                "KA=1/4",
//                "HDB=th",
//                "tan(th)=HB/BD=0.25/h",
//                "DKH=5*(PI/6)",
//                "KHD=PI-DKH-th=(PI/6)-th",
//                "IHB=(PI/6)+th",
//                "IB=0.5*tan(IHB)"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_03b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(() -> getPayloadSimple2().getPathsFull(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
//                        Triple.of(hexKA, TWO, "A"),
                        Triple.of(mainReg, ONE, "B"),
                        Triple.of(mainReg, FOUR, "D"),
                        Triple.of(main, THREE, "C"),
                        Triple.of(mainReg, TWO, "E"),
                        Triple.of(main, FOUR, "F"),
                        Triple.of(main, ONE, "H"),
                        Triple.of(hexHL, FOUR, "L"),
                        Triple.of(hexKM, ONE, "M")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),

                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainReg, Hex.DIAGONALS)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(hexKM, Hex.PERIMETER)
                ), green)
                .addSinglePaths(() -> asList(
                                () -> asList(
                                        instruction(0.5 * P, centreTransform(1, DR_V), UL_V),
                                        instruction(mainReg, DR_H)
                                )), gray
                )
                ;

    }

}