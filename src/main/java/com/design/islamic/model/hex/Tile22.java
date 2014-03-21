package com.design.islamic.model.hex;

import com.design.islamic.model.Payload;
import com.design.islamic.model.Payloads;
import com.design.islamic.model.Tile;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.google.common.collect.Lists.newArrayList;
import static java.util.Arrays.asList;

public class Tile22 implements Tile {

    private List<List<Point2D>> lines;
    private List<List<Point2D>> polygons;

    private final double newR;
    private final Point2D centre;

    private final int ur = 5;
    private final int um = 4;
    private final int ul = 3;
    private final int dl = 2;
    private final int dm = 1;
    private final int dr = 0;

//    private Point2D[] e;

    private final double DIAG = HEX_DIST_DIAGONAL_ROTATED;

    public Tile22(final Point2D centre, final double r) {

        this.centre = centre;

        lines = newArrayList();
        polygons = newArrayList();

        double newH = r / 16.0;
        newR = newH / HEX_DIST_HEIGHT;

//        e = new Point2D[]{
//
//        };

        for (int i = 0; i < HEX_N; i++) {

            l1(i);
            l2(i);
            l3(i);
            l4(i);
            l5(i);
            l6(i);
            l7(i);
            l8(i);

        }

    }

    private void l1(int i) {

        Point2D c = p(p(2, ur + i), 1, um + i);

        lines.add(asList(
                p(c, 2, dm + i),
                c,
                p(c, DIAG, ul + i)

        ));

    }

    private void l2(int i) {

        Point2D c = p(p(3, ur + i), 1, um + i);

        lines.add(asList(
                p(c, 2 + DIAG, dm + i),
                c,
                p(c, 1, ul + i)

        ));

    }

    private void l3(int i) {

        Point2D c0 = p(4, ur + i);
        Point2D c = p(c0, 1, um + i);
        Point2D c2 = p(c0, 3, um + i);

        lines.add(asList(
                p(c, 3, dm + i),
                c,
                p(c, 1 + DIAG, ul + i)

        ));

        lines.add(asList(
                p(c2, 1, um + i),
                c2,
                p(c2, DIAG, ul + i)

        ));

    }

    private void l4(int i) {

        Point2D c0 = p(5, ur + i);
        Point2D c = p(c0, 1, um + i);
        Point2D c2 = p(c0, 3, um + i);

        lines.add(asList(
                p(c, 3 + DIAG, dm + i),
                c,
                p(c, 2, ul + i)

        ));

        lines.add(asList(
                c,
                p(c, 2, um + i)

        ));

        lines.add(asList(
                c2,
                p(c2, 1, ul + i)

        ));

    }

    private void l5(int i) {

        Point2D c7 = p(6, ur + i);

        lines.add(asList(
                p(c7, 3, dm + i),
                p(c7, 3, um + i),
                p(p(c7, 3, um + i), 1 + DIAG, ul + i)
//                    p(p(c4, 1, um + i), 2, ul + i)
        ));

    }

    private void l6(int i) {

        Point2D c = p(7, ur + i);
        Point2D c2 = p(p(8, ur + i), 1, um + i);
        Point2D c3 = p(p(8, ur + i), 1, dr + i);

//        lines.addAll(newHexDiagRot(c2, newR));

//        polygons.add(newHexagonRot(c,newR));
//        polygons.add(newHexagonRot(c2,newR));
//        polygons.add(newHexagonRot(c3,newR));

        lines.add(asList(
                p(c, 1, dr + i),
                p(c, 1, dm + i),
                p(c, 1, dl + i)
        ));

        lines.add(asList(
                p(c, 1, um + i),
                p(c, DIAG, (um + i)),
                p(c, DIAG, (ur + i)),
                p(c, 1, ur + i)
        ));

        lines.add(asList(
                p(c2, 1, dl + i),
                p(c2, 1, ul + i),
                p(c2, 1, um + i)
        ));

        lines.add(asList(
                p(c2, 1, dm + i),
                p(c2, DIAG, (dm + i)),
                p(c2, DIAG, (dr + i)),
                p(c2, 1, dr + i)
        ));

        lines.add(asList(
                p(c3, 1, dl + i),
                p(c3, DIAG, (dl + i)),
                p(c3, DIAG, (ul + i)),
                p(c3, 1, ul + i)
        ));

        lines.add(asList(
                p(c3, 1, um + i),
                p(c3, 1, ur + i),
                p(c3, 1, dr + i)
        ));

    }

