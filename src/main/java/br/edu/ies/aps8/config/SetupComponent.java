package br.edu.ies.aps8.config;

import br.edu.ies.aps8.model.FuelType;
import br.edu.ies.aps8.model.Role;
import br.edu.ies.aps8.model.User;
import br.edu.ies.aps8.repository.FuelTypeRepository;
import br.edu.ies.aps8.repository.UserRepository;
import br.edu.ies.aps8.util.GallonToMJConverter;
import br.edu.ies.aps8.util.MMBtuToMJConverter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

import static br.edu.ies.aps8.util.SIConverter.KG_CO2_MJ;

@Component
@RequiredArgsConstructor
@Slf4j
public class SetupComponent {
    private final UserRepository userRepository;
    private final FuelTypeRepository fuelTypeRepository;
    private final GallonToMJConverter gallonToMJConverter;
    private final MMBtuToMJConverter mmBtuToMJConverter;
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
                FuelType.builder()
                        .name("Natural Gas")
                        .emissionFactor(mmBtuToMJConverter.convert(53.1))
                        .unit(KG_CO2_MJ)
                        .build(),
                FuelType.builder()
                        .name("Gasoline")
                        .emissionFactor(gallonToMJConverter.convert(8.89))
                        .unit(KG_CO2_MJ)
                        .build(),
                FuelType.builder()
                        .name("Diesel")
                        .emissionFactor(gallonToMJConverter.convert(10.16))
                        .unit(KG_CO2_MJ)
                        .build(),
                FuelType.builder()
                        .name("Ethanol")
                        .emissionFactor(gallonToMJConverter.convert(5.67))
                        .unit(KG_CO2_MJ)
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