package com.auracity.ui;

import javax.swing.*;
import java.awt.*;
import com.auracity.model.buildings.BuildingType;
import com.auracity.engine.SelectionManager;

public class HudPanel extends JPanel {
    public HudPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder("Build Menu"));

        // Add buttons for each building type
        addButton("Residential", BuildingType.TOWN_HOUSE);
        addButton("Power Plant", BuildingType.POWER_PLANT);
        addButton("Farm", BuildingType.FARM);
        addButton("Water Tower", BuildingType.WATER_TOWER);
    }

    private void addButton(String label, BuildingType type) {
        JButton btn = new JButton(label);
        btn.setMaximumSize(new Dimension(150, 40));
        btn.addActionListener(e -> SelectionManager.setSelection(type));
        add(btn);
        add(Box.createRigidArea(new Dimension(0, 5))); // Spacer
    }
}