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
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

//p.
public class Tile14 {

    private static double RATIO_m = 1.0 / 4.0;
    private static double KB = RATIO_m;
    private static double AB = 3 * KB;

    public final static FinalPointTransition A = fpt(pt(1.0, RIGHT));
    public final static FinalPointTransition B = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition C = A.append(pt(KB, DL_H));
    public final static FinalPointTransition D = A.append(pt(KB, UL_H));
    public final static FinalPointTransition E = B.append(pt(KB, UR_H));
    public final static FinalPointTransition F = B.append(pt(KB, DR_H));


    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_14",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_14_design")
                .addFullPaths(red, getFullPath())
                .addEquations(
                        "KB = (1/4) * KA"
                )
                .addImportantVertexes(Tile14.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        perimeter(KB, HOR).apply(K),
                        perimeter(2 * KB, HOR).apply(K),
                        perimeter(3 * KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(AB, HOR).apply(A)
                )
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(D, E, B, F, C)
        );
    }


}