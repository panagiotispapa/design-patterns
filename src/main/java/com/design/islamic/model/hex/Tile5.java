package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.WHITE;
import static com.design.common.view.SvgFactory.newStyle;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile5 implements Tile {

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    private final List<Point2D> mainHex;
    private final List<List<Point2D>> outerEdges;

    public Tile5(Point2D centre, double r) {
        mainHex = cloneAndTranslateScalePoints(centre, r, hexPointsAlt);

        double newR = r*HEX_DIST_DIAGONAL*HEX_DIST_DIAGONAL;

        outerEdges = buildOuterLines(
                cloneAndTranslateScalePoints(centre, newR*HEX_DIST_NEW_CENTRE, hexPoints),
                newR

        );
    }

    public static List<List<Point2D>> buildOuterLines(List<Point2D> outerEdges, double r) {
        List<List<Point2D>> out = newArrayList();

        int index = 0;
        for (Point2D outerEdge : outerEdges) {

            out.add(
                    asList(
                            newEdgeAt(outerEdge, r, HEX_RADIANS_ROT[toHexIndex(2 + index)]),
                            newEdgeAt(outerEdge, r, HEX_RADIANS_ROT[toHexIndex(5 + index)])
                    )
            );


            out.add(
                    asList(
                            newEdgeAt(outerEdge, r, HEX_RADIANS_ROT[toHexIndex(3 + index)]),
                            newEdgeAt(outerEdge, r, HEX_RADIANS_ROT[toHexIndex(index)])
                    )
            );

            index++;
        }

        return out;
    }


    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(outerEdges);

    }
}
