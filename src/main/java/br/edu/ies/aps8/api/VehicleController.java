package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.vehicle.VehicleRequest;
import br.edu.ies.aps8.dto.vehicle.VehicleResponse;
import br.edu.ies.aps8.model.Vehicle;
import br.edu.ies.aps8.repository.FuelTypeRepository;
import br.edu.ies.aps8.repository.VehicleRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehicles")
@RequiredArgsConstructor
public class VehicleController {
    private final VehicleRepository vehicleRepository;
    private final FuelTypeRepository fuelTypeRepository;

    private static VehicleResponse mapToResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .name(vehicle.getName())
                .fuelType(vehicle.getFuelType())
                .oilType(vehicle.getOilType())
                .oilChangeInterval(vehicle.getOilChangeInterval())
                .weight(vehicle.getWeight())
                .build();
    }

    @GetMapping("/{id}")
    public VehicleResponse getVehicle(@PathVariable Long id) {
        return vehicleRepository.findById(id)
                .map(VehicleController::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
    }

    @GetMapping
    public List<VehicleResponse> getVehicle() {
        return vehicleRepository.findAll().stream()
                .map(VehicleController::mapToResponse)
                .toList();
    }

    @PostMapping
    public VehicleResponse createVehicle(@Valid @RequestBody VehicleRequest vehicleRequest) {
        Vehicle vehicle = Vehicle.builder()
                .name(vehicleRequest.getName())
                .fuelType(fuelTypeRepository.findById(vehicleRequest.getFuelTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("Fuel type not found")))
                .oilType(vehicleRequest.getOilType())
                .oilChangeInterval(vehicleRequest.getOilChangeInterval())
                .weight(vehicleRequest.getWeight())
                .build();
        vehicle = vehicleRepository.save(vehicle);
        return mapToResponse(vehicle);
    }

    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
    }

}
