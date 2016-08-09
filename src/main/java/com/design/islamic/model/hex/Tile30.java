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
import static java.lang.Math.PI;
import static java.lang.Math.tan;

public class Tile30 {

    public static double ANGLE_1 = PI - PI / 4.0 - PI / 6.0;
    public static double ANGLE_2 = PI - ANGLE_1;
    public static double ANGLE_3 = PI - ANGLE_2 - PI / 3.0;
    public static double ANGLE = PI / 3.0 - PI / 4.0;


    public static double KB = H;
    public static double KC = KB * H;
    public static double AC = 1 - KC;
    public static double CD = AC * H;
    public static double AD = AC * RatioHelper.P6.P;
    public static double DE = CD / tan(ANGLE_3);
    public static double AE = DE + AD;
    public static double BE = 0.5 - AE;
    public static double BF = BE;
    public static double KF = KB - BF;
    public static double KG = KF * H;
    public static double GP = 0.5 * KG * tan(ANGLE);
    public static double KP = KG - GP;

    public final static FinalPointTransition A = fpt(pt(1.0, DR_V));
    public final static FinalPointTransition D = A.append(pt(AD, UP));
    public final static FinalPointTransition C = fpt(pt(KC, DR_V));
    public final static FinalPointTransition C2 = fpt(pt(KC, UR_V));
    public final static FinalPointTransition G = fpt(pt(KG, DR_V));
    public final static FinalPointTransition P = fpt(pt(KP, DR_V));
    public final static FinalPointTransition P2 = fpt(pt(KP, UR_V));
    public final static FinalPointTransition B = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition F = fpt(pt(KF, RIGHT));
    public final static FinalPointTransition E = B.append(pt(BF, DOWN));
    public final static FinalPointTransition E2 = B.append(pt(BF, UP));

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_30",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        Sequence<String> equations = sequence(
                "KPF = ANGLE_1 = PI - PI / 4.0 - PI / 6.0",
                "FHA = ECA =  ANGLE_2 = PI - ANGLE_1",
                "CEA = ANGLE_3 = PI - ANGLE_2 - PI / 3.0",
                "ANGLE = PI / 3.0 - PI / 4.0",
                "KB = h * KA",
                "KC = h * KB",
                "AC = 1 - KC",
                "CD = h * AC",
                "AD = 0.5 * AC",
                "DE = CD / tan(ANGLE_3)",
                "AE = DE + AD",
                "BE = 0.5 - AE",
                "BF = BE",
                "KF = KB - BF",
                "KG = h * KF",
                "GP = 0.5 * KG * tan(ANGLE)",
                "KP = KG - GP"
//                "DC=DE=KB",
//                "DB=1-KB"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_30_design")
                .addEquations(equations)
                .addImportantVertexes(Tile30.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        diagonals(1.0, VER).apply(K),
                        perimeter(H, HOR).apply(K),
                        diagonals(H, HOR).apply(K),
                        perimeter(KF, HOR).apply(K),
                        perimeter(KC, VER).apply(K),
                        perimeter(KG, VER).apply(K),
                        perimeter(KP, VER).apply(K),
                        perimeter(CD, HOR).apply(C),
                        perimeter(AD, VER).apply(A),
                        perimeter(AE, VER).apply(A)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15)
                ;

    }

    private static Sequence<PointsPath> getFullPath() {
        return sequence(
                PointsPath.of(C, E, P2),
                PointsPath.of(P, E2, C2)
        );
    }

}