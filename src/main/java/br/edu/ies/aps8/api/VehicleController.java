package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.vehicle.VehicleRequest;
import br.edu.ies.aps8.dto.vehicle.VehicleResponse;
import br.edu.ies.aps8.model.Vehicle;
import br.edu.ies.aps8.repository.FuelTypeRepository;
import br.edu.ies.aps8.repository.VehicleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleRepository vehicleRepository;
    private final FuelTypeRepository fuelTypeRepository;

    // TODO: Possibly create filters
    @GetMapping
    public List<VehicleResponse> getVehicle() {
        return vehicleRepository.findAll().stream()
                .map(VehicleController::mapToResponse)
                .toList();
    }

    private static VehicleResponse mapToResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .name(vehicle.getName())
                .fuelType(vehicle.getFuelType())
                .fuelConsuption(vehicle.getFuelConsuption())
                .oilType(vehicle.getOilType())
                .oilChangeInterval(vehicle.getOilChangeInterval())
                .weight(vehicle.getWeight())
                .build();
    }

    @PostMapping
    public VehicleResponse createVehicle(@RequestBody VehicleRequest vehicle) {
        Vehicle vehicleEntity = Vehicle.builder()
                .name(vehicle.getName())
                .fuelType(fuelTypeRepository.getReferenceById(vehicle.getFuelTypeId()))
                .fuelConsuption(vehicle.getFuelConsuption())
                .oilType(vehicle.getOilType())
                .oilChangeInterval(vehicle.getOilChangeInterval())
                .weight(vehicle.getWeight())
                .build();
        vehicleEntity = vehicleRepository.save(vehicleEntity);
        return mapToResponse(vehicleEntity);
    }

}
