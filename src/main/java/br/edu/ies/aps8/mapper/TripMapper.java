package br.edu.ies.aps8.mapper;

import br.edu.ies.aps8.dto.trip.TripRequest;
import br.edu.ies.aps8.dto.trip.TripResponse;
import br.edu.ies.aps8.model.Trip;
import br.edu.ies.aps8.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TripMapper {
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;

    public TripResponse mapToResponse(Trip trip) {
        return TripResponse.builder()
                .id(trip.getId())
                .fuelAmount(trip.getFuelAmount())
                .distance(trip.getDistance())
                .date(trip.getDate())
                .vehicle(vehicleMapper.mapToResponse(trip.getVehicle()))
                .build();
    }

    public Trip mapToModel(TripRequest tripRequest) {
        return Trip.builder()
                .fuelAmount(tripRequest.getFuelAmount())
                .distance(tripRequest.getDistance())
                .date(Optional.ofNullable(tripRequest.getDate()).orElse(LocalDateTime.now()))
                .vehicle(vehicleRepository.getReferenceById(tripRequest.getVehicleId()))
                .build();
    }

}
