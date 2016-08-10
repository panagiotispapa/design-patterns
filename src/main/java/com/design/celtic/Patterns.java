package com.design.celtic;

import com.design.common.model.Arc;
import com.design.common.model.Circle;
import com.design.common.model.Style;
import com.google.common.collect.Sets;

import java.awt.*;
import java.util.List;
import java.util.Set;

import static com.design.celtic.Helper.getCircleFromRow;
import static com.design.common.CanvasPoint.point;
import static com.design.common.view.SvgFactory.*;
import static com.google.common.collect.Iterables.concat;
import static java.util.Arrays.asList;

public class Patterns {

    public static String buildPattern1(Dimension dim, double r) {

        final double N1 = 18.0;
        final double step = 1.0 / N1;

        StringBuilder shapes = new StringBuilder();


        final Style styleBackStrong = new Style.Builder(Color.BLACK, 2).withStrokeOpacity(1.0).build();
        final Style styleBackLight = new Style.Builder(Color.BLACK, 2).withStrokeOpacity(0.4).build();
        final Style styleFrontGray = new Style.Builder(Color.BLACK, 2).withFill(Color.GRAY).withFillOpacity(0.8).withStrokeOpacity(1.0).build();
        final Style styleFrontWhite = new Style.Builder(Color.BLACK, 2).withFill(Color.WHITE).withFillOpacity(1.0).withStrokeOpacity(1.0).build();

        Circle mainCircle = Circle.circle(point(dim.getWidth() / 2, dim.getHeight() / 2), r);

        Set<Circle> layer1 = Sets.newHashSet(concat(
                Helper.putInARow(mainCircle, 18, 0),
                Helper.putInARow(mainCircle, 18, 2),
                Helper.putInARow(mainCircle, 18, 4),
                Helper.putInARow(mainCircle, 18, 6),
                Helper.putInARow(mainCircle, 18, 8),
                Helper.putInARow(mainCircle, 18, 10),
                Helper.putInARow(mainCircle, 18, 12),
                Helper.putInARow(mainCircle, 18, 14),
                Helper.putInARow(mainCircle, 18, 16)

        ));

        List<Circle> layer2 = asList(
                getCircleFromRow(mainCircle, 2.0 * step, 5),
                getCircleFromRow(mainCircle, 1.0 * step, 4),
                getCircleFromRow(mainCircle, 1.0 * step, 15)
        );

        List<Arc> layer3 = asList(
                Arc.of(Helper.getCircleFromRow(mainCircle, 4.0 * step, 1, 4 * step * r), false),
                Arc.of(Helper.getCircleFromRow(mainCircle, 4.0 * step, 1, 12 * step * r), true),
                Arc.of(Helper.getCircleFromRow(mainCircle, 4.0 * step, 1, 16 * step * r), false),
                Arc.of(Helper.getCircleFromRow(mainCircle, 4.0 * step, 1, 24 * step * r), true)
        );

        List<Arc> layer4 = asList(
                Arc.of(getCircleFromRow(mainCircle, 4.0 * step, 1), true),
                Arc.of(getCircleFromRow(mainCircle, 12.0 * step, 1), false),
                Arc.of(Helper.getCircleFromRow(mainCircle, 4.0 * step, 4, 4 * step * r), false),
                Arc.of(Helper.getCircleFromRow(mainCircle, 12.0 * step, 1, 12 * step * r), true)
        );

        List<Arc> layer5 = asList(
                Arc.of(getCircleFromRow(mainCircle, 2.0 * step, 2), true),
                Arc.of(Helper.getCircleFromRow(mainCircle, 10.0 * step, 1, 4 * step * r), false),
                Arc.of(getCircleFromRow(mainCircle, 2.0 * step, 8), false),
                Arc.of(Helper.getCircleFromRow(mainCircle, 10.0 * step, 1, 12 * step * r), true)
        );

        shapes.append(drawArcList(layer4, styleFrontGray));
        shapes.append(drawArcList(layer5, styleFrontWhite));
        shapes.append(drawCircles(layer1, styleBackLight));
        shapes.append(drawCircles(layer2, styleFrontGray));
        shapes.append(drawArcList(layer3, styleBackStrong));
        shapes.append(mainCircle.draw(styleBackStrong));

        return buildSvg(dim, shapes.toString());

    }

}
