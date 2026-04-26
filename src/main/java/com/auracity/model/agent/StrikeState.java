package com.auracity.model.agent;

import com.auracity.engine.TimeSnapshot;

public class StrikeState
implements CitizenState {

    @Override
    public void handle(
        Citizen citizen,
        TimeSnapshot t
    ) {

        // Return to work when food is sufficient
        if (
            citizen.getResources()
            .getResourceSurplus("Food") > 20
        ) {

            citizen.setState(
                new WorkingState()
            );
        }
    }

    @Override
    public String getName() {
        return "STRIKE";
    }
}
