package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.common.RatioHelper;
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

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.THREE;
import static com.design.islamic.model.Hex.centreTransform;
import static com.design.islamic.model.Hex.instruction;
import static java.lang.Math.PI;
import static java.util.Arrays.asList;

public class Tile29 {

    private static double ANGLE_1 = PI / 3.0 - PI / 4.0;
    private static double RATIO_M = Math.tan(ANGLE_1);
    private static double RATIO_N = Math.cos(ANGLE_1);

    private static double KB = H;
    private static double KC = KB * H;
    private static double CA = 1 - KC;
    private static double CD = RATIO_M * CA;
    private static double DA = CA / RATIO_N;
    private static double DE = RatioHelper.Ratios.RECT.$H().apply(DA);
    private static double AE = DE;
    private static double AF = 2.0 * AE;
    private static double BF = 0.5 - AF;
    private static double BG = BF;
    private static double KG = KB - BG;
    private static double KH = KG * H;
    private static double HI = 0.5 * KG * RATIO_M;
    private static double KI = KH - HI;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
//        Polygon outer = Hex.hex(1 - RATIO_2, VER, Hex.centreTransform(1, DR_V));
        Polygon main = Hex.hex(1, VER);
        Polygon hexCD = Hex.hex(CD, HOR, centreTransform(KC, DR_V));
        Polygon hexAE = Hex.hex(AE, VER, centreTransform(1, DR_V));
        Polygon hexAF = Hex.hex(AF, VER, centreTransform(1, DR_V));
        Polygon hexKG = Hex.hex(KG, HOR);
        Polygon hexKI = Hex.hex(KI, VER);
        Polygon hexBF = Hex.hex(BF, VER, centreTransform(KB, DR_H));

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_29",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(() -> asList(
                        () -> asList(
                                instruction(main, DR_V),
                                () -> Pair.of(hexCD, THREE),
                                () -> Pair.of(hexAF, THREE)
                        ),
                        () -> asList(

                                () -> Pair.of(hexBF, THREE),
                                instruction(hexKG, DR_H),
                                instruction(hexKI, DOWN),
                                instruction(hexKG, DL_H)
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

        Polygon hexKC = Hex.hex(KC, VER);
        Polygon hexCD = Hex.hex(CD, HOR, centreTransform(KC, DR_V));
        Polygon hexAE = Hex.hex(AE, VER, centreTransform(1, DR_V));
        Polygon hexAF = Hex.hex(AF, VER, centreTransform(1, DR_V));
        Polygon hexKG = Hex.hex(KG, HOR);
        Polygon hexKH = Hex.hex(KH, VER);
        Polygon hexKI = Hex.hex(KI, VER);
        Polygon hexBF = Hex.hex(BF, VER, centreTransform(KB, DR_H));

        List<String> equations = Arrays.asList(
                "IGH = ANGLE_1 = PI / 3.0 - PI / 4.0",
                "m = tan(ANGLE_1)",
                "n = cos(ANGLE_1)",
                "KB = h * KA",
                "KC = h * KB",
                "CA = 1 - KC",
                "CD = m * CA",
                "DA = CA / n",
                "DE = DA * cos(45)",
                "AE = DE",
                "AF = 2.0 * AE",
                "BF = 0.5 - AF",
                "BG = BF",
                "KG = KB - BG",
                "KH = h * KG",
                "HI = 0.5 * KG * m",
                "KI = KH - HI"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_29_design")
//                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, DR_V.getVertex(), "A"),
                        Triple.of(mainReg, DR_H.getVertex(), "B"),
                        Triple.of(hexKC, DR_V.getVertex(), "C"),
                        Triple.of(hexCD, THREE, "D"),
                        Triple.of(hexAE, THREE, "E"),
                        Triple.of(hexAF, THREE, "F"),
                        Triple.of(hexKG, DR_H.getVertex(), "G"),
                        Triple.of(hexKH, DOWN.getVertex(), "H"),
                        Triple.of(hexKI, DOWN.getVertex(), "I"),
                        Triple.of(hexBF, THREE, "J")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainReg, Hex.PERIMETER),
                        Pair.of(mainReg, Hex.DIAGONALS),
                        Pair.of(hexBF, Hex.PERIMETER)
//                        Pair.of(hexAE, Hex.PERIMETER)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(hexKG, Hex.PERIMETER)
                ), green)
                .addAllVertexesAsImportantPoints(asList(
//                        hexCD
//                        hexAE
//                        hexKG
                ))
                ;

    }

}