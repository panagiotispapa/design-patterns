package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.Polygon;
import com.design.common.model.Path;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
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

public class Tile9b extends Tile9a {
//    protected static final Function<Double, Double> tok = i -> Mappings.<Double>chain(k -> k * m, k->k);

    protected static final Double KF = 1.0 - AF;
    protected static final Double GH = 0.5 * AC - AF;
    protected static final Double KH = l + GH;


    protected static Polygon hexAF = Hex.hex(0.5 * p, VER, Hex.centreTransform(1.0, Hex.Corner.UP));
    protected static Polygon hexGH = Hex.hex(GH, HOR, Hex.centreTransform(l, Hex.Corner.UR_H));
    protected static Polygon hexHI = Hex.hex(GH, VER, Hex.centreTransform(KH, Hex.Corner.UR_H));
//    protected static Polygon mainHor = Hex.hex(1, HOR);


    @DesignSupplier
    public static DesignHelper getDesignHelperD() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09d_design")
                .addEquations(getEquationsA())
                .addImportantPoints(
                        Stream.of(
                                getImportantPointsA().stream(),
                                getImportantPointsB().stream(),
                                getImportantPointsD().stream()
                        ).flatMap(s -> s).collect(toList())
                )
                .addSinglePaths(
                        Stream.of(
                                getInstructionsGrayA().stream(),
                                getInstructionsGrayD().stream()
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
                .addFullPaths(() -> getPathFullLinesD(), red)
                .addAllVertexesAsImportantPoints(asList(
//                        hexGH
//                        Hex.hex(GH, VER, Hex.centreTransform(KH, Hex.Corner.UL_H))
                ))
                ;
    }

    protected static java.util.List<Triple<Polygon, Polygon.Vertex, String>> getImportantPointsD() {
        return asList(
                Triple.of(hexAF, DOWN.getVertex(), "F"),
                Triple.of(mainL2Hor, UR_H.getVertex(), "G"),
                Triple.of(hexGH, UR_H.getVertex(), "H"),
                Triple.of(hexHI, UL_V.getVertex(), "I")
        );
    }


    protected static java.util.List<Pair<Polygon, Function<Polygon, java.util.List<Path>>>> getInstructionsGrayD() {
        return asList(
                Pair.of(hexAF, PERIMETER),
                Pair.of(hexGH, PERIMETER),
                Pair.of(hexHI, PERIMETER)
        );
    }

    protected static java.util.List<Polygon.VertexPath> getPathFullLinesD() {
        return asList(
                () -> asList(
                        instruction(hexAF, DL_V),
                        instruction(Hex.hex(GH, VER, Hex.centreTransform(KH, Hex.Corner.UR_H)), UL_V)//,
                ),
                () -> asList(
                        instruction(hexAF, DR_V),
                        () -> Pair.of(Hex.hex(GH, VER, Hex.centreTransform(KH, Hex.Corner.UL_H)), Hex.Vertex.SIX)

                ),
                () -> asList(
                        instruction(main, UP),
                        instruction(main, UR_V)
                )
        );

    }

}
