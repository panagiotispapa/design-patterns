package com.design.islamic.model;

import com.design.common.*;
import com.google.common.collect.ImmutableMap;
import com.googlecode.totallylazy.Sequence;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.design.common.PolygonTools.newEdgeAt;
import static com.design.common.VertexPath.vp;
import static com.googlecode.totallylazy.numbers.Integers.range;
import static java.util.Arrays.asList;

public class Rect extends Polygon {

    public static final Sequence<Integer> ALL_VERTEX_INDEXES = range(0, 3);

    public static Function<FinalPointTransition, Sequence<PointsPath>> perimeter(double ratio, Type type) {
        return type == Type.HOR ?
                p -> p.toLine(
                        ratio,
                        vp(
                                Vertex.UR,
                                Vertex.UL,
                                Vertex.DL,
                                Vertex.DR,
                                Vertex.UR
                        )
                ) :
                p -> p.toLine(
                        ratio,
                        vp(
                                Vertex.UP,
                                Vertex.LEFT,
                                Vertex.DOWN,
                                Vertex.RIGHT,
                                Vertex.UP
                        )
                );

    }

    public static Function<FinalPointTransition, Sequence<PointsPath>> diagonals(double ratio, Type type) {
        return type == Type.HOR ?
                p -> p.toLine(
                        ratio,
                        vp(
                                Vertex.UL,
                                Vertex.DR
                        ),
                        vp(
                                Vertex.UR,
                                Vertex.DL
                        )
                ) :
                p -> p.toLine(
                        ratio,
                        vp(
                                Vertex.UP,
                                Vertex.DOWN
                        ),
                        vp(
                                Vertex.LEFT,
                                Vertex.RIGHT
                        )
                );
    }

    public static Function<FinalPointTransition, Sequence<PointsPath>> diagonalVertical(double ratio) {
        return p -> p.toLine(ratio,
                vp(
                        Vertex.UP,
                        Vertex.DOWN
                ));
    }

    public static Function<FinalPointTransition, Sequence<PointsPath>> diagonalHorizontal(double ratio) {
        return p -> p.toLine(ratio,
                vp(
                        Vertex.RIGHT,
                        Vertex.LEFT
                ));
    }

    private static Map<Polygon.Vertex, Polygon.Vertex> verticalMapping = (Map)
            new ImmutableMap.Builder<>()
                    .put(Vertex.DR, Vertex.UR)
                    .put(Vertex.DL, Vertex.UL)
                    .put(Vertex.UL, Vertex.DL)
                    .put(Vertex.UR, Vertex.DR)

                    .put(Vertex.RIGHT, Vertex.RIGHT)
                    .put(Vertex.LEFT, Vertex.LEFT)
                    .put(Vertex.UP, Vertex.DOWN)
                    .put(Vertex.DOWN, Vertex.UP)
                    .build();


    private static Map<Polygon.Vertex, Polygon.Vertex> horizontalMapping = (Map)
            new ImmutableMap.Builder<>()
                    .put(Vertex.DR, Vertex.DL)
                    .put(Vertex.UR, Vertex.UL)
                    .put(Vertex.UL, Vertex.UR)
                    .put(Vertex.DL, Vertex.DR)

                    .put(Vertex.RIGHT, Vertex.LEFT)
                    .put(Vertex.LEFT, Vertex.RIGHT)
                    .put(Vertex.UP, Vertex.UP)
                    .put(Vertex.DOWN, Vertex.DOWN)

                    .build();

    public static Function<PointTransition, PointTransition> mirrorVert = mirror(verticalMapping);
    public static Function<PointTransition, PointTransition> mirrorHor = mirror(horizontalMapping);

    private static Function<PointTransition, PointTransition> mirror(Map<Polygon.Vertex, Polygon.Vertex> mapping) {
        return initial ->
                PointTransition.pt(initial.getRatio(), mapping.get(initial.getVertex()));
    }


    public enum Vertex implements Polygon.Vertex {
        RIGHT(0, 0, Type.VER),
        DOWN(1, 0, Type.VER),
        LEFT(2, 0, Type.VER),
        UP(3, 0, Type.VER),

        DR(0, 0.5, Type.HOR),
        DL(1, 0.5, Type.HOR),
        UL(2, 0.5, Type.HOR),
        UR(3, 0.5, Type.HOR);

        private final int index;
        private final Point2D point;
        private final Type type;
        private final static Map<Type, List<Polygon.Vertex>> vertexMap;
        private static final int N = 4;

        static {
            vertexMap = ImmutableMap.of(
                    Type.VER, asList(
                            RIGHT,
                            DOWN,
                            LEFT,
                            UP
                    ),
                    Type.HOR, asList(
                            DR,
                            DL,
                            UL,
                            UR
                    )
            );

        }

        Vertex(int index, double offset, Type type) {
            this.index = index;
            this.type = type;
            this.point = newEdgeAt(index, offset, N);
        }

        @Override
        public Polygon.Vertex withOffset(int offset) {
            return vertexMap.get(type).get((index + offset) % N);
        }

        @Override
        public Point2D getPoint() {
            return point;
        }

    }


}
