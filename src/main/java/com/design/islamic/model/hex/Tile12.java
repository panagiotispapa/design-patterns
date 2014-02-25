package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;
import com.google.common.collect.Lists;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.islamic.model.Payloads.EMPTY;
import static com.design.islamic.model.Payloads.newPayloadFromPolygons;
import static com.design.islamic.model.Payloads.newPayloadFromPolygonsAndLines;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile12 implements Tile {


    private final Point2D centre;
    private final double r;

    private final double newR;


    private List<List<Point2D>> polygons;
    private List<List<Point2D>> lines;



    public Tile12(final Point2D centre, final double r) {
        this.centre = centre;
        this.r = r;

        newR = (r*RECT_DIST_HEIGHT)/5.0;



        Point2D centre1 = newEdgeAt(centre, 2*newR, HEX_RADIANS_ROT.get(4));
        Point2D centre2 = newEdgeAt(centre, 2*newR, HEX_RADIANS_ROT.get(1));
        Point2D centreD1 = newEdgeAt(centre1, 3*newR, HEX_RADIANS_ROT.get(5));
        Point2D centreD2 = newEdgeAt(centre1, 3*newR, HEX_RADIANS_ROT.get(3));
        Point2D centreD3 = newEdgeAt(centre2, 3*newR, HEX_RADIANS_ROT.get(2));
        Point2D centreD4 = newEdgeAt(centre2, 3*newR, HEX_RADIANS_ROT.get(0));

        Point2D centreH1 = newEdgeAt(centre, 2*newR*HEX_DIST_HEIGHT, HEX_RADIANS.get(0));
        Point2D centreH2 = newEdgeAt(centre, 2*newR*HEX_DIST_HEIGHT, HEX_RADIANS.get(3));


        polygons = asList(
//                newHexagonRot(centre, r),
                newHexStarTileRotated(centre, 2 * newR, HEX_DIST_DIAGONAL)
//                newHexagonRot(centre1, newR),
//                newHexagonRot(centre2, newR),
//                newHexagonRot(centreH1, newR),
//                newHexagonRot(centreH2, newR)

        );


        lines = newArrayList();


        lines.add(asList(
                newEdgeAt(centreD1, newR, HEX_RADIANS_ROT.get(5)),
                newEdgeAt(centreD1, newR, HEX_RADIANS_ROT.get(0)),
                newEdgeAt(centre1, newR, HEX_RADIANS_ROT.get(0)),
                newEdgeAt(centre1, newR, HEX_RADIANS_ROT.get(5)),
                newEdgeAt(centre1, newR, HEX_RADIANS_ROT.get(4)),
                newEdgeAt(centreD1, newR, HEX_RADIANS_ROT.get(3))
//                newEdgeAt(centreD1, newR, HEX_RADIANS_ROT.get(2)),
//                newEdgeAt(centreD1, newR, HEX_RADIANS_ROT.get(1))

                ));


        lines.add(asList(
                newEdgeAt(centreD2, newR, HEX_RADIANS_ROT.get(3)),
                newEdgeAt(centreD2, newR, HEX_RADIANS_ROT.get(2)),
                newEdgeAt(centre1, newR, HEX_RADIANS_ROT.get(2)),
                newEdgeAt(centre1, newR, HEX_RADIANS_ROT.get(3)),
                newEdgeAt(centre1, newR, HEX_RADIANS_ROT.get(4)),
                newEdgeAt(centreD2, newR, HEX_RADIANS_ROT.get(5))
//                newEdgeAt(centreD2, newR, HEX_RADIANS_ROT.get(0)),
//                newEdgeAt(centreD2, newR, HEX_RADIANS_ROT.get(1))

                ));


        lines.add(asList(
                newEdgeAt(centreD3, newR, HEX_RADIANS_ROT.get(2)),
                newEdgeAt(centreD3, newR, HEX_RADIANS_ROT.get(3)),
                newEdgeAt(centre2, newR, HEX_RADIANS_ROT.get(3)),
                newEdgeAt(centre2, newR, HEX_RADIANS_ROT.get(2)),
                newEdgeAt(centre2, newR, HEX_RADIANS_ROT.get(1)),
                newEdgeAt(centreD3, newR, HEX_RADIANS_ROT.get(0))
//                newEdgeAt(centreD3, newR, HEX_RADIANS_ROT.get(5)),
//                newEdgeAt(centreD3, newR, HEX_RADIANS_ROT.get(4))

                ));


        lines.add(asList(
                newEdgeAt(centreD4, newR, HEX_RADIANS_ROT.get(0)),
                newEdgeAt(centreD4, newR, HEX_RADIANS_ROT.get(5)),
                newEdgeAt(centre2, newR, HEX_RADIANS_ROT.get(5)),
                newEdgeAt(centre2, newR, HEX_RADIANS_ROT.get(0)),
                newEdgeAt(centre2, newR, HEX_RADIANS_ROT.get(1)),
                newEdgeAt(centreD4, newR, HEX_RADIANS_ROT.get(2))
//                newEdgeAt(centreD4, newR, HEX_RADIANS_ROT.get(3)),
//                newEdgeAt(centreD4, newR, HEX_RADIANS_ROT.get(4))

                ));


        lines.add(asList(
                newEdgeAt(centreH1, 4*newR, HEX_RADIANS_ROT.get(5)),
                centreH1,
                newEdgeAt(centreH1, 4*newR, HEX_RADIANS_ROT.get(0))
        ));


        lines.add(asList(
                newEdgeAt(centreH2, 4*newR, HEX_RADIANS_ROT.get(3)),
                centreH2,
                newEdgeAt(centreH2, 4*newR, HEX_RADIANS_ROT.get(2))

        ));


        lines.add(asList(
//                newEdge(,1,)
                newEdgeAt(centreD1, newR, HEX_RADIANS_ROT.get(4)),
                newEdgeAt(centreD1, newR, HEX_RADIANS_ROT.get(3)),
                newEdgeAt(centreD1, newR, HEX_RADIANS_ROT.get(2)),
                newEdgeAt(centreD1, newR, HEX_RADIANS_ROT.get(1)),
                newEdgeAt(centre, 4*newR, HEX_RADIANS_ROT.get(5)),
                newEdgeAt(centre, 4*newR, HEX_RADIANS_ROT.get(0)),
                newEdgeAt(centreD4, newR, HEX_RADIANS_ROT.get(4)),
                newEdgeAt(centreD4, newR, HEX_RADIANS_ROT.get(3)),
                newEdgeAt(centreD4, newR, HEX_RADIANS_ROT.get(2)),
                newEdgeAt(centreD4, newR, HEX_RADIANS_ROT.get(1))

        ));

        lines.add(asList(
                newEdgeAt(centreD2, newR, HEX_RADIANS_ROT.get(4)),
                newEdgeAt(centreD2, newR, HEX_RADIANS_ROT.get(5)),
                newEdgeAt(centreD2, newR, HEX_RADIANS_ROT.get(0)),
                newEdgeAt(centreD2, newR, HEX_RADIANS_ROT.get(1)),
                newEdgeAt(centre, 4*newR, HEX_RADIANS_ROT.get(3)),
                newEdgeAt(centre, 4*newR, HEX_RADIANS_ROT.get(2)),
                newEdgeAt(centreD3, newR, HEX_RADIANS_ROT.get(4)),
                newEdgeAt(centreD3, newR, HEX_RADIANS_ROT.get(5)),
                newEdge(centreD3, 1, 0),
                newEdge(centreD3, 1, 1)


        ));

    }



    private Point2D newEdge(Point2D newCentre, int times, int index) {
        return newEdgeAt(newCentre, times * newR, HEX_RADIANS_ROT.get(index));
    }

    @Override
    public Payload getPayload() {
        return newPayloadFromPolygonsAndLines(
                polygons, lines
        );
    }
}
