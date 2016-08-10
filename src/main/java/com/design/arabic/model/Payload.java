package com.design.arabic.model;

import com.design.common.CanvasPoint;
import com.design.common.Grid;
import com.design.common.InitialConditions;
import com.design.common.Line;
import com.design.common.model.Path;
import com.design.common.model.Style;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.CanvasPoint.point;
import static com.design.common.view.SvgFactory.buildBackground;
import static com.design.common.view.SvgFactory.buildSvg;
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
        SMALL(100.0),
        MEDIUM(150.0),
        LARGE(200.0);

        private final Double size;

        Size(Double size) {
            this.size = size;
        }

        public Double getSize() {
            return size;
        }
    }

    public SvgPayload toSvgPayload() {
        Double R = size.getSize();
        Dimension dim = new Dimension((int) (15 * R), (int) (10 * R));
        InitialConditions ic = InitialConditions.of(point(0, 0), R);

        return SvgPayload.svgPayload(buildSvg(dim, buildBackground(dim) + pointsToSvg(ic)), name) ;
    }

    private  String pointsToSvg(InitialConditions ic) {
        java.util.List<CanvasPoint> gridPoints = Grid.gridFromStart(ic.getCentre(), ic.getR(), getGridConfiguration(), 17);

        return
                gridPoints.parallelStream().map(p -> InitialConditions.of(p, ic.getR())).map(this::draw).collect(joining());

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

        public Builder withPathsSingleLines(Style style, Sequence<Line> lines) {
            this.paths = this.paths.join(lines.map(p -> p.toPath(style)));
            return this;
        }

        public Builder withPathsFull(Style style, Line... lines) {
            return withPathsFull(style, sequence(lines));
        }

        public Builder withPathsFull(Style style, Sequence<Line>... lines) {
            return withPathsFull(style, sequence(lines).flatMap(s -> s));
        }

        public Builder withPathsFull(Style style, Sequence<Line> lines) {
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

