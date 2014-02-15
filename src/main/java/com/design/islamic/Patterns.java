package com.design.islamic;

import com.design.common.view.SvgFactory;
import com.design.islamic.model.Tile;
import com.design.islamic.model.hex.*;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.jamesmurty.utils.XMLBuilder;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;

import java.awt.*;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Set;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.GenericTools.*;
import static com.design.islamic.model.Centre.newCentre;
import static com.google.common.collect.Iterables.concat;
import static com.google.common.collect.Iterables.transform;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.*;
import static java.util.Arrays.asList;
import static org.paukov.combinatorics.Factory.createSimpleCombinationGenerator;
import static org.paukov.combinatorics.Factory.createVector;

public final class Patterns {



    private Patterns() {

    }


    private static Set<Point2D> edgesFromCentres(Iterable<Point2D> centres, double r) {

        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        builder.addAll(calculateHexEdges(centres, r));
        builder.addAll(centres);

        return builder.build();

    }

    public static Set<Point2D> calculateHexEdges(Iterable<Point2D> centres, double r) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(cloneAndTranslateScalePoints(centre, r, hexPoints));
        }

        return builder.build();
    }

    public static Set<Point2D> calculateNewCellCentresFirstConf(Point2D startPoint, double r, int level) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        final double dist1 = 2 * r;

        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
                final double dist = j % 2 == 0 ? dist1 * i : dist1 * i - r;
                builder.add(newCentre(startPoint.getX() + dist, startPoint.getY() + j * r * HEX_DIST_NEW_CENTRE));

            }
        }

        return builder.build();
    }

    public static Set<Point2D> calculateNewCellCentresSecondConf(Point2D startPoint, double r, int level) {
        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        final double dist1 = r + r * 0.5;
        final double dist2 = r * HEX_DIST_NEW_CENTRE;


        for (int i = 0; i < level; i++) {
            for (int j = 0; j < level; j++) {
//                final double dist = i % 2 == 0 ? j * dist2 : j * dist2 - r * HEX_DIST_HEIGHT;
                final double dist_ = j % 2 == 0 ? i * dist2 : i * dist2 - r * HEX_DIST_HEIGHT;
//                builder.add(newCentre(startPoint.getX() + i * dist1, startPoint.getY() + dist));
                builder.add(newCentre(startPoint.getX() + dist_, startPoint.getY() + j*dist1));

            }
        }

        builder.add(newCentre(startPoint.getX() + 2 * dist2, startPoint.getY() ));
        return builder.build();
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
        return cloneAndTranslateScalePoints(centre, r * HEX_DIST_NEW_CENTRE, hexPointsAlt);

    }

    private static Set<Point2D> calculateNewCellCentres(Iterable<Point2D> centres, double r) {

        ImmutableSet.Builder<Point2D> builder = ImmutableSet.builder();

        for (Point2D centre : centres) {
            builder.addAll(calculateNewCellCentres(centre, r));
        }

        return builder.build();

    }

    public static XMLBuilder buildHexPattern1(CentreConfiguration centreConfiguration, final double r, Dimension dim) {

        final String styleColoured = newStyle("yellow", "green", 2, 1, 1);

//        List<Point2D> backGroundRect = asList(newCentre(0, 0), newCentre(width, 0), newCentre(width, height), newCentre(0, height));

        List<Tile1> hexagons = Tile1.fromCentres(centreConfiguration.getCentresSecondConf(), r);

        List<XMLBuilder> tiles = newArrayList(transform(hexagons, new Function<Tile1, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Tile1 tile) {
                return tile.drawMe(styleColoured);
            }
        }));

        XMLBuilder svg = buildSvg(dim,
                tiles
        );

        System.out.println("finished building svg!!!");
        return svg;
    }

    public static XMLBuilder buildHexPattern2(CentreConfiguration centreConfiguration, final double r, Dimension dim) {

        final String style = newStyle("yellow", "green", 1, 1, 0);

        List<XMLBuilder> hexagons = ImmutableList.copyOf(transform(centreConfiguration.getCentresSecondConf(), new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return drawPolygon(cloneAndTranslateScalePoints(centre, r, hexPoints), style);
            }
        }));

        final String style2 = newStyle("blue", "green", 1, 0, 1);

        List<XMLBuilder> circles = ImmutableList.copyOf(
                transform(
                        edgesFromCentres(centreConfiguration.getCentresSecondConf(), r),
                        new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D edge) {
                return SvgFactory.newCircle(edge, r, style2);
            }
        }));

        XMLBuilder svg = buildSvg(dim,
                concat(
                        hexagons
                        , circles
                )
        );

        System.out.println("finished building svg!!!");
        return svg;
    }

    public static XMLBuilder buildHexPatternStar(CentreConfiguration centreConfiguration, final double r, Dimension dim, final double dist) {

        final String style = newStyle("yellow", "yellow", 1, 1, 1);

        List<TileStar> stars = newArrayList(transform(centreConfiguration.getCentresSecondConf(), new Function<Point2D, TileStar>() {
            @Override
            public TileStar apply(Point2D centre) {
                return new TileStar(centre, r, dist);
            }
        }));

        final String style2 = newStyle("blue", "blue", 1, 1, 1);

        List<Point2D> backGroundRect = asList(
                newCentre(0, 0),
                newCentre(dim.getWidth(), 0),
                newCentre(dim.getWidth(), dim.getHeight()),
                newCentre(0, dim.getHeight()));

        List<XMLBuilder> allShapes = newArrayList();

        allShapes.add(drawPolygon(backGroundRect, style2));
        allShapes.addAll(newArrayList(transform(stars, new Function<TileStar, XMLBuilder>() {
            @Override
            public XMLBuilder apply(TileStar tileStar) {
                return tileStar.drawMe(style);
            }
        })));

        XMLBuilder svg = buildSvg(dim, allShapes);

        System.out.println("finished building svg!!!");
        return svg;
    }

    public static XMLBuilder buildHexPatternBlackAndWhite(CentreConfiguration centreConfiguration, Dimension dim, Function<Point2D, Tile> transformation) {

        final String styleBlack = newStyle(BLACK, BLACK, 1, 1, 1);

        List<Tile> tiles = newArrayList(transform(centreConfiguration.getCentresSecondConf(), transformation));

        List<Point2D> backGroundRect = asList(
                newCentre(0, 0),
                newCentre(dim.getWidth(), 0),
                newCentre(dim.getWidth(), dim.getHeight()),
                newCentre(0, dim.getHeight()));

        List<XMLBuilder> total = newArrayList();
        total.add(drawPolygon(backGroundRect, styleBlack));
        for (Tile tile : tiles) {
            total.addAll(tile.drawMe());
        }


//        total.add(drawPolygon(forGroundRect, styleWhite));

        XMLBuilder svg = buildSvg(dim,
                total);

        System.out.println("finished building svg!!!");
        return svg;

    }







}
