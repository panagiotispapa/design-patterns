package com.design.islamic.model;

import com.design.islamic.CentreConfiguration;
import com.design.islamic.Patterns;
import com.design.islamic.model.hex.*;
import com.design.islamic.model.hex.Tile1;
import com.design.islamic.model.rect.*;
import com.google.common.collect.Maps;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.Set;

import static com.design.common.PolygonTools.*;
import static com.design.islamic.model.Centre.newCentre;

public class PatternManager {

    private final Map<HexPattern, Payload> hexProviderMap;
    private final Map<RectPattern, Payload> rectProviderMap;

    private final CentreConfiguration centreConfiguration;


    private final Dimension dim;


    public PatternManager(double r, Dimension dim) {


        this.dim = dim;

        centreConfiguration = new CentreConfiguration(
                r, 17
        );

        hexProviderMap = Maps.newHashMap();
        rectProviderMap = Maps.newHashMap();

        rectProviderMap.put(RectPattern.ONE, new com.design.islamic.model.rect.Tile1(newCentre(0, 0), r).getPayload());
        rectProviderMap.put(RectPattern.TWO, new com.design.islamic.model.rect.Tile2(newCentre(0, 0), r).getPayload());


        hexProviderMap.put(HexPattern.ONE, new Tile1(newCentre(0, 0), r).getPayload());
//        hexProviderMap.put(HexPattern.TWO, new PatternProvider() {
//            @Override
//            public XMLBuilder provideSVG(CentreConfiguration centreConfiguration, double r, Dimension dim) {
//                return buildHexPattern2(centreConfiguration, r, dim);
//            }
//        });
        hexProviderMap.put(HexPattern.STAR1, new TileStar(newCentre(0, 0), r, HEX_DIST_OUTER_CIRCLE).getPayload());
        hexProviderMap.put(HexPattern.STAR2, new TileStar(newCentre(0, 0), r, HEX_DIST_DIAGONAL).getPayload());
        hexProviderMap.put(HexPattern.STAR3, new TileStar(newCentre(0, 0), r, HEX_DIST3).getPayload());
        hexProviderMap.put(HexPattern.THREE, new Tile3(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.FOUR, new Tile4(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.FIVE, new Tile5(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.SIX, new Tile6(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.SEVEN, new Tile7(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.EIGHT, new Tile8(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.NINE, new Tile9(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TEN, new Tile10(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.ELEVEN, new Tile11(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWELVE, new Tile12(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.THIRTEEN, new Tile13(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.FOURTEEN, new Tile14(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.FIFTEEN, new Tile15(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.SIXTEEN, new Tile16(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.SIXTEEN, new Tile16(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.SEVENTEEN, new Tile17(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.EIGHTEEN, new Tile18(newCentre(0, 0), r).getPayload());

    }


    public String getSvg(RectPattern rectPattern) {
        return Patterns.buildHexPatternBlackAndWhite(
                Patterns.buildHexPatterns(centreConfiguration.getCentresConfig(rectPattern.getConfig(), 1.0), rectProviderMap.get(rectPattern))
                , dim);

    }
    public String getSvg(HexPattern hexPattern) {
        return Patterns.buildHexPatternBlackAndWhite(
                Patterns.buildHexPatterns(centreConfiguration.getCentresConfig(hexPattern.getConfig(), hexPattern.getRatio()), hexProviderMap.get(hexPattern))
                , dim);

//        return hexProviderMap.get(hexPattern).provideSVG(centreConfiguration, r, dim);
    }

}
