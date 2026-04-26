package com.auracity.ui;

import com.auracity.engine.EconomyManager;
import com.auracity.engine.PopulationManager;
import com.auracity.engine.SelectionManager;
import com.auracity.model.buildings.*;
import com.auracity.model.agent.Citizen;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.HashMap;
import java.util.Map;

public class GameCanvas extends Canvas {
    private final SelectionManager selectionManager;
    private final EconomyManager economyManager;
    private final PopulationManager popManager;

    private final int TILE_SIZE = 64; // Kenney asset size
    private final int GRID_WIDTH = 20;
    private final int GRID_HEIGHT = 15;

    // The City Data Structure
    private final Building[][] grid;
    
    // Asset Cache (Prevents memory leaks from reloading images 60x a second)
    private final Map<String, Image> imageCache;

    public GameCanvas(SelectionManager selectionManager, EconomyManager economyManager, PopulationManager popManager) {
        super(1280, 960); 
        this.selectionManager = selectionManager;
        this.economyManager = economyManager;
        this.popManager = popManager;

        this.grid = new Building[GRID_WIDTH][GRID_HEIGHT];
        this.imageCache = new HashMap<>();

        setOnMousePressed(e -> {
            int gridX = (int) (e.getX() / TILE_SIZE);
            int gridY = (int) (e.getY() / TILE_SIZE);

            if (gridX < 0 || gridX >= GRID_WIDTH || gridY < 0 || gridY >= GRID_HEIGHT) return;

            if (selectionManager.isBuildMode()) {
                BuildingType typeToBuild = selectionManager.getBuildModeType();

                if (grid[gridX][gridY] != null) {
                    System.out.println("Tile already occupied!");
                    return;
                }

                if (economyManager.getCityTreasury() >= typeToBuild.getCost()) {
                    economyManager.deductFunds(typeToBuild.getCost());
                    Building newBuilding = createBuildingInstance(typeToBuild);
                    
                    newBuilding.setLocation(gridX, gridY); 
                    grid[gridX][gridY] = newBuilding;

                    if (newBuilding instanceof Housing) {
                        popManager.addHome((Housing) newBuilding);
                    }
                    System.out.println("Constructed: " + typeToBuild.getLabel() + " at [" + gridX + ", " + gridY + "]");
                } else {
                    System.out.println("Insufficient funds! Need $" + typeToBuild.getCost());
                }
            } else {
                Building clickedBuilding = grid[gridX][gridY];
                if (clickedBuilding != null) {
                    selectionManager.selectEntity(clickedBuilding);
                    System.out.println("Inspecting: " + clickedBuilding.getType().getLabel());
                }
            }
        });
    }

    // Factory method to map Enums to your physical classes
    private Building createBuildingInstance(BuildingType type) {
        switch (type) {
            case TOWN_HOUSE: return new Housing();
            case POWER_PLANT: return new PowerPlant();
            case FARM: return new FoodFarms();
            case GRANARY: return new Granary();
            case WATER_FARM: return new WaterFarm();
            case WATER_TOWER: return new WaterBuffer();
            case BANK: return new Bank();
            default: throw new IllegalArgumentException("Unknown building type");
        }
    }

    // Helper to load and cache images
    private Image getImage(String path) {
        if (!imageCache.containsKey(path)) {
            // Assumes images are in src/main/resources/assets/buildings/
            try {
                Image img = new Image(getClass().getResourceAsStream(path));
                imageCache.put(path, img);
            } catch (Exception e) {
                System.out.println("Could not load image: " + path);
                return null;
            }
        }
        return imageCache.get(path);
    }

    public void render() {
        GraphicsContext gc = getGraphicsContext2D();

        // 1. Draw Background
        gc.setFill(Color.web("#7cb342")); 
        gc.fillRect(0, 0, getWidth(), getHeight());

        // 2. Draw Grid Lines
        gc.setStroke(Color.web("#558b2f"));
        gc.setLineWidth(1);
        for (int x = 0; x < getWidth(); x += TILE_SIZE) {
            gc.strokeLine(x, 0, x, getHeight());
        }
        for (int y = 0; y < getHeight(); y += TILE_SIZE) {
            gc.strokeLine(0, y, getWidth(), y);
        }

        // 3. Draw Buildings
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                Building b = grid[x][y];
                if (b != null) {
                    Image sprite = getImage(b.getType().getSpritePath());
                    if (sprite != null) {
                        gc.drawImage(sprite, x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    } else {
                        // Fallback: draw a colored box if the image is missing
                        gc.setFill(Color.DARKGRAY);
                        gc.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    }
                }
            }
        }

        // 4. Draw Citizens
        gc.setFill(Color.web("#e74c3c")); // A nice bright red/orange for visibility
        for (Citizen c : popManager.getCitizens()) {
    
            // Calculate movement for this frame
            c.updateMovement(); 
    
            // Draw the citizen as a circle (radius 10)
            // We subtract 5 to ensure the circle's center is exactly at c.getX()
            gc.fillOval(c.getX() - 5, c.getY() - 5, 10, 10); 
        }
    }
}