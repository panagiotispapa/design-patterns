package com.design.islamic;

import com.design.common.PolygonTools;
import org.junit.Test;

import java.awt.geom.Point2D;

import static com.design.common.PolygonTools.cloneAndTranslateScalePoints;
import static com.design.common.PolygonTools.hexPoints;
import static com.design.islamic.Patterns.*;
import static com.design.islamic.model.Centre.newCentre;
import static com.design.common.view.SvgFactory.newStyle;
import static org.fest.assertions.api.Assertions.assertThat;

public class PatternsTest {
    @Test
    public void testCalculateHexEdges() throws Exception {
        Point2D centre = newCentre(400, 400);
        double r = 40;

        assertThat(cloneAndTranslateScalePoints(centre, r, hexPoints)).hasSize(6);
        assertThat(calculateHexEdges(calculateNewCellCentres(centre, r, 1), r)).hasSize(4*6);
    }

    @Test
    public void testCalculateNewCellCentres() throws Exception {
        Point2D centre = newCentre(10, 10);

        assertThat(calculateNewCellCentres(centre, 10, 1)).hasSize(7);
        assertThat(calculateNewCellCentres(centre, 10, 2)).hasSize(20);
    }


    @Test
    public void testNewHexagon() throws Exception {

//        assertThat(newHexagon(newCentre(1, 2), 10, newStyle("blue", "yellow", 2, 0.3, 1)).asString()).
//                isEqualTo("<polygon points=\"11.0,2.0 6.000000000000001,10.660254037844386 -3.9999999999999982,10.660254037844387 -9.0,2.0000000000000013 -4.000000000000004,-6.6602540378443855 5.999999999999993,-6.660254037844391 \" style=\"fill:blue;stroke:yellow;stroke-width:2;fill-opacity:0.3;stroke-opacity:1.0\"/>")
//        ;
    }


}
