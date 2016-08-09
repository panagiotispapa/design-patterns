package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.googlecode.totallylazy.Sequence;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;

import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

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
                        getEquationsA(),
                        getEquationsB(),
                        getEquationsD()
                )
                .addImportantVertexes(
                        getImportantPointsA(),
                        getImportantPointsB(),
                        getImportantPointsC(),
                        getImportantPointsD()
                )
                .addSinglePathsLines(
                        gray,
                        getInstructionsGrayA(),
                        getInstructionsGrayD()
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

    protected static Sequence<PointsPath> getPathFullLinesD() {
        return sequence(
                PointsPath.of(A, fpt(pt(1.0, UR_V))),
                PointsPath.of(F, I),
                PointsPath.of(F2, H.append(pt(GH, DR_V)))
        );
    }


    protected static Sequence<DesignHelper.ImportantVertex> getImportantPointsD() {
        return sequence(
                DesignHelper.ImportantVertex.of("G", G),
                DesignHelper.ImportantVertex.of("H", H),
                DesignHelper.ImportantVertex.of("I", I),
                DesignHelper.ImportantVertex.of("A2", A2),
                DesignHelper.ImportantVertex.of("F2", F2)
        );
    }


    protected static Sequence<PointsPath> getInstructionsGrayD() {
        return sequence(
                perimeter(AF, VER).apply(A),
                perimeter(GH, VER).apply(H),
                perimeter(GH, HOR).apply(G)
        ).flatMap(s -> s);
    }


    protected static Sequence<String> getEquationsD() {
        return sequence(
                "GH = 0.5 * AC - AF",
                "KH = KC + GH"
        );
    }


}
