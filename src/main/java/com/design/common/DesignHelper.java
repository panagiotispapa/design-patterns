package com.design.common;

import com.design.common.model.Path;
import com.design.common.model.Style;
import com.googlecode.totallylazy.Sequence;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.design.common.CanvasPoint.point;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.model.Circle.circle;
import static com.design.common.view.SvgFactory.drawText;
import static com.googlecode.totallylazy.Sequences.join;
import static com.googlecode.totallylazy.Sequences.sequence;
import static com.googlecode.totallylazy.numbers.Integers.range;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class DesignHelper {
    private Grid.Configuration gridConfig;
    private Double gridRatio = 1 / 4.0;
    private int gridSize = 12;
    private Sequence<String> equations = sequence();
    private Sequence<ImportantVertex> importantVertexes = sequence();
    private Sequence<Pair<Sequence<CircleInstruction>, Style>> circlePolygonsWithRadius = sequence();
    private Sequence<Pair<Sequence<Double>, Style>> circlesCentral = sequence();

    private Sequence<Path> singlePaths = sequence();

    //    protected Function<Triple<Polygon, ? extends Polygon.Vertex, String>, Pair<Point2D, String>> importantPoint;

    private int fontSize = 18;
    private final String name;

    private final Sequence<Integer> allVertexIndexes;

    public DesignHelper(Sequence<Integer> allVertexIndexes, String name) {
        this.allVertexIndexes = allVertexIndexes;
        this.name = name;
    }


    public DesignHelper withGrid(Grid.Configuration gridConfig) {
        this.gridConfig = gridConfig;
        return this;
    }

    public DesignHelper withGridSize(int gridSize) {
        this.gridSize = gridSize;
        return this;
    }

    public DesignHelper withGridRatio(Double gridRatio) {
        this.gridRatio = gridRatio;
        return this;
    }

    public DesignHelper withFontSize(int fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public DesignHelper addCircleWithRadius(Style style, CircleInstruction... circles) {
        this.circlePolygonsWithRadius = this.circlePolygonsWithRadius.join(sequence(Pair.of(sequence(circles), style)));
        return this;
    }

    public DesignHelper addCirclesCentral(Style style, Double... circles) {
        this.circlesCentral = this.circlesCentral.join(sequence(Pair.of(sequence(circles), style)));
        return this;
    }

    public DesignHelper addEquations(String... equations) {
        return addEquations(sequence(equations));
    }

    public DesignHelper addEquations(Sequence<String>... equations) {
        return addEquations(sequence(equations).flatMap(s -> s));
    }

    public DesignHelper addEquations(Sequence<String> equations) {
        this.equations = equations;
        return this;
    }

    private CanvasPoint calculationEquationPoint(Integer index) {
        return point(1000, (index + 1) * 40);
    }

    public DesignHelper addImportantVertexes(Class klass) {
        addImportantVertexes(sequence(
                Stream.of(klass.getDeclaredFields()).filter(f -> f.getType().getCanonicalName().equals(FinalPointTransition.class.getCanonicalName()))
                        .map(this::tryGetFromField)
                        .filter(Optional::isPresent)
                        .map(Optional::get)
                        .collect(toList())
                )
        );
        return this;
    }

    private Optional<ImportantVertex> tryGetFromField(Field field) {
        try {
            FinalPointTransition f = (FinalPointTransition) field.get(null);
            return Optional.of(ImportantVertex.of(field.getName(), f));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            return empty();
        }
    }

    public DesignHelper addImportantVertexes(ImportantVertex... importantVertices) {
        return addImportantVertexes(sequence(importantVertices));
    }

    public DesignHelper addImportantVertexes(Sequence<ImportantVertex>... importantVertices) {
        return addImportantVertexes(sequence(importantVertices).flatMap(s -> s));
    }

    public DesignHelper addImportantVertexes(Sequence<ImportantVertex> importantVertices) {
        this.importantVertexes = this.importantVertexes.join(importantVertices);
        return this;
    }

    public DesignHelper addSinglePathsLines(Style style, Line... paths) {
        return addSinglePathsLines(style, sequence(paths));
    }

    public DesignHelper addSinglePathsLines(Style style, Sequence<Line>... lines) {
        return addSinglePathsLines(style, sequence(lines).flatMap(s -> s));
    }

    public DesignHelper addSinglePathsLines(Style style, Sequence<Line> lines) {
        this.singlePaths = this.singlePaths.join(lines.map(p -> p.toPath(style)));
        return this;
    }

    public DesignHelper addFullPaths(Style style, Sequence<Line>... paths) {
        return addFullPaths(style, sequence(paths).flatMap(s -> s));
    }

    public DesignHelper addFullPaths(Style style, Line... paths) {
        return addFullPaths(style, sequence(paths));
    }

    public DesignHelper addFullPaths(Style style, Sequence<Line> lines) {
        addSinglePathsLines(style, allVertexIndexes.flatMap(offset -> lines.map(p -> p.withOffset(offset))));
        return this;
    }


    public String build(InitialConditions initialConditions) {

        final CanvasPoint centre = initialConditions.get().getLeft();
        final Double realRatio = initialConditions.get().getRight();

        Sequence<Pair<CanvasPoint, String>> importantPoints =
                join(
                        sequence(Pair.of(centre, "K")),
                        importantVertexes.map(v -> v.toPointWithText(initialConditions)),
                        getEquationsAsPoints()
                );

        List<CanvasPoint> gridPoints = ofNullable(gridConfig).map(g -> Grid.gridFromMiddle(centre, realRatio * gridRatio, g, gridSize)).orElseGet(Collections::emptyList);

        return Stream.of(
                circlePolygonsWithRadius.flatMap(p ->
                        p.getLeft().map(c -> c.toCircle(initialConditions)).map(c -> c.draw(p.getRight()))
                ).stream(),
                circlesCentral.flatMap(p ->
                        p.getLeft().map(d -> circle(centre, d * realRatio)).map(c -> c.draw(p.getRight()))
                ).stream(),
                singlePaths.map(p -> p.draw(initialConditions)).stream(),
                gridPoints.stream().map(p -> p.highlightPoint(Color.BLACK, 2)),
                importantPoints.stream().map(drawText(fontSize)),
                importantPoints.stream().map(Pair::getLeft).map(CanvasPoint::highlightPoint)

        ).flatMap(s -> s).collect(joining());
    }

    private Sequence<Pair<CanvasPoint, String>> getEquationsAsPoints() {
        return range(0).map(this::calculationEquationPoint).zip(equations).map(p -> Pair.of(p.first(), p.second()));
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

        default Pair<CanvasPoint, String> toPointWithText(InitialConditions ic) {
            return Pair.of(getFinalPointTransition().toPoint(ic), getTxt());
        }

        default String getTxt() {
            return get().getRight();
        }
    }

}
