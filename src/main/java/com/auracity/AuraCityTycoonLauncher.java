package com.auracity;

import com.auracity.engine.ResourceManager;
import com.auracity.ui.MapPanel;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import java.awt.Dimension;

public class AuraCityTycoonLauncher {

    public static void main(String[] args) {
        // Swing UI creation must always happen on the Event Dispatch Thread (EDT)
        SwingUtilities.invokeLater(() -> {
            createAndShowGUI();
        });
    }

    private static void createAndShowGUI() {
        // 1. Initialize the Core Engine (Invisible in this phase, but ready)
        ResourceManager resourceManager = new ResourceManager();
        System.out.println("AuraCity Engine Initialized. Starting Funds: " + 
                           resourceManager.getResourceSurplus("Money"));

        // 2. Set up the Window
        JFrame frame = new JFrame("AuraCity - Command Center (Pre-Alpha)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false); // Lock it for now to keep grid math simple

        // 3. Initialize and add the Canvas
        MapPanel mapPanel = new MapPanel();
        mapPanel.setPreferredSize(new Dimension(800, 800)); // 50 tiles * 16px (or just a fixed size for now)
        frame.add(mapPanel);

        // 4. Pack and Display
        frame.pack();
        frame.setLocationRelativeTo(null); // Center on screen
        frame.setVisible(true);

        // 5. Start the Render Loop (Targeting ~60 FPS)
        // 1000ms / 60 = ~16ms per frame
        Timer renderLoop = new Timer(16, e -> {
            mapPanel.refreshFrame();
        });
        renderLoop.start();
    }
}