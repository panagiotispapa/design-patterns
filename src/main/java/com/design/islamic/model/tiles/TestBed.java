package com.design.islamic.model.tiles;

import com.design.common.PolygonTools;
import com.design.common.view.SvgFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Color.BLACK;
import static java.lang.System.currentTimeMillis;

public class TestBed extends JComponent {

    private final Point2D centre;
    private final int r;

    public TestBed(Point2D centre, int r) {
        this.centre = centre;
        this.r = r;

        List<Point2D> hex = PolygonTools.newHexagon(new Point2D.Double(0, 0), 30);

        String style = SvgFactory.newStyle("black", 2, 1);

        int limit = 20000;
        List<List<Point2D>> shapes = new ArrayList<List<Point2D>>(limit);

        for (int i = 0; i < limit; i++) {
            shapes.add(hex);
        }

        long start = currentTimeMillis();
//        SvgFactory.drawPolylines(shapes, style);

        System.out.println((currentTimeMillis() - start) / 1000);

        start = currentTimeMillis();

//        System.out.println(SvgFactory.drawPolylines(shapes, style));
        SvgFactory.drawPolylines(shapes, style);

        System.out.println((currentTimeMillis() - start) / 1000);

        this.setVisible(true);
    }

    @Override
    public void paintComponent(Graphics g) {

        g.setColor(BLACK);

        Writer writer = null;

        try {

//            Set<Point2D> newCentres = calculateNewCellCentres(calculateNewCellCentres(centre, r), r, 16) ;

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("islamicTest.html"), "utf-8"));

//            writer.write(SvgFactory.buildSVG(1200, 1024, buildHexPattern0(newCentres, r, new Style("yellow", "green", 2, 1, 1))));

//            writer.write("<!DOCTYPE html>" + "\n");
//            writer.write("<html>" + "\n");
//            writer.write("<svg height=\"1024\" width=\"1000\">" + "\n");

//        Set<Point2D> edges = edgesFromCentres(newCentres, r);

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    public Point2D getCentre() {
        return centre;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setTitle("Polygon");
        frame.setSize(1024, 768);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        Container contentPane = frame.getContentPane();

        TestBed testObject = new TestBed(new Point2D.Double(frame.getWidth() / 2, frame.getHeight() / 2), 50);

        contentPane.add(testObject);

        frame.setVisible(true);

        frame.invalidate();

    }

}
