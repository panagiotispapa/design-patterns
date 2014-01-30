package com.design.islamic;

import com.design.islamic.model.PatternManager;
import com.design.islamic.model.tiles.svg.SvgFactory;
import org.apache.batik.swing.JSVGCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Main {

    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;

    private PatternManager manager;

    public Main(int width, int height, final double r) {

        manager = new PatternManager(r, width, height);

        jPanel = new JPanel();
        jPanel.setLayout(new BorderLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add("Center", jsvgCanvas);

        jsvgCanvas.setSize(width, height);
        jPanel.setSize(width, height);

        jPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
//                System.out.println(e.getKeyChar());
                if (e.getKeyChar() == 'm') {
                    manager.goToNext();
                    refreshCanvas();

                } else if (e.getKeyChar() == 'n') {
                    manager.goToPrevious();
                    refreshCanvas();

                }

            }
        });

        jPanel.setFocusable(true);

        refreshCanvas();

    }

    private void refreshCanvas() {
        System.out.println("in refreshCanvas");
        jsvgCanvas.removeAll();
//        removeChildren(jsvgCanvas);
        jsvgCanvas.setSVGDocument(SvgFactory.fromXMLBuilder(manager.getCurrent()));
    }

    public JPanel getComponent() {
        return jPanel;
    }

    public static void main(String[] args) {

        JFrame frame = new JFrame();
        frame.setTitle("Islamic patterns");
        frame.setSize(1024, 768);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();

        Main main = new Main(1024, 768, 50);
        contentPane.add(main.getComponent());
        frame.setVisible(true);

        frame.invalidate();

    }

}


