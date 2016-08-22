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
import static com.design.arabic.model.Hex.*;
import static com.design.common.CircleInstruction.circleInstruction;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile57 {

    // p. 29

    private static double RATIO_w = 1.0;
    private static double RATIO_h = RATIO_w;

    // KB + BC = KA
    // H * BD + P * BD = KA
    // BD = KA / (H + P)

    public static double KA = 1.0;
    public static double BD = KA / (H + P);
    public static double KB = BD * P;
    public static double KC = KA;
    public static double KD = BD * H;
    public static double AF = BD * H;
    public static double KG = KB / 2;
    public static double KI = KG / H;
    public static double KJ = KB * H;
    public static double KL = 2 * KJ;
    public static double AM = KA / H;
    public static double KM = AM * P;
    public static double AD = BD * P;
    public static double DN = AD * P;
    public static double JQ = DN;
    public static double KQ = KJ + JQ;
    public static double QL = KL - KQ;
    public static double QR = QL;
    public static double CB = KC - KB;
    public static double CS = CB * H;
    public static double ST = CB * P;

    public final static FinalPointTransition A = fpt(1, LEFT);
    public final static FinalPointTransition A2 = A.to(1, UP);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition B2 = fpt(KB, UL_V);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = fpt(KD, LEFT);
    public final static FinalPointTransition E = C.to(KB, LEFT);
    public final static FinalPointTransition F = A.to(AF, UP);
    public final static FinalPointTransition G = fpt(KG, UP);
    public final static FinalPointTransition I = fpt(KI, UL_H);
    public final static FinalPointTransition I2 = fpt(KI, LEFT);
    public final static FinalPointTransition J = fpt(KJ, UL_H);
    public final static FinalPointTransition Q = J.to(JQ, UL_H);
    public final static FinalPointTransition Q2 = B.to(JQ, UL_H);
    public final static FinalPointTransition Q3 = Q2.to(JQ, UL_H);
    public final static FinalPointTransition L = fpt(KL, UL_H);
    public final static FinalPointTransition M = fpt(KM, UP);
    public final static FinalPointTransition M2 = A.to(KM, RIGHT);
    public final static FinalPointTransition M3 = C.to(KM, LEFT);
    public final static FinalPointTransition N = D.to(DN, UL_H);
    public final static FinalPointTransition R = Q.to(QR, DR_H);
    public final static FinalPointTransition S = C.to(CS, LEFT);
    public final static FinalPointTransition T = S.to(ST, DOWN);
    public final static FinalPointTransition I3 = C.to(KI, DOWN);
    public final static FinalPointTransition I4 = C.to(KI, DL_V);
    public final static FinalPointTransition B3 = C.to(KB, LEFT);
    public final static FinalPointTransition I5 = A2.to(KI, RIGHT);
    public final static FinalPointTransition I6 = A2.to(KI, DR_H);
    public final static FinalPointTransition B4 = A2.to(KB, DR_V);
    public final static FinalPointTransition I7 = A.to(KI, UR_V);
    public final static FinalPointTransition B6 = A.to(KB, UR_H);
    public final static FinalPointTransition I8 = A.to(KI, UP);
    public final static FinalPointTransition L2 = A.to(KL, UR_V);
    public final static FinalPointTransition L3 = A2.to(KL, DR_H);
    public final static FinalPointTransition Q4 = A2.to(KQ, DR_H);
    public final static FinalPointTransition R2 = Q4.to(QR, UL_H);
    public final static FinalPointTransition Q5 = C.to(KQ, DL_V);
    public final static FinalPointTransition R3 = Q5.to(QR, UR_V);
    public final static FinalPointTransition Q6 = A.to(KQ, UR_V);
    public final static FinalPointTransition R4 = Q6.to(QR, DL_V);

    @TileSupplier
    public static Payload getPayload() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_57",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.SMALL)
                .withPathsSingleLines(whiteBold, getSinglePathFull())
                .withGridConf(Grid.Configuration.customRect(2 * RATIO_w, 2 * RATIO_h))
//                .withGridConf(Grid.Configs.HEX_HOR2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_57_design")
//                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
//                .withGridRatio(KC)
//                .withGridSize(16)
                .addEquations(sequence(
                        "KB + BC = KA",
                        "H * BD + P * BD = KA",
                        "BD = KA / (H + P)",
                        "KA = 1.0",
                        "BD = KA / (H + P)",
                        "KB = BD * P",
                        "KC = KA",
                        "KD = BD * H",
                        "AF = BD * H",
                        "KG = KB / 2",
                        "KI = KG / H",
                        "KJ = KB * H",
                        "KL = 2 * KJ",
                        "AM = KA / H",
                        "KM = AM * P",
                        "AD = BD * P",
                        "DN = AD * P",
                        "JQ = DN",
                        "KQ = KJ + JQ",
                        "QL = KL - KQ",
                        "QR = QL",
                        "CB = KC - KB",
                        "CS = CB * H",
                        "ST = CB * P"
                ))
                .addImportantVertexes(Tile57.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        perimeter(1, HOR).apply(K),
//                        perimeter(H, HOR).apply(K),
                        diagonals(KA, VER).apply(K),
                        diagonals(KA, HOR).apply(K)
//                        diagonals(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        green,
                        diagonalVertical(KA).apply(fpt(KA, LEFT)),
                        diagonalVertical(KA).apply(fpt(KA, RIGHT)),
                        diagonalHorizontal(KA).apply(fpt(KA, UP)),
                        diagonalHorizontal(KA).apply(fpt(KA, DOWN))
                )
                .addSinglePathsLines(
                        blue,
                        line(B, E, F, D, B),
                        line(S, T, C),
                        line()
                )
                .addSinglePathsLines(
                        gray,
                        line(A, M),
                        line(A2, M2),
                        line()
                )
                .addCirclesCentral(
                        gray,
                        KL,
                        KJ
                )
                .addCircleWithRadius(
                        gray,
                        circleInstruction(C, CB),
                        circleInstruction(A2, CB),
                        circleInstruction(A, CB)

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
                line(I2, B2, I, B),
                line(B2, L, B),
                line(I5, B4, I6, F, L3 , B4),
                line(I3, Q3, I4, B3, T, Q3, R, R2, B6, L2, D, I7, B6, I8),
                line(B4, R3, R4, B2)
        );
    }


    public static void main(String[] args) {
        Export.export(getDesignHelper());
        Export.export(getPayload().toSvgPayload());
    }


}