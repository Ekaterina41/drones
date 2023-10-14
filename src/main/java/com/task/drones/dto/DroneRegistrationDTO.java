package com.task.drones.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.experimental.Accessors;

import static jakarta.validation.constraints.Pattern.Flag.CASE_INSENSITIVE;

/**
 * DTO containing the information about the registered drone.
 */
@Data
@Accessors(chain = true)
public class DroneRegistrationDTO {
    @NotBlank(message = "Serial number is required.")
    @Size(min = 1, max = 100, message = "The length of serial number must be between 1 and 100 characters.")
    private String serialNumber;

    @NotNull(message = "Model name is required.")
    @Pattern(regexp = "Lightweight|Middleweight|Cruiserweight|Heavyweight",
            flags = CASE_INSENSITIVE,
            message = "Model name must be one of the values: Lightweight, Middleweight, Cruiserweight, Heavyweight")
    private String model;

    @Min(value = 1, message = "Max weight cannot be 0.")
    @Max(value = 500, message = "Max weight limit is 500g.")
    private short weightLimit;

    @Max(value = 100, message = "Max battery level is 100%.")
    private short batteryLevel;
}
