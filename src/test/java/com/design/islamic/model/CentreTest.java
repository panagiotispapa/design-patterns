package com.design.islamic.model;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.fest.assertions.api.Assertions;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.Set;

import static com.design.islamic.model.Centre.newCentre;

public class CentreTest {

    @Test
    public void testEquality() throws Exception {
        Point2D c1 = newCentre(1.0, 3.0);
        Point2D c2 = newCentre(4.0, 6.0);
        Point2D c3 = newCentre(4.9, 6.0);


        Point2D c4 = newCentre(280.0, 265.3589838486224);
        Point2D c5 = newCentre(280.0, 265.3589838486225);




//        Set<Point2D> set = Sets.newHashSet(c1, c2, c3);
        Set<Point2D> set = Sets.newHashSet(Lists.newArrayList(c1, c2, c3, c4, c5));

        set.addAll(Lists.newArrayList(newCentre(4.0, 6.4)));

        Assertions.assertThat(set).containsOnly(c1, c2, c4);


    }
}
