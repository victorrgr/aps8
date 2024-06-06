package br.edu.ies.aps8.dto.trip;

import br.edu.ies.aps8.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


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
    // TODO: Maybe create another field that has this field plus a description of the unit
    /**
     * kilometers
     */
    private double distance;
    private Vehicle vehicle;
}
