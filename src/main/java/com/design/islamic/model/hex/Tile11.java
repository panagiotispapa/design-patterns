package com.design.islamic.model.hex;

import com.design.islamic.model.Tile;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile11 implements Tile {

    private final Point2D centre;
    private final double r;


    private List<List<Point2D>> lines;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    private final double height;
    private final double innerR;
    private final double innerHeight;
    private final double innerR2;

    private List<Point2D> mainHex;
    private List<Point2D> mainHexRot;

    private List<Point2D> mainHeights;

    private List<Point2D> pointsA;
    private List<Point2D> pointsB;
    private List<Point2D> pointsC;
    private List<Point2D> pointsD;

    private List<Point2D> pointsE;


    public Tile11(Point2D centre, double r) {

        this.centre = centre;
        this.r = r;

        height = r * HEX_DIST_HEIGHT;
        innerR = height - r / 2.0;
        innerHeight = innerR*HEX_DIST_HEIGHT;
        innerR2 = r - innerHeight;


        mainHex = newHexagon(centre,r);
        mainHexRot = newHexagonRot(centre, r);

        mainHeights = newHexagon(centre, height);

        buildPoints();
        buildLines();

    }

    private void buildPoints() {

        pointsA = newArrayList();
        pointsB = newArrayList();
        pointsC = newArrayList();
        pointsD = newArrayList();

        pointsE = newArrayList();

        for (int i = 0; i < HEX_N; i++) {
            pointsA.add(newEdgeAt(mainHexRot.get(i),innerR*HEX_DIST_HEIGHT,HEX_RADIANS_ROT.get(toHexIndex(i+2))));
            pointsB.add(newEdgeAt(mainHexRot.get(i),innerR,HEX_RADIANS.get(toHexIndex(i+3))));
            pointsC.add(newEdgeAt(mainHexRot.get(i),innerR,HEX_RADIANS.get(toHexIndex(i+4))));
            pointsD.add(newEdgeAt(mainHexRot.get(i),innerR*HEX_DIST_HEIGHT,HEX_RADIANS_ROT.get(toHexIndex(i+4))));
        }

    }
    private void buildLines() {

        lines = newArrayList();


        for (int i = 0; i < HEX_N; i++) {
            lines.add(asList(
                    pointsA.get(i),
                    pointsB.get(i),
                    pointsC.get(i),
                    pointsD.get(i)
            ));

            lines.add(asList(
                    pointsB.get(i),
                    pointsC.get(toHexIndex(i+1))
            ));

        }


    }

    public List<List<Point2D>> getLines() {
        return lines;
    }

    public List<Point2D> getPointsA() {
        return pointsA;
    }

    public List<Point2D> getPointsB() {
        return pointsB;
    }

    public List<Point2D> getPointsC() {
        return pointsC;
    }

    public List<Point2D> getPointsD() {
        return pointsD;
    }

    public List<Point2D> getPointsE() {
        return pointsE;
    }

    public double getInnerR() {
        return innerR;
    }

    public List<Point2D> getMainHex() {
        return mainHex;
    }

    public List<Point2D> getMainHexRot() {
        return mainHexRot;
    }

    public List<Point2D> getMainHeights() {
        return mainHeights;
    }

    public double getInnerR2() {
        return innerR2;
    }

    @Override
    public List<XMLBuilder> drawMe() {

        List<XMLBuilder> out = newArrayList();

        out.addAll(drawPolylines(lines, styleWhiteBold));

        return out;
    }

}
