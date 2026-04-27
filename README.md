# 🏙️ AuraCity Tycoon

A high-performance, 2D isometric city-building simulation built in Java. AuraCity Tycoon features a custom-built event-driven backend engine paired with a hardware-accelerated JavaFX frontend, emphasizing decoupled architecture and dynamic agent-based modeling.

## ✨ Core Features

* **Custom Event-Driven Engine:** A centralized `WorldClock` utilizes a subscriber pattern to broadcast time ticks to all managers and agents, ensuring synchronized game state without heavy polling.
* **Agent-Based Simulation:** Citizens are governed by a finite state machine (Sleeping, Commuting, Working, Recreation). They dynamically pathfind and update their behavior based on the time of day and available infrastructure.
* **Hardware-Accelerated UI:** Replaced legacy Swing components with a JavaFX `Canvas` implementation. Utilizes an `AnimationTimer` for a buttery-smooth 60 FPS render loop and aggressive image caching to prevent memory leaks.
* **Dynamic Grid System:** Real-time array-based grid mapping with instant collision detection and global `UUID` registry lookups for rapid agent pathing.
* **Macro-Economy:** A fully integrated `EconomyManager` handling dynamic treasury updates, building costs, and wealth injection via migrant waves.

## 🏗️ Architecture & Design Patterns

We strictly separated the backend simulation logic from the visual rendering pipeline.

* **The State Pattern:** `CitizenState.java` handles behavioral transitions for population agents cleanly, avoiding massive `if/else` blocks in the main update loop.
* **The Registry Pattern:** A `ConcurrentHashMap` blueprint allows any agent to instantly look up the `(X, Y)` coordinates of any building on the map via UUID, solving the reference gap between backend logic and UI placement.
* **The Factory Pattern:** `GameCanvas` utilizes a centralized factory method to map `BuildingType` enums to their respective class instantiations dynamically.
* **The Singleton Pattern:** `WorldClock` ensures that all managers and agents share a single, immutable source of time.

## 💻 Tech Stack

* **Language:** Java 21
* **UI Framework:** JavaFX (Controls & Graphics modules)
* **Build Tool:** Maven
* **Assets:** Isometric (64x64 PNGs)

## 🚀 How to Run (Development)

Due to Java 11+ decoupling JavaFX from the core JDK, you cannot run the `MainApp` class directly without explicitly defining module paths. We use a proxy launcher to bypass this for rapid development.

1.  Clone the repository and ensure your IDE has a Java Language Server active.
2.  Run `mvn clean install` to fetch the OpenJFX dependencies defined in the `pom.xml`.
3.  Navigate to `src/main/java/com/auracity/AuraCityTycoonLauncher.java`.
4.  Execute the `Launcher` class via your IDE (Do not run `MainApp` directly). 

*Note for VS Code users: Use the native "Run Java" Code Lens or the "Java Projects" sidebar, rather than raw terminal `javac` commands, to ensure Maven classpath synchronization.*
