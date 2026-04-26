package com.auracity.model.buildings;

public enum BuildingType {
    // Format: NAME(DisplayLabel, Cost, PowerRate, WaterRate, FoodRate, MaxOccupants, SpritePath)
    TOWN_HOUSE("Town House", 500, -10, -5, 0, 4, "/assets/buildings/house.png"),
    FARM("Urban Farm", 800, -5, -20, 100, 10, "/assets/buildings/farm.png"),
    POWER_PLANT("Power Plant", 2000, 500, -10, 0, 15, "/assets/buildings/power.png"),
    WATER_TOWER("Water Tower", 1200, -20, 300, 0, 0, "/assets/buildings/water.png"),
    WORK_OFFICE("Commercial Office", 1500, -50, -20, 0, 50, "/assets/buildings/office.png"),
    BANK("City Bank", 2500, -40, -10, 0, 10, "/assets/buildings/bank.png"),
    GRANARY("Granary", 1000, -15, -5, 0, 5, "/assets/buildings/granary.png"),
    WATER_FARM("Water Plant", 1500, -50, 500, 0, 15, "/assets/buildings/water_farm.png"); // <--- THIS SEMICOLON IS CRITICAL

    private final String label;
    private final int cost;
    private final int powerRate; // Positive = Generation, Negative = Consumption
    private final int waterRate;
    private final int foodRate;
    private final int maxOccupants; // Moved here from the hardcoded classes
    private final String spritePath;

    BuildingType(String label, int cost, int powerRate, int waterRate, int foodRate, int maxOccupants, String spritePath) {
        this.label = label;
        this.cost = cost;
        this.powerRate = powerRate;
        this.waterRate = waterRate;
        this.foodRate = foodRate;
        this.maxOccupants = maxOccupants;
        this.spritePath = spritePath;
    }

    // Getters
    public String getLabel() { return label; }
    public int getCost() { return cost; }
    public int getPowerRate() { return powerRate; }
    public int getWaterRate() { return waterRate; }
    public int getFoodRate() { return foodRate; }
    public int getMaxOccupants() { return maxOccupants; }
    public String getSpritePath() { return spritePath; }
}