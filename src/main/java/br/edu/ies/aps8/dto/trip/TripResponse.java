package br.edu.ies.aps8.dto.trip;

import br.edu.ies.aps8.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripResponse {
    private Long id;
    /**
     * liters
     */
    private double fuelAmount;
    /**
     * kilometers
     */
    private double distance;
    private LocalDateTime date;
    private Vehicle vehicle;
}
