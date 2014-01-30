package com.design.islamic.model;

public enum Pattern {

    ONE("Pattern1"),
    TWO("Pattern2"),
    THREE("Pattern3");

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
        } else if (description.equalsIgnoreCase(THREE.getDescription())) {
            return THREE;
        } else {
            throw new IllegalArgumentException();
        }

    }

}
