package com.design.celtic.model;

import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;

public interface PatternProvider {

    XMLBuilder provideSVG(int width, int height, double r);
}
