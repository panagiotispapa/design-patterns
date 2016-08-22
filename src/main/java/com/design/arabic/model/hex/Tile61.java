package com.design.arabic.model.hex;

import com.design.Export;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile61 {

    // p. 36

    // AC + CB = AB
    // CD = CB
    // AC + CD = AB
    // AD * P + AD * H = AB
    // AD = AB / (P + H)

    public static double KA = 1;
    public static double KB = H;
    public static double AB = KA / 2;
    public static double AD = AD = AB / (P + H);
    public static double AC = AD * P;
    public static double CD = AD * H;
    public static double CB = AB - AC;
    public static double CF = CB / 4.5;
    public static double LE = CF;
    public static double EM = LE / P * H;
    public static double MB = EM + CD;
    public static double KM = KB - MB;
    public static double KN = KM / 2.0;
    public static double KQ = KN / H;

    public final static FinalPointTransition A = fpt(1, DL_H);
    public final static FinalPointTransition A2 = fpt(1, DR_H);
    public final static FinalPointTransition B = fpt(KB, DOWN);
    public final static FinalPointTransition C = A.to(AC, RIGHT);
    public final static FinalPointTransition C2 = A2.to(AC, LEFT);
    public final static FinalPointTransition D = C.to(CD, UP);
    public final static FinalPointTransition D2 = C2.to(CD, UP);
    public final static FinalPointTransition E = B.to(CD, UP);

    public final static FinalPointTransition F = C.to(CF, RIGHT);
    public final static FinalPointTransition F2 = C.to(2 * CF, RIGHT);
    public final static FinalPointTransition F3 = C.to(3 * CF, RIGHT);
    public final static FinalPointTransition F4 = C.to(4 * CF, RIGHT);
    public final static FinalPointTransition F5 = D.to(CF, RIGHT);
    public final static FinalPointTransition F6 = D.to(2 * CF, RIGHT);
    public final static FinalPointTransition F7 = D.to(3 * CF, RIGHT);
    public final static FinalPointTransition F8 = D.to(4 * CF, RIGHT);
    public final static FinalPointTransition G = D.to(CF, DOWN);
    public final static FinalPointTransition G2 = D.to(2 * CF, DOWN);
    public final static FinalPointTransition G3 = D.to(3 * CF, DOWN);
    public final static FinalPointTransition G4 = D.to(4 * CF, DOWN);
    public final static FinalPointTransition G5 = E.to(CF, DOWN);
    public final static FinalPointTransition G6 = E.to(2 * CF, DOWN);
    public final static FinalPointTransition G7 = E.to(3 * CF, DOWN);
    public final static FinalPointTransition G8 = E.to(4 * CF, DOWN);
    public final static FinalPointTransition I = G4.to(4 * CF, RIGHT);
    public final static FinalPointTransition I2 = G.to(4 * CF, RIGHT);
    public final static FinalPointTransition I3 = G.to(3 * CF, RIGHT);
    public final static FinalPointTransition I4 = G2.to(3 * CF, RIGHT);
    public final static FinalPointTransition I5 = G2.to(CF, RIGHT);
    public final static FinalPointTransition I6 = G3.to(CF, RIGHT);
    public final static FinalPointTransition I7 = G3.to(3 * CF / H * P, LEFT);
    public final static FinalPointTransition J = G4.to(4 * CF / H * P, LEFT);
    public final static FinalPointTransition J1 = G4.to(3 * CF, RIGHT);
    public final static FinalPointTransition J2 = G3.to(3 * CF, RIGHT);
    public final static FinalPointTransition J3 = G3.to(2 * CF, RIGHT);
    public final static FinalPointTransition J4 = G.to(2 * CF, RIGHT);
    public final static FinalPointTransition J5 = G.to(CF, RIGHT);
    public final static FinalPointTransition J6 = D.to(CF, RIGHT);
    public final static FinalPointTransition L = D.to(3.5 * CF, RIGHT);
    public final static FinalPointTransition L2 = D2.to(3.5 * CF, LEFT);
    public final static FinalPointTransition M = fpt(KM, DOWN);
    public final static FinalPointTransition M2 = fpt(KM, DR_V);
    public final static FinalPointTransition M3 = fpt(KM, DR_H);
    public final static FinalPointTransition M4 = fpt(KM, DL_H);
    public final static FinalPointTransition N = fpt(KN, DOWN);
    public final static FinalPointTransition Q = fpt(KQ, DR_H);
    public final static FinalPointTransition R = fpt(KQ, DOWN);
    public final static FinalPointTransition S1 = D2.to(CF, LEFT);
    public final static FinalPointTransition S2 = S1.to(CF, DOWN);
    public final static FinalPointTransition S3 = S2.to(CF, LEFT);
    public final static FinalPointTransition S4 = S3.to(2 * CF, DOWN);
    public final static FinalPointTransition S5 = S4.to(CF, LEFT);
    public final static FinalPointTransition S6 = S5.to(CF, DOWN);
    public final static FinalPointTransition S7 = S6.to(3 * CF + 4 * CF / H * P, RIGHT);
    public final static FinalPointTransition T1 = G8.to(0.5 * CF, RIGHT);
    public final static FinalPointTransition T2 = T1.to(3 * CF, UP);
    public final static FinalPointTransition T3 = T2.to(CF, RIGHT);
    public final static FinalPointTransition T4 = T3.to(CF, DOWN);
    public final static FinalPointTransition T5 = T4.to(2 * CF, RIGHT);
    public final static FinalPointTransition T6 = T5.to(CF, DOWN);
    public final static FinalPointTransition T7 = T6.to(CF + 3 * CF / H * P, RIGHT);

    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_61",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.SMALL)
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

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_61_design")
                .addEquations(sequence(
                        "KA = 1",
                        "KB = H"
                ))
                .addImportantVertexes(Tile61.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(KA, HOR).apply(K),
                        diagonals(KB, VER).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        line(D, E, B, C, D, A, C),
                        line(E, D2, C2)
                )
                .addSinglePathsLines(
                        green,
                        line(F, F5),
                        line(F2, F6),
                        line(F3, F7),
                        line(F4, F8),
                        line(G, G5),
                        line(G2, G6),
                        line(G3, G7),
                        line(G4, G8)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(12);
    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(B, I, I2, I3, I4, I5, I6, I7),
                line(J, J1, J2, J3, J4, J5, J6, L, M, Q, M2),
                line(M4, R, M3),
                line(M, L2, S1, S2, S3, S4, S5, S6, S7),
                line(B, T1, T2, T3, T4, T5, T6, T7),
                line()
        );
    }

    public static void main(String[] args) {
        Export.export(getDesignHelper());
        Export.export(getPayload().toSvgPayload());
    }

}