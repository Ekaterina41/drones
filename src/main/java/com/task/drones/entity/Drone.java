package com.task.drones.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Class that represents Drone entity. The Drone is capable of delivering
 * Medications.
 */
@Data
@Entity
@Accessors(chain = true)
public class Drone {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    @Column(length = 100)
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private Model model;

    private short weightLimit;

    private short batteryLevel;

    @Enumerated(EnumType.STRING)
    private State state;

    @OneToMany(mappedBy="drone", cascade = CascadeType.ALL)
    private List<Medication> loadedMedications = new ArrayList<>();

    public enum State {
        IDLE, LOADING, LOADED, DELIVERING, DELIVERED, RETURNING
    }

    public enum Model {
        LIGHTWEIGHT("Lightweight"),
        MIDDLEWEIGHT("Middleweight"),
        CRUISEWEIGHT("Cruiseweight"),
        HEAVYWEIGHT("Heavyweight");

        private final String name;

        Model(String name) {
            this.name = name;
        }
    }

}
