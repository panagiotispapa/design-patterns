package com.design.islamic;

import com.google.common.collect.Maps;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;
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

    private final Map<Conf, Set<Point2D>> centresConf;

    public CentreConfiguration(double r, int level) {
        centresConf = Maps.newHashMap();
        centresConf.put(Conf.HEX_FIRST, calculateNewCellCentresFirstConf(newCentre(0, 0), r, level));
        centresConf.put(Conf.HEX_SECOND, calculateNewCellCentresSecondConf(newCentre(0, 0), r, level));
        centresConf.put(Conf.HEX_THIRD, calculateNewCellCentresThirdConf(newCentre(0, 0), r, level));
        centresConf.put(Conf.RECT, calculateNewRectCentresConf(newCentre(0, 0), r, level));

    }


    public Set<Point2D> getCentresConfig(Conf config) {
        return centresConf.get(config);
    }

}
