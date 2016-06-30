package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.DesignHelper.ImportantVertex;
import com.design.common.Polygon;
import com.design.common.RatioHelper.P4;
import com.design.common.RatioHelper.P6;
import com.design.common.model.Path.Paths;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.PERIMETER;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Tile9c extends Tile9b {

    protected static final Double A1B = 0.5;
    protected static final Double A1J4 = 0.5 - pHalf;
    protected static final Double J4J5 = BC * (A1J4 / A1B);
    protected static final Double KJ5 = P6.H - J4J5;
    protected static final Double J1J5 = KB - J4J5;
    protected static final Double KJ6 = KB - J4J5 - 2.0 * (BC - J4J5);
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
    protected static final Double KJ7 = KC1 - J3J7;
    protected static final Double KJ8 = KC1 - J3J7 - 2.0 * (EC1 - J3J7);

    @DesignSupplier
    public static DesignHelper getDesignHelperE() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

//        GH

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09e_design")
                .addEquations(getEquationsA())
                .addImportantVertexes(
                        Stream.of(
                                getImportantPointsA().stream(),
                                getImportantPointsB().stream(),
                                getImportantPointsD().stream(),
                                getImportantPointsE().stream()
                        ).flatMap(s -> s).collect(toList())
                )
                .addSinglePaths(
                        Stream.of(
                                getInstructionsGrayA().stream(),
                                getInstructionsGrayD().stream(),
                                getInstructionsGrayE().stream()
                        ).flatMap(s -> s).collect(toList()),
                        gray)
                .addSinglePaths(
                        Stream.concat(
                                getInstructionsBlueA().stream(),
                                getInstructionsBlueB().stream()

                        ).collect(toList()),
                        blue)
                .addFullPaths(() ->
                        Stream.of(
                                getFullInstructionsGrayA().stream(),
                                getFullInstructionsGrayB().stream(),
                                getFullInstructionsGrayC().stream()
                        ).flatMap(s -> s).collect(toList()), gray)
                .addCircleWithRadius(getCirclesBlueC(), blue)
                .withFontSize(14)
                .addFullPaths(() ->
                        Stream.of(
                                getPathFullLinesD().stream(),
                                getPathFullLinesE().stream()
                        ).flatMap(s -> s).collect(toList())
                        , red)
                .addAllVertexesAsImportantPoints(asList(
//                        hexGH
//                        Hex.hex(GH, VER, Hex.centreTransform(KH, Hex.Corner.UL_H))
                ))
                ;
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_tile_09b"
                , Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(() ->
                                Stream.of(
                                        getPathFullLinesD().stream(),
                                        getPathFullLinesE().stream()
                                ).flatMap(s -> s).collect(toList()),
                        whiteBold
                )
                .withSize(PayloadSimple.Size.MEDIUM)
                .build();

    }

    protected static java.util.List<ImportantVertex> getImportantPointsE() {
        return asList(
                ImportantVertex.of(Hex.hex(pHalf, HOR), LEFT.getVertex(), "J1"),
                ImportantVertex.of(Hex.hex(KE * P6.H, VER), UP.getVertex(), "E1"),
                ImportantVertex.of(Hex.hex(KC * P6.H, VER), UP.getVertex(), "C1"),
                ImportantVertex.of(Hex.hex(KE * P6.H, VER, Hex.centreTransform(pHalf, LEFT)), UP.getVertex(), "J2"),
                ImportantVertex.of(Hex.hex(KC * P6.H, VER, Hex.centreTransform(pHalf, LEFT)), UP.getVertex(), "J3"),
                ImportantVertex.of(Hex.hex(P6.H, VER, Hex.centreTransform(pHalf, LEFT)), UP.getVertex(), "J4"),
                ImportantVertex.of(Hex.hex(J1J5, VER, Hex.centreTransform(pHalf, LEFT)), UP.getVertex(), "J5"),
                ImportantVertex.of(Hex.hex(KJ6, VER, Hex.centreTransform(pHalf, LEFT)), UP.getVertex(), "J6"),
                ImportantVertex.of(Hex.hex(KJ7, VER, Hex.centreTransform(pHalf, LEFT)), UP.getVertex(), "J7"),
                ImportantVertex.of(Hex.hex(KJ8, VER, Hex.centreTransform(pHalf, LEFT)), UP.getVertex(), "J8"),
                ImportantVertex.of(mainHor, UL_H.getVertex(), "A1"),
                ImportantVertex.of(mainL2Hor, UL_H.getVertex(), "G1"),
                ImportantVertex.of(Hex.hex(KL2, VER), UP.getVertex(), "L2"),
                ImportantVertex.of(Hex.hex(CL2, HOR, Hex.centreTransform(KL2, UP)), RIGHT.getVertex(), "L1")
        );
    }

    //
