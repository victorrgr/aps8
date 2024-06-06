package br.edu.ies.aps8.util;

import org.springframework.stereotype.Component;

@Component
public class GallonToLiterConverter implements SIConverter {
    private static final double GALLON_TO_LITER = 3.78541;

    @Override
    public double convert(double value) {
        return value / GALLON_TO_LITER;
    }
}
