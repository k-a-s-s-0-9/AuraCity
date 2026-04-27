package com.auracity.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.auracity.model.agent.Citizen;
import com.auracity.model.buildings.BuildingType;
import com.auracity.model.buildings.Housing; // CHANGED: Replaced ResidentialBuilding with Housing

public class PopulationManager implements TimeListener {

    private final List<Citizen> allCitizens;
    private final List<Housing> homes; // CHANGED: Now uses the Housing class
    private final ResourceManager resources;
    private int citizenCounter;

    public PopulationManager(ResourceManager resources) {
        this.resources = resources;
        this.allCitizens = new ArrayList<>();
        this.homes = new ArrayList<>();
        this.citizenCounter = 1;
    }

    public void addHome(Housing home) { // CHANGED: Parameter updated
        homes.add(home);
    }

    @Override
    public void onTimeTick(TimeSnapshot t) {
        // Spawn only once per day
        if (t.newDay()) {
            spawnCitizens();
        }
        for (Citizen c : allCitizens) {
            c.onTimeTick(t);
        }
    }

    private void spawnCitizens() {
        for (Housing home : homes) {
            if (home.hasSpace()) {
                
                // --- NEW LOGIC: Find a valid workplace ---
                String assignedWorkId = "UNEMPLOYED";
                for (com.auracity.model.buildings.Building b : com.auracity.model.buildings.Building.REGISTRY.values()) {
                    // If it's a workplace, assign it (You can make this logic smarter later to check capacity)
                    if (b.getType() == com.auracity.model.buildings.BuildingType.WORK_OFFICE || 
                        b.getType() == com.auracity.model.buildings.BuildingType.POWER_PLANT ||
                        b.getType() == com.auracity.model.buildings.BuildingType.FARM) {
                        assignedWorkId = b.getId();
                        break; 
                    }
                }
                // -----------------------------------------

                Citizen citizen = new Citizen(
                    "Citizen-" + citizenCounter,
                    home.getId(), 
                    assignedWorkId, // Now passing a real building UUID!
                    resources
                );

                citizenCounter++;
                home.addOccupant(citizen); 
                allCitizens.add(citizen);
            }
        }
    }

    public void removeCitizen(Citizen citizen) {
        allCitizens.remove(citizen);
    }

    public List<Citizen> getCitizens() {
        return allCitizens;
    }
    public void triggerMigrants(int count) {
    Random rand = new Random();
    
    for (int i = 0; i < count; i++) {
        // 1. Find a home (standard logic)
        Housing home = findAvailableHousing(); 
        if (home == null) break;

        // 2. Determine Career Path via "The Roll"
        String workId = "UNEMPLOYED";
        double classRoll = rand.nextDouble(); // 0.0 to 1.0
        double startingWallet = 0;

        if (classRoll < 0.16) { // ~1 in 6: The "Big Bucks" Office Worker
            workId = findWorkplaceByType(BuildingType.WORK_OFFICE);
            startingWallet = 5000 + rand.nextInt(5000); 
        } else if (classRoll < 0.50) { // ~34%: The Plant Worker
            workId = findWorkplaceByType(BuildingType.POWER_PLANT);
            startingWallet = 1000 + rand.nextInt(1000);
        } else if (classRoll < 0.80) { // ~30%: The Farmer
            workId = findWorkplaceByType(BuildingType.FARM);
            startingWallet = 500;
        } else { // 20%: Unemployed / Job Seeker
            workId = "UNEMPLOYED";
            startingWallet = 100;
        }

        Citizen migrant = new Citizen("Migrant-" + i, home.getId(), workId, resources);
        migrant.addMoney(startingWallet);
        // ... add to lists ...
    }
}

// Helper 1: Scans the city for an empty bed
    private com.auracity.model.buildings.Housing findAvailableHousing() {
        for (com.auracity.model.buildings.Housing home : homes) {
            if (home.hasSpace()) {
                return home;
            }
        }
        return null; // City is full!
    }

    // Helper 2: Scans the registry for a specific type of workplace
    private String findWorkplaceByType(com.auracity.model.buildings.BuildingType targetType) {
        for (com.auracity.model.buildings.Building b : com.auracity.model.buildings.Building.REGISTRY.values()) {
            if (b.getType() == targetType) {
                return b.getId();
            }
        }
        return "UNEMPLOYED"; // Couldn't find that building type
    }
}