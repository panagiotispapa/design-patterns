package com.design.islamic.model;

import com.design.common.Polygon;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.tuple.Triple;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.design.common.PolygonTools.calcVertexes;
import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class Hex extends Polygon {

    public static final int N = 6;

    public static final double PHI = (2.0 * PI) / 6.0;

    public static final double HEIGHT_RATIO = cos(PHI / 2.0);
    public static List<List<Polygon.Vertex>> NONE = emptyList();
    public static List<List<Polygon.Vertex>> PERIMETER = asList(
            asList(
                    (Polygon.Vertex) Vertex.ONE,
                    Vertex.TWO,
                    Vertex.THREE,
                    Vertex.FOUR,
                    Vertex.FIVE,
                    Vertex.SIX,
                    Vertex.ONE
            )
    );

    public static List<List<Polygon.Vertex>> INNER_TRIANGLES = asList(
            asList((Polygon.Vertex) Vertex.ONE, Vertex.THREE, Vertex.FIVE, Vertex.ONE),
            asList(Vertex.TWO, Vertex.FOUR, Vertex.SIX, Vertex.TWO)
    );

    public static List<List<Polygon.Vertex>> DIAGONALS = asList(
            Diag.ONE.getVertexes(),
            Diag.TWO.getVertexes(),
            Diag.THREE.getVertexes()
    );

    public static List<List<Polygon.Vertex>> ALL_LINES = Lists.newArrayList(
            Iterables.concat(DIAGONALS, INNER_TRIANGLES, PERIMETER)
    );

    public static enum Sides {
        ONE(asList((Polygon.Vertex) Vertex.ONE, Vertex.TWO)),
        TWO(asList((Polygon.Vertex) Vertex.TWO, Vertex.THREE)),
        THREE(asList((Polygon.Vertex) Vertex.THREE, Vertex.FOUR)),
        FOUR(asList((Polygon.Vertex) Vertex.FOUR, Vertex.FIVE)),
        FIVE(asList((Polygon.Vertex) Vertex.FIVE, Vertex.SIX)),
        SIX(asList((Polygon.Vertex) Vertex.TWO, Vertex.THREE));

        private final List<Polygon.Vertex> vertexes;

        private Sides(List<Polygon.Vertex> vertexes) {
            this.vertexes = vertexes;
        }
    }

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

    private Hex(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        super(ratio, type, centreTransform);
    }

    private Hex(double ratio, Type type) {
        super(ratio, type);
    }

    @Override
    protected double getHeightRatio() {
        return HEIGHT_RATIO;
    }

    @Override
    protected List<Polygon.Vertex> getVertexes() {
        return Arrays.stream(Vertex.values()).collect(toList());
    }

    @Override
    protected Polygon newInstance(double ratio, Type type, Function<Triple<Point2D, Double, Integer>, Triple<Point2D, Double, Integer>> centreTransform) {
        return new Hex(ratio, type, centreTransform);
    }

    //    Iterables.transform()
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
            return vertexes.get(type).get((index + offset) % 6);
        }

        @Override
        public int getIndex() {
            return index;
        }

    }

}
