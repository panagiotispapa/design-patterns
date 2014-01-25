package com.design.islamic.model.tiles.svg;

import com.design.islamic.model.Centre;
import com.design.islamic.model.PolygonTools;
import com.design.islamic.model.tiles.shapes.Shape;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

public class SvgFactoryTest {

    @Test
    public void testBuildSVG() throws Exception {
        List<? extends Shape> shapes = PolygonTools.buildHexPattern0(
                Lists.newArrayList(new Centre(2, 50)),
                20,
                new Style("blue", "yellow", 2, 0.3, 1)
        );


        System.out.println(
                SvgFactory.buildSVG(
                        1024,
                        1000,
                        shapes
                )
        );
    }
}
