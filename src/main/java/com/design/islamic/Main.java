package com.design.islamic;

import com.design.islamic.model.HexPattern;
import com.design.islamic.model.PatternManager;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import static com.design.common.view.SvgFactory.fromXMLBuilder;

public class Main implements ActionListener {

    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;
    private JMenuBar menuBar;
    private JMenu patternsMenu;

    private PatternManager manager;

    public Main(Dimension dim, final double r) {

        manager = new PatternManager(r, dim);

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        menuBar = new JMenuBar();
        patternsMenu = new JMenu("Hex Patterns");
        patternsMenu.setMnemonic(KeyEvent.VK_P);

        for (HexPattern hexPattern : HexPattern.values()) {
            patternsMenu.add(newMenuItem(hexPattern.getDescription(), this));
        }

        menuBar.add(patternsMenu);

        jsvgCanvas.setSize(dim);
        jPanel.setSize(dim);


        refreshCanvas(HexPattern.ONE);

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
        jsvgCanvas.setSVGDocument(fromXMLBuilder(manager.getSvg(hexPattern)));
    }

    public JPanel getComponent() {
        return jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());

        refreshCanvas(HexPattern.fromDescription(e.getActionCommand()));

    }

    public static void main(String[] args) {



        Dimension dim = new Dimension(1024+2*64, 768);

        JFrame frame = new JFrame();
        frame.setTitle("Islamic patterns");
        frame.setSize(dim);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();

        Main main = new Main(dim, 128);
        contentPane.add(main.getComponent());
        frame.setJMenuBar(main.getMenuBar());

        frame.setVisible(true);


        frame.invalidate();

    }
}


