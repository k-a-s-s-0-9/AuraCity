package model.buildings;
import java.util.UUID;
import java.util.List;
import java.util.ArrayList;
import model.agent.Citizen;

public class Housing {
    // Unique identifier for buildings and its properties
    private final String id;
    private int max_capacity;
    private int currentOccupants;

    //list of citizens currently residing in this housing unit
    private List<Citizen> occupants;

    // Constructor
    public Housing(int max_capacity) {
        this.id = UUID.randomUUID().toString();
        this.max_capacity = max_capacity;
        this.currentOccupants = 0;
        this.occupants = new ArrayList<>();
    }

    // Expose the final housing id so other objects (e.g. Citizen) can reference it
    public String getId() { return id; }

}