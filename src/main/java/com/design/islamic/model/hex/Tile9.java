package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.lang.Math.*;
import static java.util.Arrays.asList;

public class Tile9 implements Tile {

    private final List<List<Point2D>> lines;

    private List<Point2D> outerLayer1;
    private List<Point2D> outerLayer2;

    private List<Point2D> outerLayer1Rot;
    private List<Point2D> outerLayer2Rot;
    private List<Point2D> outerEdgesRot;

    private List<Point2D> innerLayer1;
    private List<Point2D> innerLayer1Rot;

    private List<Point2D> pointsA;
    private List<Point2D> pointsB;
    private List<Point2D> pointsC;
    private List<Point2D> pointsD;

    private List<Point2D> pointsE;
    private List<Point2D> pointsF;
    private List<Point2D> pointsG;

    private List<Point2D> pointsH;
    private List<Point2D> pointsI;
    private List<Point2D> pointsJ;

    private List<Point2D> pointsK;
    private List<Point2D> pointsL;
    private List<Point2D> pointsM;

    private List<Point2D> pointsN;
    private List<Point2D> pointsO;

    private List<Point2D> pointsP;
    private List<Point2D> pointsQ;
    private List<Point2D> pointsR;
    private List<Point2D> pointsS;

    private List<Point2D> pointsV;
    private List<Point2D> pointsU;

    private final Point2D centre;
    private final double r;
    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    public static final double OUTER_R1 = HEX_DIST_HEIGHT - HEX_DIST_PROJ * tan(HEX_PHI - PI / 4.0);
    public static final double OUTER_R2 = OUTER_R1 * OUTER_R1;
    private static final double DIST_OUTER_1_2_HALF = ((OUTER_R1 - OUTER_R2) / 2.0);
    public static final double OUTER_R4 = OUTER_R2 + DIST_OUTER_1_2_HALF;


    public static final double INNER_R1 = DIST_OUTER_1_2_HALF / cos(HEX_PHI_HALF);
    public static final double INNER_R2 = INNER_R1 * HEX_DIST_NEW_CENTRE;


    public Tile9(Point2D centre, double r) {

        this.centre = centre;
        this.r = r;
        lines = newArrayList();

        init();

        buildPointsA_B_C_D_N_O();
        buildPointsE_F_G_H_I_J();
        buildPointsK_L_M();

        buildLines();

    }

    private void init() {

        outerEdgesRot = newHexagonRot(centre, r);

        outerLayer1Rot = newHexagonRot(centre, r * OUTER_R1);
        outerLayer2Rot = newHexagonRot(centre, r * OUTER_R2);

        outerLayer1 = newHexagon(centre, r * OUTER_R1);
        outerLayer2 = newHexagon(centre, r * OUTER_R2);

        innerLayer1 = newHexagon(centre, r * INNER_R1);
        innerLayer1Rot = newHexagonRot(centre, r * INNER_R1);
    }

    private void buildPointsA_B_C_D_N_O() {
        pointsA = newArrayList();
        pointsB = newArrayList();
        pointsC = newArrayList();
        pointsD = newArrayList();

        pointsN = newArrayList();
        pointsO = newArrayList();

        pointsV = newArrayList();
        pointsU = newArrayList();

        final double b = OUTER_R2 * sin(HEX_PHI / 4.0);
        final double distN = r * (b / cos((PI - HEX_PHI) / 4.0));

        final double th = PI_QUARTER + HEX_PHI_HALF;
        final double dist = r * (((OUTER_R1 - OUTER_R2) / 2.0) / cos(PI_QUARTER - HEX_PHI_HALF));

        final double th2 = PI_HALF + HEX_PHI / 4.0;

        for (int i = 0; i < HEX_N; i++) {
            final double newPhi = HEX_RADIANS_ROT[i] + th;
            final double newPhi2 = HEX_RADIANS_ROT[i] - th;
            pointsA.add(newEdgeAt(outerLayer2Rot.get(i), dist, newPhi));
            pointsB.add(newEdgeAt(outerLayer1Rot.get(i), dist, newPhi));
            pointsC.add(newEdgeAt(outerLayer2Rot.get(i), dist, newPhi2));
            pointsD.add(newEdgeAt(outerLayer1Rot.get(i), dist, newPhi2));

            pointsN.add(newEdgeAt(outerLayer2Rot.get(i), distN, newPhi));
            pointsO.add(newEdgeAt(outerLayer2Rot.get(i), distN, newPhi2));

            pointsV.add(newEdgeAt(outerLayer2Rot.get(i), dist, HEX_RADIANS_ROT[i] + th2));
            pointsU.add(newEdgeAt(outerLayer2Rot.get(i), dist, HEX_RADIANS_ROT[i] - th2));


        }

    }

