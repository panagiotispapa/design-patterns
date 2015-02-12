package com.design.islamic.model;

import com.design.common.Mappings;
import com.design.common.Polygon;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;

public class DrawSegmentsInstructions {

    public static enum CombinedVertexes {
        STAR(asList(0, 1)),
        OUTER_VERTEXES(asList(1, 4)),;

        private final List<Integer> instructions;

        private CombinedVertexes(List<Integer> instructions) {
            this.instructions = instructions;
        }

        public List<Integer> getInstructions() {
            return instructions;
        }
    }

    public static Function<Triple<Polygon, Polygon, CombinedVertexes>, List<List<Point2D>>> combVertexes(Pair<Point2D, Double> initialConditions) {
        Function<Polygon, List<Point2D>> vertexes = Polygon.vertexes(initialConditions);
        return t -> Mappings.<Point2D>combine(t.getRight().getInstructions()).apply(Pair.of(vertexes.apply(t.getLeft()), vertexes.apply(t.getMiddle())));
    }

}