package com.design.common;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.util.stream.Collectors.toList;

public abstract class Polygon {

    public static Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform(double ratio, Polygon.Vertex vertex, Polygon.Type type) {
        return p -> Triple.of(Points.translateAndScale(p.getLeft(), p.getMiddle() * ratio).apply(vertex.getPoint(p.getRight(), type)), p.getMiddle(), p.getRight());
    }

    public static Function<Polygon, List<Point2D>> vertexes(InitialConditions ic) {
        return p -> p.getVertexes().stream().map(p.toPoint(0, ic)).collect(toList());
    }

    public static Function<ActualVertex, Point2D> vertex(InitialConditions ic) {
        return p -> p.get().getLeft().toPoint(0, ic).apply(p.get().getLeft().getVertexes().get(p.get().getRight().getIndex()));
    }

    public static Function<Integer, Function<ActualVertex, Point2D>> vertexFull(InitialConditions ic) {
        return i -> p -> p.get().getLeft().toPoint(i, ic).apply(p.get().getLeft().getVertexes().get(p.get().getRight().getIndex()));
    }

    public static Function<Polygon, List<Pair<Point2D, Double>>> toCircles(InitialConditions ic) {
        return p -> vertexes(ic).apply(p).stream().map(v -> Pair.of(v, p.getRatio() * ic.get().getRight())).collect(toList());
    }

    public static Function<Pair<Polygon, Double>, List<Pair<Point2D, Double>>> toCirclesWithRadius(InitialConditions ic) {
        return p -> vertexes(ic).apply(p.getLeft()).stream().map(v -> Pair.of(v, p.getRight() * ic.get().getRight())).collect(toList());
    }

    public Function<Vertex, Point2D> toPoint(int offset, InitialConditions ic) {
        final Point2D centre = centreTransform.apply(Triple.of(ic.get().getLeft(), ic.get().getRight(), offset)).getLeft();
        return mapVertexToPoint(centre, ic.get().getRight(), offset);
    }

    public Function<Vertex, Point2D> mapVertexToPoint(Point2D centre, double r, int offset) {
        return v -> Points.translateAndScale(centre, r * ratio).apply(v.getPoint(offset, type));
    }

    protected abstract double getHeightRatio();

    protected abstract List<Vertex> getVertexes();

    protected abstract Polygon newInstance(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform);

    public Polygon getMirror() {
        return newInstance(ratio, type.revert(), centreTransform);
    }

    public Polygon getRegistered() {
        return newInstance(ratio * getHeightRatio(), type.revert(), centreTransform);
    }

    public Polygon getFramed() {
        return newInstance(ratio / getHeightRatio(), type.revert(), centreTransform);
    }

    public static enum Type {
        HOR,
        VER;

        public Type revert() {
            return this == VER ? HOR : VER;
        }
    }

    public interface Vertex {
        Point2D getPoint(int offset, Polygon.Type type);

        int getIndex();
    }

    private final double ratio;
    private final Polygon.Type type;

    private final Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform;

    public Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> getCentreTransform() {
        return centreTransform;
    }

    public double getRatio() {
        return ratio;
    }

    public Type getType() {
        return type;
    }

    public Polygon(double ratio, Type type) {
        this(ratio, type, Function.<Triple<Point2D, Double, Integer>>identity());
    }

    public Polygon(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        this.ratio = ratio;
        this.type = type;
        this.centreTransform = centreTransform;
    }

    public static List<VertexPath> toVertexPaths(Polygon polygon, List<List<Polygon.Vertex>> vertexes) {
        return vertexes.stream().map(v -> toVertexPath(polygon, v)).collect(toList());
    }

    public static VertexPath toVertexPath(Polygon polygon, List<Polygon.Vertex> vertexes) {
        return () -> vertexes.stream().map(vertex -> ActualVertex.of(polygon, vertex)).collect(toList());

    }

    public interface InitialConditions extends Supplier<Pair<Point2D, Double>> {
        static InitialConditions of(Point2D centre, Double r) {
            return () -> Pair.of(centre, r);
        }
    }

    public interface VertexPath extends Supplier<List<ActualVertex>> {

    }

    public interface ActualVertex extends Supplier<Pair<Polygon, Polygon.Vertex>> {
        static ActualVertex of(Pair<Polygon, Polygon.Vertex> pair) {
            return () -> pair;
        }

        static ActualVertex of(Polygon polygon, Polygon.Vertex vertex) {
            return () -> Pair.of(polygon, vertex);
        }
    }

}
