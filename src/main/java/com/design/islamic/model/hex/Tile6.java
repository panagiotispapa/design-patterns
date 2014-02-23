package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.Arrays;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.WHITE;
import static com.design.common.view.SvgFactory.newStyle;
import static com.google.common.collect.Lists.newArrayList;

public class Tile6 implements Tile {
    private final List<Point2D> mainHex;
    private final List<List<Point2D>> outerEdges;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);


    public Tile6(Point2D centre, double r) {

        mainHex = newHexagonRot(centre, r);

        double newR = r * HEX_DIST_DIAGONAL_ROTATED;
        outerEdges = buildOuterLines(
                newHexagon(centre, newR * HEX_DIST_NEW_CENTRE),
                newR

        );

    }

    public static List<List<Point2D>> buildOuterLines(List<Point2D> outerEdges, double r) {
        List<List<Point2D>> out = newArrayList();

        int index = 0;
        double newRDiag = r * HEX_DIST_DIAGONAL_ROTATED;
        for (Point2D outerEdge : outerEdges) {

            out.add(
                    Arrays.asList(
                            newEdgeAt(outerEdge, r, HEX_RADIANS_ROT.get(toHexIndex(1 + index))),
                            newEdgeAt(outerEdge, newRDiag, HEX_RADIANS_ROT.get(toHexIndex(2 + index))),
                            newEdgeAt(outerEdge, r, HEX_RADIANS_ROT.get(toHexIndex(2 + index))),
                            newEdgeAt(outerEdge, r, HEX_RADIANS_ROT.get(toHexIndex(3 + index))),
                            newEdgeAt(outerEdge, newRDiag, HEX_RADIANS_ROT.get(toHexIndex(3 + index))),
                            newEdgeAt(outerEdge, r, HEX_RADIANS_ROT.get(toHexIndex(4 + index)))
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
