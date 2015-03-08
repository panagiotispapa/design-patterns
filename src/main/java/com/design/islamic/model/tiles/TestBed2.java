package com.design.islamic.model.tiles;

import com.design.common.view.SvgFactory;
import com.design.islamic.CentreConfiguration;
import com.design.islamic.Patterns;
import com.design.islamic.model.Payload;
import com.design.islamic.model.hex.Tile3;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

import static com.design.common.view.SvgFactory.*;
import static com.design.islamic.CentreConfiguration.Conf.HEX_SECOND;
import static com.design.islamic.Patterns.buildHexPatterns;
import static com.design.islamic.model.Centre.newCentre;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;

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

        CentreConfiguration centreConfiguration = new CentreConfiguration(r, 17);

        Pair<Point2D, Double> ic = Pair.of(new Point2D.Double(0, 0), r);

        long now = currentTimeMillis();

//        String mySVG = Patterns.buildHexPatternBlackAndWhite(
//                buildHexPatterns(
//                        centreConfiguration.getCentresConfig(HEX_SECOND, 1.0),
//                        new Tile3(ic).getPayload())
//                , dim
//
//        );

        Payload payload = new Tile3(ic).getPayload();
        List<Point2D> gridPoints = Grid.gridFromStart(ic.getLeft(), ic.getRight(), payload.getGridConfiguration(), 17);
        String svg = SvgFactory.drawOnGrid(payload.getPolylines(), gridPoints, newStyle(BLACK, 2, 1));

        String mySVG = buildSvg(dim,
                svg);


//        String mySVG = Patterns.buildHexPatternBlackAndWhite(
//                buildHexPatterns(centreConfiguration.getCentresConfig(RECT), new com.design.islamic.model.rect.
//                        Tile1(newCentre(0,0),r).getPayload())
//                , dim
//
//        );

        saveToFile(mySVG);

        System.out.println("Finished in " + (currentTimeMillis() - now) / 1000.0);

//        jsvgCanvas.setSVGDocument(SvgFactory.fromSvgDoc(mySVG));
//
//        System.out.println(jsvgCanvas.getSize());

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

    private void saveToFile(String svg) {

        try {
            Files.write(Paths.get("C:\\p\\", "out.html"),
                    Arrays.asList(toHtml(svg))
            );
        } catch (IOException e) {
            e.printStackTrace();
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

        contentPane.add(new TestBed2(dim, 100).getComponent());
//        frame.setVisible(true);

        frame.invalidate();

    }
}
