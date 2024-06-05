package br.edu.ies.aps8.repository;

import br.edu.ies.aps8.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
}
