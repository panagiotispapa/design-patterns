package com.design.islamic.model;

import com.design.common.Grid;
import com.design.common.InitialConditions;
import com.design.common.PointsPath;
import com.design.common.model.Path;
import com.design.common.model.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class Payload {
    private final Grid.Configuration gridConfiguration;
    private final List<Integer> allVertexes;
    private final String name;
    private final Size size;
    private final List<Path> pathsSingle;
    private final List<Path> pathsFull;

    public Payload(String name, List<Integer> allVertexes, Grid.Configuration gridConfiguration, Size size, List<Path> pathsSingle, List<Path> pathsFull) {
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

    public Grid.Configuration getGridConfiguration() {
        return gridConfiguration;
    }

    public String draw(InitialConditions ic) {
        return
                Stream.of(
                        pathsSingle.stream().map(p -> p.draw(ic)),
                        pathsFull.stream().map(p -> p.draw(allVertexes, ic)).flatMap(s -> s)
                ).flatMap(s -> s).collect(joining());
    }

    public String getName() {
        return name;
    }

    public enum Size {
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


        public Builder withPathsSingleLines(Style style, PointsPath... lines) {
            Stream.of(lines).map(Path.fromPath(style)).forEach(pathsSingle::add);
            return this;
        }

        public Builder withPathsSingleLines(Style style, List<PointsPath> lines) {
            lines.stream().map(Path.fromPath(style)).forEach(pathsSingle::add);
            return this;
        }

        public Builder withPathsFull(Style style, PointsPath... lines) {
            return withPathsFull(style, asList(lines));
        }

        public Builder withPathsFull(Style style, Stream<PointsPath>... lines) {
            return withPathsFull(style, Stream.of(lines).flatMap(s -> s).collect(toList()));
        }

        public Builder withPathsFull(Style style, List<PointsPath> lines) {
            lines.stream().map(Path.fromPath(style)).forEach(pathsFull::add);
            return this;
        }


        public Builder withSize(Size size) {
            this.size = size;
            return this;
        }

        public Payload build() {
            return new Payload(name, allVertexes, gridConfiguration, size, pathsSingle, pathsFull);
        }

    }

}

