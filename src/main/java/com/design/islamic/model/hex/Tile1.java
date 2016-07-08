package com.design.islamic.model.hex;

import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.Payload;
import com.design.islamic.model.TileSupplier;

import java.awt.*;

import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.ALL_VERTEX_INDEXES;
import static com.design.islamic.model.Hex.Vertex.DOWN;
import static com.design.islamic.model.Hex.Vertex.DR_V;

public class Tile1 {

    @TileSupplier
    public static Payload getPayloadSimple() {
//        Polygon main = Hex.hex(1, VER);

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new Payload.Builder("hex_tile_01",
                ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        whiteBold,
                        PointsPath.of(
                                fpt(pt(1, DR_V)),
                                fpt(pt(1, DOWN))
                        )
                )
                .build();
    }

}
