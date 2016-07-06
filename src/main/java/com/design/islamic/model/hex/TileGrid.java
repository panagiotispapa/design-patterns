package com.design.islamic.model.hex;

import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.Hex;
import com.design.islamic.model.PayloadSimple;
import com.design.islamic.model.TileSupplier;

import java.awt.*;

import static com.design.common.FinalPointTransition.fpt;
import static com.design.islamic.model.Hex.Corner.*;
import static com.design.islamic.model.Hex.pt;

public class TileGrid {

    @TileSupplier
    public static PayloadSimple getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_grid_01",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold,
                        PointsPath.of(fpt(pt(1.0, DR_V)), fpt(pt(1.0, DOWN))))

                .withGridConf(Grid.Configs.HEX_HOR2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple2() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_grid_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold,
                        PointsPath.of(fpt(pt(1.0, DR_H)), fpt(pt(1.0, RIGHT))))

                .withGridConf(Grid.Configs.HEX_HOR3.getConfiguration())
                .build();
    }

    @TileSupplier
    public static PayloadSimple getPayloadSimple3() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new PayloadSimple.Builder("hex_grid_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsNewFull(whiteBold,
                        PointsPath.of(fpt(pt(1.0, DR_H)), fpt(pt(1.0, RIGHT))))

                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

}
