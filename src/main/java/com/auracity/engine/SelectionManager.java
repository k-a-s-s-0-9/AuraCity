package com.auracity.engine;

import com.auracity.model.buildings.BuildingType;

public class SelectionManager {
    private static BuildingType selectedType = null;

    public static void setSelection(BuildingType type) {
        selectedType = type;
        System.out.println("Selected: " + type);
    }

    public static BuildingType getSelection() {
        return selectedType;
    }

    public static void clearSelection() {
        selectedType = null;
    }
}