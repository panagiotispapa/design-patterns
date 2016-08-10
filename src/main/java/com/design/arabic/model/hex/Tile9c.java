package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Line;
import com.design.common.RatioHelper.P4;
import com.design.common.RatioHelper.P6;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.CircleInstruction.circleInstruction;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile9c extends Tile9b {

    protected static final Double A1B = 0.5;
    protected static final Double A1J4 = 0.5 - pHalf;
    protected static final Double J4J5 = BC * (A1J4 / A1B);
    protected static final Double J1J5 = KB - J4J5;
    protected static final Double J1J6 = KB - J4J5 - 2 * (BC - J4J5);
    protected static final Double G1C1 = 0.5 * l;
    protected static final Double EC1 = pHalf;
    protected static final Double C1J3 = EC1;
    protected static final Double EJ3 = EC1 / P4.H;
    protected static final Double CJ3 = EJ3;
    protected static final Double EL1 = EJ3 / P6.H;
    protected static final Double J3L1 = EL1 * P6.P;
    protected static final Double CL1 = J3L1 + CJ3;
    protected static final Double CL2 = CL1 * P4.H;
    protected static final Double KL2 = KC - CL2;
    protected static final Double KC1 = l * P6.H;
    protected static final Double G1J3 = G1C1 - pHalf;
    protected static final Double J3J7 = G1J3 * (EC1 / G1C1);
    protected static final Double J1J7 = KC1 - J3J7;
    protected static final Double J1J8 = KC1 - J3J7 - 2 * (EC1 - J3J7);
    protected static final Double A3G2 = 1 - P6.H;
    protected static final Double KG2 = 1 - A3G2;
    protected static final Double I1G5 = KG2 - GH;


    public final static FinalPointTransition J1 = fpt(KJ1, LEFT);
    public final static FinalPointTransition I1 = fpt(KJ1, UL_V);
    public final static FinalPointTransition E1 = fpt(KE1, UP);
    public final static FinalPointTransition J2 = E1.to(KJ1, LEFT);
    public final static FinalPointTransition C1 = fpt(KC1, UP);
    public final static FinalPointTransition J3 = C1.to(KJ1, LEFT);
    public final static FinalPointTransition J4 = B.to(KJ1, LEFT);
    public final static FinalPointTransition L2 = fpt(KL2, UP);
    public final static FinalPointTransition L1 = L2.to(CL2, RIGHT);
    public final static FinalPointTransition O1 = L2.to(CL2, LEFT);
    public final static FinalPointTransition J8 = J1.to(J1J8, UP);
    public final static FinalPointTransition J7 = J1.to(J1J7, UP);
    public final static FinalPointTransition J5 = J1.to(J1J5, UP);
    public final static FinalPointTransition J6 = J1.to(J1J6, UP);

    public final static FinalPointTransition A3 = fpt(1, UL_H);
    public final static FinalPointTransition G1 = fpt(KH, UL_H, GH, DR_H);
    public final static FinalPointTransition O2 = fpt(KH, UL_H, GH, DL_V);
    public final static FinalPointTransition G2 = A3.to(A3G2, DR_H);
    public final static FinalPointTransition G3 = G2.to(A3G2, UR_V);
    public final static FinalPointTransition G4 = fpt(KG2, UR_H);
    public final static FinalPointTransition G5 = I1.to(I1G5, UR_H);
    public final static FinalPointTransition G6 = G4.to(A3G2, UL_V);
    public final static FinalPointTransition I2 = J1.to(KJ1, UP);
    public final static FinalPointTransition I3 = fpt(KJ1, RIGHT);
    public final static FinalPointTransition I4 = fpt(KJ1, DR_V);
    public final static FinalPointTransition I5 = I4.to(KJ1, UR_H);
    public final static FinalPointTransition I6 = I1.to(KJ1, UR_H);
    public final static FinalPointTransition I7 = I3.to(KJ1, UP);
    public final static FinalPointTransition I8 = I3.to(J1J8, UP);
    public final static FinalPointTransition I9 = I3.to(J1J7, UP);
    public final static FinalPointTransition L3 = I3.to(J1J5, UP);
    public final static FinalPointTransition L4 = I1.to(J1J7, UR_H);
    public final static FinalPointTransition L5 = I1.to(J1J8, UR_H);
    public final static FinalPointTransition L6 = I4.to(I1G5, UR_H);
    public final static FinalPointTransition L7 = I4.to(J1J7, UR_H);
    public final static FinalPointTransition L8 = I4.to(J1J8, UR_H);
    public final static FinalPointTransition L9 = H.to(GH, DR_V);
    public final static FinalPointTransition L10 = G4.to(A3G2, DR_V);


    @DesignSupplier
    public static DesignHelper getDesignHelperE() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

//        GH

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09e_design")
                .addEquations(
                        getEquationsA()
                                .join(getEquationsB())
                                .join(getEquationsD())
                )
                .addImportantVertexes(
                        getImportantPointsA().join(getImportantPointsB()).join(getImportantPointsC()).join(getImportantPointsD())
                )
                .addImportantVertexes(Tile9c.class)
                .addSinglePathsLines(
                        gray,
                        getInstructionsGrayA(),
                        getInstructionsGrayD(),
                        getInstructionsGrayE()
                )
                .addSinglePathsLines(
                        blue,
                        getInstructionsBlueA(),
                        getInstructionsBlueB()
                )
                .addFullPaths(
                        gray,
                        getFullInstructionsGrayA(),
                        getFullInstructionsGrayB(),
                        getFullInstructionsGrayC()

                )
                .addCircleWithRadius(
                        blue,
                        circleInstruction(A, AF)
                )
                .withFontSize(14)
                .addFullPaths(
                        red,
                        getPathFullLinesD(),
                        getPathFullLinesE()
                )
                ;
    }

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_09b"
                , Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        whiteBold,
                        getPathFullLinesD(),
                        getPathFullLinesE()

                )

                .withSize(Payload.Size.MEDIUM)
                .build();

    }

    protected static Sequence<Line> getInstructionsGrayE() {
        return sequence(
                perimeter(1, VER).apply(K),
                perimeter(1, HOR).apply(K),
                diagonals(1, VER).apply(K),
                diagonals(1, HOR).apply(K),
                perimeter(1, HOR).apply(fpt(KJ1, UR_V)),
                perimeter(A3G2, VER).apply(G2)

        ).flatMap(s -> s);
    }

    protected static Sequence<Line> getPathFullLinesE() {
        return sequence(
                Line.line(I2, J8, I9, L3, G6, G5, L4, L8, I5),
                Line.line(I7, I8, J7, J5, G3),
                Line.line(I6, L5, L7, L6, L10),
                Line.line(C, L1, L9),
                Line.line(C, O1, O2)
        );
    }


//
}
