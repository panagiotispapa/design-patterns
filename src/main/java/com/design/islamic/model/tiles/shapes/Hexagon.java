package com.design.islamic.model.tiles.shapes;

import java.awt.geom.Point2D;
import java.util.List;

public interface Hexagon extends Shape{

    Point2D getCentre();

    List<Point2D> getEdges();
}
