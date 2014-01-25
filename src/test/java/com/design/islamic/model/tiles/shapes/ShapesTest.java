package com.design.islamic.model.tiles.shapes;

import com.design.islamic.model.Centre;
import com.design.islamic.model.tiles.svg.Style;
import org.fest.assertions.api.Assertions;
import org.junit.Test;

import static com.design.islamic.model.tiles.shapes.Shapes.newCircle;
import static com.design.islamic.model.tiles.shapes.Shapes.newHexagon;

public class ShapesTest {
    @Test
    public void testNewHexagon() throws Exception {

        Assertions.assertThat(newHexagon(new Centre(1, 2), 10, new Style("blue", "yellow", 2, 0.3, 1)).toSvgXML().asString()).
                isEqualTo("<polygon points=\"11.0,2.0 6.000000000000001,10.660254037844386 -3.9999999999999982,10.660254037844387 -9.0,2.0000000000000013 -4.000000000000004,-6.6602540378443855 5.999999999999993,-6.660254037844391 \" style=\"fill:blue;stroke:yellow;stroke-width:2;fill-opacity:0.3;stroke-opacity:1.0\"/>")
        ;
    }

    @Test
    public void testNewCircle() throws Exception {

        Assertions.assertThat(newCircle(new Centre(1, 2), 10, new Style("blue", "yellow", 2, 0.3, 1)).toSvgXML().asString()).
                isEqualTo("<circle cx=\"1.0\" cy=\"2.0\" r=\"10.0\" style=\"fill:blue;stroke:yellow;stroke-width:2;fill-opacity:0.3;stroke-opacity:1.0\"/>")
        ;
    }
}
