package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.fueltype.FuelTypeRequest;
import br.edu.ies.aps8.model.FuelType;
import br.edu.ies.aps8.repository.FuelTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fueltypes")
@RequiredArgsConstructor
public class FuelTypeController {
    private final FuelTypeRepository fuelTypeRepository;

    private FuelType mapToResponse(FuelType fuelType) {
        return FuelType.builder()
                .id(fuelType.getId())
                .name(fuelType.getName())
                .emissionFactor(fuelType.getEmissionFactor())
                .unit(fuelType.getUnit())
                .build();
    }

    @GetMapping
    public List<FuelType> getFuelTypes() {
        return fuelTypeRepository.findAll();
    }

    @GetMapping("/{id}")
    public FuelType getFuelType(@PathVariable Long id) {
        return fuelTypeRepository.findById(id)
                .orElse(null);
    }

    @PostMapping
    public FuelType createFuelType(@RequestBody FuelTypeRequest fuelTypeRequest) {
        FuelType fuelType = FuelType.builder()
                .name(fuelTypeRequest.getName())
                .emissionFactor(fuelTypeRequest.getEmissionFactor())
                .unit(fuelTypeRequest.getUnit())
                .build();
        fuelType = fuelTypeRepository.save(fuelType);
        return mapToResponse(fuelType);
    }

    @DeleteMapping("/{id}")
    public void deleteFuelType(@PathVariable Long id) {
        FuelType fuelType = fuelTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fuel type with id '%s' not found".formatted(id)));
        fuelTypeRepository.delete(fuelType);
    }

}
