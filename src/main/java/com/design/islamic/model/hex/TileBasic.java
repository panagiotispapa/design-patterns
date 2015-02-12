package com.design.islamic.model.hex;

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

public abstract class TileBasic implements Tile {

    protected final Pair<Point2D, Double> initialConditions;
    private Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLines;
    private Function<Pair<Polygon, List<List<Polygon.Vertex>>>, List<List<Point2D>>> toLinesFull;
    private Function<Triple<Polygon, Polygon, DrawSegmentsInstructions.CombinedVertexes>, List<List<Point2D>>> toStar;

    protected TileBasic(Pair<Point2D, Double> initialConditions) {
        this.initialConditions = initialConditions;

        toLines = Polygon.toLines(0, initialConditions);
        toLinesFull = Polygon.toLines(IntStream.range(0, 6), initialConditions);
        toStar = DrawSegmentsInstructions.combVertexes(initialConditions);

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

    protected Stream<Triple<Polygon, Polygon, DrawSegmentsInstructions.CombinedVertexes>> getSecondaryStars() {
        return Stream.empty();
    }

    protected static Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform(double ratio, Polygon.Type type) {
        return Polygon.centreTransform(ratio, Hex.Vertex.ONE, type);
    }

    @Override
    public Payload getPayload() {

//        List<List<Point2D>> collect = Stream.of(
//                getMainLinesFull().map(linesFull),
//                getMainLinesSingle().map(lines)
//
//        ).flatMap(s -> s).map(s -> s.stream()).flatMap(s -> s).collect(Collectors.toList());
//

        return Payloads.payload()
                .lines(
                        Stream.of(
                                getMainLinesFull().map(toLinesFull),
                                getMainLinesSingle().map(toLines),
                                getMainStars().map(toStar)
                        ).flatMap(s -> s).map(s -> s.stream()).flatMap(s -> s).collect(Collectors.toList())

                ).secondaryLines(
                        Stream.of(
                                getSecondaryLinesFull().map(toLinesFull),
                                getSecondaryLinesSingle().map(toLines),
                                getSecondaryStars().map(toStar)
                        ).flatMap(s -> s).map(s -> s.stream()).flatMap(s -> s).collect(Collectors.toList())

                );
    }
}