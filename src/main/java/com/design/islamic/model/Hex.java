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
import static java.lang.Math.PI;
import static java.util.Arrays.asList;

public class Hex extends Polygon {

    public static final Sequence<Integer> ALL_VERTEX_INDEXES = range(0, 5);

    private static final int N = 6;

    public static final double PHI = (2.0 * PI) / 6.0;

    public static Function<FinalPointTransition, Sequence<PointsPath>> perimeter(double ratio, Type type) {

        return type == Type.HOR ?
                p -> p.toLine(
                        ratio,
                        vp(
                                Vertex.RIGHT,
                                Vertex.DR_H,
                                Vertex.DL_H,
                                Vertex.LEFT,
                                Vertex.UL_H,
                                Vertex.UR_H,
                                Vertex.RIGHT
                        )
                ) :
                p -> p.toLine(
                        ratio,
                        vp(
                                Vertex.DR_V,
                                Vertex.DOWN,
                                Vertex.DL_V,
                                Vertex.UL_V,
                                Vertex.UP,
                                Vertex.UR_V,
                                Vertex.DR_V
                        )
                );


    }


    public static Function<FinalPointTransition, Sequence<PointsPath>> innerTriangles(double ratio, Type type) {
        return type == Type.HOR ?
                p -> p.toLine(
                        ratio,
                        vp(
                                Vertex.RIGHT,
                                Vertex.DL_H,
                                Vertex.UL_H,
                                Vertex.RIGHT
                        ),
                        vp(
                                Vertex.DR_H,
                                Vertex.LEFT,
                                Vertex.UR_H,
                                Vertex.DR_H
                        )

                ) :
                p -> p.toLine(
                        ratio,
                        vp(
                                Vertex.DR_V,
                                Vertex.DL_V,
                                Vertex.UP,
                                Vertex.DR_V
                        ),
                        vp(
                                Vertex.DOWN,
                                Vertex.UL_V,
                                Vertex.UR_V,
                                Vertex.DOWN
                        )

                );


    }

    public static Function<FinalPointTransition, Sequence<PointsPath>> diagonalLeftToRightVert(double ratio) {
        return p -> p.toLine(ratio,
                vp(
                        Vertex.DL_V,
                        Vertex.UR_V
                ));
    }

    public static Function<FinalPointTransition, Sequence<PointsPath>> diagonalLeftToRightHor(double ratio) {
        return p -> p.toLine(ratio,
                vp(
                        Vertex.DL_H,
                        Vertex.UR_H
                ));
    }

    public static Function<FinalPointTransition, Sequence<PointsPath>> diagonalRightToLeftVert(double ratio) {
        return p -> p.toLine(ratio,
                vp(
                        Vertex.DR_V,
                        Vertex.UL_V
                ));
    }

    public static Function<FinalPointTransition, Sequence<PointsPath>> diagonalRightToLeftHor(double ratio) {
        return p -> p.toLine(ratio,
                vp(
                        Vertex.DR_H,
                        Vertex.UL_H
                ));
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


    public static Function<FinalPointTransition, Sequence<PointsPath>> diagonals(double ratio, Type type) {
        return type == Type.HOR ?
                p -> p.toLine(
                        ratio,
                        vp(
                                Vertex.RIGHT,
                                Vertex.LEFT
                        ),
                        vp(
                                Vertex.DR_H,
                                Vertex.UL_H
                        ),
                        vp(
                                Vertex.DL_H,
                                Vertex.UR_H
                        )
                ) :
                p -> p.toLine(
                        ratio,
                        vp(
                                Vertex.DR_V,
                                Vertex.UL_V
                        ),
                        vp(
                                Vertex.DOWN,
                                Vertex.UP
                        ),
                        vp(
                                Vertex.DL_V,
                                Vertex.UR_V
                        )
                );

    }


    private static Map<Polygon.Vertex, Polygon.Vertex> verticalMapping = (Map)
            new ImmutableMap.Builder<>()
                    .put(Vertex.DR_V, Vertex.UR_V)
                    .put(Vertex.DOWN, Vertex.UP)
                    .put(Vertex.DL_V, Vertex.UL_V)
                    .put(Vertex.UL_V, Vertex.DL_V)
                    .put(Vertex.UP, Vertex.DOWN)
                    .put(Vertex.UR_V, Vertex.DR_V)

                    .put(Vertex.RIGHT, Vertex.RIGHT)
                    .put(Vertex.DR_H, Vertex.UR_H)
                    .put(Vertex.DL_H, Vertex.UL_H)
                    .put(Vertex.LEFT, Vertex.LEFT)
                    .put(Vertex.UL_H, Vertex.DL_H)
                    .put(Vertex.UR_H, Vertex.DR_H)

                    .build();


    private static Map<Polygon.Vertex, Polygon.Vertex> horizontalMapping = (Map)
            new ImmutableMap.Builder<>()
                    .put(Vertex.DR_V, Vertex.DL_V)
                    .put(Vertex.DOWN, Vertex.DOWN)
                    .put(Vertex.DL_V, Vertex.DR_V)
                    .put(Vertex.UL_V, Vertex.UR_V)
                    .put(Vertex.UP, Vertex.UP)
                    .put(Vertex.UR_V, Vertex.UL_V)

                    .put(Vertex.RIGHT, Vertex.LEFT)
                    .put(Vertex.DR_H, Vertex.DL_H)
                    .put(Vertex.DL_H, Vertex.DR_H)
                    .put(Vertex.LEFT, Vertex.RIGHT)
                    .put(Vertex.UL_H, Vertex.UR_H)
                    .put(Vertex.UR_H, Vertex.UL_H)

                    .build();


    public static Function<PointTransition, PointTransition> mirrorVert = mirror(verticalMapping);
    public static Function<PointTransition, PointTransition> mirrorHor = mirror(horizontalMapping);

    private static Function<PointTransition, PointTransition> mirror(Map<Polygon.Vertex, Polygon.Vertex> mapping) {
        return initial ->
                PointTransition.pt(initial.getRatio(), mapping.get(initial.getVertex()));
    }

    public enum Vertex implements Polygon.Vertex {
        DR_V(0, 0.5, Type.VER),
        DOWN(1, 0.5, Type.VER),
        DL_V(2, 0.5, Type.VER),
        UL_V(3, 0.5, Type.VER),
        UP(4, 0.5, Type.VER),
        UR_V(5, 0.5, Type.VER),

        RIGHT(0, 0, Type.HOR),
        DR_H(1, 0, Type.HOR),
        DL_H(2, 0, Type.HOR),
        LEFT(3, 0, Type.HOR),
        UL_H(4, 0, Type.HOR),
        UR_H(5, 0, Type.HOR);

        private final int index;
        private final Point2D point;
        private final Type type;
        private final static Map<Type, List<Polygon.Vertex>> vertexMap;

        static {
            vertexMap = ImmutableMap.of(
                    Type.VER, asList(
                            DR_V,
                            DOWN,
                            DL_V,
                            UL_V,
                            UP,
                            UR_V
                    ),
                    Type.HOR, asList(
                            RIGHT,
                            DR_H,
                            DL_H,
                            LEFT,
                            UL_H,
                            UR_H
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
