package com.design.islamic.model.hex;

import com.design.common.Grid;
import com.design.common.Polygon;
import com.design.common.model.Style;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;

import java.awt.*;

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
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_grid_01",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFullFromLines(asList(
                        asList(
                                instruction(main, DR_V),
                                instruction(main, DOWN)
                        )
                ), whiteBold)
                .withGridConf(Grid.Configs.HEX_HOR2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple2() {
        Polygon main = Hex.hex(1, HOR);
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_grid_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFullFromLines(asList(
                        asList(
                                instruction(main, DR_V),
                                instruction(main, DOWN)
                        )
                ), whiteBold)
                .withGridConf(Grid.Configs.HEX_HOR3.getConfiguration())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple3() {
        Polygon main = Hex.hex(1, HOR);
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_grid_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFullFromLines(asList(
                        asList(
                                instruction(main, DR_V),
                                instruction(main, DOWN)
                        )
                ), whiteBold)
                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

}
