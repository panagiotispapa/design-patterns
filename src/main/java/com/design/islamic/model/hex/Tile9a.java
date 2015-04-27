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
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.common.RatioHelper.P6.P;
import static com.design.islamic.model.Hex.Corner.DR_V;
import static com.design.islamic.model.Hex.Corner.UP;
import static com.design.islamic.model.Hex.DIAGONALS;
import static com.design.islamic.model.Hex.PERIMETER;
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
    protected static final Double KE = l * l;
    protected static final Double KC = l;
    protected static final Double AB = m;
    protected static final Double BC = AB;
    protected static final Double KB = H;


    protected static Polygon main = Hex.hex(1, VER);
    protected static Polygon mainHor = Hex.hex(1, HOR);

    protected static Polygon mainL2 = Hex.hex(l, VER);
    protected static Polygon mainL2Hor = Hex.hex(l, HOR);

    protected static Polygon mainL3 = Hex.hex(l * l, VER);
    protected static Polygon mainL3Hor = Hex.hex(l * l, HOR);

    protected static Polygon outer = Hex.hex(k, VER, Hex.centreTransform(1, Hex.Corner.UP));

    protected static Function<Double, List<Pair<Polygon, Polygon.Vertex>>> innerLines1 = r -> {
        Polygon ver = Hex.hex(r, VER);
        Polygon hor = Hex.hex(r, HOR);
        return
                asList(
                        Pair.of(ver, Hex.Corner.DL_V.getVertex()),
                        Pair.of(hor, Hex.Corner.UL_H.getVertex()),
                        Pair.of(ver, Hex.Corner.UR_V.getVertex())
                );
    };

    protected static Function<Double, List<Pair<Polygon, Polygon.Vertex>>> innerLines2 = (r) -> {
        Polygon hor = Hex.hex(1.0, HOR, Hex.centreTransform(r, Hex.Corner.UP));
        return
                asList(
                        Pair.of(hor, Hex.Corner.LEFT.getVertex()),
                        Pair.of(hor, Hex.Corner.RIGHT.getVertex())
                );
    };

    protected static Function<Double, List<Pair<Polygon, Polygon.Vertex>>> innerLines3 = (r) -> {
        Polygon ver = Hex.hex(1.0, VER, Hex.centreTransform(r, Hex.Corner.RIGHT));
        return
                asList(
                        Pair.of(ver, Hex.Corner.UP.getVertex()),
                        Pair.of(ver, Hex.Corner.DOWN.getVertex())
                );
    };

    protected static Function<Double, List<Pair<Polygon, Polygon.Vertex>>> outerLines1 = r -> {
        Polygon ver = Hex.hex(1.0 - l * r, VER, Hex.centreTransform(1, Hex.Corner.UP));
        Polygon hor = ver.getMirror();

        return
                asList(
                        Pair.of(hor, Hex.Corner.LEFT.getVertex()),
                        Pair.of(ver, Hex.Corner.DOWN.getVertex()),
                        Pair.of(hor, Hex.Corner.RIGHT.getVertex())
                );
    };


    protected static Function<Double, List<Pair<Polygon, Polygon.Vertex>>> outerLines2 = r -> {
        Polygon ver = Hex.hex(1.0 - l * r, VER, Hex.centreTransform(1, Hex.Corner.RIGHT));
        Polygon hor = ver.getMirror();

        return
                asList(
                        Pair.of(ver, Hex.Corner.UP.getVertex()),
                        Pair.of(hor, Hex.Corner.LEFT.getVertex()),
                        Pair.of(ver, Hex.Corner.DOWN.getVertex())
                );
    };


    @DesignSupplier
    public static DesignHelper getDesignHelperA() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09a_design")
                .addEquations(getEquationsA())
                .addImportantPoints(getImportantPointsA())
                .addSinglePaths(getInstructionsGrayA(), gray)
                .addSinglePaths(getInstructionsBlueA(), blue)
                .addFullPathsFromLines(getFullInstructionsGrayA(), gray);
    }

    @DesignSupplier
    public static DesignHelper getDesignHelperB() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09b_design")
                .addEquations(getEquationsA())
                .addImportantPoints(
                        Stream.concat(
                                getImportantPointsA().stream(),
                                getImportantPointsB().stream()
                        ).collect(toList())
                )
                .addSinglePaths(getInstructionsGrayA(), gray)
                .addSinglePaths(
                        Stream.concat(
                                getInstructionsBlueA().stream(),
                                getInstructionsBlueB().stream()

                        ).collect(toList()),
                        blue)
                .addFullPathsFromLines(
                        Stream.concat(
                                getFullInstructionsGrayA().stream(),
                                getFullInstructionsGrayB().stream()
                        ).collect(toList()), gray);
    }

    @DesignSupplier
    public static DesignHelper getDesignHelperC() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09c_design")
                .addEquations(getEquationsA())
                .addImportantPoints(
                        Stream.concat(
                                getImportantPointsA().stream(),
                                getImportantPointsB().stream()
                        ).collect(toList())
                )
                .addSinglePaths(getInstructionsGrayA(), gray)
                .addSinglePaths(
                        Stream.concat(
                                getInstructionsBlueA().stream(),
                                getInstructionsBlueB().stream()

                        ).collect(toList()),
                        blue)
                .addFullPathsFromLines(
                        Stream.of(
                                getFullInstructionsGrayA().stream(),
                                getFullInstructionsGrayB().stream(),
                                getFullInstructionsGrayC().stream()
                        ).flatMap(s -> s).collect(toList()), gray)
                .addCircleWithRadius(getCirclesBlueC(), blue)
                ;
    }

    protected static List<Pair<Polygon, Double>> getCirclesBlueC() {
        return asList(
                Pair.of(main, 0.5 * p),
                Pair.of(mainHor, 0.5 * p)
        );
    }

    protected static List<List<Pair<Polygon, Polygon.Vertex>>> getFullInstructionsGrayA() {
        return asList(
                innerLines1.apply(1.0)
        );
    }

    protected static List<List<Pair<Polygon, Polygon.Vertex>>> getFullInstructionsGrayB() {
        return asList(
                innerLines1.apply(l),
                innerLines1.apply(l * l),
                outerLines1.apply(1.0),
                outerLines1.apply(l),
                outerLines2.apply(1.0),
                outerLines2.apply(l)
        );
    }

    protected static List<List<Pair<Polygon, Polygon.Vertex>>> getFullInstructionsGrayC() {
        return asList(
                innerLines2.apply(0.5 * p),
                innerLines3.apply(0.5 * p)
        );
    }

    protected static List<Pair<Polygon, Function<Polygon, List<Path>>>> getInstructionsBlueA() {
        return asList(
                Pair.of(outer, PERIMETER),
                Pair.of(mainL2, PERIMETER),
                Pair.of(mainL2Hor, PERIMETER)
        );
    }


    protected static List<Pair<Polygon, Function<Polygon, List<Path>>>> getInstructionsBlueB() {
        return asList(
                Pair.of(mainL3, PERIMETER),
                Pair.of(mainL3Hor, PERIMETER)
        );
    }

    protected static List<Pair<Polygon, Function<Polygon, List<Path>>>> getInstructionsGrayA() {
        return asList(
                Pair.of(main, PERIMETER),
                Pair.of(mainHor, PERIMETER),
                Pair.of(main, DIAGONALS),
                Pair.of(mainHor, DIAGONALS)
        );
    }

    protected static List<Triple<Polygon, Polygon.Vertex, String>> getImportantPointsA() {
        return asList(
                Triple.of(main, UP.getVertex(), "A"),
                Triple.of(Hex.hex(H, VER), UP.getVertex(), "B"),
                Triple.of(mainL2, UP.getVertex(), "C"),
                Triple.of(outer, DR_V.getVertex(), "D")

        );
    }

    protected static List<Triple<Polygon, Polygon.Vertex, String>> getImportantPointsB() {
        return asList(
                Triple.of(mainL3, UP.getVertex(), "E")
        );
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


}
