package com.task.drones.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * DTO containing the information about the medication.
 */
@Data
public class MedicationDTO {

    @NotBlank(message = "Name is required.")
    @Pattern(regexp = "^[a-zA-Z0-9\\-_]+$",
            message = "For name only letters, numbers, ‘-‘ and ‘_’ allowed")
    private String name;

    @Min(value = 1, message = "Weight cannot be 0.")
    private short weight;

    @NotBlank(message = "Code is required.")
    @Pattern(regexp = "^[A-Z0-9_]+$",
            message = "For code only upper case letters, numbers and ‘_’ allowed")
    private String code;

    private String image;
}
