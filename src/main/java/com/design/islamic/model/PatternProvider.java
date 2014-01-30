package com.design.islamic.model;

import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;

public interface PatternProvider {

    XMLBuilder provideSVG(Iterable<Point2D> newCentresFirstConf, Iterable<Point2D> newCentresSecondConf, double r, int width, int height);
}
