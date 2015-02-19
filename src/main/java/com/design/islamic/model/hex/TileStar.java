package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.DrawSegmentsInstructions;
import com.design.islamic.model.Hex;
import com.design.islamic.model.tiles.HexGrid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Stream;

import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.model.Hex.HEIGHT_RATIO;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class TileStar extends TileBasic {

    public static double RATIO_1 = 0.5;
    public static double RATIO_2 = HEIGHT_RATIO / (HEIGHT_RATIO + 0.5);
    public static double RATIO_3 = 0.5 - (HEIGHT_RATIO - 0.5) * 0.5;
    private final double ratio;

    public TileStar(Pair<Point2D, Double> initialConditions, double ratio) {

        super(initialConditions);

        this.ratio = ratio;
    }

    public static Triple<Polygon, Polygon,DrawSegmentsInstructions.CombinedVertexes> getLines(double ratio) {
        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon inner = Hex.hex(ratio, Polygon.Type.VER);

        return

                Triple.of(inner, main.getRegistered(), DrawSegmentsInstructions.CombinedVertexes.STAR);


    }

    @Override
    protected Stream<Triple<Polygon, Polygon, DrawSegmentsInstructions.CombinedVertexes>> getMainStars() {
        return Stream.of(getLines(ratio));
    }

    public String design1() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        List<Point2D> hexGrid = HexGrid.grid(initialConditions.getLeft(), initialConditions.getRight() / 4.0, HexGrid.TYPE.VER, 12);

        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon registered = main.getRegistered();
        Polygon inner = Hex.hex(RATIO_1, Polygon.Type.VER);

        return
                Stream.of(
                        Stream.of(
                                highlightPoints("black", 2).apply(hexGrid)
                        ),
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(main, Hex.DIAGONALS)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(registered, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(inner, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        Stream.of(
                                getLines(RATIO_1)
                        ).map(toStar.andThen(toPolylines(red)))

                ).flatMap(s -> s).collect(joining());

    }

    public String design2() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String blueLight = newStyle("blue", 1, 0.3);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        List<Point2D> hexGrid = HexGrid.grid(initialConditions.getLeft(), initialConditions.getRight() / 4.0, HexGrid.TYPE.VER, 12);

        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon registered = main.getRegistered();
        Polygon inner = Hex.hex(1.0 / 2.0, Polygon.Type.VER);
        Polygon inner2 = Hex.hex(RATIO_2, Polygon.Type.VER);
        Polygon inner2Reg = inner2.getRegistered();

        return
                Stream.of(
                        Stream.of(
                                highlightPoints("black", 2).apply(hexGrid)
                        ),
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(registered, Hex.DIAGONALS),
                                Pair.of(main, Hex.DIAGONALS)
                        ).map(toLines.andThen(toPolylines(gray))),

                        Stream.of(
                                Pair.of(initialConditions.getLeft(), "K"),
                                Pair.of(toVertex.apply(Pair.of(inner2, Hex.Vertex.SIX)), "B"),
                                Pair.of(toVertex.apply(Pair.of(inner2Reg, Hex.Vertex.ONE)), "A"),
                                Pair.of(toVertex.apply(Pair.of(registered, Hex.Vertex.ONE)), "C"),
                                Pair.of((Point2D) new Point2D.Double(950, 100), "AB=AC"),
                                Pair.of((Point2D) new Point2D.Double(950, 150), "KB=h/(h+0.5)")

                        ).map(drawText()),
//                        Stream.of(
//                                registered
//                        ).map(toCircles.andThen(drawCircles(blueLight))),
                        Stream.of(
                                Pair.of(inner2, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(inner, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        Stream.of(
                                getLines(RATIO_2)
                        ).map(toStar.andThen(toPolylines(red))),
                        Stream.of(
                                asList(
                                        initialConditions.getLeft(),
                                        toVertex.apply(Pair.of(inner2, Hex.Vertex.SIX)),
                                        toVertex.apply(Pair.of(inner2Reg, Hex.Vertex.ONE))
                                )
                        ).map(highlightPoints())

                ).flatMap(s -> s).collect(joining());

    }

    public String design3() {
        String black = newStyle("black", 1, 1);
        String blue = newStyle("blue", 1, 1);
        String blueLight = newStyle("blue", 1, 0.3);
        String gray = newStyle("gray", 1, 1);
        String green = newStyle("green", 1, 1);
        String red = newStyle("red", 2, 1);

        List<Point2D> hexGrid = HexGrid.grid(initialConditions.getLeft(), initialConditions.getRight() / 4.0, HexGrid.TYPE.VER, 12);

        Polygon main = Hex.hex(1, Polygon.Type.VER);
        Polygon registered = main.getRegistered();
        Polygon outer = Hex.hex(1, Polygon.Type.VER, Polygon.centreTransform(2 * HEIGHT_RATIO, Hex.Vertex.FIVE, Polygon.Type.HOR));
        Polygon outerSmall = Hex.hex(HEIGHT_RATIO - 0.5, Polygon.Type.VER, Polygon.centreTransform(1, Hex.Vertex.FIVE, Polygon.Type.VER));
        Polygon outerSmall2 = Hex.hex((HEIGHT_RATIO - 0.5) * 0.5, Polygon.Type.VER, Polygon.centreTransform(1, Hex.Vertex.FIVE, Polygon.Type.VER));
        Polygon outerSmall3 = Hex.hex(0.5, Polygon.Type.VER, Polygon.centreTransform(1, Hex.Vertex.FIVE, Polygon.Type.VER));
        Polygon outerSmall4 = Hex.hex(RATIO_3, Polygon.Type.VER, Polygon.centreTransform(HEIGHT_RATIO, Hex.Vertex.FIVE, Polygon.Type.HOR));
        Polygon outerSmall5 = Hex.hex(RATIO_3, Polygon.Type.VER, Polygon.centreTransform(HEIGHT_RATIO, Hex.Vertex.TWO, Polygon.Type.HOR));

        Polygon inner = Hex.hex(HEIGHT_RATIO * 0.5 + HEIGHT_RATIO * HEIGHT_RATIO, Polygon.Type.HOR, Polygon.centreTransform(RATIO_3, Hex.Vertex.ONE, Polygon.Type.VER));
        Polygon outerBig = Hex.hex(2, Polygon.Type.VER, Polygon.centreTransform(HEIGHT_RATIO, Hex.Vertex.ONE, Polygon.Type.HOR));

        Point2D pointA = toVertex.apply(Pair.of(main, Hex.Vertex.FIVE));
        Point2D pointB = toVertex.apply(Pair.of(outerSmall, Hex.Vertex.FOUR));
        Point2D pointC = toVertex.apply(Pair.of(outerSmall2, Hex.Vertex.THREE));
        Point2D pointD = toVertex.apply(Pair.of(outerSmall3, Hex.Vertex.THREE));
        Point2D pointE = toVertex.apply(Pair.of(outerSmall4, Hex.Vertex.THREE));
        Point2D pointF = toVertex.apply(Pair.of(outerSmall5, Hex.Vertex.THREE));
        Point2D pointG = toVertex.apply(Pair.of(outerSmall5, Hex.Vertex.SIX));

        return
                Stream.of(
                        Stream.of(
                                highlightPoints("black", 2).apply(hexGrid)
                        ),
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(main, Hex.DIAGONALS)
//                                Pair.of(outer, Hex.DIAGONALS)
                        ).map(toLines.andThen(toPolylines(gray))),
                        Stream.of(
                                registered
                        ).map(toCircles.andThen(drawCircles(blueLight))),
                        Stream.of(
                                Pair.of(registered, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                asList(
                                        asList(pointA, pointB),
                                        asList(pointD, pointB),
                                        asList(pointD, pointC),
                                        asList(pointC, pointB)
                                )

                        ).map(toPolylines(blue)),
                        Stream.of(
                                Pair.of(inner, asList(Hex.Diag.THREE.getVertexes()))
//                                Pair.of(outerBig, asList(asList((Polygon.Vertex) Hex.Vertex.TWO, Hex.Vertex.FIVE)))
                        ).map(toLinesFull.andThen(toPolylines(blue))),
                        Stream.of(
                                Pair.of(outerBig, asList(Hex.Diag.TWO.getVertexes()))
                        ).map(toLinesFull.andThen(toPolylines(gray))),
                        Stream.of(
                                Pair.of(initialConditions.getLeft(), "K"),
                                Pair.of(pointA, "A"),
                                Pair.of(pointB, "B"),
                                Pair.of(pointC, "C"),
                                Pair.of(pointD, "D"),
                                Pair.of(pointE, "E"),
                                Pair.of(pointF, "F"),
                                Pair.of(pointG, "G"),
                                Pair.of((Point2D) new Point2D.Double(950, 150), "DC = 0.5 - (h-0.5)*0.5")

                        ).map(drawText()),
//                        Stream.of(
//                                Pair.of(inner, Hex.PERIMETER)
//                        ).map(toLines.andThen(toPolylines(blue))),
                        Stream.of(
                                asList(
                                        pointA,
                                        pointB,
                                        pointC,
                                        pointD,
                                        pointE,
                                        pointF,
                                        pointG
                                )
                        ).map(highlightPoints()),
                        Stream.of(
                                getLines(RATIO_3)
                        ).map(toStar.andThen(toPolylines(red)))

                ).flatMap(s -> s).collect(joining());

    }

}
