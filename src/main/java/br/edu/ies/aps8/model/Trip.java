package br.edu.ies.aps8.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trip", schema = "general")
@SequenceGenerator(name = "TRIP_GENERATOR", schema = "security", sequenceName = "SEQ_TRIP", allocationSize = 1)
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TRIP_GENERATOR")
    private Long id;
    /**
     * liters
     */
    private double fuelAmount;
    /**
     * kilometers
     */
    private double distance;
    @ManyToOne
    @JoinColumn(name = "vehicle_id", referencedColumnName = "id")
    private Vehicle vehicle;
}