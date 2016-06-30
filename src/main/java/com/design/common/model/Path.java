package com.design.common.model;

import com.design.common.Polygon;
import com.design.islamic.GenericTools;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Supplier;

public class Path {

    private final List<Instruction> instructions;

    private final boolean closed;
    private Style style;

    public interface Instruction extends Supplier<Pair<Polygon.ActualVertex, InstructionType>> {
        static Instruction of(Polygon.ActualVertex actualVertex, InstructionType instructionType) {
            return of(Pair.of(actualVertex, instructionType));
        }

        static Instruction of(Pair<Polygon.ActualVertex, InstructionType> p) {
            return () -> p;
        }
    }

    public Path(List<Instruction> instructions, boolean closed, Style style) {
        this.instructions = instructions;
        this.closed = closed;
        this.style = style;
    }

    public static Function<Path, Path> fromPathWithStyle(Style style) {
        return path -> new Path(path.getInstructions(), path.isClosed(), style);
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public boolean isClosed() {
        return closed;
    }

    public Style getStyle() {
        return style;
    }

    public enum InstructionType {
        STARTING_POINT,
        LINE,
        ARC_LARGER,
        ARC_SMALLER
    }

    public static Function<Polygon.VertexPath, Path> vertexPathToPath =
            l -> new Path.Builder().justLines(l).build();

    public static Function<List<Polygon.VertexPath>, Paths> vertexPathsToPaths =
            GenericTools.mapLists(vertexPathToPath).andThen(Paths::of);

    public static class Builder {

        private boolean closed = false;
        private List<Instruction> instructions = new ArrayList<>();
        private Style style;

        public Builder closed() {
            closed = true;
            return this;
        }

        public Builder justLines(Polygon.VertexPath vertexPath) {
            AtomicInteger counter = new AtomicInteger(0);
            vertexPath.get().stream().forEach(p -> {
                if (counter.getAndIncrement() == 0) {
                    startWith(p);
                } else {
                    lineTo(p);
                }
            });
            return this;
        }

        public Builder startWith(Polygon.ActualVertex actualVertex) {
            instructions.add(Instruction.of(Pair.of(actualVertex, InstructionType.STARTING_POINT)));
            return this;
        }

        public Builder startWith(Polygon polygon, Polygon.Vertex vertex) {
            instructions.add(Instruction.of(Polygon.ActualVertex.of(polygon, vertex), InstructionType.STARTING_POINT));
            return this;
        }

        public Builder lineTo(Polygon.ActualVertex actualVertex) {
            instructions.add(Instruction.of(actualVertex, InstructionType.LINE));
            return this;
        }

        public Builder lineTo(Polygon polygon, Polygon.Vertex vertex) {
            instructions.add(Instruction.of(Polygon.ActualVertex.of(polygon, vertex), InstructionType.LINE));
            return this;
        }

        public Builder arcLargerTo(Polygon.ActualVertex actualVertex) {
            instructions.add(() -> Pair.of(actualVertex, InstructionType.ARC_LARGER));
            return this;
        }

        public Builder arcLargerTo(Polygon polygon, Polygon.Vertex vertex) {
            instructions.add(() -> Pair.of(() -> Pair.of(polygon, vertex), InstructionType.ARC_LARGER));
            return this;
        }

        public Builder arcSmallerTo(Polygon.ActualVertex actualVertex) {
            instructions.add(() -> Pair.of(actualVertex, InstructionType.ARC_SMALLER));
            return this;
        }

        public Builder arcSmallerTo(Polygon polygon, Polygon.Vertex vertex) {
            instructions.add(Instruction.of(Pair.of(Polygon.ActualVertex.of(Pair.of(polygon, vertex)), InstructionType.ARC_SMALLER)));
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


    public interface Paths extends Supplier<List<Path>> {
        static Paths of(List<Path> paths) {
            return () -> paths;
        }

        static Paths of(Path... paths) {
            return of(Arrays.asList(paths));
        }


    }

}
