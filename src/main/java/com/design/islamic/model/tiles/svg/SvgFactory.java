package com.design.islamic.model.tiles.svg;

import com.design.islamic.model.tiles.shapes.Circle;
import com.design.islamic.model.tiles.shapes.Hexagon;
import com.design.islamic.model.tiles.shapes.Shape;
import com.jamesmurty.utils.XMLBuilder;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.geom.Point2D;

import static java.lang.String.valueOf;

public class SvgFactory {

    private SvgFactory() {

    }

    public static String buildSVG(int width, int height, Iterable<? extends Shape> shapes)  {

        try {
            XMLBuilder xmlBuilder = XMLBuilder.create("html")
                    .e("svg").a("width", valueOf(width)).a("height", valueOf(height)).up();

            XMLBuilder svg = xmlBuilder.xpathFind("svg");

            for (Shape shape : shapes) {
                svg.importXMLBuilder(shape.toSvgXML());
            }

            return xmlBuilder
                    .asString()
                    ;
        } catch (TransformerException e) {
            return "";
        } catch (ParserConfigurationException e) {
            return "";
        } catch (XPathExpressionException e) {
            return "";
        }


    }
    public static String toXML(Circle circle, Style style) throws ParserConfigurationException, TransformerException {

        return XMLBuilder.create("circle")
                .a("cx", valueOf(circle.getCentre().getX()))
                .a("cy", valueOf(circle.getCentre().getY()))
                .a("r", valueOf(circle.getR()))
                .a("style", style.toString())
                .asString();
    }

    public static String toXML(Hexagon hexagon, Style style) throws ParserConfigurationException, TransformerException {
        StringBuilder builder = new StringBuilder();

        for (Point2D edge : hexagon.getEdges()) {
            builder.append(String.format("%s,%s ", edge.getX(), edge.getY()));
        }


        return XMLBuilder.create("polygon")
                .a("points", builder.toString())
                .a("style", style.toString())
                .asString();

    }
}
