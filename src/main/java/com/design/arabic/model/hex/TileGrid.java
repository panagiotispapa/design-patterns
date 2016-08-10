package com.design.arabic.model.hex;

import com.design.common.Grid;
import com.design.common.Line;
import com.design.common.model.Style;
import com.design.arabic.model.Hex;
import com.design.arabic.model.Payload;
import com.design.arabic.model.Rect;
import com.design.arabic.model.TileSupplier;

import java.awt.*;

import static com.design.common.FinalPointTransition.fpt;
import static com.design.arabic.model.Hex.Vertex.*;

public class TileGrid {

//    @TileSupplier
    public static Payload getHexHor() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_grid_hor",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        Line.line(fpt(1, DR_V), fpt(1, DOWN)))

                .withGridConf(Grid.Configs.HEX_HOR.getConfiguration())
                .build();
    }

    @TileSupplier
    public static Payload getHexHor2() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_grid_hor2",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        Line.line(fpt(1, DR_V), fpt(1, DOWN)))

                .withGridConf(Grid.Configs.HEX_HOR2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static Payload getHexHor3() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_grid_hor3",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        Line.line(fpt(1, DR_H), fpt(1, RIGHT)))

                .withGridConf(Grid.Configs.HEX_HOR3.getConfiguration())
                .build();
    }

//    @TileSupplier
    public static Payload getHexVer() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_grid_ver",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        Line.line(fpt(1, DR_H), fpt(1, RIGHT)))

                .withGridConf(Grid.Configs.HEX_VER.getConfiguration())
                .build();
    }

    @TileSupplier
    public static Payload getHexVer2() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_grid_ver2",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        Line.line(fpt(1, DR_H), fpt(1, RIGHT)))

                .withGridConf(Grid.Configs.HEX_VER2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static Payload getPayloadRect1() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_grid_01",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        Line.line(fpt(1, Rect.Vertex.UP), fpt(1, Rect.Vertex.RIGHT)))

                .withGridConf(Grid.Configs.RECT.getConfiguration())
                .build();
    }

    @TileSupplier
    public static Payload getPayloadRect2() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_grid_02",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        Line.line(fpt(1, Rect.Vertex.UR), fpt(1, Rect.Vertex.DR)))

                .withGridConf(Grid.Configs.RECT2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static Payload getPayloadRect3() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("rect_grid_03",
                Rect.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        Line.line(fpt(1, Rect.Vertex.UP), fpt(1, Rect.Vertex.RIGHT)))

                .withGridConf(Grid.Configs.RECT3.getConfiguration())
                .build();
    }

}
