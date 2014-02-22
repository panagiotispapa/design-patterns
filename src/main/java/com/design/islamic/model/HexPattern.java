package com.design.islamic.model;

public enum HexPattern {

    ONE("Pattern1"),
//    TWO("Pattern2"),
    STAR1("Pattern Star 1"),
    STAR2("Pattern Star 2"),
    STAR3("Pattern Star 3"),
    THREE("Pattern 3"),
    FOUR("Pattern 4"),
    FIVE("Pattern 5"),
    SIX("Pattern 6"),
    SEVEN("Pattern 7"),
    HEIGHT("Pattern 8"),
    NINE("Pattern 9")
    ;

    private final String description;

    private HexPattern(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
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
        } else if (description.equalsIgnoreCase(HEIGHT.getDescription())) {
            return HEIGHT;
        } else if (description.equalsIgnoreCase(NINE.getDescription())) {
            return NINE;
        } else {
            throw new IllegalArgumentException();
        }

    }

}
