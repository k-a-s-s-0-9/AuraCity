package com.auracity.model.buildings;

public class WaterBuffer extends Building {
    private int waterCapacity = 100;
    private int flowRate = 10;

    public WaterBuffer() {
        super(BuildingType.WATER_TOWER);
    }

    public int getWaterCapacity() { return waterCapacity; }
    public int getFlowRate() { return flowRate; }
}