package br.edu.ies.aps8.dto.vehicle;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleRequest {
    private String name;
    private Long fuelTypeId;
    private double fuelConsuption;
    /**
     * SYNTHETIC, SEMI_SYNTHETIC, CONVENTIONAL
     */
    private String oilType;
    /**
     * km
     */
    private double oilChangeInterval;
    private double weight;
}
