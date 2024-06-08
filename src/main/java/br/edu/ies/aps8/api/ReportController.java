package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.RawReportResponse;
import br.edu.ies.aps8.dto.ReportResponse;
import br.edu.ies.aps8.model.FuelType;
import br.edu.ies.aps8.model.Trip;
import br.edu.ies.aps8.model.Vehicle;
import br.edu.ies.aps8.repository.TripRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
public class ReportController {
    private final TripRepository tripRepository;

    // TODO: Maybe create a filter to limit the start and end date
    @GetMapping
    public List<ReportResponse> getReport() {
        List<Trip> trips = tripRepository.findAllOrderByDateAsc();
        Map<Vehicle, List<Trip>> vehicleGroup = trips.stream()
                .collect(Collectors.groupingBy(Trip::getVehicle));
        return vehicleGroup.entrySet().stream()
                .map(entry -> {
                    var vehicle = entry.getKey();
                    Trip trip = entry.getValue().stream()
                            .reduce((trip1, trip2) -> {
                                trip1.setFuelAmount(trip1.getFuelAmount() + trip2.getFuelAmount());
                                trip1.setDistance(trip1.getDistance() + trip2.getDistance());
                                return trip1;
                            })
                            .orElseThrow();
                    ReportResponse report = makeReport(vehicle, trips, trip);
                    report.calculateDescriptions();
                    return report;
                })
                .toList();
    }

    private static ReportResponse makeReport(Vehicle vehicle, List<Trip> trips, Trip trip) {
        return ReportResponse.builder()
                .vehicle(vehicle)
                .startDate(trips.getFirst().getDate())
                .endDate(trips.getLast().getDate())
                .fuelAmount(trip.getFuelAmount())
                .distance(trip.getDistance())
                .fuelEfficiency(trip.getDistance() / trip.getFuelAmount())
                .co2Emission(vehicle.getFuelType().getEmissionFactor() * trip.getFuelAmount())
                .tripsAmount(trips.size())
                .build();
    }

    private static RawReportResponse getReportResponse(Trip trip) {
        FuelType fuelType = trip.getVehicle().getFuelType();
        return RawReportResponse.builder()
                .tripId(trip.getId())
                .fuelAmount(trip.getFuelAmount())
                .distance(trip.getDistance())
                .fuelEfficiency(trip.getDistance() / trip.getFuelAmount())
                .co2Emission(fuelType.getEmissionFactor() * trip.getFuelAmount())
                .build();
    }

    @GetMapping("/raw")
    public List<RawReportResponse> getReportRaw() {
        return tripRepository.findAll().stream()
                .map(ReportController::getReportResponse)
                .toList();
    }

}
