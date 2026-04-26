package com.auracity.model.buildings;

public class PowerPlant extends Building {
    public PowerPlant() {
        super(BuildingType.POWER_PLANT);
    }

    public int getPowerOutput() { 
        // Inherits the rate from the Enum!
        return type.getPowerRate(); 
    }
}