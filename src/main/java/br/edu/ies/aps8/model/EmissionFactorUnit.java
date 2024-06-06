package br.edu.ies.aps8.model;

import lombok.Getter;

@Getter
public enum EmissionFactorUnit {
    KG_CO2_L("kg CO2/" + Unit.LITER.getSymbol()),
    KG_CO2_MJ("kg CO2/" + Unit.MEGA_JOULE.getSymbol()),
    KG_CO2_MMBTU("kg CO2/" + Unit.MILLION_BRITISH_THERMAL_UNIT.getSymbol())
    ;

    private final String symbol;

    EmissionFactorUnit(String symbol) {
        this.symbol = symbol;
    }

}
