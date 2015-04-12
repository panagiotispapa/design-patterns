package com.design.islamic.model;

import com.design.common.Mappings;
import com.design.common.Polygon;
import com.design.islamic.model.tiles.Grid;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PayloadSimple {
    private final List<List<Pair<Polygon, Polygon.Vertex>>> lines;
    private final List<List<Pair<Polygon, Polygon.Vertex>>> linesSingle;
    private final Grid.Configuration gridConfiguration;
    private final List<Integer> allVertexes;
    private final String name;
    private final Size size;

    public PayloadSimple(String name, List<List<Pair<Polygon, Polygon.Vertex>>> lines, List<Integer> allVertexes, Grid.Configuration gridConfiguration, List<List<Pair<Polygon, Polygon.Vertex>>> linesSingle, Size size) {
        this.lines = lines;
        this.gridConfiguration = gridConfiguration;
        this.allVertexes = allVertexes;
        this.name = name;
        this.linesSingle = linesSingle;
        this.size = size;
    }

    public Size getSize() {
        return size;
    }

    public List<List<Pair<Polygon, Polygon.Vertex>>> getLines() {
        return lines;
    }

    public List<List<Pair<Polygon, Polygon.Vertex>>> getLinesSingle() {
        return linesSingle;
    }

    public Grid.Configuration getGridConfiguration() {
        return gridConfiguration;
    }

    public List<List<Point2D>> toLines(Pair<Point2D, Double> ic) {
        List<List<Point2D>> linesFull = lines.stream().map(Polygon.mixVertexesFull(allVertexes, ic))
                .map(Collection::stream).flatMap(s -> s).collect(toList());

        List<List<Point2D>> linesOne = Mappings.fromListOfLists(Polygon.vertex(ic)).apply(linesSingle);

        return ImmutableList.copyOf(Iterables.concat(
                linesFull,
                linesOne
        ));
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
        private List<List<Pair<Polygon, Polygon.Vertex>>> lines = new ArrayList<>();
        private Grid.Configuration gridConfiguration = Grid.Configs.HEX_HOR2.getConfiguration();
        private final List<Integer> allVertexes;
        private final String name;
        private List<List<Pair<Polygon, Polygon.Vertex>>> linesSingle = new ArrayList<>();

        public Builder(String name, List<Integer> allVertexes) {
            this.allVertexes = allVertexes;
            this.name = name;
        }

        public Builder withGridConf(Grid.Configuration gridConf) {
            this.gridConfiguration = gridConf;
            return this;
        }

        public Builder withLines(List<List<Pair<Polygon, Polygon.Vertex>>> lines) {
            this.lines = lines;
            return this;
        }

        public Builder withLinesSingle(List<List<Pair<Polygon, Polygon.Vertex>>> linesSingle) {
            this.linesSingle = linesSingle;
            return this;
        }

        public Builder withSize(Size size) {
            this.size = size;
            return this;
        }
        public PayloadSimple build() {
            return new PayloadSimple(name, lines, allVertexes, gridConfiguration, linesSingle, size);
        }

    }
}

