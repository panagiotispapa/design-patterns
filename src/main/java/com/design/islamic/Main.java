package com.design.islamic;

import com.design.islamic.model.HexPattern;
import com.design.islamic.model.PatternManager;
import com.design.islamic.model.RectPattern;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.design.common.view.SvgFactory.fromSvgDoc;

public class Main implements ActionListener {

    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;
    private JMenuBar menuBar;

    private PatternManager manager;

    private final JFrame jFrame;

    public Main(Dimension dim, final double r, JFrame jFrame) {

        this.jFrame = jFrame;
        manager = new PatternManager(r, dim);

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        menuBar = new JMenuBar();
        JMenu hexPatternsMenu = new JMenu("Hex Patterns");
        hexPatternsMenu.setMnemonic(KeyEvent.VK_P);

        JMenu rectPatternsMenu = new JMenu("Rect Patterns");
        rectPatternsMenu.setMnemonic(KeyEvent.VK_R);

        ActionListener hexListener = e -> menuPressedHex(e.getActionCommand());

        ActionListener rectListener = e -> menuPressedRect(e.getActionCommand());

        for (HexPattern hexPattern : HexPattern.values()) {
            hexPatternsMenu.add(newMenuItem(hexPattern.getDescription(), hexListener));
        }

        for (RectPattern pattern : RectPattern.values()) {
            rectPatternsMenu.add(newMenuItem(pattern.getDescription(), rectListener));
        }

        menuBar.add(hexPatternsMenu);
        menuBar.add(rectPatternsMenu);

        jsvgCanvas.setSize(dim);
        jPanel.setSize(dim);

        refreshCanvas(HexPattern.ONE);

    }

    private void menuPressedHex(String command) {
        System.out.println(command);

        jFrame.setTitle(command);

        refreshCanvas(HexPattern.fromDescription(command));

    }

    private void menuPressedRect(String command) {
        System.out.println(command);

        jFrame.setTitle(command);

        refreshCanvas(RectPattern.fromDescription(command));

    }

    private JMenuItem newMenuItem(String text, ActionListener actionListener) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(actionListener);
        return item;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private void refreshCanvas(HexPattern hexPattern) {
        System.out.println("in refreshCanvas");
        jsvgCanvas.removeAll();
//        removeChildren(jsvgCanvas);

        jsvgCanvas.setSVGDocument(fromSvgDoc(manager.getSvg(hexPattern)));
    }

    private void refreshCanvas(RectPattern pattern) {
        System.out.println("in refreshCanvas rect");
        jsvgCanvas.removeAll();
//        removeChildren(jsvgCanvas);

        jsvgCanvas.setSVGDocument(fromSvgDoc(manager.getSvg(pattern)));
    }

    public JPanel getComponent() {
        return jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());

        jFrame.setTitle(e.getActionCommand());

        refreshCanvas(HexPattern.fromDescription(e.getActionCommand()));

    }

    public static void main(String[] args) {

        Dimension dim = new Dimension(1024+2*128 + 32, 768);

        JFrame frame = new JFrame();
        frame.setTitle("Islamic patterns");
        frame.setSize(dim);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();

        Main main = new Main(dim, 128, frame);
        contentPane.add(main.getComponent());
        frame.setJMenuBar(main.getMenuBar());

        frame.setVisible(true);

        frame.invalidate();

    }
}


