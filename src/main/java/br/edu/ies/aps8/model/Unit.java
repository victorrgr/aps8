package br.edu.ies.aps8.model;

import lombok.Getter;

@Getter
public enum Unit {
    KG("kg"),
    LITER("L"),
    CUBIC_METER("m³"),
    MEGA_JOULE("MJ"),
    MILLION_BRITISH_THERMAL_UNIT("MMBtu");

    private final String symbol;

    Unit(String symbol) {
        this.symbol = symbol;
    }
}
