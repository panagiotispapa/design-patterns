package com.design.islamic.model.rect;

import com.design.common.DesignHelper;
import com.design.common.FinalPointTransition;
import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.RatioHelper.P12;
import com.design.common.RatioHelper.P6;
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
import static java.lang.Math.PI;
import static java.util.Arrays.asList;

public class Tile16 {

    private static final double KA = 1.0;
    private static final double KB = H;
    private static final double KC = KB * P6.H;
    private static final double CB = KB - KC;
    private static final double CD = KB * P6.P;
    private static final double BE = CD * (KB / KC);
    private static final double AG = BE;
    private static final double EG = 2 * BE - KB;
    private static final double GI = EG / 2.0;
    private static final double BI = BE - GI;
    private static final double KJ = KB * P6.H;
    private static final double JD = KB - KJ;
    private static final double KM = KJ - JD;
    private static final double KN = KM * P6.H;
    private static final double MN = KM * P6.P;
    private static final double BM = KB - KM;
    private static final double ML = BM / P12.H;
    private static final double BL = ML * P12.P;
    private static final double MQ = (KM + KB) / P12.H;
    private static final double B2Q = MQ * P12.P;
    private static final double BN = KB - KN;
    private static final double FO = BN;
    private static final double DS = CB / P6.P;
    private static final double IS = DS * P6.H;
    private static final double GE = 2 * GI;
    private static final double MC = KC - KM;
    private static final double MV = MC / P6.H;
    private static final double CV = 0.5 * MV * P6.P;
    private static final double TE = GE;
//    private static final double TX = TE * P6.P;
//    private static final double EX = TE * P6.H;
//    private static final double KE = KB / P6.H;
    private static final double AE = KB - BE;
    private static final double AZ = AE * H;
    private static final double ZE = AZ;
    private static final double KZ = KA - AZ;
    private static final double AO4 = KB - 2 * FO;

    private static final double phi = Math.atan(AO4 / AE);
    private static final double ZY = ZE * Math.tan(0.5 * (2.0 * PI) / 4.0 - phi);
    private static final double AY = AZ - ZY;


    private static final double AL = KB - 2 * FO;
    private static final double KW = (AL / H) / 2.0;
    private static final double KW2 = KW / H;

//    private static final double KI = KA - KB;


