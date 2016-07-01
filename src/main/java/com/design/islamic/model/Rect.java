package com.design.islamic.model;

import com.design.common.FinalPointTransition;
import com.design.common.PointTransition;
import com.design.common.PointsPath;
import com.design.common.Polygon;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.PolygonTools.calcVertexes;
import static java.util.stream.Collectors.toList;

public class Rect extends Polygon {

    public static final List<Integer> ALL_VERTEX_INDEXES = IntStream.range(0, 4).boxed().collect(toList());

    public static PointTransition pt(double ratio, Corner corner) {
        return PointTransition.of(ratio, corner.getVertex(), corner.getType());
    }


    private static Function<FinalPointTransition, Stream<PointsPath>> buildLines(double ratio, Type type, Stream<Vertex>... vertices) {
        return centre -> Stream.of(
                vertices
        ).map(toPath(centre, ratio, type));
    }

    public static Function<Stream<Vertex>, PointsPath> toPath(FinalPointTransition centre, double ratio, Type type) {
        return vertexes -> PointsPath.of(
                vertexes.map(v -> PointTransition.of(ratio, v, type))
                        .map(centre::append).collect(toList())
        );
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> buildLines(double ratio, Stream<Corner>... corners) {
        return centre -> Stream.of(
                corners
        ).map(toPath(centre, ratio));
    }

    public static Function<Stream<Corner>, PointsPath> toPath(FinalPointTransition centre, double ratio) {
        return corners -> PointsPath.of(
                corners.map(corner -> PointTransition.of(ratio, corner.getVertex(), corner.getType()))
                        .map(centre::append).collect(toList())
        );
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> perimeter(double ratio, Type type) {
        return buildLines(
                ratio, type,
                Stream.of(
                        Vertex.ONE,
                        Vertex.TWO,
                        Vertex.THREE,
                        Vertex.FOUR,
                        Vertex.ONE
                )
        );
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonals(double ratio, Type type) {
        return buildLines(
                ratio, type,
                Stream.of(
                        Vertex.ONE,
                        Vertex.THREE
                ),
                Stream.of(
                        Vertex.TWO,
                        Vertex.FOUR
                )
        );
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalVertical(double ratio) {
        return buildLines(ratio,
                Stream.of(
                        Corner.UP,
                        Corner.DOWN
                ));
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalHorizontal(double ratio) {
        return buildLines(ratio,
                Stream.of(
                        Corner.RIGHT,
                        Corner.LEFT
                ));
    }


    public enum Corner {
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


    public enum Vertex implements Polygon.Vertex {
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
