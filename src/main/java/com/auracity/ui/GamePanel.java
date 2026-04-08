package com.auracity.ui;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JFrame {
    public GamePanel() {
        setTitle("AuraCity Tycoon");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        MapPanel mapPanel = new MapPanel();
        HudPanel sidebar = new HudPanel(); // Note: Ensure HudPanel class name matches the actual class name

        add(mapPanel, BorderLayout.CENTER);
        add(sidebar, BorderLayout.EAST);

        setSize(1024, 768);
        setLocationRelativeTo(null);
    }
}
