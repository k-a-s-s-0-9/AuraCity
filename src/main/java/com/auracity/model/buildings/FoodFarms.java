package com.auracity.model.buildings;

public class FoodFarms extends Building {
    public FoodFarms() {
        super(BuildingType.FARM);
    }

    public int getFoodProduction() { 
        // Production scales with the number of workers currently inside
        return type.getFoodRate() * Math.max(0, getCurrentOccupants());
    }
}