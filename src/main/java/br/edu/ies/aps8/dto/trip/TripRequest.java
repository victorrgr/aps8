package br.edu.ies.aps8.dto.trip;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripRequest {
    /**
     * liters
     */
    private double fuelAmount;
    /**
     * kilometers
     */
    private double distance;
    private Long vehicleId;
}
