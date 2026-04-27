package com.auracity.ui;

import javax.swing.*;
import java.awt.*;

import com.auracity.models.buildings.Building;
import com.auracity.models.buildings.BuildingType;

public class InfoPanel extends JPanel {

    private JLabel title;
    private JLabel idLabel;
    private JLabel typeLabel;
    private JLabel integrityLabel;

    public InfoPanel() {

        setPreferredSize(new Dimension(220, 0));
        setBackground(new Color(30, 30, 35));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Title
        title = new JLabel("No Selection");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 16));

        // Labels
        idLabel = createLabel();
        typeLabel = createLabel();
        integrityLabel = createLabel();

        add(title);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(idLabel);
        add(typeLabel);
        add(integrityLabel);
    }

    private JLabel createLabel() {
        JLabel label = new JLabel("");
        label.setForeground(Color.LIGHT_GRAY);
        label.setFont(new Font("Consolas", Font.PLAIN, 13));
        return label;
    }

    // ---------------- UPDATE PANEL ----------------

    public void showBuilding(Building b) {

        if (b == null) {
            title.setText("No Selection");
            idLabel.setText("");
            typeLabel.setText("");
            integrityLabel.setText("");
            return;
        }

        title.setText("Building Info");

        idLabel.setText("ID: " + b.getId());

        BuildingType type = b.getType();
        typeLabel.setText("Type: " + type.getLabel());

        integrityLabel.setText(
            "Integrity: " + String.format("%.1f", b.getIntegrity())
        );
    }
}
