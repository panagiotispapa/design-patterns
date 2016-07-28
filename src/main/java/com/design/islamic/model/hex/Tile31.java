package com.design.islamic.model.hex;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.DesignSupplier;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Payload;
import com.design.islamic.model.TileSupplier;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.design.common.FinalPointTransition.K;
import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.common.RatioHelper.P6.H;
import static com.design.islamic.model.Hex.Vertex.*;
import static com.design.islamic.model.Hex.diagonals;
import static com.design.islamic.model.Hex.perimeter;
import static java.util.Arrays.asList;

public class Tile31 {

//    http://2.bp.blogspot.com/-V0YODRN3S3w/TqRrNjXvxUI/AAAAAAAAAWg/oMyn1868lRY/s1600/islamic+screen+1.jpg

    public static double KA = 1.0;
    public static double KB = H;
    public static double KC = KA / 6.0;
    public static double KD = KC / H;

    public final static FinalPointTransition A = fpt(pt(1.0, UP));
    public final static FinalPointTransition B = fpt(pt(KB, RIGHT));
    public final static FinalPointTransition C = fpt(pt(KC, UP));
    public final static FinalPointTransition D = fpt(pt(KD, UR_H));
    public final static FinalPointTransition E = A.append(pt(KD, RIGHT));
    public final static FinalPointTransition F = E.append(pt(3 * KD, DL_H));
    public final static FinalPointTransition G = F.append(pt(KD, RIGHT));
    public final static FinalPointTransition I = G.append(pt(KD, UR_H));
    public final static FinalPointTransition I2 = I.append(pt(KD, DR_H));
    public final static FinalPointTransition I3 = I2.append(pt(KD, RIGHT));
    public final static FinalPointTransition M = A.append(pt(KD, DR_H));
    public final static FinalPointTransition M2 = M.append(pt(3 * KD, LEFT));
    public final static FinalPointTransition M3 = M2.append(pt(KD, DR_H));
    public final static FinalPointTransition M4 = F.append(pt(KD, LEFT));
    public final static FinalPointTransition M5 = M4.append(pt(KD, DR_H));
    public final static FinalPointTransition D2 = D.append(pt(3 * KD, DR_H));
    public final static FinalPointTransition D3 = D.append(pt(3 * KD, RIGHT));

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

        List<String> equations = Arrays.asList(
                "KC = KA / 6.0",
                "KD = KC / H"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "hex_tile_31_design")
                .withGrid(Grid.Configs.HEX_HOR.getConfiguration())
                .withGridRatio(KD)
                .withGridSize(16)
                .addEquations(equations)
                .addImportantVertexes(Tile31.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, VER).apply(K),
                        perimeter(KD, HOR).apply(K),
                        diagonals(KC, VER).apply(K)
                )
                .addFullPaths(red, getFullPath())
                .withFontSize(15)
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(E, F, G, I, I2, I3, E),
                PointsPath.of(M, M2, M3),
                PointsPath.of(M4, M5, F),
                PointsPath.of(D2, D, D3),
                PointsPath.of(fpt(pt(KC, UP)), fpt(pt(KC, DOWN))),
                PointsPath.of(fpt(pt(KC, UL_V)), fpt(pt(KC, DR_V))),
                PointsPath.of(fpt(pt(KC, UR_V)), fpt(pt(KC, DL_V))),
                PointsPath.of(A.append(pt(KC, UP)), A.append(pt(KC, DOWN))),
                PointsPath.of(A.append(pt(KC, UL_V)), A.append(pt(KC, DR_V))),
                PointsPath.of(A.append(pt(KC, UR_V)), A.append(pt(KC, DL_V)))
        );
    }

}