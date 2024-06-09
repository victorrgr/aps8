package br.edu.ies.aps8.repository;

import br.edu.ies.aps8.model.Trip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TripRepository extends JpaRepository<Trip, Long> {
    @Query("SELECT t FROM Trip t ORDER BY t.date ASC")
    List<Trip> findAllOrderByDateAsc();
}
