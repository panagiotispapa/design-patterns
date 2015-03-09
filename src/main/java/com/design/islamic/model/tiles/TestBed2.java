package com.design.islamic.model.tiles;

import com.design.common.view.SvgFactory;
import com.design.islamic.CentreConfiguration;
import com.design.islamic.model.Payload;
import com.design.islamic.model.hex.*;
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
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import static com.design.common.view.SvgFactory.*;
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

        String background = buildBackground(dim);

        List<Pair<String, Supplier<Payload>>> patterns = Arrays.asList(
//                Pair.of("Tile_Star_1", () -> new TileStar(ic, TileStar.RATIO_1).getPayload()),
//                Pair.of("Tile_Star_2", () -> new TileStar(ic, TileStar.RATIO_2).getPayload()),
//                Pair.of("Tile_Star_3", () -> new TileStar(ic, TileStar.RATIO_3).getPayload()),
//                Pair.of("Tile_02", () -> new Tile2(ic).getPayload()),
//                Pair.of("Tile_03", () -> new Tile3(ic).getPayload()),
//                Pair.of("Tile_04", () -> new Tile4(ic).getPayload()),
//                Pair.of("Tile_05", () -> new Tile5(ic).getPayload()),
//                Pair.of("Tile_06", () -> new Tile6(ic).getPayload())
                Pair.of("Tile_07", () -> new Tile7(ic).getPayload())
//                Pair.of("Tile_3", () -> new Tile3(ic).getPayload())
                );

        patterns.forEach(p ->
                saveToFile(buildSvg(dim, background + buildSvgFromPayload(p.getRight(), ic)), p.getLeft()));

//        String mySVG = buildSvg(dim,
//                background + buildSvgFromPayload(() -> new Tile3(ic).getPayload(), ic));
//
//        String mySVG = Patterns.buildHexPatternBlackAndWhite(
//                buildHexPatterns(centreConfiguration.getCentresConfig(RECT), new com.design.islamic.model.rect.
//                        Tile1(newCentre(0,0),r).getPayload())
//                , dim
//
//        );

//        saveToFile(mySVG,"out");

        System.out.println("Finished in " + (currentTimeMillis() - now) / 1000.0);

//        jsvgCanvas.setSVGDocument(SvgFactory.fromSvgDoc(mySVG));
//
//        System.out.println(jsvgCanvas.getSize());

    }

    private String buildSvgFromPayload(Supplier<Payload> supplier, Pair<Point2D, Double> ic) {
        Payload payload = supplier.get();
        List<Point2D> gridPoints = Grid.gridFromStart(ic.getLeft(), ic.getRight(), payload.getGridConfiguration(), 17);

        return SvgFactory.drawOnGrid(payload.getPolylines(), gridPoints, newStyle(WHITE, 2, 1));

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

    private void saveToFile(String svg, String name) {

        try {
            Files.write(Paths.get("C:\\p\\", name + ".html"),
                    Arrays.asList(toHtml(svg))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String buildBackground(Dimension dim) {

        final String styleBlack = newStyle(BLACK, BLACK, 1, 1, 1);
        List<Point2D> backGroundRect = asList(
                newCentre(0, 0),
                newCentre(dim.getWidth(), 0),
                newCentre(dim.getWidth(), dim.getHeight()),
                newCentre(0, dim.getHeight()));

        return drawPolygon(backGroundRect, styleBlack);
        //shapes.append();

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
