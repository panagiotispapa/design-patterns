package com.design.islamic.model.tiles;


import com.design.islamic.model.Centre;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.*;

import static java.awt.Color.BLACK;

public class TestBed extends JComponent implements Tile {

    private final Point2D centre;
    private final int r;


    public TestBed(Point2D centre, int r) {
        this.centre = centre;
        this.r = r;

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

        TestBed testObject = new TestBed(new Centre(frame.getWidth()/2, frame.getHeight()/2), 50);

        contentPane.add(testObject);

        frame.setVisible(true);

        frame.invalidate();

    }

}
