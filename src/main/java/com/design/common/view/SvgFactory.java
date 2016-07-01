package com.design.common.view;

import com.design.common.model.*;
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
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.stream.Collectors.joining;

public class SvgFactory {

    public static String BLACK = "black";
    public static String GRAY = "gray";
    public static String WHITE = "white";

    private SvgFactory() {

    }

    public static String toSVGString(Style style) {

        StringBuilder builder = new StringBuilder();
        builder.append(format("stroke:%s;", toHex(style.getStroke())));
        builder.append(format("fill:%s;", toHex(style.getFill())));
        builder.append(format("stroke-width:%d;", style.getStrokeWidth()));

        Optional.ofNullable(style.getStrokeOpcacity()).ifPresent(o -> builder.append(format("stroke-opacity:%f;", o)));
        Optional.ofNullable(style.getFillOpacity()).ifPresent(o -> builder.append(format("fill-opacity:%f;", o)));

        return builder.toString();
    }

    public static String newStyle(String stroke, int strokeWidth, double strokeOpcacity) {
        return format("fill:none;stroke:%s;stroke-width:%d;stroke-opacity:%s", stroke, strokeWidth, strokeOpcacity);
    }

    public static String newStyle(String fill, String stroke, int strokeWidth, double fillOpacity, double strokeOpcacity) {
        return format("fill:%s;stroke:%s;stroke-width:%d;fill-opacity:%s;stroke-opacity:%s", fill, stroke, strokeWidth, fillOpacity, strokeOpcacity);
    }

    public static String drawPolygon(Collection<Point2D> points, String style) {
        return format("<polygon points=\"%s\" style=\"%s\" />", toPointsString(points), style);

    }

    public static Function<List<Circle>, String> drawCircles(final String style) {
        return
                c -> c.stream().map(drawCircle(style)).collect(joining());
    }

    public static Function<List<Circle>, String> drawCircles(final Style style) {
        return
                c -> c.stream().map(drawCircle(style)).collect(joining());
    }

    public static Function<Circle, String> drawCircle(final String style) {
        return p -> newCircle(p.getCentre(), p.getR(), style);
    }

    public static Function<Circle, String> drawCircle(final Style style) {
        return p -> newCircle(p.getCentre(), p.getR(), style);
    }

    public static Function<List<Pair<Point2D, String>>, String> drawTexts() {
        return p -> p.stream().map(drawText()).collect(joining());
    }

    public static Function<Pair<Point2D, String>, String> drawText(int fontSize) {
        return p -> format("<text x=\"%f\" y=\"%f\" fill=\"black\" font-size=\"%d\">%s</text>", p.getLeft().getX() + 5, p.getLeft().getY() + 5, fontSize, p.getRight());
    }

    public static Function<Pair<Point2D, String>, String> drawText() {
        return drawText(18);
    }

    public static String newPolyline(Collection<Point2D> points, String style) {
        return format("<polyline points=\"%s\" style=\"%s\"/>", toPointsString(points), style);
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

    public static String newCircle(Point2D centre, double r, Style style) {
        return format("<circle cx=\"%f\" cy=\"%f\" r=\"%f\" style=\"%s\" />",
                centre.getX(),
                centre.getY(),
                r,
                toSVGString(style)
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

    public static String toHex(Color color) {
        if (color == null) {
            return "none";
        } else {
            return format("#%s", Integer.toHexString((color.getRGB() & 0xffffff) | 0x1000000).substring(1));
        }
    }

    public static String commaSep(Point2D point) {
        return String.format("%f,%f", point.getX(), point.getY());
    }



    public static String toSVG(Point2D point, Supplier<String> instructionType) {
        return format("%s%s", instructionType.get(), commaSep(point));
    }





//    private static String toPointsString(Collection<Line2D> points) {
//        return points.stream().map(point -> format("%s,%s", point.getX(), point.getY())).collect(joining(" "));
//    }

}
