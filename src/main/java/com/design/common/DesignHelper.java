package com.design.common;

import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.view.SvgFactory.*;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class DesignHelper {
    private List<Pair<List<Pair<Polygon, List<List<Polygon.Vertex>>>>, String>> linesInstructions = new ArrayList<>();
    private List<Pair<List<Pair<Polygon, Polygon.Vertex>>, String>> mixedLinesInstructions = new ArrayList<>();
    private List<Pair<List<Pair<Polygon, Polygon.Vertex>>, String>> singleLinesInstructions = new ArrayList<>();
    private Grid.Configuration gridConfig;
    private List<Pair<Point2D, String>> importantPoints = new ArrayList<>();
    private List<Triple<Polygon, ? extends Polygon.Vertex, String>> importantVertexes = new ArrayList<>();
    private List<Pair<List<Polygon>, String>> circlePolygons = new ArrayList<>();
    private List<Pair<List<Pair<Polygon, Double>>, String>> circlePolygonsWithRadius = new ArrayList<>();
    //    protected Function<Triple<Polygon, ? extends Polygon.Vertex, String>, Pair<Point2D, String>> importantPoint;
    protected static Function<Polygon, List<Triple<Polygon, Polygon.Vertex, String>>> allVertexesAsImportant =
            polygon -> {
                AtomicInteger index = new AtomicInteger();
                return polygon.getVertexes().stream().map(v -> Triple.of(polygon, v, String.valueOf(index.incrementAndGet()))).collect(toList());

            };

    private int fontSize = 18;
    private final String name;

    private final List<Integer> allVertexIndexes;

    public DesignHelper(List<Integer> allVertexIndexes, String name) {
        this.allVertexIndexes = allVertexIndexes;
        this.name = name;
    }

    public DesignHelper addLinesInstructions(List<Pair<Polygon, List<List<Polygon.Vertex>>>> instructions, String style) {
        linesInstructions.add(Pair.of(instructions, style));
        return this;
    }

    public DesignHelper addMixedLinesInstructionsList(List<List<Pair<Polygon, Polygon.Vertex>>> instructions, String style) {
        instructions.stream().forEach(i -> mixedLinesInstructions.add(Pair.of(i, style)));
        return this;
    }

    public DesignHelper addSingleLinesInstructionsList(List<List<Pair<Polygon, Polygon.Vertex>>> instructions, String style) {
        instructions.stream().forEach(i -> singleLinesInstructions.add(Pair.of(i, style)));
        return this;
    }

    public DesignHelper addMixedLinesInstructions(List<Pair<Polygon, Polygon.Vertex>> instructions, String style) {
        mixedLinesInstructions.add(Pair.of(instructions, style));
        return this;
    }

    public DesignHelper withGrid(Grid.Configuration gridConfig) {
        this.gridConfig = gridConfig;
        return this;
    }

    public DesignHelper withFontSize(int fontSize){
        this.fontSize=fontSize;
        return this;
    }
    public DesignHelper addCircle(List<Polygon> circles, String style) {
        circlePolygons.add(Pair.of(circles, style));
        return this;
    }

    public DesignHelper addCircleWithRadius(List<Pair<Polygon, Double>> circles, String style) {
        circlePolygonsWithRadius.add(Pair.of(circles, style));
        return this;
    }

    public DesignHelper addImportantPoint(Pair<Point2D, String> point) {
        importantPoints.add(point);
        return this;
    }

    public DesignHelper addAllVertexesAsImportantPoints(List<Polygon> polygons) {

        polygons.stream().forEach(
                polygon -> {
                    AtomicInteger index = new AtomicInteger();
                    addImportantPoints(polygon.getVertexes().stream().map(v -> Triple.of(polygon, v, String.valueOf(index.incrementAndGet()))).collect(toList()));
                }
        );

        return this;
    }

    public DesignHelper addEquations(List<String> equations) {
        IntStream.range(0, equations.size())
                .forEach(i -> importantPoints.add(Pair.of(new Point2D.Double(1000, (i + 1) * 40), equations.get(i))));
        return this;
    }

    public DesignHelper addImportantPoints(List<Triple<Polygon, ? extends Polygon.Vertex, String>> points) {
        importantVertexes.addAll(points);
        return this;
    }

    public String build(Pair<Point2D, Double> initialConditions) {
        Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLines = Polygon.toLines(0, initialConditions);
        Function<List<Pair<Polygon, Polygon.Vertex>>, List<List<Point2D>>> toMixedLines = Polygon.mixVertexesFull(allVertexIndexes, initialConditions);

        Function<Pair<Polygon, Polygon.Vertex>, Point2D> toVertex = Polygon.vertex(initialConditions);

        Function<Triple<Polygon, ? extends Polygon.Vertex, String>, Pair<Point2D, String>> importantPoint = t -> Pair.of(toVertex.apply(Pair.of(t.getLeft(), t.getMiddle())), t.getRight());

        Function<Polygon, List<Pair<Point2D, Double>>> toCircle = Polygon.toCircles(initialConditions);
        Function<Pair<Polygon, Double>, List<Pair<Point2D, Double>>> toCirclesWithRadius = Polygon.toCirclesWithRadius(initialConditions);

        importantPoints.add(Pair.of(initialConditions.getLeft(), "K"));

        importantPoints.addAll(importantVertexes.stream().map(importantPoint).collect(toList()));

        List<Point2D> gridPoints = new ArrayList<>();
        if (gridConfig != null) {
            gridPoints = Grid.grid(initialConditions.getLeft(), initialConditions.getRight() / 4.0, gridConfig, 12);

        }
//        singleLinesInstructions.stream().map(p->Mappings.fromListOfLists(toVertex).apply(p.getLeft())).
//        Mappings.fromListOfLists(toVertex).apply(singleLinesInstructions.);

        return Stream.of(
                linesInstructions.stream().map(p ->
                                p.getLeft().stream().map(toLines.andThen(toPolylines(p.getRight())))
                ).flatMap(s -> s),
                mixedLinesInstructions.stream().map(p ->
                                toPolylines(p.getRight()).apply(toMixedLines.apply(p.getLeft()))
                ),
                singleLinesInstructions.stream().map(
                        p ->
                                toPolyline(p.getRight()).apply(Mappings.fromList(toVertex).apply(p.getLeft()) )
                ),
                circlePolygons.stream().map(p ->
                                p.getLeft().stream().map(toCircle.andThen(drawCircles(p.getRight())))
                ).flatMap(s -> s),
                circlePolygonsWithRadius.stream().map(p ->
                                p.getLeft().stream().map(toCirclesWithRadius.andThen(drawCircles(p.getRight())))
                ).flatMap(s -> s),
                Stream.of(highlightPoints("black", 2).apply(gridPoints)),
                importantPoints.stream().map(drawText(fontSize)),
                importantPoints.stream().map(Pair::getLeft).map(highlightPoint())

        ).flatMap(s -> s).collect(joining());

    }

    public String getName() {
        return name;

    }

}
