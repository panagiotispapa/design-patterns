package com.design.islamic.model.tiles;


import com.design.islamic.model.Centre;
import com.design.islamic.model.tiles.shapes.Shapes;
import com.design.islamic.model.tiles.svg.Style;
import org.apache.batik.dom.svg.SVGDOMImplementation;
import org.apache.batik.swing.JSVGCanvas;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.svg.SVGDocument;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static org.apache.batik.dom.svg.SVGDOMImplementation.SVG_NAMESPACE_URI;

public class TestBed2 {


    private JSVGCanvas jsvgCanvas;
    private JPanel jPanel;

    public TestBed2() {

        jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jsvgCanvas = new JSVGCanvas();

        jPanel.add(jsvgCanvas);

        DOMImplementation impl = SVGDOMImplementation.getDOMImplementation();
        Document doc = impl.createDocument(SVG_NAMESPACE_URI, "svg", null);

// Get the root element (the 'svg' element).
        Element svgRoot = doc.getDocumentElement();

// Set the width and height attributes on the root 'svg' element.
        svgRoot.setAttributeNS(null, "width", "400");
        svgRoot.setAttributeNS(null, "height", "450");

// Create the rectangle.
        Element rectangle = doc.createElementNS(SVG_NAMESPACE_URI, "rect");
        rectangle.setAttributeNS(null, "x", "10");
        rectangle.setAttributeNS(null, "y", "20");
        rectangle.setAttributeNS(null, "width", "100");
        rectangle.setAttributeNS(null, "height", "50");
        rectangle.setAttributeNS(null, "fill", "red");

        svgRoot.appendChild(rectangle);

        svgRoot.appendChild(Shapes.newCircle(new Centre(50, 50), 20, new Style("yellow", "green", 2, 1, 1)).buildFrom(doc));


        jsvgCanvas.setSVGDocument((SVGDocument) doc);

    }


    public static void removeChildren(Node node) {
        while (node.hasChildNodes()){
            node.removeChild(node.getFirstChild());
        }

    }
    public JPanel getComponent() {
        return jPanel;
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
        contentPane.add(new TestBed2().getComponent());
        frame.setVisible(true);

        frame.invalidate();


    }
}
