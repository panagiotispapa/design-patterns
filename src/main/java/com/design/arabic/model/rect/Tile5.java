package com.design.arabic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.*;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.arabic.model.Rect.Vertex.*;
import static com.design.arabic.model.Rect.diagonals;
import static com.design.arabic.model.Rect.perimeter;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile5 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double AB = KB;
    private static final double KC = H;
    private static final double KD = KB / 2;
    private static final double KE = KD / H;
    private static final double CE = KC - KE;
    private static final double CF = CE * H;
    private static final double CG = 2 * CF;
    private static final double KI = KC * H;
    private static final double IC = KI;
    private static final double GI = IC - CG;
    private static final double DJ = KD * (GI / KI);


    public final static FinalPointTransition A = fpt(KA, UL);
    public final static FinalPointTransition A2 = fpt(KA, UR);
    public final static FinalPointTransition A3 = fpt(KA, DL);
    public final static FinalPointTransition A4 = fpt(KA, DR);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition B2 = fpt(KB, LEFT);
    public final static FinalPointTransition B3 = fpt(KB, RIGHT);
    public final static FinalPointTransition B4 = fpt(KB, DOWN);
    public final static FinalPointTransition C = fpt(KB, UL);
    public final static FinalPointTransition C2 = fpt(KB, UR);
    public final static FinalPointTransition C3 = fpt(KB, DR);
    public final static FinalPointTransition C4 = fpt(KB, DL);
    public final static FinalPointTransition D = fpt(KD, UP);
    public final static FinalPointTransition I = fpt(KI, UP);
    public final static FinalPointTransition D2 = fpt(KD, RIGHT);
    public final static FinalPointTransition D3 = fpt(KD, LEFT);
    public final static FinalPointTransition D4 = fpt(KD, DOWN);
    public final static FinalPointTransition I2 = D2.to(DJ, UP);
    public final static FinalPointTransition I3 = D2.to(DJ, DOWN);
    public final static FinalPointTransition I4 = D3.to(DJ, UP);
    public final static FinalPointTransition I5 = D3.to(DJ, DOWN);
    public final static FinalPointTransition E = fpt(KE, UL);
    public final static FinalPointTransition F = C.to(CF, RIGHT);
    public final static FinalPointTransition G = F.to(CF, RIGHT);
    public final static FinalPointTransition G2 = C2.to(2 * CF, LEFT);
    public final static FinalPointTransition G3 = C3.to(2 * CF, LEFT);
    public final static FinalPointTransition G4 = C4.to(2 * CF, RIGHT);
    public final static FinalPointTransition J = D.to(DJ, LEFT);
    public final static FinalPointTransition J2 = D.to(DJ, RIGHT);
    public final static FinalPointTransition J3 = D4.to(DJ, LEFT);
    public final static FinalPointTransition J4 = D4.to(DJ, RIGHT);
    public final static FinalPointTransition L = B2.to(KD, UP);
    public final static FinalPointTransition L2 = B2.to(KD, DOWN);
    public final static FinalPointTransition L3 = B3.to(KD, UP);
    public final static FinalPointTransition L4 = B3.to(KD, DOWN);
    public final static FinalPointTransition M = L.to(DJ, RIGHT);
    public final static FinalPointTransition M2 = L2.to(DJ, RIGHT);
    public final static FinalPointTransition M3 = L3.to(DJ, LEFT);
    public final static FinalPointTransition M4 = L4.to(DJ, LEFT);
    public final static FinalPointTransition N = B.to(KD, LEFT);
    public final static FinalPointTransition N2 = B.to(KD, RIGHT);
    public final static FinalPointTransition N3 = B4.to(KD, LEFT);
    public final static FinalPointTransition N4 = B4.to(KD, RIGHT);
    public final static FinalPointTransition P = N.to(DJ, DOWN);
    public final static FinalPointTransition P2 = N2.to(DJ, DOWN);
    public final static FinalPointTransition P3 = N3.to(DJ, UP);
    public final static FinalPointTransition P4 = N4.to(DJ, UP);


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

        Sequence<String> equations = sequence(
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

        return new DesignHelper(Rect.ALL_VERTEX_INDEXES, "rect_tile_05_design")
                .addEquations(equations)
                .addImportantVertexes(Tile5.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K),
                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        gray,
                        line(A3, G, G3, A2),
                        line(A4, G2, G4, A),
                        line(C2, B2, C3),
                        line(C, B3, C4),
                        line(C, B, C2),
                        line(C4, B4, C3)
                )
                .addFullPaths(
                        gray,
                        Rect.diagonalVertical(H).apply(fpt(KD, LEFT))
                )
                .addCirclesCentral(gray, H)
                .addSinglePathsLines(red, getAllSinglesPath())
                ;

    }

    private static Sequence<Line> getAllSinglesPath() {
        return join(
                getSinglesPath(),
                getSinglesPath().map(s -> s.mirror(Rect.mirrorVert))
        );
    }

    private static Sequence<Line> getSinglesPath() {
        return sequence(
                line(B2, I4, P, B, P2, I2, B3),
                line(A, M, J, J4, M4, A4)

        );
    }

}