package com.design.common.view;

import com.design.common.model.Arc;
import com.design.common.model.Circle;
import com.design.common.model.Points;
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
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.String.format;
import static java.lang.String.valueOf;

public class SvgFactory {

    public static String BLACK = "black";
    public static String BLUE = "blue";
    public static String ORANGE = "orange";
    public static String GREEN = "green";
    public static String GRAY = "gray";
    public static String WHITE = "white";

    private SvgFactory() {

    }

    public static String newStyle(String stroke, int strokeWidth, double strokeOpcacity) {
        return format("fill:none;stroke:%s;stroke-width:%d;stroke-opacity:%s", stroke, strokeWidth, strokeOpcacity);
    }

    public static String newStyle(String fill, String stroke, int strokeWidth, double fillOpacity, double strokeOpcacity) {
        return format("fill:%s;stroke:%s;stroke-width:%d;fill-opacity:%s;stroke-opacity:%s", fill, stroke, strokeWidth, fillOpacity, strokeOpcacity);
    }

    public static List<XMLBuilder> drawPolygons(Iterable<Points> pointsList, final String style) {

        return newArrayList(transform(pointsList, new Function<Points, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Points points) {
                return drawPolygon(points, style);
            }
        }));
    }

    public static XMLBuilder drawPolygon(Points points, String style) {
        return drawPolygon(points.getPoints(), style);
    }

    public static List<XMLBuilder> drawPolygons(List<List<Point2D>> pointsList, final String style) {
        return newArrayList(transform(pointsList,new Function<List<Point2D>, XMLBuilder>() {
            @Override
            public XMLBuilder apply(List<Point2D> point2DList) {
                return drawPolygon(point2DList, style);
            }
        }));

    }

    public static XMLBuilder drawPolygon(Iterable<Point2D> points, String style) {

        try {
            return XMLBuilder.create("polygon")
                    .a("points", toPointsString(points))
                    .a("style", style)
                    ;
        } catch (ParserConfigurationException e) {
            return null;
        }

    }

    public static List<XMLBuilder> drawPolylines(Iterable<List<Point2D>> pointsList, final String style) {

        return newArrayList(transform(pointsList, new Function<Iterable<Point2D>, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Iterable<Point2D> points) {
                return newPolyline(points, style);
            }
        }));
    }
//    public static XMLBuilder drawPolyline(Points points, String style) {
//
//        return newPolyline(points.getPoints(), style);
//    }

    public static XMLBuilder newPolyline(Iterable<Point2D> points, String style) {

        try {
            return XMLBuilder.create("polyline")
                    .a("points", toPointsString(points))
                    .a("style", style)
                    ;
        } catch (ParserConfigurationException e) {
            return null;
        }

    }

    public static List<XMLBuilder> highlightPoints(Points points) {
        return highlightPoints(points.getPoints());
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

    public static List<XMLBuilder> drawCircles(Iterable<Circle> circles, final String style) {
        return newArrayList(transform(circles, new Function<Circle, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Circle circle) {
                return drawCircle(circle, style);
            }
        }));
    }

    public static XMLBuilder drawCircle(Circle circle, String style) {
        return newCircle(circle.getCentre(), circle.getR(), style);
    }

    public static XMLBuilder newCircle(Point2D centre, double r, String style) {
        try {
            return XMLBuilder.create("circle")
                    .a("cx", String.valueOf(centre.getX()))
                    .a("cy", String.valueOf(centre.getY()))
                    .a("r", String.valueOf(r))
                    .a("style", style);
        } catch (ParserConfigurationException e) {
            return null;
        }

    }

    public static List<XMLBuilder> drawArcList(Iterable<Arc> arcList, final String style) {
        return newArrayList(transform(arcList, new Function<Arc, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Arc arc) {
                return drawArc(arc, style);
            }
        }));
    }

    public static XMLBuilder drawArc(Arc arc, String style) {
        final String path = arc.isUp() ? "M %f %f a 1 1 0 0 1 %f 0" : "M %f %f a 1 1 0 0 0 %f 0";

        try {
            return XMLBuilder.create("path")
                    .a("d", format(path,
                            arc.getCircle().getCentre().getX() - arc.getCircle().getR(),
                            arc.getCircle().getCentre().getY(),
                            2 * arc.getCircle().getR()
                    ))
                    .a("style", style);
        } catch (ParserConfigurationException e) {
            return null;
        }

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

    private static String toPointsString(Points points) {
        return toPointsString(points.getPoints());

    }

    private static String toPointsString(Iterable<Point2D> points) {
        StringBuilder builder = new StringBuilder();

        for (Point2D point : points) {
            builder.append(format("%s,%s ", point.getX(), point.getY()));
        }

        return builder.toString();
    }

}
