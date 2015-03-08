package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Tile;
import com.design.islamic.model.tiles.Grid;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.design.common.PolygonTools.*;
import static com.design.islamic.model.Payloads.newPayloadFromPolygonsAndLines;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.toList;

public class Tile12 implements Tile {

    public static final double RATIO_W = (6.0 * HEX_DIST_HEIGHT) / 5.0;

    private final Point2D centre;
    private final double r;

    private final double newR;

    private List<List<Point2D>> polygons;
    private List<List<Point2D>> lines;

    public Tile12(final Point2D centre, final double r) {
        this.centre = centre;
        this.r = r;

//        newR = ((r * RECT_DIST_HEIGHT) / 6.0)/HEX_DIST_HEIGHT ;
        newR = ((r * RECT_DIST_HEIGHT) / 5.0);

        int ur = 5;
        int um = 4;
        int ul = 3;
        int dl = 2;
        int dm = 1;
        int dr = 0;

        Point2D[] u = new Point2D[]{
                e(centre, 1, um),
                e(centre, 2, um),
                e(centre, 3, um),
                e(centre, 4, um),
                e(centre, 5, um)
        };

        Point2D[] d = new Point2D[]{
                e(centre, 1, dm),
                e(centre, 2, dm),
                e(centre, 3, dm),
                e(centre, 4, dm),
                e(centre, 5, dm)
        };

        polygons = asList(
//                newHexStarTileRotated(centre, 2 * newR, HEX_DIST_DIAGONAL)

        );

        lines = new ArrayList<>();

        List<int[]> l1 = asList(
                e(3, 4, ur),
                e(3, 0, ur),
                e(3, 1, dr),
                e(1, 1, dr),
                e(1, 2, dr),
                e(5, 6, dr),
                e(0, 5, ur),
                e(2, 5, ur),
                e(3, 4, ur)
        );

        List<int[]> l1_up_left = toSymmetricRot(l1, hexSymmetryToLeftMapRot, 1);
        List<int[]> l1_down_right = toSymmetricRot(l1, hexSymmetryToDownMapRot, -1);
        List<int[]> l1_down_left = toSymmetricRot(l1_up_left, hexSymmetryToDownMapRot, -1);


        List<int[]> l2 = asList(
                e(1, 0, ur),
                e(1, 6, ur)
        );


        List<int[]> l2_up_left = toSymmetricRot(l2, hexSymmetryToLeftMapRot, 1);
        List<int[]> l2_down_right = toSymmetricRot(l2, hexSymmetryToDownMapRot, -1);
        List<int[]> l2_down_left = toSymmetricRot(l2_up_left, hexSymmetryToDownMapRot, -1);



        List<int[]> l3 = asList(
                e(4, 2, ur),
                e(2, 2, ur),
                e(0, 4, ur),
                e(0, 4, dr),
                e(-2, 2, dr),
                e(-4, 2, dr)
                );

        List<int[]> l3_up_left = toSymmetricRot(l3, hexSymmetryToLeftMapRot, 1);

        lines.add(toHexEdgesRot(l1, u, d, newR, centre));
        lines.add(toHexEdgesRot(l1_up_left, u, d, newR, centre));
        lines.add(toHexEdgesRot(l1_down_right, u, d, newR, centre));
        lines.add(toHexEdgesRot(l1_down_left, u, d, newR, centre));

        lines.add(toHexEdgesRot(l2, u, d, newR, centre));
        lines.add(toHexEdgesRot(l2_up_left, u, d, newR, centre));
        lines.add(toHexEdgesRot(l2_down_right, u, d, newR, centre));
        lines.add(toHexEdgesRot(l2_down_left, u, d, newR, centre));

        lines.add(toHexEdgesRot(l3, u, d, newR, centre));
        lines.add(toHexEdgesRot(l3_up_left, u, d, newR, centre));



    }

    private static List<Point2D> toHexEdgesRot(List<int[]> els, final Point2D[] u, final Point2D[] d, final double newR, final Point2D centre) {
        return els.stream().map((el) -> toHexEdgeRot(el, u, d, newR, centre)).collect(toList());
    }

    private static Point2D toHexEdgeRot(int[] el, Point2D[] u, Point2D[] d, double newR, Point2D centre) {
        final Point2D newCentre =
                el[0] == 0
                ?
                centre :
                (el[0] > 0 ? u[el[0] - 1] : d[-(el[0] + 1)]);

        if (el[1] > 0) {
            return newEdgeAt(newCentre, el[1] * newR, HEX_RADIANS_ROT[el[2]]);
        } else {
            return newCentre;
        }

    }

    private static final Map<Integer, Integer> hexSymmetryToLeftMapRot = ImmutableMap.of(
            4, 4
            , 5, 3
            , 0, 2
            , 1, 1
    );

    private static final Map<Integer, Integer> hexSymmetryToDownMapRot = Maps.newHashMap();
    static {
        hexSymmetryToDownMapRot.put(0,5);
        hexSymmetryToDownMapRot.put(5,0);
        hexSymmetryToDownMapRot.put(4,1);
        hexSymmetryToDownMapRot.put(3,2);
        hexSymmetryToDownMapRot.put(2,3);
        hexSymmetryToDownMapRot.put(1,1);

    }


    private static List<int[]> toSymmetricRot(List<int[]> els, final Map<Integer, Integer> symmetryMap, final int sign) {
        return els.stream().map((el) -> toSymmetricRot(el, symmetryMap, sign)).collect(toList());
    }

    private static int[] toSymmetricRot(int[] el, Map<Integer, Integer> symmetryMap, int sign) {
        return new int[]{
                sign * el[0],
                el[1],
                symmetryMap.get(el[2])
        };
    }

    private int[] e(int centreIndex, int times, int phiIndex) {
        return new int[]{
                centreIndex, times, phiIndex
        };
    }

    private Point2D e(Point2D newCentre, int times, int index) {
        return newEdgeAt(newCentre, times * newR, HEX_RADIANS_ROT[index]);
    }

    @Override
    public Payload getPayload() {
        return newPayloadFromPolygonsAndLines(
                polygons, lines, Grid.Configs.HEX_VER.getConfiguration()
        );
    }
}
