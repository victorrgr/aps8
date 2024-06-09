package br.edu.ies.aps8.model;

import lombok.Getter;

@Getter
public enum Unit {
    GALLON("gal"),
    LITER("L");

    private final String symbol;

    Unit(String symbol) {
        this.symbol = symbol;
    }
}
