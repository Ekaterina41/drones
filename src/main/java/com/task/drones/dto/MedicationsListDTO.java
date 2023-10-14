package com.task.drones.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * DTO containing the list of medications.
 */
@Data
public class MedicationsListDTO {

    @Valid
    @NotEmpty(message = "List of medications is empty.")
    private List<MedicationDTO> medications = new ArrayList<>();

}
