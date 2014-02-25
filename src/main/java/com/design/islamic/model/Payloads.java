package com.design.islamic.model;

import java.awt.geom.Point2D;
import java.util.Collections;
import java.util.List;

import static com.design.islamic.GenericTools.*;

public class Payloads {

    public static List<List<Point2D>> EMPTY = Collections.emptyList();


    public static Payload newPayloadFromLines(final List<List<Point2D>> lines){

        return new PayloadImpl(EMPTY, EMPTY, lines, EMPTY);
    }


    public static Payload newPayloadFromPolygonsAndLines(final List<List<Point2D>> polygons, final List<List<Point2D>> lines){
        return new PayloadImpl(polygons, EMPTY, lines, EMPTY);

    }

    public static Payload newPayloadFromPolygonsAndLines(final List<List<Point2D>> polygons, final List<List<Point2D>> polygonsSec, final List<List<Point2D>> lines, final List<List<Point2D>> linesSec){
        return new PayloadImpl(polygons, polygonsSec, lines, linesSec);

    }

    public static Payload newPayloadFromPolygons(final List<List<Point2D>> polygons){
        return new PayloadImpl(polygons, EMPTY, EMPTY, EMPTY);

    }

    private static class PayloadImpl implements Payload {
        private final List<List<Point2D>> polygons;
        private final List<List<Point2D>> polygonsSecondary;

        private final List<List<Point2D>> polylines;
        private final List<List<Point2D>> polylinesSecondary;

        private PayloadImpl(List<List<Point2D>> polygons, List<List<Point2D>> polygonsSecondary, List<List<Point2D>> polylines, List<List<Point2D>> polylinesSecondary) {
            this.polygons = polygons;
            this.polygonsSecondary = polygonsSecondary;
            this.polylines = polylines;
            this.polylinesSecondary = polylinesSecondary;
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
                    cloneAndTranslatePointsLists(polylinesSecondary, centre)

            );
        }
    }


}
