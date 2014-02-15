package com.design.islamic.model;

import com.design.islamic.CentreConfiguration;
import com.design.islamic.model.hex.Tile3;
import com.design.islamic.model.hex.Tile4;
import com.design.islamic.model.hex.Tile5;
import com.design.islamic.model.hex.Tile6;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Map;

import static com.design.common.PolygonTools.HEX_DIST3;
import static com.design.common.PolygonTools.HEX_DIST_DIAGONAL;
import static com.design.common.PolygonTools.HEX_DIST_OUTER_CIRCLE;
import static com.design.islamic.Patterns.*;
import static com.design.islamic.model.Centre.newCentre;

public class PatternManager {

    private final Map<Pattern, PatternProvider> providerMap;

    private final CentreConfiguration centreConfiguration;


    private final double r;


    private final Dimension dim;
    public PatternManager(double r, Dimension dim) {

        this.r = r;

        this.dim = dim;

        centreConfiguration = new CentreConfiguration(
                calculateNewCellCentresFirstConf(newCentre(0, 0), r, 17),
                calculateNewCellCentresSecondConf(newCentre(0, 0), r, 17)
        );

        providerMap = Maps.newHashMap();

        providerMap.put(Pattern.ONE, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
                return buildHexPattern1(centreConfiguration, r, dim);
            }
        });

        providerMap.put(Pattern.TWO, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
                return buildHexPattern2(centreConfiguration, r, dim);
            }
        });

        providerMap.put(Pattern.STAR1, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
                return buildHexPatternStar(centreConfiguration, r, dim, HEX_DIST_OUTER_CIRCLE);
            }
        });

        providerMap.put(Pattern.STAR2, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
                return buildHexPatternStar(centreConfiguration, r, dim, HEX_DIST_DIAGONAL);
            }
        });

        providerMap.put(Pattern.STAR3, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
                return buildHexPatternStar(centreConfiguration, r, dim, HEX_DIST3);
            }
        });

        providerMap.put(Pattern.THREE, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, final double r, Dimension dim) {
                return buildHexPatternBlackAndWhite(centreConfiguration, dim,
                        new Function<Point2D, Tile>() {
                            @Override
                            public Tile apply(Point2D centre) {
                                return new Tile3(centre, r);
                            }
                        });
            }
        });

        providerMap.put(Pattern.FOUR, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, final double r, Dimension dim) {
                return buildHexPatternBlackAndWhite(centreConfiguration, dim,
                        new Function<Point2D, Tile>() {
                            @Override
                            public Tile apply(Point2D centre) {
                                return new Tile4(centre, r);
                            }
                        });
            }
        });

        providerMap.put(Pattern.FIVE, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, final double r, Dimension dim) {
                return buildHexPatternBlackAndWhite(centreConfiguration, dim,
                        new Function<Point2D, Tile>() {
                            @Override
                            public Tile apply(Point2D centre) {
                                return new Tile5(centre, r);
                            }
                        });
            }
        });

        providerMap.put(Pattern.SIX, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, final double r, Dimension dim) {
                return buildHexPatternBlackAndWhite(centreConfiguration, dim,
                        new Function<Point2D, Tile>() {
                            @Override
                            public Tile apply(Point2D centre) {
                                return new Tile6(centre, r);
                            }
                        });
            }
        });

    }


    public XMLBuilder getSvg(Pattern pattern) {
        return providerMap.get(pattern).provideSVG(centreConfiguration, r, dim);
    }

}
