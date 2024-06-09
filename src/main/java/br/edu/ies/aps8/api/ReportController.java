package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.RawReportResponse;
import br.edu.ies.aps8.dto.ReportResponse;
import br.edu.ies.aps8.model.*;
import br.edu.ies.aps8.repository.TripRepository;
import br.edu.ies.aps8.util.ConversionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/report")
@RequiredArgsConstructor
@Slf4j
public class ReportController {
    private final TripRepository tripRepository;
    private final ConversionService conversionService;

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
                .sorted(Comparator.comparing(a -> a.getVehicle().getName()))
                .toList();
    }

    private ReportResponse makeReport(Vehicle vehicle, List<Trip> trips, Trip trip) {
        FuelType fuelType = vehicle.getFuelType();
        double emissionFactor = conversionService.convert(fuelType.getEmissionFactor(), fuelType.getEmissionFactorUnit().getUnit(), Unit.LITER);
        double fuelEfficiency = trip.getDistance() / trip.getFuelAmount();
        return ReportResponse.builder()
                .vehicle(vehicle)
                .startDate(trips.getFirst().getDate())
                .endDate(trips.getLast().getDate())
                .fuelAmount(trip.getFuelAmount())
                .distance(trip.getDistance())
                .fuelEfficiency(fuelEfficiency)
                .co2Emission(trip.getFuelAmount() * emissionFactor)
                .oilResidue(trip.getDistance() / vehicle.getOilChangeInterval())
                .oilType(vehicle.getOilType())
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
