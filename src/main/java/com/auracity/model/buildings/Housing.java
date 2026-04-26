package com.auracity.model.buildings;

public class Housing extends Building {
    private int rentCost;

    public Housing() {
        super(BuildingType.TOWN_HOUSE); // Tells the base class what stats to load
        this.rentCost = 20; 
    }

    public int getRentCost() { return rentCost; }
    public String getHomeId() { return id; } // id is inherited from Building
}