package com.design.islamic;

import com.design.common.DesignHelper;
import com.design.common.view.SvgFactory;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import com.design.islamic.model.tiles.Grid;
import org.apache.commons.lang3.tuple.Pair;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static com.design.common.view.SvgFactory.*;
import static java.util.Arrays.asList;

public class Export {

    public Export() {

    }

    private static Reflections forPackage(String pkg) {
        return new Reflections(new ConfigurationBuilder()
                .filterInputsBy(new FilterBuilder().includePackage(pkg))
                .setUrls(ClasspathHelper.forPackage(pkg))
                .setScanners(new TypeAnnotationsScanner(), new MethodAnnotationsScanner())
        );
    }

    public static void exportPayloads() {
        Set<Method> methods = forPackage("com.design.islamic.model")
                .getMethodsAnnotatedWith(TileSupplier.class);

        System.out.println(methods.size());

        methods.stream().map(m -> Export.invokeMethod(m, PayloadSimple.class))
                .forEach(Export::export);
    }

    public static void exportDesigns() {
        Set<Method> methods = forPackage("com.design.islamic.model")
                .getMethodsAnnotatedWith(DesignSupplier.class);

        System.out.println(methods.size());

        methods.stream().map(m -> Export.invokeMethod(m, DesignHelper.class))
                .forEach(Export::export);
    }

    private static void export(PayloadSimple payload) {

        Dimension dim = new Dimension(1024 + 2 * 128 + 32, 768);
        Pair<Point2D, Double> ic = Pair.of(new Point2D.Double(0, 0), 100.0);

        System.out.println(payload.getName());
        saveToFile(buildSvg(dim, buildBackground(dim) + buildSvgFromPayloadSimple(payload, ic)), payload.getName());

    }

    private static String buildSvgFromPayloadSimple(PayloadSimple payload, Pair<Point2D, Double> ic) {
        List<Point2D> gridPoints = Grid.gridFromStart(ic.getLeft(), ic.getRight(), payload.getGridConfiguration(), 17);

        return SvgFactory.drawOnGrid(payload.toLines(ic), gridPoints, newStyle(WHITE, 2, 1));

    }

    private static String buildBackground(Dimension dim) {

        final String styleBlack = newStyle(BLACK, BLACK, 1, 1, 1);
        List<Point2D> backGroundRect = asList(
                new Point2D.Double(0, 0),
                new Point2D.Double(dim.getWidth(), 0),
                new Point2D.Double(dim.getWidth(), dim.getHeight()),
                new Point2D.Double(0, dim.getHeight()));

        return drawPolygon(backGroundRect, styleBlack);
        //shapes.append();

    }

    private static void export(DesignHelper designHelper) {

        Dimension dim = new Dimension(1024 + 2 * 128 + 32, 768);

        Point2D centre = new Point2D.Double(dim.getWidth() / 2.0, dim.getHeight() / 2.0);

        Pair<Point2D, Double> ic = Pair.of(centre, 300.0);

        buildSvg(dim, designHelper.build(ic));

        System.out.println(designHelper.getName());

        saveToFile(buildSvg(dim, designHelper.build(ic)), designHelper.getName());

    }

    private static <T> T invokeMethod(Method method, Class<T> klass) {
        try {
            return klass.cast(method.invoke(null));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return null;

    }

    private static void saveToFile(String svg, String name) {

        try {
            String path = "./";
            Files.write(Paths.get(path, name + ".html"),
                    Arrays.asList(toHtml(svg))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Export.exportDesigns();
        Export.exportPayloads();
    }
}
