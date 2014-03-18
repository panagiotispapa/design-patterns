package com.design.islamic.model.rect;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.cos;
import static java.util.Arrays.asList;

public class Tile2 implements Tile {

    private final Point2D centre;
    private final double r;

    private List<Point2D> pointsA;
    private List<Point2D> pointsB;
    private List<Point2D> central;

    private List<List<Point2D>> lines;

    private final double newHeight;
    private final double newR;

    public Tile2(Point2D centre, double r) {
        this.centre = centre;
        this.r = r;

        newHeight = r / 2.0;
        newR = newHeight / cos(OCT_PHI / 2.0);

        buildPoints();
        buildLines();

    }

    private void buildPoints() {

        List<Point2D> mainRectRot = newRectRot(centre, r);

        central = newOctRot(centre, newR);

        pointsA = newArrayList();
        pointsB = newArrayList();

        for (int i = 0; i < RECT_N; i++) {
            pointsA.add(newEdgeAt(mainRectRot.get(i), newHeight, OCT_RADIANS[toOctIndex(2 * i + 4)]));
            pointsB.add(newEdgeAt(mainRectRot.get(i), newHeight, OCT_RADIANS[toOctIndex(2 * i + 6)]));
        }

    }

    private void buildLines() {
        lines = newArrayList();

//        lines.add(newRectRot(centre, newR));

        for (int i = 0; i < RECT_N; i++) {
            lines.add(asList(
                    pointsB.get(i),
                    central.get((2 * i))
            ));
            lines.add(asList(
                    pointsA.get(i),
                    central.get((2 * i + 1))
            ));
        }

    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromPolygonsAndLines(asList(central), lines);
    }

    public List<List<Point2D>> getLines() {
        return lines;
    }

    public List<Point2D> getPointsA() {
        return pointsA;
    }

    public List<Point2D> getPointsB() {
        return pointsB;
    }

    public List<Point2D> getCentral() {
        return central;
    }
}
