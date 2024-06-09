package br.edu.ies.aps8.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RawReportResponse {
    private Long tripId;
    private double fuelAmount;
    private String fuelAmountStr;
    private double distance;
    private String distanceStr;
    private double fuelEfficiency;
    private String fuelEfficiencyStr;
    private double co2Emission;
    private String co2EmissionStr;

    public void calculateDescriptions() {
        this.fuelAmountStr = "%.2f l".formatted(this.getFuelAmount());
        this.distanceStr = "%.2f km".formatted(this.getDistance());
        this.fuelEfficiencyStr = "%.2f km/l".formatted(this.getFuelEfficiency());
        this.co2EmissionStr = "%.2f kg".formatted(this.getCo2Emission());
    }
}