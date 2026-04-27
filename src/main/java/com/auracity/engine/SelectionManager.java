package com.auracity.engine;

import com.auracity.model.buildings.BuildingType;
import com.auracity.model.agent.Citizen;


public class SelectionManager {
    private Object hoveredEntity;      // What the mouse is over
    private Object selectedEntity;     // What is clicked (Inspection)
    private BuildingType buildModeType; // What we are trying to place

    public void setBuildMode(BuildingType type) {
        this.buildModeType = type;
        this.selectedEntity = null; // Clear inspection when building
    }

    public void selectEntity(Object obj) {
        this.selectedEntity = obj;
        this.buildModeType = null; // Exit build mode when inspecting
    }
    

    public BuildingType getBuildModeType() { return buildModeType; }
    public Object getSelectedEntity() { return selectedEntity; }
    public boolean isBuildMode() { return buildModeType != null; }
}