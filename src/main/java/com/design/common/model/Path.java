package com.design.common.model;

import com.design.common.InitialConditions;
import com.design.common.PointsPath;
import com.design.common.view.SvgFactory;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.design.common.view.SvgFactory.toSVGString;


public class Path {

    private final PointsPath path;
    private final boolean closed;
    private Style style;

    public static Function<PointsPath, Path> fromPath(Style style) {
        return path -> new Path(style, path);
    }

    private Path(Style style, PointsPath path) {
        this(false, style, path);
    }

    private Path(boolean closed, Style style, PointsPath path) {
        this.closed = closed;
        this.style = style;
        this.path = path;
    }

    public PointsPath getPath() {
        return path;
    }

    public boolean isClosed() {
        return closed;
    }

    public Style getStyle() {
        return style;
    }


    private static Function<Pair<Point2D, InstructionType>, String> toSvg() {
        return p -> SvgFactory.toSVG(p.getLeft(), p.getRight()::getSvgInstruction);
    }

    public String draw(InitialConditions ic) {
        StringBuilder builder = new StringBuilder("<path d=\"");

        builder.append(fromPath(path, ic).stream().map(toSvg()).collect(Collectors.joining(" ")));

//        builder.append(getRealPointInstructions(offset, ic).stream().map(fromPathRealPointInstruction()).collect(Collectors.joining(" ")));

        if (isClosed()) {
            builder.append(" z\" style=\"");
        } else {
            builder.append("\" style=\"");
        }

        builder.append(toSVGString(getStyle()));
        builder.append("\"/>");

        return builder.toString();
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


    private List<Pair<Point2D, InstructionType>> fromPath(PointsPath path, InitialConditions ic) {
        List<Pair<Point2D, InstructionType>> instructions = Lists.newArrayList();
        AtomicInteger counter = new AtomicInteger(0);
        path.get().stream().map(p -> p.toPoint(ic)).forEach(p -> {
            if (counter.getAndIncrement() == 0) {
                instructions.add(Pair.of(p, InstructionType.STARTING_POINT));
            } else {
                instructions.add(Pair.of(p, InstructionType.LINE));
            }
        });
        return instructions;

    }


}
