package com.task.drones.enums;

/**
 * Drone models enum
 */
public enum DroneModel {
    LIGHTWEIGHT("Lightweight"),
    MIDDLEWEIGHT("Middleweight"),
    CRUISEWEIGHT("Cruiseweight"),
    HEAVYWEIGHT("Heavyweight");

    private final String name;

    DroneModel(String name) {
        this.name = name;
    }
}
