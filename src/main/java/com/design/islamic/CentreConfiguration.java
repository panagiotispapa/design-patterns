package com.design.islamic;

import com.google.common.collect.Maps;

import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Set;

import static com.design.islamic.Patterns.*;
import static com.design.islamic.model.Centre.newCentre;

public class CentreConfiguration {

    public static enum Conf {
        HEX_FIRST,
        HEX_SECOND,
        HEX_THIRD,
        RECT
    }

    private final double r;
    private final int level;

    private final Map<Conf, Set<Point2D>> centresConf;

    public CentreConfiguration(double r, int level) {
        this.r = r;
        this.level = level;
        centresConf = Maps.newHashMap();
        centresConf.put(Conf.HEX_FIRST, calculateNewCellCentresFirstConf(newCentre(0, 0), r, level));
        centresConf.put(Conf.HEX_SECOND, calculateNewCellCentresSecondConf(newCentre(0, 0), r, level));
        centresConf.put(Conf.HEX_THIRD, calculateNewCellCentresThirdConf(newCentre(0, 0), r, level));
        centresConf.put(Conf.RECT, calculateNewRectCentresConf(newCentre(0, 0), r, level, 1.0));

    }

    public Set<Point2D> getCentresConfig(Conf config, double ratio) {
        if (ratio == 1.0) {
            return centresConf.get(config);
        } else {
            if (config == Conf.RECT) {
                return calculateNewRectCentresConf(newCentre(0, 0), r, level, ratio);
            } else {
                return null;
            }
        }

    }

}
