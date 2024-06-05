package br.edu.ies.aps8.repository;

import br.edu.ies.aps8.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TripRepository extends JpaRepository<Trip, Long> {
}
