package com.design.islamic.model;

import com.design.common.Grid;
import com.design.common.InitialConditions;
import com.design.common.Polygon;
import com.design.common.PointsPath;
import com.design.common.model.Path;
import com.design.common.model.Style;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

public class PayloadSimple {
    private final Grid.Configuration gridConfiguration;
    private final List<Integer> allVertexes;
    private final String name;
    private final Size size;
    private final List<Path> pathsNewSingle;
    private final List<Path> pathsNewFull;

    public PayloadSimple(String name, List<Integer> allVertexes, Grid.Configuration gridConfiguration, Size size, List<Path> pathsNewSingle, List<Path> pathsNewFull) {
        this.gridConfiguration = gridConfiguration;
        this.allVertexes = allVertexes;
        this.name = name;
        this.size = size;
        this.pathsNewSingle = pathsNewSingle;
        this.pathsNewFull = pathsNewFull;
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
                        pathsNewSingle.stream().map(p -> p.draw(ic)),
                        pathsNewFull.stream().map(p -> p.draw(allVertexes, ic)).flatMap(s -> s)
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
        private final List<Path> pathsNewSingle = new ArrayList<>();
        private final List<Path> pathsFullNew = new ArrayList<>();

        public Builder(String name, List<Integer> allVertexes) {
            this.allVertexes = allVertexes;
            this.name = name;
        }

        public Builder withGridConf(Grid.Configuration gridConf) {
            this.gridConfiguration = gridConf;
            return this;
        }


        public Builder withPathsNewSingleLines(Style style, PointsPath... lines) {
            Stream.of(lines).map(Path.fromPath(style)).forEach(pathsNewSingle::add);
            return this;
        }

        public Builder withPathsNewSingleLines(Style style, List<PointsPath> lines) {
            lines.stream().map(Path.fromPath(style)).forEach(pathsNewSingle::add);
            return this;
        }

        public Builder withPathsNewFull(Style style, PointsPath... lines) {
            return withPathsNewFull(style, asList(lines));
        }

        public Builder withPathsNewFull(Style style, Stream<PointsPath>... lines) {
            return withPathsNewFull(style, Stream.of(lines).flatMap(s -> s).collect(toList()));
        }

        public Builder withPathsNewFull(Style style, List<PointsPath> lines) {
            lines.stream().map(Path.fromPath(style)).forEach(pathsFullNew::add);
            return this;
        }


        public Builder withSize(Size size) {
            this.size = size;
            return this;
        }

        public PayloadSimple build() {
            return new PayloadSimple(name, allVertexes, gridConfiguration, size, pathsNewSingle, pathsFullNew);
        }

    }

}

