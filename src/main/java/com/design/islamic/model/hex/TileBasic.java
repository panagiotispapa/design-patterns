package com.design.islamic.model.hex;

import com.design.common.Mappings;
import com.design.common.Polygon;
import com.design.islamic.model.*;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public abstract class TileBasic implements Tile {

    protected final Pair<Point2D, Double> initialConditions;
    protected Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLines;
    protected Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLinesFull;
    protected Function<Triple<Polygon, Polygon, DrawSegmentsInstructions.CombinedVertexes>, List<List<Point2D>>> toStar;
    protected Function<Triple<Polygon, Polygon, List<Polygon.Vertex>>, List<List<Point2D>>> toStarFull;
    protected Function<Polygon, List<Point2D>> toVertexes;
    protected Function<Pair<Polygon, Polygon.Vertex>, Point2D> toVertex;
    protected Function<Polygon, List<Pair<Point2D, Double>>> toCircles;

    protected TileBasic(Pair<Point2D, Double> initialConditions) {
        this.initialConditions = initialConditions;

        toLines = Polygon.toLines(0, initialConditions);
        toLinesFull = Polygon.toLines(IntStream.range(0, 6).boxed().collect(toList()), initialConditions);
        toStar = DrawSegmentsInstructions.combVertexes(initialConditions);
        toStarFull = DrawSegmentsInstructions.combVertexesFull(initialConditions);
        toVertexes = Polygon.vertexes(initialConditions);
        toCircles = Polygon.toCircles(initialConditions);
        toVertex = Polygon.vertex(initialConditions);
    }

    protected Stream<Pair<Polygon, List<List<Polygon.Vertex>>>> getMainLinesFull() {
        return Stream.empty();
    }

    protected Stream<Pair<Polygon, List<List<Polygon.Vertex>>>> getSecondaryLinesFull() {
        return Stream.empty();
    }

    protected Stream<Pair<Polygon, List<List<Polygon.Vertex>>>> getMainLinesSingle() {
        return Stream.empty();
    }

    protected Stream<Pair<Polygon, List<List<Polygon.Vertex>>>> getSecondaryLinesSingle() {
        return Stream.empty();
    }

    protected Stream<Triple<Polygon, Polygon, DrawSegmentsInstructions.CombinedVertexes>> getMainStars() {
        return Stream.empty();
    }

    protected Stream<Triple<Polygon, Polygon, List<Polygon.Vertex>>> getMainStarsFull() {
        return Stream.empty();
    }

    protected Stream<Triple<Polygon, Polygon, DrawSegmentsInstructions.CombinedVertexes>> getSecondaryStars() {
        return Stream.empty();
    }

    protected static Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform(double ratio, Polygon.Type type) {
        return Polygon.centreTransform(ratio, Hex.Vertex.ONE, type);
    }

    @Override
    public Payload getPayload() {

        return Payloads.payload()
                .lines(
                        Stream.of(
                                getMainLinesFull().map(toLinesFull),
                                getMainLinesSingle().map(toLines),
                                getMainStars().map(toStar),
                                getMainStarsFull().map(toStarFull)
                        ).flatMap(s -> s).map(s -> s.stream()).flatMap(s -> s).collect(toList())

                ).secondaryLines(
                        Stream.of(
                                getSecondaryLinesFull().map(toLinesFull),
                                getSecondaryLinesSingle().map(toLines),
                                getSecondaryStars().map(toStar)
                        ).flatMap(s -> s).map(s -> s.stream()).flatMap(s -> s).collect(Collectors.toList())

                );
    }
}