package com.design.islamic.model.tiles;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.InitialConditions;
import com.design.common.Polygon;
import com.design.deco.Tile4;
import com.design.deco.Tile5;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.hex.*;
import com.google.common.collect.ImmutableMap;
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
import java.util.Map;
import java.util.function.Supplier;

import static com.design.common.view.SvgFactory.*;
import static java.lang.System.currentTimeMillis;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class TestBed2 {

    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;


    private static Map<PayloadSimple.Size, Double> sizeToRNew = ImmutableMap.of(
            PayloadSimple.Size.SMALL, 100.0,
            PayloadSimple.Size.MEDIUM, 150.0,
            PayloadSimple.Size.LARGE, 200.0
    );

    public TestBed2(Dimension dim, final double r) {

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        jsvgCanvas.setSize(dim);
        jPanel.setSize(dim);

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

//        List<Supplier<PayloadSimple>> patterns = Arrays.asList(
//                Pair.of("Tile_Star_1", ()-> TileStar.getPayloadSimple(TileStar.RATIO_1)),
//                Pair.of("Tile_Star_2", ()-> TileStar.getPayloadSimple(TileStar.RATIO_2)),
//                TileStar::getPayloadSimple3
//                Pair.of("Tile_02", Tile2::getPayloadSimple),
//                Pair.of("Tile_03", Tile3::getPayloadSimple),
//                Pair.of("Tile_04", Tile4::getPayloadSimple),
//                Pair.of("Tile_05", Tile5::getPayloadSimple)
//                Pair.of("Tile_06", Tile6::getPayloadSimple)
//                Tile9New::getPayloadSimple
//                Tile12::getPayloadSimple
//                com.design.deco.Tile2::getPayloadSimple,
//                com.design.deco.Tile3::getPayloadSimple,
//                com.design.deco.Tile4::getPayloadSimple,

//                TileGrid::getPayloadSimple,
//                TileGrid::getPayloadSimple2,
//                TileGrid::getPayloadSimple3,
//                Tile2b::getPayloadSimple,
//                Tile30::getPayloadSimple,
//                Tile7::getPayloadSimple,
//                Tile8::getPayloadSimple,
//                Tile9::getPayloadSimple,
//                Tile29::getPayloadSimple
//                Pair.of("Tile_07", Tile7::getPayloadSimple)
//                Tile8::getPayloadSimple
//                Pair.of("Tile_3", () -> new Tile3(ic).getPayload())
//        );

//        patterns.forEach(p -> {
//                    PayloadSimple payloadSimple = p.get();
//                    Pair<Point2D, Double> newIC = Pair.of(ic.getLeft(), sizeToR.get(payloadSimple.getSize()));
//                    Dimension newDim = new Dimension((int) (15 * newIC.getRight()), (int) (10 * newIC.getRight()));
//                    saveToFile(
//                            buildSvg(newDim,
//                                    buildBackground(newDim) + buildSvgFromPayloadSimple(payloadSimple, newIC)), payloadSimple.getName());
//                }
//        );


        List<Supplier<PayloadSimple>> patternsNew = Arrays.asList(
                com.design.islamic.model.rect.Tile1::getPayloadSimple,
                Tile4::getPayloadSimple,
                Tile5::getPayloadSimple,
                Tile9c::getPayloadSimple,
                com.design.islamic.model.rect.Tile2::getPayloadSimple
        );

        patternsNew.forEach(p -> {
                    PayloadSimple payload = p.get();
                    InitialConditions newIC = InitialConditions.of(ic.getLeft(), sizeToRNew.get(payload.getSize()));
                    Dimension newDim = new Dimension((int) (15 * newIC.getR()), (int) (10 * newIC.getR()));
                    saveToFile(
                            buildSvg(newDim,
                                    buildBackground(newDim) + buildSvgFromPayloadSimpleNew(payload, newIC)), payload.getName());
                }
        );

        drawDesigns(dim);

//        saveToFile(mySVG,"out");

        System.out.println("Finished in " + (currentTimeMillis() - now) / 1000.0);

//        jsvgCanvas.setSVGDocument(SvgFactory.fromSvgDoc(mySVG));
//
//        System.out.println(jsvgCanvas.getSize());

    }

    private void drawDesigns(Dimension dim) {

        Point2D centre = new Point2D.Double(dim.getWidth() / 2.0, dim.getHeight() / 2.0);

        Pair<Point2D, Double> ic = Pair.of(centre, 300.0);

        List<Supplier<DesignHelper>> designs = Arrays.asList(
//                Pair.of("Tile_Star_1", new TileStar(ic, TileStar.RATIO_1)::design1),
//                Pair.of("Tile_Star_2", new TileStar(ic, TileStar.RATIO_1)::design2),
//                TileStar::getDesignHelper3
//                Tile9::getDesignHelper1,
//                Tile9::getDesignHelper2,
//                Tile9::getDesignHelper3,
//                Tile15::getDesignHelper,
//                Tile9::getDesignHelper,
//                TileStar::getDesignHelper1,
//                Tile22::getDesignHelper,
//                Tile7::getDesignHelper,
//                Tile8::getDesignHelper,
//                Tile29::getDesignHelper,
//                Tile30New::getDesignHelper,
//                Tile3::getDesignHelper,
//                Tile3::getDesignHelper2,
//                TileStar::getDesignHelper1,
//                TileStar::getDesignHelper1b,
//                TileStar::getDesignHelper2,
//                TileStar::getDesignHelper2b,
//                TileStar::getDesignHelper3,
//                TileStar::getDesignHelper3b,
                com.design.islamic.model.rect.Tile1::getDesignHelper,
//                com.design.islamic.model.rect.Tile1New::getDesignHelper,
//                Tile2::getDesignHelper,
//                Tile3::getDesignHelper,
//                Tile4::getDesignHelper,
//                Tile5::getDesignHelper,
//                Tile5::getDesignHelper,
                Tile9::getDesignHelper2,
                Tile9a::getDesignHelperA,
                Tile9a::getDesignHelperB,
                Tile9a::getDesignHelperC,
                Tile9b::getDesignHelperD,
                Tile9c::getDesignHelperE,
                com.design.islamic.model.rect.Tile2::getDesignHelper,
//                Tile2::getDesignHelper,
//                Tile2b::getDesignHelper,
//                Tile3::getDesignHelper,
//                Tile4::getDesignHelper,
//                Tile5::getDesignHelper,
//                Tile6::getDesignHelper,
//                Tile7::getDesignHelper,
//                Tile8::getDesignHelper,
//                Tile9::getDesignHelper1,
//                Tile9::getDesignHelper2,
//                Tile9::getDesignHelper3,
                Tile11::getDesignHelper
//                Tile15::getDesignHelper,
//                Tile18::getDesignHelper,
//                Tile19::getDesignHelper,
//                Tile20::getDesignHelper,
//                Tile21::getDesignHelper,
//                Tile22::getDesignHelper
//                Pair.of("Tile_2", new Tile2(ic)::design1),
//                Pair.of("Tile_3", new Tile3(ic)::design1),
//                Pair.of("Tile_4", new Tile4(ic)::design1),
//                Pair.of("Tile_5", new Tile5(ic)::design1),
//                Pair.of("Tile_6", new Tile6(ic)::design1)
//                Pair.of("Tile_7", new Tile7(ic)::design1)
//                Pair.of("Tile_8", Tile8::getDesignHelper)
//                Pair.of("Tile_8", Tile8::getDesignHelper)
        );

        designs.forEach(d -> {
            DesignHelper designHelper = d.get();
            saveToFile(
                    buildSvg(dim, designHelper.build(() -> ic)),
                    designHelper.getName()
            );
        });

    }


    private String buildSvgFromPayloadSimpleNew(PayloadSimple payload, InitialConditions ic) {

        List<Point2D> gridPoints = Grid.gridFromStart(ic.getCentre(), ic.getR(), payload.getGridConfiguration(), 20);

        return
                gridPoints.stream().map(p -> InitialConditions.of(p, ic.getR())).map(payload::draw).collect(joining());

    }

    private void saveToFile(String svg, String name) {

        try {
            Files.write(Paths.get("./", name + ".html"),
                    Arrays.asList(toHtml(svg))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private String buildBackground(Dimension dim) {

        final String styleBlack = newStyle(BLACK, BLACK, 1, 1, 1);
        List<Point2D> backGroundRect = asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(dim.getWidth(), 0),
                new Point2D.Double(dim.getWidth(), dim.getHeight()),
                new Point2D.Double(0, dim.getHeight()));

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