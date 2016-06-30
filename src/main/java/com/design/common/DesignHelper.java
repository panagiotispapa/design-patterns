package com.design.common;

import com.design.common.Polygon.VertexPaths;
import com.design.common.model.Circle;
import com.design.common.model.Path;
import com.design.common.model.Path.Paths;
import com.design.common.model.Style;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.GenericTools.flatMapLists;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class DesignHelper {
    private Grid.Configuration gridConfig;
    private List<Pair<Point2D, String>> importantPoints = new ArrayList<>();
    private List<ImportantVertex> importantVertexes = new ArrayList<>();
    private List<Pair<List<Polygon>, String>> circlePolygons = new ArrayList<>();
    private List<Pair<List<Pair<Polygon, Double>>, Style>> circlePolygonsWithRadius = new ArrayList<>();
    private List<Pair<List<Double>, Style>> circlesCentral = new ArrayList<>();

    private List<Path> singlePaths = new ArrayList<>();
    private List<Path> fullPaths = new ArrayList<>();

    //    protected Function<Triple<Polygon, ? extends Polygon.Vertex, String>, Pair<Point2D, String>> importantPoint;
    protected static Function<Polygon, List<ImportantVertex>> allVertexesAsImportant =
            polygon -> {
                AtomicInteger index = new AtomicInteger();
                return polygon.getVertexes().stream().map(v -> ImportantVertex.of(polygon, v, String.valueOf(index.incrementAndGet()))).collect(toList());

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
                    addImportantVertexes(polygon.getVertexes().stream().map(v -> ImportantVertex.of(polygon, v, String.valueOf(index.incrementAndGet()))).collect(toList()));
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

    public DesignHelper addImportantVertexes(List<ImportantVertex> importantVertices) {
        importantVertexes.addAll(importantVertices);
        return this;
    }

    public DesignHelper addImportantVertexes(ImportantVertex... importantVertices) {
        Stream.of(importantVertices).forEach(importantVertexes::add);
        return this;
    }

    public DesignHelper addFullPath(Path path) {
        fullPaths.add(path);
        return this;
    }

    public DesignHelper addFullPaths(Paths paths) {
        fullPaths.addAll(paths.get());
        return this;
    }

    public DesignHelper addFullPaths(VertexPaths lines, Style style) {
        return addFullPaths(Path.vertexPathsToPaths.apply(lines.get()), style);
    }

    public DesignHelper addFullPaths(Paths paths, Style style) {
        fullPaths.addAll(paths.get().stream().map(Path.fromPathWithStyle(style)).collect(toList()));
        return this;
    }

    public DesignHelper addSinglePaths(List<Polygon> polygons, Function<Polygon, Paths> mapper) {
        return addSinglePaths(Paths.of(flatMapLists(mapper.andThen(Supplier::get)).apply(polygons)));
    }

    public DesignHelper addSinglePaths(List<Polygon> polygons, Function<Polygon, Paths> mapper, Style style) {
        return addSinglePaths(Paths.of(flatMapLists(mapper.andThen(Supplier::get)).apply(polygons)), style);
    }

    public DesignHelper addSinglePaths(List<Pair<Polygon, Function<Polygon, Paths>>> instructions, Style style) {
        instructions.forEach(i -> addSinglePaths(() -> i.getRight().apply(i.getLeft()).get(), style));
        return this;
    }

    public DesignHelper addSinglePaths(Paths paths) {
        singlePaths.addAll(paths.get());
        return this;
    }

    public DesignHelper addSinglePaths(VertexPaths lines, Style style) {
        return addSinglePaths(Path.vertexPathsToPaths.apply(lines.get()), style);
    }

    public DesignHelper addSinglePaths(Paths paths, Style style) {
        singlePaths.addAll(paths.get().stream().map(Path.fromPathWithStyle(style)).collect(toList()));
        return this;
    }

//    public DesignHelper addSinglePaths(List<Path>... paths) {
//        Stream.of(paths).forEach(singlePaths::addAll);
//        return this;
//    }

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

    public String build(Polygon.InitialConditions initialConditions) {

        Function<Polygon.ActualVertex, Point2D> toVertex = Polygon.vertex(initialConditions);

        Function<ImportantVertex, Pair<Point2D, String>> importantPoint = t -> Pair.of(toVertex.apply(() -> Pair.of(t.get().getLeft(), t.get().getMiddle())), t.get().getRight());

        Function<Polygon, List<Circle>> toCircle = Polygon.toCircles(initialConditions);
        Function<Pair<Polygon, Double>, List<Circle>> toCirclesWithRadius = Polygon.toCirclesWithRadius(initialConditions);

        importantPoints.add(Pair.of(initialConditions.get().getLeft(), "K"));

        importantPoints.addAll(importantVertexes.stream().map(importantPoint).collect(toList()));

        List<Point2D> gridPoints = new ArrayList<>();
        if (gridConfig != null) {
            gridPoints = Grid.grid(initialConditions.get().getLeft(), initialConditions.get().getRight() / 4.0, gridConfig, 12);

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
                        p.getLeft().stream().map(d -> Circle.of(initialConditions.get().getLeft(), d * initialConditions.get().getRight())).map(drawCircle(p.getRight()))
                ).flatMap(s -> s),
                singlePaths.stream().map(drawPath(toVertex)),
                fullPaths.stream().map(drawPathFull(allVertexIndexes, Polygon.vertexFull(initialConditions))).flatMap(s -> s),
                Stream.of(highlightPoints("black", 2).apply(gridPoints)),
                importantPoints.stream().map(drawText(fontSize)),
                importantPoints.stream().map(Pair::getLeft).map(highlightPoint())


        ).flatMap(s -> s).collect(joining());

    }

    public String getName() {
        return name;

    }


    public interface ImportantVertex extends Supplier<Triple<Polygon, Polygon.Vertex, String>> {
        static ImportantVertex of(Polygon polygon, Polygon.Vertex vertex, String txt) {
            return () -> Triple.of(polygon, vertex, txt);
        }
    }

//    public interface Paths extends Supplier<List<Path>> {
//        static Paths of(List<Path> paths) {
//            return () -> paths;
//        }
//
//        static Paths of(Path... paths) {
//            return of(Arrays.asList(paths));
//        }
//    }

}
