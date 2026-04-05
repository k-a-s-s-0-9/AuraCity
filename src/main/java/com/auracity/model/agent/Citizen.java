package model.agent;

import java.util.UUID;
import model.buildings.Housing;

public class Citizen {
    // Unique identifier for tracking specific agents
    private final String id;
    private String name;
    
    // Using an Enum for strict state control
    public enum State { HOME, COMMUTING, WORKING }
    private State currentState;

    // References to grid coordinates or building IDs
    private String homeId;
    private String jobId;

    // Core agent metrics (0.0 to 100.0)
    private double health;
    private double happiness;

    public Citizen(String name, String homeId) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
        this.homeId = homeId;
        this.currentState = State.HOME;
        this.health = 100.0;
        this.happiness = 100.0;
    }

    // Convenience constructor that accepts a Housing instance
    public Citizen(String name, Housing home) {
        this(name, home.getId());
    }

    // Synchronized methods to prevent the UI thread from reading a half-updated state while the simulation tick is modifying it.
    public synchronized void updateState(State newState) {
        this.currentState = newState;
    }

    public synchronized State getCurrentState() {
        return currentState;
    }

    public synchronized void modifyHealth(double amount) {
        this.health = Math.max(0, Math.min(100, this.health + amount));
    }

    // Getters and Setters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getHomeId() { return homeId; }
    public String getJobId() { return jobId; }
    public synchronized double getHealth() { return health; }
    public synchronized double getHappiness() { return happiness; }
    public void setJobId(String jobId) { this.jobId = jobId; }

}