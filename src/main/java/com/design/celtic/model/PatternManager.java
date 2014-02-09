package com.design.celtic.model;

import com.design.celtic.Patterns;
import com.google.common.collect.Maps;
import com.jamesmurty.utils.XMLBuilder;

import java.util.Map;

import static com.design.celtic.Patterns.buildPattern1;
import static com.design.celtic.model.Pattern.ONE;
import static com.google.common.collect.Maps.newHashMap;

public class PatternManager {

    private final Map<Pattern, PatternProvider> providerMap;


    private final double r;
    private final int width;
    private final int height;


    public PatternManager(double r, int width, int height) {

        this.r = r;
        this.width = width;
        this.height = height;

        providerMap = newHashMap();
        providerMap.put(ONE, new PatternProvider() {
            @Override
            public XMLBuilder provideSVG(int width, int height, double r) {
                return buildPattern1(width, height, r);
            }
        });

    }


    public XMLBuilder getSvg(Pattern pattern) {
        return providerMap.get(pattern).provideSVG(width, height, r);
    }

}
