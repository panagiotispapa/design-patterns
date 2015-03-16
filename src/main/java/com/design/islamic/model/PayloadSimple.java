package com.design.islamic.model;

import com.design.common.Polygon;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class PayloadSimple {
    private final List<List<Pair<Polygon, Polygon.Vertex>>> lines;
    private final Grid.Configuration gridConfiguration;
    private final List<Integer> allVertexes;
    private final String name;

    public PayloadSimple(String name, List<List<Pair<Polygon, Polygon.Vertex>>> lines, List<Integer> allVertexes, Grid.Configuration gridConfiguration) {
        this.lines = lines;
        this.gridConfiguration = gridConfiguration;
        this.allVertexes = allVertexes;
        this.name = name;
    }

    public PayloadSimple(String name, List<List<Pair<Polygon, Polygon.Vertex>>> lines, List<Integer> allVertexes) {
        this(name, lines, allVertexes, Grid.Configs.HEX_HOR2.getConfiguration());
    }

    public List<List<Pair<Polygon, Polygon.Vertex>>> getLines() {
        return lines;
    }

    public Grid.Configuration getGridConfiguration() {
        return gridConfiguration;
    }

    public List<List<Point2D>> toLines(Pair<Point2D, Double> ic) {
        return lines.stream().map(Polygon.mixVertexesFull(allVertexes, ic))
                .map(Collection::stream).flatMap(s -> s).collect(toList());
    }

    public String getName() {
        return name;
    }
}

