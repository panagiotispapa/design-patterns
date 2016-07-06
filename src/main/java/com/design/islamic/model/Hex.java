package com.design.islamic.model;

import com.design.common.FinalPointTransition;
import com.design.common.PointTransition;
import com.design.common.PointsPath;
import com.design.common.Polygon;
import com.google.common.collect.ImmutableMap;

import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.design.common.PolygonTools.calcVertexes;
import static com.design.common.PolygonTools.newEdgeAt;
import static java.lang.Math.PI;
import static java.util.stream.Collectors.toList;

public class Hex extends Polygon {

    public static final List<Integer> ALL_VERTEX_INDEXES = IntStream.range(0, 6).boxed().collect(toList());

    public static final int N = 6;

    public static final double PHI = (2.0 * PI) / 6.0;

//    public static List<List<Polygon.Vertex>> PERIMETER = asList(
//            asList(
//                    (Polygon.Vertex) Vertex.ONE,
//                    Vertex.TWO,
//                    Vertex.THREE,
//                    Vertex.FOUR,
//                    Vertex.FIVE,
//                    Vertex.SIX,
//                    Vertex.ONE
//            )
//    );

    public static Function<FinalPointTransition, Stream<PointsPath>> perimeter(double ratio, Type type) {
        return buildLines(
                ratio, type,
                Stream.of(
                        Vertex.ONE,
                        Vertex.TWO,
                        Vertex.THREE,
                        Vertex.FOUR,
                        Vertex.FIVE,
                        Vertex.SIX,
                        Vertex.ONE
                )
        );
    }


    public static Function<FinalPointTransition, Stream<PointsPath>> innerTriangles(double ratio, Type type) {
        return buildLines(
                ratio, type,
                Stream.of(
                        Vertex.ONE,
                        Vertex.THREE,
                        Vertex.FIVE,
                        Vertex.ONE
                ),
                Stream.of(
                        Vertex.TWO,
                        Vertex.FOUR,
                        Vertex.SIX,
                        Vertex.TWO
                )

        );
    }

    private static Function<FinalPointTransition, Stream<PointsPath>> buildLines(double ratio, Type type, Stream<Vertex>... vertices) {
        return centre -> Stream.of(
                vertices
        ).map(toPath(centre, ratio, type));
    }

    private static Function<FinalPointTransition, Stream<PointsPath>> buildLines(double ratio, Stream<Corner>... corners) {
        return centre -> Stream.of(
                corners
        ).map(toPath(centre, ratio));
    }


    public static Function<Stream<Vertex>, PointsPath> toPath(FinalPointTransition centre, double ratio, Type type) {
        return vertexes -> PointsPath.of(
                vertexes.map(v -> PointTransition.of(ratio, v, type))
                        .map(centre::append).collect(toList())
        );
    }


