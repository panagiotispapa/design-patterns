package com.design.islamic.model.tiles.svg;

import org.junit.Test;

import java.awt.*;
import java.awt.geom.Point2D;

import static com.design.common.view.SvgFactory.*;
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

        assertThat(newCircle(new Point2D.Double(1, 2), 10, newStyle("blue", "yellow", 2, 0.3, 1))).
                isEqualTo("<circle cx=\"1.0\" cy=\"2.0\" r=\"10.0\" style=\"fill:blue;stroke:yellow;stroke-width:2;fill-opacity:0.3;stroke-opacity:1.0\"/>")
        ;
    }

    @Test
    public void testNewStyle() {
        assertThat(newStyle("blue", "yellow", 2, 0.3, 1)).
                isEqualTo("fill:blue;stroke:yellow;stroke-width:2;fill-opacity:0.3;stroke-opacity:1.0")
        ;
    }

    @Test
    public void testColors() throws Exception {
        assertThat(toHex(Color.BLACK)).isEqualTo("#000000");
        assertThat(toHex(Color.BLUE)).isEqualTo("#0000ff");
        assertThat(toHex(Color.RED)).isEqualTo("#ff0000");
        assertThat(toHex(Color.GREEN)).isEqualTo("#00ff00");
        assertThat(toHex(null)).isEqualTo("none");
    }
//
//    @Test
//    public void testFromPathStyle() throws Exception {
//        assertThat(fromPathStyle().apply(new Path.Style(Color.BLUE, 2))).isEqualTo("stroke:#0000ff; stroke-width:2; fill:none;");
//        assertThat(fromPathStyle().apply(new Path.Style(Color.BLUE, 3, Color.BLACK))).isEqualTo("stroke:#0000ff; stroke-width:3; fill:#000000;");
//    }

    @Test
    public void testDrawPath() throws Exception {
//        assertThat(
//                drawPath(
//                        Arrays.asList(
//                                Pair.of(new Point2D.Double(40.0, 20.0), Path.InstructionType.STARTING_POINT)
//                        ),
//                        new Path.Style(Color.BLUE, 2)
//                )).isEqualTo("<path d = \"M40,20  A30,30 0 0,1 70,70\" style = \"stroke:#0000ff; stroke-width:2; fill:none;\" />");

//                < path d = "M40,20  A30,30 0 0,1 70,70" style = "stroke:#0000ff; stroke-width:2; fill:none;" / >

    }
}
