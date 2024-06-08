package br.edu.ies.aps8.dto.fueltype;

import br.edu.ies.aps8.model.EmissionFactorUnit;
import br.edu.ies.aps8.model.Unit;

public class FuelTypeResponse {
    private Long id;
    private String name;
    private double emissionFactor;
    private EmissionFactorUnit emissionFactorUnit;
    private Unit unit;
}
