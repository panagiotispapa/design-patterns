package com.design.islamic.model;

import com.design.islamic.Patterns;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;
import java.util.Set;

import static com.design.islamic.Patterns.calculateNewCellCentresFirstConf;
import static com.design.islamic.Patterns.calculateNewCellCentresSecondConf;
import static com.design.islamic.model.Centre.newCentre;
import static java.util.Arrays.asList;

public class PatternManager {

    private final List<PatternProvider> providerList;

    private final Set<Point2D> newCentresFirstConf;
    private final Set<Point2D> newCentresSecondConf;

    private final double r;
    private final int width;
    private final int height;

    private int index;

    public PatternManager(double r, int width, int height) {

        this.r = r;
        this.width = width;
        this.height = height;

        newCentresFirstConf = calculateNewCellCentresFirstConf(newCentre(0, 0), r, 17);
        newCentresSecondConf = calculateNewCellCentresSecondConf(newCentre(0, 0), r, 17);

        providerList = asList(
                new PatternProvider() {
                    @Override
                    public XMLBuilder provideSVG(Iterable<Point2D> newCentresFirstConf, Iterable<Point2D> newCentresSecondConf, double r, int width, int height) {
                        return Patterns.buildHexPattern1(newCentresFirstConf, newCentresSecondConf, r, width, height);
                    }
                },
                new PatternProvider() {
                    @Override
                    public XMLBuilder provideSVG(Iterable<Point2D> newCentresFirstConf, Iterable<Point2D> newCentresSecondConf, double r, int width, int height) {
                        return Patterns.buildHexPattern2(newCentresFirstConf, newCentresSecondConf, r, width, height);
                    }
                },
                new PatternProvider() {
                    @Override
                    public XMLBuilder provideSVG(Iterable<Point2D> newCentresFirstConf, Iterable<Point2D> newCentresSecondConf, double r, int width, int height) {
                        return Patterns.buildHexPattern3(newCentresFirstConf, newCentresSecondConf, r, width, height);
                    }
                }
        );

        index = 0;

    }

    public void goToNext() {
        index++;
        if (index == providerList.size()) {
            index = 0;
        }
    }

    public void goToPrevious() {
        index--;
        if (index < 0) {
            index = 0;
        }

    }

    public XMLBuilder getCurrent() {
        return providerList.get(index).provideSVG(newCentresFirstConf, newCentresSecondConf, r, width, height);
    }

}
