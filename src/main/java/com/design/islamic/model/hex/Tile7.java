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

public class Tile7 implements Tile {
    private final List<Point2D> mainHex;
    private List<List<Point2D>> lines;

    private final String styleWhiteBold = newStyle(WHITE, 2, 1);
    private final String styleWhite = newStyle(WHITE, 1, 1);

    private List<Point2D> outerLayerRot;
    private List<Point2D> outerLayer;

    private final Point2D centre;
    private final double r;

    public Tile7(Point2D centre, double r) {

        this.centre = centre;
        this.r = r;

        mainHex = newHexagonRot(centre, r);

        buildPoints();

        buildLines();

    }

    private void buildPoints() {
        outerLayerRot = newHexagonRot(centre, r * HEX_DIST_HEIGHT);
        outerLayer = newHexagon(centre, r * HEX_DIST_HEIGHT);

    }

    private void buildLines() {
        lines = newArrayList();

        final double rSmall = r * (1 - HEX_DIST_HEIGHT);
        final double distH = rSmall * HEX_DIST_HEIGHT;
        double newR = r * HEX_DIST_DIAGONAL_ROTATED;

        for (int i = 0; i < HEX_N; i++) {
            Point2D edgeRot = outerLayerRot.get(i);
            lines.add(asList(
                    newEdgeAt(edgeRot, distH, HEX_RADIANS.get(i)),
                    edgeRot,
                    newEdgeAt(edgeRot, distH, HEX_RADIANS.get(toHexIndex(i + 1)))

            ));

            lines.add(asList(
                    newEdgeAt(edgeRot, newR, HEX_RADIANS.get(toHexIndex(i + 4))),
                    edgeRot,
                    newEdgeAt(edgeRot, newR, HEX_RADIANS.get(toHexIndex(i + 3)))
            ));

            Point2D edge = outerLayer.get(i);
            lines.add(asList(
                    newEdgeAt(edge, newR, HEX_RADIANS_ROT.get(toHexIndex(i + 3))),
                    edge,
                    newEdgeAt(edge, newR, HEX_RADIANS_ROT.get(toHexIndex(i + 2)))
            ));

        }

    }

    public List<List<Point2D>> getLines() {
        return lines;
    }


    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromLines(lines);
    }
}
