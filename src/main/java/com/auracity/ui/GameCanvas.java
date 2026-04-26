package com.auracity.ui;

import com.auracity.engine.SelectionManager;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class GameCanvas extends Canvas {
    private final SelectionManager selectionManager;
    private final int TILE_SIZE = 64; // Assuming 64x64 Kenney assets
    private final int GRID_WIDTH = 20;
    private final int GRID_HEIGHT = 15;

    public GameCanvas(SelectionManager selectionManager) {
        super(1280, 960); // Set canvas size
        this.selectionManager = selectionManager;

        // The Mouse Listener for grid placement
        setOnMousePressed(e -> {
            int gridX = (int) (e.getX() / TILE_SIZE);
            int gridY = (int) (e.getY() / TILE_SIZE);

            if (selectionManager.isBuildMode()) {
                System.out.println("Placing " + selectionManager.getBuildModeType().getLabel() + " at [" + gridX + ", " + gridY + "]");
                // TODO: Call EconomyManager to check money, then place building!
            }
        });
    }

    // Called 60 times a second by the AnimationTimer
    public void render() {
        GraphicsContext gc = getGraphicsContext2D();

        // 1. Clear the screen (paint dirt/grass background)
        gc.setFill(Color.web("#7cb342")); // Nice grass green
        gc.fillRect(0, 0, getWidth(), getHeight());

        // 2. Draw the Grid Lines (For debugging and placement)
        gc.setStroke(Color.web("#558b2f"));
        gc.setLineWidth(1);
        for (int x = 0; x < getWidth(); x += TILE_SIZE) {
            gc.strokeLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += TILE_SIZE) {
            gc.strokeLine(0, y, getWidth(), y);
        }

        // 3. TODO: Loop through your city grid and draw the actual Building and Citizen sprites here!
    }
}