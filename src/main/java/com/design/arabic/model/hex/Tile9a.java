package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.FinalPointTransition;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.CircleInstruction.circleInstruction;
import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.*;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile9a {
    protected static final Double m = 1 - H;
    protected static final Double n = 2 * m;
    protected static final Double l = 1 - n;
    protected static final Double k = m / P;
    protected static final Double p = l - l * l;
    protected static final Double q = 1 - l;
    protected static final Double pHalf = 0.5 * p;

    protected static final Double AF = 0.5 * p;
    protected static final Double AC = q;
    protected static final Double AD = AC;
    protected static final Double KE = l * l;
    protected static final Double AE = 1 - KE;
    protected static final Double KC = l;
    protected static final Double AB = m;
    protected static final Double BC = AB;
    protected static final Double KB = H;
    protected static final Double KJ1 = pHalf;
    protected static final Double KE1 = KE * H;
    protected static final Double KC1 = KC * H;


    public final static FinalPointTransition A = fpt(1, UP);
    public final static FinalPointTransition A1 = fpt(1, RIGHT);
    public final static FinalPointTransition A2 = fpt(1, UR_V);
    public final static FinalPointTransition B = fpt(H, UP);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = A.to(AD, DR_V);
    public final static FinalPointTransition E = fpt(KE, UP);
    public final static FinalPointTransition F = A.to(AF, DL_V);
    public final static FinalPointTransition F2 = A2.to(AF, DOWN);


    @DesignSupplier
    public static DesignHelper getDesignHelperA() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09a_design")
                .addEquations(getEquationsA())
                .addImportantVertexes(
                        getImportantPointsA()
                )
                .addSinglePathsLines(
                        gray,
                        getInstructionsGrayA()
                )
                .addSinglePathsLines(
                        blue,
                        getInstructionsBlueA()
                )
                .addFullPaths(
                        gray,
                        getFullInstructionsGrayA()
                )

                ;
    }

    protected static Sequence<Line> getFullInstructionsGrayA() {
        return sequence(
                Line.line(fpt(1, LEFT), fpt(1, UP), fpt(1, RIGHT))
        );
    }

    protected static Sequence<Line> getFullInstructionsGrayB() {
        return sequence(
                Line.line(fpt(KE, LEFT), fpt(KE, UP), fpt(KE, RIGHT)),
                Line.line(fpt(KC, LEFT), fpt(KC, UP), fpt(KC, RIGHT)),
                Line.line(A.to(AC, LEFT), A.to(AC, DOWN), A.to(AC, RIGHT)),
                Line.line(A.to(AE, LEFT), A.to(AE, DOWN), A.to(AE, RIGHT)),
                Line.line(A1.to(AC, UP), A1.to(AC, LEFT), A1.to(AC, DOWN)),
                Line.line(A1.to(AE, UP), A1.to(AE, LEFT), A1.to(AE, DOWN))
        );
    }

    protected static Sequence<Line> getFullInstructionsGrayC() {
        return sequence(
                diagonalHorizontal(1).apply(fpt(AF, UP)),
                diagonalVertical(1).apply(fpt(AF, RIGHT))

        ).flatMap(s -> s);
    }

    protected static Sequence<Line> getInstructionsBlueA() {
        return sequence(
                perimeter(KC, VER).apply(K),
                perimeter(KC, HOR).apply(K),
                perimeter(AC, VER).apply(A)
        ).flatMap(s -> s);
    }

    protected static Sequence<Line> getInstructionsBlueB() {
        return sequence(
                perimeter(KE, VER).apply(K),
                perimeter(KE, HOR).apply(K)
//                perimeter(AC, VER).apply(A1)
        ).flatMap(s -> s);
    }

    protected static Sequence<Line> getInstructionsGrayA() {
        return sequence(
                perimeter(1, VER).apply(K),
                perimeter(1, HOR).apply(K),
                diagonals(1, VER).apply(K),
                diagonals(1, HOR).apply(K)
        ).flatMap(s -> s);
    }

    protected static Sequence<ImportantVertex> getImportantPointsA() {
        return sequence(
                ImportantVertex.of("A", A),
                ImportantVertex.of("B", B),
                ImportantVertex.of("C", C),
                ImportantVertex.of("D", D)
        );
    }

    protected static Sequence<ImportantVertex> getImportantPointsB() {
        return sequence(
                ImportantVertex.of("E", E),
                ImportantVertex.of("A1", A1)
        );
    }

    protected static Sequence<ImportantVertex> getImportantPointsC() {
        return sequence(
                ImportantVertex.of("F", F)
        );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelperB() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09b_design")
                .addEquations(getEquationsA())
                .addImportantVertexes(
                        join(
                                getImportantPointsA(),
                                getImportantPointsB()
                        )
                )
                .addSinglePathsLines(
                        gray,
                        getInstructionsGrayA()
                )
                .addSinglePathsLines(
                        blue,
                        getInstructionsBlueA(),
                        getInstructionsBlueB()

                )
                .addFullPaths(
                        gray,
                        getFullInstructionsGrayA(),
                        getFullInstructionsGrayB()
                );
    }

    @DesignSupplier
    public static DesignHelper getDesignHelperC() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09c_design")
                .addEquations(
                        getEquationsA(),
                        getEquationsB()
                )
                .addImportantVertexes(
                        getImportantPointsA(),
                        getImportantPointsB(),
                        getImportantPointsC()
                )
                .addSinglePathsLines(
                        gray,
                        getInstructionsGrayA()
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
//                .addCircleWithRadius(getCirclesBlueC(), blue)
                ;
    }


    protected static Sequence<String> getEquationsA() {
        return sequence(
                "KA = 1",
                "KB = h",
                "AB = 1 - h",
                "AC = 2 * AB",
                "AD = AC"
        );
    }

    protected static Sequence<String> getEquationsB() {
        return sequence(
                "CE = KC - KE",
                "AF = 0.5 * CE"
        );
    }


}
