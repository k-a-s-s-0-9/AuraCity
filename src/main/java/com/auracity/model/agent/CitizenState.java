package com.auracity.model.agent;

import com.auracity.engine.TimeSnapshot;

public interface CitizenState {

    void handle(
        Citizen citizen,
        TimeSnapshot t
    );

    String getName();
}
