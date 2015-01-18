package com.design.islamic.model;

import com.design.islamic.model.tiles.Hex;

import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.stream.Collectors;

import static com.design.islamic.model.Pair.newPair;
import static com.design.islamic.model.tiles.Hex.Vertex.*;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;

public class DrawSegmentsInstructions {

    public static List<Pair<Hex.Vertex>> NONE = emptyList();
    public static List<Pair<Hex.Vertex>> PERIMETER = asList(
            newPair(ONE, TWO),
            newPair(TWO, THREE),
            newPair(THREE, FOUR),
            newPair(FOUR, FIVE),
            newPair(FIVE, SIX),
            newPair(SIX, ONE)
    );

    public static List<Pair<Hex.Vertex>> DIAGONALS = asList(
            newPair(ONE, FOUR),
            newPair(TWO, FIVE),
            newPair(THREE, SIX)
    );

    public static List<Pair<Hex.Vertex>> DIAGONALS_FULL = asList(
            newPair(ONE, FOUR),
            newPair(ONE, FIVE),
            newPair(TWO, FIVE),
            newPair(THREE, SIX)
    );

    public static List<Line2D> lines(int offset, Point2D startingPoint, double R, Hex hex, List<Pair<Hex.Vertex>> instructions) {
        return instructions.stream().map(i -> new Line2D.Double(
                i.getA().transform(offset, startingPoint, R, hex),
                i.getB().transform(offset, startingPoint, R, hex)
        )).collect(Collectors.toList());

    }

}