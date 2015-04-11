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

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.ONE;
import static java.lang.Math.PI;
import static java.lang.Math.tan;
import static java.util.Arrays.asList;

public class Tile30 {

    public static double ANGLE_1 = PI - PI / 4.0 - PI / 6.0;
    public static double ANGLE_2 = PI - ANGLE_1;
    public static double ANGLE_3 = PI - ANGLE_2 - PI / 3.0;
    public static double ANGLE = PI / 3.0 - PI / 4.0;


    public static double KB = H;
    public static double KC = KB * H;
    public static double AC = 1 - KC;
    public static double CD = AC * H;
    public static double AD = AC * P;
    public static double DE = CD / tan(ANGLE_3);
    public static double AE = DE + AD;
    public static double BE = 0.5 - AE;
    public static double BF = BE;
    public static double KF = KB - BF;
    public static double KG = KF * H;
    public static double GH = 0.5 * KG * tan(ANGLE);
    public static double KH = KG - GH;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
//        Polygon outer = Hex.hex(1 - RATIO_2, VER, Hex.centreTransform(1, DR_V));

        Polygon hexKH = Hex.hex(KH, VER);
        Polygon hexAE = Hex.hex(AE, VER, centreTransform(1, DR_V));

        Polygon hexKC = Hex.hex(KC, VER);

        return new PayloadSimple.Builder("hex_tile_30",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(Hex.hex(AE, VER, centreTransform(1, UR_V)), DOWN),
                                instruction(hexKH, DR_V),
                                instruction(Hex.hex(AE, VER, centreTransform(1, DOWN)), UR_V)
                        ),
                        asList(
                                instruction(hexAE, UP),
                                instruction(hexKC, DR_V),
                                instruction(hexAE, DL_V)
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
        Polygon mainReg = main.getRegistered();
        Polygon hexKC = Hex.hex(KC, VER);
        Polygon hexCD = Hex.hex(CD, HOR, centreTransform(KC, DR_V));
        Polygon hexAE = Hex.hex(AE, VER, centreTransform(1, DR_V));
        Polygon hexAE_Rot = Hex.hex(AE, VER, centreTransform(1, UR_V));
        Polygon hexKF = Hex.hex(KF, HOR);
        Polygon hexKG = hexKF.getRegistered();
        Polygon hexKH = Hex.hex(KH, VER);

        List<String> equations = Arrays.asList(
                "KHF = ANGLE_1 = PI - PI / 4.0 - PI / 6.0",
                "FHA = ECA =  ANGLE_2 = PI - ANGLE_1",
                "CEA = ANGLE_3 = PI - ANGLE_2 - PI / 3.0",
                "ANGLE = PI / 3.0 - PI / 4.0",
                "KB = h * KA",
                "KC = h * KB",
                "AC = 1 - KC",
                "CD = h * AC",
                "AD = 0.5 * AC",
                "DE = CD / tan(ANGLE_3)",
                "AE = DE + AD",
                "BE = 0.5 - AE",
                "BF = BE",
                "KF = KB - BF",
                "KG = h * KF",
                "GH = 0.5 * KG * tan(ANGLE)",
                "KH = KG - GH"
//                "DC=DE=KB",
//                "DB=1-KB"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_30_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, DR_V.getVertex(), "A"),
                        Triple.of(mainReg, RIGHT.getVertex(), "B"),
                        Triple.of(hexKC, DR_V.getVertex(), "C"),
                        Triple.of(hexCD, ONE, "D"),
                        Triple.of(hexAE, UP.getVertex(), "E"),
                        Triple.of(hexKF, RIGHT.getVertex(), "F"),
                        Triple.of(hexKG, DR_V.getVertex(), "G"),
                        Triple.of(hexKH, DR_V.getVertex(), "H")

                ))
                .addLinesInstructions(asList(
                        Pair.of(Hex.hex(AD, VER, centreTransform(1, DR_V)), Hex.PERIMETER),
                        Pair.of(hexKH, Hex.PERIMETER),
                        Pair.of(hexKG, Hex.PERIMETER),
                        Pair.of(hexKF, Hex.PERIMETER),
                        Pair.of(hexAE, Hex.PERIMETER),
                        Pair.of(hexCD, Hex.PERIMETER),
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainReg, Hex.PERIMETER),
                        Pair.of(mainReg, Hex.DIAGONALS),
                        Pair.of(hexKC, Hex.PERIMETER)
                ), gray)
                .addLinesInstructions(asList(
//                        Pair.of(inner2, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
//                        Pair.of(inner1, Hex.PERIMETER),
//                        Pair.of(inner3, Hex.PERIMETER)
                ), blue)
                .addAllVertexesAsImportantPoints(asList(
//                        hexCD 
//                        hexAE
//                        hexAE_Rot
                ))
                .withFontSize(15)
                ;

    }

}