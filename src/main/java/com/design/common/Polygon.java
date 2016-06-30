package com.design.common;

import com.design.common.model.Circle;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public abstract class Polygon {

    public static CentreTransform centreTransform(double ratio, Polygon.Vertex vertex, Polygon.Type type) {
        return CentreTransform.of(p -> CentreInstruction.of(Points.translateAndScale(p.getCentre(), p.getR() * ratio).apply(vertex.getPoint(p.getRadIndex(), type)), p.getR(), p.getRadIndex()));
    }

    public static Function<Polygon, List<Point2D>> vertexes(InitialConditions ic) {
        return p -> p.getVertexes().stream().map(p.toPoint(0, ic)).collect(toList());
    }

    public static Function<ActualVertex, Point2D> vertex(InitialConditions ic) {
        return p -> p.getPolygon().toPoint(0, ic).apply(p.getPolygon().getVertexes().get(p.getVertex().getIndex()));
    }

    public static Function<Integer, Function<ActualVertex, Point2D>> vertexFull(InitialConditions ic) {
        return i -> p -> p.getPolygon().toPoint(i, ic).apply(p.getPolygon().getVertexes().get(p.getVertex().getIndex()));
    }

    public static Function<Polygon, List<Circle>> toCircles(InitialConditions ic) {
        return p -> vertexes(ic).apply(p).stream().map(v -> Circle.of(v, p.getRatio() * ic.getR())).collect(toList());
    }

    public static Function<Pair<Polygon, Double>, List<Circle>> toCirclesWithRadius(InitialConditions ic) {
        return p -> vertexes(ic).apply(p.getLeft()).stream().map(v -> Circle.of(v, p.getRight() * ic.getR())).collect(toList());
    }

    public Function<Vertex, Point2D> toPoint(int offset, InitialConditions ic) {
        final Point2D centre = centreTransform.get().apply(CentreInstruction.of(ic.getCentre(), ic.getR(), offset)).getCentre();
        return mapVertexToPoint(centre, ic.getR(), offset);
    }

    public Function<Vertex, Point2D> mapVertexToPoint(Point2D centre, double r, int offset) {
        return v -> Points.translateAndScale(centre, r * ratio).apply(v.getPoint(offset, type));
    }

    protected abstract double getHeightRatio();

    protected abstract List<Vertex> getVertexes();

    protected abstract Polygon newInstance(double ratio, Type type, CentreTransform centreTransform);

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

    private final CentreTransform centreTransform;

    public CentreTransform getCentreTransform() {
        return centreTransform;
    }

    public double getRatio() {
        return ratio;
    }

    public Type getType() {
        return type;
    }

    public Polygon(double ratio, Type type) {
        this(ratio, type, CentreTransform.of(Function.<CentreInstruction>identity()));
    }

    public Polygon(double ratio, Type type, CentreTransform centreTransform) {
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

        default Point2D getCentre() {
            return get().getLeft();
        }

        default Double getR() {
            return get().getRight();
        }
    }

    public interface VertexPaths extends Supplier<List<Polygon.VertexPath>> {
        static VertexPaths of(List<Polygon.VertexPath> paths) {
            return () -> paths;
        }

        static VertexPaths of(Polygon.VertexPath... paths) {
            return of(Arrays.asList(paths));
        }
    }

    public interface VertexPath extends Supplier<List<ActualVertex>> {
        static VertexPath of(List<ActualVertex> actualVertices) {
            return () -> actualVertices;
        }

        static VertexPath of(ActualVertex... actualVertices) {
            return of(asList(actualVertices));
        }
    }

    public interface ActualVertex extends Supplier<Pair<Polygon, Polygon.Vertex>> {
        static ActualVertex of(Pair<Polygon, Polygon.Vertex> pair) {
            return () -> pair;
        }

        static ActualVertex of(Polygon polygon, Polygon.Vertex vertex) {
            return () -> Pair.of(polygon, vertex);
        }

        default Polygon getPolygon() {
            return get().getLeft();
        }

        default Vertex getVertex() {
            return get().getRight();
        }
    }

    public interface CentreInstruction extends Supplier<Triple<Point2D, Double, Integer>> {

        static CentreInstruction of(Point2D centre, Double r, Integer radIndex) {
            return () -> Triple.of(centre, r, radIndex);
        }

        default Point2D getCentre() {
            return get().getLeft();
        }

        default Double getR() {
            return get().getMiddle();
        }

        default Integer getRadIndex() {
            return get().getRight();
        }
    }

    public interface CentreTransform extends Supplier<Function<CentreInstruction, CentreInstruction>> {
        static CentreTransform of(Function<CentreInstruction, CentreInstruction> centreTransform) {
            return () -> centreTransform;
        }

        static CentreTransform of(Function<CentreInstruction, CentreInstruction>... centreTransforms) {
            return () -> Stream.of(centreTransforms).reduce(Function.identity(), Function::andThen);
        }

        static CentreTransform of(CentreTransform... centreTransforms) {
            return () -> Stream.of(centreTransforms).map(Supplier::get).reduce(Function.identity(), Function::andThen);
        }
    }


}