    private void buildPointsE_F_G_H_I_J() {
        pointsE = newArrayList();
        pointsF = newArrayList();
        pointsG = newArrayList();
        pointsH = newArrayList();
        pointsI = newArrayList();
        pointsJ = newArrayList();

        pointsP = newArrayList();
        pointsQ = newArrayList();
        pointsR = newArrayList();
        pointsS = newArrayList();

        final double dist1 = r * (((1.0 - INNER_R1) * HEX_DIST_HEIGHT - OUTER_R1) / cos(PI_QUARTER));
        final double dist2 = r * (DIST_OUTER_1_2_HALF / sin(PI_QUARTER));
        final double dist3 = r * ((1.0 - OUTER_R1) / 2.0 / cos(PI_QUARTER));
        final double dist4 = r * (DIST_OUTER_1_2_HALF / cos(HEX_PHI / 4.0));

        final double th = PI_QUARTER + HEX_PHI_HALF;
        final double th2 = PI_HALF + HEX_PHI / 4.0;

        for (int i = 0; i < HEX_N; i++) {
            pointsE.add(newEdgeAt(outerLayer1.get(i), dist1, HEX_RADIANS[i] + PI_QUARTER));
            pointsF.add(newEdgeAt(outerLayer1.get(i), dist2, HEX_RADIANS[i] + PI_QUARTER));
            pointsG.add(newEdgeAt(outerLayer1.get(i), dist3, HEX_RADIANS[i] + PI_QUARTER));

            pointsH.add(newEdgeAt(outerLayer1.get(i), dist1, HEX_RADIANS[i] - PI_QUARTER));
            pointsI.add(newEdgeAt(outerLayer1.get(i), dist2, HEX_RADIANS[i] - PI_QUARTER));
            pointsJ.add(newEdgeAt(outerLayer1.get(i), dist3, HEX_RADIANS[i] - PI_QUARTER));

            pointsP.add(newEdgeAt(outerLayer2.get(i), dist4, HEX_RADIANS[i] + th));
            pointsQ.add(newEdgeAt(outerLayer2.get(i), dist4, HEX_RADIANS[i] - th));

            pointsR.add(newEdgeAt(outerLayer2.get(i), dist4, HEX_RADIANS[i] + th2));
            pointsS.add(newEdgeAt(outerLayer2.get(i), dist4, HEX_RADIANS[i] - th2));

        }

    }

    private void buildPointsK_L_M() {
        pointsK = newArrayList();
        pointsL = newArrayList();
        pointsM = newArrayList();

        final double dist = r * Tile9.INNER_R1;

        int index = 0;
        for (Point2D edge : outerEdgesRot) {
            pointsK.add(newEdgeAt(edge, dist, HEX_RADIANS_ROT[toHexIndex(2 + index)]));
            pointsL.add(newEdgeAt(edge, dist, HEX_RADIANS_ROT[toHexIndex(3 + index)]));
            pointsM.add(newEdgeAt(edge, dist, HEX_RADIANS_ROT[toHexIndex(4 + index)]));

            index++;
        }

    }

    private void buildLines() {
        for (int i = 0; i < HEX_N; i++) {


            lines.add(asList(
                    pointsE.get(i),
                    pointsL.get(i),
                    pointsM.get(i)

            ));

            lines.add(asList(
                    pointsH.get(i),
                    pointsL.get(toHexIndex(i+5)),
                    pointsK.get(toHexIndex(i+5))

            ));

            lines.add(asList(
                    innerLayer1Rot.get(toHexIndex(i+4)),
                    pointsU.get(i),
                    pointsA.get(i),
                    pointsB.get(i),
                    pointsJ.get(toHexIndex(i+1)),
                    pointsI.get(toHexIndex(i+1)),
                    pointsQ.get(toHexIndex(i+1)),
                    pointsR.get(toHexIndex(i+1)),
                    innerLayer1.get(toHexIndex(i+2))

            ));


            lines.add(asList(
                    innerLayer1Rot.get(toHexIndex(i+1)),
                    pointsV.get(i),
                    pointsC.get(i),
                    pointsD.get(i),
                    pointsG.get(i),
                    pointsF.get(i),
                    pointsP.get(i),
                    pointsS.get(i),

                    innerLayer1.get(toHexIndex(i+5))

            ));


            lines.add(asList(
                    pointsE.get(i),
                    pointsN.get(toHexIndex(i+5)),
                    outerLayer1Rot.get(toHexIndex(i+5))
            ));

            lines.add(asList(
                    pointsH.get(i),
                    pointsO.get(i),
                    outerLayer1Rot.get(i)
            ));



        }

//        lines.add(newHexagonRot(centre, r));
    }
    public List<Point2D> getPointsB() {
        return pointsB;
    }

    public List<Point2D> getPointsA() {
        return pointsA;
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

    public List<Point2D> getPointsF() {
        return pointsF;
    }

    public List<Point2D> getPointsG() {
        return pointsG;
    }

    public List<Point2D> getPointsH() {
        return pointsH;
    }

    public List<Point2D> getPointsI() {
        return pointsI;
    }

    public List<Point2D> getPointsJ() {
        return pointsJ;
    }

    public List<Point2D> getPointsK() {
        return pointsK;
    }

    public List<Point2D> getPointsL() {
        return pointsL;
    }

    public List<Point2D> getPointsM() {
        return pointsM;
    }

    public List<Point2D> getPointsN() {
        return pointsN;
    }

    public List<List<Point2D>> getLines() {
        return lines;
    }

    public List<Point2D> getPointsO() {
        return pointsO;
    }

    public List<Point2D> getPointsP() {
        return pointsP;
    }

    public List<Point2D> getPointsQ() {
        return pointsQ;
    }

    public List<Point2D> getPointsR() {
        return pointsR;
    }

    public List<Point2D> getPointsS() {
        return pointsS;
    }

    public List<Point2D> getOuterLayer1() {
        return outerLayer1;
    }

    public List<Point2D> getOuterLayer2() {
        return outerLayer2;
    }

    public List<Point2D> getOuterLayer1Rot() {
        return outerLayer1Rot;
    }

    public List<Point2D> getOuterLayer2Rot() {
        return outerLayer2Rot;
    }

    public List<Point2D> getInnerLayer1() {
        return innerLayer1;
    }

    public List<Point2D> getInnerLayer1Rot() {
        return innerLayer1Rot;
    }





    public Payload getPayload(){
        return Payloads.newPayloadFromPolygonsAndLines(asList(newHexagonRot(centre, r)), lines);
    }


}
