package com.design.islamic.model.hex;

import com.design.islamic.model.Tile;
import com.jamesmurty.utils.XMLBuilder;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.WHITE;
import static com.design.common.view.SvgFactory.drawPolylines;
import static com.design.common.view.SvgFactory.newStyle;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile7 implements Tile{
    private final List<Point2D> mainHex;
    private final List<List<Point2D>> lines;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    public Tile7(Point2D centre, double r) {

        mainHex = newHexagonRot(centre, r);



        lines = buildOuterLines(centre,r);


    }
    public static List<List<Point2D>> buildOuterLines(Point2D centre, double r) {

        List<Point2D> outerEdges = newHexagonRot(centre, r * HEX_DIST_HEIGHT);
        double rSmall = r * (1 - HEX_DIST_HEIGHT);


        double newR = r * HEX_DIST_DIAGONAL_ROTATED;



        List<List<Point2D>> out = newArrayList();
        int index = 0;

        for (Point2D outerEdge : outerEdges) {

            List<Point2D> heights = newHexagon(outerEdge, rSmall*HEX_DIST_HEIGHT);
            List<Point2D> bigHex = newHexagon(outerEdge, newR);

            out.add(
                    asList(
                            heights.get(toHexIndex(0 + index)),
                            outerEdge,
                            heights.get(toHexIndex(1 + index))
                    )
            );

            out.add(
                    asList(
                            bigHex.get(toHexIndex(4 + index)),
                            outerEdge,
                            bigHex.get(toHexIndex(3 + index))
                    )
            );

            index++;
        }

        List<Point2D> outerEdgesRot = newHexagon(centre, r * HEX_DIST_HEIGHT);

        index = 0;
        for (Point2D outerEdge : outerEdgesRot) {
            List<Point2D> bigHex = newHexagonRot(outerEdge, newR);

            out.add(asList(
                    bigHex.get(toHexIndex(3 + index)),
                    outerEdge,
                    bigHex.get(toHexIndex(2 + index))
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
