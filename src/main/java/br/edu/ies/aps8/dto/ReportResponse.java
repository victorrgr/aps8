package br.edu.ies.aps8.dto;

import br.edu.ies.aps8.dto.vehicle.VehicleResponse;
import br.edu.ies.aps8.model.OilType;
import br.edu.ies.aps8.model.Vehicle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportResponse {
    private VehicleResponse vehicle;
    private int tripsAmount;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private OilType oilType;
    private String fuelAmountStr;
    private String distanceStr;
    private String fuelEfficiencyStr;
    private String co2EmissionStr;
    private String oilResidueStr;
    private double fuelAmount;
    private double distance;
    private double fuelEfficiency;
    private double co2Emission;
    private double oilResidue;

    public void calculateDescriptions() {
        this.fuelAmountStr = "%.2f L".formatted(this.getFuelAmount());
        this.distanceStr = "%.2f km".formatted(this.getDistance());
        this.fuelEfficiencyStr = "%.2f km/L".formatted(this.getFuelEfficiency());
        this.co2EmissionStr = "%.2f kg".formatted(this.getCo2Emission());
        this.oilResidueStr = "%.2f L".formatted(this.getOilResidue());
    }
}