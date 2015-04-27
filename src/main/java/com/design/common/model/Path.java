package com.design.common.model;

import com.design.common.Polygon;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiFunction;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class Path {

    private final List<Pair<Pair<Polygon, Polygon.Vertex>, InstructionType>> instructions;

    private final boolean closed;
    private Style style;


    public Path(List<Pair<Pair<Polygon, Polygon.Vertex>, InstructionType>> instructions, boolean closed, Style style) {
        this.instructions = instructions;
        this.closed = closed;
        this.style = style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public List<Pair<Pair<Polygon, Polygon.Vertex>, InstructionType>> getInstructions() {
        return instructions;
    }

    public boolean isClosed() {
        return closed;
    }

    public Style getStyle() {
        return style;
    }

    public static enum InstructionType {
        STARTING_POINT,
        LINE,
        ARC_LARGER,
        ARC_SMALLER
    }

    public static Function<List<Pair<Polygon, Polygon.Vertex>>, Path> justLinesPath =
            l -> new Path.Builder().justLines(l).build();

    public static BiFunction<Polygon, List<Polygon.Vertex>, List<Pair<Polygon, Polygon.Vertex>>> toPairs =
            (p, vertexes) -> vertexes.stream().map(v -> Pair.of(p, v)).collect(toList());


    public static Function<List<List<Polygon.Vertex>>, Function<Polygon, List<Path>>> fromListOfVertexes =
            vertexes -> p -> vertexes.stream().map(l -> toPairs.apply(p, l)).map(justLinesPath).collect(toList());


    public static Function<List<List<Pair<Polygon, Polygon.Vertex>>>, List<Path>> pairsToPaths =
            pairs -> pairs.stream().map(justLinesPath).collect(toList());


    public static class Builder {

        private boolean closed = false;
        private List<Pair<Pair<Polygon, Polygon.Vertex>, InstructionType>> instructions = new ArrayList<>();
        private Style style;

        public Builder closed() {
            closed = true;
            return this;
        }

        public Builder justLines(List<Pair<Polygon, Polygon.Vertex>> instructions) {
            AtomicInteger counter = new AtomicInteger(0);
            instructions.stream().forEach(p -> {

                if (counter.getAndIncrement() == 0) {
                    startWith(p);
                } else {
                    lineTo(p);
                }
            });
            return this;
        }

        public Builder startWith(Pair<Polygon, Polygon.Vertex> instruction) {
            instructions.add(Pair.of(instruction, InstructionType.STARTING_POINT));
            return this;
        }

        public Builder startWith(Polygon polygon, Polygon.Vertex vertex) {
            instructions.add(Pair.of(Pair.of(polygon, vertex), InstructionType.STARTING_POINT));
            return this;
        }

        public Builder lineTo(Pair<Polygon, Polygon.Vertex> instruction) {
            instructions.add(Pair.of(instruction, InstructionType.LINE));
            return this;
        }

        public Builder lineTo(Polygon polygon, Polygon.Vertex vertex) {
            instructions.add(Pair.of(Pair.of(polygon, vertex), InstructionType.LINE));
            return this;
        }

        public Builder arcLargerTo(Pair<Polygon, Polygon.Vertex> instruction) {
            instructions.add(Pair.of(instruction, InstructionType.ARC_LARGER));
            return this;
        }

        public Builder arcLargerTo(Polygon polygon, Polygon.Vertex vertex) {
            instructions.add(Pair.of(Pair.of(polygon, vertex), InstructionType.ARC_LARGER));
            return this;
        }

        public Builder arcSmallerTo(Pair<Polygon, Polygon.Vertex> instruction) {
            instructions.add(Pair.of(instruction, InstructionType.ARC_SMALLER));
            return this;
        }

        public Builder arcSmallerTo(Polygon polygon, Polygon.Vertex vertex) {
            instructions.add(Pair.of(Pair.of(polygon, vertex), InstructionType.ARC_SMALLER));
            return this;
        }


        public Builder withStyle(Style style) {
            this.style = style;
            return this;
        }

        public Path build() {
            return new Path(instructions, closed, style);
        }
    }

}
