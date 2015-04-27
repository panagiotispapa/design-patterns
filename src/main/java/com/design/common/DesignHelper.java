package com.design.common;

import com.design.common.model.Path;
import com.design.common.model.Style;
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
    private Grid.Configuration gridConfig;
    private List<Pair<Point2D, String>> importantPoints = new ArrayList<>();
    private List<Triple<Polygon, Polygon.Vertex, String>> importantVertexes = new ArrayList<>();
    private List<Pair<List<Polygon>, String>> circlePolygons = new ArrayList<>();
    private List<Pair<List<Pair<Polygon, Double>>, Style>> circlePolygonsWithRadius = new ArrayList<>();
    private List<Pair<List<Double>, Style>> circlesCentral = new ArrayList<>();

    private List<Path> singlePaths = new ArrayList<>();
    private List<Path> fullPaths = new ArrayList<>();

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


    public DesignHelper withGrid(Grid.Configuration gridConfig) {
        this.gridConfig = gridConfig;
        return this;
    }

    public DesignHelper withFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public DesignHelper addCircle(List<Polygon> circles, String style) {
        circlePolygons.add(Pair.of(circles, style));
        return this;
    }

    public DesignHelper addCircleWithRadius(List<Pair<Polygon, Double>> circles, Style style) {
        circlePolygonsWithRadius.add(Pair.of(circles, style));
        return this;
    }

    public DesignHelper addCirclesCentral(List<Double> circles, Style style) {
        circlesCentral.add(Pair.of(circles, style));
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

//    public DesignHelper addImportantPoints(List<Pair<Point2D, String>> importantPoints) {
//        this.importantPoints.addAll(importantPoints);
//        return this;
//    }
    
    public DesignHelper addEquations(List<String> equations) {
        IntStream.range(0, equations.size())
                .forEach(i -> importantPoints.add(Pair.of(new Point2D.Double(1000, (i + 1) * 40), equations.get(i))));
        return this;
    }

    public DesignHelper addImportantPoints(List<Triple<Polygon, Polygon.Vertex, String>> points) {
        importantVertexes.addAll(points);
        return this;
    }

    public DesignHelper addFullPath(Path path) {
        fullPaths.add(path);
        return this;
    }

    public DesignHelper addFullPaths(List<Path> paths) {
        fullPaths.addAll(paths);
        return this;
    }

    public DesignHelper addFullPathsFromLines(List<List<Pair<Polygon, Polygon.Vertex>>> lines, Style style) {
        return addFullPaths(Path.pairsToPaths.apply(lines), style);
    }

    public DesignHelper addFullPaths(List<Path> paths, Style style) {
        paths.forEach(p -> p.setStyle(style));
        fullPaths.addAll(paths);
        return this;
    }

    public DesignHelper addSinglePaths(List<Polygon> polygons, Function<Polygon, List<Path>> mapper) {
        return addSinglePaths(polygons.stream().map(mapper).map(List::stream).flatMap(s -> s).collect(toList()));
    }

    public DesignHelper addSinglePaths(List<Polygon> polygons, Function<Polygon, List<Path>> mapper, Style style) {
        return addSinglePathsList(polygons.stream().map(mapper).map(List::stream).flatMap(s -> s).collect(toList()), style);
    }

    public DesignHelper addSinglePaths(List<Pair<Polygon, Function<Polygon, List<Path>>>> instructions, Style style) {
        instructions.forEach(i -> addSinglePathsList(i.getRight().apply(i.getLeft()), style));
        return this;
    }

    public DesignHelper addSinglePaths(List<Path> paths) {
        singlePaths.addAll(paths);
        return this;
    }

    public DesignHelper addSinglePathsFromLines(List<List<Pair<Polygon, Polygon.Vertex>>> lines, Style style) {
        return addSinglePathsList(Path.pairsToPaths.apply(lines), style);
    }

    public DesignHelper addSinglePathsList(List<Path> paths, Style style) {
        paths.forEach(p -> p.setStyle(style));
        singlePaths.addAll(paths);
        return this;
    }

    public DesignHelper addSinglePaths(List<Path>... paths) {
        Stream.of(paths).forEach(singlePaths::addAll);
        return this;
    }

    public DesignHelper addSinglePath(Path path) {
        singlePaths.add(path);
        return this;
    }

//    public List<Path> getSinglePaths() {
//        return singlePaths;
//    }
//
//    public List<Path> getFullPaths() {
//        return fullPaths;
//    }
//
//    public List<Pair<Point2D, String>> getImportantPoints() {
//        return importantPoints;
//    }

    public String build(Pair<Point2D, Double> initialConditions) {

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
                circlePolygons.stream().map(p ->
                                p.getLeft().stream().map(toCircle.andThen(drawCircles(p.getRight())))
                ).flatMap(s -> s),
                circlePolygonsWithRadius.stream().map(p ->
                                p.getLeft().stream().map(toCirclesWithRadius.andThen(drawCircles(p.getRight())))
                ).flatMap(s -> s),
                circlesCentral.stream().map(p ->
                                p.getLeft().stream().map(d -> Pair.of(initialConditions.getLeft(), d * initialConditions.getRight())).map(drawCircle(p.getRight()))
                ).flatMap(s -> s),
                singlePaths.stream().map(drawPath(toVertex)),
                fullPaths.stream().map(drawPathFull(allVertexIndexes, Polygon.vertexFull2(initialConditions))).flatMap(s -> s),
                Stream.of(highlightPoints("black", 2).apply(gridPoints)),
                importantPoints.stream().map(drawText(fontSize)),
                importantPoints.stream().map(Pair::getLeft).map(highlightPoint())


        ).flatMap(s -> s).collect(joining());

    }

    public String getName() {
        return name;

    }

}
