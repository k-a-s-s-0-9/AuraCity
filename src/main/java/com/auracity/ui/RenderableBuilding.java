package com.auracity.ui;

import com.auracity.models.buildings.Building;

public class RenderableBuilding {

    public final Building building;

    public final int gridX;
    public final int gridY;

    public final int width;
    public final int height;

    public RenderableBuilding(
        Building building,
        int gridX,
        int gridY
    ) {

        this(building, gridX, gridY, 2, 2);
    }

    public RenderableBuilding(
        Building building,
        int gridX,
        int gridY,
        int width,
        int height
    ) {

        this.building = building;
        this.gridX = gridX;
        this.gridY = gridY;
        this.width = width;
        this.height = height;
    }

    // ---------------- HIT DETECTION ----------------
    public boolean contains(int x, int y) {

        return x >= gridX &&
               x < gridX + width &&
               y >= gridY &&
               y < gridY + height;
    }

    // ---------------- PIXEL HELPERS ----------------
    public int getPixelX(int tileSize) {
        return gridX * tileSize;
    }

    public int getPixelY(int tileSize) {
        return gridY * tileSize;
    }

    public int getPixelWidth(int tileSize) {
        return width * tileSize;
    }

    public int getPixelHeight(int tileSize) {
        return height * tileSize;
    }
}
