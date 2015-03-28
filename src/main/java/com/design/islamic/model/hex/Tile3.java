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
import static com.design.islamic.model.Hex.Corner.DR_H;
import static com.design.islamic.model.Hex.Corner.DR_V;
import static com.design.islamic.model.Hex.Corner.RIGHT;
import static com.design.islamic.model.Hex.HEIGHT_RATIO;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile3 {

    public static double ANGLE_1 = Math.atan(0.25 / HEIGHT_RATIO);
    public static double ANGLE_2 = Math.PI / 6.0 + ANGLE_1;

    public static double RATIO_1 = 0.5 * Math.tan(ANGLE_2);
    public static double RATIO_2 = HEIGHT_RATIO - RATIO_1;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon inner = Hex.hex(RATIO_2, HOR);

        return new PayloadSimple.Builder("hex_tile_03",
                 Hex.ALL_VERTEX_INDEXES
        )
                .withLines(
                        asList(
                                asList(
                                        instruction(inner, RIGHT),
                                        instruction(main, DR_V),
                                        instruction(inner, DR_H)
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
        Polygon outer = Hex.hex(0.5, VER, Hex.centreTransform(HEIGHT_RATIO, HOR));
//        Polygon outer2 = Hex.hex(0.5, Polygon.Type.VER, centreTransform(1, Polygon.Type.VER));
        Polygon mainReg = main.getRegistered();
        Polygon inner = Hex.hex(0.5 * 0.5, VER);
        Polygon inner2 = Hex.hex(1 - 0.5 * 0.5, VER);
        Polygon inner3 = Hex.hex(RATIO_2, HOR);

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
                        Triple.of(inner, TWO, "A"),
                        Triple.of(mainReg, ONE, "B"),
                        Triple.of(mainReg, FOUR, "D"),
                        Triple.of(main, Hex.Vertex.THREE, "C"),
                        Triple.of(mainReg, TWO, "E"),
                        Triple.of(main, FOUR, "F"),
                        Triple.of(inner2, ONE, "G"),
                        Triple.of(main, ONE, "H"),
                        Triple.of(inner3, ONE, "I")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainReg, Hex.DIAGONALS)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(outer, Hex.PERIMETER)
//                                Pair.of(outer2, Hex.PERIMETER),
//                                Pair.of(outer2, Hex.INNER_TRIANGLES)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(inner, Hex.PERIMETER),
                        Pair.of(inner3, Hex.PERIMETER)
                ), blue)
                .addMixedLinesInstructions(asList(
                        Pair.of(outer, FIVE),
                        Pair.of(mainReg, FOUR),
                        Pair.of(outer, TWO)

                ), gray);

    }

}