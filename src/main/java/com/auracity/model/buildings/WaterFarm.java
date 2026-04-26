package com.auracity.model.buildings;

public class WaterFarm extends Building {
    public WaterFarm() {
        super(BuildingType.WATER_FARM);
    }

    public int getWaterOutput() { return type.getWaterRate(); }
}