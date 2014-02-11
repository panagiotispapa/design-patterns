package com.design.islamic.model;

import com.google.common.collect.Maps;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Set;

import static com.design.islamic.Patterns.*;
import static com.design.islamic.model.Centre.newCentre;

public class PatternManager {

    private final Map<Pattern, PatternProvider> providerMap;

    private final Set<Point2D> newCentresFirstConf;
    private final Set<Point2D> newCentresSecondConf;

    private final double r;
    private final int width;
    private final int height;


    public PatternManager(double r, int width, int height) {

        this.r = r;
        this.width = width;
        this.height = height;

        newCentresFirstConf = calculateNewCellCentresFirstConf(newCentre(0, 0), r, 17);
        newCentresSecondConf = calculateNewCellCentresSecondConf(newCentre(0, 0), r, 17);

        providerMap = Maps.newHashMap();

        providerMap.put(Pattern.ONE, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(Iterable<Point2D> newCentresFirstConf, Iterable<Point2D> newCentresSecondConf, double r, int width, int height) {
                return buildHexPattern1(newCentresFirstConf, newCentresSecondConf, r, width, height);
            }
        });

        providerMap.put(Pattern.TWO, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(Iterable<Point2D> newCentresFirstConf, Iterable<Point2D> newCentresSecondConf, double r, int width, int height) {
                return buildHexPattern2(newCentresFirstConf, newCentresSecondConf, r, width, height);
            }
        });

        providerMap.put(Pattern.STAR1, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(Iterable<Point2D> newCentresFirstConf, Iterable<Point2D> newCentresSecondConf, double r, int width, int height) {
                return buildHexPatternStar(newCentresFirstConf, newCentresSecondConf, r, width, height, HEX_DIST_1);
            }
        });

        providerMap.put(Pattern.STAR2, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(Iterable<Point2D> newCentresFirstConf, Iterable<Point2D> newCentresSecondConf, double r, int width, int height) {
                return buildHexPatternStar(newCentresFirstConf, newCentresSecondConf, r, width, height, HEX_DIST2);
            }
        });

        providerMap.put(Pattern.STAR3, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(Iterable<Point2D> newCentresFirstConf, Iterable<Point2D> newCentresSecondConf, double r, int width, int height) {
                return buildHexPatternStar(newCentresFirstConf, newCentresSecondConf, r, width, height, HEX_DIST3);
            }
        });
        providerMap.put(Pattern.THREE, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(Iterable<Point2D> newCentresFirstConf, Iterable<Point2D> newCentresSecondConf, double r, int width, int height) {
                return buildHexPattern3(newCentresFirstConf, newCentresSecondConf, r, width, height);
            }
        });

    }


    public XMLBuilder getSvg(Pattern pattern) {
        return providerMap.get(pattern).provideSVG(newCentresFirstConf, newCentresSecondConf, r, width, height);
    }

}
