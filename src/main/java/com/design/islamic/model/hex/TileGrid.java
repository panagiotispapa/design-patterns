package com.design.islamic.model.hex;

import com.design.common.Polygon;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;
import com.design.islamic.model.tiles.Grid;

import static com.design.common.Polygon.Type.HOR;
import static com.design.common.Polygon.Type.VER;
import static com.design.islamic.model.Hex.Corner.DOWN;
import static com.design.islamic.model.Hex.Corner.DR_V;
import static com.design.islamic.model.Hex.instruction;
import static java.util.Arrays.asList;

public class TileGrid {

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Polygon main = Hex.hex(1, VER);

        return new PayloadSimple.Builder("hex_grid_01",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(main, DR_V),
                                instruction(main, DOWN)
                        )
                ))
                .withGridConf(Grid.Configs.HEX_HOR2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple2() {
        Polygon main = Hex.hex(1, HOR);

        return new PayloadSimple.Builder("hex_grid_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(main, DR_V),
                                instruction(main, DOWN)
                        )
                ))
                .withGridConf(Grid.Configs.HEX_HOR3.getConfiguration())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple3() {
        Polygon main = Hex.hex(1, HOR);

        return new PayloadSimple.Builder("hex_grid_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withLines(asList(
                        asList(
                                instruction(main, DR_V),
                                instruction(main, DOWN)
                        )
                ))
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

}
