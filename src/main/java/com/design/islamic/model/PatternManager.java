package com.design.islamic.model;

import com.design.islamic.CentreConfiguration;
import com.design.islamic.model.hex.*;
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

    private final Map<HexPattern, PatternProvider> providerMap;

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

        providerMap.put(HexPattern.ONE, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
                return buildHexPattern1(centreConfiguration, r, dim);
            }
        });

//        providerMap.put(HexPattern.TWO, new PatternProvider() {
//            @Override
//            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
//                return buildHexPattern2(centreConfiguration, r, dim);
//            }
//        });

        providerMap.put(HexPattern.STAR1, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
                return buildHexPatternStar(centreConfiguration, r, dim, HEX_DIST_OUTER_CIRCLE);
            }
        });

        providerMap.put(HexPattern.STAR2, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
                return buildHexPatternStar(centreConfiguration, r, dim, HEX_DIST_DIAGONAL);
            }
        });

        providerMap.put(HexPattern.STAR3, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
                return buildHexPatternStar(centreConfiguration, r, dim, HEX_DIST3);
            }
        });

        providerMap.put(HexPattern.THREE, new PatternProvider() {
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

        providerMap.put(HexPattern.FOUR, new PatternProvider() {
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

        providerMap.put(HexPattern.FIVE, new PatternProvider() {
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

        providerMap.put(HexPattern.SIX, new PatternProvider() {
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

        providerMap.put(HexPattern.SEVEN, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, final double r, Dimension dim) {
                return buildHexPatternBlackAndWhite(centreConfiguration, dim,
                        new Function<Point2D, Tile>() {
                            @Override
                            public Tile apply(Point2D centre) {
                                return new Tile7(centre, r);
                            }
                        });
            }
        });

        providerMap.put(HexPattern.HEIGHT, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, final double r, Dimension dim) {
                return buildHexPatternBlackAndWhite(centreConfiguration, dim,
                        new Function<Point2D, Tile>() {
                            @Override
                            public Tile apply(Point2D centre) {
                                return new Tile8(centre, r);
                            }
                        });
            }
        });

    }


    public XMLBuilder getSvg(HexPattern hexPattern) {
        return providerMap.get(hexPattern).provideSVG(centreConfiguration, r, dim);
    }

}
