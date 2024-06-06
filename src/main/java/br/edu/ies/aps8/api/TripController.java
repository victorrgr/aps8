package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.trip.TripRequest;
import br.edu.ies.aps8.dto.trip.TripResponse;
import br.edu.ies.aps8.model.Trip;
import br.edu.ies.aps8.repository.TripRepository;
import br.edu.ies.aps8.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trips")
@RequiredArgsConstructor
public class TripController {
    private final TripRepository tripRepository;
    private final VehicleRepository vehicleRepository;

    private static TripResponse mapToResponse(Trip trip) {
        return TripResponse.builder()
                .id(trip.getId())
                .fuelAmount(trip.getFuelAmount())
                .distance(trip.getDistance())
                .vehicle(trip.getVehicle())
                .build();
    }

    @GetMapping
    public List<TripResponse> trips() {
        return tripRepository.findAll().stream()
                .map(TripController::mapToResponse)
                .toList();
    }

    @PostMapping
    public TripResponse addTrip(@RequestBody TripRequest tripRequest) {
        Trip trip = Trip.builder()
                .fuelAmount(tripRequest.getFuelAmount())
                .distance(tripRequest.getDistance())
                .vehicle(vehicleRepository.findById(tripRequest.getVehicleId())
                        .orElseThrow(() -> new IllegalArgumentException("Vehicle not found")))
                .build();
        trip = tripRepository.save(trip);
        return mapToResponse(trip);
    }


}
