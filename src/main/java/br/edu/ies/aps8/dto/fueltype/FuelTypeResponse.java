package br.edu.ies.aps8.dto.fueltype;

import br.edu.ies.aps8.model.EmissionFactorUnit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuelTypeResponse {
    private Long id;
    private String name;
    private double emissionFactor;
    private EmissionFactorUnit emissionFactorUnit;
}
