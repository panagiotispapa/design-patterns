package com.design.islamic;

import com.design.islamic.model.hex.*;
import com.google.common.collect.Maps;
import org.apache.batik.swing.JSVGCanvas;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.design.common.view.SvgFactory.buildSvg;
import static com.design.common.view.SvgFactory.fromSvgDoc;
import static com.design.islamic.model.Centre.newCentre;

public class MainDesign implements ActionListener {

    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;
    private JMenuBar menuBar;

    private final JFrame jFrame;

    private Map<String, Supplier<String>> designs = Maps.newHashMap();

    private Dimension dim;

    public MainDesign(Dimension dim, final double r, JFrame jFrame) {

        this.dim = dim;
        this.jFrame = jFrame;

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        menuBar = new JMenuBar();
        JMenu hexPatternsMenu = new JMenu("Hex Patterns");
        hexPatternsMenu.setMnemonic(KeyEvent.VK_P);

        ActionListener hexListener = e -> menuPressedHex(e.getActionCommand());

        Function<String, JMenuItem> toMenuItemMapper = toMenuItem(hexListener);

        Point2D centre = newCentre(dim.getWidth() / 2.0, dim.getHeight() / 2.0);
        Pair<Point2D, Double> ic = Pair.of(centre, r);

        designs = Maps.newHashMap();
        designs.put("Star 1", new TileStar(ic, TileStar.RATIO_1)::design1);
        designs.put("Star 2", new TileStar(ic, TileStar.RATIO_1)::design2);
        designs.put("Star 3", new TileStar(ic, TileStar.RATIO_1)::design3);
        designs.put("Tile 2", new Tile2(ic)::design1);
        designs.put("Tile 3", new Tile3(ic)::design1);
        designs.put("Tile 4", new Tile4(ic)::design1);
        designs.put("Tile 5", new Tile5(ic)::design1);
        designs.put("Tile 6", new Tile6(ic)::design1);
        designs.put("Tile 11", new Tile11(ic)::design1);

        designs.entrySet().stream().map(Map.Entry::getKey).map(toMenuItemMapper).forEach(hexPatternsMenu::add);

//        for (HexPattern hexPattern : HexPattern.values()) {
//            hexPatternsMenu.add(newMenuItem(hexPattern.getDescription(), hexListener));
//        }

        menuBar.add(hexPatternsMenu);

        jsvgCanvas.setSize(dim);
        jPanel.setSize(dim);

        refreshCanvas(designs.get("Star 1"));

    }

    private void menuPressedHex(String command) {
        System.out.println(command);

        jFrame.setTitle(command);

        refreshCanvas(designs.get(command));

    }

    private Function<String, JMenuItem> toMenuItem(ActionListener actionListener) {
        return text -> {
            JMenuItem item = new JMenuItem(text);
            item.addActionListener(actionListener);
            return item;
        };

    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private void refreshCanvas(Supplier<String> hexDesign) {
        System.out.println("in refreshCanvas");
        jsvgCanvas.removeAll();
//        removeChildren(jsvgCanvas);

        jsvgCanvas.setSVGDocument(fromSvgDoc(buildSvg(dim, hexDesign.get())));
    }

    public JPanel getComponent() {
        return jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());

        jFrame.setTitle(e.getActionCommand());

        refreshCanvas(designs.get(e.getActionCommand()));

    }

    public static void main(String[] args) {
//        Dimension dim = new Dimension(1024 + 2 * 64, 768);
        Dimension dim = new Dimension(1024 + 2 * 128 + 32, 768);

        JFrame frame = new JFrame();
        frame.setTitle("Islamic patterns");
        frame.setSize(dim);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();

        MainDesign main = new MainDesign(dim, 300, frame);
        contentPane.add(main.getComponent());
        frame.setJMenuBar(main.getMenuBar());

        frame.setVisible(true);

        frame.invalidate();

    }
}


