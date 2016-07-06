package com.design.islamic.model.hex;

import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;

import java.awt.*;

import static com.design.common.FinalPointTransition.fpt;
import static com.design.islamic.model.Hex.ALL_VERTEX_INDEXES;
import static com.design.islamic.model.Hex.Corner.DOWN;
import static com.design.islamic.model.Hex.Corner.DR_V;
import static com.design.islamic.model.Hex.pt;

public class Tile1 {

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
//        Polygon main = Hex.hex(1, VER);

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_01",
                ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(
                        whiteBold,
                        PointsPath.of(
                                fpt(pt(1, DR_V)),
                                fpt(pt(1, DOWN))
                        )
                )
//                .withPathsFull(
//                        VertexPaths.of(
//                                VertexPath.of(
//                                        instruction(main, DR_V),
//                                        instruction(main, DOWN))
//
//                        ), whiteBold
//                )
                .build();
    }

}
