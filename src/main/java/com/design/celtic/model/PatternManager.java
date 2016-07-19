package com.design.celtic.model;

import com.design.celtic.Patterns;

import java.awt.*;
import java.util.Map;

import static com.design.celtic.Patterns.buildPattern1;
import static com.design.celtic.model.Pattern.ONE;
import static com.google.common.collect.Maps.newHashMap;

public class PatternManager {

    private final Map<Pattern, PatternProvider> providerMap;


    private final double r;
    private final Dimension dim;


    public PatternManager(double r, Dimension dim) {

        this.r = r;
        this.dim = dim;


        providerMap = newHashMap();
        providerMap.put(ONE, Patterns::buildPattern1);

    }


    public String getSvg(Pattern pattern) {
        return providerMap.get(pattern).provideSVG(dim, r);
    }

}
