# AuraCity
## 1. Project Concept
AuraCity is a real-time strategy and management game. Players must balance four primary resources while keeping a population of "Sim-Citizens" alive and productive. Unlike static simulations, AuraCity features an active Agent-Based Model where citizens physically commute, work, and consume resources in real-time.
## 2. The "Living City" Tech Stack
* Language: Java 21.
* Rendering: Java2D (Graphics2D). We will use a BufferStrategy for smooth 60FPS animations of citizens.
* UI: Swing + FlatLaf (Dark Mode) for the "Command Center" dashboard.
* Sound: Java Sound API (for incident alarms and click feedback).
* Persistence: SQLite for "Save Game" slots.
## 3. Key Features
### A. Real-time Simulation Engine
* Tick-Based Processing: A background thread calculates resource consumption (Supply - Demand = Surplus/Deficit) every 1000ms.
* Dynamic Scaling: Building efficiency drops as "Health" or "Resource Access" decreases.
### B. Interactive Grid Canvas
* Drag-and-Drop Placement: Users select entities from a sidebar and place them on a coordinate-based grid.
* Contextual Inspector: Clicking a building opens a "Wrapper Class" dashboard to view real-time stats (e.g., current wattage, occupancy).
### C. Incident & AI Engine
* Strategy-Based Events: The system triggers "Strategies" (e.g., FireSpreadStrategy, GridFailureStrategy) that affect nearby grid cells.
* Automated Response: Deployable units (Fire Trucks/Ambulances) use basic pathfinding to reach incident coordinates.
### D. Persistence Layer
* State Snapshot: Save the entire grid (coordinates, entity types, current resources) to an SQLite .db file.
* Auto-Save: Optional periodic background saves to prevent data loss.

### E. The Quadrant Resource System
Each resource has a unique behavior profile:
* Power: Instantaneous. No storage. If Supply < Demand, random buildings shut down.
* Water: Fluid storage. Requires "Water Towers" to buffer against peak usage.
* Food: Produced by Farms; stored in Granaries. If it hits zero, population starts dropping.
* Money (Credits): Generated via "Taxation" ticks from working citizens. Used for repairs and expansion.
### F. The Degradation & Maintenance Loop
Every building has a StructuralIntegrity %:
* Passive Decay: Decreases slowly over time.
* Heavy Load: A Power Plant running at 100% capacity decays 2\times faster.
* The Game Loop: Users must spend Money to "Service" buildings before they hit 0% and trigger a Catastrophic Failure (Incident).
## 4. The "Agent" System (Citizens & X-Ray View)
The Commute Logic
Citizens aren't just numbers; they are Objects with states: HOME, COMMUTING, WORKING.
* Commute: At a specific "Tick" (e.g., 08:00 City Time), Citizen objects move from a House coordinate to a Workplace coordinate.
* X-Ray View: When a workplace is clicked, a "Building Interior" panel opens. This utilizes a Wrapper Class that stores a List<Citizen> currently inside.
* Visuals: The UI renders a mini-grid showing the citizens at their "stations," generating Money/Resources for the city.
## 5. Random Event "Chaos" Engine
Events are now "Game Challenges":
* Strike: If Food is low, workers stay home.
* Meltdown: If a Power Plant reaches 0% integrity, it explodes, damaging adjacent tiles.
* Epidemic: If Water is low, citizens move slower (reduced productivity).
## 6. Core Entities for AuraCity
* Citizen (Agent): The lifeblood of the city. Tracks Name, JobID, HomeID, and current state (HOME, COMMUTING, WORKING). Includes Health and Happiness stats.
* Residential Buildings: Town House (Basic) and Apartment Complex (High Density). These serve as the "Home" for your citizens.
* Production Buildings: Farm (Food), Power Plant (Power), and Water Plant (Water). These generate the primary resources.
* Storage & Buffers: Granary (Food), Water Tower (Water), and Battery Bank (Power). These allow the city to handle spikes in demand.
* Employment Hubs: Work Office (Standard income) and Factory (High income but faster structural degradation).
* Essential Services: Hospital (Restores citizen health) and Maintenance Center (Reduces the degradation rate of nearby structures).
* Infrastructure: Roads (Essential for citizen pathfinding) and Connectors (Pipes/Lines for dependency mapping).
* The Engines: Incident Engine (Chaos generator) and Resource Manager (The global Observer that tracks city-wide stats).


## 6. Production Phases
### Phase 1: The Foundation (Core OOP)
* Member A: Create the Citizen agent class and the ResourceManager (The Observer).
* Member B: Build the Custom MapPanel that can render different "layers" (Buildings vs. Citizens).
### Phase 2: The Working World (Logic & UI)
* Member A: Implement the "Work Schedule" logic.
* Member B: Create the X-Ray Dashboard. Build the UI that pops up when a building is selected to show the internal "Wrapper" data.
### Phase 3: The Economy & Chaos (Gameplay)
* Member A: Implement the Degradation Variable and the Strategy Pattern for random incidents.
* Member B: Integrate the SQLite Save/Load system and the "Build Menu."
### Phase 4: Juice & Polish
* Both: Add animations for walking citizens, "Warning" icons for decaying buildings, and balance the resource costs.

