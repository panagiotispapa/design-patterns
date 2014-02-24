package com.design.islamic;

import java.awt.geom.Point2D;
import java.util.Set;

public class CentreConfiguration {

    private final Set<Point2D> centresFirstConf;
    private final Set<Point2D> centresSecondConf;
    private final Set<Point2D> centresRectConf;

    public CentreConfiguration(Set<Point2D> centresFirstConf, Set<Point2D> centresSecondConf, Set<Point2D> centresRectConf) {
        this.centresFirstConf = centresFirstConf;
        this.centresSecondConf = centresSecondConf;

        this.centresRectConf= centresRectConf;
    }

    public Set<Point2D> getCentresFirstConf() {
        return centresFirstConf;
    }

    public Set<Point2D> getCentresSecondConf() {
        return centresSecondConf;
    }
    public Set<Point2D> getCentresRectConf() {
        return centresRectConf;
    }
}
