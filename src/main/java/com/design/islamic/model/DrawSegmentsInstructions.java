package com.design.islamic.model;

import com.design.islamic.model.tiles.Hex;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static com.design.islamic.model.tiles.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class DrawSegmentsInstructions {

    public static List<List<Hex.Vertex>> NONE = emptyList();
    public static List<List<Hex.Vertex>> PERIMETER = asList(
            asList(
                    Hex.Vertex.ONE,
                    Hex.Vertex.TWO,
                    Hex.Vertex.THREE,
                    Hex.Vertex.FOUR,
                    Hex.Vertex.FIVE,
                    Hex.Vertex.SIX,
                    Hex.Vertex.ONE
            )
    );

    public static List<List<Hex.Vertex>> INNER_TRIANGLES = asList(
            asList(ONE, THREE, FIVE, ONE),
            asList(TWO, FOUR, SIX, TWO)
    );
    public static List<List<Hex.Vertex>> DIAGONALS = asList(
            asList(ONE, FOUR),
            asList(TWO, FIVE),
            asList(THREE, SIX)
    );

    public static List<List<Hex.Vertex>> ALL_LINES = Lists.newArrayList(
            Iterables.concat(DIAGONALS, INNER_TRIANGLES, PERIMETER)
    );



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

        public List<List<Point2D>> lines(Hex hex, List<List<Hex.Vertex>> instructions) {
            return lines(0, hex, instructions);
        }

        public List<List<Point2D>> lines(int offset, Hex hex, List<List<Hex.Vertex>> instructions) {
            return hex.lines(offset, initialConditions, instructions);
        }

        public List<Point2D> vertexes(Hex hex) {
            return hex.vertexes(initialConditions);
        }

        public List<Point2D> combineVertexes(Hex first, Hex second) {
            return DrawSegmentsInstructions.combineVertexes(initialConditions, first, second);
        }

    }

    public static enum Sides {
        ONE(asList(Hex.Vertex.ONE, Hex.Vertex.TWO)),
        TWO(asList(Hex.Vertex.TWO, Hex.Vertex.THREE)),
        THREE(asList(Hex.Vertex.THREE, Hex.Vertex.FOUR)),
        FOUR(asList(Hex.Vertex.FOUR, Hex.Vertex.FIVE)),
        FIVE(asList(Hex.Vertex.FIVE, Hex.Vertex.SIX)),
        SIX(asList(Hex.Vertex.SIX, Hex.Vertex.ONE));

        private final List<Hex.Vertex> vertexes;

        Sides(List<Hex.Vertex> vertexes) {
            this.vertexes = vertexes;
        }

        public List<Hex.Vertex> getVertexes() {
            return vertexes;
        }
    }

}