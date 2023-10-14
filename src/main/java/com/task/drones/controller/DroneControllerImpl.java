package com.task.drones.controller;

import com.task.drones.dto.MedicationsListDTO;
import com.task.drones.dto.DroneRegistrationDTO;
import com.task.drones.service.DroneService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller implementing DroneController.
 */
@RestController
@RequiredArgsConstructor
public class DroneControllerImpl implements DroneController {

    private final DroneService droneService;

    @Override
    public ResponseEntity<Object> register(DroneRegistrationDTO droneDTO) {
        return new ResponseEntity<>(droneService.create(droneDTO), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Object> loadDrone(Integer droneId,
                                           MedicationsListDTO medicationsDTO,
                                           boolean isFinalLoad) {
        return new ResponseEntity<>(droneService
                .load(droneId, medicationsDTO.getMedications(), isFinalLoad), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getLoadedMedications(Integer droneId) {
        return new ResponseEntity<>(droneService.getLoadedMedications(droneId),
                    HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Object> getAvailableDrones() {
        return new ResponseEntity<>(droneService.getAvailableDrones(), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<String> getBatteryLevel(Integer droneId) {
        return new ResponseEntity<>(droneService.getBatteryLevel(droneId) + "%", HttpStatus.OK);
    }
}
