package com.design.common;

import java.awt.geom.Point2D;

public abstract class Polygon {

    public enum Type {
        HOR,
        VER
    }

    public interface Vertex {
        Point2D getPoint(int offset, Polygon.Type type);

        int getIndex();
    }




}
