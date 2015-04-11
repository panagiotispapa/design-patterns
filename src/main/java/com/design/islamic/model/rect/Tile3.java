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
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.model.Rect.Corner.DR;
import static com.design.islamic.model.Rect.Corner.UR;
import static com.design.islamic.model.Rect.*;
import static java.util.Arrays.asList;

public class Tile3 {

    private static final double KA = 1.0;
    private static final double AB = KA / 4.0;
    private static final double KB = KA - AB;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Polygon main = Rect.rect(1, HOR);
        Polygon rectKA = Rect.rect(KA, HOR);
        Polygon rectKB = Rect.rect(KB, HOR);
        Polygon rectAB = Rect.rect(AB, HOR, centreTransform(KA, DR));

        return new PayloadSimple.Builder("rect_tile_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(rectKB, UR),
                                instruction(rectKB, DR)
                        ),
                        asList(
                                instruction(rectKB, DR),
                                instruction(rectKA, DR)
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
        Polygon rectAB = Rect.rect(AB, HOR, centreTransform(KA, DR));

        List<String> equations = Arrays.asList(
                "AB = KA / 4.0"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_03_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, DR.getVertex(), "A"),
                        Triple.of(rectKB, DR.getVertex(), "B")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(rectKB, PERIMETER),
                        Pair.of(rectAB, PERIMETER)
                ), gray)
                .addLinesInstructions(asList(
//                        Pair.of(rectAC, PERIMETER)
                ), green)
                .addLinesInstructions(asList(
//                        Pair.of(inner1, ),
//                        Pair.of(hexKB, )
                ), blue)
                ;

    }

}