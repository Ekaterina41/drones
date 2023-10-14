package com.task.drones.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;

/**
 * Entity for storing battery level change history.
 */
@Data
@Entity
@Accessors(chain = true)
public class BatteryLevelLogRecord {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private int id;

    private LocalDateTime checkTime;

    private int droneId;

    private short batteryLevel;
}
