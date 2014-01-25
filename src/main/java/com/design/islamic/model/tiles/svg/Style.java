package com.design.islamic.model.tiles.svg;

public class Style {

    private final String fill;
    private final String stroke;
    private final int strokeWidth;
    private final double fillOpacity;
    private final double strokeOpcatity;


    public Style(String fill, String stroke, int strokeWidth, double fillOpacity, double strokeOpcatity) {
        this.fill = fill;
        this.stroke = stroke;
        this.strokeWidth = strokeWidth;
        this.fillOpacity = fillOpacity;
        this.strokeOpcatity = strokeOpcatity;
    }

    public String getFill() {
        return fill;
    }

    public String getStroke() {
        return stroke;
    }

    public int getStrokeWidth() {
        return strokeWidth;
    }

    public double getFillOpacity() {
        return fillOpacity;
    }

    public double getStrokeOpcatity() {
        return strokeOpcatity;
    }

    @Override
    public String toString() {
        return String.format("fill:%s;stroke:%s;stroke-width:%d;fill-opacity:%s;stroke-opacity:%s", fill.toString(), stroke.toString(), strokeWidth, fillOpacity, strokeOpcatity);
    }
}
