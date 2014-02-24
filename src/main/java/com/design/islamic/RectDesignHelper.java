package com.design.islamic;

import com.design.islamic.model.rect.Tile2;

import java.awt.geom.Point2D;
import java.util.List;

import static com.design.common.PolygonTools.*;
import static com.design.common.view.SvgFactory.*;
import static java.lang.Math.cos;

public class RectDesignHelper {

    public static String newRectDesign1(final Point2D centre, final double r) {

        String blue = newStyle("blue", 2, 1);
        final String gray = newStyle("gray", 1, 1);
        final String green = newStyle("green", 2, 1);
        final String red = newStyle("red", 2, 1);

        Tile2 tile = new Tile2(centre, r);

        final double newHeight = r / 2.0;
        final double newR = newHeight / cos(OCT_PHI / 2.0);

        StringBuilder builder = new StringBuilder();

        List<Point2D> mainRectRot = newRectRot(centre, r);

        builder.append(drawPolygon(mainRectRot, gray));

        builder.append(drawPolygon(newOctRot(centre, newR), gray));
        for (Point2D edge : mainRectRot) {
            builder.append(drawPolygon(newOctRot(edge, newR), gray));
        }

        builder.append(drawPolylines(buildLines(centre, newRectRot(centre, r * RECT_DIST_HEIGHT)), gray));
        builder.append(drawPolylines(buildLines(centre, mainRectRot), gray));

        builder.append(highlightPoints(tile.getCentral()));
        builder.append(highlightPoints(tile.getPointsA()));
        builder.append(highlightPoints(tile.getPointsB()));

//        builder.append(drawPolygon(tile.getLines(), red));
        builder.append(drawPolylines(tile.getLines(), red));

        return builder.toString();

    }

}
