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
import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.SIX;
import static com.design.islamic.model.Hex.Vertex.TWO;
import static com.design.islamic.model.Hex.centreTransform;
import static com.design.islamic.model.Hex.instruction;
import static java.lang.Math.PI;
import static java.util.Arrays.asList;

public class Tile28 {

    public static double KB = 0.5;
    public static double KC = KB / H;
    private static double ANGLE_1 = PI / 3.0 - PI / 4.0;
    private static double BD = 0.5 * KC * Math.tan(ANGLE_1);
    private static double KD = KB - BD;
    private static double KE = H;
    private static double CE = KC - KE;
    private static double EF = CE;


    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, HOR);

        Polygon hexKD = Hex.hex(KD, HOR);
        Polygon hexEF = Hex.hex(EF, HOR, centreTransform(KE, DR_V));
        Polygon hexEF_Rot = Hex.hex(EF, HOR, centreTransform(KE, UR_V));

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_28",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(() -> asList(
                        () -> asList(
                                instruction(hexKD, RIGHT),
                                () -> Pair.of(hexEF, SIX)
                        ),
                        () -> asList(
                                instruction(hexKD, RIGHT),
                                () -> Pair.of(hexEF_Rot, TWO)
                        )

                ), whiteBold)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, HOR);
        Polygon hexKB = Hex.hex(KB, HOR);
        Polygon hexKC = Hex.hex(KC, VER);
        Polygon hexKD = Hex.hex(KD, HOR);
        Polygon hexKE = Hex.hex(KE, VER);
        Polygon hexEF = Hex.hex(EF, HOR, centreTransform(KE, DR_V));
        Polygon hexEF_Rot = Hex.hex(EF, HOR, centreTransform(KE, UR_V));

        List<String> equations = Arrays.asList(
                "KE = h",
                "KB = 0.5 * KA",
                "BD = tan(15) * BC",
                "KD = KA - DB - BA",
                "KC = KB / h",
                "CE = KE - KC",
                "CE = EF"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_28_design")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, RIGHT.getVertex(), "A"),
                        Triple.of(hexKB, RIGHT.getVertex(), "B"),
                        Triple.of(hexKC, DR_V.getVertex(), "C"),
                        Triple.of(hexKD, RIGHT.getVertex(), "D"),
                        Triple.of(hexKE, DR_V.getVertex(), "E"),
                        Triple.of(hexEF, SIX, "F")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(main, Hex.INNER_TRIANGLES),
                        Pair.of(main.getRegistered(), Hex.DIAGONALS)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(hexEF, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                ), blue)
                .addAllVertexesAsImportantPoints(asList(
//                        hexEF,
//                        hexEF_Rot
                ))
                ;

    }

}