package com.design.islamic.model;

import com.design.common.Mappings;
import com.design.common.Points;
import com.design.islamic.model.tiles.Grid;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static com.design.islamic.GenericTools.cloneAndTranslatePointsLists;

public class Payloads {

    public static List<List<Point2D>> EMPTY = Collections.emptyList();

    public static Payload newPayloadFromLines(final List<List<Point2D>> lines, Grid.Configuration config) {

        return new PayloadImpl(EMPTY, EMPTY, lines, EMPTY, config);
    }

    public static Payload newPayloadFromPolygonsAndLines(final List<List<Point2D>> polygons, final List<List<Point2D>> lines, Grid.Configuration config) {
        return new PayloadImpl(polygons, EMPTY, lines, EMPTY, config);

    }

    public static Payload newPayloadFromPolygonsAndLines(final List<List<Point2D>> polygons, final List<List<Point2D>> polygonsSec, final List<List<Point2D>> lines, final List<List<Point2D>> linesSec, Grid.Configuration config) {
        return new PayloadImpl(polygons, polygonsSec, lines, linesSec, config);

    }

    public static Payload newPayloadFromPolygons(final List<List<Point2D>> polygons, Grid.Configuration config) {
        return new PayloadImpl(polygons, EMPTY, EMPTY, EMPTY, config);

    }

    public interface LinesBuilder {
        LinesSecondaryBuilder lines(List<List<Point2D>> lines);
    }

    public interface LinesSecondaryBuilder {
        GridConfigurationBuilder secondaryLines(List<List<Point2D>> secondaryLines);
    }

    public interface GridConfigurationBuilder {
        Payload gridConfiguration(Grid.Configuration gridConfiguration);
    }

    public static LinesBuilder payload() {
        return lines -> linesSecondary -> gridConfiguration -> new PayloadImpl(EMPTY, EMPTY, lines, linesSecondary, gridConfiguration);
    }

    public static Function<Point2D, Payload> translate(Payload payload) {
        return p -> new PayloadImpl(
                Mappings.fromListOfLists(Points.translate(p)).apply(payload.getPolygons()),
                Mappings.fromListOfLists(Points.translate(p)).apply(payload.getPolygonsSecondary()),
                Mappings.fromListOfLists(Points.translate(p)).apply(payload.getPolylines()),
                Mappings.fromListOfLists(Points.translate(p)).apply(payload.getPolygonsSecondary()),
                payload.getGridConfiguration()
        );
    }

    private static class PayloadImpl implements Payload {
        private final List<List<Point2D>> polygons;
        private final List<List<Point2D>> polygonsSecondary;

        private final List<List<Point2D>> polylines;
        private final List<List<Point2D>> polylinesSecondary;
        private final Grid.Configuration configuration;

        private PayloadImpl(List<List<Point2D>> polygons, List<List<Point2D>> polygonsSecondary, List<List<Point2D>> polylines, List<List<Point2D>> polylinesSecondary, Grid.Configuration configuration) {
            this.polygons = polygons;
            this.polygonsSecondary = polygonsSecondary;
            this.polylines = polylines;
            this.polylinesSecondary = polylinesSecondary;
            this.configuration = configuration;
        }

        @Override
        public Grid.Configuration getGridConfiguration() {
            return configuration;
        }

        @Override
        public List<List<Point2D>> getPolygons() {
            return polygons;
        }

        @Override
        public List<List<Point2D>> getPolygonsSecondary() {
            return polygonsSecondary;
        }

        @Override
        public List<List<Point2D>> getPolylines() {
            return polylines;
        }

        @Override
        public List<List<Point2D>> getPolylinesSecondary() {
            return polylinesSecondary;
        }

        @Override
        public Payload translate(Point2D centre) {

            return new PayloadImpl(
                    cloneAndTranslatePointsLists(polygons, centre),
                    cloneAndTranslatePointsLists(polygonsSecondary, centre),
                    cloneAndTranslatePointsLists(polylines, centre),
                    cloneAndTranslatePointsLists(polylinesSecondary, centre),
                    configuration

            );
        }
    }

}
