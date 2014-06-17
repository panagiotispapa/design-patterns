package com.design.common.view;

import com.design.common.model.Arc;
import com.design.common.model.Circle;
import com.design.islamic.model.Payload;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.w3c.dom.svg.SVGDocument;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;
import static java.lang.String.join;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.joining;

public class SvgFactory {

    public static String BLACK = "black";
    public static String BLUE = "blue";
    public static String ORANGE = "orange";
    public static String GREEN = "green";
    public static String RED = "red";
    public static String GRAY = "gray";
    public static String WHITE = "white";

    private static final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private static final String styleWhite = newStyle(WHITE, 1, 1);

    private SvgFactory() {

    }

    public static String newStyle(String stroke, int strokeWidth, double strokeOpcacity) {
        return format("fill:none;stroke:%s;stroke-width:%d;stroke-opacity:%s", stroke, strokeWidth, strokeOpcacity);
    }

    public static String newStyle(String fill, String stroke, int strokeWidth, double fillOpacity, double strokeOpcacity) {
        return format("fill:%s;stroke:%s;stroke-width:%d;fill-opacity:%s;stroke-opacity:%s", fill, stroke, strokeWidth, fillOpacity, strokeOpcacity);
    }

    public static String drawPolygons(List<List<Point2D>> pointsList, String style) {
        return pointsList.stream().map(p -> drawPolygon(p, style)).collect(joining());
    }

    public static String drawPolygon(Collection<Point2D> points, String style) {

        return format("<polygon points=\"%s\" style=\"%s\" />", toPointsString(points), style);

//        try {
//            return XMLBuilder.create("polygon")
//                    .a("points", toPointsString(points))
//                    .a("style", style)
//                    ;
//        } catch (ParserConfigurationException e) {
//            return null;
//        }

    }

    public static String drawPolylines(List<List<Point2D>> pointsList, final String style) {
        return pointsList.stream().map(p->newPolyline(p, style)).collect(joining());
    }

    public static String newPolyline(Collection<Point2D> points, String style) {

        return format("<polyline points=\"%s\" style=\"%s\"/>", toPointsString(points), style);

    }

    public static String drawPayload(Payload payload) {

        StringBuilder builder = new StringBuilder();

        builder.append(drawPolygons(payload.getPolygons(), styleWhiteBold));
        builder.append(drawPolygons(payload.getPolygonsSecondary(), styleWhite));

        builder.append(drawPolylines(payload.getPolylines(), styleWhiteBold));
        builder.append(drawPolylines(payload.getPolylinesSecondary(), styleWhite));

        return builder.toString();
    }

    public static String highlightPoints(List<Point2D> points) {
        String style = newStyle("red", "black", 1, 1, 1);

        return points.stream().map(p->newCircle(p, 3, style)).collect(joining());

    }

    public static String highlightPoints(Point2D[] points) {

        final String style = newStyle("red", "black", 1, 1, 1);
        StringBuilder builder = new StringBuilder(points.length);

        for (Point2D centre : points) {
            builder.append(newCircle(centre, 3, style));
        }

        return builder.toString();

    }

    public static String drawCircles(Collection<Circle> circles, final String style) {
        return circles.stream().map(circle->drawCircle(circle, style)).collect(joining());
    }

    public static String drawCircle(Circle circle, String style) {
        return newCircle(circle.getCentre(), circle.getR(), style);
    }

    public static String newCircle(Point2D centre, double r, String style) {
        return format("<circle cx=\"%f\" cy=\"%f\" r=\"%f\" style=\"%s\" />",
                centre.getX(),
                centre.getY(),
                r,
                style
        );

    }

    public static String drawArcList(List<Arc> arcList, String style) {
        return arcList.stream().map(arc -> drawArc(arc, style)).collect(joining());

    }

    public static String drawArc(Arc arc, String style) {
        final String path = arc.isUp() ? "M %f %f a 1 1 0 0 1 %f 0" : "M %f %f a 1 1 0 0 0 %f 0";

        return format("<path d=\"%s\" style=\"%s\"/>",
                format(path,
                        arc.getCircle().getCentre().getX() - arc.getCircle().getR(),
                        arc.getCircle().getCentre().getY(),
                        2 * arc.getCircle().getR()
                ),
                style);

    }

    public static SVGDocument fromSvgDoc(String svgDoc) {

        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
        try {
            return f.createSVGDocument("http://www.test.com", new StringReader(svgDoc));

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public static String buildSvg(Dimension dim, String shapes) {
        return format("<svg width=\"%s\" height=\"%s\">%s</svg>", valueOf(dim.getWidth()), valueOf(dim.getHeight()), shapes);
    }

    private static String toPointsString(Collection<Point2D> points) {
        return points.stream().map(point->format("%s,%s", point.getX(), point.getY())).collect(joining(" "));

//        StringBuilder builder = new StringBuilder();
//        points.forEach(point -> builder.append(format("%s,%s ", point.getX(), point.getY())));
//
//        return builder.toString();
    }

}
