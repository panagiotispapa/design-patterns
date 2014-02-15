package com.design.islamic.model;

import com.design.islamic.CentreConfiguration;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.*;
import java.awt.geom.Point2D;

public interface PatternProvider {

    XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim);
}
