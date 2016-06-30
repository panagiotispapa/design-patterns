package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.common.Polygon.VertexPaths;
import com.design.common.model.Style;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;

import java.awt.*;

import static com.design.common.Polygon.Type.VER;
import static com.design.islamic.model.Hex.Corner.DOWN;
import static com.design.islamic.model.Hex.Corner.DR_V;
import static com.design.islamic.model.Hex.instruction;

public class Tile1 {

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);

        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();
        return new PayloadSimple.Builder("hex_tile_01",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(
                        VertexPaths.of(
                                Polygon.VertexPath.of(
                                        instruction(main, DR_V),
                                        instruction(main, DOWN))

                        ), whiteBold
                )
                .build();
    }

}
