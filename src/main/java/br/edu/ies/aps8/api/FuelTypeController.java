package br.edu.ies.aps8.api;

import br.edu.ies.aps8.model.FuelType;
import br.edu.ies.aps8.repository.FuelTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/fueltypes")
@RequiredArgsConstructor
public class FuelTypeController {
    private final FuelTypeRepository fuelTypeRepository;

    @GetMapping
    public List<FuelType> getFuelTypes() {
        return fuelTypeRepository.findAll();
    }
}
