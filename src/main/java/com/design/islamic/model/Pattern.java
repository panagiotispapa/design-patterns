package com.design.islamic.model;

public enum Pattern {

    ONE("Pattern1"),
    TWO("Pattern2"),
    STAR1("Pattern Star 1"),
    STAR2("Pattern Star 2"),
    STAR3("Pattern Star 3"),
    THREE("Pattern 3"),
    FOUR("Pattern 4"),
    FIVE("Pattern 5")
    ;

    private final String description;

    private Pattern(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static Pattern fromDescription(String description) {

        if (description.equalsIgnoreCase(ONE.getDescription())) {
            return ONE;
        } else if (description.equalsIgnoreCase(TWO.getDescription())) {
            return TWO;
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
        } else {
            throw new IllegalArgumentException();
        }

    }

}
