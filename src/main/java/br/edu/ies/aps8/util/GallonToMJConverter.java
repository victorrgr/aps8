package br.edu.ies.aps8.util;

import org.springframework.stereotype.Component;

@Component
public class GallonToMJConverter implements SIConverter {
    private static final double GALLON_TO_MJ = 131.76;

    @Override
    public double convert(double value) {
        return value / GALLON_TO_MJ;
    }
}
