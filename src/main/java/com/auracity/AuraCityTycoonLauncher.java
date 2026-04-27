package com.auracity;

import com.auracity.engine.ResourceManager;
import com.auracity.models.buildings.PowerPlant;
import com.auracity.models.buildings.FoodFarms;
import com.auracity.ui.MapPanel;
import com.auracity.ui.InfoPanel;
import com.auracity.ui.RenderableBuilding;

import javax.swing.*;
import java.awt.*;

public class AuraCityTycoonLauncher {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {

        // ---------------- ENGINE ----------------
        ResourceManager resources = new ResourceManager();

        System.out.println(
            "AuraCity Engine Initialized. Starting Funds: " +
            resources.getResourceSurplus("Money")
        );

        // ---------------- WINDOW ----------------
        JFrame frame = new JFrame("AuraCity - Command Center (Pre-Alpha)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // ---------------- UI PANELS ----------------
        InfoPanel infoPanel = new InfoPanel();
        MapPanel mapPanel = new MapPanel(infoPanel);

        mapPanel.setPreferredSize(new Dimension(800, 800));

        frame.add(mapPanel, BorderLayout.CENTER);
        frame.add(infoPanel, BorderLayout.EAST);

        // ---------------- BUILDINGS (REAL OBJECTS) ----------------
        PowerPlant plant = new PowerPlant(1, resources);
        FoodFarms farm = new FoodFarms(2, resources);

        RenderableBuilding r1 =
            new RenderableBuilding(plant, 5, 5);

        RenderableBuilding r2 =
            new RenderableBuilding(farm, 10, 8);

        mapPanel.addBuilding(r1);
        mapPanel.addBuilding(r2);

        // ---------------- DISPLAY ----------------
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // ---------------- RENDER LOOP ----------------
        Timer renderLoop = new Timer(16, e -> {
            mapPanel.refreshFrame();
        });

        renderLoop.start();
    }
}
