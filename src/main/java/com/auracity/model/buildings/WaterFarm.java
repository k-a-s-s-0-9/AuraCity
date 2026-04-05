package model.buildings;

import java.util.UUID;
import java.util.*;
import model.agent.Citizen;

public class WaterFarm extends building {
    // WaterFarm-specific properties
    private int waterOutput;


    public WaterFarm(UUID id, int max_capacity, int waterOutput) {
        super(id, max_capacity);
        this.waterOutput = waterOutput;
    }

    public int getWaterOutput() { return waterOutput; }
}
