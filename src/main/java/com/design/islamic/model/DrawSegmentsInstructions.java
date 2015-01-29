package com.design.islamic.model;

import com.design.islamic.model.tiles.Hex;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static com.design.islamic.model.tiles.Hex.Vertex.*;
import static com.google.common.collect.Lists.newArrayList;
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

    public static List<List<Hex.Vertex>> ALL_LINES = newArrayList(
            Iterables.concat(DIAGONALS, INNER_TRIANGLES, PERIMETER)
    );

    public static enum CombinedVertexes {
        STAR(asList(0, 1)),
        OUTER_VERTEXES(asList(1, 4)),

        ;

        private final List<Integer> instructions;

        private CombinedVertexes(List<Integer> instructions) {
            this.instructions = instructions;
        }

        public List<Integer> getInstructions() {
            return instructions;
        }
    }

    public static Function<Hex.Vertex, List<Point2D>> connectVertexes(Pair<Point2D, Double> initialConditions, Pair<Hex, Integer> first, Pair<Hex, Integer> second) {
        return v -> asList(
                Hex.Vertex.mapToPoint(initialConditions, first).apply(v),
                Hex.Vertex.mapToPoint(initialConditions, second).apply(v)
        );
    }

    public static Function<Triple<Hex, Hex, CombinedVertexes>, List<List<Point2D>>> combineVertexes(Pair<Point2D, Double> initialConditions) {
        return tr -> tr.getRight().getInstructions().stream().flatMap(
                i -> Hex.Vertex.ALL.stream().map(v -> connectVertexes(initialConditions, Pair.of(tr.getLeft(), 0), Pair.of(tr.getMiddle(), i)).apply(v))
        ).collect(toList());

    }

    public static List<List<Point2D>> combineOuterVertexes2(Pair<Point2D, Double> initialConditions, Hex first, Hex second) {
        return
                Arrays.stream(Hex.Vertex.values()).flatMap(v -> Stream.of(
                        connectVertexes(initialConditions, Pair.of(first, 0), Pair.of(second, 1)).apply(v),
                        connectVertexes(initialConditions, Pair.of(second, 0), Pair.of(first, 2)).apply(v)
                )).collect(toList());

    }

    public static List<List<Point2D>> combineOuterVertexes(Pair<Point2D, Double> initialConditions, Hex first, Hex second) {

        Stream<List<Point2D>> map = Arrays.stream(Hex.Vertex.values()).flatMap(v -> Stream.of(
                asList(v.transform(0, initialConditions, first), v.transform(1, initialConditions, second)),
                asList(v.transform(0, initialConditions, second), v.transform(2, initialConditions, first))
        ));
        return
                map.collect(toList());
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