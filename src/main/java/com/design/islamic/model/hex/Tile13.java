package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Payload;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

//p. 4
public class Tile13 {

    private static double RATIO_m = 1.0 / 3.0;
    private static double KB = RATIO_m;
    private static double KC = 2 * KB;

    public final static FinalPointTransition A = fpt(pt(1.0, RIGHT));
    public final static FinalPointTransition B = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition C = fpt(pt(2 * KB, RIGHT));
    public final static FinalPointTransition D = C.append(pt(KB, DL_H));
    public final static FinalPointTransition E = C.append(pt(KB, DR_H));
    public final static FinalPointTransition F = C.append(pt(KB, UR_H));
    public final static FinalPointTransition G = C.append(pt(KB, UL_H));

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_13",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        List<String> equations = Arrays.asList(
                "KB = (1/3) * KA"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_13_design")
                .addFullPaths(red, getFullPath())
                .addEquations(equations)
                .addImportantVertexes(Tile13.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        perimeter(KC, HOR).apply(K),
                        perimeter(KB, HOR).apply(K)
                )

                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(B, G, F, E, D, B)
        );
    }

}