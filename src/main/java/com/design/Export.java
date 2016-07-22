package com.design;

import com.design.common.DesignHelper;
import com.design.common.Grid;
import com.design.common.InitialConditions;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Payload;
import com.design.islamic.model.TileSupplier;
import com.design.islamic.model.rect.Tile3;
import com.design.islamic.model.rect.Tile4;
import com.design.islamic.model.rect.Tile5;
import com.design.islamic.model.rect.Tile6;
import com.google.common.collect.ImmutableMap;
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
import java.util.Map;
import java.util.Set;

import static com.design.common.view.SvgFactory.*;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;

public class Export {

    public Export() {

    }

    private static Map<Payload.Size, Double> sizeToRNew = ImmutableMap.of(
            Payload.Size.SMALL, 100.0,
            Payload.Size.MEDIUM, 150.0,
            Payload.Size.LARGE, 200.0
    );

    private static Reflections forPackage(String pkg) {
        return new Reflections(new ConfigurationBuilder()
                .filterInputsBy(new FilterBuilder().includePackage(pkg))
                .setUrls(ClasspathHelper.forPackage(pkg))
                .setScanners(new TypeAnnotationsScanner(), new MethodAnnotationsScanner())
        );
    }

    private static void exportPayloads() {
        Set<Method> methods = forPackage("com.design")
                .getMethodsAnnotatedWith(TileSupplier.class);

//        System.out.println(methods.size());

        methods.stream().map(m -> Export.invokeMethod(m, Payload.class))
                .forEach(Export::export);
    }

    private static void exportDesigns() {
        Set<Method> methods = forPackage("com.design")
                .getMethodsAnnotatedWith(DesignSupplier.class);

        methods.stream().map(m -> Export.invokeMethod(m, DesignHelper.class))
                .forEach(Export::export);
    }

    private static void export(Payload payload) {

        Double R = sizeToRNew.get(payload.getSize());
        Dimension dim = new Dimension((int) (15 * R), (int) (10 * R));
        InitialConditions ic = InitialConditions.of(new Point2D.Double(0, 0), R);

        System.out.println(payload.getName());
        saveToFile(buildSvg(dim, buildBackground(dim) + buildSvgFromPayloadSimple(payload, ic)), payload.getName());

    }


    private static String buildSvgFromPayloadSimple(Payload payload, InitialConditions ic) {
        List<Point2D> gridPoints = Grid.gridFromStart(ic.getCentre(), ic.getR(), payload.getGridConfiguration(), 17);

        return
                gridPoints.stream().map(p -> InitialConditions.of(p, ic.getR())).map(payload::draw).collect(joining());

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

        System.out.println(designHelper.getName());

        buildSvg(dim, designHelper.build(() -> ic));

        saveToFile(buildSvg(dim, designHelper.build(() -> ic)), designHelper.getName());

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
//
//        Export.export(Tile6.getDesignHelper());
//        Export.export(Tile6.getPayloadSimple());

    }
}
