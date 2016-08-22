package com.design.arabic.model.hex;

import com.design.Export;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.design.common.*;
import com.design.common.model.Style;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile60 {

    // p. 30

    private static double RATIO_w = 1.0;
    private static double RATIO_h = RATIO_w;

    public static double KA = 1.0;
    public static double KB = KA / 2.0;
    public static double AB = KB;
    public static double AC = AB / P;
    public static double BC = AC * H;
    public static double BD = KA - BC;
    public static double DE = BD / H;
    public static double BE = DE * P;
    public static double DF = BD / P;
    public static double BF = DF * H;
    public static double KE = KB - BE;
    public static double KG = KE / 3;
    public static double AJ = AB / H;
    public static double BJ = AJ * P;
    public static double BM = BJ + BE;
    public static double MJ = BM - BJ;
    public static double JQ = MJ / H;
    public static double MQ = JQ * P;
    public static double BS = KA / 2 - MQ;
    public static double ST = MQ;
    public static double KR = 2 * KG * H;



    public final static FinalPointTransition A = fpt(KA, LEFT);
    public final static FinalPointTransition A2 = A.to(KA, UP);
    public final static FinalPointTransition A3 = fpt(KA, UP);
    public final static FinalPointTransition B = fpt(KB, LEFT);
    public final static FinalPointTransition B2 = fpt(KB, UP);
    public final static FinalPointTransition B3 = A.to(KB, UP);
    public final static FinalPointTransition B4 = A2.to(KB, RIGHT);
    public final static FinalPointTransition C = B.to(BC, UP);
    public final static FinalPointTransition C2 = B3.to(BC, RIGHT);
    public final static FinalPointTransition C3 = B2.to(BC, LEFT);
    public final static FinalPointTransition D = B4.to(BC, DOWN);
    public final static FinalPointTransition E = B.to(BE, RIGHT);
    public final static FinalPointTransition F = B.to(BF, RIGHT);
    public final static FinalPointTransition G = fpt(KG, LEFT);
    public final static FinalPointTransition E2 = E.to(KG, UR_H);
    public final static FinalPointTransition E3 = E2.to(2 * KG, RIGHT);
    //    public final static FinalPointTransition E4 = E3.to(KG, UR_H);
    public final static FinalPointTransition R = fpt(KR, UP);
    public final static FinalPointTransition R2 = A3.to(KR, LEFT);
    public final static FinalPointTransition R3 = A3.to(KR, DL_H);
    public final static FinalPointTransition R4 = A2.to(KR, DOWN);
    public final static FinalPointTransition R5 = A2.to(KR, DR_V);
    public final static FinalPointTransition R6 = A.to(KR, RIGHT);
    public final static FinalPointTransition R7 = A.to(KR, UR_H);
    public final static FinalPointTransition E5 = G.to(2 * KG, UL_H);
    public final static FinalPointTransition E6 = R.to(KG, UL_H);
    public final static FinalPointTransition E7 = E6.to(KG, LEFT);
    public final static FinalPointTransition E4 = A3.to(KG, DL_V);
    public final static FinalPointTransition E8 = A3.to(KG, DOWN);
    public final static FinalPointTransition J = B.to(BJ, UP);
    public final static FinalPointTransition J2 = B4.to(BJ, DOWN);
    public final static FinalPointTransition J3 = B3.to(BJ, RIGHT);
    public final static FinalPointTransition M = B.to(BM, UP);
    public final static FinalPointTransition N = M.to(BD, LEFT);
    public final static FinalPointTransition M2 = B4.to(BM, DOWN);
    public final static FinalPointTransition N2 = M2.to(BD, RIGHT);
    public final static FinalPointTransition M3 = B3.to(BM, RIGHT);
    public final static FinalPointTransition M4 = B2.to(BM, LEFT);
    public final static FinalPointTransition N3 = M3.to(BD, UP);
    public final static FinalPointTransition N4 = M.to(BD, RIGHT);
    public final static FinalPointTransition Q = M.to(MQ, RIGHT);
    public final static FinalPointTransition S = B.to(BS, UP);
    public final static FinalPointTransition S2 = B4.to(BS, DOWN);
    public final static FinalPointTransition S3 = M3.to(MQ, DOWN);
    public final static FinalPointTransition S4 = M4.to(MQ, UP);
    public final static FinalPointTransition S5 = M2.to(MQ, LEFT);
    public final static FinalPointTransition T = S.to(ST, RIGHT);
    public final static FinalPointTransition T2 = S2.to(ST, RIGHT);
    public final static FinalPointTransition T3 = S.to(ST, LEFT);
    public final static FinalPointTransition T4 = S2.to(ST, LEFT);
    public final static FinalPointTransition X = A3.to(3 * KG, DOWN);
    public final static FinalPointTransition X2 = A2.to(3 * KG, RIGHT);
    public final static FinalPointTransition X3 = A.to(3 * KG, UP);
    public final static FinalPointTransition E9 = R3.to(KG, DOWN);
    public final static FinalPointTransition E10 = R3.to(KG, DL_V);
    public final static FinalPointTransition E11 = A2.to(KG, DR_H);
    public final static FinalPointTransition E12 = A2.to(KG, RIGHT);
    public final static FinalPointTransition E13 = R5.to(KG, RIGHT);
    public final static FinalPointTransition E14 = R5.to(KG, DR_H);
    public final static FinalPointTransition E15 = R4.to(KG, DR_H);
    public final static FinalPointTransition E16 = R2.to(KG, DL_V);
    public final static FinalPointTransition E17 = A.to(KG, UR_V);
    public final static FinalPointTransition E18 = A.to(KG, UP);
    public final static FinalPointTransition E19 = R6.to(KG, UR_V);
    public final static FinalPointTransition E20 = R7.to(KG, UR_V);
    public final static FinalPointTransition E21 = R7.to(KG, UP);

    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_60",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.MEDIUM)
                .withPathsSingleLines(whiteBold, getSinglePathFull())
                .withGridConf(Grid.Configuration.customRect(2 * RATIO_w, 2 * RATIO_h))
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_60_design")
                .addEquations(sequence(
                        "KA = 1.0",
                        "KB = KA / 2.0",
                        "AB = KB",
                        "AC = AB / P",
                        "BC = AC * H",
                        "BD = KA - BC",
                        "DE = BD / H",
                        "BE = DE * P",
                        "DF = BD / P",
                        "BF = DF * H",
                        "KE = KB - BE",
                        "KG = KE / 3",
                        "AJ = AB / H",
                        "BJ = AJ * P",
                        "BM = BJ + BE",
                        "MJ = BM - BJ",
                        "JQ = MJ / H",
                        "MQ = JQ * P",
                        "BS = KA / 2 - MQ",
                        "ST = MQ",
                        "KR = 2 * KG * H"
                ))
                .addImportantVertexes(Tile60.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        perimeter(1, HOR).apply(K),
                        diagonals(KA, VER).apply(K),
                        diagonals(KA, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        diagonalVertical(KA).apply(fpt(KA, LEFT)),
                        diagonalVertical(KA).apply(fpt(KA, RIGHT)),
                        diagonalHorizontal(KA).apply(fpt(KA, UP)),
                        diagonalHorizontal(KA).apply(fpt(KA, DOWN))
                )
                .addCircleWithRadius(
                        gray,
                        CircleInstruction.circleInstruction(E, KG)
                )
                .addCirclesCentral(gray,
                        KE / 3
                )
                .addSinglePathsLines(
                        green,
                        line(B2, B3),
                        line(B, B4),
                        line(A, B, C),
                        line(A2, C2, B3),
                        line(A3, C3, B2),
                        line(A2, D, B4),
                        line(E, D, B),
                        line(D, F),
                        line(A, J, M, N),
                        line(A3, J2, M2, N2),
                        line(A, J3, M3, N3),
                        line(K, J, N4, M),
                        line(J, Q),
                        line()
                )

                .addSinglePathsLines(red, getSinglePathFull())
                .withFontSize(15);

    }

    private static Sequence<Line> getSinglePathFull() {
        Sequence<Line> leftSide = join(
                getSinglePath(),
                getSinglePath().map(s -> s.mirror(Hex.mirrorVert))
        );
        return join(
                leftSide,
                leftSide.map(s -> s.mirror(Hex.mirrorHor))
        );
    }

    private static Sequence<Line> getSinglePath() {
        return sequence(
                line(N4, D, E, E2, E3, R),
                line(G, E5, C2),

                line(C2, X, E9, R3, E10, C, X2, E13, R5, E14, C3),
                line(R2, E4, R3, E8),
                line(E12, R5, E11, R4, E15, N3, C),
                line(N3, S3, T3, T2, S4, N4, E6, R),
                line(N, Q, T, T4, S5, N2, C2),
                line(N2, E16, R2),
                line(N, E19, R6, E17, R7, E18),
                line(N, C3, X3, E21, R7, E20, D)
        );
    }


    public static void main(String[] args) {
        Export.export(getDesignHelper());
        Export.export(getPayload().toSvgPayload());
    }


}