package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.trip.TripRequest;
import br.edu.ies.aps8.dto.trip.TripResponse;
import br.edu.ies.aps8.model.Trip;
import br.edu.ies.aps8.repository.TripRepository;
import br.edu.ies.aps8.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
                .date(trip.getDate())
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
    public TripResponse addTrip(@Valid @RequestBody TripRequest tripRequest) {
        Trip trip = Trip.builder()
                .fuelAmount(tripRequest.getFuelAmount())
                .distance(tripRequest.getDistance())
                .date(Optional.ofNullable(tripRequest.getDate()).orElse(LocalDateTime.now()))
                .vehicle(vehicleRepository.getReferenceById(tripRequest.getVehicleId()))
                .build();
        trip = tripRepository.save(trip);
        return mapToResponse(trip);
    }

    @PostMapping("/batch")
    public List<Object> addTripBatch(@RequestBody List<TripRequest> tripRequests) {
        return tripRequests.stream()
                .map(tripRequest -> {
                    try {
                        return addTrip(tripRequest);
                    } catch (Exception e) {
                        return Map.of("error", e.getMessage());
                    }
                })
                .toList();
    }

}
