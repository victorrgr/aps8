package br.edu.ies.aps8.util;

public interface SIConverter {
    String KG_CO2_MJ = "kg CO2/MJ";

    double GALLON_TO_LITER = 3.78541;

    /**
     * Convert a value to the counterpart SI value
     * @param value value in the specified unit
     * @return the SI converted value
     */
    double convert(double value);
}