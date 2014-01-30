package com.design.islamic.model.tiles.svg;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.jamesmurty.utils.XMLBuilder;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.StringReader;
import java.util.List;

import static com.google.common.collect.Iterables.transform;
import static java.lang.String.format;
import static java.lang.String.valueOf;

public class SvgFactory {

    private SvgFactory() {

    }

    public static String newStyle(String fill, String stroke, int strokeWidth, double fillOpacity, double strokeOpcacity) {
        return format("fill:%s;stroke:%s;stroke-width:%d;fill-opacity:%s;stroke-opacity:%s", fill, stroke, strokeWidth, fillOpacity, strokeOpcacity);
    }

    public static XMLBuilder newPolygon(Iterable<Point2D> points, String style) {

        try {
            return XMLBuilder.create("polygon")
                    .a("points", toPointsString(points))
                    .a("style", style)
                    ;
        } catch (ParserConfigurationException e) {
            return null;
        }

    }

    private static String toPointsString(Iterable<Point2D> points) {
        StringBuilder builder = new StringBuilder();

        for (Point2D point : points) {
            builder.append(format("%s,%s ", point.getX(), point.getY()));
        }

        return builder.toString();
    }

    public static List<XMLBuilder> highlightPoints(Iterable<Point2D> points) {

        final String style = newStyle("red", "black", 1, 1, 1);
        return ImmutableList.copyOf(transform(points, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D point) {
                return newCircle(point, 3, style);
            }
        }));
    }

    public static XMLBuilder newCircle(Point2D centre, double r, String style) {
        try {
            return XMLBuilder.create("circle")
                    .a("cx", String.valueOf(centre.getX()))
                    .a("cy", String.valueOf(centre.getY()))
                    .a("r", String.valueOf(r))
                    .a("style", style);
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

}
