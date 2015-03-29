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

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;

public class Tile3 {

    private static double ANGLE_1 = Math.atan(0.25 / HEIGHT_RATIO);
    private static double ANGLE_2 = Math.PI / 6.0 + ANGLE_1;

    private static double IB = 0.5 * Math.tan(ANGLE_2);
    private static double KI = HEIGHT_RATIO - IB;
    private static double HL = $P.apply(0.5);
    private static double EL = $H.apply(0.5);
    private static double HM = EL + HL;
    private static double KM = 1 - HM;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon hexKI = Hex.hex(KI, HOR);

        return new PayloadSimple.Builder("hex_tile_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(
                        asList(
                                asList(
                                        instruction(hexKI, RIGHT),
                                        instruction(main, DR_V),
                                        instruction(hexKI, DR_H)
                                )
                        )
                )
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple2() {
        Polygon main = Hex.hex(1, VER);
        Polygon mainReg = main.getRegistered();
        Polygon hexKM = hex(KM, VER);

        return new PayloadSimple.Builder("hex_tile_03b",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(
                        asList(
                                asList(
                                        instruction(mainReg, RIGHT),
                                        instruction(hexKM, DR_V),
                                        instruction(mainReg, DR_H)
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

        double atan = Math.atan(0.5 / (2.0 * HEIGHT_RATIO));

        Polygon main = Hex.hex(1, VER);
        Polygon mainReg = main.getRegistered();
        Polygon hexBH = Hex.hex(0.5, VER, centreTransform(HEIGHT_RATIO, RIGHT));
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
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
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
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),

                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainReg, Hex.DIAGONALS)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(hexBH, Hex.PERIMETER),
                        Pair.of(hexBH, Hex.PERIMETER)
//                        Pair.of(hexKM, Hex.PERIMETER)
//                        Pair.of(hexKJ, Hex.PERIMETER)
//                                Pair.of(outer2, Hex.PERIMETER),
//                                Pair.of(outer2, Hex.INNER_TRIANGLES)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(hexKA, Hex.PERIMETER),
                        Pair.of(hexKI, Hex.PERIMETER)
                ), blue)
                .addMixedLinesInstructions(asList(
                        instruction(hexBH, UP),
                        instruction(mainReg, LEFT),
                        instruction(hexBH, DOWN)

                ), gray)
                .addMixedLinesInstructions(asList(
                        instruction($P.apply(0.5), centreTransform(1, UP), DOWN),
                        instruction(mainReg, UR_H)
                ), gray)
                ;

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        double atan = Math.atan(0.5 / (2.0 * HEIGHT_RATIO));

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
                .addMixedLinesInstructionsList(getPayloadSimple2().getLines(), red)
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
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),

                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainReg, Hex.DIAGONALS)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(hexKM, Hex.PERIMETER)
                ), green)
                .addSingleLinesInstructionsList(asList(asList(
                        instruction($P.apply(0.5), centreTransform(1, DR_V), UL_V),
                        instruction(mainReg, DR_H)
                )), gray)
                ;

    }

}