package com.task.drones.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Class that describes Medication entity that was loaded on the Drone. This
 * entity is supposed to be created, when the Drone is loaded, and removed
 * after delivery.
 */
@Data
@Entity
@Accessors(chain = true)
public class Medication {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    private String name;

    private short weight;

    private String code;

    private String image;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="drone_id", nullable=false)
    private Drone drone;
}
