package com.auracity.model.buildings;
public enum BuildingType {
    // Define the "Templates" for your buildings
    // Format: NAME(DisplayLabel, Cost, PowerUsage, WaterUsage, FoodOutput, SpritePath)
    TOWN_HOUSE("Town House", 500, -10, -5, 0, "/assets/buildings/house.png"),
    FARM("Urban Farm", 800, -5, -20, 100, "/assets/buildings/farm.png"),
    POWER_PLANT("Power Plant", 2000, 500, -10, 0, "/assets/buildings/power.png"),
    WATER_TOWER("Water Tower", 1200, -20, 300, 0, "/assets/buildings/water.png"),
    WORK_OFFICE("Commercial Office", 1500, -50, -20, 0, "/assets/buildings/office.png");

    private final String label;
    private final int cost;
    private final int powerRate; // Positive = Generation, Negative = Consumption
    private final int waterRate;
    private final int foodRate;
    private final String spritePath;

    BuildingType(String label, int cost, int powerRate, int waterRate, int foodRate, String spritePath) {
        this.label = label;
        this.cost = cost;
        this.powerRate = powerRate;
        this.waterRate = waterRate;
        this.foodRate = foodRate;
        this.spritePath = spritePath;
    }

    // Getters
    public String getLabel() { return label; }
    public int getCost() { return cost; }
    public int getPowerRate() { return powerRate; }
    public int getWaterRate() { return waterRate; }
    public int getFoodRate() { return foodRate; }
    public String getSpritePath() { return spritePath; }
}