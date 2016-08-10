package com.design;

import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Payload;
import com.design.arabic.model.SvgPayload;
import com.design.arabic.model.TileSupplier;
import com.design.common.CanvasPoint;
import com.design.common.DesignHelper;
import org.apache.commons.lang3.tuple.Pair;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.awt.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;

import static com.design.common.CanvasPoint.point;
import static com.design.common.view.SvgFactory.buildSvg;
import static com.design.common.view.SvgFactory.toHtml;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

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

    private static void exportPayloads() {
        Set<Method> methods = forPackage("com.design")
                .getMethodsAnnotatedWith(TileSupplier.class);

//        System.out.println(methods.size());

        List<SvgPayload> payloads = methods.parallelStream().map(m -> Export.invokeMethod(m, Payload.class)).map(Payload::toSvgPayload).collect(toList());
        System.out.println("A total of " + payloads.size());

        payloads.parallelStream()
                .forEach(Export::export);


        saveToFile(payloads.stream()
                .filter(p -> !p.getName().contains("grid"))
                .filter(p -> !p.getName().contains("deco"))
                .sorted(SvgPayload::sortByName)
//                .map(SvgPayload::getPayload)
                .map(p -> format("<p>%s<p>%s", p.getName(), p.getPayload()))
                .collect(joining()), "all");
    }

    public static void export(SvgPayload svgPayload) {
        saveToFile(svgPayload.getPayload(), svgPayload.getName());
    }


    private static void exportDesigns() {
        Set<Method> methods = forPackage("com.design")
                .getMethodsAnnotatedWith(DesignSupplier.class);

        methods.parallelStream().map(m -> Export.invokeMethod(m, DesignHelper.class))
                .forEach(Export::export);
    }


    public static void export(DesignHelper designHelper) {

        Dimension dim = new Dimension(1024 + 2 * 128 + 32, 768);

        CanvasPoint centre = point(dim.getWidth() / 2.0, dim.getHeight() / 2.0);

        Pair<CanvasPoint, Double> ic = Pair.of(centre, 300.0);

//        System.out.println(designHelper.getName());

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
                    asList(toHtml(svg))
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Export.exportDesigns();
        Export.exportPayloads();

//        https://thumbs.dreamstime.com/z/vector-seamless-pattern-geometric-texture-arabic-islamic-art-48545611.jpg
//        http://cache1.asset-cache.net/xc/468533522.jpg?v=2&c=IWSAsset&k=2&d=nhiGm00rkVdHlEl67mSGS_tpqJ_23He3KpaItMU-w_WiKBT_FYk-6-Jnwb2LSq900
//        http://i.istockimg.com/file_thumbview_approve/61420962/3/stock-illustration-61420962-abstract-seamless-geometric-islamic-wallpaper-pattern.jpg
//        http://www.turkotek.com/misc_00130/kufic_files/jornal-of-the-society-of-arts-1905_2.jpg
//        https://s-media-cache-ak0.pinimg.com/736x/50/6a/fd/506afd013423012a58283ae8fbabf5aa.jpg
//        http://thumb101.shutterstock.com/photos/display_pic_with_logo/1118624/276480473.jpg
//        https://image.yayimages.com/512/photo/vector-seamless-black-and-white-geometric-trianfgle-zigzag-shape-islamic-pattern-150750312.jpg
//        http://l7.alamy.com/zooms/7fa9b6c7592342009d2e4e5b80bbac1e/vector-seamless-black-and-white-hexagonal-geometric-star-islamic-pattern-fb2pkg.jpg
//        http://predecessorstopioneers.weebly.com/uploads/4/5/5/8/45580021/7214015_orig.gif
//        https://s-media-cache-ak0.pinimg.com/736x/12/a1/18/12a11839f121e6d22b036c07d58e7e29.jpg
//        http://images.patterninislamicart.com/image.php?image=/ia/mah_108b.jpg&width=650&height=600
//        https://www.davidmus.dk/files/8/9/2100/moenster10.png

    }
}
