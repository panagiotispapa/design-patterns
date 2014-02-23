package com.design.islamic.model;

import java.awt.geom.Point2D;
import java.util.List;

public interface Payload {

    List<List<Point2D>> getPolygons();
    List<List<Point2D>> getPolygonsSecondary();

    List<List<Point2D>> getPolylines();
    List<List<Point2D>> getPolylinesSecondary();

    Payload translate(Point2D centre);
}

