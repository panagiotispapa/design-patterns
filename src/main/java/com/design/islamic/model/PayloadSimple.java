package com.design.islamic.model;

import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Path;
import com.design.common.model.Path.Paths;
import com.design.common.model.Style;
import com.design.islamic.GenericTools;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static com.design.common.view.SvgFactory.drawPath;
import static com.design.common.view.SvgFactory.drawPathFull;
import static java.util.stream.Collectors.joining;

public class PayloadSimple {
    private final Grid.Configuration gridConfiguration;
    private final List<Integer> allVertexes;
    private final String name;
    private final Size size;
    private final List<Path> pathsSingle;
    private final List<Path> pathsFull;

    public PayloadSimple(String name, List<Integer> allVertexes, Grid.Configuration gridConfiguration, Size size, List<Path> pathsSingle, List<Path> pathsFull) {
        this.gridConfiguration = gridConfiguration;
        this.allVertexes = allVertexes;
        this.name = name;
        this.size = size;
        this.pathsSingle = pathsSingle;
        this.pathsFull = pathsFull;
    }

    public Size getSize() {
        return size;
    }

    public List<Path> getPathsSingle() {
        return pathsSingle;
    }

    public List<Path> getPathsFull() {
        return pathsFull;
    }

    public Grid.Configuration getGridConfiguration() {
        return gridConfiguration;
    }

    public String draw(Polygon.InitialConditions ic) {
        return
                Stream.of(
                        pathsSingle.stream().map(drawPath(Polygon.vertex(ic))),
                        pathsFull.stream().map(drawPathFull(allVertexes, Polygon.vertexFull(ic))).flatMap(s -> s)
                ).flatMap(s -> s).collect(joining());
    }

    public String getName() {
        return name;
    }

    public static enum Size {
        SMALL,
        MEDIUM,
        LARGE
    }

    public static class Builder {
        private Size size = Size.SMALL;
        private Grid.Configuration gridConfiguration = Grid.Configs.HEX_HOR2.getConfiguration();
        private final List<Integer> allVertexes;
        private final String name;
        private final List<Path> pathsSingle = new ArrayList<>();
        private final List<Path> pathsFull = new ArrayList<>();

        public Builder(String name, List<Integer> allVertexes) {
            this.allVertexes = allVertexes;
            this.name = name;
        }

        public Builder withGridConf(Grid.Configuration gridConf) {
            this.gridConfiguration = gridConf;
            return this;
        }

        public Builder withPathsSingle(Polygon.VertexPaths lines, Style style) {
            return withPathsSingle(Path.vertexPathsToPaths.apply(lines.get()), style);
        }

        public Builder withPathsSingle(Paths paths, Style style) {
            return withPathsSingle(Paths.of(GenericTools.mapLists(Path.fromPathWithStyle(style)).apply(paths.get())));
        }

        public Builder withPathsSingle(Paths paths) {
            this.pathsSingle.addAll(paths.get());
            return this;
        }

        public Builder withPathsFull(Polygon.VertexPaths lines, Style style) {
            return withPathsFull(Path.vertexPathsToPaths.apply(lines.get()), style);
        }

        public Builder withPathsFull(Paths paths, Style style) {
            return withPathsFull(
                    Paths.of(
                            GenericTools.mapLists(Path.fromPathWithStyle(style)).apply(paths.get())
                    )
            );
        }

        public Builder withPathsFull(Paths paths) {
            this.pathsFull.addAll(paths.get());
            return this;
        }

        public Builder withSize(Size size) {
            this.size = size;
            return this;
        }

        public PayloadSimple build() {
            return new PayloadSimple(name, allVertexes, gridConfiguration, size, pathsSingle, pathsFull);
        }

    }

}

