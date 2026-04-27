package com.auracity.engine;

import java.util.ArrayList;
import java.util.List;

import com.auracity.model.agent.Citizen;
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
    public void triggerMigrants(double startingCash) {
        int newArrivals = 0;
        
        for (com.auracity.model.buildings.Housing home : homes) {
            while (home.hasSpace()) {
                // Find them a job instantly
                String assignedWorkId = "UNEMPLOYED";
                for (com.auracity.model.buildings.Building b : com.auracity.model.buildings.Building.REGISTRY.values()) {
                    if (b.getType() == com.auracity.model.buildings.BuildingType.WORK_OFFICE || 
                        b.getType() == com.auracity.model.buildings.BuildingType.POWER_PLANT ||
                        b.getType() == com.auracity.model.buildings.BuildingType.FARM) {
                        assignedWorkId = b.getId();
                        break; 
                    }
                }

                // Spawn migrant, give cash, move them in
                com.auracity.model.agent.Citizen migrant = new com.auracity.model.agent.Citizen(
                    "Migrant-" + citizenCounter, home.getId(), assignedWorkId, resources
                );
                migrant.addMoney(startingCash);
                
                citizenCounter++;
                home.addOccupant(migrant);
                allCitizens.add(migrant);
                newArrivals++;
            }
        }
        System.out.println("🚢 " + newArrivals + " wealthy migrants have moved into AuraCity!");
    }
}