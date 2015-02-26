package com.design.islamic.model;

import com.design.islamic.CentreConfiguration;
import com.design.islamic.model.hex.*;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.tuple.Pair;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.Map;

import static com.design.islamic.Patterns.buildHexPatternBlackAndWhite;
import static com.design.islamic.Patterns.buildHexPatterns;
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
        Pair<Point2D, Double> ic = Pair.of(newCentre(0, 0), r);

        hexProviderMap.put(HexPattern.STAR1, new TileStar(ic, TileStar.RATIO_2).getPayload());
        hexProviderMap.put(HexPattern.STAR2, new TileStar(ic, TileStar.RATIO_1).getPayload());
        hexProviderMap.put(HexPattern.STAR3, new TileStar(ic, TileStar.RATIO_3).getPayload());
        hexProviderMap.put(HexPattern.THREE, new Tile3New(ic).getPayload());
        hexProviderMap.put(HexPattern.FOUR, new Tile4(ic).getPayload());
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
        hexProviderMap.put(HexPattern.NINETEEN, new Tile19(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWENTY, new Tile20(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWENTY_ONE, new Tile21(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWENTY_TWO, new Tile22(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWENTY_THREE, new Tile23(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWENTY_FOUR, new Tile24(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWENTY_FIVE, new Tile25(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWENTY_SIX, new Tile26(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWENTY_SEVEN, new Tile27(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWENTY_EIGHT, new Tile28(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.TWENTY_NINE, new Tile29(newCentre(0, 0), r).getPayload());
        hexProviderMap.put(HexPattern.THIRTY, new Tile30(newCentre(0, 0), r).getPayload());

    }

    public String getSvg(RectPattern rectPattern) {
        return buildHexPatternBlackAndWhite(
                buildHexPatterns(centreConfiguration.getCentresConfig(rectPattern.getConfig(), 1.0), rectProviderMap.get(rectPattern))
                , dim);

    }

    public String getSvg(HexPattern hexPattern) {
        return buildHexPatternBlackAndWhite(
                buildHexPatterns(centreConfiguration.getCentresConfig(hexPattern.getConfig(), hexPattern.getRatio()), hexProviderMap.get(hexPattern))
                , dim);

//        return hexProviderMap.get(hexPattern).provideSVG(centreConfiguration, r, dim);
    }

}
