package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.FinalPointTransition;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.List;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Tile9a {
    protected static final Double m = 1.0 - H;
    protected static final Double n = 2.0 * m;
    protected static final Double l = 1.0 - n;
    protected static final Double k = m / P;
    protected static final Double p = l - l * l;
    protected static final Double q = 1.0 - l;
    protected static final Double pHalf = 0.5 * p;

    protected static final Double AF = 0.5 * p;
    protected static final Double AC = q;
    protected static final Double AD = AC;
    protected static final Double KE = l * l;
    protected static final Double AE = 1.0 - KE;
    protected static final Double KC = l;
    protected static final Double AB = m;
    protected static final Double BC = AB;
    protected static final Double KB = H;
    protected static final Double KJ1 = pHalf;
    protected static final Double KE1 = KE * H;
    protected static final Double KC1 = KC * H;


    public final static FinalPointTransition A = fpt(pt(1.0, UP));
    public final static FinalPointTransition A1 = fpt(pt(1.0, RIGHT));
    public final static FinalPointTransition A2 = fpt(pt(1.0, UR_V));
    public final static FinalPointTransition B = fpt(pt(H, UP));
    public final static FinalPointTransition C = fpt(pt(KC, UP));
    public final static FinalPointTransition D = A.append(pt(AD, DR_V));
    public final static FinalPointTransition E = fpt(pt(KE, UP));
    public final static FinalPointTransition F = A.append(pt(AF, DL_V));
    public final static FinalPointTransition F2 = A2.append(pt(AF, DOWN));


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

    protected static Stream<PointsPath> getFullInstructionsGrayA() {
        return Stream.of(
                PointsPath.of(fpt(pt(1.0, LEFT)), fpt(pt(1.0, UP)), fpt(pt(1.0, RIGHT)))
        );
    }

    protected static Stream<PointsPath> getFullInstructionsGrayB() {
        return Stream.of(
                PointsPath.of(fpt(pt(KE, LEFT)), fpt(pt(KE, UP)), fpt(pt(KE, RIGHT))),
                PointsPath.of(fpt(pt(KC, LEFT)), fpt(pt(KC, UP)), fpt(pt(KC, RIGHT))),
                PointsPath.of(A.append(pt(AC, LEFT)), A.append(pt(AC, DOWN)), A.append(pt(AC, RIGHT))),
                PointsPath.of(A.append(pt(AE, LEFT)), A.append(pt(AE, DOWN)), A.append(pt(AE, RIGHT))),
                PointsPath.of(A1.append(pt(AC, UP)), A1.append(pt(AC, LEFT)), A1.append(pt(AC, DOWN))),
                PointsPath.of(A1.append(pt(AE, UP)), A1.append(pt(AE, LEFT)), A1.append(pt(AE, DOWN)))
        );
    }

    protected static Stream<PointsPath> getFullInstructionsGrayC() {
        return Stream.of(
                diagonalHorizontal(1.0).apply(fpt(pt(AF, UP))),
                diagonalVertical(1.0).apply(fpt(pt(AF, RIGHT)))

        ).flatMap(s -> s);
    }

    protected static Stream<PointsPath> getInstructionsBlueA() {
        return Stream.of(
                perimeter(KC, VER).apply(K),
                perimeter(KC, HOR).apply(K),
                perimeter(AC, VER).apply(A)
        ).flatMap(s -> s);
    }

    protected static Stream<PointsPath> getInstructionsBlueB() {
        return Stream.of(
                perimeter(KE, VER).apply(K),
                perimeter(KE, HOR).apply(K)
//                perimeter(AC, VER).apply(A1)
        ).flatMap(s -> s);
    }

    protected static Stream<PointsPath> getInstructionsGrayA() {
        return Stream.of(
                perimeter(1.0, VER).apply(K),
                perimeter(1.0, HOR).apply(K),
                diagonals(1.0, VER).apply(K),
                diagonals(1.0, HOR).apply(K)
        ).flatMap(s -> s);
    }

    protected static Stream<ImportantVertex> getImportantPointsA() {
        return Stream.of(
                ImportantVertex.of("A", A),
                ImportantVertex.of("B", B),
                ImportantVertex.of("C", C),
                ImportantVertex.of("D", D)
        );
    }

    protected static Stream<ImportantVertex> getImportantPointsB() {
        return Stream.of(
                ImportantVertex.of("E", E),
                ImportantVertex.of("A1", A1)
        );
    }

    protected static Stream<ImportantVertex> getImportantPointsC() {
        return Stream.of(
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
                        Stream.concat(
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
                        Stream.concat(
                                getInstructionsBlueA(),
                                getInstructionsBlueB()
                        )
                )
                .addFullPaths(
                        gray,
                        Stream.of(
                                getFullInstructionsGrayA(),
                                getFullInstructionsGrayB()
                        ).flatMap(s -> s)
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
                        Stream.concat(
                                getEquationsA().stream(),
                                getEquationsB().stream()
                        ).collect(toList())
                )
                .addImportantVertexes(
                        Stream.of(
                                getImportantPointsA(),
                                getImportantPointsB(),
                                getImportantPointsC()
                        ).flatMap(s -> s)
                )
                .addSinglePathsLines(
                        gray,
                        getInstructionsGrayA()
                )
                .addSinglePathsLines(
                        blue,
                        Stream.concat(
                                getInstructionsBlueA(),
                                getInstructionsBlueB()
                        )
                )
                .addFullPaths(
                        gray,
                        Stream.of(
                                getFullInstructionsGrayA(),
                                getFullInstructionsGrayB(),
                                getFullInstructionsGrayC()
                        ).flatMap(s -> s)

                )
                .addCircleWithRadius(
                        blue,
                        Pair.of(A, AF)
                )
//                .addCircleWithRadius(getCirclesBlueC(), blue)
                ;
    }


    protected static List<String> getEquationsA() {
        return asList(
                "KA = 1",
                "KB = h",
                "AB = 1 - h",
                "AC = 2 * AB",
                "AD = AC"
        );
    }

    protected static List<String> getEquationsB() {
        return asList(
                "CE = KC - KE",
                "AF = 0.5 * CE"
        );
    }


}
