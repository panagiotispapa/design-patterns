package com.design.celtic.model;

public enum Pattern {

    ONE("Pattern1")
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
//        } else if (description.equalsIgnoreCase(TWO.getDescription())) {
//            return TWO;
//        } else if (description.equalsIgnoreCase(STAR1.getDescription())) {
//            return STAR1;
//        } else if (description.equalsIgnoreCase(STAR2.getDescription())) {
//            return STAR2;
//        } else if (description.equalsIgnoreCase(STAR3.getDescription())) {
//            return STAR3;
        } else {
            throw new IllegalArgumentException();
        }

    }

}
