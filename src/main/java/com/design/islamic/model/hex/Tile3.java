package com.design.islamic.model.hex;

import com.design.islamic.model.Tile;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.PolygonTools.HEX_RADIANS;
import static com.design.common.view.SvgFactory.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile3 implements Tile {

    private final List<Point2D> innerEdges;

//    private final List<List<Point2D>> outerRectangles;
//    private final List<List<Point2D>> outerLines;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    private final Point2D centre;
    private final double r;

    private List<List<Point2D>> lines;
    private List<List<Point2D>> lines2;


    public Tile3(Point2D centre, double r) {

        this.centre = centre;
        this.r = r;

        innerEdges = newHexagonRot(centre, r * HEX_DIST_EQ1);


        buildLines();

    }

    private void buildLines() {


        final double newR = r * HEX_DIST_EQ1;
        final double extConfR = r - newR;


        lines = newArrayList();
        final double height = extConfR * HEX_DIST_HEIGHT;

        int index = 0;
        for (Point2D innerEdge : innerEdges) {

            lines.add(
                    asList(
                            newEdgeAt(innerEdge,height,HEX_RADIANS.get(toHexIndex(1+index))),
                            innerEdge,
                            newEdgeAt(innerEdge,height,HEX_RADIANS.get(index))
                    )
            );

            index++;
        }





        lines2 = newArrayList();

        final double newRHalf = newR * 0.5;
        final double newRHeight = newR * HEX_DIST_HEIGHT;
        double extConfRHeight = extConfR * HEX_DIST_HEIGHT;
        double extConfRDiag = extConfR * HEX_DIST_DIAGONAL;


        for (int i=0;i<HEX_N;i++) {

            Point2D centre1 = newEdgeAt(centre, newR, HEX_RADIANS_ROT.get(toHexIndex(5 + i)));
            Point2D centre2 = newEdgeAt(centre, newRHeight, HEX_RADIANS.get(i));
            Point2D centre3 = newEdgeAt(centre, newR, HEX_RADIANS_ROT.get(i));

            lines2.add(asList(
                    newEdgeAt(centre1, extConfRHeight, HEX_RADIANS.get(i)),
                    newEdgeAt(centre2, extConfRDiag, HEX_RADIANS_ROT.get(toHexIndex(5 + i))),
                    newEdgeAt(centre3, newRHalf, HEX_RADIANS_ROT.get(toHexIndex(4 + i))),
                    newEdgeAt(centre2, extConfRDiag, HEX_RADIANS_ROT.get(i)),
                    newEdgeAt(centre3, extConfRHeight, HEX_RADIANS.get(i)),
                    newEdgeAt(centre3, extConfRHeight, HEX_RADIANS.get(toHexIndex(1+i)))

//                    newHexEdge(innerEdge, extConfRHeight, (1 + index))


            ));
//            index++;
        }



    }

    public List<List<Point2D>> getLines() {
        return lines;
    }

    public List<List<Point2D>> getLines2() {
        return lines2;
    }

    @Override
    public List<XMLBuilder> drawMe() {

        List<XMLBuilder> out = newArrayList();

        out.add(drawPolygon(innerEdges, styleWhite));
        out.add(drawPolygon(newHexStarTileRotated(centre, r * HEX_DIST_EQ1, HEX_DIST_DIAGONAL), styleWhiteBold));

        out.addAll(drawPolylines(lines, styleWhite));
        out.addAll(drawPolylines(lines2, styleWhiteBold));

        return out;
    }





}
