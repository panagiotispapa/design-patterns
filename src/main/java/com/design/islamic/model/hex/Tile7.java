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
import static com.design.common.RatioHelper.£2;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;

public class Tile7 {

    public static double RATIO_AB = 1.0 - HEIGHT_RATIO;
    public static double RATIO_BE = $P.apply(RATIO_AB);
    public static double RATIO_KD = $H.andThen(£2.andThen(£H)).apply(1.0);

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon inner = Hex.hex(RATIO_KD, HOR);
        Polygon outer = Hex.hex(RATIO_BE, VER, centreTransform(1, DR_V));
        Polygon outerBig = Hex.hex(0.5, VER, centreTransform(1, DR_V));

        return new PayloadSimple.Builder("hex_tile_07",
                 Hex.ALL_VERTEX_INDEXES
        )
                .withLines(                asList(
                                asList(
                                        instruction(outer, UP),
                                        instruction(inner, DR_H)
                                ),
                                asList(
                                        instruction(outer, DL_V),
                                        instruction(inner, RIGHT)
                                ),
                                asList(
                                        instruction(outerBig, UP),
                                        instruction(outerBig, UL_V),
                                        instruction(outerBig, DL_V)
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

//        List<Point2D> hexGrid = Grid.gridFromStart(new Point2D.Double(0, 0), initialConditions.getRight() / 4.0,
//                Grid.Configuration.customRect(RECT_DIST_HEIGHT * 2 * 1.2, RECT_DIST_HEIGHT * 2), 24);
//                Grid.Configs.HEX_VER2.getConfiguration(), 24);

//        CentreConfiguration centreConfiguration = new CentreConfiguration(initialConditions.getRight() / 4.0, 8);
//        Set<Point2D> centresConfig = centreConfiguration.getCentresConfig(CentreConfiguration.Conf.HEX_THIRD, 1.0);

        Polygon main = Hex.hex(1, VER);
        Polygon mainReg = main.getRegistered();
        Polygon mainHor = main.getMirror();
        Polygon mainHorReg = mainHor.getRegistered();
        Polygon inner = Hex.hex(0.5, VER);
        Polygon innerReg = inner.getRegistered();
        Polygon innerHor = inner.getMirror();
        Polygon innerHorReg = innerHor.getRegistered();

        Polygon outerSmall = Hex.hex(RATIO_BE, VER, centreTransform(1, VER));
//        Polygon inner1 = Hex.hex(RATIO_1, HOR);
//        Polygon inner2 = Hex.hex(0.5, VER);
//        Polygon outer = Hex.hex(0.5, VER, centreTransform(1, VER));
//        Polygon outerReg = outer.getRegistered();

        List<String> equations = asList(
                "AB=1-h",
                "KC=0.5*h",
                "KD=KC/h",
                "BE=0.5*AB"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_07_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addLinesInstructions(asList(
                        Pair.of(main, Hex.PERIMETER),
                        Pair.of(mainHor, Hex.PERIMETER),
                        Pair.of(main, Hex.DIAGONALS),
                        Pair.of(mainHor, Hex.DIAGONALS),
                        Pair.of(mainHorReg, Hex.INNER_TRIANGLES)
//                                Pair.of(main.getRegistered(), Hex.INNER_TRIANGLES),
//                                Pair.of(outer, Hex.PERIMETER),
//                                Pair.of(outerReg, Hex.PERIMETER)
                ), gray)
                .addLinesInstructions(asList(
                        Pair.of(inner, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
                        Pair.of(innerHor, Hex.PERIMETER),
                        Pair.of(outerSmall, Hex.PERIMETER)
                ), green)
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addImportantPoints(asList(
                        Triple.of(main, DR_V.getVertex(), "B"),
                        Triple.of(mainHorReg, RIGHT.getVertex(), "A"),
                        Triple.of(innerHorReg, DR_V.getVertex(), "C"),
                        Triple.of(inner, DR_V.getVertex(), "D"),
                        Triple.of(outerSmall, UP.getVertex(), "E")
                ))
                .addEquations(equations);

    }

}