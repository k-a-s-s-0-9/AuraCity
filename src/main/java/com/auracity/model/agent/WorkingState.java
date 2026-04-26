package com.auracity.models.agents;

import com.auracity.engine.TimeSnapshot;

public class WorkingState
implements CitizenState {

    @Override
    public void handle(
        Citizen citizen,
        TimeSnapshot t
    ) {

        // Strike condition (low food)
        if (
            citizen.getResources()
            .getResourceSurplus("Food") < 0
        ) {

            citizen.setState(
                new StrikeState()
            );

            return;
        }

        citizen.work();

        if (t.hour() >= 17) {

            citizen.setState(
                new RecreationState()
            );
        }
    }

    @Override
    public String getName() {
        return "WORKING";
    }
}
