package com.auracity.model.buildings;

public class Granary extends Building {
    private int storageCapacity;

    public Granary() {
        super(BuildingType.GRANARY);
        this.storageCapacity = 5000;
    }

    public int getStorageCapacity() { return storageCapacity; }
}