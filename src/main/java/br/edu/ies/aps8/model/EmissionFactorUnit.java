package br.edu.ies.aps8.model;

import lombok.Getter;

// TODO Possibly remove this enum as it does not seem necessary
@Getter
public enum EmissionFactorUnit {
    KG_CO2_L("kg CO2/" + Unit.LITER.getSymbol(), Unit.LITER),
    KG_CO2_GAL("kg CO2/" + Unit.GALLON.getSymbol(), Unit.GALLON);

    private final String symbol;
    private final Unit unit;

    EmissionFactorUnit(String symbol, Unit unit) {
        this.symbol = symbol;
        this.unit = unit;
    }

}
