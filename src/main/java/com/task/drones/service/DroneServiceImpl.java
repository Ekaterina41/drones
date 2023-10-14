package com.task.drones.service;

import com.task.drones.dto.MedicationDTO;
import com.task.drones.entity.Drone;
import com.task.drones.entity.Medication;
import com.task.drones.exception.DroneLoadException;
import com.task.drones.dto.DroneRegistrationDTO;
import com.task.drones.repository.DroneRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Service for operations with Drones.
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DroneServiceImpl implements DroneService {

    public static final int MIN_BATTERY_LEVEL_FOR_LOAD = 25;
    public static final Set<Drone.State> AVAILABLE_DRONE_STATES =
            Set.of(Drone.State.IDLE, Drone.State.LOADING);

    private final DroneRepository droneRepository;

    @Override
    public Drone create(DroneRegistrationDTO droneDTO) {
        Drone newDrone = new Drone()
                .setSerialNumber(droneDTO.getSerialNumber())
                .setModel(Drone.Model.valueOf(droneDTO.getModel().toUpperCase()))
                .setWeightLimit(droneDTO.getWeightLimit())
                .setBatteryLevel(droneDTO.getBatteryLevel())
                .setState(Drone.State.IDLE);

        return droneRepository.save(newDrone);
    }

    @Override
    public Drone load(Integer droneId, List<MedicationDTO> medicationsDTOList,
                      boolean isFinalLoad) {

        Drone drone = fetchDrone(droneId);

        if (!AVAILABLE_DRONE_STATES.contains(drone.getState())) {
            throw new DroneLoadException("Drone is not available for load.");
        }

        List<Medication> medications = medicationsDTOList
                .stream()
                .map(dto -> new Medication()
                        .setName(dto.getName())
                        .setWeight(dto.getWeight())
                        .setCode(dto.getCode())
                        .setImage(dto.getImage())
                        .setDrone(drone))
                .toList();

        int totalLoadedWeight = getTotalWeight(medications);
        int droneRemainingWeight = drone.getWeightLimit() - getTotalWeight(
                drone.getLoadedMedications());

        if (totalLoadedWeight > droneRemainingWeight) {
            throw new DroneLoadException(String.format("""
                            Drone weight limit is exceeded, medications weight
                            is %d, but remaining max weight is %d.""",
                    totalLoadedWeight, droneRemainingWeight));
        }

        if (drone.getBatteryLevel() < MIN_BATTERY_LEVEL_FOR_LOAD) {
            throw new DroneLoadException("Drone battery level is too low.");
        }

        drone.getLoadedMedications().addAll(medications);
        medications.forEach(medication -> medication.setDrone(drone));

        if (isFinalLoad) {
            drone.setState(Drone.State.LOADED);
        } else {
            drone.setState(Drone.State.LOADING);
        }



        return droneRepository.save(drone);
    }

    @Override
    public List<Medication> getLoadedMedications(Integer droneId) {
        return fetchDrone(droneId).getLoadedMedications();
    }

    @Override
    public List<Drone> getAvailableDrones() {
        return droneRepository.findByStateIn(AVAILABLE_DRONE_STATES);
    }

    @Override
    public short getBatteryLevel(Integer droneId) {
        return droneRepository.findById(droneId)
                .map(Drone::getBatteryLevel)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Drone with id = %d not found.", droneId)));
    }

    private Drone fetchDrone(Integer droneId) {
        return droneRepository.findById(droneId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Drone with id = %d not found.", droneId)));
    }

    private int getTotalWeight(List<Medication> medications) {
        return medications.stream()
                .mapToInt(Medication::getWeight)
                .sum();
    }
}
