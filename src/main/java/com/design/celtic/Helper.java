package com.design.celtic;

import com.design.common.model.Circle;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public class Helper {

    public static Circle getCircleFromRow(Circle bigR, double part, int index, double offset) {

        final double newR = bigR.getR() * part;
        final double startPoint = bigR.getCentre().getX() - bigR.getR() + newR + offset;

        return Circle.circle(point(startPoint + (index - 1) * 2 * newR, bigR.getCentre().getY()), newR);

    }

    //
//    public static Circle getCircleFromRow(Circle bigR, int total, int times, int index, int offsetN) {
//
//        final double step = 1.0 / (double) total;
//
//        final double offset = offsetN * step * 2.0 * bigR.getR();
//
//        final double newR = bigR.getR() * times * step;
//        final double startPoint = bigR.getCentre().getX() - bigR.getR() + newR + offset;
//
//        return newCircle(newCentre(startPoint + (index - 1) * 2 * newR, bigR.getCentre().getY()), newR);
//    }
    public static Circle getCircleFromRow(Circle bigR, double part, int index) {

        final double newR = bigR.getR() * part;
        final double startPoint = bigR.getCentre().getX() - bigR.getR() + newR;

        return Circle.circle(point(startPoint + (index - 1) * 2 * newR, bigR.getCentre().getY()), newR);

    }

    public static Circle getCircleFromRowRightToLeft(Circle bigR, double part, int index) {

        final double newR = bigR.getR() * part;
        final double startPoint = bigR.getCentre().getX() + bigR.getR() - newR;

        return Circle.circle(point(startPoint - (index - 1) * 2 * newR, bigR.getCentre().getY()), newR);

    }

    public static List<Circle> putInARow(Circle bigR, int n, int offsetN) {
        final double step = 1.0 / (double) n;
        final double offset = offsetN * step * 2.0 * bigR.getR();

        List<Circle> output = new ArrayList<>();

        output.addAll(putInARowLeftToRight(bigR, 1.0 * step, offset));
        for (int i = 2; i < n; i += 2) {
            output.addAll(putInARowLeftToRight(bigR, i * step, offset));
        }

        return output;

    }

    private static List<Circle> putInARowLeftToRight(Circle bigR, double part, double offset) {

        List<Circle> output = new ArrayList<>();

        final double newR = bigR.getR() * part;
        final double startPoint = bigR.getCentre().getX() - bigR.getR() + offset + newR;
        final double max = bigR.getCentre().getX() + bigR.getR() + 2;
        for (double posX = startPoint; posX + newR < max; posX += 2 * newR) {
            output.add(Circle.circle(point(posX, bigR.getCentre().getY()), newR));
        }

        return output;
    }

    public static List<Circle> putInARowRightToLeft(Circle bigR, double part) {

        List<Circle> output = new ArrayList<>();

        final double newR = bigR.getR() * part;
        final double startPoint = bigR.getCentre().getX() + bigR.getR() - newR;
        final double min = bigR.getCentre().getX() - bigR.getR() - 2;
        for (double posX = startPoint; posX - newR > min; posX -= 2 * newR) {
            output.add(Circle.circle(point(posX, bigR.getCentre().getY()), newR));
        }

        return output;
    }

    public static List<Circle> splitInHalf(Iterable<Circle> bigCircles) {
        List<Circle> output = new ArrayList<>();

        for (Circle bigCircle : bigCircles) {
            output.addAll(splitInHalf(bigCircle));
        }

        return output;

    }

    private static List<Circle> splitInHalf(Circle big) {

        final double newR = big.getR() / 2.0;

        return asList(
                Circle.circle(point(big.getCentre().getX() - newR, big.getCentre().getY()), newR),
                Circle.circle(point(big.getCentre().getX() + newR, big.getCentre().getY()), newR)
        );

    }

    private static Point2D point(double x, double y) {
        return new Point2D.Double(x, y);
    }

}
