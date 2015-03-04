package com.design.islamic.model.hex;

import com.design.common.Polygon;
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
import static java.util.stream.Collectors.toList;

public class TileStar extends TileBasic {

    public static double RATIO_1 = 0.5;
    public static double RATIO_2 = HEIGHT_RATIO / (HEIGHT_RATIO + 0.5);
    public static double RATIO_3 = (1.5 - HEIGHT_RATIO) * 0.5;
    private final double ratio;

    public TileStar(Pair<Point2D, Double> initialConditions, double ratio) {

        super(initialConditions);

        this.ratio = ratio;
    }

    public static List<Pair<Polygon, Polygon.Vertex>> getLines(double ratio) {
        Polygon main = Hex.hex(HEIGHT_RATIO, Polygon.Type.HOR);
        Polygon inner = Hex.hex(ratio, Polygon.Type.VER);

        return
                asList(
                        Pair.of(inner, Hex.Vertex.ONE),
                        Pair.of(main, Hex.Vertex.TWO),
                        Pair.of(inner, Hex.Vertex.TWO)
                );
    }

    @Override
    protected Stream<List<Pair<Polygon, Polygon.Vertex>>> getMainMixVertexesFull() {
        return
                Stream.of(
                        getLines(ratio)
                );
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
                        ).map(toMixVertexesFull.andThen(toPolylines(red)))

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

        List<Pair<Point2D, String>> importantPoints = Stream.of(
                Triple.of(inner2, Hex.Vertex.SIX, "B"),
                Triple.of(inner2Reg, Hex.Vertex.ONE, "A"),
                Triple.of(registered, Hex.Vertex.ONE, "C")
        ).map(importantPoint).collect(toList());

        importantPoints.add(Pair.of(initialConditions.getLeft(), "K"));
        importantPoints.add(Pair.of(new Point2D.Double(1000, 100), "AB=AC"));
        importantPoints.add(Pair.of(new Point2D.Double(1000, 150), "KB=h/(h+0.5)"));

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

//                        Stream.of(
//                                registered
//                        ).map(toCircles.andThen(drawCircles(blueLight))),
                        Stream.of(
                                Pair.of(inner2, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(inner, Hex.PERIMETER)
                        ).map(toLines.andThen(toPolylines(blue))),
                        importantPoints.stream().map(drawText()),
                        importantPoints.stream().map(Pair::getLeft).map(highlightPoint()),
                        Stream.of(
                                getLines(RATIO_2)
                        ).map(toMixVertexesFull.andThen(toPolylines(red)))

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
        Polygon outerSmall = Hex.hex(1.5 - HEIGHT_RATIO, Polygon.Type.VER, centreTransform(2 * HEIGHT_RATIO, Polygon.Type.HOR));

        Polygon inner = Hex.hex(HEIGHT_RATIO * 0.5 + HEIGHT_RATIO * HEIGHT_RATIO, Polygon.Type.HOR, Polygon.centreTransform(RATIO_3, Hex.Vertex.ONE, Polygon.Type.VER));
        Polygon outerBig = Hex.hex(2, Polygon.Type.VER, Polygon.centreTransform(HEIGHT_RATIO, Hex.Vertex.ONE, Polygon.Type.HOR));

        List<Pair<Point2D, String>> importantPoints = Stream.of(
                Triple.of(main, Hex.Vertex.ONE, "A"),
                Triple.of(outerSmall, Hex.Vertex.THREE, "B"),
                Triple.of(outerSmall, Hex.Vertex.FOUR, "C"),
                Triple.of(registered, Hex.Vertex.ONE, "D"),
                Triple.of(registered, Hex.Vertex.TWO, "E")
        ).map(importantPoint).collect(toList());

        importantPoints.add(Pair.of(initialConditions.getLeft(), "K"));
        importantPoints.add(Pair.of(new Point2D.Double(1000, 50), "KE = EB = h"));
        importantPoints.add(Pair.of(new Point2D.Double(1000, 100), "AB = h-0.5"));
        importantPoints.add(Pair.of(new Point2D.Double(1000, 150), "BC = 1-AB = 1.5-h"));

        return
                Stream.of(
                        Stream.of(
                                highlightPoints("black", 2).apply(hexGrid)
                        ),
                        Stream.of(
                                Pair.of(main, Hex.PERIMETER),
                                Pair.of(outerSmall, Hex.PERIMETER),
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
                                Triple.of(registered, outerSmall, asList((Polygon.Vertex) Hex.Vertex.THREE, Hex.Vertex.FOUR))
                        ).map(toStarFull.andThen(toPolylines(green))),
                        Stream.of(
                                Pair.of(inner, asList(Hex.Diag.THREE.getVertexes()))
//                                Pair.of(outerBig, asList(asList((Polygon.Vertex) Hex.Vertex.TWO, Hex.Vertex.FIVE)))
                        ).map(toLinesFull.andThen(toPolylines(blue))),
                        Stream.of(
                                Pair.of(outerBig, asList(Hex.Diag.TWO.getVertexes()))
                        ).map(toLinesFull.andThen(toPolylines(gray))),
                        importantPoints.stream().map(drawText()),
                        importantPoints.stream().map(Pair::getLeft).map(highlightPoint()),
//                        Stream.of(
//                                Pair.of(Hex.hex(RATIO_3, Polygon.Type.VER), Hex.PERIMETER)
//                        ).map(toLines.andThen(toPolylines(red))),
                        Stream.of(
                                getLines(RATIO_3)
                        ).map(toMixVertexesFull.andThen(toPolylines(red)))

                ).flatMap(s -> s).collect(joining());

    }

}