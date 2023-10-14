package com.task.drones.service;

import com.task.drones.dto.MedicationDTO;
import com.task.drones.entity.Drone;
import com.task.drones.entity.Medication;
import com.task.drones.exception.DroneLoadException;
import com.task.drones.dto.DroneRegistrationDTO;
import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface DroneService {

    /**
     * Save new drone in IDLE status.
     * @param droneDTO new drone request
     * @return created drone
     */
    Drone create(DroneRegistrationDTO droneDTO);

    /**
     * Save medications to load them on the drone.
     * @param droneId id of loaded drone
     * @param medicationsDTOList medications to load
     * @param isFinalLoad indicates if drone status will be changed to LOADED
     *                    or will remain LOADING in case when more loads are
     *                    expected
     * @return drone with loaded medications
     * @throws EntityNotFoundException if drone is not found for the given id
     * @throws DroneLoadException if load conditions are failed
     */
    Drone load(Integer droneId, List<MedicationDTO> medicationsDTOList, boolean isFinalLoad);

    /**
     * Get loaded medication items for a given drone
     * @param droneId drone id
     * @return list of loaded medications
     */
    List<Medication> getLoadedMedications(Integer droneId);

    /**
     * Get the list of available drones for loading.
     * @return list of drones in IDLE and LOADING status
     */
    List<Drone> getAvailableDrones();

    /**
     * Get the battery level for a given drone.
     * @param droneId id of the drone to check
     * @return current battery level percentage
     */
    short getBatteryLevel(Integer droneId);
}
