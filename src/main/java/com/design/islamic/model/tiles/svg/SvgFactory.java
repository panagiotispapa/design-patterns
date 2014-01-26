package com.design.islamic.model.tiles.svg;

import com.design.islamic.model.PolygonTools;
import com.design.islamic.model.tiles.shapes.Circle;
import com.design.islamic.model.tiles.shapes.Hexagon;
import com.design.islamic.model.tiles.shapes.Shape;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.jamesmurty.utils.XMLBuilder;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static com.google.common.collect.Iterables.transform;
import static java.lang.String.valueOf;
import static org.apache.batik.dom.svg.SVGDOMImplementation.SVG_NAMESPACE_URI;

public class SvgFactory {

    private SvgFactory() {

    }



    public static List<XMLBuilder> newShapes(Iterable<Point2D> centres, final double r, Function<Point2D, XMLBuilder> f) {
        return ImmutableList.copyOf(transform(centres, f));
    }


    public static XMLBuilder newHexagon(Point2D centre, double r, Style style) {
        List<Point2D> edges = PolygonTools.calculateHexEdges(centre, r);

        StringBuilder builder = new StringBuilder();

        for (Point2D edge : edges) {
            builder.append(String.format("%s,%s ", edge.getX(), edge.getY()));
        }

        try {
            return XMLBuilder.create("polygon")
                    .a("points", builder.toString())
                    .a("style", style.toString())
                    ;
        } catch (ParserConfigurationException e) {
            return null;
        }

    }

    public static List<XMLBuilder> newCircles(Iterable<Point2D> centres, final double r, final Style style) {
        return ImmutableList.copyOf(transform(centres, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newCircle(centre, r, style);
            }
        }));
    }

    public static XMLBuilder newCircle(Point2D centre, double r, Style style) {
        try {
            return XMLBuilder.create("circle")
                    .a("cx", String.valueOf(centre.getX()))
                    .a("cy", String.valueOf(centre.getY()))
                    .a("r", String.valueOf(r))
                    .a("style", style.toString());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    public static SVGDocument fromXMLBuilder(XMLBuilder xmlBuilder) {

        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
        try {
            return f.createSVGDocument("http://www.test.com", new StringReader(xmlBuilder.asString()));

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (TransformerException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return null;
    }

    public static XMLBuilder buildSvg(int width, int height, Iterable<XMLBuilder> shapes) {

        XMLBuilder xmlBuilder = null;
        try {
            xmlBuilder = XMLBuilder.create("svg")
                    .a("width", valueOf(width)).a("height", valueOf(height));

            for (XMLBuilder shape : shapes) {
                xmlBuilder.importXMLBuilder(shape);
            }

            return xmlBuilder;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return xmlBuilder;

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
