package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.diagonals;
import static com.design.islamic.model.Rect.perimeter;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Tile12 {

    private static final double KA = 1.0;
    private static final double KB = H;
    private static final double KC = KB / 3.0;


    public final static FinalPointTransition A = fpt(pt(KA, UR));
    public final static FinalPointTransition B = fpt(pt(KB, UP));
    public final static FinalPointTransition C = fpt(pt(KC, UP));
    public final static FinalPointTransition D = fpt(pt(2 * KC, UP));
    public final static FinalPointTransition E = C.append(pt(KC, LEFT));
    public final static FinalPointTransition F = C.append(pt(KC, RIGHT));
    public final static FinalPointTransition G = D.append(pt(KC, RIGHT));
    public final static FinalPointTransition I = A.append(pt(KC, DOWN));
    public final static FinalPointTransition J = B.append(pt(KC, RIGHT));
    public final static FinalPointTransition L = I.append(pt(KC, LEFT));
    public final static FinalPointTransition P = fpt(pt(2 * KC, RIGHT));
    public final static FinalPointTransition Q = P.append(pt(KC, UP));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_12",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsSingleLines(whiteBold, getAllSinglesPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        List<String> equations = Arrays.asList(
                "KB = h",
                "KC = KB / 3"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_12_design")
                .addEquations(equations)
                .addImportantVertexes(Tile12.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(KC / H, HOR).apply(K),
                        perimeter(2.0 * KC / H, HOR).apply(K)
                )
                .addCirclesCentral(asList(
                        H
                ), gray)
                .addSinglePathsLines(red, getAllSinglesPath())
                ;

    }

    private static List<PointsPath> getAllSinglesPath() {
        return Stream.concat(
                getSinglesPath2().stream(),
                getSinglesPath2().stream().map(s -> s.mirror(Rect.mirrorHor))
        ).collect(toList());
    }

    private static List<PointsPath> getSinglesPath2() {
        return Stream.concat(
                getSinglesPath().stream(),
                getSinglesPath().stream().map(s -> s.mirror(Rect.mirrorVert))
        ).collect(toList());
    }


    private static List<PointsPath> getSinglesPath() {
        return asList(
//                PointsPath.of(J, G, C),
                PointsPath.of(C, F, P, Q, I, L, J, G, C)
        );
    }

}