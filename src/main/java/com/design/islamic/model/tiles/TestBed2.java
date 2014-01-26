package com.design.islamic.model.tiles;


import com.design.islamic.model.Centre;
import com.design.islamic.model.tiles.svg.Style;
import com.design.islamic.model.tiles.svg.SvgFactory;
import com.google.common.base.Function;
import com.jamesmurty.utils.XMLBuilder;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Set;

import static com.design.islamic.model.PolygonTools.calculateNewCellCentres;
import static com.design.islamic.model.tiles.svg.SvgFactory.*;

public class TestBed2 {


    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;

    public TestBed2(int width, int height, final double r) {

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        jsvgCanvas.setSize(width, height);
        jPanel.setSize(width, height);

//        jsvgCanvas.setLocation(0,0);

        Set<Point2D> newCentres = calculateNewCellCentres(calculateNewCellCentres(Centre.newCentre(width/2.0, height/2.0), r), r, 16) ;

        final Style style = new Style("yellow", "green", 2, 1, 1);
        List<XMLBuilder> shapes = newShapes(newCentres, r, new Function<Point2D, XMLBuilder>() {
            @Override
            public XMLBuilder apply(Point2D centre) {
                return newHexagon(centre, r, style);
            }
        });


        XMLBuilder mySVG = buildSvg(width, height, shapes);


        jsvgCanvas.setSVGDocument(SvgFactory.fromXMLBuilder(mySVG));

        System.out.println(jsvgCanvas.getSize());

    }


    public static void removeChildren(Node node) {
        while (node.hasChildNodes()){
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
        contentPane.add(new TestBed2(1024, 768, 40).getComponent());
        frame.setVisible(true);

        frame.invalidate();


    }
}
