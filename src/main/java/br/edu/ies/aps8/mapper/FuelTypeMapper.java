package br.edu.ies.aps8.mapper;

import br.edu.ies.aps8.dto.fueltype.FuelTypeRequest;
import br.edu.ies.aps8.dto.fueltype.FuelTypeResponse;
import br.edu.ies.aps8.model.FuelType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuelTypeMapper {

    public FuelTypeResponse mapToResponse(FuelType fuelType) {
        return FuelTypeResponse.builder()
                .id(fuelType.getId())
                .name(fuelType.getName())
                .emissionFactor(fuelType.getEmissionFactor())
                .emissionFactorUnit(fuelType.getEmissionFactorUnit())
                .build();
    }

    public FuelType mapToModel(FuelTypeRequest fuelTypeRequest) {
        return FuelType.builder()
                .name(fuelTypeRequest.getName())
                .emissionFactor(fuelTypeRequest.getEmissionFactor())
                .emissionFactorUnit(fuelTypeRequest.getEmissionFactorUnit())
                .build();
    }
}
