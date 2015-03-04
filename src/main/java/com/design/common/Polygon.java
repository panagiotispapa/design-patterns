package com.design.common;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public abstract class Polygon {

    public static final Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> IDENTITY = p -> p;

    public static Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform(double ratio, Polygon.Vertex vertex, Polygon.Type type) {
        return p -> Triple.of(Points.translateAndScale(p.getLeft(), p.getMiddle() * ratio).apply(vertex.getPoint(p.getRight(), type)), p.getMiddle(), p.getRight());
    }

    public static Function<Polygon, List<Point2D>> vertexes(Pair<Point2D, Double> ic) {
        return p -> p.getVertexes().stream().map(p.toPoint(0, ic)).collect(toList());
    }

    public static Function<Pair<Polygon, Vertex>, Point2D> vertex(Pair<Point2D, Double> ic) {
        return p -> p.getLeft().toPoint(0, ic).apply(p.getLeft().getVertexes().get(p.getRight().getIndex()));
    }

    public static Function<Pair<Polygon, Vertex>, List<Point2D>> vertexesFull(List<Integer> offsets, Pair<Point2D, Double> ic) {
        return p -> {
            final Vertex chosen = p.getLeft().getVertexes().get(p.getRight().getIndex());
            return offsets.stream().map(o -> p.getLeft().toPoint(o, ic))
                    .map(t -> t.apply(chosen)).collect(toList());
        };
    }

    public static Function<List<Pair<Polygon, Vertex>>, List<List<Point2D>>> mixVertexesFull(List<Integer> offsets, Pair<Point2D, Double> ic) {
        Function<Pair<Polygon, Vertex>, List<Point2D>> toVertexesFull = vertexesFull(offsets, ic);
        return p -> Mappings.<Point2D>combine().apply(
                p.stream().map(toVertexesFull).collect(toList())
        );
    }

    public static Function<Polygon, List<Pair<Point2D, Double>>> toCircles(Pair<Point2D, Double> ic) {
        return p -> vertexes(ic).apply(p).stream().map(v -> Pair.of(v, p.getRatio() * ic.getRight())).collect(toList());
    }

    public static Function<Pair<Polygon, List<List<Vertex>>>, List<List<Point2D>>> toLines(List<Integer> offsets, Pair<Point2D, Double> ic) {
        return p -> offsets.stream().map(i -> toLines(i, ic).apply(p)).flatMap(Collection::stream).collect(toList());
    }

    public static Function<Pair<Polygon, List<List<Vertex>>>, List<List<Point2D>>> toLines(int offset, Pair<Point2D, Double> ic) {
        return pair -> Mappings.fromListOfLists(pair.getLeft().toPoint(offset, ic)).apply(pair.getRight());
    }

    public Function<Vertex, Point2D> toPoint(int offset, Pair<Point2D, Double> ic) {
        final Point2D centre = centreTransform.apply(Triple.of(ic.getLeft(), ic.getRight(), offset)).getLeft();
        return mapVertexToPoint(centre, ic.getRight(), offset);
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

//    public static Polygon newPolygon(double ratio, Type type) {
//        return new Polygon(ratio, type, IDENTITY);
//    }
//
//    public static Polygon newPolygon(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
//        return new Polygon(ratio, type, centreTransform);
//    }

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
        this(ratio, type, IDENTITY);
    }

    public Polygon(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        this.ratio = ratio;
        this.type = type;
        this.centreTransform = centreTransform;
    }
}
