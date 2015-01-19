package com.design.islamic.model;

import com.design.islamic.model.tiles.Hex;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Stream;

import static com.design.islamic.model.tiles.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class DrawSegmentsInstructions {

    public static List<Pair<Hex.Vertex, Hex.Vertex>> NONE = emptyList();
    public static List<Pair<Hex.Vertex, Hex.Vertex>> PERIMETER = asList(
            Sides.ONE.getVertexes(),
            Sides.TWO.getVertexes(),
            Sides.THREE.getVertexes(),
            Sides.FOUR.getVertexes(),
            Sides.FIVE.getVertexes(),
            Sides.SIX.getVertexes()
    );

    public static List<Pair<Hex.Vertex, Hex.Vertex>> DIAGONALS = asList(
            Pair.of(ONE, FOUR),
            Pair.of(TWO, FIVE),
            Pair.of(THREE, SIX)
    );

    public static List<Pair<Hex.Vertex, Hex.Vertex>> DIAGONALS_FULL = Lists.newArrayList(
            Iterables.concat(DIAGONALS, Arrays.asList(
                    Pair.of(ONE, THREE),
                    Pair.of(ONE, FIVE),
                    Pair.of(TWO, FOUR),
                    Pair.of(TWO, SIX),
                    Pair.of(THREE, FIVE),
                    Pair.of(FOUR, SIX)
            ))
    );


    public static List<Line2D> lines(int offset, Pair<Point2D, Double> initialConditions, Hex hex, List<Pair<Hex.Vertex, Hex.Vertex>> instructions) {
        return instructions.stream().map(i -> new Line2D.Double(
                i.getLeft().transform(offset, initialConditions, hex),
                i.getRight().transform(offset, initialConditions, hex)
        )).collect(toList());
    }

    public static List<Point2D> vertexes(Pair<Point2D, Double> initialConditions, Hex hex) {
        return Arrays.stream(Hex.Vertex.values()).map(v -> v.transform(0, initialConditions, hex)).collect(toList());
    }

    public static List<Point2D> combineVertexes(Pair<Point2D, Double> initialConditions, Hex first, Hex second) {
        Stream<Point2D> point2DStream = Arrays.stream(Hex.Vertex.values())
                .flatMap(v -> Stream.of(v.transform(0, initialConditions, second), v.transform(0, initialConditions, first)));
        return point2DStream.collect(toList());
    }

    public static class Builder {

        private final Pair<Point2D, Double> initialConditions;

        public static Builder with(Pair<Point2D, Double> initialConditions) {
            return new Builder(initialConditions);
        }

        private Builder(Pair<Point2D, Double> initialConditions) {
            this.initialConditions = initialConditions;
        }

        public List<Line2D> lines(int offset, Hex hex, List<Pair<Hex.Vertex, Hex.Vertex>> instructions) {
            return DrawSegmentsInstructions.lines(offset, initialConditions, hex, instructions);
        }

        public List<Line2D> lines(Hex hex, List<Pair<Hex.Vertex, Hex.Vertex>> instructions) {
            return lines(0, hex, instructions);
        }

        public List<Point2D> vertexes(Hex hex) {
            return DrawSegmentsInstructions.vertexes(initialConditions, hex);
        }

        public List<Point2D> combineVertexes(Hex first, Hex second) {
            return DrawSegmentsInstructions.combineVertexes(initialConditions, first, second);
        }

    }

    public static enum Sides {
        ONE(Pair.of(Hex.Vertex.ONE, Hex.Vertex.TWO)),
        TWO(Pair.of(Hex.Vertex.TWO, Hex.Vertex.THREE)),
        THREE(Pair.of(Hex.Vertex.THREE, Hex.Vertex.FOUR)),
        FOUR(Pair.of(Hex.Vertex.FOUR, Hex.Vertex.FIVE)),
        FIVE(Pair.of(Hex.Vertex.FIVE, Hex.Vertex.SIX)),
        SIX(Pair.of(Hex.Vertex.SIX, Hex.Vertex.ONE));

        private final Pair<Hex.Vertex, Hex.Vertex> vertexes;

        Sides(Pair<Hex.Vertex, Hex.Vertex> vertexes) {
            this.vertexes = vertexes;
        }

        public Pair<Hex.Vertex, Hex.Vertex> getVertexes() {
            return vertexes;
        }
    }

}