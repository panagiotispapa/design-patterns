package com.design.celtic;

import com.design.common.view.SvgFactory;
import com.jamesmurty.utils.XMLBuilder;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class TestBed {
    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;

    public TestBed(Dimension dim, final double r) {

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        jsvgCanvas.setSize(dim);
        jPanel.setSize(dim);

//        Set<Point2D> newCentresFirstConf = calculateNewCellCentresFirstConf(newCentre(0, 0), r, 17);
//        Set<Point2D> newCentresSecondConf = calculateNewCellCentresSecondConf(newCentre(0, 0), r, 17);

//        XMLBuilder mySVG = com.design.islamic.Patterns.buildHexPatternStar(newCentresFirstConf, newCentresSecondConf, r, width, height, HEX_DIST3);

//        XMLBuilder mySVG = buildSvg(width, height, highlightPoints(calculateHexEdges(newCentres, r)) );

        XMLBuilder mySvg = Patterns.buildPattern1(dim, r);

        jsvgCanvas.setSVGDocument(SvgFactory.fromXMLBuilder(mySvg));

        System.out.println(jsvgCanvas.getSize());

    }

    public JPanel getComponent() {
        return jPanel;
    }

    public static void main(String[] args) {

        Dimension dim = new Dimension(1024, 768);

        JFrame frame = new JFrame();
        frame.setTitle("Polygon");
        frame.setSize(dim);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();

        contentPane.add(new TestBed(dim, 256).getComponent());
        frame.setVisible(true);

        frame.invalidate();

    }


}
