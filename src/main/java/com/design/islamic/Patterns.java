package com.design.islamic;

import com.design.islamic.model.Centre;
import com.design.islamic.model.tiles.svg.SvgFactory;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Set;

import static com.design.islamic.GenericTools.*;
import static com.design.islamic.model.tiles.svg.SvgFactory.buildSvg;
import static com.design.islamic.model.tiles.svg.SvgFactory.newPolygon;
import static com.design.islamic.model.tiles.svg.SvgFactory.newStyle;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.retainAll;
import static com.google.common.collect.Iterables.transform;
import static java.lang.Math.*;
import static java.lang.String.format;

public final class Patterns {

    public final static int HEX_N = 6;

    public final static double HEX_PHI = (2.0 * PI) / HEX_N;

    public static List<Point2D> hexPoints = computePoints(HEX_N, HEX_PHI);
    public static List<Point2D> hexPointsAlt = computePointsAlt(HEX_N, HEX_PHI);


    private Patterns() {

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

    public static Set<Point2D> edgesFromCentres(Iterable<Point2D> centres, double r) {

        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        builder.addAll(calculateHexEdges(centres, r));
        builder.addAll(centres);

        return builder.build();

    }

    public static Set<Point2D> calculateHexEdges(Iterable<Point2D> centres, double r) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(calculateHexEdges(centre, r));
        }

        return builder.build();
    }

    public static List<Point2D> calculateHexEdges(Point2D centre, double r) {
        List<Point2D> edges = clonePoints(Patterns.hexPoints);
        scalePoints(edges, r);
        translatePoints(edges, centre);

        return edges;

    }

    public static Set<Point2D> calculateNewCellCentres(Point2D centre, double r, int level) {
        return calculateNewCellCentres(calculateNewCellCentres(centre, r), r, level);
    }

    private static Set<Point2D> calculateNewCellCentres(Iterable<Point2D> centres, double r, int level) {

        if (level == 1) {
            return Sets.newHashSet(centres);
        } else {
            return calculateNewCellCentres(calculateNewCellCentres(centres, r), r, --level);
        }

    }


    private static List<Point2D> calculateNewCellCentres(Point2D centre, double r) {
        List<Point2D> newPoints = clonePoints(Patterns.hexPointsAlt);

        scalePoints(newPoints, 2.0 * r * cos(HEX_PHI / 2.0));
        translatePoints(newPoints, centre);
        newPoints.add(centre);

        return newPoints;
    }

    private static Set<Point2D> calculateNewCellCentres(Iterable<Point2D> centres, double r) {


        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(calculateNewCellCentres(centre, r));
        }


        return builder.build();

    }

    public static XMLBuilder buildHexPattern1(Iterable<Point2D> centres, final double r, int width, int height) {

        final String style = newStyle("yellow", "green", 2, 1, 1);

        List<XMLBuilder> hexagons = ImmutableList.copyOf(transform(centres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newHexagon(centre, r, style);
            }
        }));

        return buildSvg(width, height,
                concat(
                        hexagons
//                        , highlightPoints(centres)
                )
        );
    }


    public static XMLBuilder buildHexPattern2(Iterable<Point2D> centres, final double r, int width, int height) {

        final String style = newStyle("yellow", "green", 1, 1, 0);

        List<XMLBuilder> hexagons = ImmutableList.copyOf(transform(centres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newHexagon(centre, r, style);
            }
        }));


        final String style2 = newStyle("blue", "green", 1, 0, 1);

        List<XMLBuilder> circles = ImmutableList.copyOf(transform(edgesFromCentres(centres, r), new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D edge) {
                return SvgFactory.newCircle(edge, r, style2);
            }
        }));

        return buildSvg(width, height,
                concat(
                        hexagons
                        , circles
                )
        );
    }


    public static XMLBuilder buildHexPattern3(Iterable<Point2D> centres, final double r, int width, int height) {

        final String style = newStyle("blue", "green", 0, 1, 0);

        List<XMLBuilder> hexagons = ImmutableList.copyOf(transform(centres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newHexagon(centre, r, style);
            }
        }));

        final String style2 = newStyle("yellow", "yellow", 0, 1, 0);
        final String style3 = newStyle("yellow", "yellow", 0, 1, 0);

        List<XMLBuilder> layer2 = ImmutableList.copyOf(transform(centres, new Function<Point2D, XMLBuilder>() {
            int index = 0;
            @Override
            public XMLBuilder apply(Point2D centre) {
                String style = index %2 == 0 ? style2 : style3;
                index++;
                return newHexTile1(centre, r, style);
            }
        }));



        return buildSvg(width, height,
                concat(
                        hexagons,
                        layer2
//                        , highlightPoints(centres)
                )
        );
    }


    public static XMLBuilder newHexTile1(Point2D centre, double r, String style) {
        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);


        final double d1 = r * cos(Patterns.HEX_PHI / 2.0);
        final double d2 = d1 - (r - d1);

        List<Point2D> edgesAlt = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAlt, d2);
        translatePoints(edgesAlt, centre);


        StringBuilder builder = new StringBuilder();

        for(int i=0;i<edges.size();i++) {
            builder.append(format("%s,%s ", edges.get(i).getX(), edges.get(i).getY()));
            builder.append(format("%s,%s ", edgesAlt.get(i).getX(), edgesAlt.get(i).getY()));
        }

        return newPolygon(builder.toString(), style);

    }



    public static XMLBuilder newHexagon(Point2D centre, double r, String style) {
        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        StringBuilder builder = new StringBuilder();

        for (Point2D edge : edges) {
            builder.append(format("%s,%s ", edge.getX(), edge.getY()));
        }

        return newPolygon(builder.toString(), style);


    }












}
