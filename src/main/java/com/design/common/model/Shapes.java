package com.design.common.model;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.awt.geom.Point2D;

public class Shapes {

    public static Arc newArc(Circle circle, boolean up) {
        return new ArcImpl(circle, up);
    }

    public static Circle newCircle(Point2D centre, double r) {
        return new CircleImpl(centre, r);
    }

    private static class CircleImpl implements Circle {
        private final Point2D centre;
        private final double r;

        private CircleImpl(Point2D centre, double r) {
            this.centre = centre;
            this.r = r;
        }

        @Override
        public Point2D getCentre() {
            return centre;
        }

        @Override
        public double getR() {
            return r;
        }

        @Override
        public int hashCode() {
            return HashCodeBuilder.reflectionHashCode(this);
        }

        @Override
        public boolean equals(Object obj) {
            return EqualsBuilder.reflectionEquals(obj, this);
        }
    }

    private static class ArcImpl implements Arc {
        private final Circle circle;
        private final boolean up;

        private ArcImpl(Circle circle, boolean up) {
            this.circle = circle;
            this.up = up;
        }

        @Override
        public Circle getCircle() {
            return circle;
        }

        @Override
        public boolean isUp() {
            return up;
        }
    }

}
