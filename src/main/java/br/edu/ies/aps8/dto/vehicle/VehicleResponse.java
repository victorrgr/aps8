package br.edu.ies.aps8.dto.vehicle;

import br.edu.ies.aps8.model.FuelType;
import br.edu.ies.aps8.model.OilType;
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
    private OilType oilType;
    private double oilCapacity;
    private double oilChangeInterval;
    private double tireCapacity;
    private double tireChangeInterval;
    private double batteryChangeInterval;
    private double batteryCapacity;
    private double weight;
    private double fuelCapacity;
}
