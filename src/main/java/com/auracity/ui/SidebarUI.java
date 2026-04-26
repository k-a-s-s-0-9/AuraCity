package com.auracity.ui;

import com.auracity.engine.SelectionManager;
import com.auracity.model.buildings.BuildingType;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class SidebarUI extends VBox {
    private final SelectionManager selectionManager;

    public SidebarUI(SelectionManager selectionManager) {
        this.selectionManager = selectionManager;
        
        setPadding(new Insets(15));
        setSpacing(10);
        setStyle("-fx-background-color: #2c3e50; -fx-text-fill: white;");

        Label title = new Label("Build Menu");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: white; -fx-font-weight: bold;");
        getChildren().add(title);

        // Dynamically create a button for every building type in your Enum
        for (BuildingType type : BuildingType.values()) {
            Button btn = new Button(type.getLabel() + " ($" + type.getCost() + ")");
            btn.setMaxWidth(Double.MAX_VALUE);
            btn.setStyle("-fx-background-color: #34495e; -fx-text-fill: white;");
            
            btn.setOnAction(e -> {
                selectionManager.setBuildMode(type);
                System.out.println("Armed and ready to build: " + type.getLabel());
            });
            
            getChildren().add(btn);
        }
    }
}