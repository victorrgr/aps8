package br.edu.ies.aps8.dto.vehicle;


import br.edu.ies.aps8.model.OilType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
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
    @NotNull(message = "Fuel Type is required")
    @Positive(message = "Fuel Type must be positive")
    private Long fuelTypeId;
    /**
     * SYNTHETIC, SEMI_SYNTHETIC, CONVENTIONAL
     */
    @NotNull(message = "Oil Type is required")
    private OilType oilType;
    @NotNull(message = "Oil Capacity is required")
    private Double oilCapacity;
    /**
     * kilometers
     */
    @NotNull(message = "Oil Change Interval is required")
    private Double oilChangeInterval;
    @NotNull(message = "Tire Capacity is required")
    private Double tireCapacity;
    /**
     * kilometers
     */
    @NotNull(message = "Tire Change Interval is required")
    private Double tireChangeInterval;
    @NotNull(message = "Battery Capacity is required")
    private Double batteryCapacity;
    /**
     * kilometers
     */
    @NotNull(message = "Battery Change Interval is required")
    private Double batteryChangeInterval;
    @NotNull(message = "Weight is required")
    private Double weight;
    @NotNull(message = "Fuel Capacity is required")
    private Double fuelCapacity;
}
