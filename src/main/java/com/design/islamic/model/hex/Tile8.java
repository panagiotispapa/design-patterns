package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.Hex.*;
import static com.design.islamic.model.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Tile8 extends TileBasic {

    private static final double KB = 0.5;
    private static final double KC = £H.apply(KB);
    private static final double AC = 1 - KC;
    private static final double AD = 1 - HEIGHT_RATIO;
    private static final double AE = $H.apply(AC);
    private static final double AF = $H.apply(AE);
    private static final double FE = AE * 0.5;
    private static final double AG = AC * 0.5;
    private static final double KF = 1 - AF;
    private static final double KH = FE;
    private static final double KI = £H.apply(KH);
    private static final double KJ = 2.0 * KH;
    private static final double JA = 1 - KJ;
    private static final double IJ = KJ - KI;
    private static final double JD = JA - AD;
    private static final double JM = $H.apply(IJ);

    public Tile8(Pair<Point2D, Double> initialConditions) {

        super(initialConditions);

    }

    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);
        Polygon mainReg = main.getRegistered();
        Polygon hexAG = Hex.hex(AC * 0.5, VER, centreTransform(1, VER));
        Polygon hexJD = Hex.hex(JD, HOR, centreTransform(KJ, VER));
        Polygon hexJDRot = Hex.hex(JD, HOR, Polygon.centreTransform(KJ, Hex.Vertex.SIX, VER));
        Polygon hexJM = Hex.hex(JM, HOR, centreTransform(KJ, VER));
        Polygon hexJM_2 = Hex.hex(JM, VER, Polygon.centreTransform(KJ, Hex.Vertex.ONE, HOR));
        Polygon hexJM_3 = Hex.hex(JM, VER, Polygon.centreTransform(KJ, Hex.Vertex.TWO, HOR));
        Polygon hexJM_4 = Hex.hex(JM, HOR, Polygon.centreTransform(KJ, Hex.Vertex.SIX, VER));

        return new PayloadSimple(
                Arrays.asList(
                        asList(
                                Pair.of(hexJM_3, FOUR),
                                Pair.of(hexAG, THREE),
                                Pair.of(hexAG, FOUR),
                                Pair.of(hexAG, FIVE),
                                Pair.of(hexJM_2, FOUR)
                        ),
                        asList(

                                Pair.of(hexJM_4, FOUR),
                                Pair.of(hexJDRot, ONE),
                                Pair.of(mainReg, ONE),
                                Pair.of(hexJD, ONE),
                                Pair.of(hexJM, FOUR)
                        )
                )
                , Hex.ALL_VERTEX_INDEXES
        );
    }

    public String design1() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        List<Point2D> hexGrid = Grid.grid(initialConditions.getLeft(), initialConditions.getRight() / 4.0, Grid.Configs.HEX_VER.getConfiguration(), 12);

        Polygon main = Hex.hex(1, VER);
        Polygon mainHor = main.getMirror();
        Polygon mainReg = main.getRegistered();
        Polygon mainHorReg = mainHor.getRegistered();

        Polygon hexKB = Hex.hex(0.5, VER);
        Polygon hexKC = Hex.hex(hexKB.getRatio() / HEIGHT_RATIO, VER);
        Polygon hexKD = Hex.hex(HEIGHT_RATIO, VER);
        Polygon hexAE = Hex.hex((1 - hexKC.getRatio()) * HEIGHT_RATIO, HOR, centreTransform(1, VER));
        Polygon hexAF = Hex.hex(hexAE.getRatio() * HEIGHT_RATIO, VER, centreTransform(1, VER));
//        Polygon hexFE = Hex.hex(hexAE.getRatio() * 0.5, VER);
        Polygon hexKF = Hex.hex(KF, HOR);
        Polygon hexAG = Hex.hex(AC * 0.5, VER, centreTransform(1, VER));
        Polygon hexKH = Hex.hex(KH, VER);
        Polygon hexKI = Hex.hex(KI, VER);
        Polygon hexKJ = Hex.hex(KJ, VER);
        Polygon hexJD = Hex.hex(JD, HOR, centreTransform(KJ, VER));
        Polygon hexJARot = Hex.hex(JA, HOR, Polygon.centreTransform(KJ, Hex.Vertex.SIX, VER));
        Polygon hexJM = Hex.hex(JM, HOR, centreTransform(KJ, VER));
        Polygon hexJM_2 = Hex.hex(JM, VER, Polygon.centreTransform(KJ, Hex.Vertex.ONE, HOR));
        Polygon hexJM_3 = Hex.hex(JM, VER, Polygon.centreTransform(KJ, Hex.Vertex.TWO, HOR));
        Polygon hexJM_4 = Hex.hex(JM, HOR, Polygon.centreTransform(KJ, Hex.Vertex.SIX, VER));

        Polygon hexRot1 = Hex.hex(1, HOR, centreTransform(FE, VER));
        Polygon hexRot2 = Hex.hex(1, VER, centreTransform(FE, HOR));
        Polygon hexRot3 = Hex.hex(1, VER, centreTransform(KF, HOR));

