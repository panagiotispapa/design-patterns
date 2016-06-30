package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Path;
import com.design.common.model.Style;
import com.design.islamic.model.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.islamic.model.Rect.Corner.*;
import static com.design.islamic.model.Rect.*;
import static java.util.Arrays.asList;

public class Tile2 {

    private static final double KA = 1.0;
    private static final double KC = H;
    private static final double KB = H;
    private static final double KD = H * KB;
    private static final double BD = KD;
    private static final double DC = KD + KC;
    private static final double KE = BD * (KC / DC);
    private static final double FG = KC / H;
    private static final double KF = KE;
    private static final double KG = FG + KF;


    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Rect.rect(1, VER);
        Polygon rectGF = Rect.rect(FG, HOR, centreTransform(KG, RIGHT));
        Polygon rectGF_ver = Rect.rect(FG, VER, centreTransform(KG, RIGHT));

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("rect_tile_02",
                Hex.ALL_VERTEX_INDEXES)
                .withPathsFull(
                        VertexPaths.of(
                                VertexPath.of(
                                        instruction(rectGF, UL),
                                        instruction(rectGF_ver, LEFT),
                                        instruction(rectGF, DL)
                                ))
                        , whiteBold
                )
                .withGridConf(Grid.Configs.RECT3.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();

        Polygon main = Rect.rect(KA, HOR);
        Polygon rectKC = Rect.rect(H, VER);
        Polygon rectKB = Rect.rect(H, HOR);
        Polygon rectKD = Rect.rect(KD, VER);
        Polygon rectKE = Rect.rect(KE, VER);
        Polygon rectGF = Rect.rect(FG, HOR, centreTransform(KG, RIGHT));
        Polygon rectGF_ver = Rect.rect(FG, VER, centreTransform(KG, RIGHT));

        List<String> equations = Arrays.asList(
                "KC = h",
                "KB = h",
                "KD = h * KB",
                "BD = KD",
                "DC = KD + KC",
                "KE = BD * (KC / DC)",
                "FG = KC / h",
                "KF = KE",
                "KG = FG + KF"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_02_design")
//                .addMixedLinesInstructionsList(getPayloadSimple().getLines(), red)
                .addFullPaths(() -> getPayloadSimple().getPathsFull(), red)
                .addEquations(equations)
                .addImportantVertexes(asList(
                        ImportantVertex.of(main, DR.getVertex(), "A"),
                        ImportantVertex.of(rectKB, UR.getVertex(), "B"),
                        ImportantVertex.of(rectKC, LEFT.getVertex(), "C"),
                        ImportantVertex.of(rectKD, RIGHT.getVertex(), "D"),
                        ImportantVertex.of(rectKE, UP.getVertex(), "E"),
                        ImportantVertex.of(rectKE, RIGHT.getVertex(), "F"),
                        ImportantVertex.of(Rect.rect(KG, VER), RIGHT.getVertex(), "G")
                ))
                .addSinglePaths(asList(
                        Pair.of(main, PERIMETER),
                        Pair.of(main, DIAGONALS),
                        Pair.of(rectKB, PERIMETER),
                        Pair.of(rectGF, PERIMETER),
                        Pair.of(rectGF_ver, PERIMETER),
                        Pair.of(rectKC, DIAGONALS)

                ), gray)
                .addCirclesCentral(asList(
                        H
                ), gray)
                .addFullPaths(() -> asList(
                        new Path.Builder()
                                .startWith(instruction(rectKC, LEFT))
                                .lineTo(instruction(rectKB, UR))
                                .build(),
                        new Path.Builder()
                                .startWith(instruction(rectKC, RIGHT))
                                .lineTo(instruction(rectKB, UL))
                                .build()

                ), gray)

//
//
//                .addSinglePath(
//                        new Path.Builder()
//                                .startWith(main, DL.getVertex())
//                                .arcLargerTo(main, UR.getVertex())
//                                .lineTo(main, DR.getVertex())
//                                .withStyle(new Style.Builder(Color.BLUE, 2).build())
//                                .build()
//                )


                ;

    }

}