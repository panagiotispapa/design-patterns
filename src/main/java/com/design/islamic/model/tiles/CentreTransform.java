package com.design.islamic.model.tiles;

public class CentreTransform {

    private final double ratio;
    private final Hex.Type type;
    private final Hex.Vertex vertex;

    public CentreTransform(double ratio, Hex.Type type, Hex.Vertex vertex) {
        this.ratio = ratio;
        this.type = type;
        this.vertex = vertex;
    }

    public double getRatio() {
        return ratio;
    }

    public Hex.Vertex getVertex() {
        return vertex;
    }

    public Hex.Type getType() {
        return type;
    }

    public static CentreTransform newTransform(Hex hex, Hex.Vertex vertex) {
        return newTransform(hex.getRatio(), hex.getType(), vertex);
    }

    public static CentreTransform newTransform(double ratio, Hex.Type type, Hex.Vertex vertex) {
        return new CentreTransform(ratio, type, vertex);
    }

    public interface RatioBuilder {
        TypeBuilder ratio(double ratio);
    }

    public interface TypeBuilder {
        VertexBuilder type(Hex.Type type);
    }

    public interface VertexBuilder {
        CentreTransform vertex(Hex.Vertex vertex);
    }

    public static RatioBuilder transform() {
        return ratio -> type -> vertex -> newTransform(ratio, type, vertex);

    }
}
