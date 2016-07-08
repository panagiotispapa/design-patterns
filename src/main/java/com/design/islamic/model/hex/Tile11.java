package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
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
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;

//p. 2
public class Tile11 {

    private static double RATIO_m = 1.0 / 4.0;
    private static double RATIO_n = RATIO_m / H;
    private static double KA = RATIO_n;
    private static double KE = 2.0 * KA;
    private static double KD = 3.0 * KA;
    private static double KF = KD / H;
    private static double KI = 3.0 * KA;
    private static double KJ = 4.0 * KA;

    public final static FinalPointTransition A = fpt(pt(KA, RIGHT));
    public final static FinalPointTransition E = fpt(pt(KE, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KE, DR_H));
    public final static FinalPointTransition B = fpt(pt(KD, DR_H));
    public final static FinalPointTransition D = fpt(pt(KD, RIGHT));
    public final static FinalPointTransition E1 = D.append(pt(KA, DL_H));
    public final static FinalPointTransition E2 = D.append(pt(KA, DR_H));
    public final static FinalPointTransition C1 = B.append(pt(KA, RIGHT));
    public final static FinalPointTransition C2 = B.append(pt(KA, UR_H));
    public final static FinalPointTransition F = fpt(pt(KF, DR_V));
    public final static FinalPointTransition I = fpt(pt(KI, RIGHT));
    public final static FinalPointTransition J = fpt(pt(KJ, RIGHT));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_11",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }


    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(C1, C2, C, E, E1, E2)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        List<String> equations = Arrays.asList(
                "KA = (1/4) / h",
                "BC DE = KA"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_11_design")
                .addEquations(equations)
                .addImportantVertexes(Tile11.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(KI, HOR).apply(K),
                        perimeter(KJ, HOR).apply(K),
                        perimeter(KF, VER).apply(K),
                        diagonals(KF, VER).apply(K),
                        perimeter(KA, HOR).apply(K),
                        perimeter(KD, HOR).apply(K),
                        diagonals(KI, HOR).apply(K)
                )
                .addFullPaths(red, getFullPath())
                ;

    }

}