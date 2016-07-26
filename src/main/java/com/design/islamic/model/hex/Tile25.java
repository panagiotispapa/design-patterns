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
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.diagonals;
import static com.design.islamic.model.Hex.perimeter;
import static java.util.Arrays.asList;

public class Tile25 {


    private static double KA = 1.0;
    private static double KC = KA / 2.0;
    private static double KB = KC / H;
    private static double AC = KC;
    private static double AB = KB;
    private static double AD = AB / 2.0;

    public final static FinalPointTransition A = fpt(pt(KA, UP));
    public final static FinalPointTransition A2 = fpt(pt(KA, UR_V));
    public final static FinalPointTransition B = fpt(pt(KB, UL_H));
    public final static FinalPointTransition B2 = fpt(pt(KB, UR_H));
    public final static FinalPointTransition C = fpt(pt(KC, UP));
    public final static FinalPointTransition D = A.append(pt(AD, DR_H));
    public final static FinalPointTransition E = A.append(pt(AD, DL_H));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_25",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .withSize(Payload.Size.SMALL)
                .withGridConf(Grid.Configs.HEX_HOR2.getConfiguration())
                .build();

    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        List<String> equations = asList(
                "KC = KA / 2.0",
                "KB = KC / H",
                "AE = EB"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_25_design")
                .addEquations(equations)
                .addImportantVertexes(Tile25.class)
                .addSinglePathsLines(gray,
                        perimeter(1.0, VER).apply(K),
                        perimeter(KB, HOR).apply(K),
                        diagonals(1.0, VER).apply(K),
                        diagonals(H, HOR).apply(K)
                )
                .addFullPaths(gray,
                        PointsPath.of(A, B2, A2)
                )
                .addSinglePathsLines(blue,
                        perimeter(AD, HOR).apply(A)
                )
                .addFullPaths(red, getFullPath())
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(A, B2, B, A),
                PointsPath.of(E, C, D, E)
        );
    }
}