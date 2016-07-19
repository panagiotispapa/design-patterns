package com.design.islamic.model;

import com.design.common.FinalPointTransition;
import com.design.common.PointsPath;
import com.design.common.Polygon;
import com.google.common.collect.ImmutableMap;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.PointsPath.buildLines;
import static com.design.common.PolygonTools.newEdgeAt;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Rect extends Polygon {

    public static final List<Integer> ALL_VERTEX_INDEXES = IntStream.range(0, 4).boxed().collect(toList());


    public static Function<FinalPointTransition, Stream<PointsPath>> perimeter(double ratio, Type type) {
        return type == Type.HOR ?
                buildLines(
                        ratio,
                        Stream.of(
                                Vertex.UR,
                                Vertex.UL,
                                Vertex.DL,
                                Vertex.DR,
                                Vertex.UR
                        )
                ) :
                buildLines(
                        ratio,
                        Stream.of(
                                Vertex.UP,
                                Vertex.LEFT,
                                Vertex.DOWN,
                                Vertex.RIGHT,
                                Vertex.UP
                        )
                );

    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonals(double ratio, Type type) {
        return type == Type.HOR ?
                buildLines(
                        ratio,
                        Stream.of(
                                Vertex.UL,
                                Vertex.DR
                        ),
                        Stream.of(
                                Vertex.UR,
                                Vertex.DL
                        )
                ) :
                buildLines(
                        ratio,
                        Stream.of(
                                Vertex.UP,
                                Vertex.DOWN
                        ),
                        Stream.of(
                                Vertex.LEFT,
                                Vertex.RIGHT
                        )
                );
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalVertical(double ratio) {
        return buildLines(ratio,
                Stream.of(
                        Vertex.UP,
                        Vertex.DOWN
                ));
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalHorizontal(double ratio) {
        return buildLines(ratio,
                Stream.of(
                        Vertex.RIGHT,
                        Vertex.LEFT
                ));
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