    private void l7(int i) {
        Point2D c = p(11, ur + i);
        Point2D c2 = p(c, 1, um + i);
        Point2D c3 = p(c, 1, dm + i);
        Point2D c4 = p(c, 2, ul + i);
        Point2D c5 = p(c4, 1, um + i);
        Point2D c6 = p(c4, 2, um + i);
        Point2D c7 = p(c4, 3, um + i);

//        polygons.add(newHexagonRot(c,newR));
//        polygons.add(newHexagonRot(c2,newR));
//        polygons.add(newHexagonRot(c3,newR));
//        polygons.add(newHexagonRot(c4, newR));
//        polygons.add(newHexagonRot(c5, newR));
//        polygons.add(newHexagonRot(c6, newR));
//        polygons.add(newHexagonRot(c7, newR));

        lines.add(asList(
                p(c, 1, ur + i),
                c,
                p(c, 5 + DIAG, ul + i)
        ));

        lines.add(asList(
                p(c2, DIAG, ur + i),
                c2,
                p(c2, 1, dr + i)
        ));

        lines.add(asList(
                p(c3, 1 + DIAG, ur + i),
                c3,
                p(c3, 6, ul + i)
        ));

        lines.add(asList(
                c4,
                p(c4, 2, ur + i)
        ));

        lines.add(asList(
                p(c5, 1 + DIAG, ur + i),
                c5,
                p(c5, 3, ul + i)
        ));

        lines.add(asList(
                p(c6, 1, ur + i),
                c6,
                p(c6, 2 + DIAG, ul + i)
        ));

        lines.add(asList(
                p(c7, DIAG, ur + i),
                c7,
                p(c7, 2, ul + i)
        ));

    }

    private void l8(int i) {
//        Point2D c = p(8, ur + i);
        Point2D c = p(p(7, ur + i), 2, dm + i);
        Point2D c2 = p(p(10, ur + i), 3, dm + i);
        Point2D c3 = p(c2, 1, dr + i);
        Point2D c4 = p(c2, 2, dr + i);
        Point2D c5 = p(c2, 3, dr + i);

        Point2D c_2 = p(p(8, ur + i), 3, dm + i);
        Point2D c_3 = p(p(9, ur + i), 4, dm + i);

//        polygons.add(newHexagonRot(c, newR));
//        polygons.add(newHexagonRot(c_3, newR));
//        polygons.add(newHexagonRot(c3, newR));

        lines.add(asList(
                p(c, 1 + DIAG, dm + i),
                c,
                p(c, 6, ur + i)
        ));

        lines.add(asList(
                p(c2, 3 + DIAG, ur + i),
                c2,
                p(c2, 2, dm + i)
        ));

        lines.add(asList(
                p(c_2, 2, ur + i),
                c_2,
                p(c_2, 1, dm + i)
        ));

        lines.add(asList(
                p(c_3, 1, dl + i),
                c_3,
                p(c_3, DIAG, dm + i)
        ));

        lines.add(asList(
                p(c3, 1 + DIAG, dm + i),
                c3,
                p(c3, 3, ur + i)
        ));

        lines.add(asList(
                p(c4, 1, dm + i),
                c4,
                p(c4, 2 + DIAG, ur + i)
        ));

        lines.add(asList(
                p(c5, DIAG, dm + i),
                c5,
                p(c5, 2, ur + i)
        ));

    }

    private Point2D p(Point2D centre, double times, int phiIndex) {
        if (times == 0) {
            return centre;
        } else {
            return newEdgeAt(centre, times * newR, toPhi(phiIndex));
        }

    }

    private double toPhi(int phiIndex) {
        return HEX_RADIANS_ROT[toHexIndex(phiIndex)];
    }

    private Point2D p(int times, int phiIndex) {
        return newEdgeAt(centre, times * newR, HEX_RADIANS_ROT[toHexIndex(phiIndex)]);

    }

    @Override
    public Payload getPayload() {
        return Payloads.newPayloadFromPolygonsAndLines(polygons,
                lines
        );
    }

}
