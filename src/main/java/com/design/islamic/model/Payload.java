package com.design.islamic.model;

import com.design.common.Grid;
import com.design.common.InitialConditions;
import com.design.common.PointsPath;
import com.design.common.model.Path;
import com.design.common.model.Style;
import com.googlecode.totallylazy.Sequence;

import static com.googlecode.totallylazy.Sequences.sequence;
import static java.util.stream.Collectors.joining;

public class Payload {
    private final Grid.Configuration gridConfiguration;
    private final String name;
    private final Size size;
    private final Sequence<Path> paths;

    public Payload(String name, Grid.Configuration gridConfiguration, Size size, Sequence<Path> paths) {
        this.gridConfiguration = gridConfiguration;
        this.name = name;
        this.size = size;
        this.paths = paths;
    }

    public Size getSize() {
        return size;
    }

    public Grid.Configuration getGridConfiguration() {
        return gridConfiguration;
    }

    public String draw(InitialConditions ic) {
        return paths.map(p -> p.draw(ic)).stream().collect(joining());
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
        private final Sequence<Integer> allVertexes;
        private final String name;
        private Sequence<Path> paths = sequence();

        public Builder(String name, Sequence<Integer> allVertexes) {
            this.allVertexes = allVertexes;
            this.name = name;
        }

        public Builder withGridConf(Grid.Configuration gridConf) {
            this.gridConfiguration = gridConf;
            return this;
        }


        public Builder withPathsSingleLines(Style style, Sequence<PointsPath> lines) {
            this.paths = this.paths.join(lines.map(p -> p.toPath(style)));
            return this;
        }

        public Builder withPathsFull(Style style, PointsPath... lines) {
            return withPathsFull(style, sequence(lines));
        }

        public Builder withPathsFull(Style style, Sequence<PointsPath>... lines) {
            return withPathsFull(style, sequence(lines).flatMap(s -> s));
        }

        public Builder withPathsFull(Style style, Sequence<PointsPath> lines) {
            withPathsSingleLines(style, allVertexes.flatMap(offset -> lines.map(p -> p.withOffset(offset))));
            return this;
        }


        public Builder withSize(Size size) {
            this.size = size;
            return this;
        }

        public Payload build() {
            return new Payload(name, gridConfiguration, size, paths);
        }

    }

}

