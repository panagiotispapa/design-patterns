package com.design.common;

import java.awt.geom.Point2D;

public abstract class Polygon {

    public enum Type {
        HOR,
        VER
    }

    public interface Vertex {

        Vertex withOffset(int offset);

        Point2D getPoint();

    }

}
