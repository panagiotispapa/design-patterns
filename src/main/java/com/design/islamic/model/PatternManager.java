package com.design.islamic.model;

import com.design.common.PolygonTools;
import com.design.islamic.CentreConfiguration;
import com.design.islamic.model.hex.Tile3;
import com.design.islamic.model.hex.Tile4;
import com.design.islamic.model.hex.Tile5;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Set;

import static com.design.common.PolygonTools.HEX_DIST3;
import static com.design.common.PolygonTools.HEX_DIST_DIAGONAL;
import static com.design.common.PolygonTools.HEX_DIST_OUTER_CIRCLE;
import static com.design.islamic.Patterns.*;
import static com.design.islamic.model.Centre.newCentre;

public class PatternManager {

    private final Map<Pattern, PatternProvider> providerMap;

    private final CentreConfiguration centreConfiguration;


    private final double r;
    private final int width;
    private final int height;


    public PatternManager(double r, int width, int height) {

        this.r = r;
        this.width = width;
        this.height = height;

        centreConfiguration = new CentreConfiguration(
                calculateNewCellCentresFirstConf(newCentre(0, 0), r, 17),
                calculateNewCellCentresSecondConf(newCentre(0, 0), r, 17)
        );

        providerMap = Maps.newHashMap();

        providerMap.put(Pattern.ONE, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, int width, int height) {
                return buildHexPattern1(centreConfiguration, r, width, height);
            }
        });

        providerMap.put(Pattern.TWO, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, int width, int height) {
                return buildHexPattern2(centreConfiguration, r, width, height);
            }
        });

        providerMap.put(Pattern.STAR1, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, int width, int height) {
                return buildHexPatternStar(centreConfiguration, r, width, height, HEX_DIST_OUTER_CIRCLE);
            }
        });

        providerMap.put(Pattern.STAR2, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, int width, int height) {
                return buildHexPatternStar(centreConfiguration, r, width, height, HEX_DIST_DIAGONAL);
            }
        });

        providerMap.put(Pattern.STAR3, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, int width, int height) {
                return buildHexPatternStar(centreConfiguration, r, width, height, HEX_DIST3);
            }
        });

        providerMap.put(Pattern.THREE, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, final double r, int width, int height) {
                return buildHexPatternBlackAndWhite(centreConfiguration, r, width, height,
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
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, final double r, int width, int height) {
                return buildHexPatternBlackAndWhite(centreConfiguration, r, width, height,
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
            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, final double r, int width, int height) {
                return buildHexPatternBlackAndWhite(centreConfiguration, r, width, height,
                        new Function<Point2D, Tile>() {
                            @Override
                            public Tile apply(Point2D centre) {
                                return new Tile5(centre, r);
                            }
                        });
            }
        });

    }


    public XMLBuilder getSvg(Pattern pattern) {
        return providerMap.get(pattern).provideSVG(centreConfiguration, r, width, height);
    }

}
