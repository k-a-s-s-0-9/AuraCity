package com.auracity.model.buildings;

import java.util.*;
import com.auracity.model.agent.Citizen;

public class WaterFarm extends building {
    // WaterFarm-specific properties
    private int waterOutput;


    public WaterFarm(int max_occupants, List<Citizen> occupants, int waterOutput) {
        super(max_occupants, 0, occupants);
        this.waterOutput = waterOutput;
    }
    public int getWaterOutput() { return waterOutput; }
}
