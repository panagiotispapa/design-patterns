package com.design.islamic.model;

import com.design.common.Polygon;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;

import static com.design.common.PolygonTools.calcVertexes;
import static com.design.common.RatioHelper.Ratios.RECT;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Rect extends Polygon {

    public static final double H = RECT.$H().apply(1.0);

    public static final List<Integer> ALL_VERTEX_INDEXES = IntStream.range(0, 4).boxed().collect(toList());

    public static Polygon rect(double ratio, Type type) {
        return new Rect(ratio, type);
    }

    public static Polygon rect(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        return new Rect(ratio, type, centreTransform);
    }
    
    public static Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform(double ratio, Corner corner) {
        return Polygon.centreTransform(ratio, corner.getVertex(), corner.getType());
    }
    
    public static List<List<Polygon.Vertex>> PERIMETER = asList(
            asList(
                    (Polygon.Vertex) Vertex.ONE,
                    Vertex.TWO,
                    Vertex.THREE,
                    Vertex.FOUR,
                    Vertex.ONE
            )
    );

    public static List<List<Polygon.Vertex>> DIAGONALS = asList(
            Diag.ONE.getVertexes(),
            Diag.TWO.getVertexes()
    );

    public static enum Diag {
        ONE(asList((Polygon.Vertex) Vertex.ONE, Vertex.THREE)),
        TWO(asList((Polygon.Vertex) Vertex.TWO, Vertex.FOUR));

        private final List<Polygon.Vertex> vertexes;

        private Diag(List<Polygon.Vertex> vertexes) {
            this.vertexes = vertexes;
        }

        public List<Polygon.Vertex> getVertexes() {
            return vertexes;
        }
    }

    private Rect(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        super(ratio, type, centreTransform);
    }

    private Rect(double ratio, Type type) {
        super(ratio, type);
    }

    public static Pair<Polygon, Polygon.Vertex> instruction(Polygon polygon, Corner corner) {
        return Pair.of(
                polygon,
                corner.getVertex()
        );
    }

    public static Pair<Polygon, Polygon.Vertex> instruction(double ratio, Corner corner) {
        return Pair.of(
                rect(ratio, corner.getType()),
                corner.getVertex()
        );
    }

    public static Pair<Polygon, Polygon.Vertex> instruction(double ratio, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform, Corner corner) {
        return Pair.of(
                rect(ratio, corner.getType(), centreTransform),
                corner.getVertex()
        );
    }

    @Override
    protected double getHeightRatio() {
        return 0;
    }

    @Override
    protected List<Polygon.Vertex> getVertexes() {
        return Arrays.stream(Vertex.values()).collect(toList());
    }

    @Override
    protected Polygon newInstance(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        return new Rect(ratio, type, centreTransform);
    }

    public static enum Corner {
        DR(Vertex.ONE, Type.HOR),
        DL(Vertex.TWO, Type.HOR),
        UL(Vertex.THREE, Type.HOR),
        UR(Vertex.FOUR, Type.HOR),

        RIGHT(Vertex.ONE, Type.VER),
        DOWN(Vertex.TWO, Type.VER),
        LEFT(Vertex.THREE, Type.VER),
        UP(Vertex.FOUR, Type.VER);

        private final Polygon.Vertex vertex;
        private final Type type;

        Corner(Polygon.Vertex vertex, Type type) {
            this.vertex = vertex;
            this.type = type;
        }

        public Polygon.Vertex getVertex() {
            return vertex;
        }

        public Type getType() {
            return type;
        }
    }


    public static enum Vertex implements Polygon.Vertex {
        ONE(0),
        TWO(1),
        THREE(2),
        FOUR(3);

        private static final int N = 4;

        private static final Map<Type, List<Point2D>> vertexes;

        private final int index;

        private Vertex(int index) {
            this.index = index;
        }

        static {
            vertexes = new HashMap<>();
            vertexes.put(Polygon.Type.VER, calcVertexes(N, 0));
            vertexes.put(Polygon.Type.HOR, calcVertexes(N, 0.5));
        }

        @Override
        public Point2D getPoint(int offset, Polygon.Type type) {
            return vertexes.get(type).get((index + offset) % N);
        }

        @Override
        public int getIndex() {
            return index;
        }

        public Polygon.Vertex cast() {
            return this;
        }
    }

}
