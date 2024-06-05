package br.edu.ies.aps8.util;

import org.springframework.stereotype.Component;

@Component
public class MMBtuToMJConverter implements SIConverter {
    private static final double MMBTU_TO_MJ = 1055.06;

    @Override
    public double convert(double value) {
        return value / MMBTU_TO_MJ;
    }
}
