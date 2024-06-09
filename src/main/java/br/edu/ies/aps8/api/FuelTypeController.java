package br.edu.ies.aps8.api;

import br.edu.ies.aps8.dto.fueltype.FuelTypeRequest;
import br.edu.ies.aps8.dto.fueltype.FuelTypeResponse;
import br.edu.ies.aps8.mapper.FuelTypeMapper;
import br.edu.ies.aps8.model.FuelType;
import br.edu.ies.aps8.repository.FuelTypeRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/fueltypes")
@RequiredArgsConstructor
public class FuelTypeController {
    private final FuelTypeRepository fuelTypeRepository;
    private final FuelTypeMapper fuelTypeMapper;

    @GetMapping("/{id}")
    public FuelType findById(@PathVariable Long id) {
        return fuelTypeRepository.findById(id)
                .orElse(null);
    }

    @GetMapping
    public List<FuelTypeResponse> findAll() {
        return fuelTypeRepository.findAll().stream()
                .map(fuelTypeMapper::mapToResponse)
                .toList();
    }

    @PostMapping
    public FuelTypeResponse save(@Valid @RequestBody FuelTypeRequest fuelTypeRequest) {
        FuelType fuelType = fuelTypeMapper.mapToModel(fuelTypeRequest);
        fuelType = fuelTypeRepository.save(fuelType);
        return fuelTypeMapper.mapToResponse(fuelType);
    }

    @PostMapping("/batch")
    public List<FuelTypeResponse> saveBatch(@RequestBody List<FuelTypeRequest> fuelTypeRequests) {
        return fuelTypeRequests.stream()
                .map(this::save)
                .toList();
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        FuelType fuelType = fuelTypeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Fuel type with id '%s' not found".formatted(id)));
        fuelTypeRepository.delete(fuelType);
    }

}
