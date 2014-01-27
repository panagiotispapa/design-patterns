package com.design.islamic.model.tiles.svg;

import org.junit.Test;

import static com.design.islamic.model.Centre.newCentre;
import static com.design.islamic.model.tiles.svg.SvgFactory.newCircle;
import static com.design.islamic.model.tiles.svg.SvgFactory.newStyle;
import static org.fest.assertions.api.Assertions.assertThat;

public class SvgFactoryTest {

    @Test
    public void testBuildSVG() throws Exception {
//        List<? extends Shape> shapes = PolygonTools.buildHexPattern0(
//                Lists.newArrayList(new Centre(2, 50)),
//                20,
//                new Style("blue", "yellow", 2, 0.3, 1)
//        );
//
//
//        System.out.println(
//                SvgFactory.buildSVG(
//                        1024,
//                        1000,
//                        shapes
//                )
//        );
    }

    @Test
    public void testNewCircle() throws Exception {

        assertThat(newCircle(newCentre(1, 2), 10, newStyle("blue", "yellow", 2, 0.3, 1)).asString()).
                isEqualTo("<circle cx=\"1.0\" cy=\"2.0\" r=\"10.0\" style=\"fill:blue;stroke:yellow;stroke-width:2;fill-opacity:0.3;stroke-opacity:1.0\"/>")
        ;
    }

    @Test
    public void testNewStyle() {
        assertThat(newStyle("blue", "yellow", 2, 0.3, 1)).
                isEqualTo("fill:blue;stroke:yellow;stroke-width:2;fill-opacity:0.3;stroke-opacity:1.0")
        ;

    }
}
