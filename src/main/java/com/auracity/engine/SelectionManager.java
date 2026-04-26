package com.auracity.engine;

import com.auracity.models.agents.Citizen;
import com.auracity.models.buildings.ResidentialBuilding;

public class SelectionManager {

    private Object selected;

    public void select(Object obj) {

        selected = obj;
    }

    public void clearSelection() {

        selected = null;
    }

    public Object getSelected() {

        return selected;
    }

    // ---------------- Type-safe helpers ----------------

    public boolean isCitizenSelected() {

        return selected instanceof Citizen;
    }

    public boolean isBuildingSelected() {

        return selected instanceof ResidentialBuilding;
    }

    public Citizen getSelectedCitizen() {

        if (isCitizenSelected()) {
            return (Citizen) selected;
        }

        return null;
    }

    public ResidentialBuilding getSelectedBuilding() {

        if (isBuildingSelected()) {
            return (ResidentialBuilding) selected;
        }

        return null;
    }
}
