package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import org.apache.commons.lang3.tuple.Pair;

import static com.design.islamic.model.Hex.Vertex.ONE;
import static com.design.islamic.model.Hex.Vertex.TWO;
import static java.util.Arrays.asList;

public class Tile1 {

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, Polygon.Type.VER);

        return new PayloadSimple("hex_tile_01",
                asList(
                        asList(
                                Pair.of(main, ONE),
                                Pair.of(main, TWO)
                        )
                ),
                Hex.ALL_VERTEX_INDEXES
        );
    }

}
