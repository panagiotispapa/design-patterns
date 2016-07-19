package com.design.common;

import com.design.common.model.Circle;
import com.design.common.model.Path;
import com.design.common.model.Style;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.view.SvgFactory.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class DesignHelper {
    private Grid.Configuration gridConfig;
    private List<Pair<Point2D, String>> importantPoints = new ArrayList<>();
    private List<ImportantVertex> importantVertexes = new ArrayList<>();
    private List<Pair<List<Pair<FinalPointTransition, Double>>, Style>> circlePolygonsWithRadius = new ArrayList<>();
    private List<Pair<List<Double>, Style>> circlesCentral = new ArrayList<>();

    private List<Path> singlePaths = new ArrayList<>();

    //    protected Function<Triple<Polygon, ? extends Polygon.Vertex, String>, Pair<Point2D, String>> importantPoint;

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


    public DesignHelper addCircleWithRadius(Style style, Pair<FinalPointTransition, Double>... circles) {
        circlePolygonsWithRadius.add(Pair.of(asList(circles), style));
        return this;
    }

    public DesignHelper addCirclesCentral(List<Double> circles, Style style) {
        circlesCentral.add(Pair.of(circles, style));
        return this;
    }


    public DesignHelper addEquations(String... equations) {
        return addEquations(Stream.of(equations).collect(toList()));
    }

    public DesignHelper addEquations(List<String> equations) {
        IntStream.range(0, equations.size())
                .forEach(i -> importantPoints.add(Pair.of(new Point2D.Double(1000, (i + 1) * 40), equations.get(i))));
        return this;
    }


    public DesignHelper addImportantVertexes(Class klass) {
        Stream.of(klass.getDeclaredFields()).filter(f -> f.getType().getCanonicalName().equals(FinalPointTransition.class.getCanonicalName())).forEach(field -> {
            try {
                FinalPointTransition f = (FinalPointTransition) field.get(null);
                importantVertexes.add(ImportantVertex.of(field.getName(), f));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        });
        return this;
    }

    public DesignHelper addImportantVertexes(ImportantVertex... importantVertices) {
        return addImportantVertexes(Stream.of(importantVertices));
    }

    public DesignHelper addImportantVertexes(Stream<ImportantVertex> importantVertices) {
        importantVertices.forEach(importantVertexes::add);
        return this;
    }


    public DesignHelper addSinglePathsLines(Style style, PointsPath... paths) {
        Stream.of(paths).map(Path.fromPath(style)).forEach(singlePaths::add);
        return this;
    }

    public DesignHelper addSinglePathsLines(Style style, Stream<PointsPath>... lines) {
        Stream.of(lines).flatMap(s -> s).map(Path.fromPath(style)).forEach(singlePaths::add);
        return this;
    }

    public DesignHelper addSinglePathsLines(Style style, List<PointsPath>... lines) {
        Stream.of(lines).map(Collection::stream).flatMap(s -> s).map(Path.fromPath(style)).forEach(singlePaths::add);
        return this;
    }

    public DesignHelper addFullPaths(Style style, Stream<PointsPath>... paths) {
        return addFullPaths(style, Stream.of(paths).flatMap(s -> s).collect(toList()));
    }

    public DesignHelper addFullPaths(Style style, PointsPath... paths) {
        return addFullPaths(style, asList(paths));
    }

    public DesignHelper addFullPaths(Style style, List<PointsPath> paths) {
        allVertexIndexes.stream().flatMap(offset -> paths.stream().map(p -> p.withOffset(offset))).map(Path.fromPath(style)).forEach(singlePaths::add);
        return this;
    }

    public String build(InitialConditions initialConditions) {

        Function<ImportantVertex, Pair<Point2D, String>> importantPoint = v -> Pair.of(v.getFinalPointTransition().toPoint(initialConditions), v.getTxt());

        Function<Pair<FinalPointTransition, Double>, Circle> toCircleWithRadius = Circle.toCircleWithRadius(initialConditions);

        importantPoints.add(Pair.of(initialConditions.get().getLeft(), "K"));

        importantPoints.addAll(importantVertexes.stream().map(importantPoint).collect(toList()));

        List<Point2D> gridPoints = new ArrayList<>();
        if (gridConfig != null) {
            gridPoints = Grid.grid(initialConditions.get().getLeft(), initialConditions.get().getRight() / 4.0, gridConfig, 12);

        }
//        singleLinesInstructions.stream().map(p->Mappings.fromListOfLists(toVertex).apply(p.getLeft())).
//        Mappings.fromListOfLists(toVertex).apply(singleLinesInstructions.);

        return Stream.of(
                circlePolygonsWithRadius.stream().map(p ->
                        p.getLeft().stream().map(toCircleWithRadius.andThen(drawCircle(p.getRight())))
                ).flatMap(s -> s),
//                circlePolygonsWithRadius.stream().map(p ->
//                        p.getLeft().stream().map(toCirclesWithRadius.andThen(drawCircles(p.getRight())))
//                ).flatMap(s -> s),
                circlesCentral.stream().map(p ->
                        p.getLeft().stream().map(d -> Circle.of(initialConditions.get().getLeft(), d * initialConditions.get().getRight())).map(drawCircle(p.getRight()))
                ).flatMap(s -> s),
                singlePaths.stream().map(p -> p.draw(initialConditions)),
                Stream.of(highlightPoints("black", 2).apply(gridPoints)),
                importantPoints.stream().map(drawText(fontSize)),
                importantPoints.stream().map(Pair::getLeft).map(highlightPoint())


        ).flatMap(s -> s).collect(joining());

    }

    public String getName() {
        return name;

    }


    public interface ImportantVertex extends Supplier<Pair<FinalPointTransition, String>> {
        static ImportantVertex of(String txt, PointTransition... pointTransitions) {
            return () -> Pair.of(fpt(pointTransitions), txt);
        }

        static ImportantVertex of(String txt, FinalPointTransition finalPointTransition) {
            return () -> Pair.of(finalPointTransition, txt);
        }

        default FinalPointTransition getFinalPointTransition() {
            return get().getLeft();
        }

        default String getTxt() {
            return get().getRight();
        }
    }

}
