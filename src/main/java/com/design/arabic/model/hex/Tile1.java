package com.design.arabic.model.hex;

import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.Payload;
import com.design.arabic.model.TileSupplier;

import java.awt.*;

import static com.design.common.FinalPointTransition.fpt;
import static com.design.arabic.model.Hex.ALL_VERTEX_INDEXES;
import static com.design.arabic.model.Hex.Vertex.DOWN;
import static com.design.arabic.model.Hex.Vertex.DR_V;

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
                        Line.line(
                                fpt(1, DR_V),
                                fpt(1, DOWN)
                        )
                )
                .build();
    }

}
