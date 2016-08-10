package com.design.arabic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.DesignSupplier;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;
import com.googlecode.totallylazy.Sequence;

import java.awt.*;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.Line.line;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.arabic.model.Hex.Vertex.*;
import static com.design.arabic.model.Hex.diagonals;
import static com.design.arabic.model.Hex.perimeter;
import static com.googlecode.totallylazy.Sequences.sequence;

public class Tile31 {

//    http://2.bp.blogspot.com/-V0YODRN3S3w/TqRrNjXvxUI/AAAAAAAAAWg/oMyn1868lRY/s1600/islamic+screen+1.jpg

    public static double KA = 1;
    public static double KB = H;
    public static double KC = KA / 6;
    public static double KD = KC / H;

    public final static FinalPointTransition A = fpt(1, UP);
    public final static FinalPointTransition B = fpt(KB, RIGHT);
    public final static FinalPointTransition C = fpt(KC, UP);
    public final static FinalPointTransition D = fpt(KD, UR_H);
    public final static FinalPointTransition E = A.to(KD, RIGHT);
    public final static FinalPointTransition F = E.to(3 * KD, DL_H);
    public final static FinalPointTransition G = F.to(KD, RIGHT);
    public final static FinalPointTransition I = G.to(KD, UR_H);
    public final static FinalPointTransition I2 = I.to(KD, DR_H);
    public final static FinalPointTransition I3 = I2.to(KD, RIGHT);
    public final static FinalPointTransition M = A.to(KD, DR_H);
    public final static FinalPointTransition M2 = M.to(3 * KD, LEFT);
    public final static FinalPointTransition M3 = M2.to(KD, DR_H);
    public final static FinalPointTransition M4 = F.to(KD, LEFT);
    public final static FinalPointTransition M5 = M4.to(KD, DR_H);
    public final static FinalPointTransition D2 = D.to(3 * KD, DR_H);
    public final static FinalPointTransition D3 = D.to(3 * KD, RIGHT);

    @TileSupplier
    public static Payload getPayloadSimple() {

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_tile_31",
                Hex.ALL_VERTEX_INDEXES
        )
                .withSize(Payload.Size.MEDIUM)
                .withPathsFull(whiteBold, getFullPath())
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style blue = new Style.Builder(Color.BLUE, 1).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style green = new Style.Builder(Color.GREEN, 1).build();
        Style red = new Style.Builder(Color.RED, 2).build();

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_31_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KD)
                .withGridSize(16)
                .addEquations(sequence(
                        "KC = KA / 6",
                        "KD = KC / H"
                ))
                .addImportantVertexes(Tile31.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1, VER).apply(K),
                        perimeter(KD, HOR).apply(K),
                        diagonals(KC, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15)
                ;

    }

    private static Sequence<Line> getFullPath() {
        return sequence(
                line(E, F, G, I, I2, I3, E),
                line(M, M2, M3),
                line(M4, M5, F),
                line(D2, D, D3),
                line(fpt(KC, UP), fpt(KC, DOWN)),
                line(fpt(KC, UL_V), fpt(KC, DR_V)),
                line(fpt(KC, UR_V), fpt(KC, DL_V)),
                line(A.to(KC, UP), A.to(KC, DOWN)),
                line(A.to(KC, UL_V), A.to(KC, DR_V)),
                line(A.to(KC, UR_V), A.to(KC, DL_V))
        );
    }

}