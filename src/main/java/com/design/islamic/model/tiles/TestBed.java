package com.design.islamic.model.tiles;


import com.design.islamic.model.Centre;
import com.design.islamic.model.tiles.svg.Style;
import com.design.islamic.model.tiles.svg.SvgFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.*;

import static com.design.islamic.model.PolygonTools.*;
import static com.design.islamic.model.PolygonTools.calculateNewCellCentres;
import static java.awt.Color.BLACK;
import static java.lang.Math.PI;

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

            Set<Point2D> newCentres = calculateNewCellCentres(calculateNewCellCentres(centre, r), r, 16) ;

            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("islamicTest.html"), "utf-8"));


            writer.write(SvgFactory.buildSVG(1200, 1024, buildHexPattern0(newCentres, r, new Style("yellow", "green", 2, 1, 1))));

//            writer.write("<!DOCTYPE html>" + "\n");
//            writer.write("<html>" + "\n");
//            writer.write("<svg height=\"1024\" width=\"1000\">" + "\n");

//        drawHexPattern0((Graphics2D) g, centre, r);
//        drawHexPattern1((Graphics2D) g, centre, r);
//        drawPoints((Graphics2D) g, myPoints);
//        List<Point2D> newCentres = calculateNewCellCentres(centre, r);
//        Set<Point2D> newCentres = calculateNewCellCentres(calculateNewCellCentres(centre, r), r) ;
//        Set<Point2D> newCentres = calculateNewCellCentres(calculateNewCellCentres(calculateNewCellCentres(centre, r), r), r) ;

//        System.out.println("size " + Sets.newHashSet(newCentres).size() );

//        System.out.println("size 2 " + newCentres.size());

//        drawPoints((Graphics2D) g, newCentres);
//        drawPoints((Graphics2D) g, calculateNewCellCentres(centre, r) );


        Set<Point2D> edges = edgesFromCentres(newCentres, r);
//        System.out.println("ed " + edges.size());
//        System.out.println("ed " + Sets.newHashSet(edges).size());
//        for (Point2D edge : edges) {
//            System.out.println(edge);
//        }

//        drawPoints((Graphics2D) g, edges);

//        drawHexPattern0((Graphics2D) g, newCentres, r);
//        drawHexPattern1((Graphics2D) g, newCentres, r);

//        drawHexPattern0(writer, newCentres, r, new Style("yellow", "green", 2, 1, 1));
//        drawHexPattern1(writer, newCentres, r, new Style("yellow", "green", 1, 0, 1));


//        drawHexPattern0(newCentres, r, new Style("yellow", "green", 1, 0, 0.4));
//        drawHexPattern1(newCentres, r, new Style("yellow", "green", 1, 0, 1));


//            writer.write("</svg>" + "\n");
//            writer.write("</body>" + "\n");
//            writer.write("</html>" + "\n");

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
