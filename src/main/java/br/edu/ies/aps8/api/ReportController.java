package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.ReportResponse;
import br.edu.ies.aps8.model.FuelType;
import br.edu.ies.aps8.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final TripRepository tripRepository;

    @GetMapping
    public List<ReportResponse> getReport() {
        return tripRepository.findAll().stream()
                .map(trip -> {
                    FuelType fuelType = trip.getVehicle().getFuelType();
                    ReportResponse report = ReportResponse.builder()
                            .tripId(trip.getId())
                            .fuelAmount(trip.getFuelAmount())
                            .distance(trip.getDistance())
                            .fuelEfficiency(trip.getDistance() / trip.getFuelAmount())
                            .co2Emission(fuelType.getEmissionFactor() * trip.getFuelAmount())
                            .build();
                    report.setFuelEfficiencyStr("%s km/l".formatted(report.getFuelEfficiency()));
                    report.setCo2EmissionStr("%s kg".formatted(report.getCo2Emission()));
                    return report;
                })
                .toList();
    }

    @GetMapping("/week")
    public String getHello() {
        return "Report Week";
    }

}
