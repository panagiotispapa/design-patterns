package com.design.islamic.model.tiles;

import com.design.islamic.Patterns;
import com.design.common.view.SvgFactory;
import com.jamesmurty.utils.XMLBuilder;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.Set;

import static com.design.islamic.Patterns.*;
import static com.design.islamic.model.Centre.newCentre;

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

        Set<Point2D> newCentresFirstConf = calculateNewCellCentresFirstConf(newCentre(0, 0), r, 17);
        Set<Point2D> newCentresSecondConf = calculateNewCellCentresSecondConf(newCentre(0, 0), r, 17);

        XMLBuilder mySVG = Patterns.buildHexPattern4(newCentresFirstConf, newCentresSecondConf, r, width, height);

//        XMLBuilder mySVG = buildSvg(width, height, highlightPoints(calculateHexEdges(newCentres, r)) );

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

        contentPane.add(new TestBed2(1024, 768, 70).getComponent());
        frame.setVisible(true);

        frame.invalidate();

    }
}
