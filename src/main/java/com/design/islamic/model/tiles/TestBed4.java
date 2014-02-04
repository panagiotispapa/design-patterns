package com.design.islamic.model.tiles;

import com.design.islamic.model.tiles.svg.SvgFactory;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterables;
import com.jamesmurty.utils.XMLBuilder;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.*;
import java.util.List;

import static com.design.islamic.Patterns.*;
import static com.design.islamic.model.Centre.newCentre;
import static com.design.islamic.model.tiles.svg.SvgFactory.*;

public class TestBed4 {

    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;

    public TestBed4(int width, int height, final double r) {

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        jsvgCanvas.setSize(width, height);
        jPanel.setSize(width, height);

//        Set<Point2D> newCentres = calculateNewCellCentres(calculateNewCellCentres(newCentre(width / 2.0, height / 2.0), r), r, 16);
        Point2D centre = newCentre(width / 2.0, height / 2.0);
        Set<Point2D> newCentres = calculateNewCellCentres(centre, r, 17);

        final String styleBack = newStyle("black", "black", 1, 1, 1);
        final String style = newStyle("yellow", "yellow", 1, 1, 1);

        XMLBuilder backObj = newHexagon(centre, r, styleBack);

//        XMLBuilder testObject = newHexStarTile(centre, r, style);

//        Set<Point2D> testPoints = calculateNewCellCentresSecondConf(newCentre(100, 100), r, 3);
        List<XMLBuilder> testObject = newStarDesign1(centre, r);
//        List<XMLBuilder> testObject2 = newHexTile1_2(centre, r, style);

        ImmutableList.Builder<XMLBuilder> shapes = ImmutableList.builder();

        shapes.add(backObj);
//        shapes.add(testObject);
//        shapes.addAll(testObject2);

//        XMLBuilder mySVG = buildSvg(width, height, shapes.build());

        XMLBuilder mySVG = buildSvg(width, height, (testObject)
        );

        jsvgCanvas.setSVGDocument(SvgFactory.fromXMLBuilder(mySVG));

        System.out.println(jsvgCanvas.getSize());

    }

    public static void removeChildren(Node node) {
        while (node.hasChildNodes()) {
            node.removeChild(node.getFirstChild());
        }

    }

    public JPanel getComponent() {
        return jPanel;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Polygon");
        frame.setSize(1024, 768);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();
        contentPane.add(new TestBed4(1024, 768, 200).getComponent());
        frame.setVisible(true);

        frame.invalidate();

    }
}
