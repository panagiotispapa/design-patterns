package com.design.islamic.model.tiles;

import com.design.common.PolygonTools;
import com.design.islamic.CentreConfiguration;
import com.design.islamic.Patterns;
import com.design.common.view.SvgFactory;
import com.design.islamic.model.hex.*;
import com.design.islamic.model.rect.*;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.util.Set;

import static com.design.common.PolygonTools.HEX_DIST_HEIGHT;
import static com.design.common.PolygonTools.HEX_PHI;
import static com.design.islamic.CentreConfiguration.Conf.*;
import static com.design.islamic.Patterns.*;
import static com.design.islamic.model.Centre.newCentre;
import static java.lang.System.currentTimeMillis;

public class TestBed2 {

    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;

    public TestBed2(Dimension dim, final double r) {

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        jsvgCanvas.setSize(dim);
        jPanel.setSize(dim);

        CentreConfiguration centreConfiguration = new CentreConfiguration(r,17);




        long now = currentTimeMillis();

        String mySVG = Patterns.buildHexPatternBlackAndWhite(
                buildHexPatterns(
                        centreConfiguration.getCentresConfig(HEX_SECOND, 1.0),
                        new Tile26(newCentre(0,0),r).getPayload())
                , dim

        );


//        String mySVG = Patterns.buildHexPatternBlackAndWhite(
//                buildHexPatterns(centreConfiguration.getCentresConfig(RECT), new com.design.islamic.model.rect.
//                        Tile1(newCentre(0,0),r).getPayload())
//                , dim
//
//        );




        System.out.println("Finished in " + (currentTimeMillis()-now)/1000.0);


        jsvgCanvas.setSVGDocument(SvgFactory.fromSvgDoc(mySVG));

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

        Dimension dim = new Dimension(1024+2*128 + 32, 768);

        JFrame frame = new JFrame();
        frame.setTitle("Polygon");
        frame.setSize(dim);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();

        contentPane.add(new TestBed2(dim, 100).getComponent());
        frame.setVisible(true);

        frame.invalidate();

    }
}
