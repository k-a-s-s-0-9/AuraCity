package com.auracity.engine;

import com.auracity.model.agent.Citizen;

public class HappinessManager
implements TimeListener {

    private final PopulationManager populationManager;

    private final ResourceManager resources;

    public HappinessManager(
        PopulationManager populationManager,
        ResourceManager resources
    ) {

        this.populationManager = populationManager;
        this.resources = resources;
    }

    @Override
    public void onTimeTick(
        TimeSnapshot t
    ) {

        // Update happiness every hour
        if (t.newHour()) {

            updateHappiness();
        }
    }

    private void updateHappiness() {

        double foodAccess =
            Math.min(
                1.0,
                resources.getResourceSurplus("Food") / 100.0
            );

        double powerAccess =
            Math.min(
                1.0,
                resources.getResourceSurplus("Power") / 100.0
            );

        for (Citizen citizen : populationManager.getCitizens()) {

            double walletHealth =
                Math.min(
                    1.0,
                    citizen.getWallet() / 500.0
                );

            double happiness =
                (foodAccess * 0.4)
                +
                (powerAccess * 0.3)
                +
                (walletHealth * 0.3);

            citizen.setHappiness(happiness);
        }
    }
}
