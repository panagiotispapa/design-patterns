package com.design.islamic;

import java.awt.geom.Point2D;
import java.util.Set;

public class CentreConfiguration {

    private final Set<Point2D> centresFirstConf;
    private final Set<Point2D> centresSecondConf;

    public CentreConfiguration(Set<Point2D> centresFirstConf, Set<Point2D> centresSecondConf) {
        this.centresFirstConf = centresFirstConf;
        this.centresSecondConf = centresSecondConf;
    }

    public Set<Point2D> getCentresFirstConf() {
        return centresFirstConf;
    }

    public Set<Point2D> getCentresSecondConf() {
        return centresSecondConf;
    }
}
