package com.design.common.model;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;

public class Shapes {

    public static List<Points> newPointsList(List<List<Point2D>> pointsList) {
        return newArrayList(transform(pointsList, new Function<List<Point2D>, Points>() {
            @Override
            public Points apply(List<Point2D> point2DList) {
                return newPoints(point2DList);
            }
        }));
    }

    public static Points newPoints(Iterable<Point2D> points) {
        return new PointsImpl(points);
    }

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

    private static class PointsImpl implements Points {
        private final List<Point2D> points;

        private PointsImpl(Iterable<Point2D> points) {
            this.points = ImmutableList.copyOf(points);
        }

        @Override
        public List<Point2D> getPoints() {
            return points;
        }
    }

}
