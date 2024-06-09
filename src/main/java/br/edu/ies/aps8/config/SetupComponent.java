package br.edu.ies.aps8.config;

import br.edu.ies.aps8.dto.fueltype.FuelTypeRequest;
import br.edu.ies.aps8.dto.trip.TripRequest;
import br.edu.ies.aps8.dto.vehicle.VehicleRequest;
import br.edu.ies.aps8.mapper.FuelTypeMapper;
import br.edu.ies.aps8.mapper.TripMapper;
import br.edu.ies.aps8.mapper.VehicleMapper;
import br.edu.ies.aps8.model.Role;
import br.edu.ies.aps8.model.User;
import br.edu.ies.aps8.repository.FuelTypeRepository;
import br.edu.ies.aps8.repository.TripRepository;
import br.edu.ies.aps8.repository.UserRepository;
import br.edu.ies.aps8.repository.VehicleRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupComponent {
    private final UserRepository userRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final FuelTypeMapper fuelTypeMapper;
    private final VehicleRepository vehicleRepository;
    private final VehicleMapper vehicleMapper;
    private final TripRepository tripRepository;
    private final TripMapper tripMapper;
    private final ObjectMapper objectMapper;
    private final PasswordEncoder passwordEncoder;

    @Value("${application.default-password}")
    private String defaultPassword;

    @Value("${application.input-test-data}")
    private boolean inputTestData;

    @PostConstruct
    public void setup() {
        setupUser();
        if (inputTestData) {
            setupFuelTypes();
            setupVehicles();
            setupTrips();
        }
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
        try {
            File file = new File("./data/test/fuel-types.json");
            List<FuelTypeRequest> fuelTypes = objectMapper.readValue(file, new TypeReference<>(){});
            fuelTypes.stream()
                    .map(fuelTypeMapper::mapToModel)
                    .forEach(fuelTypeRepository::save);
            log.info("Fuel Types successfully setup");
        } catch (IOException e) {
            log.error("Error reading fuel-types.json", e);
        }
    }

    private void setupVehicles() {
        if (!vehicleRepository.findAll().isEmpty()) {
            log.info("Vehicles already setup");
            return;
        }
        try {
            File file = new File("./data/test/vehicles.json");
            List<VehicleRequest> vehicles = objectMapper.readValue(file, new TypeReference<>(){});
            vehicles.stream()
                    .map(vehicleMapper::mapToModel)
                    .forEach(vehicleRepository::save);
            log.info("Vehicles successfully setup");
        } catch (IOException e) {
            log.error("Error reading vehicles.json", e);
        }
    }

    private void setupTrips() {
        if (!tripRepository.findAll().isEmpty()) {
            log.info("Trips already setup");
            return;
        }
        try {
            File file = new File("./data/test/trips.json");
            List<TripRequest> trips = objectMapper.readValue(file, new TypeReference<>(){});
            trips.stream()
                    .map(tripMapper::mapToModel)
                    .forEach(tripRepository::save);
            log.info("Trips successfully setup");
        } catch (IOException e) {
            log.error("Error reading trips.json", e);
        }
    }

}