package com.task.drones.controller;

import com.task.drones.dto.MedicationsListDTO;
import com.task.drones.dto.DroneRegistrationDTO;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller interface for operations with Drones.
 */
@RequestMapping("/drone")
public interface DroneController {

    /**
     * Register a new drone in IDLE status.
     * @param droneRegistrationDTO information for drone registration
     * @return registered drone
     */
    @PostMapping("/register")
    ResponseEntity<Object> register(
            @Valid @RequestBody DroneRegistrationDTO droneRegistrationDTO);

    /**
     * Load drone with medication items.
     * @param droneId drone to load
     * @param medicationsDTO medications for load
     * @param isFinalLoad indicates if drone status will be changed to LOADED
     *                    or will remain LOADING in case when more loads are
     *                    expected
     * @return loaded drone
     */
    @PutMapping("/{droneId}/load")
    ResponseEntity<Object> loadDrone(@PathVariable Integer droneId,
                                     @RequestBody @Valid
                                     MedicationsListDTO medicationsDTO,
                                     @RequestParam(defaultValue = "true") boolean isFinalLoad);

    /**
     * Check loaded medication items for a given drone.
     * @param droneId id of the drone
     * @return list of loaded medications
     */
    @GetMapping("/{droneId}/items")
    ResponseEntity<Object> getLoadedMedications(@PathVariable Integer droneId);

    /**
     * Get the list of available drones for loading.
     * @return list of drones in IDLE and LOADING status
     */
    @GetMapping("/available")
    ResponseEntity<Object> getAvailableDrones();

    /**
     * Get the battery level for a given drone.
     * @param droneId id of the drone to check
     * @return current battery level percentage
     */
    @GetMapping("/{droneId}/batteryLevel")
    ResponseEntity<String> getBatteryLevel(@PathVariable Integer droneId);









}
