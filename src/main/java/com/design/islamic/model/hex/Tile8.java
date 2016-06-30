package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Polygon;
import com.design.common.Polygon.ActualVertex;
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
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.centreTransform;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class Tile8 {

    private static final double KA = 1.0;
    private static final double KD = KA * H;
    private static final double KB = 0.5;
    private static final double KC = KB / H;
    private static final double AC = 1 - KC;
    private static final double AD = 1 - KD;
    private static final double AE = AC * H;
    private static final double AF = AE * H;
    private static final double FE = AE * 0.5;
    private static final double AG = AC * 0.5;
    private static final double KF = 1 - AF;
    private static final double KH = FE;
    private static final double KI = KH / H;
    private static final double KJ = 2.0 * KH;
    private static final double JA = 1 - KJ;
    private static final double IJ = KJ - KI;
    private static final double JD = JA - AD;
    private static final double JM = IJ * H;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon mainReg = main.getRegistered();
        Polygon hexAG = Hex.hex(AC * 0.5, VER, centreTransform(1, DR_V));
        Polygon hexJD = Hex.hex(JD, HOR, centreTransform(KJ, DR_V));
        Polygon hexJDRot = Hex.hex(JD, HOR, centreTransform(KJ, UR_V));
        Polygon hexJM = Hex.hex(JM, HOR, centreTransform(KJ, DR_V));
        Polygon hexJM_2 = Hex.hex(JM, VER, centreTransform(KJ, RIGHT));
        Polygon hexJM_3 = Hex.hex(JM, VER, centreTransform(KJ, DR_H));
        Polygon hexJM_4 = Hex.hex(JM, HOR, centreTransform(KJ, UR_V));
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_08",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(VertexPaths.of(
                        VertexPath.of(
                                instruction(hexJM_3, UL_V),
                                instruction(hexAG, DL_V),
                                instruction(hexAG, UL_V),
                                instruction(hexAG, UP),
                                instruction(hexJM_2, UL_V)
                        ),
                        VertexPath.of(

                                instruction(hexJM_4, LEFT),
                                instruction(hexJDRot, RIGHT),
                                instruction(mainReg, RIGHT),
                                instruction(hexJD, RIGHT),
                                instruction(hexJM, LEFT)
                        )
                        ), whiteBold
                )
                .withSize(PayloadSimple.Size.MEDIUM)
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, VER);
        Polygon mainHor = main.getMirror();
        Polygon mainReg = main.getRegistered();
        Polygon mainHorReg = mainHor.getRegistered();

        Polygon hexKB = Hex.hex(KB, VER);
        Polygon hexKC = Hex.hex(KC, VER);
        Polygon hexKD = Hex.hex(KD, VER);
        Polygon hexAE = Hex.hex(AE, HOR, centreTransform(1, DR_V));
        Polygon hexAF = Hex.hex(AF, VER, centreTransform(1, DR_V));
//        Polygon hexFE = Hex.hex(hexAE.getRatio() * 0.5, VER);
        Polygon hexKF = Hex.hex(KF, HOR);
        Polygon hexAG = Hex.hex(AC * 0.5, VER, centreTransform(1, DR_V));
        Polygon hexKH = Hex.hex(KH, VER);
        Polygon hexKI = Hex.hex(KI, VER);
        Polygon hexKJ = Hex.hex(KJ, VER);
        Polygon hexJD = Hex.hex(JD, HOR, centreTransform(KJ, VER));
        Polygon hexJARot = Hex.hex(JA, HOR, centreTransform(KJ, UR_V));
        Polygon hexJM = Hex.hex(JM, HOR, centreTransform(KJ, VER));
        Polygon hexJM_2 = Hex.hex(JM, VER, centreTransform(KJ, RIGHT));
        Polygon hexJM_3 = Hex.hex(JM, VER, centreTransform(KJ, DR_H));
        Polygon hexJM_4 = Hex.hex(JM, HOR, centreTransform(KJ, UR_V));

        Polygon hexRot1 = Hex.hex(1, HOR, centreTransform(FE, VER));
        Polygon hexRot2 = Hex.hex(1, VER, centreTransform(FE, HOR));
        Polygon hexRot3 = Hex.hex(1, VER, centreTransform(KF, HOR));

        List<String> equations = asList(
                "KB = 0.5",
                "KC = KB/h",
                "KD = h",
                "AC = 1 - KC",
                "AD = 1 - h",
                "AE = AC * h",
                "AF = AE * h",
                "FE = AE * 0.5",
                "AG = AC * 0.5",
                "KH = FE",
                "KI = KH / h",
                "KJ = 2 * KH",
                "JA = 1 - KJ",
                "JL = JA",
                "IJ = KJ-KI",
                "JM = IJ*h"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_08_design")
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainHor, Hex.DIAGONALS),
                        Pair.of(hexAE, Hex.PERIMETER)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.INNER_TRIANGLES)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(mainHor, Hex.INNER_TRIANGLES),
                        Pair.of(mainHor, Hex.PERIMETER)
                ), blue)
                .addAllVertexesAsImportantPoints(
                        asList(
//                        hexJARot,
//                        hexKI,
//                        hexAG,
//                        hexJM,
//                        hexJM_2,
//                        hexJM_3,
//                                hexJM_4
                        )
                )


                .addFullPaths(VertexPaths.of(
                        VertexPath.of(
                                ActualVertex.of(hexRot1, THREE),
                                ActualVertex.of(hexRot1, SIX)
                        ),
                        VertexPath.of(
                                ActualVertex.of(hexRot2, TWO),
                                ActualVertex.of(hexRot2, FIVE)
                        ),
                        VertexPath.of(
                                ActualVertex.of(hexRot3, TWO),
                                ActualVertex.of(hexRot3, FIVE)
                        ),
                        VertexPath.of(
                                ActualVertex.of(main, SIX),
                                ActualVertex.of(mainHor, ONE),
                                ActualVertex.of(main, ONE)
                        ),
                        VertexPath.of(
                                ActualVertex.of(mainHorReg, SIX),
                                ActualVertex.of(mainReg, ONE),
                                ActualVertex.of(mainHorReg, ONE)
                        )
                ), gray)
                .addImportantVertexes(
                        ImportantVertex.of(main, DR_V.getVertex(), "A"),
                        ImportantVertex.of(hexKB, DR_V.getVertex(), "B"),
                        ImportantVertex.of(hexKC, DR_V.getVertex(), "C"),
                        ImportantVertex.of(hexKD, DR_V.getVertex(), "D"),
                        ImportantVertex.of(hexAE, UL_H.getVertex(), "E"),
                        ImportantVertex.of(hexAF, UL_V.getVertex(), "F"),
                        ImportantVertex.of(hexKF, RIGHT.getVertex(), "F"),
                        ImportantVertex.of(hexAG, UL_V.getVertex(), "G"),
                        ImportantVertex.of(hexKH, DR_V.getVertex(), "H"),
                        ImportantVertex.of(hexKI, DOWN.getVertex(), "I"),
                        ImportantVertex.of(hexKJ, DR_V.getVertex(), "J"),
                        ImportantVertex.of(hexJD, DR_H.getVertex(), "L"),
                        ImportantVertex.of(hexJM, UP.getVertex(), "M")
                )

                ;

    }

}