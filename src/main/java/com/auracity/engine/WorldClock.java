package com.auracity.engine;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WorldClock {

    private int minutes;
    private int day;
    private boolean running;
    private boolean started;
    
    private int lastHour;
    private final ScheduledExecutorService executor;
    private final List<TimeListener> listeners;

    private WorldClock() {
        this.minutes = 0;
        this.day = 0;
        this.running = true;
        this.started = false;
        this.lastHour = 0;
        this.executor = Executors.newSingleThreadScheduledExecutor();
        this.listeners = new CopyOnWriteArrayList<>();
    }

    private static class InstanceHolder {
        private static final WorldClock INSTANCE = new WorldClock();
    }

    public static WorldClock getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public synchronized void start() {
        if (started) {
            return;
        }
        started = true;
        executor.scheduleAtFixedRate(this::tick, 0, 1, TimeUnit.SECONDS);
    }

    public synchronized void stop() {
        executor.shutdown();
        started = false;
    }

    private synchronized void tick() {
        if (!running) {
            return;
        }

        boolean newDay = false;
        minutes += 30;

        if (minutes >= 1440) {
            minutes = 0;
            day++;
            newDay = true;
            lastHour = 0;
        }

        int currentHour = minutes / 60;
        boolean newHour = (currentHour != lastHour) || newDay;

        if (newHour) {
            lastHour = currentHour;
        }

        TimeSnapshot snapshot = new TimeSnapshot(
            minutes,
            day,
            currentHour,
            newHour,
            newDay
        );

        notifyListeners(snapshot);
    }

    public synchronized void pause() {
        this.running = false;
    }

    public synchronized void resume() {
        this.running = true;
    }

    public void subscribe(TimeListener listener) {
        listeners.add(listener);
    }

    public void unsubscribe(TimeListener listener) {
        listeners.remove(listener);
    }

    private void notifyListeners(TimeSnapshot snapshot) {
        for (TimeListener listener : listeners) {
            listener.onTimeTick(snapshot);
        }
    }

    public synchronized int getMinutes() {
        return minutes;
    }

    public synchronized int getDay() {
        return day;
    }

    public synchronized String getFormattedTime() {
        int h = minutes / 60;
        int m = minutes % 60;
        return String.format("Day %d %02d:%02d", day, h, m);
    }
}
