package com.design.islamic.model.tiles;

public class CentreTransform {

    private final float ratio;
    private final Hex.Vertex vertex;
    private final Hex.Type type;

    public CentreTransform(float ratio, Hex.Vertex vertex, Hex.Type type) {
        this.ratio = ratio;
        this.vertex = vertex;
        this.type = type;
    }

    public float getRatio() {
        return ratio;
    }

    public Hex.Vertex getVertex() {
        return vertex;
    }

    public Hex.Type getType() {
        return type;
    }

    public static CentreTransform newTransform(float ratio, Hex.Vertex vertex, Hex.Type type) {
        return new CentreTransform(ratio, vertex, type);

    }
}
