package com.design.common.view;

import com.design.common.CanvasPoint;
import com.design.common.model.Arc;
import com.design.common.model.Circle;
import com.design.common.model.Style;
import org.apache.batik.dom.svg.SAXSVGDocumentFactory;
import org.apache.batik.util.XMLResourceDescriptor;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.svg.SVGDocument;

import java.awt.*;
import java.io.IOException;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;

import static com.design.common.CanvasPoint.point;
import static java.lang.Integer.toHexString;
import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.joining;

public class SvgFactory {

    private SvgFactory() {

    }

    public static String buildBackground(Dimension dim) {
        return drawPolygon(
                asList(
                        point(0, 0),
                        point(dim.getWidth(), 0),
                        point(dim.getWidth(), dim.getHeight()),
                        point(0, dim.getHeight())
                ),
                new Style.Builder(Color.BLACK, 1).withFill(Color.BLACK).build());
    }

    public static String drawPolygon(Collection<CanvasPoint> points, Style style) {
        return format("<polygon points=\"%s\" style=\"%s\" />", points.stream().map(CanvasPoint::toCommaSeparatedString).collect(joining(" ")), style.toSVG());

    }

    public static Function<Pair<CanvasPoint, String>, String> drawText(int fontSize) {
        return p -> format("<text x=\"%f\" y=\"%f\" fill=\"black\" font-size=\"%d\">%s</text>", p.getLeft().getX() + 5, p.getLeft().getY() + 5, fontSize, p.getRight());
    }

    public static String drawCircles(Collection<Circle> circles, final Style style) {
        return circles.stream().map(circle -> circle.draw(style)).collect(joining());
    }


    public static String newCircle(CanvasPoint centre, double r, Style style) {
        return format("<circle cx=\"%f\" cy=\"%f\" r=\"%f\" style=\"%s\" />",
                centre.getX(),
                centre.getY(),
                r,
                style.toSVG()
        );
    }

    public static String drawArcList(List<Arc> arcList, Style style) {
        return arcList.stream().map(arc -> drawArc(arc, style)).collect(joining());

    }

    private static String drawArc(Arc arc, Style style) {
        final String path = arc.isUp() ? "M %f %f a 1 1 0 0 1 %f 0" : "M %f %f a 1 1 0 0 0 %f 0";

        return format("<path d=\"%s\" style=\"%s\"/>",
                format(path,
                        arc.getCircle().getCentre().getX() - arc.getCircle().getR(),
                        arc.getCircle().getCentre().getY(),
                        2 * arc.getCircle().getR()
                ),
                style.toSVG());

    }

    public static SVGDocument fromSvgDoc(String svgDoc) {

        SAXSVGDocumentFactory f = new SAXSVGDocumentFactory(XMLResourceDescriptor.getXMLParserClassName());
        try {
            return f.createSVGDocument("http://www.test.com", new StringReader(svgDoc));

        } catch (IOException e) {
            e.printStackTrace();  //To change body circle catch statement use File | Settings | File Templates.
        }
        return null;
    }

    public static String buildSvg(Dimension dim, String shapes) {
        return format("<svg width=\"%s\" height=\"%s\">%s</svg>", valueOf(dim.getWidth()), valueOf(dim.getHeight()), shapes);
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
        return ofNullable(color).map(c -> format("#%s", toHexString((c.getRGB() & 0xffffff) | 0x1000000).substring(1))).orElse("none");
    }

}
