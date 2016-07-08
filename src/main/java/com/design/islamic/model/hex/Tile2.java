package com.design.islamic.model.hex;

import com.design.common.*;
import com.design.common.DesignHelper.ImportantVertex;
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
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Vertex.*;

public class Tile2 {

    private static double RATIO_KB = H / (H + 0.5);
    private static double RATIO_BD = Mappings.<Double>chain(i -> 1 - i, i -> i * H).apply(RATIO_KB);

    private final static FinalPointTransition G = fpt(pt(RATIO_KB, DOWN));
    private final static FinalPointTransition B = fpt(pt(RATIO_KB, DR_V));
    private final static FinalPointTransition D = B.append(pt(RATIO_BD, RIGHT));
    private final static FinalPointTransition F = B.append(pt(RATIO_BD, DR_H));

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();


        return new Payload.Builder("hex_tile_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        whiteBold,
                        getFullPath()
                )
                .build();
    }

    private static List<PointsPath> getFullPath() {
        return Arrays.asList(
                PointsPath.of(D, B, F),
                PointsPath.of(B, G)

        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_02_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath())
                .addEquations(
                        "KB=h/(h+0.5)",
                        "BD=h*(1-KB)"
                )
                .addImportantVertexes(
                        ImportantVertex.of("A", pt(1, DR_V)),
                        ImportantVertex.of("E", pt(H, RIGHT)),
                        ImportantVertex.of("B", B),
                        ImportantVertex.of("G", pt(RATIO_KB, DOWN)),
                        ImportantVertex.of("C", pt(RATIO_KB * H, RIGHT)),
                        ImportantVertex.of("D", B.append(pt(RATIO_BD, RIGHT))),
                        ImportantVertex.of("F", B.append(pt(RATIO_BD, DR_H)))
                )
                .addSinglePathsLines(
                        gray,
                        Hex.perimeter(1.0, VER).apply(K),
                        Hex.diagonals(1.0, VER).apply(K),
                        Hex.diagonals(H, HOR).apply(K),
                        Hex.diagonals(RATIO_BD, HOR).apply(B)
                )
                .addSinglePathsLines(
                        green,
                        Hex.perimeter(RATIO_KB, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        Hex.perimeter(RATIO_BD, HOR).apply(B)
                );

    }

}