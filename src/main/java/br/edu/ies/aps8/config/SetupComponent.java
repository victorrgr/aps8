package br.edu.ies.aps8.config;

import br.edu.ies.aps8.model.*;
import br.edu.ies.aps8.repository.FuelTypeRepository;
import br.edu.ies.aps8.repository.UserRepository;
import br.edu.ies.aps8.util.GallonToLiterConverter;
import br.edu.ies.aps8.util.MMBtuToMJConverter;
import br.edu.ies.aps8.util.SIConverter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupComponent {
    private final UserRepository userRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.default-password}")
    private String defaultPassword;

    @PostConstruct
    public void setup() {
        setupUser();
        setupFuelTypes();
    }

    private void setupFuelTypes() {
        if (!fuelTypeRepository.findAll().isEmpty()) {
            log.info("Fuel Types already setup");
            return;
        }
        var fuelTypes = List.of(
//                FuelType.builder()
//                        .name("Natural Gas")
//                        .emissionFactor(mmBtuToMJConverter.convert(53.1))
//                        .emissionFactorUnit(EmissionFactorUnit.KG_CO2_MJ)
//                        .unit(Unit.CUBIC_METER)
//                        .build(),
                FuelType.builder()
                        .name("Gasoline")
                        .emissionFactor(8.78 / SIConverter.GALLON_TO_LITER)
                        .emissionFactorUnit(EmissionFactorUnit.KG_CO2_L)
                        .unit(Unit.LITER)
                        .build(),
                FuelType.builder()
                        .name("Diesel")
                        .emissionFactor(10.21 / SIConverter.GALLON_TO_LITER)
                        .emissionFactorUnit(EmissionFactorUnit.KG_CO2_L)
                        .unit(Unit.LITER)
                        .build(),
                FuelType.builder()
                        .name("Ethanol")
                        .emissionFactor(5.75 / SIConverter.GALLON_TO_LITER)
                        .emissionFactorUnit(EmissionFactorUnit.KG_CO2_L)
                        .unit(Unit.LITER)
                        .build()
        );
        fuelTypes.forEach(fuelTypeRepository::save);
        log.info("Fuel Types successfully setup");
    }

    private void setupUser() {
        if (!userRepository.findByRoles(Role.ADMIN).isEmpty()) {
            log.info("Admin User already setup");
            return;
        }
        String encodedPassword = passwordEncoder.encode(defaultPassword);
        userRepository.save(User.builder()
                .username("admin")
                .password(encodedPassword)
                .roles(List.of(Role.ADMIN))
                .build());
        log.info("Admin User successfully setup");
    }

}