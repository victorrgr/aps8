package br.edu.ies.aps8.dto.fueltype;

import br.edu.ies.aps8.model.EmissionFactorUnit;
import br.edu.ies.aps8.model.Unit;
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
public class FuelTypeRequest {
    @NotNull(message = "name cannot be null")
    private String name;
    @NotNull(message = "Emission Factor cannot be null")
    @Positive(message = "Emission Factor must be positive")
    private Double emissionFactor;
    @NotNull(message = "Emission Factor Unit cannot be null")
    private EmissionFactorUnit emissionFactorUnit;
    @NotNull(message = "Unit cannot be null")
    private Unit unit;
}
