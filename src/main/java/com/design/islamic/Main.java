package com.design.islamic;

import com.design.islamic.model.Pattern;
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

    public Main(int width, int height, final double r) {

        manager = new PatternManager(r, width, height);

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        menuBar = new JMenuBar();
        patternsMenu = new JMenu("Patterns");
        patternsMenu.setMnemonic(KeyEvent.VK_P);

        for (Pattern pattern : Pattern.values()) {
            patternsMenu.add(newMenuItem(pattern.getDescription(), this));
        }

        menuBar.add(patternsMenu);

        jsvgCanvas.setSize(width, height);
        jPanel.setSize(width, height);


        refreshCanvas(Pattern.ONE);

    }

    private JMenuItem newMenuItem(String text, ActionListener actionListener) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(actionListener);
        return item;
    }

    public JMenuBar getMenuBar() {
        return menuBar;
    }

    private void refreshCanvas(Pattern pattern) {
        System.out.println("in refreshCanvas");
        jsvgCanvas.removeAll();
//        removeChildren(jsvgCanvas);
        jsvgCanvas.setSVGDocument(fromXMLBuilder(manager.getSvg(pattern)));
    }

    public JPanel getComponent() {
        return jPanel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println(e.getActionCommand());

        refreshCanvas(Pattern.fromDescription(e.getActionCommand()));

    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("Islamic patterns");
        frame.setSize(1024+2*64, 768);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();

        Main main = new Main(1024+2*64, 768, 70);
        contentPane.add(main.getComponent());
        frame.setJMenuBar(main.getMenuBar());

        frame.setVisible(true);


        frame.invalidate();

    }
}


