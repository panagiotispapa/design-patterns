package com.design.arabic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.*;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P4.H;
import static com.design.arabic.model.Rect.ALL_VERTEX_INDEXES;
import static com.design.arabic.model.Rect.Vertex.*;
import static com.design.arabic.model.Rect.diagonals;
import static com.design.arabic.model.Rect.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile7 {

    private static final double KA = 1;
    private static final double KB = H;
    private static final double AC = 2 * KB;
    private static final double AD = AC / (2 * (1 + H));
    private static final double DE = AD;
    private static final double DB = DE / 2;
    private static final double BF = DB;
    private static final double AG = AD / H;

    public final static FinalPointTransition A = fpt(KA, UL);
    public final static FinalPointTransition B = fpt(KB, UP);
    public final static FinalPointTransition C = fpt(KA, UR);
    public final static FinalPointTransition D = A.to(AD, RIGHT);
    public final static FinalPointTransition D2 = A.to(AD, DOWN);
    public final static FinalPointTransition E = C.to(AD, LEFT);
    public final static FinalPointTransition E2 = C.to(AD, DOWN);
    public final static FinalPointTransition F = B.to(BF, DOWN);
    public final static FinalPointTransition G = A.to(AG, DR);
    public final static FinalPointTransition G2 = C.to(AG, DL);


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_07",
                ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();

        Sequence<String> equations = sequence(
                "KB = h",
                "AD = DG = DF = FE = EC",
                "DE = AD",
                "DE = 2 * DB",
                "AD + DE + EC = AC",
                "2*AD + 2*DB = AC",
                "2*AD + 2*DF*h = AC",
                "2*AD + 2*AD*h = AC",
                "AD = AC/2*(1+H)"
        );

        return new DesignHelper(ALL_VERTEX_INDEXES, "rect_tile_07_design")
                .addEquations(equations)
                .addImportantVertexes(Tile7.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, HOR).apply(K),
                        diagonals(1, HOR).apply(K),
                        diagonals(H, VER).apply(K)
                )
                .addCirclesCentral(gray, H)
                .addFullPaths(red, getFullPath())
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                Line.line(D2, G, D, F, E),
                Line.line(F, K),
                Line.line(G, K)
        );
    }

}