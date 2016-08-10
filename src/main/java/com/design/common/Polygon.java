package com.design.common;

public abstract class Polygon {

    public enum Type {
        HOR,
        VER
    }

    public interface Vertex {

        Vertex withOffset(int offset);

        CanvasPoint getCanvasPoint();

        default FinalPointTransition fpt(double r) {
            return FinalPointTransition.fpt(r, this);
        }

        default PointTransition pt(double r) {
            return PointTransition.pt(r, this);
        }

    }

}
