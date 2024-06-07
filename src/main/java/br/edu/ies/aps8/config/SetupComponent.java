package br.edu.ies.aps8.config;

import br.edu.ies.aps8.model.*;
import br.edu.ies.aps8.repository.FuelTypeRepository;
import br.edu.ies.aps8.repository.TripRepository;
import br.edu.ies.aps8.repository.UserRepository;
import br.edu.ies.aps8.repository.VehicleRepository;
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
    private final VehicleRepository vehicleRepository;
    private final TripRepository tripRepository;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.default-password}")
    private String defaultPassword;

    @PostConstruct
    public void setup() {
        setupUser();
        setupFuelTypes();
        setupVehicles();
        setupTrips();
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

    private void setupFuelTypes() {
        if (!fuelTypeRepository.findAll().isEmpty()) {
            log.info("Fuel Types already setup");
            return;
        }
        var fuelTypes = List.of(
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

    private void setupVehicles() {
        if (!vehicleRepository.findAll().isEmpty()) {
            log.info("Vehicles already setup");
            return;
        }
        var vehicles = List.of(
                Vehicle.builder()
                        .name("Celta")
                        .fuelType(fuelTypeRepository.findById(1L).get())
                        .oilType(OilType.SEMI_SYNTHETIC)
                        .oilChangeInterval(7500.0)
                        .weight(890.0)
                        .fuelCapacity(54.0)
                        .build(),
                Vehicle.builder()
                        .name("Civic")
                        .fuelType(fuelTypeRepository.findById(1L).get())
                        .oilType(OilType.SEMI_SYNTHETIC)
                        .oilChangeInterval(5000.0)
                        .weight(1449.0)
                        .fuelCapacity(40.0)
                        .build(),
                Vehicle.builder()
                        .name("Compass")
                        .fuelType(fuelTypeRepository.findById(2L).get())
                        .oilType(OilType.SYNTHETIC)
                        .oilChangeInterval(5000.0)
                        .weight(1734.0)
                        .fuelCapacity(60.0)
                        .build(),
                Vehicle.builder()
                        .name("Kicks")
                        .fuelType(fuelTypeRepository.findById(3L).get())
                        .oilType(OilType.SEMI_SYNTHETIC)
                        .oilChangeInterval(10000.0)
                        .weight(1139.0)
                        .fuelCapacity(41.0)
                        .build()
        );
        vehicles.forEach(vehicleRepository::save);
        log.info("Vehicles successfully setup");
    }

    private void setupTrips() {
        if (!tripRepository.findAll().isEmpty()) {
            log.info("Trips already setup");
            return;
        }
        // TODO: Add more trips
    }

}