package com.design.common.view;

import com.design.common.Points;
import com.design.common.model.Arc;
import com.design.common.model.Circle;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.svg.SVGDocument;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static java.lang.String.format;
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

    public static String drawPolylinesFromLine2D(List<Line2D> lines, final String style) {
        return lines.stream().map(line -> newPolyline(line, style)).collect(joining());
    }

    public static String drawPolylines(List<List<Point2D>> pointsList, final String style) {
        return pointsList.stream().map(p -> newPolyline(p, style)).collect(joining());
    }

    public static Function<List<Pair<Point2D, Double>>, String> drawCircles(final String style) {
        return
                c -> c.stream().map(drawCircle(style)).collect(joining());
    }

    public static Function<Pair<Point2D, Double>, String> drawCircle(final String style) {
        return p -> newCircle(p.getLeft(), p.getRight(), style);
    }

    public static Function<List<Pair<Point2D, String>>, String> drawTexts() {
        return p -> p.stream().map(drawText()).collect(joining());
    }

    public static Function<Pair<Point2D, String>, String> drawText(int fontSize) {
        return p -> String.format("<text x=\"%f\" y=\"%f\" fill=\"black\" font-size=\"%d\">%s</text>", p.getLeft().getX() + 5, p.getLeft().getY() + 5, fontSize, p.getRight());
    }

    public static Function<Pair<Point2D, String>, String> drawText() {
        return drawText(18);
    }

    public static Function<List<List<Point2D>>, String> toPolylines(final String style) {
        return pointsList -> pointsList.stream().map(toPolyline(style)).collect(joining());
    }

    public static Function<List<Point2D>, String> toPolyline(String style) {
        return points -> format("<polyline points=\"%s\" style=\"%s\"/>", toPointsString(points), style);
    }

    public static String newPolyline(Collection<Point2D> points, String style) {
        return format("<polyline points=\"%s\" style=\"%s\"/>", toPointsString(points), style);
    }

    public static Function<List<Point2D>, String> mapToPolyline(String style) {
        return points -> format("<polyline points=\"%s\" style=\"%s\"/>", toPointsString(points), style);
    }

    public static String newPolyline(Line2D line, String style) {
        return format("<polyline points=\"%s\" style=\"%s\"/>", toPointsString(line), style);
    }

//    public static String newPolyline(Collection<Line2D> points, String style) {
//
//        return format("<polyline points=\"%s\" style=\"%s\"/>", toPointsString(points), style);
//
//    }

    public static String drawOnGrid(List<List<Point2D>> lines, List<Point2D> gridPoints, String style) {
        List<List<Point2D>> allLines = Points.applyGrid(gridPoints).apply(lines);
        return drawPolylines(allLines, style);

    }

    public static Function<Collection<Point2D>, String> highlightPoints() {
        return highlightPoints("red", 3);
    }

    public static Function<Collection<Point2D>, String> highlightPoints(String fill, int radius) {
        return points -> points.stream().map(highlightPoint(fill, radius)).collect(joining());
    }

    public static Function<Point2D, String> highlightPoint() {
        return highlightPoint("red", 3);
    }

    public static Function<Point2D, String> highlightPoint(String fill, int radius) {
        String style = newStyle(fill, "black", 1, 1, 1);
        return p -> newCircle(p, radius, style);
    }

    public static String highlightPoints(List<Point2D> points) {
        String style = newStyle("red", "black", 1, 1, 1);

        return points.stream().map(p -> newCircle(p, 3, style)).collect(joining());

    }


    public static String drawCircles(Collection<Circle> circles, final String style) {
        return circles.stream().map(circle -> drawCircle(circle, style)).collect(joining());
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
        return points.stream().map(point -> format("%s,%s", point.getX(), point.getY())).collect(joining(" "));
    }

    private static String toPointsString(Line2D line) {
        return toPointsString(Arrays.asList(line.getP1(), line.getP2()));
    }


    public static String toHtml(String svg) {
        return "<html>" +
                "<header>" +
                "</header>" +
                "<body>" +
                svg +
                "</body>" +
                "</html>";

    }

//    private static String toPointsString(Collection<Line2D> points) {
//        return points.stream().map(point -> format("%s,%s", point.getX(), point.getY())).collect(joining(" "));
//    }

}
