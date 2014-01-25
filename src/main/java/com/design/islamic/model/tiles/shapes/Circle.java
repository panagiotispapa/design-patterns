package com.design.islamic.model.tiles.shapes;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.awt.geom.Point2D;

public interface Circle extends Shape{

    Point2D getCentre();

    double getR();

    Element buildFrom(Document doc);

}
