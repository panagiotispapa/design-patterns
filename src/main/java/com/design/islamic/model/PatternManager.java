package com.design.islamic.model;

import com.design.islamic.CentreConfiguration;
import com.design.islamic.Patterns;
import com.design.islamic.model.hex.*;
import com.google.common.collect.Maps;

import java.awt.*;
import java.util.Map;

import static com.design.common.PolygonTools.*;
import static com.design.islamic.Patterns.calculateNewCellCentresFirstConf;
import static com.design.islamic.Patterns.calculateNewCellCentresSecondConf;
import static com.design.islamic.Patterns.calculateNewRectCentresConf;
import static com.design.islamic.model.Centre.newCentre;

public class PatternManager {

    private final Map<HexPattern, Payload> providerMap;

    private final CentreConfiguration centreConfiguration;

    private final double r;

    private final Dimension dim;

    public PatternManager(double r, Dimension dim) {

        this.r = r;

        this.dim = dim;

        centreConfiguration = new CentreConfiguration(
                calculateNewCellCentresFirstConf(newCentre(0, 0), r, 17),
                calculateNewCellCentresSecondConf(newCentre(0, 0), r, 17),
                calculateNewRectCentresConf(newCentre(0, 0), r, 17)
        );

        providerMap = Maps.newHashMap();

        providerMap.put(HexPattern.ONE, new Tile1(newCentre(0, 0), r).getPayload());

//        providerMap.put(HexPattern.TWO, new PatternProvider() {
//            @Override
//            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
//                return buildHexPattern2(centreConfiguration, r, dim);
//            }
//        });
        providerMap.put(HexPattern.STAR1, new TileStar(newCentre(0, 0), r,HEX_DIST_OUTER_CIRCLE).getPayload());
        providerMap.put(HexPattern.STAR2, new TileStar(newCentre(0, 0), r,HEX_DIST_DIAGONAL).getPayload());
        providerMap.put(HexPattern.STAR3, new TileStar(newCentre(0, 0), r,HEX_DIST3).getPayload());
        providerMap.put(HexPattern.THREE, new Tile3(newCentre(0, 0), r).getPayload());
        providerMap.put(HexPattern.FOUR, new Tile4(newCentre(0, 0), r).getPayload());
        providerMap.put(HexPattern.FIVE, new Tile5(newCentre(0, 0), r).getPayload());
        providerMap.put(HexPattern.SIX, new Tile6(newCentre(0, 0), r).getPayload());
        providerMap.put(HexPattern.SEVEN, new Tile7(newCentre(0, 0), r).getPayload());
        providerMap.put(HexPattern.EIGHT, new Tile8(newCentre(0, 0), r).getPayload());
        providerMap.put(HexPattern.NINE, new Tile9(newCentre(0, 0), r).getPayload());
        providerMap.put(HexPattern.TEN, new Tile10(newCentre(0, 0), r).getPayload());
        providerMap.put(HexPattern.ELEVEN, new Tile11(newCentre(0, 0), r).getPayload());

    }

    public String getSvg(HexPattern hexPattern) {
        return Patterns.buildHexPatternBlackAndWhite(
                Patterns.buildHexPatterns(centreConfiguration.getCentresSecondConf(), providerMap.get(hexPattern))
                , dim);

//        return providerMap.get(hexPattern).provideSVG(centreConfiguration, r, dim);
    }

}
