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
    /**
     * kilometers
     */
    @NotNull(message = "Oil Change Interval is required")
    private Double oilChangeInterval;
    @NotNull(message = "Weight is required")
    private Double weight;
}