//        List<Pair<Point2D, String>> importantPoints = new ArrayList<>();
        List<Pair<Point2D, String>> importantPoints = Stream.of(
                Triple.of(main, ONE, "A"),
                Triple.of(hexKB, ONE, "B"),
                Triple.of(hexKC, ONE, "C"),
                Triple.of(hexKD, ONE, "D"),
                Triple.of(hexAE, FIVE, "E"),
                Triple.of(hexAF, FOUR, "F"),
                Triple.of(hexKF, ONE, "F"),
                Triple.of(hexAG, FOUR, "G"),
                Triple.of(hexKH, ONE, "H"),
                Triple.of(hexKI, TWO, "I"),
                Triple.of(hexKJ, ONE, "J"),
                Triple.of(hexJD, TWO, "L"),
                Triple.of(hexJM, FIVE, "M")
        ).map(importantPoint).collect(toList());

        importantPoints.add(Pair.of(initialConditions.getLeft(), "K"));

//        importantPoints.addAll(
//                Stream.of(
//                        hexJARot,
//                        hexKI,
//                        hexAG,
//                        hexJM,
//                        hexJM_2,
//                        hexJM_3,
//                        hexJM_4
//                ).map(allHexVertexesAsImportant).map(Collection::stream).flatMap(s -> s).map(importantPoint).collect(toList())
//        );

        List<String> equations = asList(
                "KB = 0.5",
                "KC = KB/h",
                "KD = h",
                "AC = 1 - KC",
                "AD = 1 - h",
                "AE = AC * h",
                "AF = AE * h",
                "FE = AE * 0.5",
                "AG = AC * 0.5",
                "KH = FE",
                "KI = KH / h",
                "KJ = 2 * KH",
                "JA = 1 - KJ",
                "JL = JA",
                "IJ = KJ-KI",
                "JM = IJ*h"
        );

        IntStream.range(0, equations.size())
                .forEach(i -> importantPoints.add(Pair.of(new Point2D.Double(1000, (i + 1) * 40), equations.get(i))));

        importantPoints.stream().map(drawText());
        importantPoints.stream().map(Pair::getLeft).map(highlightPoint());

        return
                Stream.of(
//                        Stream.of(
//                                highlightPoints("black", 2).apply(hexGrid)
//                        ),
                        Stream.of(

                                Pair.of(main, Hex.DIAGONALS),
                                Pair.of(mainHor, Hex.DIAGONALS),
                                Pair.of(hexAE, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(main, Hex.INNER_TRIANGLES)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(mainHor, Hex.INNER_TRIANGLES),
                                Pair.of(mainHor, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        Stream.of(
                                toMixVertexesFull.apply(
                                        asList(
                                                Pair.of(hexRot1, THREE),
                                                Pair.of(hexRot1, SIX)
                                        )
                                ),
                                toMixVertexesFull.apply(
                                        asList(
                                                Pair.of(hexRot2, TWO),
                                                Pair.of(hexRot2, FIVE)
                                        )
                                ),
                                toMixVertexesFull.apply(
                                        asList(
                                                Pair.of(hexRot3, TWO),
                                                Pair.of(hexRot3, FIVE)
                                        )
                                ),
                                toMixVertexesFull.apply(
                                        asList(
                                                Pair.of(main, SIX),
                                                Pair.of(mainHor, ONE),
                                                Pair.of(main, ONE)
                                        )
                                ),
                                toMixVertexesFull.apply(
                                        asList(
                                                Pair.of(mainHorReg, SIX),
                                                Pair.of(mainReg, ONE),
                                                Pair.of(mainHorReg, ONE)
                                        )
                                )
                        ).map(toPolylines(gray)),
                        importantPoints.stream().map(drawText()),
                        importantPoints.stream().map(Pair::getLeft).map(highlightPoint()),
                        Stream.of(
                                getPayloadSimple().toLines(initialConditions)
                        ).map(toPolylines(red))

                ).flatMap(s -> s).collect(joining());
    }

}