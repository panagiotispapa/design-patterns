package com.design.islamic.model.tiles;

import com.design.common.PolygonTools;
import com.design.common.view.SvgFactory;
import com.design.islamic.DesignHelper;
import com.google.common.collect.ImmutableList;
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

import static com.design.common.PolygonTools.cloneAndTranslateScalePoints;
import static com.design.common.PolygonTools.hexPoints;
import static com.design.common.view.SvgFactory.buildSvg;
import static com.design.common.view.SvgFactory.drawPolygon;
import static com.design.common.view.SvgFactory.newStyle;
import static com.design.islamic.Patterns.*;
import static com.design.islamic.model.Centre.newCentre;

public class TestBed4 {

    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;

    public TestBed4(Dimension dim, final double r) {

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        jsvgCanvas.setSize(dim);
        jPanel.setSize(dim);

//        Set<Point2D> newCentres = calculateNewCellCentres(calculateNewCellCentres(newCentre(width / 2.0, height / 2.0), r), r, 16);
        Point2D centre = newCentre(dim.getWidth() / 2.0, dim.getHeight() / 2.0);
        Set<Point2D> newCentres = calculateNewCellCentres(centre, r, 17);

        final String styleBack = newStyle("black", "black", 1, 1, 1);
        final String style = newStyle("yellow", "yellow", 1, 1, 1);

        XMLBuilder backObj = drawPolygon(cloneAndTranslateScalePoints(centre, r, hexPoints), styleBack);

        List<XMLBuilder> testObject = DesignHelper.newStarDesign8(centre, r);

        ImmutableList.Builder<XMLBuilder> shapes = ImmutableList.builder();

        shapes.add(backObj);

        XMLBuilder mySVG = buildSvg(dim, (testObject)
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
        Dimension dim = new Dimension(1024+2*64, 768);

        JFrame frame = new JFrame();
        frame.setTitle("Polygon");
        frame.setSize(dim);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();
        contentPane.add(new TestBed4(dim, 300).getComponent());
        frame.setVisible(true);

        frame.invalidate();

    }
}
