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
import static java.util.Arrays.asList;

public class Tile4 {

    private static final double KA = 1.0;
    private static final double KB = KA / 5.0;
    private static final double KC = KA * H;
    private static final double CD = KB;
    private static final double CE = CD * H;

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Polygon main = Rect.rect(1, VER);
        Polygon rectKB = Rect.rect(KB, VER);
        Polygon rectKC = Rect.rect(KC, HOR);
        Polygon rectCD = Rect.rect(CD, VER, centreTransform(KC, DR));
        Polygon rectCE = Rect.rect(CE, HOR, centreTransform(KC, DR));

        return new PayloadSimple.Builder("rect_tile_04",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(rectKB, LEFT),
                                instruction(rectCE, DL)
                        )
                ))
                .withGridConf(Grid.Configs.RECT3.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        Polygon main = Rect.rect(1, VER);
        Polygon rectKB = Rect.rect(KB, VER);
        Polygon rectKC = Rect.rect(KC, HOR);
        Polygon rectCD = Rect.rect(CD, VER, centreTransform(KC, DR));
        Polygon rectCE = Rect.rect(CE, HOR, centreTransform(KC, DR));

        List<String> equations = Arrays.asList(
                "KB = KA / 5.0"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_04_design")
                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addEquations(equations)
                .addImportantPoints(asList(
                        Triple.of(main, RIGHT.getVertex(), "A"),
                        Triple.of(rectKB, RIGHT.getVertex(), "B"),
                        Triple.of(rectKC, DR.getVertex(), "C"),
                        Triple.of(rectCD, LEFT.getVertex(), "D"),
                        Triple.of(rectCE, DL.getVertex(), "E")
                ))
                .addLinesInstructions(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(rectKB, PERIMETER),
                        Pair.of(rectKC, PERIMETER),
                        Pair.of(rectCD, PERIMETER),
                        Pair.of(rectCE, PERIMETER)
//                        Pair.of(rectAB, PERIMETER)
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