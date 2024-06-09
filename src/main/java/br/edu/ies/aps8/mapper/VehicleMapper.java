package br.edu.ies.aps8.mapper;

import br.edu.ies.aps8.dto.vehicle.VehicleRequest;
import br.edu.ies.aps8.dto.vehicle.VehicleResponse;
import br.edu.ies.aps8.model.Vehicle;
import br.edu.ies.aps8.repository.FuelTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleMapper {
    private final FuelTypeRepository fuelTypeRepository;

    public VehicleResponse mapToResponse(Vehicle vehicle) {
        return VehicleResponse.builder()
                .id(vehicle.getId())
                .name(vehicle.getName())
                .fuelType(vehicle.getFuelType())
                .oilType(vehicle.getOilType())
                .oilCapacity(vehicle.getOilCapacity())
                .oilChangeInterval(vehicle.getOilChangeInterval())
                .batteryCapacity(vehicle.getBatteryCapacity())
                .batteryChangeInterval(vehicle.getBatteryChangeInterval())
                .tireCapacity(vehicle.getTireCapacity())
                .tireChangeInterval(vehicle.getTireChangeInterval())
                .fuelCapacity(vehicle.getFuelCapacity())
                .weight(vehicle.getWeight())
                .build();
    }

    public Vehicle mapToModel(VehicleRequest vehicleRequest) {
        return Vehicle.builder()
                .name(vehicleRequest.getName())
                .fuelType(fuelTypeRepository.findById(vehicleRequest.getFuelTypeId())
                        .orElseThrow(() -> new IllegalArgumentException("Fuel type not found")))
                .oilType(vehicleRequest.getOilType())
                .oilCapacity(vehicleRequest.getOilCapacity())
                .oilChangeInterval(vehicleRequest.getOilChangeInterval())
                .batteryCapacity(vehicleRequest.getBatteryCapacity())
                .batteryChangeInterval(vehicleRequest.getBatteryChangeInterval())
                .tireCapacity(vehicleRequest.getTireCapacity())
                .tireChangeInterval(vehicleRequest.getTireChangeInterval())
                .fuelCapacity(vehicleRequest.getFuelCapacity())
                .weight(vehicleRequest.getWeight())
                .build();
    }

}
