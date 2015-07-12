package com.design.islamic.model;

import com.design.common.Polygon;
import com.design.common.RatioHelper;
import com.design.common.model.Path;
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
import static java.lang.Math.PI;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Hex extends Polygon {

    public static final List<Integer> ALL_VERTEX_INDEXES = IntStream.range(0, 6).boxed().collect(toList());

    public static Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform(double ratio, Polygon.Type type) {
        return Polygon.centreTransform(ratio, Vertex.ONE, type);
    }

    public static Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform(double ratio, Corner corner) {
        return Polygon.centreTransform(ratio, corner.getVertex(), corner.getType());
    }

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

    public static Function<Polygon, List<Path>> PERIMETER =
            p -> asList(new Path.Builder()
                    .startWith(p, Vertex.ONE)
                    .lineTo(p, Vertex.TWO)
                    .lineTo(p, Vertex.THREE)
                    .lineTo(p, Vertex.FOUR)
                    .lineTo(p, Vertex.FIVE)
                    .lineTo(p, Vertex.SIX)
                    .closed()
                    .build());

    public static Function<Polygon, List<Path>> INNER_TRIANGLES =
            p -> asList(
                    new Path.Builder()
                            .startWith(p, Vertex.ONE)
                            .lineTo(p, Vertex.THREE)
                            .lineTo(p, Vertex.FIVE)
                            .closed()
                            .build(),
                    new Path.Builder()
                            .startWith(p, Vertex.TWO)
                            .lineTo(p, Vertex.FOUR)
                            .lineTo(p, Vertex.SIX)
                            .closed()
                            .build()
            );

//    public static List<List<Polygon.Vertex>> INNER_TRIANGLES = asList(
//            asList((Polygon.Vertex) Vertex.ONE, Vertex.THREE, Vertex.FIVE, Vertex.ONE),
//            asList(Vertex.TWO, Vertex.FOUR, Vertex.SIX, Vertex.TWO)
//    );

    public static Function<Polygon, List<Path>> DIAGONAL_ONE = p -> Path.vertexPathsToPaths.apply(
            Polygon.toVertexPaths(p, asList(
                    asList(
                            Vertex.ONE,
                            Vertex.FOUR
                    )
            )));
    public static Function<Polygon, List<Path>> DIAGONAL_TWO = p -> Path.vertexPathsToPaths.apply(
            Polygon.toVertexPaths(p, asList(
                    asList(
                            Vertex.TWO,
                            Vertex.FIVE
                    )
            )));
    public static Function<Polygon, List<Path>> DIAGONAL_THREE = p -> Path.vertexPathsToPaths.apply(
            Polygon.toVertexPaths(p, asList(
                    asList(
                            Vertex.THREE,
                            Vertex.SIX
                    )
            )));
    public static Function<Polygon, List<Path>> DIAGONALS = p -> Path.vertexPathsToPaths.apply(
            Polygon.toVertexPaths(p, asList(
                    asList(
                            Vertex.ONE,
                            Vertex.FOUR
                    ),
                    asList(
                            Vertex.TWO,
                            Vertex.FIVE
                    ),
                    asList(
                            Vertex.THREE,
                            Vertex.SIX
                    )
            )));


//    public static List<List<Polygon.Vertex>> DIAGONALS = asList(
//            Diag.ONE.getVertexes(),
//            Diag.TWO.getVertexes(),
//            Diag.THREE.getVertexes()
//    );


    public static enum Diag {
        ONE(asList((Polygon.Vertex) Vertex.ONE, Vertex.FOUR)),
        TWO(asList((Polygon.Vertex) Vertex.TWO, Vertex.FIVE)),
        THREE(asList((Polygon.Vertex) Vertex.THREE, Vertex.SIX));

        private final List<Polygon.Vertex> vertexes;

        private Diag(List<Polygon.Vertex> vertexes) {
            this.vertexes = vertexes;
        }

        public List<Polygon.Vertex> getVertexes() {
            return vertexes;
        }
    }

    public static Polygon hex(double ratio, Type type) {
        return new Hex(ratio, type);
    }

    public static Polygon hex(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        return new Hex(ratio, type, centreTransform);
    }

    public static ActualVertex instruction(Polygon polygon, Corner corner) {
        return () -> Pair.of(
                polygon,
                corner.getVertex()
        );
    }

    public static ActualVertex instruction(double ratio, Corner corner) {
        return () -> Pair.of(
                hex(ratio, corner.getType()),
                corner.getVertex()
        );
    }

    public static ActualVertex instruction(double ratio, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform, Corner corner) {
        return () -> Pair.of(
                hex(ratio, corner.getType(), centreTransform),
                corner.getVertex()

        );

    }

    private Hex(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        super(ratio, type, centreTransform);
    }

    private Hex(double ratio, Type type) {
        super(ratio, type);
    }

    @Override
    protected double getHeightRatio() {
        return RatioHelper.Ratios.HEX.$H().apply(1.0);
    }

    @Override
    protected List<Polygon.Vertex> getVertexes() {
        return Arrays.stream(Vertex.values()).collect(toList());
    }

    @Override
    protected Polygon newInstance(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        return new Hex(ratio, type, centreTransform);
    }

    public static enum Corner {
        DR_V(Vertex.ONE, Type.VER),
        DOWN(Vertex.TWO, Type.VER),
        DL_V(Vertex.THREE, Type.VER),
        UL_V(Vertex.FOUR, Type.VER),
        UP(Vertex.FIVE, Type.VER),
        UR_V(Vertex.SIX, Type.VER),

        RIGHT(Vertex.ONE, Type.HOR),
        DR_H(Vertex.TWO, Type.HOR),
        DL_H(Vertex.THREE, Type.HOR),
        LEFT(Vertex.FOUR, Type.HOR),
        UL_H(Vertex.FIVE, Type.HOR),
        UR_H(Vertex.SIX, Type.HOR);

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
        FOUR(3),
        FIVE(4),
        SIX(5);

        private static final Map<Polygon.Type, List<Point2D>> vertexes;

        private final int index;

        private Vertex(int index) {
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
