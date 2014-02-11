package com.design.islamic.model.hex;

import com.design.islamic.Patterns;
import com.google.common.base.Function;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.view.SvgFactory.drawPolygon;
import static com.design.common.view.SvgFactory.drawPolygons;
import static com.design.islamic.GenericTools.*;
import static com.design.islamic.Patterns.*;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tiles {

    private Tiles() {

    }

    public static Tile1 newTile1(final Point2D centre, final double r) {
        return new Tile1() {
            @Override
            public List<Point2D> getEdges() {
                return calculateHexEdges(centre, r);
            }
        };
    }

    public static List<Tile1> newTiles1(final Iterable<Point2D> centres, final double r) {
        return newArrayList(transform(centres, new Function<Point2D, Tile1>() {
            @Override
            public Tile1 apply(Point2D centre) {
                return newTile1(centre, r);
            }
        }));
    }

    public static XMLBuilder draw(Tile1 tile, String style) {
        return drawPolygon(tile.getEdges(), style);
    }

    public static TileStar newTileStar(final Point2D centre, final double r, final double dist) {
        List<Point2D> edges = Patterns.calculateHexEdges(centre, r);

        List<Point2D> edgesAlt = clonePoints(Patterns.hexPointsAlt);

        scalePoints(edgesAlt, r * dist);
        translatePoints(edgesAlt, centre);

        final List<Point2D> starEdges = concatEdges(edges, edgesAlt);
        return new TileStar() {
            @Override
            public List<Point2D> getEdges() {
                return starEdges;
            }
        };

    }

    public static XMLBuilder draw(TileStar tile, String style) {
        return drawPolygon(tile.getEdges(), style);
    }

    public static Tile3 newTile3(final Point2D centre, final double r) {

        final double newR = r * HEX_DIST_EQ1;

        final List<Point2D> innerEdges = buildHexPoints(centre, newR);

        final double extConfR = r - newR;

        final List<List<Point2D>> extConfigs = newArrayList();
        for (int i = 0; i < innerEdges.size(); i++) {
            extConfigs.add(buildExtConfig(innerEdges.get(i), extConfR, i));
        }

        return new Tile3() {
            @Override
            public List<Point2D> getInnerEdges() {
                return innerEdges;
            }

            @Override
            public List<List<Point2D>> getOuterRectangles() {
                return extConfigs;
            }
        };

    }

    public static List<XMLBuilder> draw(Tile3 tile, String style, String outerStyle) {

        List<XMLBuilder> out = newArrayList();
        out.add(drawPolygon(tile.getInnerEdges(), style));
        out.addAll(drawPolygons(tile.getOuterRectangles(), outerStyle));

        return out;
    }

    private static List<Point2D> buildExtConfig(final Point2D centre, final double r, int index) {

        List<Point2D> edges = buildHexPoints(centre, r);
        List<Point2D> heights = buildHeightEdges(centre, r);

        return asList(
                centre,
                heights.get(index),
                edges.get(index),
                heights.get((index + 5) % 6)
        );

    }

}
