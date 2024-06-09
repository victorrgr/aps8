package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.vehicle.VehicleRequest;
import br.edu.ies.aps8.dto.vehicle.VehicleResponse;
import br.edu.ies.aps8.mapper.VehicleMapper;
import br.edu.ies.aps8.model.Vehicle;
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
    private final VehicleMapper vehicleMapper;

    @GetMapping("/{id}")
    public VehicleResponse findById(@PathVariable Long id) {
        return vehicleRepository.findById(id)
                .map(vehicleMapper::mapToResponse)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
    }

    @GetMapping
    public List<VehicleResponse> findAll() {
        return vehicleRepository.findAll().stream()
                .map(vehicleMapper::mapToResponse)
                .toList();
    }

    @PostMapping
    public VehicleResponse save(@Valid @RequestBody VehicleRequest vehicleRequest) {
        Vehicle vehicle = vehicleMapper.mapToModel(vehicleRequest);
        vehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.mapToResponse(vehicle);
    }

    @PutMapping("/{id}")
    public VehicleResponse update(@PathVariable Long id, @Valid @RequestBody VehicleRequest vehicleRequest) {
        if (!vehicleRepository.existsById(id)) {
            throw new IllegalArgumentException("Vehicle not found");
        }
        Vehicle vehicle = vehicleMapper.mapToModel(vehicleRequest);
        vehicle.setId(id);
        vehicle = vehicleRepository.save(vehicle);
        return vehicleMapper.mapToResponse(vehicle);
    }

    @PostMapping("/batch")
    public List<VehicleResponse> saveBatch(@Valid @RequestBody List<VehicleRequest> vehicleRequests) {
        return vehicleRequests.stream()
                .map(this::save)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Vehicle not found"));
        vehicleRepository.delete(vehicle);
    }

}
