package br.edu.ies.aps8.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private Long tripId;
    private double fuelAmount;
    private double distance;
    private double fuelEfficiency;
    private String fuelEfficiencyStr;
    private double co2Emission;
    private String co2EmissionStr;
}