    public final static FinalPointTransition A = fpt(pt(KA, UR));
    public final static FinalPointTransition A2 = fpt(pt(KA, UL));
    public final static FinalPointTransition A3 = fpt(pt(KA, DL));
    public final static FinalPointTransition A4 = fpt(pt(KA, DR));
    public final static FinalPointTransition B = fpt(pt(KB, RIGHT));
    //    public static final FinalPointTransition O3 = B.append(pt(2 * FO, UP));
    public final static FinalPointTransition B2 = fpt(pt(KB, LEFT));
    public final static FinalPointTransition B3 = fpt(pt(KB, DOWN));
    public final static FinalPointTransition C = fpt(pt(KC, RIGHT));
    public final static FinalPointTransition C2 = fpt(pt(KC, UP));
    public final static FinalPointTransition C3 = fpt(pt(KC * P6.H, LEFT), pt(KC * P6.P, UP));
    public final static FinalPointTransition C4 = fpt(pt(KC * P6.H, DOWN), pt(KC * P6.P, RIGHT));
    public final static FinalPointTransition C5 = fpt(pt(KC * P6.H, LEFT), pt(KC * P6.P, DOWN));
    public final static FinalPointTransition D = C.append(pt(CD, UP));
    public final static FinalPointTransition D2 = fpt(pt(KC, UP), pt(CD, RIGHT));
    public final static FinalPointTransition I2 = D2.append(pt(CB, UP));
    public final static FinalPointTransition E = B.append(pt(BE, UP));
    public final static FinalPointTransition F = fpt(pt(KB, UP));
    public final static FinalPointTransition E2 = F.append(pt(BE, RIGHT));
    public final static FinalPointTransition G = A.append(pt(AG, DOWN));
    public final static FinalPointTransition I = G.append(pt(GI, UP));
    public final static FinalPointTransition G2 = A2.append(pt(AG, DOWN));
    public final static FinalPointTransition G3 = A2.append(pt(AG, RIGHT));
    public final static FinalPointTransition G4 = A3.append(pt(AG, RIGHT));
    public final static FinalPointTransition J = fpt(pt(KJ * P6.H, RIGHT), pt(KJ * P6.P, UP));
    public final static FinalPointTransition N = fpt(pt(KN, RIGHT));
    public final static FinalPointTransition N2 = fpt(pt(KN, LEFT));
    public final static FinalPointTransition N3 = fpt(pt(KN, UP));
    public final static FinalPointTransition M = N.append(pt(MN, UP));
    public final static FinalPointTransition M2 = N2.append(pt(MN, DOWN));
    public final static FinalPointTransition M3 = N3.append(pt(MN, LEFT));
    public final static FinalPointTransition M4 = N2.append(pt(MN, UP));
    public final static FinalPointTransition P = B.append(pt(BL, UP));
    public final static FinalPointTransition P2 = F.append(pt(BL, RIGHT));
    public final static FinalPointTransition Q = B2.append(pt(B2Q, DOWN));
    public final static FinalPointTransition Q2 = B3.append(pt(B2Q, LEFT));
    public final static FinalPointTransition O = F.append(pt(FO, LEFT));
    public final static FinalPointTransition O2 = F.append(pt(2 * FO, LEFT));
    public final static FinalPointTransition O3 = B.append(pt(2 * FO, UP));
    public final static FinalPointTransition L = F.append(pt(2 * FO, RIGHT));
    public final static FinalPointTransition O5 = B3.append(pt(2 * FO, LEFT));
    public final static FinalPointTransition S = I.append(pt(IS, DOWN));
    public final static FinalPointTransition S2 = I2.append(pt(IS, LEFT));
    public final static FinalPointTransition T = D.append(pt(GE, UP));
    public final static FinalPointTransition T2 = D2.append(pt(GE, RIGHT));
    public final static FinalPointTransition V = C.append(pt(CV, DOWN));
    public final static FinalPointTransition V2 = C.append(pt(CV, UP));
    public final static FinalPointTransition V3 = C2.append(pt(CV, RIGHT));
    public final static FinalPointTransition V4 = C3.append(pt(CV * P6.H, DOWN), pt(CV * P6.P, LEFT));
    public final static FinalPointTransition V5 = C3.append(pt(CV * P6.H, UP), pt(CV * P6.P, RIGHT));
    public final static FinalPointTransition V6 = C4.append(pt(CV * P6.H, LEFT), pt(CV * P6.P, DOWN));
    public final static FinalPointTransition V7 = C5.append(pt(CV * P6.H, DOWN), pt(CV * P6.P, RIGHT));
    public final static FinalPointTransition Z = A.append(pt(AZ, DL));
    public final static FinalPointTransition Y = A.append(pt(AY, DL));
    public final static FinalPointTransition W = fpt(pt(KW, DR));
    public final static FinalPointTransition W2 = fpt(pt(KW2, UP));
    public final static FinalPointTransition W5 = fpt(pt(KW2, LEFT));
    public final static FinalPointTransition W3 = fpt(pt(KW2 * P6.H, RIGHT), pt(KW2 * P6.P, DOWN));
    public final static FinalPointTransition W4 = fpt(pt(KW2 * P6.H, UP), pt(KW2 * P6.P, LEFT));


    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_tile_16",
                Rect.ALL_VERTEX_INDEXES)
                .withPathsFull(whiteBold, getFullPath())
                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .withSize(Payload.Size.MEDIUM)
                .build();
    }

    @DesignSupplier
    public static DesignHelper getDesignHelper() {
        Style red = new Style.Builder(Color.RED, 2).build();
        Style gray = new Style.Builder(Color.GRAY, 1).build();
        Style blue = new Style.Builder(Color.BLUE, 1).build();

        List<String> equations = Arrays.asList(
                "KB = h",
                "KC = KB * P6.H",
                "CD = KB * P6.P",
                "BE = CD * (KB / KC) = AG",
                "EG + AE + BE = KB",
                "EG + AG - EG + BE - EG = KB",
                "EG = 2 * BE - KB",
                "GI = EG / 2",
                "KJ = KB * P6.H",
                "KL = KJ * P6.H",
                "JL = KJ * P6.P",
                "JD = KB - KJ",
                "KM = KJ -JD",
                "KN = KM * P6.H",
                "MN = KM * P6.P",
                "AL = KB - 2 * FO",
                "KW = (AL / H) / 2.0"
        );

        return new DesignHelper(Hex.ALL_VERTEX_INDEXES, "rect_tile_16_design")
                .addEquations(equations)
                .addImportantVertexes(Tile16.class)
                .addSinglePathsLines(
                        gray,
                        perimeter(1.0, HOR).apply(K),
//                        diagonals(1.0, HOR).apply(K),
                        diagonals(H, VER).apply(K),
                        perimeter(H, VER).apply(K)
//                        perimeter(KB, HOR).apply(K)
                )
                .addSinglePathsLines(
                        blue,
                        PointsPath.of(C, D),
                        PointsPath.of(I, D),
                        PointsPath.of(fpt(pt(KJ * P6.H, RIGHT)), J),
                        PointsPath.of(M, N),
                        PointsPath.of(M3, O),
                        PointsPath.of(K, A),
                        PointsPath.of(E, E2),
                        PointsPath.of(L, O3),
                        PointsPath.of(K, W),
                        PointsPath.of(fpt(pt(KW2 * P6.H, RIGHT)), W3),
                        PointsPath.of(fpt(pt(KC * P6.H, LEFT)), C3)

                )
                .addFullPaths(
                        gray,
                        PointsPath.of(K, E),
                        PointsPath.of(K, E2),
                        PointsPath.of(F, G),
                        PointsPath.of(F, G2),
                        PointsPath.of(A.append(pt(CB, LEFT)), A4.append(pt(CB, LEFT))),
                        PointsPath.of(Q, P),
                        PointsPath.of(Q2, P2),
                        PointsPath.of(O2, O3),
                        PointsPath.of(E2, O3),
                        PointsPath.of(E, L),
                        PointsPath.of(E2, S),
                        PointsPath.of(E, S2),
                        PointsPath.of(T2, L),
                        PointsPath.of(T, O3),
                        PointsPath.of(O5, O3)
                )
                .addCirclesCentral(asList(
                        H,
                        KM
                ), gray)
                .addFullPaths(red, getFullPath())
                .withFontSize(14)
                ;

    }

    private static List<PointsPath> getFullPath() {
        return asList(
                PointsPath.of(B3, V6, W3, V2, T, O3, Y, L, T2, V3, W4, V4, B2),
                PointsPath.of(G3, V5, W5, V7, G4)
        );
    }

}