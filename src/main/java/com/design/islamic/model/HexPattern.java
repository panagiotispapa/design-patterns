package com.design.islamic.model;

import com.design.islamic.CentreConfiguration;
import com.google.common.collect.Maps;

import java.util.Map;

import static com.design.islamic.CentreConfiguration.Conf.HEX_SECOND;
import static com.design.islamic.CentreConfiguration.Conf.HEX_THIRD;

public enum HexPattern {

    ONE("Pattern1", HEX_SECOND),
//    TWO("Pattern2"),
    STAR1("Pattern Star 1", HEX_SECOND),
    STAR2("Pattern Star 2", HEX_SECOND),
    STAR3("Pattern Star 3", HEX_SECOND),
    THREE("Pattern 3", HEX_SECOND),
    FOUR("Pattern 4", HEX_SECOND),
    FIVE("Pattern 5", HEX_SECOND),
    SIX("Pattern 6", HEX_SECOND),
    SEVEN("Pattern 7", HEX_SECOND),
    EIGHT("Pattern 8", HEX_SECOND),
    NINE("Pattern 9", HEX_SECOND),
    TEN("Pattern 10", HEX_SECOND),
    ELEVEN("Pattern 11", HEX_SECOND),
    TWELVE("Pattern 12(h)", HEX_THIRD)
    ;


    private final String description;
    private final CentreConfiguration.Conf config;

    private HexPattern(String description, CentreConfiguration.Conf config) {
        this.description = description;
        this.config = config;
    }

    public String getDescription() {
        return description;
    }

    public CentreConfiguration.Conf getConfig() {
        return config;
    }

    public static HexPattern fromDescription(String description) {

        if (description.equalsIgnoreCase(ONE.getDescription())) {
            return ONE;
//        } else if (description.equalsIgnoreCase(TWO.getDescription())) {
//            return TWO;
        } else if (description.equalsIgnoreCase(STAR1.getDescription())) {
            return STAR1;
        } else if (description.equalsIgnoreCase(STAR2.getDescription())) {
            return STAR2;
        } else if (description.equalsIgnoreCase(STAR3.getDescription())) {
            return STAR3;
        } else if (description.equalsIgnoreCase(THREE.getDescription())) {
            return THREE;
        } else if (description.equalsIgnoreCase(FOUR.getDescription())) {
            return FOUR;
        } else if (description.equalsIgnoreCase(FIVE.getDescription())) {
            return FIVE;
        } else if (description.equalsIgnoreCase(SIX.getDescription())) {
            return SIX;
        } else if (description.equalsIgnoreCase(SEVEN.getDescription())) {
            return SEVEN;
        } else if (description.equalsIgnoreCase(EIGHT.getDescription())) {
            return EIGHT;
        } else if (description.equalsIgnoreCase(NINE.getDescription())) {
            return NINE;
        } else if (description.equalsIgnoreCase(TEN.getDescription())) {
            return TEN;
        } else if (description.equalsIgnoreCase(ELEVEN.getDescription())) {
            return ELEVEN;
        } else if (description.equalsIgnoreCase(TWELVE.getDescription())) {
            return TWELVE;
        } else {
            throw new IllegalArgumentException();
        }

    }

}