//
    protected static java.util.List<Pair<Polygon, Function<Polygon, Paths>>> getInstructionsGrayE() {
        return asList(
                Pair.of(Hex.hex(BC, VER, Hex.centreTransform(KB, UL_H)), PERIMETER),
                Pair.of(Hex.hex(1.0, HOR, Hex.centreTransform(pHalf, Hex.Corner.UR_V)), PERIMETER)
        );
    }

    protected static java.util.List<Polygon.VertexPath> getPathFullLinesE() {
        return asList(
                () -> asList(
                        instruction(Hex.hex(BC, VER, Hex.centreTransform(KB, UL_H)), UR_V),
                        instruction(Hex.hex(KJ5, VER, Hex.centreTransform(pHalf, Hex.Corner.LEFT)), UP),
                        instruction(Hex.hex(KJ7, VER, Hex.centreTransform(pHalf, Hex.Corner.LEFT)), UP),
                        instruction(Hex.hex(KJ8, VER, Hex.centreTransform(pHalf, Hex.Corner.RIGHT)), UP),
                        instruction(Hex.hex(pHalf, VER, Hex.centreTransform(pHalf, Hex.Corner.RIGHT)), UP)
                ),
                () -> asList(
                        instruction(Hex.hex(BC, VER, Hex.centreTransform(KB, UR_H)), UL_V),
                        instruction(Hex.hex(KJ5, VER, Hex.centreTransform(pHalf, Hex.Corner.RIGHT)), UP),
                        instruction(Hex.hex(KJ7, VER, Hex.centreTransform(pHalf, Hex.Corner.RIGHT)), UP),
                        instruction(Hex.hex(KJ8, VER, Hex.centreTransform(pHalf, Hex.Corner.LEFT)), UP),
                        instruction(Hex.hex(pHalf, VER, Hex.centreTransform(pHalf, Hex.Corner.LEFT)), UP)
                ),
                () -> asList(
                        instruction(Hex.hex(BC, VER, Hex.centreTransform(KB, RIGHT)), UP),
                        instruction(Hex.hex(KB - GH, HOR, Hex.centreTransform(pHalf, UP)), RIGHT),
                        instruction(Hex.hex(KJ7, HOR, Hex.centreTransform(pHalf, UP)), RIGHT),
                        instruction(Hex.hex(KJ8, HOR, Hex.centreTransform(pHalf, DOWN)), RIGHT),
                        instruction(Hex.hex(pHalf, HOR, Hex.centreTransform(pHalf, DOWN)), RIGHT)
                ),
                () -> asList(
                        instruction(Hex.hex(BC, VER, Hex.centreTransform(KB, RIGHT)), DOWN),
                        instruction(Hex.hex(KB - GH, HOR, Hex.centreTransform(pHalf, DOWN)), RIGHT),
                        instruction(Hex.hex(KJ7, HOR, Hex.centreTransform(pHalf, DOWN)), RIGHT),
                        instruction(Hex.hex(KJ8, HOR, Hex.centreTransform(pHalf, UP)), RIGHT),
                        instruction(Hex.hex(pHalf, HOR, Hex.centreTransform(pHalf, UP)), RIGHT)
                ),

                () -> asList(
                        instruction(Hex.hex(GH, VER, Hex.centreTransform(KC + GH, UL_H)), DL_V),
                        instruction(Hex.hex(CL2, HOR, Hex.centreTransform(KL2, UP)), LEFT),
                        instruction(Hex.hex(KC, VER), UP),
                        instruction(Hex.hex(CL2, HOR, Hex.centreTransform(KL2, UP)), RIGHT),
                        instruction(Hex.hex(GH, VER, Hex.centreTransform(KC + GH, UR_H)), DR_V)
                )
        );

    }
//
}
