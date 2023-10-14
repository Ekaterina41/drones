package com.task.drones.controller;

import com.task.drones.entity.Medication;
import com.task.drones.exception.DroneLoadException;
import com.task.drones.model.DroneRegistrationRequest;
import com.task.drones.service.DroneService;
import jakarta.persistence.EntityNotFoundException;
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
    public ResponseEntity<Object> register(DroneRegistrationRequest droneRegistrationRequest) {
        try {
            return new ResponseEntity<>(droneService
                    .create(droneRegistrationRequest), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> loadDrone(Integer droneId,
                                           List<Medication> medications,
                                           boolean isLastLoad) {
        try {
            return new ResponseEntity<>(droneService.load(droneId, medications, isLastLoad), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (DroneLoadException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getLoadedMedications(Integer droneId) {
        try {
            return new ResponseEntity<>(droneService.getLoadedMedications(droneId),
                    HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Object> getAvailableDrones() {
        try {
            return new ResponseEntity<>(droneService.getAvailableDrones(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<String> getBatteryLevel(Integer droneId) {
        try {
            return new ResponseEntity<>(droneService.getBatteryLevel(droneId) + "%", HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
