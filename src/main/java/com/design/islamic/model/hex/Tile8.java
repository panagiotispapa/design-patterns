package com.design.islamic.model.hex;

import com.design.islamic.model.Tile;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.*;
import static java.util.Arrays.asList;

public class Tile8 implements Tile {

    private final List<Point2D> mainHex;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);







    private List<List<Point2D>> lines;

    private final double height;
    private final double innerHeight;
    private final double innerR;
    private final double innerR2;

    private final double outerR;
    private final double outerPhi;

    private final Point2D centre;
    private final double r;

    private List<Point2D> heights;

    private List<Point2D> pointsA;
    private List<Point2D> pointsB;
    private List<Point2D> pointsC;

    private List<Point2D> pointsD;
    private List<Point2D> pointsE;

    private List<Point2D> pointsF;
    private List<Point2D> pointsG;
    private List<Point2D> pointsH;

    public Tile8(Point2D centre, double r) {

        this.centre = centre;
        this.r = r;

        height = r * HEX_DIST_HEIGHT;

        innerHeight = r * (sin(HEX_PHI) - 0.5) * cos(HEX_PHI);
        innerR = innerHeight / HEX_DIST_HEIGHT;

        double sin = sin(HEX_PHI_QUARTER);

        outerR = 2.0 * height * sin - 2.0 * (height - 2.0 * innerHeight) * sin;
        outerPhi = PI - (PI_HALF - HEX_PHI_QUARTER);

        innerR2 = innerHeight / sin(PI_QUARTER);

        mainHex = newHexagonRot(centre, r);

        heights = newHexagon(centre, height);


        buildPoints();
        buildLines();
    }

    private void buildPoints() {
        pointsA = newArrayList();
        pointsB = newArrayList();
        pointsC = newArrayList();

        pointsD = newArrayList();
        pointsE = newArrayList();

        pointsF = newArrayList();
        pointsG = newArrayList();
        pointsH = newArrayList();

        for (int i = 0; i < HEX_N; i++) {
            pointsA.add(newEdgeAt(mainHex.get(i), innerR, HEX_RADIANS_ROT.get(toHexIndex(i + 4))));
            pointsB.add(newEdgeAt(mainHex.get(i), innerR, HEX_RADIANS_ROT.get(toHexIndex(i + 3))));
            pointsC.add(newEdgeAt(mainHex.get(i), innerR, HEX_RADIANS_ROT.get(toHexIndex(i + 2))));

            pointsD.add(newEdgeAt(heights.get(i), outerR, HEX_RADIANS.get(i) - outerPhi));
            pointsE.add(newEdgeAt(heights.get(i), outerR, HEX_RADIANS.get(i) + outerPhi));
        }

        for (int i = 0; i < RECT_N; i++) {
            pointsF.add(newEdgeAt(centre, innerR2, RECT_RADIANS_ROT.get(i)));
            pointsG.add(newEdgeAt(centre, innerR2, RECT_RADIANS_ROT.get(i) - HEX_PHI_HALF));
            pointsH.add(newEdgeAt(centre, innerR2, RECT_RADIANS_ROT.get(i) - HEX_PHI));
        }

    }

    private void buildLines() {
        lines = newArrayList();

        for (int i = 0; i < HEX_N; i++) {
            lines.add(asList(
                    pointsA.get(i),
                    pointsB.get(i),
                    pointsC.get(i)
            ));

            lines.add(asList(
                    pointsD.get(i),
                    heights.get(i),
                    pointsE.get(i)
            ));

        }

        lines.add(asList(
                pointsA.get(1),
                pointsF.get(0),
                pointsE.get(0)

        ));

        lines.add(asList(
                pointsC.get(1),
                pointsF.get(1),
                pointsD.get(3)

        ));

        lines.add(asList(
                pointsA.get(4),
                pointsF.get(2),
                pointsE.get(3)

        ));

        lines.add(asList(
                pointsC.get(4),
                pointsF.get(3),
                pointsD.get(0)

        ));

        lines.add(asList(
                pointsA.get(0),
                pointsH.get(0),
                pointsE.get(5)

        ));

        lines.add(asList(
                pointsC.get(0),
                pointsH.get(1),
                pointsD.get(2)

        ));

        lines.add(asList(
                pointsA.get(3),
                pointsH.get(2),
                pointsE.get(2)

        ));

        lines.add(asList(
                pointsC.get(3),
                pointsH.get(3),
                pointsD.get(5)

        ));

        lines.add(asList(
                pointsA.get(5),
                pointsG.get(3),
                pointsE.get(4)

        ));

        lines.add(asList(
                pointsC.get(5),
                pointsG.get(0),
                pointsD.get(1)

        ));

        lines.add(asList(
                pointsA.get(2),
                pointsG.get(1),
                pointsE.get(1)

        ));

        lines.add(asList(
                pointsC.get(2),
                pointsG.get(2),
                pointsD.get(4)

        ));

    }

    public double getInnerR() {
        return innerR;
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

    public List<Point2D> getHeights() {
        return heights;
    }

    public List<Point2D> getPointsE() {
        return pointsE;
    }

    public List<Point2D> getPointsD() {
        return pointsD;
    }

    public List<Point2D> getPointsH() {
        return pointsH;
    }

    public List<Point2D> getPointsG() {
        return pointsG;
    }

    public List<Point2D> getPointsF() {
        return pointsF;
    }

    public List<List<Point2D>> getLines() {
        return lines;
    }






    @Override
    public List<XMLBuilder> drawMe() {
        List<XMLBuilder> out = newArrayList();

//        out.add(drawPolygon(mainHex, styleWhite));
        out.addAll(drawPolylines(lines, styleWhiteBold));

        return out;

    }
}
