package com.design.islamic.model;

import org.apache.commons.lang.builder.HashCodeBuilder;

import java.awt.geom.Point2D;

public class Centre extends Point2D.Double {

    public Centre() {

    }

    public Centre(double x, double y) {
        super(x, y);
    }

    @Override
    public int hashCode() {


        int result = (int)(x + y);
        return result;

//        return HashCodeBuilder.reflectionHashCode(this);

    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else {
            //            System.out.println("distance " + distance);


            return ((Point2D) other).distance(this) < 1.0;
        }

    }
}
