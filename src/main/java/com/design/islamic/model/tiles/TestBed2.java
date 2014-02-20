package com.design.islamic.model.tiles;

import com.design.islamic.CentreConfiguration;
import com.design.islamic.Patterns;
import com.design.common.view.SvgFactory;
import com.design.islamic.model.Tile;
import com.design.islamic.model.hex.*;
import com.google.common.base.Function;
import com.jamesmurty.utils.XMLBuilder;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;

import static com.design.common.PolygonTools.HEX_DIST_DIAGONAL;
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

//        Set<Point2D> newCentresFirstConf = calculateNewCellCentresFirstConf(newCentre(0, 0), r, 17);
//        Set<Point2D> newCentresSecondConf = calculateNewCellCentresSecondConf(newCentre(0, 0), r, 17);

        CentreConfiguration centreConfiguration = new CentreConfiguration(
                calculateNewCellCentresFirstConf(newCentre(0, 0), r, 17),
                calculateNewCellCentresSecondConf(newCentre(0, 0), r, 17));

        long now = currentTimeMillis();

        XMLBuilder mySVG = Patterns.buildHexPatternBlackAndWhite(centreConfiguration, dim,
                new Function<Point2D, Tile>() {
                    @Override
                    public Tile apply(Point2D centre) {
                        return new Tile3(centre, r);
                    }
                }
        );

        System.out.println("Finished in " + (currentTimeMillis()-now)/1000.0);

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

        Dimension dim = new Dimension(1024+2*64 + 32, 768);

        JFrame frame = new JFrame();
        frame.setTitle("Polygon");
        frame.setSize(dim);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();

        contentPane.add(new TestBed2(dim, 64).getComponent());
        frame.setVisible(true);

        frame.invalidate();

    }
}
