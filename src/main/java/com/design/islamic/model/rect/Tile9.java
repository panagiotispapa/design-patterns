package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.*;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.islamic.model.Rect.ALL_VERTEX_INDEXES;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.diagonals;
import static com.design.islamic.model.Rect.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile9 {

    private static final double KA = 1.0;
    private static final double KB = H;
    private static final double KC = KA / 4.0;
    private static final double CE = 2 * KC * H;

    public final static FinalPointTransition A = fpt(pt(KA, UR));
    public final static FinalPointTransition B = fpt(pt(KB, UP));
    public final static FinalPointTransition B2 = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KC, UR));
    public final static FinalPointTransition C2 = fpt(pt(KC, UL));
    public final static FinalPointTransition C3 = fpt(pt(KC, DL));
    public final static FinalPointTransition C4 = fpt(pt(KC, DR));
    public final static FinalPointTransition D = A.append(pt(KC, DL));
    public final static FinalPointTransition E = C.append(pt(CE, RIGHT));
    public final static FinalPointTransition F = C.append(pt(CE, UP));
    public final static FinalPointTransition F2 = C2.append(pt(CE, UP));
    public final static FinalPointTransition G = B.append(pt(KC * H, RIGHT));
    public final static FinalPointTransition G2 = B.append(pt(KC * H, LEFT));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_09",
                ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        return new DesignHelper(ALL_VERTEX_INDEXES, "rect_tile_09_design")
                .addEquations(sequence(
                        "KB = h",
                        "KC = AD = KA / 4"
                ))
                .addImportantVertexes(Tile9.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        diagonals(CE, VER).apply(C),
                        diagonals(CE, VER).apply(D)
                )
                .addCirclesCentral(gray, H)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(K, C, E, D, A),
                PointsPath.of(C, F, D),
                PointsPath.of(F, G),
                PointsPath.of(F2, G2),
                PointsPath.of(F2, F)
        );
    }

}