package com.auracity.ui;

import com.auracity.engine.PopulationManager;
import com.auracity.engine.SelectionManager;
import com.auracity.model.buildings.Building;
import com.auracity.model.buildings.BuildingType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SidebarUI extends VBox {
    private final SelectionManager selectionManager;
    private final PopulationManager popManager;
    
    // HUD Elements
    private final VBox inspectionPanel;
    private final Label inspectNameLabel;
    private final Label inspectIdLabel;
    private final Label inspectIntegrityLabel;
    private final Label inspectOccupantsLabel;

    public SidebarUI(SelectionManager selectionManager, PopulationManager popManager) {
        this.selectionManager = selectionManager;
        this.popManager = popManager;
        
        setPadding(new Insets(15));
        setSpacing(10);
        setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white;");

        Label title = new Label("Build Menu");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        getChildren().add(title);

        // --- 1. THE FIXED LOOP (No Cancel Buttons in here!) ---
        for (BuildingType type : BuildingType.values()) {
            Button btn = new Button(type.getLabel() + " ($" + type.getCost() + ")");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
            btn.setOnAction(e -> selectionManager.setBuildMode(type));
            getChildren().add(btn);
        }

        // --- 2. THE MIGRANT BUTTON ---
        Button migrantBtn = new Button("🚢 Accept Migrants (+$1000 ea)");
        migrantBtn.setMaxWidth(Double.MAX_VALUE);
        migrantBtn.setStyle("-fx-background-color: #f39c12; -fx-text-fill: white; -fx-font-weight: bold;");
        // Pass $1000 starting cash to every migrant
        migrantBtn.setOnAction(e -> popManager.triggerMigrants(1000.0));
        
        VBox.setMargin(migrantBtn, new Insets(15, 0, 0, 0)); // Add some space above it
        getChildren().add(migrantBtn);

        // --- 3. THE INSPECTION HUD ---
        inspectionPanel = new VBox(5);
        inspectionPanel.setStyle("-fx-background-color: #34495e; -fx-padding: 10; -fx-border-color: #ecf0f1; -fx-border-width: 1;");
        
        Label hudTitle = new Label("--- INSPECTION DATA ---");
        hudTitle.setStyle("-fx-font-weight: bold; -fx-text-fill: #3498db;");
        
        inspectNameLabel = new Label("Target: None");
        inspectNameLabel.setStyle("-fx-text-fill: white;");
        inspectIdLabel = new Label("ID: --");
        inspectIdLabel.setStyle("-fx-text-fill: white;");
        inspectIntegrityLabel = new Label("Integrity: --");
        inspectIntegrityLabel.setStyle("-fx-text-fill: white;");
        inspectOccupantsLabel = new Label("Occupants: --");
        inspectOccupantsLabel.setStyle("-fx-text-fill: white;");

        // The Cancel Button is safely inside the HUD panel!
        Button cancelBtn = new Button("🛑 Cancel / Deselect");
        cancelBtn.setMaxWidth(Double.MAX_VALUE);
        cancelBtn.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; -fx-font-weight: bold;");
        cancelBtn.setOnAction(e -> {
            selectionManager.setBuildMode(null);
            selectionManager.selectEntity(null); 
        });

        inspectionPanel.getChildren().addAll(hudTitle, inspectNameLabel, inspectIdLabel, inspectIntegrityLabel, inspectOccupantsLabel, cancelBtn);
        VBox.setMargin(inspectionPanel, new Insets(20, 0, 0, 0));
        getChildren().add(inspectionPanel);
    }

    // Called by the Engine 60 times a second to update the UI text
    public void updateHUD() {
        if (!selectionManager.isBuildMode() && selectionManager.getSelectedEntity() instanceof Building) {
            Building b = (Building) selectionManager.getSelectedEntity();
            inspectNameLabel.setText("Target: " + b.getType().getLabel());
            inspectIdLabel.setText("ID: " + b.getId().substring(0, 8) + "...");
            inspectIntegrityLabel.setText("Integrity: " + b.getStructuralIntegrity() + "%");
            inspectOccupantsLabel.setText("Occupants: " + b.getCurrentOccupants() + " / " + b.getType().getMaxOccupants());
            inspectionPanel.setVisible(true);
        } else {
            inspectionPanel.setVisible(false); // Hide the HUD if nothing is selected
        }
    }
}