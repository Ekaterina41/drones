package com.task.drones.model;

import com.task.drones.enums.DroneModel;
import lombok.Data;

/**
 * Request containing the information about the registered drone
 */
@Data
public class DroneRegistrationRequest {
    private String serialNumber;
    private DroneModel model;
    private short weightLimit;
    private short batteryLevel;
}
