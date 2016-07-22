package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.*;
import com.google.common.base.Supplier;

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
import static com.design.islamic.model.Hex.mirrorHor;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.diagonals;
import static com.design.islamic.model.Rect.perimeter;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Tile5 {

    private static final double KA = 1.0;
    private static final double KB = H;
    private static final double AB = KB;
    private static final double KC = H;
    private static final double KD = KB / 2.0;
    private static final double KE = KD / H;
    private static final double CE = KC - KE;
    private static final double CF = CE * H;
    private static final double CG = 2.0 * CF ;
    private static final double KI = KC * H;
    private static final double IC = KI;
    private static final double GI = IC - CG;
    private static final double DJ = KD * (GI / KI);


    public final static FinalPointTransition A = fpt(pt(KA, UL));
    public final static FinalPointTransition A2 = fpt(pt(KA, UR));
    public final static FinalPointTransition A3 = fpt(pt(KA, DL));
    public final static FinalPointTransition A4 = fpt(pt(KA, DR));
    public final static FinalPointTransition B = fpt(pt(KB, UP));
    public final static FinalPointTransition B2 = fpt(pt(KB, LEFT));
    public final static FinalPointTransition B3 = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition B4 = fpt(pt(KB, DOWN));
    public final static FinalPointTransition C = fpt(pt(KB, UL));
    public final static FinalPointTransition C2 = fpt(pt(KB, UR));
    public final static FinalPointTransition C3 = fpt(pt(KB, DR));
    public final static FinalPointTransition C4 = fpt(pt(KB, DL));
    public final static FinalPointTransition D = fpt(pt(KD, UP));
    public final static FinalPointTransition I = fpt(pt(KI, UP));
    public final static FinalPointTransition D2 = fpt(pt(KD, RIGHT));
    public final static FinalPointTransition D3 = fpt(pt(KD, LEFT));
    public final static FinalPointTransition D4 = fpt(pt(KD, DOWN));
    public final static FinalPointTransition I2 = D2.append(pt(DJ, UP));
    public final static FinalPointTransition I3 = D2.append(pt(DJ, DOWN));
    public final static FinalPointTransition I4 = D3.append(pt(DJ, UP));
    public final static FinalPointTransition I5 = D3.append(pt(DJ, DOWN));
    public final static FinalPointTransition E = fpt(pt(KE, UL));
    public final static FinalPointTransition F = C.append(pt(CF, RIGHT));
    public final static FinalPointTransition G = F.append(pt(CF, RIGHT));
    public final static FinalPointTransition G2 = C2.append(pt(2 * CF, LEFT));
    public final static FinalPointTransition G3 = C3.append(pt(2 * CF, LEFT));
    public final static FinalPointTransition G4 = C4.append(pt(2 * CF, RIGHT));
    public final static FinalPointTransition J = D.append(pt(DJ, LEFT));
    public final static FinalPointTransition J2 = D.append(pt(DJ, RIGHT));
    public final static FinalPointTransition J3 = D4.append(pt(DJ, LEFT));
    public final static FinalPointTransition J4 = D4.append(pt(DJ, RIGHT));
    public final static FinalPointTransition L = B2.append(pt(KD, UP));
    public final static FinalPointTransition L2 = B2.append(pt(KD, DOWN));
    public final static FinalPointTransition L3 = B3.append(pt(KD, UP));
    public final static FinalPointTransition L4 = B3.append(pt(KD, DOWN));
    public final static FinalPointTransition M = L.append(pt(DJ, RIGHT));
    public final static FinalPointTransition M2 = L2.append(pt(DJ, RIGHT));
    public final static FinalPointTransition M3 = L3.append(pt(DJ, LEFT));
    public final static FinalPointTransition M4 = L4.append(pt(DJ, LEFT));
    public final static FinalPointTransition N = B.append(pt(KD, LEFT));
    public final static FinalPointTransition N2 = B.append(pt(KD, RIGHT));
    public final static FinalPointTransition N3 = B4.append(pt(KD, LEFT));
    public final static FinalPointTransition N4 = B4.append(pt(KD, RIGHT));
    public final static FinalPointTransition P = N.append(pt(DJ, DOWN));
    public final static FinalPointTransition P2 = N2.append(pt(DJ, DOWN));
    public final static FinalPointTransition P3 = N3.append(pt(DJ, UP));
    public final static FinalPointTransition P4 = N4.append(pt(DJ, UP));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_05",
                Rect.ALL_VERTEX_INDEXES)
//                .withPathsFull(whiteBold, getFullPath())
                .withPathsSingleLines(whiteBold, getAllSinglesPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();

        List<String> equations = Arrays.asList(
                "KB = h = KC",
                "KI = KB / 2 = IC",
                "KE = KI / h",
                "CE = KC  - KE",
                "CF = CE * h",
                "CG = 2 * CF",
                "GI = IC - CG",
                "DJ / KD = GI / KI",
                "DJ = LM = NP"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_05_design")
                .addEquations(equations)
                .addImportantVertexes(Tile5.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        gray,
                        PointsPath.of(A3, G, G3, A2),
                        PointsPath.of(A4, G2, G4, A),
                        PointsPath.of(C2, B2, C3),
                        PointsPath.of(C, B3, C4),
                        PointsPath.of(C, B, C2),
                        PointsPath.of(C4, B4, C3)
                )
                .addFullPaths(
                        gray,
                        Rect.diagonalVertical(H).apply(fpt(pt(KD, LEFT)))
                )
                .addCirclesCentral(asList(
                        H
                ), gray)
                .addSinglePathsLines(red, getAllSinglesPath())
                ;

    }

    private static List<PointsPath> getAllSinglesPath() {
        return Stream.concat(
                getSinglesPath().stream(),
                getSinglesPath().stream().map(s -> s.mirror(Rect.mirrorVert))
        ).collect(toList());
    }

    private static List<PointsPath> getSinglesPath() {
        return asList(
                PointsPath.of(B2, I4, P, B, P2, I2, B3),
                PointsPath.of(A, M, J, J4, M4, A4)

        );
    }

}