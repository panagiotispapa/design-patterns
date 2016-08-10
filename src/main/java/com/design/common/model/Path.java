package com.design.common.model;

import com.design.common.CanvasPoint;
import com.design.common.InitialConditions;
import com.design.common.Line;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.design.common.model.Path.Instruction.instruction;
import static java.util.stream.Collectors.joining;


public class Path {

    private final Line path;
    private final boolean closed;
    private Style style;

    public Path(Style style, Line path) {
        this(false, style, path);
    }

    private Path(boolean closed, Style style, Line path) {
        this.closed = closed;
        this.style = style;
        this.path = path;
    }

    public Line getPath() {
        return path;
    }

    public boolean isClosed() {
        return closed;
    }

    public Style getStyle() {
        return style;
    }


    public String draw(InitialConditions ic) {
        return Stream.of(
                "<path d=\"",
                fromPath(path.generatePoints(ic)).stream().map(Instruction::toSvg).collect(joining(" ")),
                isClosed() ? " z\"" : "\"",
                " style=\"" + style.toSVG(),
                "\"/>"
        ).collect(joining());
    }

    private enum InstructionType {
        STARTING_POINT("M"),
        LINE("L");

        private final String svgInstruction;

        InstructionType(String svgInstruction) {
            this.svgInstruction = svgInstruction;
        }

        public String getSvgInstruction() {
            return svgInstruction;
        }
    }


    private List<Instruction> fromPath(List<CanvasPoint> points) {
        List<Instruction> instructions = Lists.newArrayList();
        AtomicInteger counter = new AtomicInteger(0);
        points.forEach(p -> {
            if (counter.getAndIncrement() == 0) {
                instructions.add(instruction(p, InstructionType.STARTING_POINT));
            } else {
                instructions.add(instruction(p, InstructionType.LINE));
            }
        });
        return instructions;
    }


    public interface Instruction extends Supplier<Pair<CanvasPoint, InstructionType>> {

        static Instruction instruction(CanvasPoint point, InstructionType instructionType) {
            return () -> Pair.of(point, instructionType);
        }

        default CanvasPoint getPoint() {
            return get().getLeft();
        }

        default InstructionType getInstructionType() {
            return get().getRight();
        }

        default String toSvg() {
            return getPoint().toSVGInstruction(getInstructionType()::getSvgInstruction);
        }
    }

}
