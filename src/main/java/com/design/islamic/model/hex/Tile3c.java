package com.design.islamic.model.hex;

import com.design.common.*;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Payload;
import com.design.islamic.model.TileSupplier;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile3c {


    private static final double KB = H;
    private static final double KC = KB * H;
    private static final double CA = 1 - KC;
    private static final double CB = 0.5 * KB;
    private static final double CD = CB;
    private static final double KD = 1 - CA - CD;

    public final static FinalPointTransition A = fpt(pt(1.0, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KC, RIGHT));
    public final static FinalPointTransition D = fpt(pt(KD, RIGHT));
    public final static FinalPointTransition B = fpt(pt(H, DR_V));
    public final static FinalPointTransition E = fpt(pt(H, UR_V));

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_03c",
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_03c_design")
                .addFullPaths(red, getFullPath())
                .addEquations(sequence(
                        "KB = h",
                        "KC = h * KB",
                        "BC = 0.5 * KB",
                        "DC = BC",
                        "CA = KA - KC",
                        "KD = KA - KC - DC"
                ))
                .addImportantVertexes(Tile3c.class)
//                .addImportantVertexes(
//                        ImportantVertex.circle(main, RIGHT.getVertex(), "A"),
//                        ImportantVertex.circle(main_Ver, DR_V.getVertex(), "B"),
//                        ImportantVertex.circle(hexKC, RIGHT.getVertex(), "C"),
//                        ImportantVertex.circle(hexKD, RIGHT.getVertex(), "D")
//                )
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        perimeter(KB, VER).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        perimeter(KC, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        perimeter(KD, HOR).apply(K)
                )
                ;

    }

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(B, D, E)
        );
    }
}