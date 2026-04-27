package com.auracity;

import com.auracity.engine.*;
import com.auracity.model.buildings.Bank;
import com.auracity.ui.GameCanvas;
import com.auracity.ui.SidebarUI;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private SelectionManager selectionManager;
    private EconomyManager economyManager;
    private Label topStatsLabel;

    @Override
    public void start(Stage primaryStage) {
        // --- 1. INITIALIZE THE BACKEND ENGINE ---
        WorldClock clock = WorldClock.getInstance();
        ResourceManager resources = new ResourceManager();
        PopulationManager popManager = new PopulationManager(resources);
        selectionManager = new SelectionManager();
        
        Bank cityBank = new Bank();
        economyManager = new EconomyManager(popManager, cityBank, resources);
        
        clock.subscribe(popManager);
        clock.subscribe(economyManager);
        clock.subscribe(new HappinessManager(popManager, resources));
        clock.start();

        // --- 2. INITIALIZE THE UI ---
        BorderPane root = new BorderPane();
        
        // Top Bar (Stats)
        topStatsLabel = new Label("Treasury: $0 | Time: Day 0 00:00");
        topStatsLabel.setStyle("-fx-font-size: 16px; -fx-padding: 10px; -fx-background-color: #ecf0f1;");
        HBox topBar = new HBox(topStatsLabel);
        root.setTop(topBar);

        // Center (Canvas)
        // Inside MainApp.java start() method:
        GameCanvas canvas = new GameCanvas(selectionManager, economyManager, popManager);        root.setCenter(canvas);

        // Right (Sidebar)
        SidebarUI sidebar = new SidebarUI(selectionManager, popManager);
        root.setRight(sidebar);

        // --- 3. THE GAME LOOP (60 FPS) ---
        new AnimationTimer() {
            @Override
            public void handle(long now) {
                String stats = String.format("Treasury: $%.0f | Time: Day %d %02d:%02d", 
                    economyManager.getCityTreasury(), 
                    clock.getDay(), 
                    clock.getMinutes() / 60, 
                    clock.getMinutes() % 60);
                topStatsLabel.setText(stats);

                // -> ADD THIS LINE: Keep the Sidebar data live!
                sidebar.updateHUD();

                canvas.render();
            }
        }.start();

        // --- 4. SHOW THE WINDOW ---
        Scene scene = new Scene(root, 1400, 900);
        primaryStage.setTitle("AuraCity: Tycoon Commander");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}