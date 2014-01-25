package com.design.islamic.model.tiles.shapes;

import com.design.islamic.model.Centre;
import com.design.islamic.model.PolygonTools;
import com.design.islamic.model.tiles.svg.Style;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.jamesmurty.utils.XMLBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.geom.Point2D;
import java.util.List;

import static com.google.common.collect.Iterables.transform;
import static java.lang.Math.*;

public final class Shapes {

    public final static int HEX_N = 6;

    public final static double HEX_PHI = (2.0 * PI) / HEX_N;

    public static List<Point2D> hexPoints = computePoints(HEX_N, HEX_PHI);
    public static List<Point2D> hexPointsAlt = computePointsAlt(HEX_N, HEX_PHI);


    private Shapes() {

    }


    private static List<Point2D> computePoints(int n, double phi) {

        ImmutableList.Builder<Point2D> posBuilder = ImmutableList.builder();
        for (int k = 0; k < n; k++) {
            posBuilder.add(new Centre(
                    cos(phi * k),
                    sin(phi * k)
            ));
        }
        return posBuilder.build();
    }


    private static List<Point2D> computePointsAlt(int n, double phi) {

        ImmutableList.Builder<Point2D> posBuilder = ImmutableList.builder();
        for (int k = 0; k < n; k++) {
            posBuilder.add(new Centre(
                    cos(phi * (k + 0.5)),
                    sin(phi * (k + 0.5))
            ));
        }
        return posBuilder.build();
    }


    public static List<Hexagon> newHexagons(Iterable<? extends Point2D> centres, final double r, final Style style) {
        return ImmutableList.copyOf(transform(centres, new Function<Point2D, Hexagon>() {
            @Override
            public Hexagon apply(Point2D centre) {
                return newHexagon(centre, r, style);
            }
        }));
    }

    public static Hexagon newHexagon(Point2D centre, double r, Style style) {
        return new HexagonImpl(centre, r, style);
    }

    public static List<Circle> newCircles(Iterable<Point2D> centres, final double r, final Style style) {
        System.out.println("ss " + Iterables.size(centres));

        return ImmutableList.copyOf(transform(centres, new Function<Point2D, Circle>() {
            @Override
            public Circle apply(Point2D centre) {
                return newCircle(centre, r, style);
            }
        }));
    }

    public static Circle newCircle(Point2D centre, double r, Style style) {
        return new CircleImpl(centre, r, style);
    }

    private static class HexagonImpl implements Hexagon {
        private final Point2D centre;
        private final Style style;

        private final List<Point2D> edges;

        private HexagonImpl(Point2D centre, double r, Style style) {
            this.centre = centre;
            this.style = style;

            this.edges = PolygonTools.calculateHexEdges(centre, r);

        }

        @Override
        public Point2D getCentre() {
            return centre;
        }

        @Override
        public List<Point2D> getEdges() {
            return edges;
        }

        @Override
        public XMLBuilder toSvgXML() {
            StringBuilder builder = new StringBuilder();

            for (Point2D edge : this.edges) {
                builder.append(String.format("%s,%s ", edge.getX(), edge.getY()));
            }

            try {
                return XMLBuilder.create("polygon")
                        .a("points", builder.toString())
                        .a("style", style.toString())
                        ;
            } catch (ParserConfigurationException e) {
                return null;
            }
        }
    }

    private static class CircleImpl implements Circle {
        private final Point2D centre;
        private final double r;
        private final Style style;

        private CircleImpl(Point2D centre, double r, Style style) {
            this.centre = centre;
            this.r = r;
            this.style = style;
        }

        @Override
        public Point2D getCentre() {
            return centre;
        }

        @Override
        public double getR() {
            return r;
        }

        @Override
        public XMLBuilder toSvgXML() {
            try {
                return XMLBuilder.create("circle")
                        .a("cx", String.valueOf(this.centre.getX()))
                        .a("cy", String.valueOf(this.centre.getY()))
                        .a("r", String.valueOf(this.r))
                        .a("style", style.toString())
                        ;
            } catch (ParserConfigurationException e) {
                return null;
            }
        }
    }
}
