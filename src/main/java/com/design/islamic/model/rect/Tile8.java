package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.islamic.model.Rect.Vertex.*;
import static com.design.islamic.model.Rect.diagonals;
import static com.design.islamic.model.Rect.perimeter;
import static java.util.Arrays.asList;

public class Tile8 {

    private static final double KA = 1.0;
    private static final double KB = H;
    private static final double AC = 2 * KB;
    private static final double AD = AC / (2 * (1 + H));
    private static final double DB = AD * H;
    private static final double BF = DB;
    private static final double AG = AD;

    public final static FinalPointTransition A = fpt(pt(KA, UL));
    public final static FinalPointTransition B = fpt(pt(KB, UP));
    public final static FinalPointTransition B2 = fpt(pt(KB, LEFT));
    public final static FinalPointTransition C = fpt(pt(KA, UR));
    public final static FinalPointTransition D = A.append(pt(AD, RIGHT));
    public final static FinalPointTransition D2 = A.append(pt(AD, DOWN));
    public final static FinalPointTransition E = C.append(pt(AD, LEFT));
    public final static FinalPointTransition E2 = C.append(pt(AD, DOWN));
    public final static FinalPointTransition F = B.append(pt(BF, DOWN));
    public final static FinalPointTransition F2 = B2.append(pt(BF, RIGHT));
    public final static FinalPointTransition G = A.append(pt(AG, DR));
    public final static FinalPointTransition G2 = C.append(pt(AG, DL));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_08",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();

        List<String> equations = Arrays.asList(
                "KB = h",
                "AD = DG = DF = FE = EC",
                "DB = DF * h",
                "DE = 2 * DB",
                "AD + DE + EC = AC",
                "2*AD + 2*DB = AC",
                "2*AD + 2*DF*h = AC",
                "2*AD + 2*AD*h = AC",
                "AD = AC/2*(1+H)"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_08_design")
                .addEquations(equations)
                .addImportantVertexes(Tile8.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K)
//                        perimeter(H, VER).apply(K),
//                        perimeter(KB, HOR).apply(K)
                )
                .addCirclesCentral(asList(
                        H
                ), gray)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(A, D2, F2, G, A, D, F, G)
        );
    }

}