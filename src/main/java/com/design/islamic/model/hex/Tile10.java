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

public class Tile10 implements Tile {

    private final Point2D centre;
    private final double r;

    private final List<Point2D> innerEdges;
    private List<List<Point2D>> lines;


    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    public Tile10(Point2D centre, double r) {

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
                            newEdgeAt(innerEdge, height, HEX_RADIANS[toHexIndex(1 + index)]),
                            innerEdge,
                            newEdgeAt(innerEdge, height, HEX_RADIANS[index])
                    )
            );

            index++;
        }
    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromPolygonsAndLines(
                asList(innerEdges),
                lines
        );
    }
}
