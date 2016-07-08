package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
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
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;

public class Tile3 {


    private static double KA = 1.0;
    private static double KB = KA * H;
    private static double HEIGHT = KB;


    private static double ANGLE_1 = Math.atan(0.25 / HEIGHT);
    private static double ANGLE_2 = Math.PI / 6.0 + ANGLE_1;

    private static double IB = 0.5 * Math.tan(ANGLE_2);
    private static double KI = KB - IB;
    private static double AE = 0.5;
    private static double AL = AE * P;
    private static double EL = AE * H;
    private static double HM = EL + AL;
    private static double KM = 1 - HM;
    private static double KT = 0.5 * 0.5;
    private static double KL = 1 - AL;

    public final static FinalPointTransition I = fpt(pt(KI, RIGHT));
    public final static FinalPointTransition Q = fpt(pt(KI, DR_H));
    public final static FinalPointTransition T = fpt(pt(KT, DOWN));
    public final static FinalPointTransition B = fpt(pt(H, RIGHT));
    public final static FinalPointTransition A = fpt(pt(1, DR_V));
    public final static FinalPointTransition C = fpt(pt(1, DL_V));
    public final static FinalPointTransition F = fpt(pt(1, UL_V));
    public final static FinalPointTransition D = fpt(pt(H, LEFT));
    public final static FinalPointTransition M = fpt(pt(KM, DR_V));
    public final static FinalPointTransition L = fpt(pt(KL, DR_V));
    public final static FinalPointTransition E = fpt(pt(H, DR_H));

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_03",
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
                PointsPath.of(I, A, Q)
        );
    }

    @TileSupplier
    public static Payload getPayloadSimple2() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_03b",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        whiteBold,
                        getFullPathB()
                )
                .build();
    }

    private static List<PointsPath> getFullPathB() {
        return Arrays.asList(
                PointsPath.of(B, M, E)
        );
    }


    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        List<String> equations = asList(
                "DC/DB = AK/KB",
                "KT=1/4",
                "HDB=th",
                "tan(th)=HB/BD=0.25/h",
                "DKH=5*(PI/6)",
                "KHD=PI-DKH-th=(PI/6)-th",
                "IHB=(PI/6)+th",
                "IB=0.5*tan(IHB)"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_03_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPath())
                .addEquations(equations)
                .addImportantVertexes(
                        ImportantVertex.of("A", A),
                        ImportantVertex.of("B", B),
                        ImportantVertex.of("D", D),
                        ImportantVertex.of("C", C),
                        ImportantVertex.of("E", E),
                        ImportantVertex.of("F", F),
                        ImportantVertex.of("I", I),
                        ImportantVertex.of("T", T),
                        ImportantVertex.of("Q", Q)
                )
                .addSinglePathsLines(
                        gray,
                        Hex.perimeter(1.0, VER).apply(K),
                        Hex.diagonals(1.0, VER).apply(K),
                        Hex.diagonals(H, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        Hex.perimeter(0.5, VER).apply(B)
                )
                .addSinglePathsLines(
                        blue,
                        Hex.perimeter(KT, VER).apply(K),
                        Hex.perimeter(KI, HOR).apply(K)
                )
                .addFullPaths(gray,
                        PointsPath.of(C, B, F)
                );


    }

    @DesignSupplier
    public static DesignHelper getDesignHelper2() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();


        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_03b_design")
                .withGrid(Grid.Configs.HEX_VER.getConfiguration())
                .addFullPaths(red, getFullPathB())
                .addEquations(
                        "AE = 0.5",
                        "AL = AE * P",
                        "EL = AE * H",
                        "HM = EL + AL",
                        "KM = 1 - HM"
                )
                .addImportantVertexes(
                        ImportantVertex.of("A", A),
                        ImportantVertex.of("B", B),
                        ImportantVertex.of("D", D),
                        ImportantVertex.of("C", C),
                        ImportantVertex.of("E", E),
                        ImportantVertex.of("F", F),
                        ImportantVertex.of("L", L),
                        ImportantVertex.of("M", M)
                )
                .addSinglePathsLines(
                        gray,
                        Hex.perimeter(1.0, VER).apply(K),
                        Hex.diagonals(1.0, VER).apply(K),
                        Hex.diagonals(H, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        Hex.perimeter(KM, VER).apply(K)
                )
                .addSinglePathsLines(gray,
                        PointsPath.of(E, L)
                );

    }

}