    public static Function<Stream<Corner>, PointsPath> toPath(FinalPointTransition centre, double ratio) {
        return conrners -> PointsPath.of(
                conrners.map(corner -> PointTransition.of(ratio, corner.getVertex(), corner.getType()))
                        .map(centre::append).collect(toList())
        );
    }


    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalLeftToRightVert(double ratio) {
        return buildLines(ratio,
                Stream.of(
                        Corner.DL_V,
                        Corner.UR_V
                ));
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalLeftToRightHor(double ratio) {
        return buildLines(ratio,
                Stream.of(
                        Corner.DL_H,
                        Corner.UR_H
                ));
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalRightToLeftVert(double ratio) {
        return buildLines(ratio,
                Stream.of(
                        Corner.DR_V,
                        Corner.UL_V
                ));
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalRightToLeftHor(double ratio) {
        return buildLines(ratio,
                Stream.of(
                        Corner.DR_H,
                        Corner.UL_H
                ));
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

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalOne(double ratio, Type type) {
        return buildLines(
                ratio, type,

                Stream.of(
                        Vertex.ONE,
                        Vertex.FOUR
                )
        );
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalTwo(double ratio, Type type) {
        return buildLines(
                ratio, type,

                Stream.of(
                        Vertex.TWO,
                        Vertex.FIVE
                )
        );
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonalThree(double ratio, Type type) {
        return buildLines(
                ratio, type,

                Stream.of(
                        Vertex.THREE,
                        Vertex.SIX
                )
        );
    }

    public static Function<FinalPointTransition, Stream<PointsPath>> diagonals(double ratio, Type type) {
        return buildLines(
                ratio, type,
                Stream.of(
                        Vertex.ONE,
                        Vertex.FOUR
                ),
                Stream.of(
                        Vertex.TWO,
                        Vertex.FIVE
                ),
                Stream.of(
                        Vertex.THREE,
                        Vertex.SIX
                )
        );
    }


    public static PointTransition pt(double ratio, Corner corner) {
        return PointTransition.of(ratio, corner.getVertex(), corner.getType());
    }


    public enum Corner {
        DR_V(Vertex.ONE, Type.VER),
        DOWN(Vertex.TWO, Type.VER),
        DL_V(Vertex.THREE, Type.VER),
        UL_V(Vertex.FOUR, Type.VER),
        UP(Vertex.FIVE, Type.VER),
        UR_V(Vertex.SIX, Type.VER),
//
//
        RIGHT(Vertex.ONE, Type.HOR),
        DR_H(Vertex.TWO, Type.HOR),
        DL_H(Vertex.THREE, Type.HOR),
        LEFT(Vertex.FOUR, Type.HOR),
        UL_H(Vertex.FIVE, Type.HOR),
        UR_H(Vertex.SIX, Type.HOR);
//
//        DR_V(VertexNew.DR_V, Type.VER),
//        DOWN(VertexNew.DOWN, Type.VER),
//        DL_V(VertexNew.DL_V, Type.VER),
//        UL_V(VertexNew.UL_V, Type.VER),
//        UP(VertexNew.UP, Type.VER),
//        UR_V(VertexNew.UR_V, Type.VER),
//
//        RIGHT(VertexNew.RIGHT, Type.HOR),
//        DR_H(VertexNew.DR_H, Type.HOR),
//        DL_H(VertexNew.DL_H, Type.HOR),
//        LEFT(VertexNew.LEFT, Type.HOR),
//        UL_H(VertexNew.UL_H, Type.HOR),
//        UR_H(VertexNew.UR_H, Type.HOR);

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

    public static Map<Polygon.Type, Map<Polygon.Vertex, Polygon.Vertex>> verticalMapping = ImmutableMap.of(
            Type.VER, (Map)
                    new ImmutableMap.Builder<>()
                            .put(Vertex.ONE, Vertex.SIX)
                            .put(Vertex.TWO, Vertex.FIVE)
                            .put(Vertex.THREE, Vertex.FOUR)
                            .put(Vertex.FOUR, Vertex.THREE)
                            .put(Vertex.FIVE, Vertex.TWO)
                            .put(Vertex.SIX, Vertex.ONE)
                            .build()
            ,
            Type.HOR, (Map)
                    new ImmutableMap.Builder<>()
                            .put(Vertex.ONE, Vertex.ONE)
                            .put(Vertex.TWO, Vertex.SIX)
                            .put(Vertex.THREE, Vertex.FIVE)
                            .put(Vertex.FOUR, Vertex.FOUR)
                            .put(Vertex.FIVE, Vertex.THREE)
                            .put(Vertex.SIX, Vertex.TWO)
                            .build()

    );


    public static Map<Polygon.Type, Map<Polygon.Vertex, Polygon.Vertex>> horizontallMapping = ImmutableMap.of(
            Type.VER, (Map)
                    new ImmutableMap.Builder<>()
                            .put(Vertex.ONE, Vertex.THREE)
                            .put(Vertex.TWO, Vertex.TWO)
                            .put(Vertex.THREE, Vertex.ONE)
                            .put(Vertex.FOUR, Vertex.SIX)
                            .put(Vertex.FIVE, Vertex.FIVE)
                            .put(Vertex.SIX, Vertex.FOUR)
                            .build()
            ,
            Type.HOR, (Map)
                    new ImmutableMap.Builder<>()
                            .put(Vertex.ONE, Vertex.FOUR)
                            .put(Vertex.TWO, Vertex.THREE)
                            .put(Vertex.THREE, Vertex.TWO)
                            .put(Vertex.FOUR, Vertex.ONE)
                            .put(Vertex.FIVE, Vertex.SIX)
                            .put(Vertex.SIX, Vertex.FIVE)
                            .build()

    );


    public static Function<PointTransition, PointTransition> mirrorVert = mirror(verticalMapping);
    public static Function<PointTransition, PointTransition> mirrorHor = mirror(horizontallMapping);

    private static Function<PointTransition, PointTransition> mirror(Map<Polygon.Type, Map<Polygon.Vertex, Polygon.Vertex>> mapping) {
        return initial ->
                PointTransition.of(initial.getRatio(), mapping.get(initial.getType()).get(initial.getVertex()), initial.getType());
    }

    public enum VertexNew implements Polygon.Vertex {
        DR_V(0, 0.5),
        DOWN(1, 0.5),
        DL_V(2, 0.5),
        UL_V(3, 0.5),
        UP(4, 0.5),
        UR_V(5, 0.5),

        RIGHT(0, 0),
        DR_H(1, 0),
        DL_H(2, 0),
        LEFT(3, 0),
        UL_H(4, 0),
        UR_H(5, 0);

        private final int index;
        private final Point2D point;

        VertexNew(int index, double offset) {
            this.index = index;
            this.point = newEdgeAt(index, offset, 6);
        }


        @Override
        public Point2D getPoint(int offset, Type type) {
            return point;
        }

        @Override
        public int getIndex() {
            return index;
        }
    }

    public enum Vertex implements Polygon.Vertex {
        ONE(0),
        TWO(1),
        THREE(2),
        FOUR(3),
        FIVE(4),
        SIX(5);

        private static final Map<Polygon.Type, List<Point2D>> vertexes;

        private final int index;

        Vertex(int index) {
            this.index = index;
        }

        static {
            vertexes = new HashMap<>();
            vertexes.put(Polygon.Type.HOR, calcVertexes(N, 0));
            vertexes.put(Polygon.Type.VER, calcVertexes(N, 0.5));
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
