package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.util.Collection;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Tile9b extends Tile9a {
//    protected static final Function<Double, Double> tok = i -> Mappings.<Double>chain(k -> k * m, k->k);

    protected static final Double KF = 1.0 - AF;
    protected static final Double GH = 0.5 * AC - AF;
    protected static final Double KH = l + GH;


    public final static FinalPointTransition H = fpt(pt(KH, UR_H));
    public final static FinalPointTransition G = H.append(pt(GH, DL_H));
    public final static FinalPointTransition I = H.append(pt(GH, UL_V));


    @DesignSupplier
    public static DesignHelper getDesignHelperD() {

        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_09d_design")
                .addEquations(
                        Stream.of(
                                getEquationsA(),
                                getEquationsB(),
                                getEquationsD()
                        ).map(Collection::stream).flatMap(s -> s).collect(toList())
                )
                .addImportantVertexes(
                        Stream.of(
                                getImportantPointsA(),
                                getImportantPointsB(),
                                getImportantPointsC(),
                                getImportantPointsD()
                        ).flatMap(s -> s)
                )
                .addSinglePathsLines(
                        gray,
                        getInstructionsGrayA(),
                        getInstructionsGrayD()
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
                .withFontSize(14)
                .addFullPaths(
                        red,
                        getPathFullLinesD()
                )

//                .addFullPaths(() -> getPathFullLinesD(), red)
                ;
    }

    protected static Stream<PointsPath> getPathFullLinesD() {
        return Stream.of(
                PointsPath.of(A, fpt(pt(1.0, UR_V))),
                PointsPath.of(F, I),
                PointsPath.of(F2, H.append(pt(GH, DR_V)))
        );
    }


    protected static Stream<DesignHelper.ImportantVertex> getImportantPointsD() {
        return Stream.of(
                DesignHelper.ImportantVertex.of("G", G),
                DesignHelper.ImportantVertex.of("H", H),
                DesignHelper.ImportantVertex.of("I", I),
                DesignHelper.ImportantVertex.of("A2", A2),
                DesignHelper.ImportantVertex.of("F2", F2)
        );
    }




    protected static Stream<PointsPath> getInstructionsGrayD() {
        return Stream.of(
                perimeter(AF, VER).apply(A),
                perimeter(GH, VER).apply(H),
                perimeter(GH, HOR).apply(G)
        ).flatMap(s -> s);
    }


    protected static java.util.List<String> getEquationsD() {
        return asList(
                "GH = 0.5 * AC - AF",
                "KH = KC + GH"
        );
    }


}
