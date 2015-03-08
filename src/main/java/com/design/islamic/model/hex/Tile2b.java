package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;
import com.design.islamic.model.tiles.Grid;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.WHITE;
import static com.design.common.view.SvgFactory.newStyle;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile2b implements Tile {

    private final List<Point2D> innerEdges;

//    private final List<List<Point2D>> outerRectangles;
//    private final List<List<Point2D>> outerLines;


    private final Point2D centre;
    private final double r;

    private List<List<Point2D>> lines;
    private List<List<Point2D>> lines2;

    public Tile2b(Point2D centre, double r) {

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

        lines2 = newArrayList();

        final double newRHalf = newR * 0.5;
        final double newRHeight = newR * HEX_DIST_HEIGHT;
        double extConfRHeight = extConfR * HEX_DIST_HEIGHT;
        double extConfRDiag = extConfR * HEX_DIST_DIAGONAL;

        for (int i = 0; i < HEX_N; i++) {

            Point2D centre1 = newEdgeAt(centre, newR, HEX_RADIANS_ROT[toHexIndex(5 + i)]);
            Point2D centre2 = newEdgeAt(centre, newRHeight, HEX_RADIANS[i]);
            Point2D centre3 = newEdgeAt(centre, newR, HEX_RADIANS_ROT[i]);

            lines2.add(asList(
                    newEdgeAt(centre1, extConfRHeight, HEX_RADIANS[i]),
                    newEdgeAt(centre2, extConfRDiag, HEX_RADIANS_ROT[toHexIndex(5 + i)]),
                    newEdgeAt(centre3, newRHalf, HEX_RADIANS_ROT[toHexIndex(4 + i)]),
                    newEdgeAt(centre2, extConfRDiag, HEX_RADIANS_ROT[i]),
                    newEdgeAt(centre3, extConfRHeight, HEX_RADIANS[i]),
                    newEdgeAt(centre3, extConfRHeight, HEX_RADIANS[toHexIndex(1 + i)])

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
    public Payload getPayload() {

        return Payloads.newPayloadFromPolygonsAndLines(
                asList(newHexStarTileRotated(centre, r * HEX_DIST_EQ1, HEX_DIST_DIAGONAL)),
                asList(innerEdges),
                lines2,
                lines,
                Grid.Configs.HEX_VER.getConfiguration()
        );

    }
}
