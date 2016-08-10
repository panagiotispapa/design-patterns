package com.design.common.model;

import java.awt.*;

import static com.design.common.view.SvgFactory.toHex;
import static java.lang.String.format;
import static java.util.Optional.ofNullable;

public class Style {
    private final Color stroke;
    private final int strokeWidth;
    private final Color fill;
    private final Double fillOpacity;
    private final Double strokeOpcacity;


    public Style(Color stroke, int strokeWidth, Color fill, Double fillOpacity, Double strokeOpcacity) {
        this.stroke = stroke;
        this.strokeWidth = strokeWidth;
        this.fill = fill;
        this.fillOpacity = fillOpacity;
        this.strokeOpcacity = strokeOpcacity;
    }

    public Color getStroke() {
        return stroke;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public Color getFill() {
        return fill;
    }

    public Double getFillOpacity() {
        return fillOpacity;
    }

    public Double getStrokeOpcacity() {
        return strokeOpcacity;
    }

    public String toSVG() {
        StringBuilder builder = new StringBuilder();
        builder.append(format("stroke:%s;", toHex(stroke)));
        builder.append(format("fill:%s;", toHex(fill)));
        builder.append(format("stroke-width:%d;", strokeWidth));

        ofNullable(strokeOpcacity).ifPresent(o -> builder.append(format("stroke-opacity:%f;", o)));
        ofNullable(fillOpacity).ifPresent(o -> builder.append(format("fill-opacity:%f;", o)));

        return builder.toString();

    }


    public static class Builder {
        private final Color stroke;
        private final int strokeWidth;
        private Color fill;
        private Double fillOpacity;
        private Double strokeOpacity;

        public Builder(Color stroke, int strokeWidth) {
            this.stroke = stroke;
            this.strokeWidth = strokeWidth;
        }

        public Builder withFill(Color fill) {
            this.fill = fill;
            return this;
        }

        public Builder withFillOpacity(Double fillOpacity) {
            this.fillOpacity = fillOpacity;
            return this;
        }

        public Builder withStrokeOpacity(Double strokeOpcacity) {
            this.strokeOpacity = strokeOpcacity;
            return this;
        }

        public Style build() {
            return new Style(stroke, strokeWidth, fill, fillOpacity, strokeOpacity);
        }

    }


}
