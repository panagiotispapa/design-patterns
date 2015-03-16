package com.design.islamic.model.tiles;

import com.design.islamic.HexDesignHelper;
import com.design.islamic.model.hex.*;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.commons.lang3.tuple.Pair;
import org.w3c.dom.Node;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static com.design.common.view.SvgFactory.buildSvg;
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
//        Set<Point2D> newCentres = calculateNewCellCentres(centre, r, 17);

//        final String styleBack = newStyle("black", "black", 1, 1, 1);
//        final String style = newStyle("yellow", "yellow", 1, 1, 1);

//        String backObj = drawPolygon(cloneAndTranslateScalePoints(centre, r, hexPoints), styleBack);

//        String testObject = new TileStar(Pair.of(centre, r), TileStar.RATIO_1).design3();
//        String testObject = new Tile7(Pair.of(centre, r)).design1();

        String testObject = HexDesignHelper.with(Pair.of(centre, r)).newDesign12();
//        String testObject = HexDesignHelper.with(Pair.of(centre, r)).newDesign28();
//        String testObject = HexDesignHelper.with(Pair.of(centre, r)).newStarDesign3();
//        String testObject = HexDesignHelper.newDesign8(Pair.of(centre, r));
//        String testObject = RectDesignHelper.newRectDesign1(centre, r);

//        StringBuilder shapes = new StringBuilder();
//
//        shapes.append(backObj);

        String mySVG = buildSvg(dim, (testObject)
        );

        saveToFile(mySVG);

//        jsvgCanvas.setSVGDocument(SvgFactory.fromSvgDoc(mySVG));
//
//        System.out.println(jsvgCanvas.getSize());

    }

    private void saveToFile(String svg) {

        try {
            Files.write(Paths.get("C:\\p\\", "out.html"),
                    Arrays.asList(toHtml(svg))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String toHtml(String svg) {
        return "<html>" +
                "<header>" +
                "</header>" +
                "<body>" +
                svg +
                "</body>" +
                "</html>";

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
        Dimension dim = new Dimension(1024 + 2 * 128 + 32, 768);

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
//        frame.setVisible(true);

        frame.invalidate();

    }
}
