package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.centreTransform;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class TileStar {

    public static double RATIO_1 = 0.5;
    public static double RATIO_2 = H / (H + 0.5);
    public static double RATIO_3 = (1.5 - H) * 0.5;

    public static List<Pair<Polygon, Polygon.Vertex>> getLines(double ratio) {
        Polygon main = Hex.hex(H, HOR);
        Polygon inner = Hex.hex(ratio, VER);

        return
                asList(
                        instruction(inner, DR_V),
                        instruction(main, DR_H),
                        instruction(inner, DOWN)
                );
    }

    public static List<Pair<Polygon, Polygon.Vertex>> getLinesB(double ratio) {
        Polygon main = Hex.hex(H, VER);
        Polygon inner = Hex.hex(ratio, HOR);

        return
                asList(
                        instruction(main, UP),
                        instruction(inner, UR_H),
                        instruction(main, UR_V)
                );
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple1() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple
                .Builder(
                "hex_tile_star_01",
                Hex.ALL_VERTEX_INDEXES
        ).withPathsFullFromLines(asList(
                getLines(RATIO_1)
        ), whiteBold)
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple2() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_star_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFullFromLines(asList(
                        getLines(RATIO_2)
                ), whiteBold)
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple3() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_star_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFullFromLines(asList(
                        getLines(RATIO_3)
                ), whiteBold)
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple1b() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple
                .Builder(
                "hex_tile_star_01b",
                Hex.ALL_VERTEX_INDEXES
        ).withPathsFullFromLines(asList(
                getLinesB(RATIO_1)
        ), whiteBold)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple2b() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple
                .Builder(
                "hex_tile_star_02b",
                Hex.ALL_VERTEX_INDEXES
        ).withPathsFullFromLines(asList(
                getLinesB(RATIO_2)
        ), whiteBold)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple3b() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple
                .Builder(
                "hex_tile_star_03b",
                Hex.ALL_VERTEX_INDEXES
        ).withPathsFullFromLines(asList(
                getLinesB(RATIO_3)
        ), whiteBold)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper1() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, VER);
        Polygon registered = main.getRegistered();
        Polygon inner = Hex.hex(RATIO_1, VER);

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_01_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(getPayloadSimple1().getPathsFull(), red)
//                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(registered, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(inner, Hex.PERIMETER)
                ), blue);

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, VER);
        Polygon registered = main.getRegistered();
        Polygon inner = Hex.hex(1.0 / 2.0, VER);
        Polygon inner2 = Hex.hex(RATIO_2, VER);
        Polygon inner2Reg = inner2.getRegistered();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_02_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(getPayloadSimple2().getPathsFull(), red)
                .addEquations(asList(
                        "AB=AC",
                        "KB=h/(h+0.5)"
                ))
                .addImportantPoints(asList(
                        Triple.of(inner2, Hex.Vertex.SIX, "B"),
                        Triple.of(inner2Reg, ONE, "A"),
                        Triple.of(registered, ONE, "C")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(registered, Hex.DIAGONALS),
                        Pair.of(main, Hex.DIAGONALS)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(inner2, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(inner, Hex.PERIMETER)
                ), blue);

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper3() {
        String blueLight = newStyle("blue", 1, 0.3);

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        final double KA = 1;
        final double KE = KA * H;
        final double KD = KE;
        final double EB = KE;
        final double AB = EB - KA * 0.5;
        final double AF = AB * P;

        Polygon main = Hex.hex(1, VER);
        Polygon registered = main.getRegistered();
        Polygon outerSmall = Hex.hex(1.5 - KD, VER, Hex.centreTransform(2 * KD, RIGHT));

        Polygon inner = Hex.hex(H * 0.5 + H * H, HOR, centreTransform(RATIO_3, DR_V));
        Polygon outerBig = Hex.hex(2, VER, centreTransform(H, RIGHT));
        Polygon hexAF = Hex.hex(AF, VER, Hex.centreTransform(1, VER));

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_03_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(getPayloadSimple3().getPathsFull(), red)
                .addEquations(asList(
                        "KA = 1",
                        "KE = EB = h",
                        "AB = EB - EA = h - 0.5",
                        "AF = 0.5 * AB",
                        "DF = AD - AF = 0.5 - 0.5 * AB",
                        "BC = 2 * DF = 1 - AB = 1.5 - h"
                ))
                .addAllVertexesAsImportantPoints(asList(
//                        hexAF
                ))
                .addImportantPoints(asList(
                        Triple.of(main, ONE, "A"),
                        Triple.of(outerSmall, THREE, "B"),
                        Triple.of(outerSmall, FOUR, "C"),
                        Triple.of(registered, ONE, "D"),
                        Triple.of(registered, TWO, "E"),
                        Triple.of(hexAF, FIVE, "F")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(outerSmall, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)
//                                Pair.of(outer, Hex.DIAGONALS)

                ), gray)
                .addSinglePaths(asList(
                        Pair.of(registered, Hex.PERIMETER)
                ), green)
                .addFullPathsFromLines(asList(asList(
                        instruction(outerSmall, DL_V),
                        instruction(registered, RIGHT),
                        instruction(outerSmall, UL_V)

                )), green)
                .addFullPathsFromLines(asList(asList(
                        instruction(inner, DL_H),
                        instruction(inner, UR_H)

                )), blue)
                .addFullPathsFromLines(asList(asList(
                        instruction(outerBig, DOWN),
                        instruction(outerBig, UP)

                )), gray)
                .addCircle(
                        asList(
                                registered
                        ), blueLight
                )
                ;

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper1b() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, HOR);
        Polygon registered = main.getRegistered();
        Polygon inner = Hex.hex(RATIO_1, HOR);

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_01b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(getPayloadSimple1b().getPathsFull(), red)
//                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(registered, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(inner, Hex.PERIMETER)
                ), blue);

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2b() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, HOR);
        Polygon registered = main.getRegistered();
        Polygon inner = Hex.hex(RATIO_2, HOR);

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_02b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(getPayloadSimple2b().getPathsFull(), red)
//                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(registered, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(inner, Hex.PERIMETER)
                ), blue);

    }

    @DesignSupplier
    public static DesignHelper getDesignHelper3b() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Polygon main = Hex.hex(1, HOR);
        Polygon registered = main.getRegistered();
        Polygon inner = Hex.hex(RATIO_3, HOR);

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_star_03b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(getPayloadSimple3b().getPathsFull(), red)
//                .addEquations(equations)
                .addSinglePaths(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS)
                ), gray)
                .addSinglePaths(asList(
                        Pair.of(registered, Hex.PERIMETER)
                ), green)
                .addSinglePaths(asList(
                        Pair.of(inner, Hex.PERIMETER)
                ), blue);

    }

}