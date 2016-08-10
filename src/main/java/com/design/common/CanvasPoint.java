package com.design.common;

import com.design.common.model.Style;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.function.Supplier;

import static java.lang.String.format;

public interface CanvasPoint extends Supplier<Point2D> {

    static CanvasPoint point(double x, double y) {
        return () -> new Point2D.Double(x, y);
    }

    default double getX() {
        return get().getX();
    }

    default double getY() {
        return get().getY();
    }

    default CanvasPoint translate(CanvasPoint translation) {
        return point(getX() + translation.getX(), getY() + translation.getY());
    }

    default CanvasPoint scale(double scaleTo) {
        return point(getX() * scaleTo, getY() * scaleTo);
    }

    default String toSVGInstruction(Supplier<String> instructionType) {
        return format("%s%s", instructionType.get(), toCommaSeparatedString());
    }

    default String toCommaSeparatedString() {
        return format("%s,%s", getX(), getY());
    }

    default String highlightPoint() {
        return highlightPoint(Color.RED, 3);
    }

    default String highlightPoint(Color fill, int radius) {
        Style style = new Style.Builder(Color.BLACK, 1).withFill(fill).build();
        return toSvgCircle(radius, style);
    }

    default String toSvgCircle(double r, Style style) {
        return format("<circle cx=\"%f\" cy=\"%f\" r=\"%f\" style=\"%s\" />",
                getX(),
                getY(),
                r,
                style.toSVG()
        );
    }
}
