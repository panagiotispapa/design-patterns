package com.design.islamic.model;

import com.design.islamic.CentreConfiguration;

import static com.design.islamic.CentreConfiguration.Conf.HEX_SECOND;
import static com.design.islamic.CentreConfiguration.Conf.HEX_THIRD;
import static com.design.islamic.CentreConfiguration.Conf.RECT;

public enum RectPattern {

    ONE("Pattern 1", RECT),
    TWO("Pattern 2", RECT),
    ;


    private final String description;
    private final CentreConfiguration.Conf config;

    private RectPattern(String description, CentreConfiguration.Conf config) {
        this.description = description;
        this.config = config;
    }

    public String getDescription() {
        return description;
    }

    public CentreConfiguration.Conf getConfig() {
        return config;
    }

    public static RectPattern fromDescription(String description) {

        if (description.equalsIgnoreCase(ONE.getDescription())) {
            return ONE;
        } else if (description.equalsIgnoreCase(TWO.getDescription())) {
            return TWO;
        } else {
            throw new IllegalArgumentException();
        }

    }

}
