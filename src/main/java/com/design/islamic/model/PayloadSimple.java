package com.design.islamic.model;

import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Path;
import com.design.common.model.Style;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
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

    public String draw(Pair<Point2D, Double> ic) {
        return
                Stream.of(
                        pathsSingle.stream().map(drawPath(Polygon.vertex(ic))),
                        pathsFull.stream().map(drawPathFull(allVertexes, Polygon.vertexFull2(ic))).flatMap(s -> s)
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

        public Builder withPathsSingleFromLines(List<List<Pair<Polygon, Polygon.Vertex>>> lines, Style style) {
            return withPathsSingle(Path.pairsToPaths.apply(lines), style);
        }

        public Builder withPathsSingle(List<Path> paths, Style style) {
            paths.forEach(p -> p.setStyle(style));
            return withPathsSingle(paths);
        }

        public Builder withPathsSingle(List<Path> paths) {
            this.pathsSingle.addAll(paths);
            return this;
        }

        public Builder withPathsFullFromLines(List<List<Pair<Polygon, Polygon.Vertex>>> lines, Style style) {
            return withPathsFull(Path.pairsToPaths.apply(lines), style);
        }
        
        public Builder withPathsFull(List<Path> paths, Style style) {
            paths.forEach(p -> p.setStyle(style));
            return withPathsFull(paths);
        }

        public Builder withPathsFull(List<Path> paths) {
            this.pathsFull.addAll(paths);
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

