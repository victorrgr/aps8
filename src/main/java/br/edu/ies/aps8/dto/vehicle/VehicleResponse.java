package br.edu.ies.aps8.dto.vehicle;

import br.edu.ies.aps8.model.FuelType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleResponse {
    private Long id;
    private String name;
    private FuelType fuelType;
    private double fuelConsuption;
    private String oilType;
    private double oilChangeInterval;
    private double weight;
}
