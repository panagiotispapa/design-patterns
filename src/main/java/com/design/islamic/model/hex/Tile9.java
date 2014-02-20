package com.design.islamic.model.hex;

import com.design.islamic.model.Tile;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.*;

public class Tile9 implements Tile {

    private final List<List<Point2D>> lines;

    private List<Point2D> pointsA;
    private List<Point2D> pointsB;

    private final Point2D centre;
    private final double r;
    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    public static final double OUTER_R1 = HEX_DIST_HEIGHT - HEX_DIST_PROJ * tan(HEX_PHI - PI / 4.0);
    public static final double OUTER_R2 = OUTER_R1 * OUTER_R1;
    public static final double OUTER_R3 = OUTER_R1 * OUTER_R1 * OUTER_R1;
    public static final double OUTER_R4 = OUTER_R2 + (OUTER_R1 - OUTER_R2) / 2.0;

    public static final double OUTER_EXT_R1 = 1.0 - OUTER_R1;
    public static final double OUTER_EXT_R2 = 1.0 - OUTER_R2;
    public static final double INNER_R1 = ((OUTER_R1 - OUTER_R2) / 2.0) / cos(HEX_PHI / 2.0);
    public static final double INNER_R2 = INNER_R1 * HEX_DIST_NEW_CENTRE;
    public static final double INNER_R3 = 1.0 - INNER_R1;
    public static final double INNER_R4 = (INNER_R3 * HEX_DIST_HEIGHT - OUTER_R1) / cos(PI / 4.0);

    public Tile9(Point2D centre, double r) {

        this.centre = centre;
        this.r = r;
        lines = newArrayList();

        lines.addAll(calcOuterLines1(centre, r));
        lines.add(newHexagonRot(centre, r));

        buildPointsAandB();

    }

    private void buildPointsAandB() {
        pointsA = newArrayList();
        pointsB = newArrayList();

        List<Point2D> outerLayer1Rot = newHexagonRot(centre, r * OUTER_R1);
        List<Point2D> outerLayer2Rot = newHexagonRot(centre, r * OUTER_R2);

        final double th = PI / 4.0 + HEX_PHI / 2.0;
        final double k = PI / 4.0 + HEX_PHI / 2.0;
        final double dist = ((OUTER_R1 - OUTER_R2) / 2.0) / cos(PI / 4.0 - HEX_PHI / 2.0);

        for (int i = 0; i < HEX_N; i++) {

            pointsA.add(newEdgeAt(outerLayer2Rot.get(i), r * dist, HEX_PHI * (i + 0.5) + k));
            pointsB.add(newEdgeAt(outerLayer1Rot.get(i), r * dist, HEX_PHI * (i + 0.5) + k));
        }

    }

    public List<Point2D> getPointsB() {
        return pointsB;
    }

    public List<Point2D> getPointsA() {
        return pointsA;
    }

    @Override
    public List<XMLBuilder> drawMe() {
        List<XMLBuilder> out = newArrayList();

//        out.add(drawPolygon(mainHex, styleWhite));
        out.addAll(drawPolylines(lines, styleWhiteBold));

        return out;

    }

    public static List<List<Point2D>> calcOuterLines1(Point2D centre, double r) {

        List<List<Point2D>> out = newArrayList();

        List<Point2D> outerRot = newHexagonRot(centre, r);

        int index = 0;
        List<Point2D> outerEdges = newHexagon(centre, r * OUTER_R1);

        for (Point2D edge : outerRot) {
            List<Point2D> smallHex = newHexagonRot(edge, r * Tile9.INNER_R1);

            out.add(Arrays.asList(
                    smallHex.get(toHexIndex(2 + index)),
                    smallHex.get(toHexIndex(3 + index)),
                    smallHex.get(toHexIndex(4 + index))

            ));

            out.add(Arrays.asList(
//                    newEdgeAt(outerEdges.get(toHexIndex(1+index)), r*INNER_R4, 4*(Math.PI/4.0) + (1+index)*HEX_PHI),
//                    outerEdges.get(toHexIndex(1+index)),
                    smallHex.get(toHexIndex(3 + index)),
                    newEdgeAt(outerEdges.get(index), r * INNER_R4, (Math.PI / 4.0) + index * HEX_PHI)
//                    newRectEdgeRot(outerEdges.get(index), r*INNER_R4, 0+index)
                    //outerEdges.get(index)

            ));

            out.add(Arrays.asList(
                    newEdgeAt(outerEdges.get(index), r * INNER_R4, -(Math.PI / 4.0) + index * HEX_PHI),
                    newHexEdgeRot(outerRot.get(toHexIndex(5 + index)), r * Tile9.INNER_R1, toHexIndex(2 + index))
//                    smallHex.get(toHexIndex(1+index))
            ));

            index++;
        }

        return out;
    }

    public static List<List<Point2D>> calcOuterLines2(Point2D centre, double r) {

        List<List<Point2D>> out = newArrayList();

        List<Point2D> outerEdges = newHexagonRot(centre, r * OUTER_R1);

        int index = 0;
        for (Point2D edge : outerEdges) {
            List<Point2D> smallHex = newHexagonRot(edge, r * Tile9.INNER_R1);

            out.add(Arrays.asList(
                    smallHex.get(toHexIndex(2 + index)),
                    smallHex.get(toHexIndex(3 + index)),
                    smallHex.get(toHexIndex(4 + index))

            ));

            index++;
        }

        return out;
    }
}
