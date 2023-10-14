package com.task.drones.service;

import com.task.drones.dto.DroneRegistrationDTO;
import com.task.drones.dto.MedicationDTO;
import com.task.drones.entity.Drone;
import com.task.drones.entity.Medication;
import com.task.drones.exception.DroneLoadException;
import com.task.drones.repository.DroneRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Unit tests for DroneService.
 */
@ExtendWith(MockitoExtension.class)
class DroneServiceImplTest {

    @Mock
    private DroneRepository droneRepository;

    @Mock
    private ImageStorageService imageStorageService;

    @InjectMocks
    private DroneServiceImpl droneService;

    @Test
    public void createDrone_success() {

        DroneRegistrationDTO droneDTO = new DroneRegistrationDTO()
                .setSerialNumber("DRN001")
                .setModel(Drone.Model.LIGHTWEIGHT.name())
                .setWeightLimit((short) 400)
                .setBatteryLevel((short) 70);

        when(droneRepository.save(any(Drone.class)))
                .then(invocation -> invocation.getArgument(0));

        Drone result = droneService.create(droneDTO);

        assertNotNull(result);
        assertEquals(droneDTO.getSerialNumber(), result.getSerialNumber());
        assertEquals(droneDTO.getModel(), result.getModel().name());
        assertEquals(droneDTO.getWeightLimit(), result.getWeightLimit());
        assertEquals(droneDTO.getBatteryLevel(), result.getBatteryLevel());
        assertEquals(Drone.State.IDLE, result.getState());
    }

    @Test
    public void loadDrone_success() {

        List<MedicationDTO> medicationsDTOList = buildMedicationsList();

        Drone drone = buildDrone();

        when(droneRepository.findById(1))
                .thenReturn(Optional.ofNullable(drone));
        when(droneRepository.save(any(Drone.class)))
                .then(invocation -> invocation.getArgument(0));

        Drone result = droneService.load(1, medicationsDTOList, false);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(2, result.getLoadedMedications().size());
        assertEquals(Drone.State.LOADING, result.getState());
    }

    @Test
    public void loadDrone_finalLoad_success() {

        List<MedicationDTO> medicationsDTOList = buildMedicationsList();

        Drone drone = buildDrone();

        when(droneRepository.findById(1))
                .thenReturn(Optional.ofNullable(drone));
        when(droneRepository.save(any(Drone.class)))
                .then(invocation -> invocation.getArgument(0));

        Drone result = droneService.load(1, medicationsDTOList, true);

        assertNotNull(result);
        assertEquals(1, result.getId());
        assertEquals(2, result.getLoadedMedications().size());
        assertEquals(Drone.State.LOADED, result.getState());
    }

    @Test
    public void loadDrone_wrongState_throwException() {

        List<MedicationDTO> medicationsDTOList = buildMedicationsList();

        Drone drone = buildDrone();
        drone.setState(Drone.State.LOADED);

        when(droneRepository.findById(1))
                .thenReturn(Optional.of(drone));

        Exception exception = assertThrows(DroneLoadException.class,
                () -> droneService.load(1, medicationsDTOList, true));
        assertEquals("Drone is not available for load.", exception.getMessage());
    }

    @Test
    public void loadDrone_weightExceeded_throwException() {

        List<MedicationDTO> medicationsDTOList = buildMedicationsList();

        Drone drone = buildDrone();
        drone.getLoadedMedications().add(new Medication()
                .setWeight((short) 100));

        when(droneRepository.findById(1))
                .thenReturn(Optional.of(drone));

        Exception exception = assertThrows(DroneLoadException.class,
                () -> droneService.load(1, medicationsDTOList, true));
        assertEquals("""
                            Drone weight limit is exceeded, medications weight
                            is 150, but remaining max weight is 100.""",
                exception.getMessage());
    }

    @Test
    public void loadDrone_lowBattery_throwException() {

        List<MedicationDTO> medicationsDTOList = buildMedicationsList();

        Drone drone = buildDrone();
        drone.setBatteryLevel((short) 10);

        when(droneRepository.findById(1))
                .thenReturn(Optional.of(drone));

        Exception exception = assertThrows(DroneLoadException.class,
                () -> droneService.load(1, medicationsDTOList, true));
        assertEquals("Drone battery level is too low.", exception.getMessage());
    }

    @Test
    public void getLoadedMedications_success() {

        Drone drone = buildDrone();
        drone.setLoadedMedications(List.of(
                new Medication().setId(1), new Medication().setId(2)
                )
        );

        when(droneRepository.findById(1))
                .thenReturn(Optional.of(drone));

        List<Medication> result = droneService.getLoadedMedications(1);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(1, result.get(0).getId());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    public void getAvailableDrones_success() {

        Drone droneIdle = buildDrone();
        droneIdle.setState(Drone.State.IDLE);
        Drone droneLoading = buildDrone();
        droneLoading.setState(Drone.State.LOADING);
        Drone droneLoaded = buildDrone();
        droneLoaded.setState(Drone.State.LOADED);
        Drone droneDelivering = buildDrone();
        droneDelivering.setState(Drone.State.DELIVERING);
        Drone droneDelivered = buildDrone();
        droneDelivered.setState(Drone.State.DELIVERED);
        Drone droneReturning = buildDrone();
        droneReturning.setState(Drone.State.RETURNING);

        Set<Drone.State> availabeStates = Set.of(
                Drone.State.IDLE, Drone.State.LOADING);

        when(droneRepository.findByStateIn(availabeStates))
                .thenReturn(List.of(droneIdle, droneLoading));

        List<Drone> result = droneService.getAvailableDrones();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertTrue(availabeStates.contains(result.get(0).getState()));
        assertTrue(availabeStates.contains(result.get(1).getState()));
    }

    @Test
    public void getBatteryLevel_success() {

        Drone drone = buildDrone();

        when(droneRepository.findById(1))
                .thenReturn(Optional.of(drone));

        short result = droneService.getBatteryLevel(1);

        assertEquals((short) 70, result);
    }

    private static Drone buildDrone() {
        return new Drone().setId(1)
                .setSerialNumber("DRN001")
                .setModel(Drone.Model.MIDDLEWEIGHT)
                .setWeightLimit((short) 200)
                .setBatteryLevel((short) 70)
                .setState(Drone.State.IDLE)
                .setLoadedMedications(new ArrayList<>());
    }

    private static List<MedicationDTO> buildMedicationsList() {
        MedicationDTO medDtoFirst = new MedicationDTO()
                .setName("FirstMed")
                .setWeight((short) 50)
                .setCode("M1");

        MedicationDTO medDtoSecond = new MedicationDTO()
                .setName("SecondMed")
                .setWeight((short) 100)
                .setCode("M2");

        return List.of(medDtoFirst, medDtoSecond);
    }

}