package com.design.islamic.model.hex;

import com.design.common.Grid;
import com.design.common.PointsPath;
import com.design.common.model.Style;
import com.design.islamic.model.Hex;
import com.design.islamic.model.Payload;
import com.design.islamic.model.Rect;
import com.design.islamic.model.TileSupplier;
import com.sun.javafx.font.directwrite.RECT;

import java.awt.*;

import static com.design.common.FinalPointTransition.fpt;
import static com.design.common.PointTransition.pt;
import static com.design.islamic.model.Hex.Vertex.*;

public class TileGrid {

    @TileSupplier
    public static Payload getPayloadSimple() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_grid_01",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        PointsPath.of(fpt(pt(1.0, DR_V)), fpt(pt(1.0, DOWN))))

                .withGridConf(Grid.Configs.HEX_HOR2.getConfiguration())
                .build();
    }

    @TileSupplier
    public static Payload getPayloadSimple2() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_grid_02",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        PointsPath.of(fpt(pt(1.0, DR_H)), fpt(pt(1.0, RIGHT))))

                .withGridConf(Grid.Configs.HEX_HOR3.getConfiguration())
                .build();
    }

    @TileSupplier
    public static Payload getPayloadSimple3() {
        Style whiteBold = new Style.Builder(Color.WHITE, 2).build();

        return new Payload.Builder("hex_grid_03",
                Hex.ALL_VERTEX_INDEXES
        )
                .withPathsFull(whiteBold,
                        PointsPath.of(fpt(pt(1.0, DR_H)), fpt(pt(1.0, RIGHT))))

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
                        PointsPath.of(fpt(pt(1.0, Rect.Vertex.UP)), fpt(pt(1.0, Rect.Vertex.RIGHT))))

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
                        PointsPath.of(fpt(pt(1.0, Rect.Vertex.UR)), fpt(pt(1.0, Rect.Vertex.DR))))

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
                        PointsPath.of(fpt(pt(1.0, Rect.Vertex.UP)), fpt(pt(1.0, Rect.Vertex.RIGHT))))

                .withGridConf(Grid.Configs.RECT3.getConfiguration())
                .build();
    }

}
