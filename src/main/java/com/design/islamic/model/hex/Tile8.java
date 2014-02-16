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

public class Tile8 implements Tile {

    private final List<Point2D> mainHex;
    private final List<List<Point2D>> lines;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    public static final double OUTER_R1 = 1.0 - HEX_DIST_DIAGONAL;
    public static final double OUTER_R2 = OUTER_R1 * HEX_DIST_PROJ;
    public static final double OUTER_R3 = OUTER_R1 - OUTER_R2;
    public static final double OUTER_R4 = OUTER_R1*HEX_DIST_HEIGHT;
    public static final double OUTER_R5 = 1-Tile8.OUTER_R4 * HEX_DIST_NEW_CENTRE;

    public Tile8(Point2D centre, double r) {

        mainHex = newHexagonRot(centre, r);

        lines = newArrayList();

        lines.addAll(calcOuterLines1(centre, r));
        lines.addAll(calcOuterLines2(centre, r));
        lines.addAll(calcOuterLines3(centre, r));
//        lines.addAll(calcOuterLines4(centre, r));

    }


    public static List<Point2D> calcOuterPoints1(Point2D centre, double r) {

        List<Point2D> out = newArrayList();

        List<Point2D> outerHexPoints = newHexagon(centre, r);

        double newR = r * OUTER_R1;

        int index = 0;
        for (Point2D outerHexPoint : outerHexPoints) {

//            List<Point2D> layerOut = newHexagon(outerHexPoint, newR);
            List<Point2D> layerOutHeights = newHexagonRot(outerHexPoint, newR * HEX_DIST_HEIGHT);

            out.add(layerOutHeights.get(toHexIndex(2 + index)));
            out.add(layerOutHeights.get(toHexIndex(3 + index)));
            index++;
        }

        return out;

    }

    public static List<List<Point2D>> calcOuterHex1(Point2D centre, double r) {
        List<Point2D> newCentres = calcOuterPoints1(centre, r);

        List<List<Point2D>> out = newArrayList();

        double newR = r * OUTER_R2;

        for (Point2D newCentre : newCentres) {
            out.add(newHexagonRot(newCentre, newR));
        }

        return out;

    }

    public static List<List<Point2D>> calcOuterLines1(Point2D centre, double r) {

        List<List<Point2D>> out = newArrayList();

        List<Point2D> outerHexRotEdges = newHexagonRot(centre, r);

        int index =0;
        for (Point2D outerHexRotEdge : outerHexRotEdges) {
            List<Point2D> edges = newHexagonRot(outerHexRotEdge, r * Tile8.OUTER_R3);
            List<Point2D> outerEdges = newHexagon(outerHexRotEdge, r * Tile8.OUTER_R3 * HEX_DIST_NEW_CENTRE);

            out.add(asList(
                    outerEdges.get(toHexIndex(4 + index)),
                    edges.get(toHexIndex(4 + index)),
                    edges.get(toHexIndex(3 + index)),
                    edges.get(toHexIndex(2 + index)),
                    outerEdges.get(toHexIndex(3 + index))

            ));


            index++;
                    
                            
                            
        }

        return out;

    }

    public static List<List<Point2D>> calcOuterLines2(Point2D centre, double r) {

        List<List<Point2D>> out = newArrayList();

        List<Point2D> heights = newHexagon(centre, r * HEX_DIST_HEIGHT);

        List<Point2D> outerPoints = calcOuterPoints1(centre, r);

        int index = 0;

        for (Point2D height : heights) {

            List<Point2D> heights_1 = newHexagon(outerPoints.get(2*index), r*Tile8.OUTER_R2*HEX_DIST_HEIGHT*0.73);
            List<Point2D> heights_2 = newHexagon(outerPoints.get(2*index + 1), r*Tile8.OUTER_R2*HEX_DIST_HEIGHT*0.73);

            out.add(asList(
                    height,
                    heights_1.get(toHexIndex(0+index)),
                    outerPoints.get(2*index)
            ));

           out.add(asList(
                    height,
                    heights_2.get(toHexIndex(0+index)),
                    outerPoints.get(2*index+1)
            ));

            index++;
        }

        return out;
    }

    public static List<Point2D> calcOuterPoints2(Point2D centre, double r) {

        List<Point2D> out = newArrayList();

        List<Point2D> outerHexEdges = newHexagon(centre, r);

        int index = 0;
        for (Point2D outerHexEdge : outerHexEdges) {

            List<Point2D> points = newHexagon(outerHexEdge, r * OUTER_R4 * HEX_DIST_NEW_CENTRE);

            out.add(points.get(toHexIndex(3+index)));

                    index++;

        }


        return out;
    }

    public static List<List<Point2D>> calcOuterLines3(Point2D centre, double r) {

        List<List<Point2D>> out = newArrayList();

        List<Point2D> edges = newHexagon(centre, r*Tile8.OUTER_R5);
        List<Point2D> edgesRot = newHexagonRot(centre, r*Tile8.OUTER_R5);

        double newR = r * OUTER_R4;
        double newR2 = r*Tile8.OUTER_R5 * Tile8.OUTER_R1*HEX_DIST_HEIGHT;

        int index = 0;
        for (Point2D edge : edges) {

            List<Point2D> layer1 = newHexagonRot(edge, newR);
            List<Point2D> layer2 = newHexagonRot(edge, newR2);

            out.add(asList(

                    layer1.get(toHexIndex(0+index)),
                    edge,
                    layer1.get(toHexIndex(5+index))

            ));

            out.add(asList(
                    layer1.get(toHexIndex(0+index)),
                    edgesRot.get(toHexIndex(0+index)),
                    layer2.get(toHexIndex(2+index)),
                    edge
            ));
            out.add(asList(
                    layer1.get(toHexIndex(5+index)),
                    edgesRot.get(toHexIndex(5+index)),
                    layer2.get(toHexIndex(3+index)),
                    edge
            ));



            index++;
        }

        return out;
    }


    @Override
    public List<XMLBuilder> drawMe() {
        List<XMLBuilder> out = newArrayList();




//        out.add(drawPolygon(mainHex, styleWhite));
        out.addAll(drawPolylines(lines, styleWhiteBold));

        return out;

    }
}
