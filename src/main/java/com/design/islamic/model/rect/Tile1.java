package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.islamic.model.*;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Rect.Corner.*;
import static com.design.islamic.model.Rect.*;
import static java.lang.Math.PI;
import static java.lang.Math.tan;
import static java.util.Arrays.asList;

public class Tile1 {

    private static final double KA = 1.0;
    private static final double KB = KA / 2.0;
    private static final double BC = KB * tan(PI / 8.0);
    private static final double KD = KA * H;
    private static final double DE = BC;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon rectBC = Rect.rect(BC, HOR, centreTransform(KB, DR));
        Polygon rectBC_Rot = Rect.rect(BC, HOR, centreTransform(KB, DL));
        Polygon rectDE = Rect.rect(DE, VER, centreTransform(KD, DOWN));

        return new PayloadSimple.Builder("rect_tile_01",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(rectDE, RIGHT),
                                instruction(rectBC, DL),
                                instruction(rectBC_Rot, DR),
                                instruction(rectDE, LEFT)
                        ),
                        asList(
                                instruction(rectBC, DL),
                                instruction(rectBC, UR)
                        )
                ))
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Rect.rect(1, HOR);
        Polygon rectKB = Rect.rect(KB, HOR);
        Polygon rectBC = Rect.rect(BC, HOR, centreTransform(KB, DR));
        Polygon rectBC_Rot = Rect.rect(BC, HOR, centreTransform(KB, DL));
        Polygon rectKD = Rect.rect(KD, VER);
        Polygon rectDE = Rect.rect(DE, VER, centreTransform(KD, DOWN));

        List<String> equations = Arrays.asList(
                "KF = h"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_01_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, DR.getVertex(), "A"),
                        Triple.of(rectKB, DR.getVertex(), "B"),
                        Triple.of(rectBC, DL.getVertex(), "C"),
                        Triple.of(rectKD, DOWN.getVertex(), "D"),
                        Triple.of(rectDE, RIGHT.getVertex(), "E"),
                        Triple.of(rectDE, LEFT.getVertex(), "F"),
                        Triple.of(rectBC_Rot, DR.getVertex(), "G")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(rectKB, PERIMETER),
                        Pair.of(rectBC, PERIMETER),
                        Pair.of(rectBC, DIAGONALS),
                        Pair.of(rectKD, PERIMETER),
                        Pair.of(rectKD, DIAGONALS),
                        Pair.of(rectDE, PERIMETER),
                        Pair.of(rectBC_Rot, PERIMETER)
                ), gray)
                .addLinesInstructions(asList(
//                        Pair.of(hexKA, Hex.PERIMETER)
                ), green)
                .addLinesInstructions(asList(
//                        Pair.of(inner1, Hex.PERIMETER),
//                        Pair.of(hexKB, Hex.PERIMETER)
                ), blue)
                ;

    }

}