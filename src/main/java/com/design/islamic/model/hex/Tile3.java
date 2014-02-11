package com.design.islamic.model.hex;

import java.awt.geom.Point2D;
import java.util.List;

public interface Tile3 {
    List<Point2D> getInnerEdges();
    List<List<Point2D>> getOuterRectangles();


}
