package br.edu.ies.aps8.dto.trip;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripRequest {
    /**
     * liters
     */
    @NotNull(message = "Fuel amount is required")
    @Positive(message = "Fuel amount must be positive")
    private Double fuelAmount;
    /**
     * kilometers
     */
    @NotNull(message = "Distance is required")
    @Positive(message = "Distance must be positive")
    private Double distance;
    private LocalDateTime date;
    @NotNull(message = "Vehicle id is required")
    private Long vehicleId;
}
